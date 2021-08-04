package com.ydw.resource.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.resource.dao.UserAppsMapper;
import com.ydw.resource.model.db.Task;
import com.ydw.resource.model.vo.AppInfo;
import com.ydw.resource.service.*;
import com.ydw.resource.task.RebootingScanTask;
import com.ydw.resource.utils.RedisUtil;
import com.ydw.resource.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.resource.dao.DevicesMapper;
import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.model.vo.DeviceInfo;
import com.ydw.resource.model.vo.DeviceSizeVO;
import com.ydw.resource.task.ThreadPool;
import com.ydw.resource.utils.HttpUtil;
import com.ydw.resource.utils.ResultInfo;

@Service
public class DeviceServiceImpl extends ServiceImpl<DevicesMapper, Devices> implements IDeviceService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DevicesMapper devMapper;

	@Autowired
	private IConnectService connectServiceImpl;
	
	@Autowired
	private IYdwScheduleService ydwScheduleServiceImpl;

	@Autowired
	private YdwClusterServiceImpl ydwClusterService;

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private ThreadPool threadPool;

	@Autowired
	private RebootingScanTask rebootingScanTask;

	/**
	 * 上报状态
	 * @param deviceId
	 * @param status
	 */
	@Override
	public ResultInfo reportStatus(String deviceId, Integer status) {
		logger.info("开始上报设备{}状态{}", deviceId, status);
		if (status == Constant.DEVICE_STATUS_IDLE){
		    logger.info("状态变成idle，从重启中状态移除！");
			Long aLong = rebootingScanTask.dequeue(deviceId);
			if (aLong != null && aLong != 0L){
				taskService.runTurnOnTask(deviceId);
			}
		}
		return ydwScheduleServiceImpl.reportStatus(deviceId, status);
	}

    @Override
    public ResultInfo reportStatus(String deviceId, String macAddr, Integer status) {
	    if(StringUtil.isBlank(deviceId)){
            //可能是deviceId或者mac
            QueryWrapper<Devices> qw = new QueryWrapper<>();
            qw.eq("valid",true);
			qw.eq("inner_mac",macAddr);
            deviceId = this.getOne(qw).getId();
        }
        return reportStatus(deviceId, status);
    }

    /**
	 * 重启
	 * @param devids
	 * @return
	 */
	@Override
	public ResultInfo reboot(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(asList);
		for (DeviceInfo device : deviceInfos) {
			threadPool.submit(() -> {
				/// 查询当前设备的connectId
				ResultInfo connectByDevice = ydwScheduleServiceImpl.getConnectByDevice(device.getDeviceId());
				logger.info("根据设备id【{}】，获取连接id【{}】",device.getDeviceId(),JSON.toJSONString(connectByDevice));
				if (connectByDevice.getCode() == 200){
					// 释放当前设备内容中的状态
					logger.info("开始释放设备！{}",device.getDeviceId());
					connectServiceImpl.forceRelease(device.getDeviceId());
				}else{
					//设置重启中
					logger.info("设备{}状态！{}",device.getDeviceId(),Constant.DEVICE_STATUS_REBOOTING);
					reportStatus(device.getDeviceId(), Constant.DEVICE_STATUS_REBOOTING);
					// 通知边缘节点重启设备
					doDeviceOperation(device, Constant.URL_EDGE_REBOOT);
					// 加入重启中
					rebootingScanTask.enqueue(device.getDeviceId());
				}
			});
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo rebuild(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(asList);
		for (DeviceInfo device : deviceInfos) {
			threadPool.submit(() -> {
				// 释放当前设备内容中的状态
				connectServiceImpl.forceRelease(device.getDeviceId());
				//设置重启中
				reportStatus(device.getDeviceId(), Constant.DEVICE_STATUS_REBOOTING);
				// 通知边缘节点重建设备
				doDeviceOperation(device, Constant.URL_EDGE_REBUILD);
			});
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo forseRelease(String[] devids) {
		for (String devid : devids) {
			connectServiceImpl.forceRelease(devid);
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo shutdown(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(asList);
		for (DeviceInfo device : deviceInfos) {
			threadPool.submit(() -> {
				// 释放当前设备内容中的状态
				connectServiceImpl.forceRelease(device.getDeviceId());
				//设置重启中
				reportStatus(device.getDeviceId(), Constant.DEVICE_STATUS_CLOSED);
				// 通知边缘节点关闭设备
				doDeviceOperation(device, Constant.URL_EDGE_SHUTDOWN);
			});
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo open(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(asList);
		for (DeviceInfo device : deviceInfos) {
			threadPool.submit(() -> {
				// 通知边缘节点开启设备
				doDeviceOperation(device, Constant.URL_EDGE_OPEN);
			});
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo enable(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		UpdateWrapper<Devices> uw = new UpdateWrapper<>();
		uw.set("sch_status", true).in("id", asList);
		devMapper.update(null, uw);
		List<Devices> devices = devMapper.selectBatchIds(asList);
		for (Devices device : devices) {
			reportStatus(device.getId(), device.getStatus());
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo disable(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		UpdateWrapper<Devices> uw = new UpdateWrapper<>();
		uw.set("sch_status", false).in("id", asList);
		devMapper.update(null, uw);
		List<Devices> devices = devMapper.selectBatchIds(asList);
		for (Devices device : devices) {
			reportStatus(device.getId(), device.getStatus());
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo setId(String[] devids) {
		List<String> asList = Arrays.asList(devids);
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(asList);
		for (DeviceInfo device : deviceInfos) {
			threadPool.submit(() -> {
				doDeviceOperation(device, Constant.URL_EDGE_SETID);
			});
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo getId(String devid) {
		DeviceInfo device = devMapper.getDeviceInfo(devid);
		return doDeviceOperation(device, Constant.URL_EDGE_GETID);
	}
	
	private ResultInfo doDeviceOperation(DeviceInfo device, String url){
        Map<String,String> headers = ydwClusterService.buildHeader(device.getClusterId());
        // 通知边缘节点重启设备
		HashMap<String, Object> params = new HashMap<>();
		params.put("deviceId", device.getDeviceId());
		params.put("deviceType", device.getDeviceType());
		params.put("innerIp", device.getInnerIp());
		params.put("innerPort", device.getInnerPort());
		params.put("srtPort", device.getSrtPort());
		params.put("adbIp", device.getAdbIp());
		params.put("adbPort", device.getAdbPort());
		params.put("nodeUrl", device.getNodeUrl());
		params.put("sdPath", device.getSdPath());
		params.put("model", device.getModel());
        params.put("clusterId", device.getClusterId());
        params.put("uuid", device.getUuid());
		url = device.getApiUrl() + url;
		String doJsonPost = HttpUtil.doJsonPost(url, headers, params);
		return JSON.parseObject(doJsonPost, ResultInfo.class);
	}

	@Override
	public ResultInfo initDeviceInfo(String[] devids) {
		String result = null;
		List<String> successDevids = new LinkedList<>();
		List<DeviceInfo> deviceInfos = devMapper.getDeviceInfos(Arrays.asList(devids));
		for (DeviceInfo device : deviceInfos) {
			try {
				ResultInfo doDeviceOperation = doDeviceOperation(device, Constant.URL_EDGE_INIT);
				if (doDeviceOperation.getCode() == 200) {
					successDevids.add(device.getDeviceId());
				} else {
					logger.error("device 【{}】 init fail {}", device.getDeviceId(), result);
				}
			} catch (Exception ex) {
				logger.error("device 【{}】 init fail {}", device.getDeviceId(), ex.getMessage());
			}
		}				
		if (!successDevids.isEmpty()) {
			UpdateWrapper<Devices> uw = new UpdateWrapper<>();
			uw.set("init", true).in("id", successDevids);
			devMapper.update(null, uw);
		}
		return ResultInfo.success();
	}

	/// 修改设备的磁盘使用情况
	@Override
	public void updateDiskSize(DeviceSizeVO deviceSize) {
		UpdateWrapper<Devices> uw = new UpdateWrapper<>();
		uw.set("used_space", deviceSize.getUsedSize());
		uw.set("free_space", deviceSize.getAvailableSize());
		uw.eq("id", deviceSize.getId());
		devMapper.update(null, uw);
	}

	@Override
	public ResultInfo updateExternalIp(String gateway, String newIp) {
		UpdateWrapper<Devices> uw = new UpdateWrapper<>();
		uw.set("ip", newIp);
		uw.eq("gateway", gateway);
		boolean b = update(uw);
        if (b){
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
	}
}
