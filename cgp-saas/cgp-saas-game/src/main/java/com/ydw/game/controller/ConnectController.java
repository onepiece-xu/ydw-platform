package com.ydw.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.IConnectService;

@RestController
@RequestMapping("/connect")
public class ConnectController {
	
	@Autowired
	private IConnectService connectService;

	@GetMapping("/abnormalConnect")
	public ResultInfo abnormalConnect(@RequestParam String connectId){
		return connectService.abnormalConnect(connectId);
	}
}
