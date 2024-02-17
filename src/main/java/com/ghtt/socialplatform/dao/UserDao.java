package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ghtt.socialplatform.domain.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserDao extends BaseMapper<User>, IPage<User> {
    @Select("select userId,pwd as password from user where userId=#{userId}")
    //@Results({@Result(property = "password", column = "pwd")})
    User selectUserWithPwdById(Long userId);

    @Select("select userId,pwd as password from user where phone=#{phone}")
    User selectIdAndPwdByPhone(String phone);

    @Update("update user set isPrivate = 1 where userId = #{userId}")
    int setPrivate(Long userId);

    @Update("update user set isPrivate = 0 where userId = #{userId}")
    int setPublic(Long userId);

    @Select("select banned from user where userId=#{userId}")
    Integer selectBanned(Long userId);

    @Update("update user set deleted=1,phone = null where userId=#{userId}")
    int deleteUserById(Long userId);

    @Update("update user set pwd=#{password} where userId=#{userId}")
    int updatePassword(Long userId,String password);

    @Select("select userId,nickName,realName,phone,sex,country,province,city,district,school,campus,college,major from user where userId=#{userId}")
    User selectDetailedInfoById(Long userId);
}
