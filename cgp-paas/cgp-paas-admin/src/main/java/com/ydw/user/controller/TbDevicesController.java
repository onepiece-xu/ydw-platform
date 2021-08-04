package com.ydw.user.controller;


import com.ydw.user.model.db.TbDevices;
import com.ydw.user.service.ITbDevicesService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
@Controller
@RequestMapping("/v1/devices")
public class TbDevicesController  extends BaseController{
    @Autowired
    private ITbDevicesService iTbDevicesService;

    @RequestMapping(value = "/createDevice", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createDevice(HttpServletRequest request, @RequestBody TbDevices device) {

        return iTbDevicesService.createDevice(request, device);
    }

    @RequestMapping(value = "/updateDevice", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateDevice(HttpServletRequest request, @RequestBody TbDevices device) {
        return iTbDevicesService.updateDevice(request, device);
    }

    @RequestMapping(value = "/deleteDevices", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteDevice(HttpServletRequest request, @RequestBody List<String> ids) {
        return iTbDevicesService.deleteDevices(request, ids);
    }

    /**
     * 获取集群下设备
     * @param request
     * @param name
     * @param baseId
     * @param clusterId   必选
     * @param groupId
     * @param idc
     * @param cabinet
     * @param location
     * @param slot
     * @param managerIp
     * @param managerPort
     * @param innerIp
     * @param innerPort
     * @param ip
     * @param port
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getDevices", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getDevices(HttpServletRequest request,
                                   @RequestParam(required = false) String id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) Integer baseType,
                                   @RequestParam(required = false) String clusterId,
                                   @RequestParam(required = false)Integer groupId,
                                   @RequestParam(required = false) String idc,
                                   @RequestParam(required = false) String cabinet,
                                   @RequestParam(required = false) String location,
                                   @RequestParam(required = false) String slot,
                                   @RequestParam(required = false) String managerIp,
                                   @RequestParam(required = false) Integer managerPort,
                                   @RequestParam(required = false) String innerIp,
                                   @RequestParam(required = false) Integer innerPort,
                                   @RequestParam(required = false) String ip,
                                   @RequestParam(required = false) Integer port,
                                   @RequestParam(required = false) String status,
                                   @RequestParam(required = false) String schStatus,
                                   @RequestParam(required = false) String search,
                                   @RequestParam(required = false) Integer pageNum,
                                   @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getDevices(request,id, name, baseType, clusterId, groupId,idc, cabinet, location, slot
                , managerIp, managerPort, innerIp, innerPort, ip, port, status,schStatus,search,  buildPage());
    }

    /**
     * 设备详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDeviceById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getDevice(HttpServletRequest request, @PathVariable String id) {
        return iTbDevicesService.getDevice(request, id);
    }

    /**
     * 导入设备Excel
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/fileUpload", produces = "application/json; charset=utf-8")
    public ResultInfo  UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return iTbDevicesService.ajaxUploadExcel(request, response);
    }


    /**
     * 获取设备规格
     * @param request
     * @return
     */
    @GetMapping(value = "/getDevicesBases")
    @ResponseBody
    public ResultInfo getDeviceBases(HttpServletRequest request) {
        return iTbDevicesService.getDeviceBases(request);
    }


    /**
     *获取可以安装设备
     * @param request
     * @param appId
     * @param clusterId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getCanInstallDevicesByAppId/{appId}")
    @ResponseBody
    public ResultInfo getInstallDevicesByAppId(HttpServletRequest request,@PathVariable String appId,
                                        @RequestParam(required = false) String clusterId,
                                        @RequestParam(required = false) Integer groupId,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getInstallDevicesByAppId(request,appId,clusterId,groupId,buildPage());
    }

    @GetMapping(value = "/getCanUninstallDevicesByAppId/{appId}")
    @ResponseBody
    public ResultInfo getUninstallDevicesByAppId(HttpServletRequest request,@PathVariable String appId,
                                        @RequestParam(required = false) String clusterId,
                                        @RequestParam(required = false) Integer groupId,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getUninstallDevicesByAppId(request, appId, clusterId, groupId, buildPage());
    }

    //设备管理列表查询
    @RequestMapping(value = "/getDevicesList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getDevicesList(HttpServletRequest request,
                                 @RequestParam(required = false) String id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String innerMac,
                                 @RequestParam(required = false) String clusterName,
                                 @RequestParam(required = false) String idc,
                                 @RequestParam(required = false) String cabinet,
                                 @RequestParam(required = false) String location,
                                 @RequestParam(required = false) String slot,
                                 @RequestParam(required = false) String groupName,
                                 @RequestParam(required = false) String managerIp,
                                 @RequestParam(required = false) Integer managerPort,
                                 @RequestParam(required = false) String innerIp,
                                 @RequestParam(required = false) Integer innerPort,
                                 @RequestParam(required = false) String ip,
                                 @RequestParam(required = false) Integer port,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) Integer schStatus,
                                 @RequestParam(required = false) Integer baseType,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer pageNum,
                                 @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getDevicesList(request,id, name, innerMac, clusterName, idc, cabinet, location, slot, groupName
                , managerIp, managerPort, innerIp, innerPort, ip, port, status, description,schStatus,baseType,search,buildPage());
    }

    /**
     * 查询应用已安装设备列表
     */
    @GetMapping(value = "/getDevicesByInstallAppId/{appId}")
    @ResponseBody
    public ResultInfo getDevicesByInstallAppId(HttpServletRequest request,@PathVariable String appId,
                                                 @RequestParam(required = false) String clusterId,
                                                 @RequestParam(required = false) Integer groupId,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getDevicesByInstallAppId(request, appId, clusterId, groupId, buildPage());
    }

    /**
     * 查询可添加设备列表
     * @param request

     * @param clusterId
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getAddDevices")
    @ResponseBody
    public ResultInfo getAddDevices(HttpServletRequest request,
                                               @RequestParam(required = false) String clusterId,
                                               @RequestParam(required = false) Integer groupId,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getAddDevices(request, clusterId, groupId,buildPage());
    }

    /**
     * 获取设备组下的设备
     */
    @GetMapping(value = "/getGroupDevices")
    @ResponseBody
    public ResultInfo getGroupDevices(HttpServletRequest request,
                                      @RequestParam(required = false) String clusterId,
                                      @RequestParam(required = false) Integer groupId,
                                      @RequestParam(required = false) String id,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String innerMac,
                                      @RequestParam(required = false) String clusterName,
                                      @RequestParam(required = false) String idc,
                                      @RequestParam(required = false) String cabinet,
                                      @RequestParam(required = false) String location,
                                      @RequestParam(required = false) String slot,
                                      @RequestParam(required = false) String managerIp,
                                      @RequestParam(required = false) Integer managerPort,
                                      @RequestParam(required = false) String innerIp,
                                      @RequestParam(required = false) Integer innerPort,
                                      @RequestParam(required = false) String ip,
                                      @RequestParam(required = false) Integer port,
                                      @RequestParam(required = false) String status,
                                      @RequestParam(required = false) String description,
                                      @RequestParam(required = false) Integer schStatus,
                                      @RequestParam(required = false) Integer baseType,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize) {
        return iTbDevicesService.getGroupDevices(request,clusterId,groupId,id,name, innerMac,baseType, clusterName,idc, cabinet, location, slot
                , managerIp, managerPort, innerIp, innerPort, ip, port, status,description,schStatus,search, buildPage());
    }

}



