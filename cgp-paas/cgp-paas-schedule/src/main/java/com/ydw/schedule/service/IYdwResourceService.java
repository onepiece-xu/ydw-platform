package com.ydw.schedule.service;

import com.ydw.schedule.model.vo.ConnectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "cgp-paas-resource", path = "/cgp-paas-resource")
public interface IYdwResourceService {

	@PostMapping(value = "/deviceApp/install/{appId}")
    String installAppToDevice(@PathVariable("appId")String appId, @RequestBody List<String> deviceIds);
	
	@PostMapping(value = "/deviceApp/uninstall/{appId}")
    String uninstallAppFromDevice(@PathVariable("appId")String appId, @RequestBody List<String> deviceIds);

	@PostMapping(value = "/connect/noticeResourceApplyed")
	String noticeResourceApplyed(@RequestBody ConnectVO vo);
}
