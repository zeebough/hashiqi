package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghtt.socialplatform.dao.UserGameDao;
import com.ghtt.socialplatform.domain.GameInfo;
import com.ghtt.socialplatform.domain.multitable.UserGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserGameServiceImpl implements UserGameService{
    @Autowired
    private UserGameDao userGameDao;
    @Override
    public int addGame(UserGame userGame) {
        return userGameDao.insert(userGame);
    }

    @Override
    public int deleteGame(UserGame userGame) {
        return userGameDao.delete(new QueryWrapper<UserGame>()
                .eq("gameId",userGame.getGameId())
                .eq("userId",userGame.getUserId()));
    }

    @Override
    public int setPrivate(UserGame userGame) {
        userGame.setIsPrivate(1);
        return userGameDao.update(userGame,new QueryWrapper<UserGame>()
                .eq("gameId",userGame.getGameId())
                .eq("userId",userGame.getUserId()));
    }

    @Override
    public int setPublic(UserGame userGame) {
        userGame.setIsPrivate(0);
        return userGameDao.update(userGame,new QueryWrapper<UserGame>()
                .eq("gameId",userGame.getGameId())
                .eq("userId",userGame.getUserId()));
    }

    @Override
    public List<GameInfo> selectPersonalGames(Long userId) {
        return userGameDao.selectPersonalGames(userId);
    }

    @Override
    public List<GameInfo> selectPersonalGamesPublicOnly(Long userId) {
        return userGameDao.selectPersonalGamesPublicOnly(userId);
    }
}
