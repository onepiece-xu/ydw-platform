package com.ydw.admin.model.vo;

import java.util.List;

public class FixActivityVO {
    private  List<Integer> ids;
    private  Integer type;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
