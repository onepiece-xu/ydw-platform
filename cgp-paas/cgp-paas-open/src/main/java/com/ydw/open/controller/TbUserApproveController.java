package com.ydw.open.controller;


import com.ydw.open.model.db.TbUserApprove;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */

@Controller
@RequestMapping("/v1/userApprove")
public class TbUserApproveController  extends BaseController{
    @Autowired
    private com.ydw.open.service.ITbUserApproveService ITbUserApproveService;


    /**
     * 登录
     * @param request
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response, @RequestBody TbUserApprove user) {

        return ITbUserApproveService.login(request,response, user);
    }



    /**
     * 注册
     * @param request
     * @param response
     * @param file
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo register(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file,
                                TbUserApprove user) {

        return ITbUserApproveService.register(request,response,file, user);
    }

    /**
     * 获取服务类型
     */

    @GetMapping(value = "/getServices")
    @ResponseBody
    public ResultInfo getServices(HttpServletRequest request) {

        return ITbUserApproveService.getServices(request);
    }

    /**
     *更新审批用户信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateUserApprove(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file,TbUserApprove user) {

        return ITbUserApproveService.updateUserApprove(request,response,file, user);
    }


    /**
     * 用户审批
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo approve(HttpServletRequest request, @RequestBody TbUserApprove approve) {
        return ITbUserApproveService.approve(request, approve);
    }

    /**
     * 审批用户列表
     *
     */
    @GetMapping(value = "/getUserApproveList")
    @ResponseBody
    public ResultInfo getUserApproveList(HttpServletRequest request,
                                         @RequestParam(required = false) String search,
                                         @RequestParam(required = false) String schStatus,
                                         @RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String enterpriseName) {

        return ITbUserApproveService.getUserApproveList(request,search,schStatus,buildPage(),enterpriseName);

    }

    /**
     * 用户详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUserInfo(HttpServletRequest request,@PathVariable String id){
        return ITbUserApproveService.getUserInfo(request,id);
    }

}

