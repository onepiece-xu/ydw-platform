package com.ydw.advert.model.vo;

import java.io.Serializable;

/**
 * 用户websocket断开连接vo
 * @author xulh
 *
 */
public class UserDisconnectVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1453237586377696227L;

	/**
	 * 用户id
	 */
	private String account;
	
	/**
	 * 断开时间
	 */
	private String disConnectTime;
	
	public UserDisconnectVO() {
		super();
	}
	
	public UserDisconnectVO(String account, String disConnectTime) {
		super();
		this.account = account;
		this.disConnectTime = disConnectTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDisConnectTime() {
		return disConnectTime;
	}

	public void setDisConnectTime(String disConnectTime) {
		this.disConnectTime = disConnectTime;
	}

}
