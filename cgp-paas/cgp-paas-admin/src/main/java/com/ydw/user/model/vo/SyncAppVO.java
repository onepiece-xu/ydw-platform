package com.ydw.user.model.vo;

import java.util.Date;

public class SyncAppVO {

    /**
     * 自动生成ID
     */
    private String id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用许可证号
     */
    private String accessId;

    /**
     * 用类型：arm/x86     0: arm  1: x86
     */
    private Integer type;

    /**
     * 应用描述
     */
    private String description;


    /**
     * 发布时间
     */
    private Date createTime;

    private String  action;

    private Integer free;

    private Integer platform;

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

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}
