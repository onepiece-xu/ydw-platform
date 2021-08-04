package com.ydw.resource.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-paas-authentication", path = "/cgp-paas-authentication")
public interface IYdwAuthenticationService {

	//上报状态
	@PostMapping(value = "/login/clusterLogin")
	String clusterLogin(@RequestParam(value = "clusterId") String clusterId);

	@GetMapping(value = "/login/getUserInfo")
	String getUserInfoByToken(@RequestParam(value = "token") String token);
}
