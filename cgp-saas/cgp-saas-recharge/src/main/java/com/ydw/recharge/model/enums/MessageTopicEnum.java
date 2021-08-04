package com.ydw.recharge.model.enums;

public enum MessageTopicEnum {

	RECAHRGE("recharge");
	
	private String topic;
	
	private MessageTopicEnum(String topic){
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
