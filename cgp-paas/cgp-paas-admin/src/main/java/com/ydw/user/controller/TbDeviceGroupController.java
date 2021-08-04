package com.ydw.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbDeviceGroup;
import com.ydw.user.service.ITbDeviceGroupService;
import com.ydw.user.utils.ResultInfo;
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
 * @since 2020-05-13
 */
@Controller
@RequestMapping("/v1/deviceGroup")
public class TbDeviceGroupController  extends BaseController{
    @Autowired
    private ITbDeviceGroupService iTbDeviceGroupService;


    @RequestMapping(value = "/createDeviceGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createCluster(HttpServletRequest request, @RequestBody TbDeviceGroup group) {

        return iTbDeviceGroupService.createDeviceGroup(request, group);
    }

    /**
     * 编辑
     * @param request
     * @param group
     * @return
     */
    @RequestMapping(value = "/updateDeviceGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateCluster(HttpServletRequest request, @RequestBody TbDeviceGroup group) {

        return iTbDeviceGroupService.updateDeviceGroup(request, group);
    }

    /**
     * 删除设备组
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteDeviceGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteCluster(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbDeviceGroupService.deleteDeviceGroup(request, ids);
    }

    /**
     * 获取设备组列表
     * @param request
     * @param name
     * @param description
     * @param schStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getDeviceGroupList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getDeviceGroupList(HttpServletRequest request, @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) Integer schStatus,
                                     @RequestParam(required = false) String search,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize) {
        return iTbDeviceGroupService.getDeviceGroupList(request, name, description, schStatus,search, buildPage());
    }

    /**
     * 设备组组管理 添加设备
     */
    @PostMapping(value = "/addDevices")
    @ResponseBody
    public ResultInfo addDevices(HttpServletRequest request,  @RequestBody String body){

        return  iTbDeviceGroupService.addDevices(request,body);

    }

    /**
     * 设备组组管理 移除设备
     */
    @PostMapping(value = "/removeDevices")
    @ResponseBody
    public ResultInfo removeDevices(HttpServletRequest request,  @RequestBody String body){

        return  iTbDeviceGroupService.removeDevices(request,body);

    }
}

