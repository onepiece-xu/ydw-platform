package com.ydw.admin.model.vo;

import java.util.List;

public class FixBindCoupon {
    private Integer id;

    private List<String> ids;

    /** type =1 绑定  0 解绑*/
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
