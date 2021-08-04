package com.ydw.platform.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
public class TimeLimitedMission implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 活动截止时间
     */
    private Date endTime;

    /**
     * 是否删除 1正常 0删除
     */
    private Boolean valid;

    /**
     * 后台管理 是否启用 1启用 2禁用
     */
    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TimeLimitedMission{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", valid=" + valid +
        ", status=" + status +
        "}";
    }
}
