package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghtt.socialplatform.dao.UserDao;
import com.ghtt.socialplatform.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("loveService")
public class LoveServiceImpl extends AbstractParamMapService {
    @Autowired
    protected UserDao userDao;
    @Override
    public List<User> selectUser() {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("isPrivate",0)
                .eq(paramMap.containsKey("sex"),"sex",paramMap.get("sex"))
                .eq(paramMap.containsKey("isHomo"),"isHomo",paramMap.get("isHomo"))
                .eq(paramMap.containsKey("major"),"major",paramMap.get("major"))
                //.eq(paramMap.containsKey("age"),"age",paramMap.get("age"))
                .eq(paramMap.containsKey("country"),"country",paramMap.get("country"))
                .eq(paramMap.containsKey("province"),"province",paramMap.get("province"))
                .eq(paramMap.containsKey("city"),"city",paramMap.get("city"))
                .eq(paramMap.containsKey("district"),"district",paramMap.get("district"))
                .eq(paramMap.containsKey("school"),"school",paramMap.get("school"))
                .eq(paramMap.containsKey("campus"),"campus",paramMap.get("campus"))
                .eq(paramMap.containsKey("college"),"college",paramMap.get("college"))
                .ge(paramMap.containsKey("heightMin"),"height",paramMap.get("heightMin"))
                .le(paramMap.containsKey("heightMax"),"height",paramMap.get("heightMax"))
                .ge(paramMap.containsKey("weightMin"),"weight",paramMap.get("weightMin"))
                .eq(paramMap.containsKey("weightMax"),"weight",paramMap.get("weightMax"));
        return userDao.selectList(wrapper);
    }

}
