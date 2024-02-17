package com.ghtt.socialplatform.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.controller.exceptions.SystemException;
import com.ghtt.socialplatform.dao.GameInfoDao;
import com.ghtt.socialplatform.domain.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GameInfoServiceImpl implements GameInfoService {
    @Autowired
    private GameInfoDao gameInfoDao;
    @Override
    public Map<Long,String> selectAllGameInfosAsMap() {
        List<GameInfo> gameInfos =gameInfoDao.selectList(null);
        if (null==gameInfos)throw new SystemException(Code.SELECT_ERR,"游戏列表为空");
        Map<Long,String> idNameMap=new HashMap<>();
        for (GameInfo g:gameInfos
             ) {
            idNameMap.put(g.getGameId(),g.getGameName());
        }
        return idNameMap;
    }

    @Override
    public int insert(GameInfo gameInfo) {
        return gameInfoDao.insert(gameInfo);
    }

    @Override
    public int deleteById(Long gameId) {
        return gameInfoDao.deleteById(gameId);
    }
    @Override
    public int updateGameName(GameInfo gameInfo){
        return gameInfoDao.updateById(gameInfo);
    }

    @Override
    public String selectGameNameById(Long gameId) {
        GameInfo g= gameInfoDao.selectById(gameId);
        if (null==g)throw new BusinessException(Code.SELECT_ERR,"非常抱歉，该游戏目前暂未被收录，我们将持续完善游戏目录覆盖范围");
        return g.getGameName();
    }

    @Override
    public Long selectGameIdByName(String gameName) {
        GameInfo g= gameInfoDao.selectOne(new QueryWrapper<GameInfo>().eq("gameName", gameName));
        if (null==g)throw new BusinessException(Code.SELECT_ERR,"非常抱歉，该游戏目前暂未被收录，我们将持续完善游戏目录覆盖范围");
        return g.getGameId();
    }
}
