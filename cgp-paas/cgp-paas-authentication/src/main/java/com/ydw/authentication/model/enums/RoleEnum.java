package com.ydw.authentication.model.enums;

public enum RoleEnum {
	USER("user"), ENTERPRISE("enterprise");

	private String value;

	private RoleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}