package com.ydw.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-saas-authentication", path = "/cgp-saas-authentication")
public interface IYdwAuthenticationService {

	@GetMapping("/login/checkToken")
	String authentic(@RequestParam("token") String token);
}
