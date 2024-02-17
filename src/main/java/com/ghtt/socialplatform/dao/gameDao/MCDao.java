package com.ghtt.socialplatform.dao.gameDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.games.MC;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MCDao extends GameDao,BaseMapper<MC> {
}
