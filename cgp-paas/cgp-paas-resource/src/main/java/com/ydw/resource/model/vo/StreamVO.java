package com.ydw.resource.model.vo;

import java.io.Serializable;

public class StreamVO implements Serializable{

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
     * 客户端类型, 0-移动客户端， 1-pc客户端，用于设置不同的流策略, 默认为 0
     */
    private  Integer client ;
    
    /**
     * 客户端类型 ,apk /webrtc
     */
    private Integer type;
    
	public StreamVO() {
		super();
	}

	public StreamVO(String connectId, String deviceId, String appId,String account, Integer client,
			Integer type) {
		super();
		this.connectId = connectId;
		this.deviceId = deviceId;
		this.appId = appId;
		this.account = account;
		this.client = client;
		this.type = type;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}
	
}
