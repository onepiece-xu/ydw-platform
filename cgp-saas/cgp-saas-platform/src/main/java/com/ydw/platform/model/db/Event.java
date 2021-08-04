package com.ydw.platform.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-01-26
 */
public class Event implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 活动id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 访问地址
     */
    private String accessUrl;

    /**
     * 活动是否有效  1启用 0禁用
     */
    private Boolean status;

    /**
     * 1 pc类型活动  2arm类型活动
     */
    private Integer type;

    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
        "id=" + id +
        ", name=" + name +
        ", picUrl=" + picUrl +
        ", accessUrl=" + accessUrl +
        ", status=" + status +
        ", type=" + type +
        "}";
    }
}
