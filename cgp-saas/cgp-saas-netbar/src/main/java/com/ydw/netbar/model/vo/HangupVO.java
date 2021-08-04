package com.ydw.netbar.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HangupVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9189419947231266172L;
	
	/**
	 * 连接id
	 */
	private String connectId;
	
	/**
	 * 挂机结束时间
	 */
	private LocalDateTime hangupTime;

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public LocalDateTime getHangupTime() {
		return hangupTime;
	}

	public void setHangupTime(LocalDateTime hangupTime) {
		this.hangupTime = hangupTime;
	}
	
}
