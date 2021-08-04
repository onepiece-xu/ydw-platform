package com.ydw.edge.service.impl;

import com.ydw.edge.config.ApiConfig;
import com.ydw.edge.model.constants.Constant;
import com.ydw.edge.model.vo.*;
import com.ydw.edge.service.IDeviceService;
import com.ydw.edge.service.IYdwPaasService;
import com.ydw.edge.utils.DeviceSocket;
import com.ydw.edge.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

///pc 设备操作类
@Service
public class PcDeviceServiceImpl implements IDeviceService {
	
	@Autowired
    private IYdwPaasService ywdPaasServiceImpl;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApiConfig apiConfig;

	private ResultInfo sendCommand(Integer cmd, DeviceOperateParam param) {
		DeviceSocket devsock = null;
		if (param instanceof AppOperateParam){
			devsock = new DeviceSocket((AppOperateParam)param);
		}else if (param instanceof StreamOperateParam){
			devsock = new DeviceSocket((StreamOperateParam)param);
		}else{
			devsock = new DeviceSocket(param);
		}
		boolean isOK = devsock.sendCommand(cmd);
		if (isOK) {
			return ResultInfo.success();
		} else {
			return ResultInfo.fail();
		}
	}

	/**********************/
	/* 重启设备 */
	/**********************/
	@Override
	public ResultInfo reboot(DeviceOperateParam param) {
		List<DeviceApiVO> list = apiConfig.getList();
		if (list != null && !list.isEmpty()){
			for (DeviceApiVO vo : list){
				if (vo.getClusterId().equals(param.getClusterId())){
					String rebootUrl = vo.getRebootUrl();
					HashMap<String, Object> params = new HashMap<>();
					params.put("uuid", param.getUuid());
					logger.info("url:{}", rebootUrl);
					logger.info("uuid:{}",param.getUuid());
					String s = HttpUtil.doJsonPost(rebootUrl, params);
					logger.info("重启返回结果：{}", s);
					return ResultInfo.success();
				}
			}
		}
		return sendCommand(Constant.COMMAND_SYSTEM_REBOOT, param);
	}

	/**********************/
	/* 重建设备 */
	/**********************/
	@Override
	public ResultInfo rebuild(DeviceOperateParam param) {

		return sendCommand(Constant.COMMAND_SYSTEM_REBUILD, param);
	}

	/**********************/
	/* 开机接口，暂不实现 */
	/***********************/
	@Override
	public ResultInfo open(DeviceOperateParam param) {

		return ResultInfo.success();
	}

	/**********************/
	/* 设备关机 */
	/**********************/
	@Override
	public ResultInfo shutdown(DeviceOperateParam param) {

		return sendCommand(Constant.COMMAND_SYSTEM_CLOSE, param);
	}

	/**********************/
	/* 开启流服务 */
	/**********************/
	@Override
	public ResultInfo startStream(StreamOperateParam param) {
		return sendCommand(Constant.COMMAND_STREAM_START, param);
	}

	/**********************/
	/* 关闭流服务 */
	/**********************/
	@Override
	public ResultInfo stopStream(StreamOperateParam param) {
		return sendCommand(Constant.COMMAND_STREAM_STOP, param);
	}

	/**********************/
	/* 开启APP */
	/**********************/
	@Override
	public ResultInfo startApp(AppOperateParam param) {

		return sendCommand(Constant.COMMAND_APP_START, param);
	}

	/**********************/
	/* 关闭APP */
	/**********************/
	@Override
	public ResultInfo stopApp(AppOperateParam param) {

		logger.info("PC Stop：" + param.toString());
		sendCommand(Constant.COMMAND_APP_STOP, param);

		return ResultInfo.success();
	}

	/**********************/
	/* 安装接口，暂不实现 */
	/***********************/
	@Override
	public ResultInfo install(AppOperateParam param) {
		DeviceAppOperateResultVO resultvo = new DeviceAppOperateResultVO();
    	resultvo.setAppId(param.getAppId());
    	resultvo.setDeviceId(param.getDeviceId());
    	resultvo.setType(1);
    	resultvo.setStatus(true);
    	ywdPaasServiceImpl.reportDeviceAppResult(resultvo);
		return ResultInfo.success();
	}

	/**********************/
	/* 卸载接口，暂不实现 */
	/***********************/
	@Override
	public ResultInfo unInstall(AppOperateParam param) {
		DeviceAppOperateResultVO resultvo = new DeviceAppOperateResultVO();
    	resultvo.setAppId(param.getAppId());
    	resultvo.setDeviceId(param.getDeviceId());
    	resultvo.setType(2);
    	resultvo.setStatus(true);
    	ywdPaasServiceImpl.reportDeviceAppResult(resultvo);
		return ResultInfo.success();
	}

	public ResultInfo setID(DeviceOperateParam param) {
		return sendCommand(Constant.COMMAND_SYSTEM_SETID, param);
	}

	/// 获取 devip
	public ResultInfo getID(DeviceOperateParam param) {
		return sendCommand(Constant.COMMAND_SYSTEM_GETID, param);
	}
	
	@Override
	public ResultInfo initDeviceInfo(DeviceOperateParam param) {
		return sendCommand(Constant.COMMAND_SYSTEM_INIT, param);
	}
}
