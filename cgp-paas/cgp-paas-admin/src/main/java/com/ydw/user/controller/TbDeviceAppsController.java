package com.ydw.user.controller;


import com.ydw.user.service.ITbDeviceAppsService;
import com.ydw.user.utils.ResultInfo;
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
 * @since 2020-04-14
 */
@Controller
@RequestMapping("/v1/deviceApps")
public class TbDeviceAppsController extends BaseController {
    @Autowired
    private ITbDeviceAppsService iTbDeviceAppsService;

    //设备安装应用
    @RequestMapping(value = "/createDeviceApp/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo installApp(HttpServletRequest request,@PathVariable String deviceId,
                                @RequestBody List<String> appIds) {

        return iTbDeviceAppsService.createApp(request, deviceId,appIds);
    }
    //设备卸载应用
    @RequestMapping(value = "/UninstallApp/{deviceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResultInfo UninstallApp(HttpServletRequest request, @PathVariable String deviceId,
                                   @RequestBody List<String> appIds) {

        return iTbDeviceAppsService.updateApp(request, deviceId,appIds);
    }
    @RequestMapping(value = "/deleteApp", method = RequestMethod.PUT)
    @ResponseBody
    public ResultInfo deleteApp(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbDeviceAppsService.deleteApp(request, ids);
    }

    @RequestMapping(value = "/appList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getAppList(HttpServletRequest request,
                                 @RequestParam(required = false)  String deviceId,
                                 @RequestParam(required = false) Integer appId,
                                 @RequestParam(required = false) Integer status,
                                 @RequestParam(required = false) Integer pageNum,
                                 @RequestParam(required = false) Integer pageSize) {
        return iTbDeviceAppsService.getAppList(request,deviceId,appId,status,buildPage());
    }

    //应用批量安装到设备
    @RequestMapping(value = "/InstallToDevices/{appId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo InstallToDevices(HttpServletRequest request,@PathVariable String appId,
                                 @RequestBody List<String> deviceIds) {

        return iTbDeviceAppsService.InstallToDevices(request, appId,deviceIds);
    }

    //应用批量卸载到设备
    @RequestMapping(value = "/UninstallToDevices/{appId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo UninstallToDevices(HttpServletRequest request,@PathVariable String appId,
                                       @RequestBody List<String> deviceIds) {

        return iTbDeviceAppsService.UninstallToDevices(request, appId,deviceIds);
    }
}

