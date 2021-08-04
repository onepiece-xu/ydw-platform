package com.ydw.authentication.model.vo;

import java.io.Serializable;

public class LoginVO implements Serializable{

	/**
	 * 企业号
	 */
	private String identification;
	
	/**
	 * 企业密钥
	 */
	private String secretKey;
	
	/**
	 * saas平台  1->云电脑， 2-> 云游戏，0->试玩
	 */
	private Integer saas = 2;

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getSaas() {
		return saas;
	}

	public void setSaas(Integer saas) {
		this.saas = saas;
	}
}
