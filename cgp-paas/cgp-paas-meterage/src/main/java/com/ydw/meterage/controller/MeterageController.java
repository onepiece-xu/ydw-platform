package com.ydw.meterage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ydw.meterage.model.db.TbDeviceUsed;
import com.ydw.meterage.model.vo.ResultInfo;
import com.ydw.meterage.service.ITbDeviceUsedService;

import java.util.HashMap;

@RestControllerAdvice
@RestController
@RequestMapping("/meterage")
public class MeterageController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(Exception.class)
    public ResultInfo handle(Exception e) {
        logger.error("【{}】处发生异常: 【{}】",e.getStackTrace()[0],e.getMessage());
        return ResultInfo.fail("接口返回失败！");
    }
	
	@Autowired
	private ITbDeviceUsedService tbDeviceUsedServiceImpl;
	
	@PostMapping(value = "/beginUse")
	public ResultInfo beginUse(@RequestBody TbDeviceUsed vo){
		if(vo == null || StringUtils.isBlank(vo.getConnectId()) || StringUtils.isBlank(vo.getDeviceId())){
			return ResultInfo.fail("非法参数！"+JSON.toJSONString(vo));
		}
		return tbDeviceUsedServiceImpl.beginUse(vo);
	}
	
	@GetMapping(value = "/endUse")
	public ResultInfo endUse(@RequestParam String connectId){
		if(StringUtils.isBlank(connectId)){
			return ResultInfo.fail("非法参数！");
		}
		return tbDeviceUsedServiceImpl.endUse(connectId);
	}
	
	@GetMapping(value = "/getConnectDetail")
	public ResultInfo getConnectDetail(@RequestParam String connectId){
		return tbDeviceUsedServiceImpl.getConnectDetail(connectId);
	}

	//修改连接方式
	@PostMapping(value = "/updateConnectMethod")
	ResultInfo updateConnectMethod(@RequestBody HashMap<String, Object> params){
		String connectId = (String)params.get("connectId");
		int client = (int)params.get("client");
		int type = (int)params.get("type");
		return tbDeviceUsedServiceImpl.updateConnectMethod(connectId, client ,type);
	}
}
