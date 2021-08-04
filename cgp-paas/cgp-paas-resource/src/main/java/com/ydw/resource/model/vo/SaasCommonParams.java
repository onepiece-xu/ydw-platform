package com.ydw.resource.model.vo;

import java.io.Serializable;

public class SaasCommonParams implements Serializable {

    /**
     * 企业id
     */
    protected String enterpriseId;
    /**
     * saas  0->平台 1->试玩， 2-> 云游戏，3-> 云电脑，4-> 云网吧
     */
    protected Integer saas;

    /**
     * saas的用户id
     */
    protected String account;

    public SaasCommonParams() {
    }

    public SaasCommonParams(String enterpriseId, Integer saas, String account) {
        this.enterpriseId = enterpriseId;
        this.saas = saas;
        this.account = account;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getSaas() {
        return saas;
    }

    public void setSaas(Integer saas) {
        this.saas = saas;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
