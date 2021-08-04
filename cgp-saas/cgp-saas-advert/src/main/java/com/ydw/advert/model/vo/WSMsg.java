package com.ydw.advert.model.vo;

import java.io.Serializable;

/**
 * ws消息
 * @author xulh
 *
 */
public class WSMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8200946457625700450L;
	
	/**
	 * 接收方
	 */
	private String receiver;
	
	/**
	 * 发送方
	 */
	private String sender;
	
	/**
	 * 消息目的地
	 */
	private String destination;
	
	/**
	 * 消息内容
	 */
	private Object data;
	
	/**
	 * 消息类型（0：私发，1：广播）
	 */
	private Integer type = 0;

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
