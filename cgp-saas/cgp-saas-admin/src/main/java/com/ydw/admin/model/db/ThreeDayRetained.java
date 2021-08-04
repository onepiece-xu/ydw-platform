package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-03-16
 */
public class ThreeDayRetained implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 该记录对应的日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;

    private Integer loginCount;

    /**
     * 注册用户数
     */
    private Integer registerCount;

    /**
     * 留存率次日
     */
    private Float rate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ThreeDayRetained{" +
        "id=" + id +
        ", dateTime=" + dateTime +
        ", loginCount=" + loginCount +
        ", registerCount=" + registerCount +
        ", rate=" + rate +
        "}";
    }
}
