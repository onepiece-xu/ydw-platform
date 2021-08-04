package com.ydw.game.model.vo;

import java.io.Serializable;

public class Colonys implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Integer delay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
