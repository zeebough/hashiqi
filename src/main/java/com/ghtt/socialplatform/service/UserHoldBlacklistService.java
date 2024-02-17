package com.ghtt.socialplatform.service;

import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.domain.multitable.UserHoldBlacklist;

import java.util.List;

public interface UserHoldBlacklistService {
    int add2Blacklist(UserHoldBlacklist userHoldBlacklist);
    int deleteFromBlacklist(UserHoldBlacklist userHoldBlacklist);
    List<User> selectBlackListById(Long holderId);
}
