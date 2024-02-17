
package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.domain.Connections;
import com.ghtt.socialplatform.service.ConnectionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
每人每种联系方式只能添加一个
无需再在增改之前检查数据库中是否已经存在该条联系方式，正常用app访问的人不会在添加该联系方式之前就update。
即使遇到攻击，也不过是多几个小exception，不会影响系统运行
 */
@RestController
@Slf4j
@RequestMapping("/connections")
public class ConnectionsController {
    @Autowired
    private ConnectionsService connectionsService;
    private static final List<String> conTypes=new ArrayList<>();
    static {
        conTypes.add("QQ");
        conTypes.add("WeChat");
    }
    private void check(String conType){
        if(!conTypes.contains(conType))throw new BusinessException(Code.ILLEGAL_INPUT,"暂不支持该联系方式！");
    }

    /*
    每一个联系方式单独发一次request，不允许一次性发过来多个联系方式
     */
    @PostMapping
    public Result addConnections(@RequestAttribute String userId,@RequestParam String conType,@RequestParam String value){
        check(conType);
        Connections con=new Connections(Long.valueOf(userId),conType,value);
        if (connectionsService.addConnections(con)<1)throw new BusinessException(Code.INSERT_ERR,"添加"+conType+"失败，请检查您是否已经添加该联系方式或稍后再试");
        return new Result(Code.INSERT_OK,"成功添加"+conType);
    }

    @PutMapping
    public Result updateConnections(@RequestAttribute String userId,@RequestParam String conType,@RequestParam String value){
        check(conType);
        Connections con=new Connections(Long.valueOf(userId),conType,value);
        if (connectionsService.updateConnections(con)<1)throw new BusinessException(Code.UPDATE_ERR,"更新"+conType+"失败，请稍后再试");
        return new Result(Code.INSERT_OK,"成功更新"+conType);
    }

    @DeleteMapping
    public Result deleteConnection(@RequestAttribute String userId,@RequestParam String conType){
        check(conType);
        Connections con=new Connections(Long.valueOf(userId),conType);
        if (connectionsService.deleteConnection(con)<1)throw new BusinessException(Code.DELETE_ERR,"删除"+conType+"失败，请稍后再试");
        return new Result(Code.DELETE_OK,"成功删除"+conType);
    }

    @GetMapping("/{userID}")
    List<Connections> selectConnectionsById(@PathVariable String userID){
        List<Connections> result = connectionsService.selectConnectionsById(Long.valueOf(userID));
        if (result.size()<1)throw new BusinessException(Code.SELECT_ERR,"该用户尚未添加任何联系方式");
        return result;
    }

    @GetMapping
    List<Connections> selectMyConnections(@RequestAttribute String userId){
        List<Connections> result = connectionsService.selectConnectionsById(Long.valueOf(userId));
        if (result.size()<1)throw new BusinessException(Code.SELECT_ERR,"您尚未添加任何联系方式");
        return result;
    }
}
