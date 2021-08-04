package com.ydw.admin.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-04-19
 */
public class MessageTemplate implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 内容模板
     */
    private String content;

    /**
     * 0无效1有效
     */
    private Boolean valid;

    /**
     * 模板的类型
     */
    private Integer type;

    /**
     * 0 私信 1全部通知信息
     */
    private Boolean toAll;


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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getToAll() {
        return toAll;
    }

    public void setToAll(Boolean toAll) {
        this.toAll = toAll;
    }

    @Override
    public String toString() {
        return "MessageTemplate{" +
        "id=" + id +
        ", title=" + title +
        ", content=" + content +
        ", valid=" + valid +
        ", type=" + type +
        ", toAll=" + toAll +
        "}";
    }
}
