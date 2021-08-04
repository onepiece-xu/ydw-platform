package com.ydw.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class NewRegisterCountVO {
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;
    /**
     * 当日新增注册用户
     */
    private Integer newRegisterCount;

    /**
     * 当前总注册用户
     */
    private Integer totalUserCount;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getNewRegisterCount() {
        return newRegisterCount;
    }

    public void setNewRegisterCount(Integer newRegisterCount) {
        this.newRegisterCount = newRegisterCount;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }
}
