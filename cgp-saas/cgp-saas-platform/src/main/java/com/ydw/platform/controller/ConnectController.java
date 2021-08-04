package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/connect")
public class ConnectController {
    Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IConnectService connectService;

	@GetMapping("/abnormalConnect")
	public void abnormalConnect(@RequestParam String connectId){
		connectService.abnormalConnect(connectId);
	}

    @GetMapping("/getConnectInfo")
	public ResultInfo getConnectInfo(@RequestParam String connectId){
		return connectService.getConnectInfo(connectId);
	}

	@GetMapping("/offlineScan")
	public void offlineScan(){
		connectService.offlineScan();
	}

	@PostMapping("/connectCallback")
	public ResultInfo connectCallback(@RequestBody HashMap<String, Object> map){
		String connectId = (String)map.get("connectId");
		String account = (String)map.get("account");
		int status = (int)map.get("status");
		Object detail = map.get("detail");
        logger.info("连接状态回调connectId={},account={},status={},detail={}",connectId,account,status,detail);
		connectService.connectCallback(connectId, account, status, detail);
		return ResultInfo.success();
	}

}
