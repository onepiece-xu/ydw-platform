package com.ydw.resource.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
@TableName("tb_app_strategy")
public class AppStrategy implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增ID 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 策略名称
     */
    private String name;

    /**
     * 启动帧率
     */
    private Integer fps;

    /**
     * 启动码率
     */
    private Integer speed;

    /**
     * 画质设置
     */
    private Integer video;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 编码方式
     */
    private String encode;

    /**
     * 0 默认的流策略，不可被删                                               1  可被删除的流策略
     */
    private Integer type;

    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFps() {
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
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
        return "AppStrategy{" +
        "id=" + id +
        ", name=" + name +
        ", fps=" + fps +
        ", speed=" + speed +
        ", video=" + video +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", encode=" + encode +
        ", type=" + type +
        ", valid=" + valid +
        "}";
    }
}
