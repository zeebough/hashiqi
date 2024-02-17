package com.ghtt.socialplatform.service.gameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.games.LOL;
import com.ghtt.socialplatform.domain.games.MC;
import com.ghtt.socialplatform.global.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mc")
public class MCService extends AttributeMapGameService<MC>{
    public MCService(){
        this.c=MC.class;
    }
    @Override
    public List<MC> selectPlayer(Long current) {
        QueryWrapper<MC> wrapper=new QueryWrapper<>();
        wrapper.lambda()
                .eq(paramMap.containsKey("sex"), MC::getSex,paramMap.get("sex"))
                .eq(paramMap.containsKey("school"),MC::getSchool,paramMap.get("school"))
                .eq(paramMap.containsKey("campus"),MC::getCampus,paramMap.get("campus"))
                .eq(paramMap.containsKey("college"),MC::getCollege,paramMap.get("college"))
                .eq(paramMap.containsKey("major"),MC::getMajor,paramMap.get("major"));
                if (paramMap.containsKey("tags")){
                    String[] tags=paramMap.get("tags").split("，");
                    wrapper.and(tagWrapper->{
                        for (String tag : tags) {
                            tagWrapper.lambda().or().like(MC::getTags, tag);
                        }
                    });
                }
                //.like(paramMap.containsKey("tags"),MC::getTags,
                        //paramMap.get("tags")==null?null:StringUtil.sortByUTF8(paramMap.get("tags")));
        return dao.selectPage(new Page<MC>(current, ServerProperties.DEFAULT_PAGE_SIZE),wrapper).getRecords();
    }
}
