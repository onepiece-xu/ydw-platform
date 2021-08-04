package com.ydw.open.controller;


import com.ydw.open.service.ITbGameGroupService;
import com.ydw.open.utils.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@Controller
@RequestMapping("/gameGroup")
public class TbGameGroupController  extends BaseController{
    @Autowired
    private ITbGameGroupService iTbGameGroupService;

    @PostMapping(value = "/getGameGroupAppList")
    @ResponseBody
    public ResultInfo getGameGroupAppList(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.getGameGroupAppList(request,body);
    }

    @PostMapping(value = "/getHotGameList")
    @ResponseBody
    public ResultInfo getHotGameList(HttpServletRequest request, @RequestBody String body) {
        return iTbGameGroupService.getHotGameList(request,body);
    }

    @GetMapping(value = "/getNewGameList")
    @ResponseBody
    public ResultInfo getNewGameList(HttpServletRequest request) {
        return iTbGameGroupService.getNewGameList(request);
    }

    /**
     * 未用
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value="/searchApp")
    @ResponseBody
    public ResultInfo searchApp(HttpServletRequest request, @RequestBody String body){
        return  iTbGameGroupService.searchApp(request,body);
    }

    /**
     * 添加游戏到游戏组
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/addApps")
    @ResponseBody
    public ResultInfo addApps(HttpServletRequest request,@RequestBody String body) {
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
    public ResultInfo updateApps(HttpServletRequest request,@RequestBody String body) {
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
    public ResultInfo deleteApps(HttpServletRequest request,@RequestBody List<Integer> ids) {
        return iTbGameGroupService.deleteApps(request,ids);
    }


    /**
     * 获取游戏组下面的游戏列表
     */

    @GetMapping(value = "/getGroupAppList")
    @ResponseBody
    public ResultInfo getGroupAppList(HttpServletRequest request,@RequestParam Integer id, @RequestParam(required = false)Integer pageSize , @RequestParam (required = false)Integer pageNum) {
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
}

