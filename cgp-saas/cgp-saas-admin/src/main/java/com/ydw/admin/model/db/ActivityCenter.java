package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
public class ActivityCenter implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 图片地址
     */
    private String picPath;

    /**
     * 访问地址
     */
    private String accessPath;

    /**
     * 活动类型
     */
    private Integer type;

    private Integer orderNum;

    private Boolean valid;

    private String delPath;

    public String getDelPath() {
        return delPath;
    }

    public void setDelPath(String delPath) {
        this.delPath = delPath;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActivityCenter{" +
                "id=" + id +
                ", picPath='" + picPath + '\'' +
                ", accessPath='" + accessPath + '\'' +
                ", type='" + type + '\'' +
                ", orderNum=" + orderNum +
                ", valid=" + valid +
                '}';
    }
}
