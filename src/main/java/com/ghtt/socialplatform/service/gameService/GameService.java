package com.ghtt.socialplatform.service.gameService;

import java.util.List;
import java.util.Map;

public interface GameService<T> {
    void setParams(Map<String, String> params);
    int addPlayer() throws Exception;
    int deletePlayer();
    int updatePlayer() throws Exception;
    T bindData2Entity() throws Exception;
    List<T> selectPlayer(Long current);
}