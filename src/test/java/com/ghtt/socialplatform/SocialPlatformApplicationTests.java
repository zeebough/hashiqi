package com.ghtt.socialplatform;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghtt.socialplatform.dao.UserDao;
import com.ghtt.socialplatform.dao.UserSaltDao;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.domain.UserSalt;
import com.ghtt.socialplatform.global.DigestUtil;
import com.ghtt.socialplatform.global.ServerProperties;
import com.ghtt.socialplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class SocialPlatformApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserSaltDao userSaltDao;

    @Test
    void testInsertUserSalt(){
        userSaltDao.insert(new UserSalt(2L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(333L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1919810L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1550335344883388418L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1556505010295017474L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566052793842892802L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566089086014554114L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566257567481692161L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566258776917639169L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566258866080153602L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1566260048202792962L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1568060144833769473L,ServerProperties.PASSWORD_SALT));
        userSaltDao.insert(new UserSalt(1601167432063975428L,ServerProperties.PASSWORD_SALT));
//        ''
//        ''
//        ''
//        ''
//        ''
//        ''
//        ''
//        ''

    }

    @Test
    void testList(){
        List<Long> longs=new ArrayList<>();
        longs.add(1L);
        longs.add(2L);
        userDao.selectList(new QueryWrapper<User>().select("userId","nickName").in("userId",longs));
    }

    @Test
    void testUpdateUser(){
        User user=new User(1L);
        user.setNickName("vans");
        userService.updateUserById(user);

    }

    @Test
    void testtt(){
        List<String> pwd=new ArrayList<>();
        pwd.add("114514");
        pwd.add("1919810");
        pwd.add("121564");
        pwd.add("102102102");
        pwd.add("henghenghengawula123");
        pwd.add("nishiyige1919");
        pwd.add("baozi11223");
        pwd.add("11111");
        pwd.add("11111");
        pwd.add("22222");
        pwd.add("23232");
        pwd.add("333444");
        pwd.add("xry0x4F5DA2");
        pwd.add("20021104");

        for (String s : pwd) {
            System.out.println(DigestUtil.encrypt(s, "SHA-512",ServerProperties.PASSWORD_SALT));
        }
    }

}
