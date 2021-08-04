package com.ydw.schedule.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ydw.schedule.model.vo.ApplyConnectVO;
import com.ydw.schedule.model.vo.ConnectVO;
import com.ydw.schedule.model.vo.ResultInfo;
import com.ydw.schedule.service.IConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connect")
public class ConnectController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConnectService connectServiceImpl;

	/**
	 * 申请连接
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/applyConnect")
	public ResultInfo applyConnect(@RequestBody ApplyConnectVO vo) {
		if (StringUtils.isBlank(vo.getAppId())
				|| vo.getClusterIds() == null
				|| vo.getClusterIds().isEmpty()
				|| StringUtils.isBlank(vo.getAccount())) {
			String params = JSON.toJSONString(vo);
			logger.error("申请设备时，参数错误！{}", params);
			return ResultInfo.fail("申请设备时，参数错误！" + params);
		}
		return connectServiceImpl.applyConnect(vo);
	}

	/**
	 * 释放连接
	 *
	 * @return
	 */
	@PostMapping(value = "/releaseConnect")
	public ResultInfo releaseConnect(@RequestBody ConnectVO vo) {
		if (StringUtils.isBlank(vo.getConnectId())
				|| StringUtils.isBlank(vo.getDeviceId())) {
			String params = JSON.toJSONString(vo);
			logger.error("归还设备时，参数错误！{}", params);
			return ResultInfo.fail("归还设备时，参数错误！" + params);
		}
		return connectServiceImpl.releaseConnect(vo);
	}

	/**
	 * 获取用户连接信息
	 *
	 * @return
	 */
	@GetMapping(value = "/getUserConnectStatus")
	public ResultInfo getUserConnectStatus(@RequestParam String account) {
		return connectServiceImpl.getUserConnectStatus(account);
	}

	/**
	 * 根据设备id获取连接id
	 *
	 * @param deviceId
	 * @return
	 */
	@GetMapping(value = "/getConnectByDevice")
	public ResultInfo getConnectByDevice(@RequestParam String deviceId) {
		return connectServiceImpl.getConnectByDevice(deviceId);
	}

	/**
	 * 根据连接id获取设备id
	 *
	 * @param connectId
	 * @return
	 */
	@GetMapping(value = "/getDeviceByConnect")
	public ResultInfo getDeviceByConnect(@RequestParam String connectId) {
		return connectServiceImpl.getDeviceByConnect(connectId);
	}
}