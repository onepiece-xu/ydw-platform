package com.ydw.admin.model.db;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-03-26
 */
public class UserChannel implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 渠道商id
     */
    private String enterpriseId;

    /**
     * 渠道商名称
     */
    private String enterpriseName;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 0：不存在，1：正常
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserChannel{" +
        "id=" + id +
        ", enterpriseId=" + enterpriseId +
        ", enterpriseName=" + enterpriseName +
        ", channelId=" + channelId +
        ", valid=" + valid +
        "}";
    }
}
