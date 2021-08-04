package com.ydw.admin.model.vo;

import java.io.Serializable;

public class ConnectVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176650383763765377L;

	/**
	 * 连接id
	 */
	private String connectId;
	
	/**
	 * 设备id
	 */
	private String deviceId;
	
	/**
	 * 游戏Id
	 */
	private String appId;
	
	/**
	 * 用户账号
	 */
	private String account;

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
