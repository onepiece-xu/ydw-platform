package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-12-10
 */
public class NetbarPic implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String pic;

    private Integer orderNum;

    private String netbarId;

    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "NetbarPic{" +
        "id=" + id +
        ", pic=" + pic +
        ", orderNum=" + orderNum +
        ", netbarId=" + netbarId +
        ", valid=" + valid +
        "}";
    }
}
