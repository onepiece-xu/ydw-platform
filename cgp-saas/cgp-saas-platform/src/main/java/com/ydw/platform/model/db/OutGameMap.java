package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-07-27
 */
public class OutGameMap implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 第三方游戏id
     */
    private String outGameId;

    /**
     * 我方游戏id
     */
    private String selfGameId;

    /**
     * 类型(1：游戏管家)
     */
    private Integer type;

    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutGameId() {
        return outGameId;
    }

    public void setOutGameId(String outGameId) {
        this.outGameId = outGameId;
    }

    public String getSelfGameId() {
        return selfGameId;
    }

    public void setSelfGameId(String selfGameId) {
        this.selfGameId = selfGameId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "OutGameMap{" +
        "id=" + id +
        ", outGameId=" + outGameId +
        ", selfGameId=" + selfGameId +
        ", type=" + type +
        ", valid=" + valid +
        "}";
    }
}
