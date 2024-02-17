package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.GameInfo;
import com.ghtt.socialplatform.domain.multitable.UserGame;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserGameDao extends BaseMapper<UserGame> {

    @Select("select user_game.gameId,gameName from user_game left join game_info on user_game.gameId=game_info.gameId where userId=#{userId}")
    List<GameInfo> selectPersonalGames(Long userId);

    @Select("select user_game.gameId,gameName from user_game left join game_info on user_game.gameId=game_info.gameId where isPrivate=0 and userId=#{userId}")
    List<GameInfo> selectPersonalGamesPublicOnly(Long userId);

}
