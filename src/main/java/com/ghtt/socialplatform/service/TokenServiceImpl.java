package com.ghtt.socialplatform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ghtt.socialplatform.dao.TokenDao;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.global.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{
    @Autowired
    private TokenDao tokenDao;
    @Override
    public String getToken(User user) {
        String token;
        //accessToken有效期为24h
        long lifespan=86400000L;
        token= JWT.create()
                .withIssuer(ServerProperties.TOKEN_ISSUER)
                .withClaim("type","access")
                .withAudience(Long.toString(user.getUserId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+lifespan))
                .sign(Algorithm.HMAC256(TokenService.prikey));
        return token;
    }

    @Override
    public String getRefreshToken(User user) {
        String refreshToken;
        //refreshToken有效期为14d
        long lifespan=1209600000L;
        refreshToken= JWT.create()
                .withIssuer(ServerProperties.TOKEN_ISSUER)
                .withClaim("type","refresh")
                .withAudience(Long.toString(user.getUserId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+lifespan))
                .sign(Algorithm.HMAC256(TokenService.prikey));
        return refreshToken;
    }

    @Override
    public int  expireToken(String token) {
        return tokenDao.banToken(token);
    }

    @Override
    public boolean banned(String token) {
        //无记录则为null
        return tokenDao.selectXTokenByToken(token) == null;
    }

}
