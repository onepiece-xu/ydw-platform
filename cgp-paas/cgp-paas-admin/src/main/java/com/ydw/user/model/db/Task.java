package com.ydw.user.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author heao
 * @since 2021-05-26
 */
@TableName("tb_task")
public class Task implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 场景：0：开机后调用，1：准备资源前调用
     */
    private Integer scene;

    /**
     * 执行内容（如果是开关应用，这里则是应用id，如果是shell则就是内容）
     */
    private String content;

    /**
     * 类型 0：执行shell脚本，1：打开应用，2：关闭应用
     */
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 是否有效(0：失效，1：有效）
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Task{" +
        "id=" + id +
        ", name=" + name +
        ", description=" + description +
        ", scene=" + scene +
        ", content=" + content +
        ", type=" + type +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", valid=" + valid +
        "}";
    }
}
