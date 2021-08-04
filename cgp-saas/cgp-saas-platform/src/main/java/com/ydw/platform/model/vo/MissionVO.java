package com.ydw.platform.model.vo;

import com.ydw.platform.model.db.Mission;

public class MissionVO extends Mission {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
