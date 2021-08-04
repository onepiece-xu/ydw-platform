package com.ydw.resource.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ydw.resource.utils.ResultInfo;

@FeignClient(value = "cgp-paas-schedule", path = "/cgp-paas-schedule")
public interface IYdwScheduleService {

	//上报状态
	@GetMapping(value = "/device/reportStatus")
	ResultInfo reportStatus(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "status") int status);
	
	//申请连接
	@PostMapping(value = "/connect/applyConnect", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResultInfo applyConnect(@RequestBody Map<String,Object> map);
	
	//释放连接
	@PostMapping(value = "/connect/releaseConnect")
	ResultInfo releaseConnect(@RequestBody Map<String,Object> map);
	
	//安装app后上报
	@GetMapping(value = "/device/installedApp")
	ResultInfo installedApp(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "appId") String appId);
	
	//卸载app后上报
	@GetMapping(value = "/device/uninstalledApp")
	ResultInfo uninstalledApp(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "appId") String appId);
	
	//获取用户连接状态
	@GetMapping(value = "/connect/getUserConnectStatus")
	ResultInfo getUserConnectStatus(@RequestParam(value = "account") String account);
	
	//根据设备获取用户连接
	@GetMapping(value = "/connect/getConnectByDevice")
	ResultInfo getConnectByDevice(@RequestParam(value = "deviceId") String deviceId);

	//获取用户排名
	@GetMapping(value = "/queue/getUserRank")
	ResultInfo getUserRank(@RequestParam(value = "account") String account);

	//用户退出排队
	@GetMapping(value = "/queue/queueOut")
	ResultInfo queueOut(@RequestParam(value = "account") String account);
}
