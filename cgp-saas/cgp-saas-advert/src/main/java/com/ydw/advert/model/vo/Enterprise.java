package com.ydw.advert.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public class Enterprise implements Serializable {

    private static final long serialVersionUID=1L;

    private String identification;

    private String secretKey;

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

    @Override
    public String toString() {
        return "Enterprise{" +
        ", identification=" + identification +
        ", secretKey=" + secretKey +
        "}";
    }
}
