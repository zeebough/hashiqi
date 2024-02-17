package com.ghtt.socialplatform.service.gameService;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghtt.socialplatform.domain.games.Game;
import com.ghtt.socialplatform.service.UserGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AttributeMapGameService<T extends Game> implements GameService<T> {

    @Autowired
    protected BaseMapper<T> dao;

    @Autowired
    protected UserGameService userGameService;

    protected Map<String, String> paramMap = new ConcurrentHashMap<>();

    // 子类必须以构造方法初始化c，否则c==null
    protected Class<T> c;

    @Override
    public void setParams(Map<String, String> params) {
        this.paramMap = params;
    }

    @Override
    public int addPlayer() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        T entity = bindData2Entity();
        return dao.insert(entity);
    }

    @Override
    public int deletePlayer() {
        return dao.deleteById(Long.valueOf(paramMap.get("userId")));
    }

    @Override
    public int updatePlayer() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        T entity = bindData2Entity();
        return dao.updateById(entity);
    }

    @Override
    public T bindData2Entity() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        T entity = c.getDeclaredConstructor().newInstance();
        for (String key : paramMap.keySet()) {
            Field field;
            try {
                field = entity.getClass().getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                log.error("实体字段不存在");
                continue;
            }
            field.setAccessible(true);
            if (field.getType().equals(Long.class)) {
                field.set(entity, Long.valueOf(paramMap.get(key)));
                continue;
            }
            if (field.getType().equals(Integer.class)) {
                field.set(entity, Integer.valueOf(paramMap.get(key)));
                continue;
            }
            if (field.getType().equals(Float.class)) {
                field.set(entity, Float.valueOf(paramMap.get(key)));
                continue;
            }
            if (field.getType().equals(Double.class)) {
                field.set(entity, Double.valueOf(paramMap.get(key)));
                continue;
            }
            field.set(entity, paramMap.get(key));
        }
        return entity;
    }

    public abstract List<T> selectPlayer(Long current);
}
