package com.ydw.resource.model.vo;

import com.ydw.resource.model.db.DeviceUsed;
import com.ydw.resource.model.db.UserApps;
import com.ydw.resource.utils.EncryptionUtil;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xulh
 * @since 2020-05-12
 */

public class ConnectInfo implements Serializable {

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
	 * 游戏类型 0: arm  1: x86   2：云游戏   3：云电脑，4：云手机
	 */
	private int appType;

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
	 * 连接时长（秒）
	 */
	private Long connectedTime;

	/**
	 * 排队时长
	 */
	private long queuedTime = 0L;

	/**
	 * 集群id
	 */
	private String clusterId;

	/**
	 * 集群名称
	 */
	private String clusterName;

    /**
     * 测速ip
     */
	private String speedIp;


    /**
	 * webrtc 参数
	 */
	private String signalServer;
	private String[] turnServer;
	private String turnUser;
	private String turnPassword;

    /**
     * 用户连接客户端 sdk，pc
     */
    private int client;

    /**
     * 用户连接类型srt或者webrtc
     */
    private int type;

	/**
	 * 是否是新连接
	 */
	private boolean newConnect = true;

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

	public Long getConnectedTime() {
		return connectedTime;
	}

	public void setConnectedTime(Long connectedTime) {
		this.connectedTime = connectedTime;
	}

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getSpeedIp() {
        return speedIp;
    }

    public void setSpeedIp(String speedIp) {
        this.speedIp = speedIp;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public boolean getNewConnect() {
		return newConnect;
	}

	public void setNewConnect(boolean newConnect) {
		this.newConnect = newConnect;
	}

	public long getQueuedTime() {
		return queuedTime;
	}

	public void setQueuedTime(long queuedTime) {
		this.queuedTime = queuedTime;
	}

	public void buildAppInfo(UserApps appInfo){
	    this.appName = appInfo.getName();
        this.appId = appInfo.getId();
        this.appType = appInfo.getType();
        this.screen = appInfo.getScreen();
	}

	public void buildDeviceInfo(DeviceInfo deviceInfo){
		this.deviceId = deviceInfo.getDeviceId();
		this.deviceIp = deviceInfo.getIp();
		this.devicePort = deviceInfo.getPort();
		this.deviceName = deviceInfo.getDeviceName();
		this.deviceType = deviceInfo.getDeviceType();
		this.clusterId = deviceInfo.getClusterId();
		this.clusterName = deviceInfo.getClusterName();
		this.speedIp = deviceInfo.getAccessIp();
	}

    public void buildConnectInfo(WebrtcConfig webRtcConfig){
        if (webRtcConfig != null){
            this.signalServer = webRtcConfig.getSignalServerHttps();
            this.turnServer = new String[]{webRtcConfig.getStunUrl(),
                    webRtcConfig.getTurnTcpUrl(), webRtcConfig.getTurnUdpUrl()};
            this.turnUser = webRtcConfig.getTurnUser();
            this.turnPassword = webRtcConfig.getTurnPassword();
        }

    }

	public void buildConnectInfo(DeviceUsed deviceUsed){
		if (deviceUsed != null){
			this.connectId = deviceUsed.getId();
			this.token = EncryptionUtil.MD5encode(deviceUsed.getId() + deviceUsed.getCustomId());
			this.connectedTime = Duration.between(deviceUsed.getBeginTime(), LocalDateTime.now()).getSeconds();
			this.client = deviceUsed.getClient();
			this.type = deviceUsed.getType();
			this.newConnect = false;
		}

	}

	public void buildConnectInfo(DeviceUsed deviceUsed, WebrtcConfig webRtcConfig){
		buildConnectInfo(deviceUsed);
		buildConnectInfo(webRtcConfig);
	}

}
