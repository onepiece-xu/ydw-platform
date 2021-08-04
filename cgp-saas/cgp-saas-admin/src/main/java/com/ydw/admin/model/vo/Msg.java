package com.ydw.admin.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 消息
 * @author xulh
 *
 */
public class Msg implements Serializable{

	/**
	 *
	 */private static final long serialVersionUID = -8200946457625700450L;

	/**
	 * 接收方
	 */
	private List<String> receivers;

	/**
	 * 发送方
	 */
	private String sender;

	/**
	 * 消息内容
	 */
	private String data;

	/**
	 * 消息类型
	 */
	private String type;

	/**
	 * 消息处理类型（0：通知（不需要处理），1：指令）
	 */
	private int dealType = 0;

	/**
	 * 通讯类型（0：单播，1：多播，2：广播）
	 * 单播：只往一个客户端发送
	 * 多播：往接收者的多个客户端发送
	 * 广播：往所有客户端发送
	 */
	private int communicationType = 1;

	/**
	 * 消息渠道（0：全部，1：站内通知，2：短信）
	 * 目前没有用到短信网关
	 */
	private int channel = 1;

	/**
	 * 消息保活时长
	 * （默认不保活，只发送给在线用户）
	 */
	private long timeToLive = 0;

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(int communicationType) {
		this.communicationType = communicationType;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public int getDealType() {
		return dealType;
	}

	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

}
