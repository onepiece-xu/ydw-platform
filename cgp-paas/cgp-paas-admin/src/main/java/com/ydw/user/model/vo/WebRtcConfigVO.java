package com.ydw.user.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class WebRtcConfigVO {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * signal server format: http://ip:port
     */
    private String signalServer;

    private String signalServerHttps;

    /**
     * turn server format: stun:stun.yidianwan.cn:3478
     */
    private String turnServer;

    /**
     * turn 服务登录名称
     */
    private String turnUser;



    /**
     * 是否删除标志
     */
    private Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSignalServer() {
        return signalServer;
    }

    public void setSignalServer(String signalServer) {
        this.signalServer = signalServer;
    }

    public String getSignalServerHttps() {
        return signalServerHttps;
    }

    public void setSignalServerHttps(String signalServerHttps) {
        this.signalServerHttps = signalServerHttps;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
