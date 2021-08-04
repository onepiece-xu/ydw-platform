package com.ydw.charge.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-platform", path = "/cgp-saas-platform")
public interface IYdwPlatformService {

	//获取连接信息
	@GetMapping(value = "/connect/getConnectInfo")
	String getConnectInfo(@RequestParam(value = "connectId") String connectId);

	//获取连接信息
	@PostMapping(value = "/resource/release")
	String release(@RequestBody HashMap<String,Object> connectVO);
}
