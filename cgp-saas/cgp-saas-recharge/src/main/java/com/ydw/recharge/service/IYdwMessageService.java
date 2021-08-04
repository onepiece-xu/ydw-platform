package com.ydw.recharge.service;

import com.ydw.recharge.model.vo.MsgVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-message", path = "/cgp-saas-message")
public interface IYdwMessageService {

	//获取连接信息
	@PostMapping(value = "/message/sendMsg")
	String sendMsg(@RequestBody MsgVO msg);

	//获取连接信息
	@PostMapping(value = "/sms/sendSms")
	String sendSms(@RequestBody HashMap<String,Object> param);
}
