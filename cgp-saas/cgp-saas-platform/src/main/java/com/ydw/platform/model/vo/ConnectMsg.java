package com.ydw.platform.model.vo;

import com.ydw.platform.model.enums.MessageTopicEnum;

import java.io.Serializable;

public class ConnectMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023069056530208541L;
	
	/**
	 * 连接id
	 */
	private String connectId;

	/**
	 * 0:未连接，1:申请连接，2:正在连接，3:退出连接，4:连接异常
	 */
	private int connectStatus;

	/**
	 * 类型
	 */
	private String type = MessageTopicEnum.CONNECT.getTopic();

	/**
	 * 详情
	 */
	private Object detail;

	public int getConnectStatus() {
		return connectStatus;
	}

	public void setConnectStatus(int connectSatus) {
		this.connectStatus = connectSatus;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
