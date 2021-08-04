package com.ydw.resource.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
public class WebrtcConfig implements Serializable {

    /**
     * 集群id
     */
    private String clusterId;
    /**
     * 信令服务器url
     */
    private String signalServer;

    private String signalServerHttps;
    
    private String stunUrl;
    
    private String turnTcpUrl;
    
    private String turnUdpUrl;

    /**
     * turn 服务登录名称
     */
    private String turnUser;

    /**
     * turn 登录密码
     */
    private String turnPassword;
    
    public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
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

}
