package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
public class Faq implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * FAQ的标题
     */
    private String title;

    /**
     * FAQ发布时间
     */
    private Date time;

    /**
     * FAQ内容
     */
    private String content;

    /**
     * 0失效 1有效
     */
    private Boolean valid;
    /**
     * 顺序
     */
    private Integer orderNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Faq{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", valid=" + valid +
                ", orderNumber=" + orderNumber +
                '}';
    }
}
