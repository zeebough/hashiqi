package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.GameInfo;
import com.ghtt.socialplatform.domain.multitable.UserGame;

import java.util.List;

public interface UserGameService {
    int addGame(UserGame userGame);
    int deleteGame(UserGame userGame);
    int setPrivate(UserGame userGame);
    int setPublic(UserGame userGame);
    List<GameInfo> selectPersonalGames(Long userId);
    List<GameInfo> selectPersonalGamesPublicOnly(Long userId);
}
