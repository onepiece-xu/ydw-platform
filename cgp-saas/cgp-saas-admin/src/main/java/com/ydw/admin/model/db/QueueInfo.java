package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-03-29
 */
public class QueueInfo implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 此时刻用户排队数
     */
    private Integer queueNum;

    /**
     * 此时时刻连接的用户数
     */
    private Integer connectNum;

    /**
     * 记录的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8" )
    private Date dateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
    }

    public Integer getConnectNum() {
        return connectNum;
    }

    public void setConnectNum(Integer connectNum) {
        this.connectNum = connectNum;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "QueueInfo{" +
        "id=" + id +
        ", queueNum=" + queueNum +
        ", connectNum=" + connectNum +
        ", dateTime=" + dateTime +
        "}";
    }
}
