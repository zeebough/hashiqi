package com.ghtt.socialplatform.dao.gameDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.games.LOL;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LOLDao extends GameDao, BaseMapper<LOL> {

}
