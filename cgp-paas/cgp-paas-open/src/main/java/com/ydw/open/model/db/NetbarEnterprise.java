package com.ydw.open.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-12-10
 */
public class NetbarEnterprise implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 网吧id
     */
    private String netbarId;

    /**
     * 企业用户id
     */
    private String enterpriseId;

    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "NetbarEnterprise{" +
        "id=" + id +
        ", netbarId=" + netbarId +
        ", enterpriseId=" + enterpriseId +
        ", valid=" + valid +
        "}";
    }
}
