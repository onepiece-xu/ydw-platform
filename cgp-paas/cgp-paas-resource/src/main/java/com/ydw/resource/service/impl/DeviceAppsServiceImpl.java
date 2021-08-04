package com.ydw.resource.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.ydw.resource.model.enums.AppTypeEnum;
import com.ydw.resource.model.vo.AppInfo;
import com.ydw.resource.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.resource.dao.DeviceAppsMapper;
import com.ydw.resource.dao.DevicesMapper;
import com.ydw.resource.dao.UserAppsMapper;
import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.db.DeviceApps;
import com.ydw.resource.model.db.UserApps;
import com.ydw.resource.model.vo.DeviceAppOperateResultVO;
import com.ydw.resource.model.vo.DeviceInfo;
import com.ydw.resource.task.ThreadPool;
import com.ydw.resource.utils.HttpUtil;
import com.ydw.resource.utils.ResultInfo;

@Service
public class DeviceAppsServiceImpl implements IDeviceAppsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDeviceService deviceServiceImpl;
	
	@Autowired
	private IYdwScheduleService ydwScheduleServiceImpl;

    @Autowired
    private YdwClusterServiceImpl ydwClusterService;

	@Autowired
	private DeviceAppsMapper deviceAppMapper;

	@Autowired
	private DevicesMapper devicesMapper;

	@Autowired
	private UserAppsMapper userAppsMapper;

	@Autowired
	private IConnectService connectService;
	
	@Autowired
	private ThreadPool threadPool;

	@Override
	public ResultInfo installToDevices(String appId, List<String> deviceIds) {
		for (String deviceId : deviceIds) {
			installApp(deviceId, appId);
		}
		return ResultInfo.success();
	}

	private ResultInfo installApp(String deviceId, String appId) {
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
		AppInfo appInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
		return installApp(deviceInfo, appInfo);
	}

	private ResultInfo installApp(DeviceInfo deviceInfo, AppInfo appInfo) {
        boolean installAble = deviceInfo.getDeviceType() == AppTypeEnum.ARM.type ?
                appInfo.getType() == AppTypeEnum.ARM.type || appInfo.getType() == AppTypeEnum.PHONE.type :
                appInfo.getType() == AppTypeEnum.X86.type || appInfo.getType() == AppTypeEnum.COMPUTER.type;
        if (!installAble){
            // 上报设备状态
            deviceServiceImpl.reportStatus(deviceInfo.getDeviceId(), Constant.DEVICE_STATUS_IDLE);
            return ResultInfo.success();
        }
        Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
		// 上报设备状态
		deviceServiceImpl.reportStatus(deviceInfo.getDeviceId(), Constant.DEVICE_STATUS_INSTALLING);
		// 安装应用数+1
		addAppInstallNum(appInfo);
		logger.info("{}应用安装数+1", JSON.toJSONString(appInfo));
		HashMap<String, Object> params = new HashMap<>();
		params.put("deviceId", deviceInfo.getDeviceId());
		params.put("deviceType", deviceInfo.getDeviceType());
		params.put("innerIp", deviceInfo.getInnerIp());
		params.put("innerPort", deviceInfo.getInnerPort());
		params.put("adbIp", deviceInfo.getAdbIp());
		params.put("adbPort", deviceInfo.getAdbPort());
		params.put("taskID", new Random().nextInt());

        params.put("appId", appInfo.getId());
		params.put("packageName", appInfo.getPackageName());
		params.put("packageFilePath", appInfo.getPackageFilePath());
        params.put("packageFileName", appInfo.getPackageFileName());
		params.put("model", deviceInfo.getModel());
		String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_INSTALLAPP;
		threadPool.submit(() -> HttpUtil.doJsonPost(url, headers,params));
		return ResultInfo.success();
	}

    @Override
    public ResultInfo unInstallFromDevices(String appId, List<String> deviceIds) {
        for (String deviceId : deviceIds) {
            uninstallApp(deviceId, appId);
        }
        return ResultInfo.success();
    }

	public ResultInfo uninstallApp(String deviceId, String appId) {
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
		AppInfo appInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
		return uninstallApp(deviceInfo, appInfo);
	}

	public ResultInfo uninstallApp(DeviceInfo deviceInfo, AppInfo appInfo) {
        boolean installAble = deviceInfo.getDeviceType() == AppTypeEnum.ARM.type ?
                appInfo.getType() == AppTypeEnum.ARM.type || appInfo.getType() == AppTypeEnum.PHONE.type :
                appInfo.getType() == AppTypeEnum.X86.type || appInfo.getType() == AppTypeEnum.COMPUTER.type;
        if (!installAble){
            // 上报设备状态
            deviceServiceImpl.reportStatus(deviceInfo.getDeviceId(), Constant.DEVICE_STATUS_IDLE);
            return ResultInfo.success();
        }
        Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
		// 上报设备状态
		deviceServiceImpl.reportStatus(deviceInfo.getDeviceId(), Constant.DEVICE_STATUS_INSTALLING);
		// 安装应用数-1
		reduceAppInstallNum(appInfo);
		logger.info("{}应用安装数-1", JSON.toJSONString(appInfo));
		HashMap<String, Object> params = new HashMap<>();
		params.put("deviceId", deviceInfo.getDeviceId());
		params.put("deviceType", deviceInfo.getDeviceType());
		params.put("innerIp", deviceInfo.getInnerIp());
		params.put("innerPort", deviceInfo.getInnerPort());
		params.put("adbIp", deviceInfo.getAdbIp());
		params.put("adbPort", deviceInfo.getAdbPort());
		params.put("taskID", new Random().nextInt());

        params.put("appId", appInfo.getId());
        params.put("packageName", appInfo.getPackageName());
        params.put("packageFilePath", appInfo.getPackageFilePath());
        params.put("packageFileName", appInfo.getPackageFileName());
		params.put("model", deviceInfo.getModel());
		String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_UNINSTALLAPP;
		threadPool.submit(() -> HttpUtil.doJsonPost(url, headers, params));
		return ResultInfo.success();
	}

	private void addDeviceApp(String deviceId, String appId) {
		QueryWrapper<DeviceApps> qw = new QueryWrapper<>();
		qw.eq("device_id", deviceId);
		qw.eq("app_id", appId);
		qw.eq("valid", true);
		Integer count = deviceAppMapper.selectCount(qw);
		if(count == 0){
			DeviceApps d = new DeviceApps();
			d.setAppId(appId);
			d.setDeviceId(deviceId);
			d.setSetupTime(new Date());
			deviceAppMapper.insert(d);
		}
	}

	private void delDeviceApp(String deviceId, String appId) {
		UpdateWrapper<DeviceApps> uw = new UpdateWrapper<>();
		uw.set("valid", false);
		uw.set("uninstall_time", new Date());
		uw.eq("device_id", deviceId);
		uw.eq("app_id", appId);
		deviceAppMapper.update(null, uw);
	}

	private void addAppInstallNum(UserApps userApps) {
		userApps.setInstallCurrentNumber(userApps.getInstallCurrentNumber() + 1);
		userAppsMapper.updateById(userApps);
	}

	private void reduceAppInstallNum(UserApps userApps) {
		userApps.setInstallCurrentNumber(userApps.getInstallCurrentNumber() - 1);
		userAppsMapper.updateById(userApps);
	}

	// 上报游戏安装
	private void reportAppInstallStatus(String deviceId, String appId) {
		ydwScheduleServiceImpl.installedApp(deviceId, appId);
	}

	// 上报游戏卸载
	private void reportAppUnInstallStatus(String deviceId, String appId) {
		ydwScheduleServiceImpl.uninstalledApp(deviceId, appId);
	}

	@Override
	public void checkDownApps(String deviceId) {
        List<String> appIds = deviceAppMapper.getDeviceUninstallApps(deviceId);
        if (!appIds.isEmpty()){
            for (String appId : appIds) {
                logger.info("设备:{}的app:{},已不允许调度或失效，app开始卸载！", deviceId, appId);
                uninstallApp(deviceId, appId);
            }
        }
	}

	@Override
	public ResultInfo reportDeviceAppResult(DeviceAppOperateResultVO vo) {
		if (vo.getType() == 1) {
			if (vo.getStatus()) {
				// 设备和app关联
				addDeviceApp(vo.getDeviceId(), vo.getAppId());
				// 上报应用安装状态
				reportAppInstallStatus(vo.getDeviceId(), vo.getAppId());
			} else {
				// 安装应用数-1
				UserApps userApps = userAppsMapper.selectById(vo.getAppId());
				reduceAppInstallNum(userApps);
			}
		} else {
			if (vo.getStatus()) {
				// 设备和app关联
				delDeviceApp(vo.getDeviceId(), vo.getAppId());
				// 上报应用卸载
				reportAppUnInstallStatus(vo.getDeviceId(), vo.getAppId());
			} else {
				// 安装应用数+1
				UserApps userApps = userAppsMapper.selectById(vo.getAppId());
				addAppInstallNum(userApps);
			}
		}
		if(vo.getMsg() != null){
			// 修改设备存储情况
			logger.info("diskSizevo is {}", JSON.toJSONString(vo.getMsg()));
			deviceServiceImpl.updateDiskSize(vo.getMsg());
		}
		// 上报设备状态
		deviceServiceImpl.reportStatus(vo.getDeviceId(), Constant.DEVICE_STATUS_IDLE);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo startApp(List<String> deviceIds, String appId) {
        for (String deviceId : deviceIds) {
            DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
            AppInfo userAppInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
            connectService.openApp(deviceInfo, userAppInfo, null, null);
        }
        return ResultInfo.success();
	}

	@Override
	public ResultInfo stopApp(List<String> deviceIds, String appId) {
        for (String deviceId : deviceIds) {
            DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
            AppInfo userAppInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
            connectService.closeApp(deviceInfo, userAppInfo, null);
        }
        return ResultInfo.success();
	}
}
