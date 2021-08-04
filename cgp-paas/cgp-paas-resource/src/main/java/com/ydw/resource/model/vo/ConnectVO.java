package com.ydw.resource.model.vo;

public class ConnectVO extends SaasCommonParams{

	/**
	 * 连接id
	 */
	protected String connectId;
	
	/**
	 * 设备id
	 */
    protected String deviceId;
	
	/**
	 * 游戏Id
	 */
    protected String appId;

	/**
	 * 连接时间
	 */
    protected long connectTime;

	/**
	 * 排队时长
	 */
    protected long queuedTime = 0L;

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
				", connectTime=" + connectTime +
				", queuedTime=" + queuedTime +
				", enterpriseId='" + enterpriseId + '\'' +
				", saas=" + saas +
				", account='" + account + '\'' +
				'}';
	}
}
