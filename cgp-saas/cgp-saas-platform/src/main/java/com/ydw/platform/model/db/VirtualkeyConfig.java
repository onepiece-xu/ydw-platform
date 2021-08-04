package com.ydw.platform.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public class VirtualkeyConfig implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 配置内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者id
     */
    private String userId;

    /**
     * 游戏
     */
    private String appId;

    /**
     * 创建类型（0：平台创建，1：用户创建）
     */
    private Integer createType = 1;

    /**
     * 游戏类型（0：arm游戏，1：pc游戏）
     */
    private Integer appType;

    /**
     * 虚拟按键类型（0：虚拟键盘，1：虚拟手柄）
     */
    private Integer keyType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
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

    @Override
    public String toString() {
        return "VirtualkeyConfig{" +
        "id=" + id +
        ", name=" + name +
        ", content=" + content +
        ", remark=" + remark +
        ", createType=" + createType +
        ", appType=" + appType +
        ", keyType=" + keyType +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
