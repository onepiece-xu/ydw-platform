package com.ydw.schedule.model.vo;

import java.io.Serializable;

/**
 * 连接和设备对应关系
 * @author xulh
 *
 */
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
	 * appId
	 */
	private String appId;
	
	/**
	 * 用户
	 */
	private String account;

    /**
     * 连接时间
     */
	private long connectTime;

	/**
	 * 排队时长
	 */
	private long queuedTime = 0L;
	
	public ConnectVO() {
		super();
	}
	
	public ConnectVO(String connectId, String deviceId, String appId) {
		super();
		this.connectId = connectId;
		this.deviceId = deviceId;
		this.appId = appId;
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

    public long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

	public long getQueuedTime() {
		return queuedTime;
	}

	public void setQueuedTime(long queuedTime) {
		this.queuedTime = queuedTime;
	}

	@Override
	public String toString() {
		return "ConnectVO{" +
				"connectId='" + connectId + '\'' +
				", deviceId='" + deviceId + '\'' +
				", appId='" + appId + '\'' +
				", account='" + account + '\'' +
				", connectTime=" + connectTime +
				", queuedTime=" + queuedTime +
				'}';
	}
}
