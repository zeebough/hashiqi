package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.User;

import java.util.List;

public interface UserService {
    User selectUserById(Long id);

    List<User> selectUserByName(String userName,Long current,Long size);

    void register(User entity);

    User login(String phone, String password);

    int addUser(User user);

    int updateUserById(User user);

    //User selectUserByPhone(String phone);

    int deleteUser(User user);

    int setPrivate(Long userId);

    int setPublic(Long userId);

    int updatePassword(Long userId,String oldPassword,String newPassword);

    boolean selectBanned(Long userId);

    List<User> selectSimpleByFuzzyName(String fuzzyName,Long current,Long size);

    User selectMyInfo(Long userId);

}
