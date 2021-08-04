package com.ydw.platform.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class PostMessageVO {

    private static final long serialVersionUID=1L;


    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
                '}';
    }
}
