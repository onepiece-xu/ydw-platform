package com.ydw.game.model.vo;

public class LoginUserVO {
	
	private Integer identityType;
	
    private String identifier;
	
    private String account;

	private String password;
	
	private String  mobileNumber;
	
	private String messageCode;
	
	private String type;
	
	

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
