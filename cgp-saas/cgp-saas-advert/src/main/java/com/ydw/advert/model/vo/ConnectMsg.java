package com.ydw.advert.model.vo;

import java.io.Serializable;

public class ConnectMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023069056530208541L;
	
	/**
	 * 连接id
	 */
	private String connectId;
	
	/**
	 * 连接id
	 */
	private String deviceId;

	/**
	 * 0:未连接，1:申请连接，2:正在连接，3:退出连接，4:连接异常
	 */
	private int connectSatus;
	
	private Object connectInfo;

	public int getConnectSatus() {
		return connectSatus;
	}

	public void setConnectSatus(int connectSatus) {
		this.connectSatus = connectSatus;
	}

	public Object getConnectInfo() {
		return connectInfo;
	}

	public void setConnectInfo(Object connectInfo) {
		this.connectInfo = connectInfo;
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
	
}
