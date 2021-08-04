package com.ydw.charge.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cgp-saas-schedulejob", path = "/cgp-saas-schedulejob")
public interface IYdwSchedulejobService {

	//获取连接信息
	@PostMapping(value = "/scheduleJob/addScheduleJob")
	String addScheduleJob(@RequestBody Map<String,Object> scheduleJob);

	@DeleteMapping(value = "/scheduleJob/deleteScheduleJobByName")
	String deleteScheduleJob(@RequestParam(value = "scheduleJobName") String scheduleJobName);

}
