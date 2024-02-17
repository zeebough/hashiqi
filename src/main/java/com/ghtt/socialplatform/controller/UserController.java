package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.domain.multitable.UserHoldBlacklist;
import com.ghtt.socialplatform.global.ServerProperties;
import com.ghtt.socialplatform.service.TokenService;
import com.ghtt.socialplatform.service.UserHoldBlacklistService;
import com.ghtt.socialplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/*
Id代表自己的userId,ID代表被操作对象的userId
 */

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserHoldBlacklistService userHoldBlacklistService;

    @PostMapping("/login")
    public Result login(@NonNull @RequestHeader(value = "phone")String phone,
                        @NonNull @RequestHeader(value = "password")String password){
        //传入参数时，若header中属性未填写，则传入的值为“”
        User user=userService.login(phone,password);
        String token=tokenService.getToken(user);
        String refreshToken=tokenService.getRefreshToken(user);
        return new Result(Code.LOGIN_OK,"登录成功",new String[]{token,refreshToken});
    }

    @PostMapping("/register")
    public Result register(HttpServletRequest request) throws UnsupportedEncodingException, IllegalAccessException {
        request.setCharacterEncoding("UTF-8");
        User entity=new User();
        entity.setPhone(request.getHeader("phone"));
        entity.setPassword(request.getHeader("password"));
        for (String key : request.getParameterMap().keySet()) {
            Field field;
            try{
                field= User.class.getDeclaredField(key);
            }catch (NoSuchFieldException e){
                continue;
            }
            field.setAccessible(true);
            if(field.getType().equals(Long.class)){
                field.set(entity,Long.valueOf(request.getParameter(key)));
                continue;
            }
            if(field.getType().equals(Integer.class)){
                field.set(entity,Integer.valueOf(request.getParameter(key)));
                continue;
            }
            if(field.getType().equals(Float.class)){
                field.set(entity,Float.valueOf(request.getParameter(key)));
                continue;
            }
            if(field.getType().equals(Double.class)){
                field.set(entity,Double.valueOf(request.getParameter(key)));
                continue;
            }
            field.set(entity,request.getParameter(key));
        }
//        entity.setNickName(request.getParameter("nickName"));
//        entity.setSex(request.getParameter("sex"));
//        entity.setCountry(request.getParameter("country"));
//        entity.setProvince(request.getParameter("province"));
//        entity.setCity(request.getParameter("city"));
//        entity.setDistrict(request.getParameter("district"));
//        entity.setSchool(request.getParameter("school"));
//        entity.setCampus(request.getParameter("campus"));
//        entity.setCollege(request.getParameter("college"));
//        entity.setMajor(request.getParameter("major"));
        userService.register(entity);
        return new Result(Code.REGISTER_OK,"注册成功");
    }

    @PutMapping("/privacy/{isPrivate}")
    public Result setPrivacy(@NonNull @PathVariable String isPrivate,@NonNull @RequestAttribute String userId){
        Integer p =Integer.valueOf(isPrivate);
        if (!p.equals(0)&&!p.equals(1))throw new BusinessException(Code.ILLEGAL_INPUT,"非法参数");
        int rows=0;
        if (p.equals(0)) rows=userService.setPublic(Long.valueOf(userId));
        if (p.equals(1)) rows=userService.setPrivate(Long.valueOf(userId));
        if (rows<1)throw new BusinessException(Code.UPDATE_ERR,"修改隐私设置失败，请稍后再试");
        return new Result(Code.INSERT_OK,"修改隐私设置成功");
    }

    @PutMapping("/password")
    public Result updatePassword(HttpServletRequest request,@NonNull @RequestAttribute String userId){
        String oldPassword=request.getParameter("oldPassword");
        String newPassword=request.getParameter("newPassword");
        Assert.notNull(oldPassword,"原密码为空");
        Assert.notNull(newPassword,"新密码为空");
        if(userService.updatePassword(Long.valueOf(userId),oldPassword,newPassword)<1)
            throw new BusinessException(Code.UPDATE_ERR,"密码修改失败，请稍后再试");
        return new Result(Code.UPDATE_OK,"密码修改成功");
    }

    @GetMapping
    public Result selectMyInfo(@NonNull @RequestAttribute String userId){
        //User user=userService.selectUserById(Long.valueOf(userId));
        User user=userService.selectMyInfo(Long.valueOf(userId));
        if(null==user)return new Result(Code.SELECT_ERR,"什么也没有找到");
        return new Result(Code.SELECT_OK,null,user);
    }


    @PutMapping
    public Result updateUserById(@NonNull @RequestAttribute String userId,HttpServletRequest request) throws IllegalAccessException {
        User userForUpdate=new User(Long.valueOf(userId)
        );
        //问题在于userForUpdate的数据绑定上面
        Enumeration<String> paramNames = request.getParameterNames();
        if (request.getParameterNames().hasMoreElements()) {
            System.out.println(request.getParameterNames().nextElement());
        }
        if(!paramNames.hasMoreElements())return new Result(Code.UPDATE_ERR,"更新失败，请填写要更新的值");
        for (Iterator<String> it = request.getParameterNames().asIterator(); it.hasNext(); ) {
            String key=it.next();
            String value=request.getParameter(key);
            Field field;
            try{
                field = userForUpdate.getClass().getDeclaredField(key);
            }catch (NoSuchFieldException e){
                continue;
            }
            field.setAccessible(true);
            if(field.getType().equals(Long.class)){
                field.set(userForUpdate,Long.valueOf(value));
                continue;
            }
            if(field.getType().equals(Integer.class)){
                field.set(userForUpdate,Integer.valueOf(value));
                continue;
            }
            field.set(userForUpdate,value);
        }
        System.out.println(userForUpdate);
        if (userService.updateUserById(userForUpdate) < 1) {
            return new Result(Code.UPDATE_ERR,"更新失败，请稍后再试");
        }
        return new Result(Code.UPDATE_OK,"更新成功");
    }

    @DeleteMapping
    public Result deleteMyAccount(@NonNull @RequestAttribute String userId){
        if(userService.deleteUser(new User(Long.valueOf(userId)))<1)
            throw new BusinessException(Code.DELETE_ERR,"注销失败，请稍后再试");
        return new Result(Code.USER_DELETED,"注销成功");
    }

    //查其他用户
    @GetMapping("/{userID}")
    public Result selectUserById(@PathVariable String userID){
        User user=userService.selectUserById(Long.valueOf(userID));
        if(null==user)return new Result(Code.SELECT_ERR,"什么也没有找到");
        return new Result(Code.SELECT_OK,null,user);
    }

    //暂时关闭精确查询
    //@GetMapping("/name")
    public Result selectUserByName(@RequestParam String userName ,@RequestParam Long current){
        List<User> users= userService.selectUserByName(userName,current,ServerProperties.DEFAULT_PAGE_SIZE);
        if(users.size()<1)return new Result(Code.SELECT_ERR,"什么也没有找到");
        return new Result(Code.SELECT_OK,null,users);
    }

    @PostMapping("/hello")
    public Result hello(){
        return new Result(Code.SELECT_OK,"hello","haha");
    }

    @PostMapping("/blacklist")
    public Result add2Blacklist(@NonNull @RequestAttribute String userId,@NonNull @RequestParam String userID){
        if(userHoldBlacklistService.add2Blacklist(new UserHoldBlacklist(Long.valueOf(userId),Long.valueOf(userID)))<0)
            throw new BusinessException(Code.INSERT_ERR,"添加黑名单失败，请稍后再试");
        return new Result(Code.INSERT_OK,"添加黑名单成功");
    }

    @DeleteMapping("/blacklist")
    public Result deleteFromBlacklist(@NonNull @RequestAttribute String userId,@NonNull @RequestParam String userID){
        if(userHoldBlacklistService.deleteFromBlacklist(new UserHoldBlacklist(Long.valueOf(userId),Long.valueOf(userID)))<0)
            throw new BusinessException(Code.DELETE_ERR,"删除黑名单失败，请稍后再试");
        return new Result(Code.DELETE_OK,"删除黑名单成功");
    }

    @GetMapping("/blacklist")
    public Result selectBlacklist(@NonNull @RequestAttribute String userId){
        List<User> blackList= userHoldBlacklistService.selectBlackListById(Long.valueOf(userId));
        if (blackList.size()<1)throw new BusinessException(Code.SELECT_ERR,"您的黑名单为空");
        return new Result(Code.SELECT_OK,"已拉黑"+blackList.size()+"位用户",blackList);
    }

    //模糊名字查询用户
    @GetMapping("/fuzzyName")
    public Result selectUserByFuzzyName(@RequestParam String fuzzyName,@RequestParam Long current){
        List<User> fuzzyList=userService.selectSimpleByFuzzyName(fuzzyName,current, ServerProperties.DEFAULT_PAGE_SIZE);
        if (fuzzyList.size()<1)throw new BusinessException(Code.SELECT_ERR,"没有找到类似名字的用户");
        return new Result(Code.SELECT_OK,null,fuzzyList);
    }
}
