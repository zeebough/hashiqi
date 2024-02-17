package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.GameInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameInfoDao extends BaseMapper<GameInfo> {
}
