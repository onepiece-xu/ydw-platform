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
 * @since 2020-08-22
 */
public class TurnServer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String serverName;

    private String domain;

    private String publicIp;

    private Integer publicPort;

    private String stunUrl;

    private String turnTcpUrl;

    private String turnUdpUrl;

    private String userName;

    private String credential;

    private String internalIp;

    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public Integer getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(Integer publicPort) {
        this.publicPort = publicPort;
    }

    public String getStunUrl() {
        return stunUrl;
    }

    public void setStunUrl(String stunUrl) {
        this.stunUrl = stunUrl;
    }

    public String getTurnTcpUrl() {
        return turnTcpUrl;
    }

    public void setTurnTcpUrl(String turnTcpUrl) {
        this.turnTcpUrl = turnTcpUrl;
    }

    public String getTurnUdpUrl() {
        return turnUdpUrl;
    }

    public void setTurnUdpUrl(String turnUdpUrl) {
        this.turnUdpUrl = turnUdpUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TurnServer{" +
        "id=" + id +
        ", serverName=" + serverName +
        ", domain=" + domain +
        ", publicIp=" + publicIp +
        ", publicPort=" + publicPort +
        ", stunUrl=" + stunUrl +
        ", turnTcpUrl=" + turnTcpUrl +
        ", turnUdpUrl=" + turnUdpUrl +
        ", userName=" + userName +
        ", credential=" + credential +
        ", internalIp=" + internalIp +
        ", valid=" + valid +
        "}";
    }
}
