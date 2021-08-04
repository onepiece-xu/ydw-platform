package com.ydw.authentication.service;

import com.ydw.authentication.model.vo.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-message", path = "/cgp-saas-message")
public interface IYdwMessageService {

	/**
     * 输入参数：
	 *  registrationId
	 *  token
	 * @param map
	 * @return
	 */
	@PostMapping(value = "/jpush/bind")
	String bind(@RequestBody HashMap<String,String> map);

	/**
     * 输入参数：
	 *  registrationId
	 *  token
	 * @param map
	 * @return
	 */
	@PostMapping(value = "/jpush/unbind")
	String unbind(@RequestBody HashMap<String,String> map);

	/**
	 * 发送消息
	 * @param msg
	 * @return
	 */
	@PostMapping(value = "/message/sendMsg")
	String sendMsg(@RequestBody Msg msg);
}
