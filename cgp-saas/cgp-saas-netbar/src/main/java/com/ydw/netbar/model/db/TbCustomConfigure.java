package com.ydw.netbar.model.db;

import java.io.Serializable;

/**
 * <p>
 * 自定义配置
 * </p>
 *
 * @author xulh
 * @since 2020-07-27
 */
public class TbCustomConfigure implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 描述
     */
    private String name;

    /**
     * 机器配置id
     */
    private String baseId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    @Override
    public String toString() {
        return "TbCustomConfigure{" +
        "id=" + id +
        ", name=" + name +
        ", baseId=" + baseId +
        "}";
    }
}
