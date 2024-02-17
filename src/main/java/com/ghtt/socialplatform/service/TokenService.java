package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.User;
import java.util.UUID;

public interface TokenService {
    String prikey= UUID.randomUUID().toString();
    String getToken(User user);
    String getRefreshToken(User user);
    int expireToken(String token);
    boolean banned(String token);

}
