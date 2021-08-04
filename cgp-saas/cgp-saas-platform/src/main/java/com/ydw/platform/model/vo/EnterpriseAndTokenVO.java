package com.ydw.platform.model.vo;

public class EnterpriseAndTokenVO {
    private String token;

    private String enterpriseId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "EnterpriseAndTokenVO{" +
                "token='" + token + '\'' +
                ", enterpriseId='" + enterpriseId + '\'' +
                '}';
    }
}
