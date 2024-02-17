package com.ghtt.socialplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.Study;
import com.ghtt.socialplatform.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudyDao extends BaseMapper<Study> {
    //约自习业务专属
    @Select("select sex,major from user where userId=#{userId}")
    User selectSexAndMajorById(Long userId);

    /**
     *
     * @param stu 传入筛选条件
     * @return 某一天的查询结果，类似于12306车票查询的时间设置，不支持查询某一天之后或某一天之前的记录。
     */
    Page<Study> selectStudy(Page<Study> page, Study stu);

}
