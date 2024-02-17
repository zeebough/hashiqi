package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.domain.Study;
import com.ghtt.socialplatform.global.ServerProperties;
import com.ghtt.socialplatform.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@RestController
@RequestMapping("/study")
public class StudyController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public StudyService getStudyService(){
        return this.applicationContext.getBean("studyService",StudyService.class);
    }

    @GetMapping("/myRecords")
    public Result selectMyRecords(@NonNull @RequestAttribute String userId,@RequestParam Long current){
        StudyService studyService=getStudyService();
        Map<String,String> params= new HashMap<>();
        params.put("userId",userId);
        studyService.setParamMap(params);
        List<Study> studyList= studyService.selectMyRecords(current, ServerProperties.DEFAULT_PAGE_SIZE);
        if (studyList.size()<1)return new Result(Code.SELECT_ERR,"没有记录");
        return new Result(Code.SELECT_OK,null,studyList);
    }

    @PostMapping("/myRecords")
    public Result addMyRecords(HttpServletRequest request,@NonNull @RequestAttribute String userId) throws Exception{
        StudyService studyService=getStudyService();
        Map<String, String> params = new HashMap<>();
        for (Iterator<String> it = request.getParameterNames().asIterator(); it.hasNext(); ) {
            String key = it.next();
            String value = request.getParameter(key);
            params.put(key, value);
        }
        params.put("userId",userId);
        studyService.setParamMap(params);
        if (studyService.addMyRecords()<1)throw new BusinessException(Code.INSERT_ERR,"添加失败请稍后再试");
        return new Result(Code.INSERT_OK,"添加成功");
    }

    @DeleteMapping("/myRecords")
    public Result deleteMyRecords(@NonNull @RequestAttribute String userId,@NonNull @RequestParam String recordId){
        String[] idStrs=recordId.split(",");
        List<Long> ids=new ArrayList<>();
        for (int i = 0; i < idStrs.length; i++) {
            ids.add(Long.valueOf(idStrs[i]));
        }
        StudyService studyService=getStudyService();
        Map<String,String> params= new HashMap<>();
        params.put("userId",userId);
        studyService.setParamMap(params);
        if (studyService.deleteMyRecords(ids)<1)throw new BusinessException(Code.DELETE_ERR,"删除失败，请稍后再试");
        return new Result(Code.DELETE_OK,"删除成功");
    }

    @GetMapping
    public Result selectStudy(HttpServletRequest request,@NonNull @RequestAttribute String userId,@RequestParam Long current) throws Exception {
        Map<String, String> params = new ConcurrentHashMap<>();
        for (Iterator<String> it = request.getParameterNames().asIterator(); it.hasNext(); ) {
            String key = it.next();
            String value = request.getParameter(key);
            params.put(key, value);
        }
        params.put("userId", userId);
        StudyService studyService=getStudyService();
        studyService.setParamMap(params);
        List<Study> studyList= studyService.selectStudy(current,ServerProperties.DEFAULT_PAGE_SIZE);
        if (studyList.size()<1)return new Result(Code.SELECT_ERR,"没有记录");
        return new Result(Code.SELECT_OK,null,studyList);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
