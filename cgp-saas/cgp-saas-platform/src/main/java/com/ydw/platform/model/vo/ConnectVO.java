package com.ydw.platform.model.vo;

import java.io.Serializable;

public class ConnectVO implements Serializable{
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

	/**
	 * 连接类型 ,0-apk / 1-webrtc
	 */
	private int type;
	/**
	 * 客户端类型, 0-移动客户端， 1-pc客户端，用于设置不同的流策略, 默认为 0
	 */
	private int client;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }
}
