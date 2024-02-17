package com.ghtt.socialplatform.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.controller.Code;
import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.dao.StudyDao;
import com.ghtt.socialplatform.domain.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("studyService")
@Slf4j
public class StudyService{
    @Autowired
    private StudyDao studyDao;

    public StudyService(StudyDao studyDao){
        this.studyDao=studyDao;
    }

    private Map<String,String> paramMap=new ConcurrentHashMap<>();

    public void setParamMap(Map<String,String> paramMap){
        this.paramMap=paramMap;
    }

    public List<Study> selectMyRecords(Long current,Long size){
        Assert.notNull(paramMap.get("userId"),"无userId");
        List<Study> studyList= studyDao.selectPage(new Page<Study>(current,size),new QueryWrapper<Study>().eq("userId",paramMap.get("userId"))).getRecords();
        for (Study s:studyList
             ) {
            StringBuilder fuzzyTime= new StringBuilder()
                    .append(s.getExpectTime().getYear())
                    .append("-")
                    .append(s.getExpectTime().getMonthValue())
                    .append("-")
                    .append(s.getExpectTime().getDayOfMonth())
                    .append(" ");
            if(s.getExpectTime().getHour()<12)fuzzyTime.append("上午");
            else fuzzyTime.append("下午");
            s.setExpectTimeStr(fuzzyTime.toString());
            s.setExpectTime(null);
        }
        return studyList;
    }

    public int deleteMyRecords(List<Long> recordId){
        Assert.notNull(paramMap.get("userId"),"无userId");
        Long userId=Long.valueOf(paramMap.get("userId"));
        List<Study> deleteList= studyDao.selectBatchIds(recordId);
        for (Study s:deleteList
             ) {
            if (!userId.equals(s.getUserId()))throw new BusinessException(Code.ILLEGAL_ACCESS,"非法删除他人数据");
        }
        return studyDao.deleteBatchIds(recordId);
    }

    public int addMyRecords() throws IllegalAccessException {
        Assert.notNull(paramMap.get("userId"),"无userId");
        Assert.notNull(paramMap.get("expectTimeStr"),"无预计时间");
        Study entity=new Study();
        for (String key:paramMap.keySet()
        ) {
            Field field;
            try{
                field= entity.getClass().getDeclaredField(key);
            }catch (NoSuchFieldException e){
                log.error("实体字段不存在");
                continue;
            }
            field.setAccessible(true);
            if(field.getType().equals(Long.class)){
                field.set(entity,Long.valueOf(paramMap.get(key)));
                continue;
            }
            field.set(entity,paramMap.get(key));
        }

        entity.setRecordId(null);
        entity.setIssueTime(null);
        String expectTimeStr=paramMap.get("expectTimeStr");
        String[] timeBeforeHandle=expectTimeStr.split(" ");
        String[] yMd =timeBeforeHandle[0].split("-");
        if("上午".equals(timeBeforeHandle[1]))entity.setExpectTime(LocalDateTime.of(Integer.parseInt(yMd[0]),
                Integer.parseInt(yMd[1]),Integer.parseInt(yMd[2]),11,59));
        else if ("下午".equals(timeBeforeHandle[1]))entity.setExpectTime(LocalDateTime.of(Integer.parseInt(yMd[0]),
                Integer.parseInt(yMd[1]),Integer.parseInt(yMd[2]),23,59));
        else throw new BusinessException(Code.ILLEGAL_INPUT,"请输入正确的时间格式");

        return studyDao.insert(entity);
    }

    public List<Study> selectStudy(Long current,Long size) throws IllegalAccessException {
          Study entity=new Study();
        for (String key:paramMap.keySet()
        ) {
            Field field;
            try{
                field= entity.getClass().getDeclaredField(key);
            }catch (NoSuchFieldException e){
                log.error("实体字段不存在");
                continue;
            }
            field.setAccessible(true);
            if(field.getType().equals(Long.class)){
                field.set(entity,Long.valueOf(paramMap.get(key)));
                continue;
            }
            field.set(entity,paramMap.get(key));
        }
        List<Study> studyList= studyDao.selectStudy(new Page<>(current,size),entity).getRecords();
        for (Study s:studyList
             ) {
            StringBuilder fuzzyTime= new StringBuilder()
                    .append(s.getExpectTime().getYear())
                    .append("-")
                    .append(s.getExpectTime().getMonthValue())
                    .append("-")
                    .append(s.getExpectTime().getDayOfMonth())
                    .append(" ");
            if(s.getExpectTime().getHour()<12)fuzzyTime.append("上午");
            else fuzzyTime.append("下午");
            s.setExpectTimeStr(fuzzyTime.toString());
            s.setExpectTime(null);
        }
        return studyList;
    }
}
