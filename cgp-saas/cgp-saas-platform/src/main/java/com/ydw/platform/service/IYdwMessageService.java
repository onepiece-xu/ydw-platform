package com.ydw.platform.service;

import com.ydw.platform.model.vo.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cgp-saas-message", path = "/cgp-saas-message")
public interface IYdwMessageService {

	//获取连接信息
	@PostMapping(value = "/message/sendMsg")
	String sendMsg(@RequestBody Msg msg);
}
