package com.ydw.netbar.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public class ApplyParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ApplyParameter() {
		super();
	}
    
	public ApplyParameter(Integer client, Integer type, List<Colonys> colonys, String baseId) {
		super();
		this.client = client;
		this.type = type;
		this.colonys = colonys;
		this.baseId = baseId;
	}

	/**
     * saas type类型, 1->云电脑， 2-> 云游戏，0->试玩
     */
    private Integer saas ;
    /**
     * 客户端类型, 0-移动客户端， 1-pc客户端，用于设置不同的流策略, 默认为 0
     */
    private Integer client ;
    /**
     * 重连超时时间，如果重连超时时间为0， 这异常中断后，直接退出
     */
    private LocalDateTime reconnectTime ;
    /**
     * 游戏id
     */
    private String gameId;
    /**
     * 用户id
     */
    private String account;
    /**
     * 企业id
     */
    private String enterpriseId;
    /**
     * 客户端类型 ,0-apk / 1-webrtc
     */
    private Integer type;
    /**
     * 区服信息
     */
    private List<Colonys> colonys;
    /**
     * 型号id
     */
    private String baseId;
    /**
     * 请求者ip
     */
    private String customIp;


    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public List<Colonys> getColonys() {
        return colonys;
    }

    public void setColonys(List<Colonys> colonys) {
        this.colonys = colonys;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getCustomIp() {
		return customIp;
	}

	public void setCustomIp(String customIp) {
		this.customIp = customIp;
	}

	public Integer getSaas() {
		return saas;
	}

	public void setSaas(Integer saas) {
		this.saas = saas;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public LocalDateTime getReconnectTime() {
		return reconnectTime;
	}

	public void setReconnectTime(LocalDateTime reconnectTime) {
		this.reconnectTime = reconnectTime;
	}

}


