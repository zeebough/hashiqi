package com.ghtt.socialplatform.service.gameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.games.LOL;
import com.ghtt.socialplatform.global.ServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("lol")
public class LOLService extends AttributeMapGameService<LOL>{

    public LOLService(){
        this.c=LOL.class;
    }

    @Override
    public List<LOL> selectPlayer(Long current) {
        QueryWrapper<LOL> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(paramMap.containsKey("zone"),LOL::getZone,paramMap.get("zone"))
                .eq(paramMap.containsKey("sex"), LOL::getSex,paramMap.get("sex"))
                .eq(paramMap.containsKey("school"),LOL::getSchool,paramMap.get("school"))
                .eq(paramMap.containsKey("campus"),LOL::getCampus,paramMap.get("campus"))
                .eq(paramMap.containsKey("college"),LOL::getCollege,paramMap.get("college"))
                .eq(paramMap.containsKey("major"),LOL::getMajor,paramMap.get("major"));
//                .like(paramMap.containsKey("positions"),LOL::getPositions,
//                        paramMap.get("positions")==null?null:StringUtil.sortByUTF8(paramMap.get("positions")))
                //.eq(paramMap.containsKey("ranking"),LOL::getRanking,paramMap.get("ranking"));
        if (paramMap.containsKey("positions")){
            String[] positions=paramMap.get("positions").split("，");
            wrapper.and(positionWrapper->{
                for (String position : positions) {
                    positionWrapper.lambda().or().like(LOL::getPositions, position);
                }
            });
        }
        if (paramMap.containsKey("ranking")){
            String[] rankings=paramMap.get("ranking").split("，");
            wrapper.and(rankingWrapper->{
                for (String ranking : rankings) {
                    rankingWrapper.lambda().or().like(LOL::getRanking, ranking);
                }
            });
//            QueryWrapper<LOL> rankingWrapper=new QueryWrapper<>();
//            for (String ranking : rankings) {
//                rankingWrapper.lambda().or().like(LOL::getRanking, ranking);
//            }
        }
        return dao.selectPage(new Page<>(current, ServerProperties.DEFAULT_PAGE_SIZE),wrapper).getRecords();
    }
}
