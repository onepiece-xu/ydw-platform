package com.ydw.message.service;

import com.ydw.message.model.vo.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-saas-authentication", path = "/cgp-saas-authentication")
public interface IYdwAuthenticationService {

	//获取用户信息
	@GetMapping(value = "/login/getUserInfo")
	String getUserInfoByToken(@RequestParam(value = "token") String token);

	//判断token是否失效
    @GetMapping(value = "/login/checkToken")
    ResultInfo checkToken(@RequestParam("token") String token);
}
