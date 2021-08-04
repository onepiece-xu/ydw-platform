package com.ydw.platform.model.vo;

import java.util.List;

public class EnableApps {

    private Integer type;

    private List<String> ids;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
