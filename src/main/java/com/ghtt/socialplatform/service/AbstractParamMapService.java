package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractParamMapService implements LoveService{
    protected Map<String,String> paramMap=new ConcurrentHashMap<>();
    public void setParamMap(Map<String,String> paramMap){
        this.paramMap=paramMap;
    }
    public abstract List<User> selectUser();

}
