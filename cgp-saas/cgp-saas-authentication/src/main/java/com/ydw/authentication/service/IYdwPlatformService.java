package com.ydw.authentication.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-platform", path = "/cgp-saas-platform")
public interface IYdwPlatformService {

	/**
     * 输入参数：
	 *  registrationId
	 *  token
	 * @param map
	 * @return
	 */
	@PostMapping(value = "/distributionAward/pullNewAward")
	String pullNewAward(@RequestBody HashMap<String, String> map);

	@GetMapping(value = "/distributionAward/registerAward")
	String registerAward(@RequestParam("userId") String userId);

	/**
	 * 释放用户资源
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/resource/releaseUserRes")
	String releaseUserRes(@RequestParam("userId") String userId);

}
