package com.ydw.edge.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.edge.adbdrive.AdbClientManager;
import com.ydw.edge.adbdrive.AppDetailed;
import com.ydw.edge.adbdrive.DevDiskDetailed;
import com.ydw.edge.adbdrive.IAdbClient;
import com.ydw.edge.model.constants.Constant;
import com.ydw.edge.model.vo.*;
import com.ydw.edge.service.IDeviceService;
import com.ydw.edge.service.IYdwPaasService;
import com.ydw.edge.utils.DeviceSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


///arm 设备操作类
@Service
public class ArmDeviceServiceImpl implements IDeviceService {
	
    @Autowired
    private IYdwPaasService ywdPaasServiceImpl;
	
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * socket操作
     * @param cmd
     * @param param
     * @return
     */
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
            logger.info("device ip [" + param.getInnerIp() + "] operate success, command [" + cmd.toString() + "]");
            if (cmd == Constant.COMMAND_SYSTEM_GETID) {
                return ResultInfo.success(param.getDeviceId());
            } else {
                return ResultInfo.success();
            }
        }
        logger.error("device ip [" + param.getInnerIp() + "] operate fail, command [" + cmd.toString() + "]");
        return ResultInfo.fail();
    }
    

    /**
     * 重启设备
     */
    @Override
    public ResultInfo reboot(DeviceOperateParam param) {
        logger.info("Reboot:begin to reboot [" + param.getAdbIp() + "]");
        IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(), param.getAdbPort(), param.getModel());
        Boolean reboot = adbClient.reboot();
        if (reboot == null || !reboot) {
            logger.error("Reboot: reboot device [" + param.getAdbIp() + "] fail");
            return ResultInfo.fail();
        }
        logger.info("Reboot: reboot device [" + param.getAdbIp() + "] success");
        return ResultInfo.success();
    }

    /**
     * 重建还原
     */
    @Override
    public ResultInfo rebuild(DeviceOperateParam param) {
    	//重建还原
        logger.info("Rebuild: device [" + param.getAdbIp() + "] ");
        return ResultInfo.success();
    }

    /**
     * 开机
     */
    @Override
    public ResultInfo open(DeviceOperateParam param) {
        //重建还原
        logger.info("Rebuild: device [" + param.getAdbIp() + "] ");
        return ResultInfo.success();
    }

    /**
     * 设备关机
     */
    @Override
    public ResultInfo shutdown(DeviceOperateParam param) {
        logger.info("Shutdown:device ip [" + param.getAdbIp() + "]");
        return sendCommand(Constant.COMMAND_SYSTEM_CLOSE, param);
    }

    /**
     * 开启流服务
     */
    @Override
    public ResultInfo startStream(StreamOperateParam param) {
    	logger.info("开始打开流服务{}", JSON.toJSONString(param));
        return sendCommand(Constant.COMMAND_STREAM_START, param);
    }

    /**
     * 关闭流服务
     * @param param
     * @return
     */
    @Override
    public ResultInfo stopStream(StreamOperateParam param) {
        logger.info("开始关闭流服务{}", JSON.toJSONString(param));
        return sendCommand(Constant.COMMAND_STREAM_STOP, param);
    }

    /**
     * 开启游戏
     */
    @Override
    public ResultInfo startApp(AppOperateParam param) {
        logger.info("Start:app is [" + param.getPackageName() + "], param is [" + param.toString() + "]");
        IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(), param.getAdbPort(), param.getModel());
        AppDetailed appDetailed = adbClient.getAppDetailed(param.getPackageName());
        if (appDetailed == null) {
            logger.error("Start:get device [" + param.getAdbIp() + "] app {} fail",param.getPackageName());
            return ResultInfo.fail();
        }
        Boolean startApp = adbClient.startApp(appDetailed.packageName, appDetailed.launcherActivityName);
        if (startApp == null || !startApp) {
            logger.error("Start: device [" + param.getAdbIp() + "] app is [" + appDetailed.packageName + "] fail");
            return ResultInfo.fail();
        }
        logger.info("Start: app is [" + appDetailed.packageName + " ]lanucher is [ " + appDetailed.launcherActivityName + "]  start successful..");
        return ResultInfo.success();
    }

    /**
     * 关闭游戏
     * @param param
     * @return
     */
    @Override
    public ResultInfo stopApp(AppOperateParam param) {
        logger.info("Stop:app is [" + param.getPackageName() + "], param is [" + param.toString() + "]");
        IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(), param.getAdbPort(), param.getModel());
        adbClient.stopApp(param.getPackageName());
        ///开始清理设备应用残留
        if ( param.getCloseShell() != null){
            logger.error( "Stop: device [" +param.getAdbIp() + "] exec shell [" + param.getCloseShell() + "] success");
        }
        ///获取设备的总容量和剩余容量
        DeviceSize devSize = getDeviceSize (param.getAdbIp(),param.getAdbPort(), param.getModel());
        ResultInfo res = sendCommand(Constant.COMMAND_STREAM_STOP, param);
        if ( res.getCode() == 200){
            //返回成功
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            res.setData( devSize);
            return res ;
        }
        return ResultInfo.success(devSize) ;
    }

    /**
     * 安装应用
     * @param param
     * @return
     */
    @Override
    public ResultInfo install(AppOperateParam param) {
    	logger.info("Install:device ip [" + param.getAdbIp() + "] install app is [" + param.getPackageFilePath() + "]");
    	DeviceAppOperateResultVO resultvo = new DeviceAppOperateResultVO();
    	resultvo.setAppId(param.getAppId());
    	resultvo.setDeviceId(param.getDeviceId());
    	resultvo.setType(1);
    	resultvo.setStatus(true);
    	///用adb安装应用
    	IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(),param.getAdbPort(), param.getModel());
    	AppDetailed appDetailed = adbClient.getAppDetailed(param.getPackageName());
    	if(appDetailed == null){
    		Boolean installAPP = adbClient.installAPP(param.getPackageFilePath() + param.getPackageFileName());
    		if (installAPP == null || !installAPP) {
                logger.error("Install:device ip [" + param.getAdbIp() + "] install app is [" + param.getPackageFilePath() + "] fail!!");
                resultvo.setStatus(false);
            }
    	}
        if (Constant.ARM_AGENT_PACKAGE.equals(param.getPackageName())) {
            Boolean startApp = adbClient.startApp(appDetailed.packageName, appDetailed.launcherActivityName);
            if (startApp == null || !startApp) {
                logger.error("Install:device ip [ " + param.getAdbIp() + "]  app is [" + param.getPackageName() + "]，start fail ");
                resultvo.setStatus(false);
            }
            logger.error("Install:app is [" + appDetailed.packageName + " ]lanucher is [ " + appDetailed.launcherActivityName + "] success");
        }
        ///获取设备的总容量和剩余容量
        DeviceSize devSize = getDeviceSize (param.getAdbIp(),param.getAdbPort(), param.getModel());
        devSize.setId(param.getDeviceId());
        resultvo.setMsg(devSize);
        logger.info("devicesize is {}", JSON.toJSONString(resultvo));
        ywdPaasServiceImpl.reportDeviceAppResult(resultvo);
        return ResultInfo.success();
    }

    /**
     * 卸载应用
     * @param param
     * @return
     */
    @Override
    public ResultInfo unInstall(AppOperateParam param) {
        ///用adb 卸载应用
        logger.info("Uninstall:device ip 【{}】 uninstall app is 【{}】", param.getAdbIp() , param.getPackageName());
        DeviceAppOperateResultVO resultvo = new DeviceAppOperateResultVO();
    	resultvo.setAppId(param.getAppId());
    	resultvo.setDeviceId(param.getDeviceId());
    	resultvo.setType(2);
    	resultvo.setStatus(true);
    	IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(),param.getAdbPort(), param.getModel());
    	AppDetailed appDetailed = adbClient.getAppDetailed(param.getPackageName());
    	if(appDetailed != null){
    		Boolean uninstallAPP = adbClient.uninstallAPP(param.getPackageName());
            if (uninstallAPP == null || !uninstallAPP) {
                logger.error("Uninstall:device ip [" + param.getAdbIp() + "] uninstall app is [" + param.getPackageName() + "] fail");
                resultvo.setStatus(false);
            }
    	}else{
    		logger.error("Uninstall:device ip 【{}】 uninstall app 【{}】 is not exist", param.getAdbIp() , param.getPackageName());
    	}
        ///获取设备的总容量和剩余容量
        DeviceSize devSize = getDeviceSize (param.getAdbIp(),param.getAdbPort(), param.getModel());
        resultvo.setMsg(devSize);
        logger.info("devicesize is {}", JSON.toJSONString(resultvo));
        ywdPaasServiceImpl.reportDeviceAppResult(resultvo);
        return ResultInfo.success();
    }

    /**
     * 获取磁盘使用情况
     * @param ip
     * @return
     */
    private DeviceSize getDeviceSize(String ip, int adbPort, String model) {
    	IAdbClient adbClient = AdbClientManager.getAdbClient(ip, adbPort ,model);
        DevDiskDetailed disk = adbClient.getDiskSize();
        DeviceSize devSize = null;
        if (disk != null) {
            devSize = new DeviceSize();
            if (disk.totalSize > -1) {
                devSize.setTotalSize(disk.totalSize /1024);
            }
            if (disk.availableSize > -1) {
                devSize.setAvailableSize(disk.availableSize /1024);
            }
            if (disk.usedSize > -1) {
                devSize.setUsedSize(disk.usedSize/1024);
            }
            if (disk.usedRate > -1) {
                devSize.setUsedSize(disk.usedSize/1024);
            }
            logger.info(devSize.toString());
        }
        return devSize;
    }

    @Override
	public ResultInfo initDeviceInfo(DeviceOperateParam param) {
		IAdbClient adbClient = AdbClientManager.getAdbClient(param.getAdbIp(), param.getAdbPort() ,param.getModel());
		String path = param.getAdbIp() + ":" + param.getAdbPort();
		try {
			File file = new File(path);
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			String jsonString = JSON.toJSONString(param);
			logger.info("配置文件内容为：{}", jsonString);
			out.write(jsonString);
			out.flush();
			out.close();
			adbClient.upLoadFile(file.getAbsolutePath(), param.getSdPath() + Constant.CONFIG_NAME);
			file.delete();
			return ResultInfo.success();
		} catch (Exception e) {
			return ResultInfo.fail();
		} 
	}
}
