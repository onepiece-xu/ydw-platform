package com.ydw.admin.model.enums;

public enum MessageTopicEnum {


//	CONNECT("connect");
	NOTICE("notice");

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
