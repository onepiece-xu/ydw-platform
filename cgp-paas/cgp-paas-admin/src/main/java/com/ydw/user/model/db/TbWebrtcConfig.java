package com.ydw.user.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
public class TbWebrtcConfig implements Serializable {

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
     * turn 登录密码
     */
    private String turnPassword;

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

    public String getTurnPassword() {
        return turnPassword;
    }

    public void setTurnPassword(String turnPassword) {
        this.turnPassword = turnPassword;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbWebrtcConfig{" +
        "id=" + id +
        ", signalServer=" + signalServer +
        ", signalServerHttps=" + signalServerHttps +
        ", turnServer=" + turnServer +
        ", turnUser=" + turnUser +
        ", turnPassword=" + turnPassword +
        ", valid=" + valid +
        "}";
    }
}
