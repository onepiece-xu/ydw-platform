package com.ydw.platform.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "cgp-saas-schedulejob", path = "/cgp-saas-schedulejob")
public interface IYdwSchedulejobService {

	//添加定时任务
	@PostMapping(value = "/scheduleJob/addScheduleJob")
	String addScheduleJob(@RequestBody Map<String, Object> scheduleJob);

	//删除定时任务
	@DeleteMapping(value = "/scheduleJob/deleteScheduleJobByNameAndGroup")
	String deleteScheduleJob(@RequestParam(value = "scheduleJobName") String scheduleJobName,
							 @RequestParam(value = "scheduleJobGroup") String scheduleJobGroup);

    //删除定时任务
    @GetMapping(value = "/scheduleJob/getScheduleJobByNameAndGroup")
    JSONObject getScheduleJob(@RequestParam(value = "scheduleJobName") String scheduleJobName,
                              @RequestParam(value = "scheduleJobGroup") String scheduleJobGroup);

}
