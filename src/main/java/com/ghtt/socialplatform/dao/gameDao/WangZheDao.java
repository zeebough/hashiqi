package com.ghtt.socialplatform.dao.gameDao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.games.WangZhe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WangZheDao  extends GameDao,BaseMapper<WangZhe> {
//    IPage<WangZhe> getPlayerList(@Param("sex") String sex, @Param("school") String school,
//                               @Param("campus") String campus, @Param("college") String college,
//                                 @Param("major") String major,@Param("page") Page<WangZhe> page,
//                               @Param(Constants.WRAPPER) Wrapper<WangZhe> wrapper);

}
