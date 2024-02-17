package com.ghtt.socialplatform.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.Result;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.global.ServerProperties;
import com.ghtt.socialplatform.service.TokenService;
import com.ghtt.socialplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object handler) {
        //throws IOException, ServletException {

        try {
            httpServletRequest.setCharacterEncoding("UTF-8");
            httpServletResponse.reset();
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");

            //浏览器跨域请求时，返回200放行
            if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
                return true;
            }

            PrintWriter pw = httpServletResponse.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String token = httpServletRequest.getHeader("token");
            // 执行认证
            if (null == token) {
                pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "无token，请重新登录")));
                log.warn("无token，请重新登录");
                return false;
            }
            if (!tokenService.banned(token)) {
                pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "token被禁，请重新登录")));
                log.warn("无效token，请重新登录");
                return false;
            }

            // 获取 token 中的信息
            Date currentTime = new Date();
            String issuer;
            String type;
            String userId;
            Date iss;
            Date exp;
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenService.prikey)).build();
            try {
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                issuer = decodedJWT.getIssuer();
                type = decodedJWT.getClaim("type").asString();
                userId = decodedJWT.getAudience().get(0);
                iss = decodedJWT.getIssuedAt();
                exp = decodedJWT.getExpiresAt();
            } catch (JWTVerificationException e) {
                pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "token验证错误")));
                log.warn("token验证错误");
                return false;
            }
            try {
                if (userService.selectBanned(Long.valueOf(userId))) {
                    if (tokenService.expireToken(token) < 1) log.error("token强制过期异常");
                    pw.write(mapper.writeValueAsString(new Result(Code.USER_BANNED, "该用户已被封禁")));
                    log.warn("该用户已被封禁");
                    return false;
                }
            }catch (NumberFormatException numberFormatException){
                pw.write(mapper.writeValueAsString(new Result(Code.ILLEGAL_INPUT,"请输入正确的数字")));
                log.warn("请输入正确的数字");
                return false;
            }

            if (!ServerProperties.TOKEN_ISSUER.equals(issuer)) {
                pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "token签发者非法")));
                log.warn("token签发者非法");
                return false;
            }
            if (currentTime.before(iss)) {
                pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "token签发时间非法")));
                log.warn("token签发时间非法");
                return false;
            }

            switch (type) {
                case "access":
                    if (currentTime.after(exp)) {
                        pw.write(mapper.writeValueAsString(new Result(Code.REFRESH_ACCESS_TOKEN,null)));
                        return false;
                    }
                    User user = userService.selectUserById(Long.valueOf(userId));
                    if (null == user) {
                        pw.write(mapper.writeValueAsString(new Result(Code.SELECT_ERR, "用户不存在，请重新登录")));
                        log.warn("用户不存在，请重新登录");
                        return false;
                    }
                    httpServletResponse.reset();
                    httpServletRequest.setAttribute("userId", userId);
                    return true;

                case "refresh":
                    if (currentTime.after(exp)) {
                        pw.write(mapper.writeValueAsString(new Result(Code.INVALID_TOKEN, "您的登录时间过长，请重新登录")));
                        log.warn("您的登录时间过长，请重新登录");
                        return false;
                    }

                    User userForRefresh = new User(Long.valueOf(userId));
                    String newToken = tokenService.getToken(userForRefresh);
                    MyHttpRequestWrapper wrapper = new MyHttpRequestWrapper(httpServletRequest);
                    httpServletResponse.reset();
                    wrapper.addHeader("Referer", ServerProperties.INTERNAL_SERVER_REQUEST);
                    wrapper.setAttribute("newToken", newToken);
                    wrapper.setAttribute("userId",userId);
                    wrapper.getRequestDispatcher("/refresh").forward(wrapper, httpServletResponse);
                    return true;

                default:
                    pw.write(mapper.writeValueAsString( new Result(Code.INVALID_TOKEN,"未知token类型")));
                    httpServletResponse.reset();
                    return false;
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()),e);
        }
        return false;
    }
}

