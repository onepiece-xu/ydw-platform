package com.ydw.admin.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-04-19
 */
public class AwardUser implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String moblieNumber;

    private String userId;

    private Integer type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoblieNumber() {
        return moblieNumber;
    }

    public void setMoblieNumber(String moblieNumber) {
        this.moblieNumber = moblieNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AwardUser{" +
        "id=" + id +
        ", moblieNumber=" + moblieNumber +
        ", userId=" + userId +
        ", type=" + type +
        "}";
    }
}
