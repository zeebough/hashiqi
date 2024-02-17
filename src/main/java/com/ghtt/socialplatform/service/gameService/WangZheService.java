package com.ghtt.socialplatform.service.gameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.games.WangZhe;
import com.ghtt.socialplatform.global.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.List;

//同表名
@Service("wang_zhe")
public class WangZheService extends AttributeMapGameService<WangZhe> {
    public WangZheService(){
        this.c=WangZhe.class;
    }

    @Override
    public List<WangZhe> selectPlayer(Long current) {
        QueryWrapper<WangZhe> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(paramMap.containsKey("zone"),WangZhe::getZone,paramMap.get("zone"))
                .eq(paramMap.containsKey("sex"), WangZhe::getSex,paramMap.get("sex"))
                .eq(paramMap.containsKey("school"),WangZhe::getSchool,paramMap.get("school"))
                .eq(paramMap.containsKey("campus"),WangZhe::getCampus,paramMap.get("campus"))
                .eq(paramMap.containsKey("college"),WangZhe::getCollege,paramMap.get("college"))
                .eq(paramMap.containsKey("major"),WangZhe::getMajor,paramMap.get("major"))
//                .like(paramMap.containsKey("positions"),WangZhe::getPositions,
//                        paramMap.get("positions")==null?null:StringUtil.sortByUTF8(paramMap.get("positions")))
                .eq(paramMap.containsKey("ranking"),WangZhe::getRanking,paramMap.get("ranking"));
        if (paramMap.containsKey("positions")){
            String[] positions=paramMap.get("positions").split("，");
            wrapper.and(positionWrapper->{
                for (String position : positions) {
                    positionWrapper.lambda().or().like(WangZhe::getPositions, position);
                }
            });
        }
        if (paramMap.containsKey("ranking")){
            String[] rankings=paramMap.get("ranking").split("，");
            wrapper.and(rankingWrapper->{
                for (String ranking : rankings) {
                    rankingWrapper.lambda().or().like(WangZhe::getRanking, ranking);
                }
            });
        }
        return dao.selectPage(new Page<>(current, ServerProperties.DEFAULT_PAGE_SIZE),wrapper).getRecords();
    }
}
