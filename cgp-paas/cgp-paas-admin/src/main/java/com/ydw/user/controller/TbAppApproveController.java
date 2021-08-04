package com.ydw.user.controller;


import com.ydw.user.model.db.TbAppApprove;
import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.service.ITbAppApproveService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
@Controller
@RequestMapping("/v1/appApprove")
public class TbAppApproveController  extends BaseController{

    @Autowired
    private ITbAppApproveService iTbAppApproveService;


    /**
     *应用审批列表
     */
    @RequestMapping(value = "/getAppApproveList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getList(HttpServletRequest request,
                              @RequestParam(required = false) Integer status,
                              @RequestParam(required = false) Integer  cooperationType,
                              @RequestParam(required = false) String  search,
                              @RequestParam(required = false) Integer pageNum,
                              @RequestParam(required = false) Integer pageSize) {

        return iTbAppApproveService.getList(request,status,cooperationType,search,buildPage());
    }
    /**
     *应用审批详情
     */
    @RequestMapping(value = "/getAppApprove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getAppApprove(HttpServletRequest request, @PathVariable String id) {

        return iTbAppApproveService.getAppApprove(request,id);
    }

    /**
     *  应用审批
     */
    @RequestMapping(value = "/approveApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo approveApp(HttpServletRequest request, @RequestBody TbAppApprove appApprove) {

        return iTbAppApproveService.approveApp(request,appApprove);
    }

    /**
     * 获取应用游戏类型
     */

    @RequestMapping(value = "/getGameType", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getGameType(HttpServletRequest request){
        return iTbAppApproveService.getGameType(request);
    }

    /**
     * 应用上架
     */
    @RequestMapping(value = "/launchApps", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo launchApps(HttpServletRequest request, @RequestBody TbUserApps tbUserApps) {

        return iTbAppApproveService.launchApps(request,tbUserApps);
    }

}

