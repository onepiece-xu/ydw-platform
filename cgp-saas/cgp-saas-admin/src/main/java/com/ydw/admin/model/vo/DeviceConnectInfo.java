package com.ydw.admin.model.vo;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author huzh
 * @since 2020-05-12
 */

public class DeviceConnectInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备id
	 */
	private String deviceId;
	/**
	 * 资源名称
	 */
	private String deviceName;

	/**
	 * 游戏id
	 */
	private String appId;
	
	/**
	 * 游戏名称
	 */
	private String appName;

	/**
	 * 设备类型
	 */
	private Integer deviceType;

	/**
	 * 外网访问IP，可以是路由器Nat地址
	 * 
	 */
	private String deviceIp;
	
	/**
	 * 外部访问端口，可以是路由器Nat端口
	 */
	private Integer devicePort;

	/**
	 * 用户连接token
	 */
	private String token;

	/**
	 *
	 * 连接id
	 */
	private String connectId;

	/**
	 * 横、竖屏
	 */
	private Integer screen;

	/**
	 * webrtc 参数
	 */
	private String signalServer;
	private String[] turnServer;
	private String turnUser;
	private String turnPassword;

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public Integer getScreen() {
		return screen;
	}

	public void setScreen(Integer screen) {
		this.screen = screen;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}


	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public Integer getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(Integer devicePort) {
		this.devicePort = devicePort;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSignalServer() {
		return signalServer;
	}

	public void setSignalServer(String signalServer) {
		this.signalServer = signalServer;
	}

	public String[] getTurnServer() {
		return turnServer;
	}

	public void setTurnServer(String[] turnServer) {
		this.turnServer = turnServer;
	}

	public String getTurnUser() {
		return turnUser;
	}

	public void setTurnUser(String turnUser) {
		this.turnUser = turnUser;
	}

	public String getTurnPassword() {
		return turnPassword;
	}

	public void setTurnPassword(String turnPassword) {
		this.turnPassword = turnPassword;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
