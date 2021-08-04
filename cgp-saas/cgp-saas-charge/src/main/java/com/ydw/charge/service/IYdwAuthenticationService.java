package com.ydw.charge.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-saas-authentication", path = "/cgp-saas-authentication")
public interface IYdwAuthenticationService {

	//获取用户信息
	@GetMapping(value = "/login/getUserInfo")
	String getUserInfoByToken(@RequestParam(value = "token") String token);

	//获取用户信息
	@GetMapping(value = "/login/getUserOnlineTokens")
	String getUserOnlineTokens(@RequestParam(value = "userId") String userId);
}
