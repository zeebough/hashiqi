package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.multitable.UserHoldBlacklist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserHoldBlacklistDao extends BaseMapper<UserHoldBlacklist> {
}
