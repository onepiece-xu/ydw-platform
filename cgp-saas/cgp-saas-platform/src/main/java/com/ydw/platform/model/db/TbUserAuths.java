package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2019-09-24
 */
public class TbUserAuths implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private Integer identityType;
    private String identifier;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "TbUserAuths{" +
        ", id=" + id +
        ", userId=" + userId +
        ", identityType=" + identityType +
        ", identifier=" + identifier +
        "}";
    }
}
