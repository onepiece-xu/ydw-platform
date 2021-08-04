package com.ydw.schedule.controller;

import com.ydw.schedule.model.vo.ResultInfo;
import com.ydw.schedule.service.IDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDeviceService deviceServiceImpl;

	/**
	 * 上报设备状态
	 * @param deviceId
	 * @param status
	 * @return
	 */
	@GetMapping(value = "/reportStatus")
	public ResultInfo reportStatus(@RequestParam String deviceId,
								   @RequestParam Integer status){
		return deviceServiceImpl.reportStatus(deviceId, status);
	}
	
	/**
	 * 安装成功app后上报
	 * @param deviceId
	 * @param appId
	 * @return
	 */
	@GetMapping(value = "/installedApp")
	public ResultInfo installedApp(@RequestParam String deviceId, @RequestParam String appId){
		return deviceServiceImpl.installedApp(deviceId, appId);
	}
	
	/**
	 * 安装成功app后上报
	 * @param deviceId
	 * @param appId
	 * @return
	 */
	@GetMapping(value = "/uninstalledApp")
	public ResultInfo uninstalledApp(@RequestParam String deviceId, @RequestParam String appId){
		return deviceServiceImpl.uninstalledApp(deviceId, appId);
	}
}