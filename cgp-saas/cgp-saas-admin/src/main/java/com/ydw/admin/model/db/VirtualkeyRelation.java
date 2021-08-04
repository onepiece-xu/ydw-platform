package com.ydw.admin.model.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public class VirtualkeyRelation implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 配置id
     */
    private String configId;

    /**
     * 游戏id
     */
    private String appId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否默认配置
     */
    private boolean isDefault;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date updateTime;


    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "VirtualkeyRelation{" +
        "configId=" + configId +
        ", appId=" + appId +
        ", userId=" + userId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
