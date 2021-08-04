package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户签到表
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
public class SignIn implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 签到用户id
     */
    private String userId;

    /**
     * 连续签到天数
     */
    private Integer continueDays;

    /**
     * 更新日期, 最后签到日期
     */

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", continueDays=" + continueDays +
                ", updateTime=" + updateTime +
                '}';
    }


    }
