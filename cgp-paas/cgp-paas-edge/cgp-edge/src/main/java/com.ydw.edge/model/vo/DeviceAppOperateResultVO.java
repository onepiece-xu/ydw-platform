package com.ydw.edge.model.vo;

import java.io.Serializable;

public class DeviceAppOperateResultVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793942874967374041L;

	/**
	 * 类型，1:安装 2:卸载
	 */
	private Integer type;

	/**
	 * 设备id
	 */
	private String deviceId;
	
	/**
	 * 应用id
	 */
	private String appId;
	
	/**
	 * 是否成功
	 */
	private boolean status;
	
	/**
	 * 信息
	 */
	private DeviceSize msg;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public DeviceSize getMsg() {
		return msg;
	}

	public void setMsg(DeviceSize msg) {
		this.msg = msg;
	}
	
}
