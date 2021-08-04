package com.ydw.recharge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置
 */
@Configuration
@ConfigurationProperties(prefix = "pay.wxpay")
public class WXPayConfig {

    /**
     * 应用编号
     */
    private String appId;
    /**
     * 应用编号
     */
    private String appId1;
    /**
     * 应用密钥
     */
    private String appScret;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户平台「API安全」中的 API 密钥
     */
    private String apiKey;
    /**
     * 商户平台「API安全」中的 APIv3 密钥
     */
    private String apiKey3;
    /**
     * 应用域名，回调中会使用此参数
     */
    private String domain;
    /**
     * API 证书中的 p12
     */
    private String certPath;
    /**
     * API 证书中的 key.pem
     */
    private String keyPemPath;
    /**
     * API 证书中的 cert.pem
     */
    private String certPemPath;
    /**
     * 支付回调地址
     */
    private String notifyUrl;
    /**
     * 其他附加参数
     */
    private Object exParams;

    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiKey3() {
        return apiKey3;
    }

    public String getDomain() {
        return domain;
    }

    public String getCertPath() {
        return certPath;
    }

    public String getKeyPemPath() {
        return keyPemPath;
    }

    public String getCertPemPath() {
        return certPemPath;
    }

    public Object getExParams() {
        return exParams;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiKey3(String apiKey3) {
        this.apiKey3 = apiKey3;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public void setKeyPemPath(String keyPemPath) {
        this.keyPemPath = keyPemPath;
    }

    public void setCertPemPath(String certPemPath) {
        this.certPemPath = certPemPath;
    }

    public void setExParams(Object exParams) {
        this.exParams = exParams;
    }

    public String getAppScret() {
        return appScret;
    }

    public void setAppScret(String appScret) {
        this.appScret = appScret;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppId1() {
        return appId1;
    }

    public void setAppId1(String appId1) {
        this.appId1 = appId1;
    }
}
