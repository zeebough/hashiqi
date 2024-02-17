package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.Connections;

import java.util.List;


public interface ConnectionsService {
    /*----------------------basic crud------------------------*/
    int addConnections(Connections connections);
    int updateConnections(Connections connections);
    int deleteConnection(Connections connections);
    List<Connections> selectConnectionsById(Long userId);
}
