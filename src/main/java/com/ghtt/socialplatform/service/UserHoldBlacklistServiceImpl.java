package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ghtt.socialplatform.dao.UserDao;
import com.ghtt.socialplatform.dao.UserHoldBlacklistDao;
import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.domain.multitable.UserHoldBlacklist;
import com.ghtt.socialplatform.service.UserHoldBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class UserHoldBlacklistServiceImpl implements UserHoldBlacklistService {
    @Autowired
    private UserHoldBlacklistDao userHoldBlacklistDao;
    @Autowired
    private UserDao userDao;
    @Override
    public int add2Blacklist(UserHoldBlacklist userHoldBlacklist) {
        return userHoldBlacklistDao.insert(userHoldBlacklist);
    }

    @Override
    public int deleteFromBlacklist(UserHoldBlacklist userHoldBlacklist) {
        return userHoldBlacklistDao.delete(new QueryWrapper<UserHoldBlacklist>()
                .eq("holderId",userHoldBlacklist.getHolderId())
                .eq("memberId",userHoldBlacklist.getMemberId()));
    }

    @Override
    public List<User> selectBlackListById(Long holderId) {
        QueryWrapper<UserHoldBlacklist> wrapper= Wrappers.query();
        wrapper.select("memberId").eq("holderId",holderId);
//                .groupBy("holderId")
//                .having("holderId = {0}",holderId);
        List<Map<String,Object>> memberIds=userHoldBlacklistDao.selectMaps(wrapper);
        List<BigInteger> ids=new ArrayList<>();
        for (Map<String,Object> map:memberIds
             ) {
            ids.add((BigInteger) map.get("memberId"));
        }
        return userDao.selectList(new QueryWrapper<User>().select("userId","nickName").in("userId",ids));
    }
}
