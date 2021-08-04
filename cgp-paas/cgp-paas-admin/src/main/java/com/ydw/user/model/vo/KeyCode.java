package com.ydw.user.model.vo;

import java.math.BigDecimal;

public class KeyCode {

    private String name;
    private Integer keycode;
    private String type;
    private BigDecimal x;
    private BigDecimal y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKeycode() {
        return keycode;
    }

    public void setKeycode(Integer keycode) {
        this.keycode = keycode;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "KeyCode{" +
                "name='" + name + '\'' +
                ", keycode=" + keycode +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
