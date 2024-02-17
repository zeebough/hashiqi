package com.ghtt.socialplatform.service;


import com.ghtt.socialplatform.domain.GameInfo;

import java.util.Map;

public interface GameInfoService {
    Map<Long,String> selectAllGameInfosAsMap();
    int insert(GameInfo gameInfo);
    int deleteById(Long gameId);
    int updateGameName(GameInfo gameInfo);
    String selectGameNameById(Long gameId);
    Long selectGameIdByName(String gameName);
}
