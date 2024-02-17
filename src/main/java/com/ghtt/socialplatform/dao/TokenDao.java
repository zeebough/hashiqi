package com.ghtt.socialplatform.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenDao {
    @Insert("insert into token_blacklist(token) values(#{token}) ")
    int banToken(String token);

    @Select("select token from token_blacklist where token=#{token} ")
    String selectXTokenByToken(String token);

//    @Select("select token from token_blacklist where tokenId=#{xtokenId} ")
//    Long selectXTokenById(Long xtokenId);
//
//    @Delete("delete from token_blacklist where tokenId=#{xtokenId} ")
//    int deleteXTokenById(Long xtokenId);
//
//    @Delete("delete from token_blacklist where token=#{xtoken} ")
//    int deleteXTokenByToken(String xtoken);
}
