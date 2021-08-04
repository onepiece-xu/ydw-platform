package com.ydw.open.controller;


import com.ydw.open.service.ITbUserAppsRelatedService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-08-06
 */
@Controller
@RequestMapping("/userAppsRelated")
public class TbUserAppsRelatedController extends BaseController{
    @Autowired
    private ITbUserAppsRelatedService iTbUserAppsRelatedService;
    /**
     获取游戏列表
     */
    @GetMapping(value = "/getAppList")
    @ResponseBody
    public ResultInfo getAppList(@RequestParam( required=false ) String id,
                                 @RequestParam( required=false ) Integer pageSize,
                                 @RequestParam( required=false ) Integer pageNum){
        return  iTbUserAppsRelatedService.getAppList(id,buildPage());
    }
    /**
     * 申请游戏
     */
    @PostMapping(value = "/approve")
    @ResponseBody
    public ResultInfo addApps(@RequestBody String body){
        return  iTbUserAppsRelatedService.addApps(body);
    }

    /**
     * 获取用户提交的游戏申请
     *
     */
    @GetMapping(value = "/getAppApproveList")
    @ResponseBody
    public ResultInfo  getAppApproveList(@RequestParam( required=false ) String body, @RequestParam( required=false )String search){
        return  iTbUserAppsRelatedService.getAppApproveList(body,search,buildPage());
    }

    /**
     * 获取用户游戏列表(已審批通過列表)
     *
     */
    @GetMapping(value = "/getAppApproved")
    @ResponseBody
    public ResultInfo  getAppApproved(@RequestParam( required=false ) String id,@RequestParam( required=false ) String search){
        return  iTbUserAppsRelatedService.getAppApproved(id,search,buildPage());
    }


    /**
     * 审批用户提交的游戏申请
     *
     */
    @PostMapping(value = "/appApprove")
    @ResponseBody
    public ResultInfo  appApprove(HttpServletRequest request, @RequestBody( required=false ) String id){
        return  iTbUserAppsRelatedService.appApprove(request,id);
    }
    /**
     *
     */
    /**
     * 获取用户自己的游戏申请列表
     */
    @GetMapping(value = "/getAppApproves")
    @ResponseBody
    public ResultInfo  getAppApproveList(@RequestParam( required=false ) String body, @RequestParam( required=false ) Integer pageSize,
                                         @RequestParam( required=false ) Integer pageNum, @RequestParam( required=false ) String id){

        return  iTbUserAppsRelatedService.getAppApproves(body,buildPage(),id);
    }

    /**
     * 根据企业用户获取
     */
    @PostMapping("/getOwnerAppList")
    @ResponseBody
    public  ResultInfo getOwnerAppList(@RequestBody String body){
        return  iTbUserAppsRelatedService.getOwnerAppList(body);
    }

    /**
     * 用戶游戲列表 (已经审批通过的)
     */
    @GetMapping(value = "/getGameListApproved")
    @ResponseBody
    public ResultInfo  getGameListApproved(@RequestParam( required=false ) String id,
                                           @RequestParam( required=false ) String search){

        return  iTbUserAppsRelatedService.getGameListApproved(id,search,buildPage());
    }

    /**
     * 取消审批功能
     * @param body
     * @return
     */
    @PostMapping(value = "/cancelApproved")
    @ResponseBody
    public ResultInfo  cancelApproved(HttpServletRequest request,@RequestBody( required=false ) List<Integer> body){

        return  iTbUserAppsRelatedService.cancelApproved(request,body);
    }
}

