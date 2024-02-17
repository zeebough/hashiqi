package com.ghtt.socialplatform.service.gameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ghtt.socialplatform.domain.games.Genshin;
import com.ghtt.socialplatform.global.ServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("genshin")
@Slf4j
public class GenshinService extends AttributeMapGameService<Genshin>{
    public GenshinService(){
        this.c=Genshin.class;
    }
//    2.筛选信息功能：可以根据所在服务器，世界等级的范围，冒险等级的范围，是否可以嫖资源，以及是否拥有某角色进行筛选。
    @Override
    public List<Genshin> selectPlayer(Long current) {
        //TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), Genshin.class);
        QueryWrapper<Genshin> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(paramMap.containsKey("zone"),Genshin::getZone,paramMap.get("zone"))
                //can not find lambda cache for this property [sex] of entity [com.ghtt.socialplatform.domain.games.Genshin]
                .eq(paramMap.containsKey("sex"),Genshin::getSex,paramMap.get("sex"))
                .eq(paramMap.containsKey("school"),Genshin::getSchool,paramMap.get("school"))
                .eq(paramMap.containsKey("campus"),Genshin::getCampus,paramMap.get("campus"))
                .eq(paramMap.containsKey("college"),Genshin::getCollege,paramMap.get("college"))
                .eq(paramMap.containsKey("major"),Genshin::getMajor,paramMap.get("major"))
                .gt(paramMap.containsKey("worldLevelMin"),Genshin::getWorldLevel,paramMap.get("worldLevelMin"))
                .lt(paramMap.containsKey("worldLevelMax"),Genshin::getWorldLevel,paramMap.get("worldLevelMax"))
                .gt(paramMap.containsKey("AdventureLevelMin"),Genshin::getAdventureLevel,paramMap.get("AdventureLevelMin"))
                .lt(paramMap.containsKey("AdventureLevelMax"),Genshin::getAdventureLevel,paramMap.get("AdventureLevelMax"))
                .eq(paramMap.containsKey("resourceSharable"), Genshin::getResourceSharable,
                        paramMap.get("resourceSharable")==null?null:Integer.valueOf(paramMap.get("resourceSharable")));
//                .like(paramMap.containsKey("characters"),Genshin::getCharacters,
//                        paramMap.get("characters")==null?null:StringUtil.sortByUTF8(paramMap.get("characters")));
        if (paramMap.containsKey("characters")){
            String[] characters=paramMap.get("characters").split("，");
            wrapper.and(characterWrapper->{
                for (String character : characters) {
                    characterWrapper.lambda().or().like(Genshin::getCharacters, character);
                }
            });
        }
        return dao.selectPage(new Page<>(current, ServerProperties.DEFAULT_PAGE_SIZE),wrapper).getRecords();
    }
}
