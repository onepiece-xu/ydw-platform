package com.ydw.user.model.db;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
public class TbDemo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 展示的序号
     */
    private Integer orderNumber;
    /**
     * 1有效 0无效
     */
    private Boolean valid;
    /**
     *  0 SDK  1 WEBRTC
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbDemo{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", orderNumber=" + orderNumber +
                ", valid=" + valid +
                ", type=" + type +
                '}';
    }
}
