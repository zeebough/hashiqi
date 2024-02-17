package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.dao.UserDao;
import com.ghtt.socialplatform.dao.UserSaltDao;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.domain.UserSalt;
import com.ghtt.socialplatform.global.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private static final Integer zero=0;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserSaltDao userSaltDao;

    @Override
    @Transactional
    public void register(User entity){
        if(entity.getPhone()==null||entity.getPhone().trim().equals(""))
            throw new BusinessException(Code.INSERT_ERR,"手机号不能为空");
        if(entity.getPassword()==null||entity.getPassword().trim().equals(""))
            throw new BusinessException(Code.INSERT_ERR,"密码不能为空");
        if(entity.getNickName()==null||entity.getNickName().trim().equals(""))
            throw new BusinessException(Code.INSERT_ERR,"昵称不能为空");
        User existUser=userDao.selectIdAndPwdByPhone(entity.getPhone());
        if(null!=existUser) throw new BusinessException(Code.INSERT_ERR,"该手机号已被注册");
        Long userId=IdWorker.getId();
        entity.setUserId(userId);
        String salt= UUID.randomUUID().toString();
        entity.setPassword(DigestUtil.encrypt(entity.getPassword(),"SHA-512",salt));
        if (userSaltDao.insert(new UserSalt(userId,salt))<1)
            throw new BusinessException(Code.INSERT_ERR,"注册失败，密码加密异常，请稍后再试");
        if(addUser(entity)<1)
            throw new BusinessException(Code.INSERT_ERR,"注册失败，请检查所填信息或稍后再试");
    }

    @Override
    public User login(String phone, String password){
        if(phone==null||phone.trim().equals(""))throw new BusinessException(Code.SELECT_ERR,"手机号不能为空");
        if(password==null||password.trim().equals(""))throw new BusinessException(Code.SELECT_ERR,"密码不能为空");

        User user=userDao.selectIdAndPwdByPhone(phone);
        if(user==null)throw new BusinessException(Code.SELECT_ERR,"用户不存在");
        if (!zero.equals(userDao.selectBanned(user.getUserId())))
            throw new BusinessException(Code.USER_BANNED,"该用户已被封禁");

        String salt=userSaltDao.selectSalt(user.getUserId());
        if (salt==null)
            throw new BusinessException(Code.SELECT_ERR,"密码解析异常");
        if(!DigestUtil.encrypt(password,"SHA-512",salt).equals(user.getPassword()))
            throw new BusinessException(Code.SELECT_ERR,"密码错误");
        return user;
    }

    @Override
    public User selectUserById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> selectUserByName(String userName,Long current,Long size) {
        return userDao.selectPage(new Page<>(current,size),new QueryWrapper<User>().like("userName",userName)).getRecords();
    }

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateUserById(User user) {
       return userDao.updateById(user);
    }

//    @Override
//    public User selectUserByPhone(String phone) {
//        QueryWrapper<User> wrapper=new QueryWrapper<>();
//        wrapper.eq("phone",phone);
//        return userDao.selectOne(wrapper);
//    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUserById(user.getUserId());
    }

    @Override
    public int setPrivate(Long userId) {
        return userDao.setPrivate(userId);
    }

    @Override
    public int setPublic(Long userId) {
        return userDao.setPublic(userId);
    }

    @Override
    public int updatePassword(Long userId, String oldPassword,String newPassword) {
        User user=userDao.selectUserWithPwdById(userId);
        if (null==user)throw new BusinessException(Code.SELECT_ERR,"用户不存在");
        String salt=userSaltDao.selectSalt(userId);
        if (salt==null) throw new BusinessException(Code.SELECT_ERR,"原密码解析异常");
        oldPassword=DigestUtil.encrypt(oldPassword, "SHA-512",salt);
        newPassword=DigestUtil.encrypt(newPassword, "SHA-512",salt);
        if (!user.getPassword().equals(oldPassword)) throw new BusinessException(Code.UPDATE_ERR,"原密码错误，请重新输入");
        return userDao.updatePassword(userId,newPassword);
    }

    @Override
    public boolean selectBanned(Long userId){
        return !zero.equals(userDao.selectBanned(userId));
    }

    @Override
    public List<User> selectSimpleByFuzzyName(String fuzzyName,Long current,Long size) {
        return userDao.selectPage(new Page<>(current, size),new QueryWrapper<User>().select("userId","nickName").like("nickName",fuzzyName)).getRecords();
    }

    @Override
    public User selectMyInfo(Long userId) {
        return userDao.selectDetailedInfoById(userId);
    }
}
