package com.ydw.admin.model.vo;

import java.io.Serializable;
import java.util.List;


public class ApplyParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ApplyParameter() {
		super();
	}
    
	public ApplyParameter(Integer client, Integer type, List<String> clusterIds, String appId) {
		super();
		this.client = client;
		this.type = type;
		this.clusterIds = clusterIds;
		this.appId = appId;
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
     * 游戏id
     */
    private String appId;
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
    private List<String> clusterIds;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<String> getClusterIds() {
		return clusterIds;
	}

	public void setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
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
}


