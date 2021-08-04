package com.ydw.authentication.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户关系树
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public class UserRelational implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 推荐人
     */
    private String recommender;

    /**
     * 下级
     */
    private String inferior;

    /**
     * 拉新方式
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否有效
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getInferior() {
        return inferior;
    }

    public void setInferior(String inferior) {
        this.inferior = inferior;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "UserRelational{" +
        "id=" + id +
        ", recommender=" + recommender +
        ", inferior=" + inferior +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", valid=" + valid +
        "}";
    }
}
