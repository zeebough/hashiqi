package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghtt.socialplatform.dao.ConnectionsDao;
import com.ghtt.socialplatform.domain.Connections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("connections")
public class ConnectionsServiceImpl implements ConnectionsService {
    @Autowired
    private ConnectionsDao connectionsDao;
    @Override
    public int addConnections(Connections connections) {
        return connectionsDao.insert(connections);
    }

    @Override
    public int updateConnections(Connections connections) {
        return connectionsDao.update(connections,new QueryWrapper<Connections>()
                .eq("userId",connections.getUserId())
                .eq("conType",connections.getConType()));
    }

    @Override
    public int deleteConnection(Connections connections) {
        return connectionsDao.delete(new QueryWrapper<Connections>()
                .eq("userId",connections.getUserId())
                .eq("conType",connections.getConType()));
    }

    @Override
    public List<Connections> selectConnectionsById(Long userId) {
        return connectionsDao.selectList(new QueryWrapper<Connections>().eq("userId",userId));
    }
}
