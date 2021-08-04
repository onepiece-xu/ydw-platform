package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
public class KeyConfig implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 所属的游戏id
     */
    private String appId;

    /**
     * 0移动端 1web端
     */
    private Integer platform;

    /**
     * 配置内容
     */
    private String config;

    /**
     * 创建者 默认admin或用户ID
     */
    private String author;

    /**
     * 0无效/已删除 1有效
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbKeyConfig{" +
        "id=" + id +
        ", name=" + name +
        ", appId=" + appId +
        ", platform=" + platform +
        ", config=" + config +
        ", author=" + author +
        ", valid=" + valid +
        "}";
    }
}
