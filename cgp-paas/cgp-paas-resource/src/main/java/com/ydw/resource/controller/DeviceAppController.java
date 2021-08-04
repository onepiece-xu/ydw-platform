package com.ydw.resource.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ydw.resource.model.vo.DeviceAppOperateResultVO;
import com.ydw.resource.service.IDeviceAppsService;
import com.ydw.resource.utils.ResultInfo;

@RestController
@RequestMapping("/deviceApp")
public class DeviceAppController {
	
	@Autowired
    private IDeviceAppsService appService ;

	///安装应用
    @PostMapping(value = "/install/{appId}")
    public ResultInfo install(@PathVariable String appId,@RequestBody List<String> deviceIds) {
        return appService.installToDevices(appId, deviceIds);
    }

    ///卸载应用
    @PostMapping(value = "/uninstall/{appId}")
    public ResultInfo uninstall(@PathVariable String appId,@RequestBody List<String> deviceIds) {
        return appService.unInstallFromDevices(appId, deviceIds);
    }
    
    @PostMapping(value = "/reportDeviceAppResult")
    public ResultInfo reportDeviceAppResult(@RequestBody DeviceAppOperateResultVO vo){
    	return appService.reportDeviceAppResult(vo);
    }

    @PostMapping(value = "/startApp")
    public ResultInfo startApp(@RequestBody HashMap<String,Object> param){
        List<String> deviceIds = (List<String>)param.get("deviceIds");
        String appId = (String)param.get("appId");
        return appService.startApp(deviceIds, appId);
    }

    @PostMapping(value = "/stopApp")
    public ResultInfo stopApp(@RequestBody HashMap<String,Object> param){
        List<String> deviceIds = (List<String>)param.get("deviceIds");
        String appId = (String)param.get("appId");
        return appService.stopApp(deviceIds, appId);
    }
}
