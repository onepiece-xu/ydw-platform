package com.ydw.user.service;


import com.ydw.user.utils.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "cgp-paas-authentication", path = "/cgp-paas-authentication")
public interface IYdwAuthenticationService {


	@GetMapping(value = "/login/getUserInfo")
	ResultInfo getUserInfo(@RequestParam(value = "token") String token);


	@GetMapping(value = "/login/getIdentification")
	ResultInfo getIdentification(@RequestParam(value = "token") String token);
}
