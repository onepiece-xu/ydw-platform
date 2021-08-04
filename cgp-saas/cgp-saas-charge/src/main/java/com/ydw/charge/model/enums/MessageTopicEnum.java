package com.ydw.charge.model.enums;

public enum MessageTopicEnum {

	CHARGE("charge");
	
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
