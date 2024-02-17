package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.domain.User;
import com.ghtt.socialplatform.service.LoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@RestController
//@RequestMapping("/love")
public class LoveController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private LoveService getLoveService(){
        return this.applicationContext.getBean("loveService",LoveService.class);
    }

    @PostMapping
    public Result selectUser(HttpServletRequest request){
        Map<String,String> paramMap=new ConcurrentHashMap<>();
        LoveService loveService=getLoveService();
        for (Iterator<String> it = request.getParameterNames().asIterator(); it.hasNext(); ) {
            String key = it.next();
            String value = request.getParameter(key);
            paramMap.put(key, value);
        }
        loveService.setParamMap(paramMap);
        List<User> users= loveService.selectUser();
        if(users.size()<1)return new Result(Code.SELECT_ERR,"未找到符合条件的用户");
        return new Result(Code.SELECT_OK,"共找到"+ users.size()+"位用户",users);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
