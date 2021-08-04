package com.ydw.user.controller;


import com.ydw.user.service.ITbGameGroupService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

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
    public ResultInfo getGameGroupAppList(HttpServletRequest request,@RequestBody  String body) {
        return iTbGameGroupService.getGameGroupAppList(request,body);
    }

    @PostMapping(value = "/getHotGameList")
    @ResponseBody
    public ResultInfo getHotGameList(HttpServletRequest request,@RequestBody  String body) {
        return iTbGameGroupService.getHotGameList(request,body);
    }

    @GetMapping(value = "/getNewGameList")
    @ResponseBody
    public ResultInfo getNewGameList(HttpServletRequest request) {
        return iTbGameGroupService.getNewGameList(request);
    }

    @PostMapping(value="/searchApp")
    @ResponseBody
    public ResultInfo searchApp(HttpServletRequest request,@RequestBody String body){
        return  iTbGameGroupService.searchApp(request,body);
    }
}

