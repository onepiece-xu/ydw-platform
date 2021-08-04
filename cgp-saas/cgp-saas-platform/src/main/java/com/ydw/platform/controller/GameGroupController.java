package com.ydw.platform.controller;


import com.ydw.platform.service.IGameGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ydw.platform.model.vo.ResultInfo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-07-14
 */

@RestController
@RequestMapping("/gameGroup")
public class GameGroupController extends BaseController{
    @Autowired
    private IGameGroupService iTbGameGroupService;

    /**
     * 根据游戏组id获取游戏列表展示
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/getGameGroupAppList")
    @ResponseBody
    public ResultInfo getGameGroupAppList(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.getGameGroupAppList(request,body);
    }

    /**
     * 热门榜单
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/getHotGameList")
    @ResponseBody
    public ResultInfo getHotGameList(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.getHotGameList(request,body);
    }
    /**
     * 热门榜单
     * @param request
     * @param
     * @return
     */
    @GetMapping(value = "/new/getHotGameApps")
    @ResponseBody
    public ResultInfo getHotGameApps(HttpServletRequest request) {
        return iTbGameGroupService.getHotGameApps(request);
    }


    /**
     * 热门榜单 安卓
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/getHotGameListAndroid")
    @ResponseBody
    public ResultInfo getHotGameListAndroid(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.getHotGameListAndroid(request,body);
    }


    /**
     * 新游列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getNewGameListAndroid")
    @ResponseBody
    public ResultInfo getNewGameListAndroid(HttpServletRequest request) {
        return iTbGameGroupService.getNewGameListAndroid(request);
    }

    /**
     * 新游列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getNewGameList")
    @ResponseBody
    public ResultInfo getNewGameList(HttpServletRequest request) {
        return iTbGameGroupService.getNewGameList(request);
    }

    /**
     * 新游列表
     * @param request
     * @return
     */
    @GetMapping(value = "/new/getNewApps")
    @ResponseBody
    public ResultInfo getNewApps(HttpServletRequest request) {
        return iTbGameGroupService.getNewApps(request);
    }

    /**未使用
     *
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/searchApp")
    @ResponseBody
    public ResultInfo searchApp(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.searchApp(request,body);
    }

    /**
     * 添加游戏到游戏组
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/addApps")
    @ResponseBody
    public ResultInfo addApps(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.addApps(request,body);
    }


    /**
     * 編輯游戏到游戏组
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/updateApps")
    @ResponseBody
    public ResultInfo updateApps(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.updateApps(request,body);
    }

    /**
     * 編輯游戏到游戏组
     * @param request
     * @param
     * @return
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResultInfo deleteApps(HttpServletRequest request, @RequestBody List<Integer> ids) {
        return iTbGameGroupService.deleteApps(request,ids);
    }


    /**
     * 获取游戏组下面的游戏列表
     */

    @GetMapping(value = "/getGroupAppList")
    @ResponseBody
    public ResultInfo getGroupAppList(HttpServletRequest request, @RequestParam Integer id, @RequestParam(required = false)Integer pageSize , @RequestParam(required = false)Integer pageNum) {
        return iTbGameGroupService.getGroupAppList(request,id,buildPage());
    }

    /**
     * 游戲列表
     */
    @GetMapping(value = "/getGameList")
    @ResponseBody
    public ResultInfo getGameList(HttpServletRequest request) {
        return iTbGameGroupService.getGameList(request);
    }

    /**获取首页下方所有列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getGameGroupLists")
    public ResultInfo getGameGroupLists(HttpServletRequest request) {
        return iTbGameGroupService.getGameGroupLists(request);
    }

    /**获取首页下方所有列表
     * @param request
     * @return
     */
    @GetMapping(value = "/new/getGameGroupApps")
    public ResultInfo getGameGroupApps(HttpServletRequest request) {
        return iTbGameGroupService.getGameGroupApps(request);
    }

    /**
     * 获取推荐游戏
     */

    @GetMapping("/getRecommendList")
    public ResultInfo getRecommendList(HttpServletRequest request){
        return iTbGameGroupService.getRecommendList(request);
    }

    /**
     * 获取推荐游戏
     */

    @GetMapping("/new/getRecommendApps")
    public ResultInfo getRecommendApps(HttpServletRequest request){
        return iTbGameGroupService.getRecommendApps(request);
    }


    /**
     * 获取榜单3
     */

    @GetMapping("/getTopGames")
    public ResultInfo getTopGames(HttpServletRequest request){
        return iTbGameGroupService.getTopGames(request);
    }

    /**
     * 获取榜单3
     */

    @GetMapping("/new/getTopApps")
    public ResultInfo getTopApps(HttpServletRequest request){
        return iTbGameGroupService.getTopApps(request);
    }

    /**
     * 根据id获取组
     */

    @GetMapping("/getGroupById")
    public ResultInfo getGroupById(HttpServletRequest request,@RequestParam Integer id){
        return iTbGameGroupService.getGroupById(request,id);
    }

}

