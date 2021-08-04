package com.ydw.platform.model.vo;

import java.io.Serializable;
import java.util.List;


public class ApplyParameter implements Serializable {
    /**
     * 游戏id
     */
    private String appId;
    /**
     * 用户id
     */
    private String account;
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
	/**
	 * 网吧id
	 */
	private String netbarId;

	/**
	 * 租号类型：
	 * 0：不租号
	 * 1：游戏管家的租号模式
	 */
	private int rentalType = 1;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<String> getClusterIds() {
		return clusterIds;
	}

	public void setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
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

	public String getNetbarId() {
		return netbarId;
	}

	public void setNetbarId(String netbarId) {
		this.netbarId = netbarId;
	}

	public int getRentalType() {
		return rentalType;
	}

	public void setRentalType(int rentalType) {
		this.rentalType = rentalType;
	}

	@Override
	public String toString() {
		return "ApplyParameter{" +
				"appId='" + appId + '\'' +
				", account='" + account + '\'' +
				", clusterIds=" + clusterIds +
				", baseId='" + baseId + '\'' +
				", customIp='" + customIp + '\'' +
				", netbarId='" + netbarId + '\'' +
				", rentalType=" + rentalType +
				'}';
	}
}


