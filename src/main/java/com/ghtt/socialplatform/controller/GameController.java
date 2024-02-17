package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.domain.GameInfo;
import com.ghtt.socialplatform.domain.multitable.UserGame;
import com.ghtt.socialplatform.service.GameInfoService;
import com.ghtt.socialplatform.service.UserGameService;
import com.ghtt.socialplatform.service.gameService.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController implements ApplicationContextAware {
    private ApplicationContext applicationContext;
//    @Autowired
//    private Map<String,GameService> gameServiceMap=new HashMap<>();
    @Autowired
    private GameInfoService gameInfoService;
    @Autowired
    private UserGameService userGameService;

    private GameService<?> getGameService(String gameServiceName){
        return this.applicationContext.getBean(gameServiceName,GameService.class);
    }


    @SuppressWarnings("unchecked")
    public GameService<?> dispatchGameService(HttpServletRequest request,String gameName,String userId){
        Map<String, String> params = new ConcurrentHashMap<>();
        for (Iterator<String> it = request.getParameterNames().asIterator(); it.hasNext(); ) {
            String key = it.next();
            String value = request.getParameter(key);
            params.put(key, value);
        }
        params.put("userId", userId);
        GameService<?> service = getGameService(gameName);
        service.setParams(params);
        return service;
    }

    //查询他人的游戏列表
    @GetMapping("/gameList/{userID}")
    public Result selectOnesGameList(@NonNull @PathVariable String userID){
        List<GameInfo> gameList=userGameService.selectPersonalGamesPublicOnly(Long.valueOf(userID));
        if(gameList.size()<1)throw new BusinessException(Code.SELECT_ERR,"TA好像不玩游戏耶");
        return new Result(Code.SELECT_OK,"TA在玩"+gameList.size()+"款游戏",gameList);
    }

    //查询自己的游戏列表
    @GetMapping("/gameList")
    public Result selectMyGameList(@NonNull @RequestAttribute String userId){
        List<GameInfo> gameList=userGameService.selectPersonalGames(Long.valueOf(userId));
        if(gameList.size()<1)throw new BusinessException(Code.SELECT_ERR,"您尚未添加任何一款游戏");
        return new Result(Code.SELECT_OK,"在玩"+gameList.size()+"款游戏",gameList);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/gameName")
    public Result addPlayer(HttpServletRequest request, @NonNull  @RequestParam String gameName,
                            @NonNull @RequestAttribute String userId) throws Exception {
        GameService<?> service=dispatchGameService(request,gameName,userId);
        Long gameId=gameInfoService.selectGameIdByName(gameName);
        int rowsAffected = service.addPlayer();
        int rows= userGameService.addGame(new UserGame(Long.valueOf(userId),gameId));
        if (rowsAffected > 0&&rows>0) {
            return new Result(Code.INSERT_OK,"添加成功");
        } else throw new BusinessException(Code.INSERT_ERR, "添加失败，请稍后再试");

    }

    //按照要求筛选玩游戏名为gameName的玩家
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/gameName")
    public Result selectPlayer(HttpServletRequest request, @RequestParam String gameName,
                               @NonNull @RequestAttribute String userId, @RequestParam Long current){
        GameService<?> service=dispatchGameService(request,gameName,userId);
        List<?> data = service.selectPlayer(current);
        if(data.size()<1) throw new BusinessException(Code.SELECT_ERR,"未找到符合条件的玩家");
        else return new Result(Code.SELECT_OK,null,data);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/gameName")
    public Result deletePlayer(HttpServletRequest request, @NonNull  @RequestParam String gameName,
                               @NonNull @RequestAttribute String userId){
        GameService<?> service=dispatchGameService(request,gameName,userId);
        Long gameId=gameInfoService.selectGameIdByName(gameName);
        int rowsAffected = service.deletePlayer();
        int rows= userGameService.deleteGame(new UserGame(Long.valueOf(userId),gameId));
        if (rowsAffected > 0&&rows>0) return new Result(Code.DELETE_OK,"删除成功");
        else throw new BusinessException(Code.DELETE_ERR, "删除失败，请稍后再试");
    }

    @PutMapping("/gameName")
    public Result updatePlayer(HttpServletRequest request, @NonNull @RequestParam String gameName,
                               @NonNull @RequestAttribute String userId) throws Exception {
        GameService<?> service=dispatchGameService(request,gameName,userId);
        if(service.updatePlayer()>0) return new Result(Code.UPDATE_OK,"更新成功");
        else throw new BusinessException(Code.UPDATE_ERR,"更新失败，请稍后再试");
    }

    //--------------------------------------------gameId API------------------------------------------------------------

    //@PostMapping("/gameId/{gameId}")
    public Result addPlayer2(HttpServletRequest request, @NonNull  @PathVariable("gameId") String gameId,
                            @NonNull @RequestAttribute String userId) throws Exception {
        String gameName=gameInfoService.selectGameNameById(Long.valueOf(gameId));
        return addPlayer(request,gameName,userId);
    }

    //@GetMapping("/gameId")
    public Result selectPlayer2(HttpServletRequest request, @NonNull  @RequestParam String gameId,
                               @NonNull @RequestAttribute String userId,@RequestParam Long current){
        String gameName=gameInfoService.selectGameNameById(Long.valueOf(gameId));
        return selectPlayer(request,gameName,userId,current);
    }

    //@DeleteMapping("/gameId/{gameId}")
    public Result deletePlayer2(HttpServletRequest request, @NonNull  @PathVariable("gameId") String gameId,
                               @NonNull @RequestAttribute String userId){
        String gameName=gameInfoService.selectGameNameById(Long.valueOf(gameId));
        return deletePlayer(request,gameName,userId);
    }

    //@PutMapping("/gameId/{gameId}")
    public Result updatePlayer2(HttpServletRequest request, @NonNull @PathVariable("gameId") String gameId,
                               @NonNull @RequestAttribute String userId) throws Exception {
        String gameName=gameInfoService.selectGameNameById(Long.valueOf(gameId));
        return updatePlayer(request,gameName,userId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}