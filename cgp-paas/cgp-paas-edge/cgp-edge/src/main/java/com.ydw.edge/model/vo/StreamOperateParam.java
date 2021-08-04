package com.ydw.edge.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/511:03
 */
public class StreamOperateParam extends DeviceOperateParam implements Serializable {

    /**
     * 连接凭证
     */
    private String token;

    /// 应用策略
    /// 应用启动的编码方式
    private Integer codec;

    /// 帧率
    private Integer fps;

    /// 分辨率
    private Integer video;

    /**
     * 游戏屏幕 1-横屏， 2-竖屏
     */
    private Integer screen;

    /**
     * 码率
     */
    private Integer speed;

    /**
     * 连接id
     */
    private String connectId;

    /**
     * 默认是云游戏模式
     */
    private int type = 2;

    /**
     * webrtc 参数
     */
    private boolean webRTC;
    private String signalServer;
    private String turnServer;
    private String turnUser;
    private String turnPassword;

    /**
     * 设备基本信息
     */
    private DeviceOperateParam deviceOperateParam;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCodec() {
        return codec;
    }

    public void setCodec(Integer codec) {
        this.codec = codec;
    }

    public Integer getFps() {
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public boolean getWebRTC() {
        return webRTC;
    }

    public void setWebRTC(boolean webRTC) {
        this.webRTC = webRTC;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DeviceOperateParam getDeviceOperateParam() {
        if (deviceOperateParam == null){
            deviceOperateParam = new DeviceOperateParam(super.deviceId,super.deviceType,super.innerIp,super.innerPort,
                    super.srtPort,super.adbIp,super.adbPort,super.model,super.sdPath,super.nodeUrl);
        }
        return deviceOperateParam;
    }

    public void setDeviceOperateParam(DeviceOperateParam deviceOperateParam) {
        this.deviceOperateParam = deviceOperateParam;
    }
}
