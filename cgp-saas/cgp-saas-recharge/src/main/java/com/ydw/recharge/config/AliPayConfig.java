package com.ydw.recharge.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "pay.alipay")
public class AliPayConfig {

	private String protocol;
	
	private String gatewayHost;
	
	private String signType;
	
	private String appId;
	
	private String merchantPrivateKey;
	
	private String merchantCertPath;
	
	private String alipayCertPath;
	
	private String alipayRootCertPath;
	
	private String alipayPublicKey;
	
	private String notifyUrl;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getGatewayHost() {
		return gatewayHost;
	}

	public void setGatewayHost(String gatewayHost) {
		this.gatewayHost = gatewayHost;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMerchantPrivateKey() {
		return merchantPrivateKey;
	}

	public void setMerchantPrivateKey(String merchantPrivateKey) {
		this.merchantPrivateKey = merchantPrivateKey;
	}

	public String getMerchantCertPath() {
		return merchantCertPath;
	}

	public void setMerchantCertPath(String merchantCertPath) {
		this.merchantCertPath = merchantCertPath;
	}

	public String getAlipayCertPath() {
		return alipayCertPath;
	}

	public void setAlipayCertPath(String alipayCertPath) {
		this.alipayCertPath = alipayCertPath;
	}

	public String getAlipayRootCertPath() {
		return alipayRootCertPath;
	}

	public void setAlipayRootCertPath(String alipayRootCertPath) {
		this.alipayRootCertPath = alipayRootCertPath;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@PostConstruct
	public void initConfig (){
		Config config = new Config();
		config.alipayCertPath = alipayCertPath;
		config.alipayPublicKey = alipayPublicKey;
		config.alipayRootCertPath = alipayRootCertPath;
		config.appId = appId;
		config.gatewayHost = gatewayHost;
		config.notifyUrl = notifyUrl;
		config.protocol = protocol;
		config.merchantCertPath = merchantCertPath;
		config.merchantPrivateKey = merchantPrivateKey;
		config.signType = signType;
		Factory.setOptions(config);
	}
}
