package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.UserSalt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserSaltDao extends BaseMapper<UserSalt> {
    @Select("select salt from user_salt where userId = #{userId}")
    String selectSalt(Long userId);
}
