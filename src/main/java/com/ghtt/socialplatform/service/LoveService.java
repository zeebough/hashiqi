package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.User;

import java.util.List;
import java.util.Map;

public interface LoveService {
    void setParamMap(Map<String,String> paramMap);
    List<User> selectUser();
}
