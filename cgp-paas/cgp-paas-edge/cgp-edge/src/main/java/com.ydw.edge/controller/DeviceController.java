package com.ydw.edge.controller;

import com.ydw.edge.model.constants.Constant;
import com.ydw.edge.model.vo.*;
import com.ydw.edge.service.IDeviceService;
import com.ydw.edge.service.IDeviceStatusService;
import com.ydw.edge.service.IDownAppService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 设备处理
 * @author xulh
 *
 */
@RestController
@RequestMapping("/v1/device")
public class DeviceController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDeviceStatusService statusService;

    @Resource(name = "armDeviceServiceImpl")
    private IDeviceService armDeviceServiceImpl;
    
    @Resource(name = "pcDeviceServiceImpl")
    private IDeviceService pcDeviceServiceImpl;

    @Autowired
    private IDownAppService downAppService;

    ///重启
    @PostMapping(value = "/reboot")
    public ResultInfo reboot(@RequestBody DeviceOperateParam param) {
        if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.reboot(param);
        } else {
        	return pcDeviceServiceImpl.reboot(param);
        }
    }

    ///重置
    @PostMapping(value = "/rebuild")
    public ResultInfo rebuild(@RequestBody DeviceOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.rebuild(param);
        } else {
        	return pcDeviceServiceImpl.rebuild(param);
        }
    }

    ///关机
    @PostMapping(value = "/shutdown")
    public ResultInfo shutdown(@RequestBody DeviceOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.shutdown(param);
        } else {
        	return pcDeviceServiceImpl.shutdown(param);
        }
    }

    ///开机
    @PostMapping(value = "/open")
    public ResultInfo open(@RequestBody DeviceOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.open(param);
        } else {
        	return pcDeviceServiceImpl.open(param);
        }
    }

    ///开启流服务
    @PostMapping(value = "/startStream")
    public ResultInfo startStream(@RequestBody StreamOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.startStream(param);
        } else {
        	return pcDeviceServiceImpl.startStream(param);
        }
    }

    ///关闭流服务
    @PostMapping(value = "/stopStream")
    public ResultInfo stopStream(@RequestBody StreamOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.stopStream(param);
        } else {
        	return pcDeviceServiceImpl.stopStream(param);
        }
    }

    ///开启流服务
    @PostMapping(value = "/startApp")
    public ResultInfo startApp(@RequestBody AppOperateParam param) {
        if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
            return armDeviceServiceImpl.startApp(param);
        } else {
            return pcDeviceServiceImpl.startApp(param);
        }
    }

    ///关闭流服务
    @PostMapping(value = "/stopApp")
    public ResultInfo stopApp(@RequestBody AppOperateParam param) {
        if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
            return armDeviceServiceImpl.stopApp(param);
        } else {
            return pcDeviceServiceImpl.stopApp(param);
        }
    }

    ///安装应用
    @PostMapping(value = "/install")
    public ResultInfo install(@RequestBody AppOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.install(param);
        } else {
        	return pcDeviceServiceImpl.install(param);
        }
    }

    ///卸载应用
    @PostMapping(value = "/uninstall")
    public ResultInfo uninstall(@RequestBody AppOperateParam param) {
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.unInstall(param);
        } else {
        	return pcDeviceServiceImpl.unInstall(param);
        }
    }
    
    ///设置初始化设备信息
    @PostMapping(value = "/initDeviceInfo")
    public ResultInfo initDeviceInfo(@RequestBody  DeviceOperateParam param){
    	if (param.getDeviceType() == Constant.DEVICE_ARCH_ARM){
        	return armDeviceServiceImpl.initDeviceInfo(param);
        } else {
        	return pcDeviceServiceImpl.initDeviceInfo(param);
        }
    }

    ///上传状态
    @PostMapping(value = "/updatestatus")
    public ResultInfo updateStatus(@RequestBody DeviceStatus devStatus){
        return statusService.updateStatus(devStatus);
    }

    ///上报异常
    @PostMapping(value = "/abnormal")
    public ResultInfo abnormal(@RequestBody HashMap<String,String> parameter){
        String errCode = StringUtils.isBlank(parameter.get("errCode")) ? "0" : parameter.get("errCode");
        String connectId = parameter.get("connectId");
        logger.info("流服务发生异常上报connectId:{},errCode:{}", connectId, errCode);
        return statusService.abnormal(errCode, connectId);
    }

    ///上报正常连接
    @PostMapping(value = "/normal")
    public ResultInfo normal(@RequestBody HashMap<String,String> parameter){
        String connectId = parameter.get("connectId");
        return statusService.normal(connectId);
    }

    ///上报正常连接
    @PostMapping(value = "/downLoadApp")
    public ResultInfo downLoadApp(@RequestBody HashMap<String,String> parameter){
        String relativeRemoteDirectory = parameter.get("relativeRemoteDirectory");
        String remoteFileName = parameter.get("remoteFileName");
        String absoluteLocalDirectory = parameter.get("absoluteLocalDirectory");
        String localFileName = parameter.get("localFileName");
        String appId = parameter.get("appId");
        if (StringUtils.isBlank(absoluteLocalDirectory)){
            absoluteLocalDirectory = "\\";
        }
        if (StringUtils.isBlank(localFileName)){
            localFileName = remoteFileName;
        }
        final String absoluteLocalDirectory1 = absoluteLocalDirectory;
        final String localFileName1 = localFileName;
        new Thread(() -> downAppService.downLoadApp(appId,relativeRemoteDirectory,remoteFileName,absoluteLocalDirectory1,localFileName1)).start();
        return ResultInfo.success();
    }
}

