package com.ydw.admin.controller;


import com.ydw.admin.model.db.GameGroupInfo;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IGameGroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/gameGroupInfo")
public class GameGroupInfoController extends BaseController {

    @Autowired
    private IGameGroupInfoService iTbGamegroupInfoService;

    /**
     * 获取游戏组列表
     */
    @GetMapping(value = "/getGameGroupList")
    @ResponseBody
    public ResultInfo getGameGroupList(HttpServletRequest request,
                                       @RequestParam(required = false)Integer pageSize , @RequestParam(required = false)Integer pageNum) {
        return iTbGamegroupInfoService.getGameGroupList(request,buildPage());
    }

    /**
     * 添加游戏组
     */
    @PostMapping(value = "/addGameGroup")
    @ResponseBody
    public ResultInfo addGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {
        return iTbGamegroupInfoService.addGameGroup(request,tbGamegroupInfo);
    }

    /**
     * 添加游戏组
     */
    @PostMapping(value = "/updateGameGroup")
    @ResponseBody
    public ResultInfo updateGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {
        return iTbGamegroupInfoService.updateGameGroup(request,tbGamegroupInfo);
    }

    /**
     * 删除游戏组
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResultInfo deleteGameGroup(HttpServletRequest request, @RequestBody List<Integer> ids) {
        return iTbGamegroupInfoService.deleteGameGroup(request,ids);
    }
}

