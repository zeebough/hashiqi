package com.ghtt.socialplatform.dao.gameDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.games.Genshin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenshinDao  extends GameDao, BaseMapper<Genshin> {

}
