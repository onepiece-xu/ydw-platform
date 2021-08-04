package com.ydw.platform.model.db;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @author hea
 * @since 2020-10-08
 */
public class PostMessage implements Serializable {

    private static final long serialVersionUID=1L;


    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    /**
     * 是否已读
     */
    private Boolean hasRead;

    /**
     * 标题
     */
    private String title;

    private  String userId;

    private Boolean toAll;

    private Integer type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getToAll() {
        return toAll;
    }

    public void setToAll(Boolean toAll) {
        this.toAll = toAll;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PostMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", hasRead=" + hasRead +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", toAll=" + toAll +
                ", type=" + type +
                '}';
    }
}
