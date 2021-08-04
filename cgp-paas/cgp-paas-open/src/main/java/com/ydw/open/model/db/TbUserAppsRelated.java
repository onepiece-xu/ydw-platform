package com.ydw.open.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-08-06
 */
public class TbUserAppsRelated implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String  appId;

    private String enterpriseId;

    private Integer valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbUserAppsRelated{" +
        "id=" + id +
        ", appId=" + appId +
        ", enterpriseId=" + enterpriseId +
        ", valid=" + valid +
        "}";
    }
}
