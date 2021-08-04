package com.ydw.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-paas-authentication", path = "/cgp-paas-authentication")
public interface IYdwAuthenticationService {

	@GetMapping("/login/checkToken")
	String authentic(@RequestParam("token") String token);
}
