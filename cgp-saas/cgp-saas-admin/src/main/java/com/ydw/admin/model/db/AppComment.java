package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
public class AppComment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 评论的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 评论的用户id
     */
    private String ownerId;

    /**
     * 内容
     */
    private String comment;

    /**
     * 点赞数
     */
    private Integer thumb;

    /**
     * 0删除 1正常 
     */
    private Integer status;

    /**
     * 评论时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 1 已点赞 0未点赞
     */
    private Integer liked;

    private Integer reported;

    private Integer approved;

    public Integer getReported() {
        return reported;
    }

    public void setReported(Integer reported) {
        this.reported = reported;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getThumb() {
        return thumb;
    }

    public void setThumb(Integer thumb) {
        this.thumb = thumb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "AppComment{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", comment='" + comment + '\'' +
                ", thumb=" + thumb +
                ", status=" + status +
                ", createTime=" + createTime +
                ", liked=" + liked +
                ", reported=" + reported +
                ", approved=" + approved +
                '}';
    }
}
