package com.ydw.advert.controller;

import com.ydw.advert.model.vo.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.advert.model.vo.ConnectVO;
import com.ydw.advert.service.IConnectService;

import java.util.HashMap;

@RestController
@RequestMapping("/connect")
public class ConnectController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConnectService connectServiceImpl;

	@PostMapping("/connectCallback")
	public ResultInfo connectCallback(@RequestBody HashMap<String, Object> map){
		String connectId = (String)map.get("connectId");
		String account = (String)map.get("account");
		int status = (int)map.get("status");
		Object detail = map.get("detail");
		logger.info("连接状态回调connectId={},account={},status={},detail={}",connectId,account,status,detail);
		connectServiceImpl.connectCallback(connectId, account, status, detail);
		return ResultInfo.success();
	}
}
