package com.ydw.netbar.model.vo;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author huzh
 * @since 2020-05-12
 */

public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 资源名称
     */
    private String name;


    private String gameId;

    private Integer deviceType;


    /**
     * 外网访问IP，可以是路由器Nat地址

     */
    private String ip;
    /**
     * 外部访问端口，可以是路由器Nat端口
     */
    private Integer port;

    /**
     * 用户连接token
     */
    private String token ;

    /**
     *
     * 连接id
     */
    private String connectId;


    /**
     * 横、竖屏
     */
    private Integer screen ;


    /**
     * webrtc 参数
     */
    private String  signalServer;
    private String 	turnServer;
    private String  turnUser;
    private String 	turnPassword;



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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getSignalServer() {
        return signalServer;
    }

    public void setSignalServer(String signalServer) {
        this.signalServer = signalServer;
    }

    public String getTurnServer() {
        return turnServer;
    }

    public void setTurnServer(String turnServer) {
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


}
