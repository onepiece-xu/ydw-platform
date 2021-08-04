package com.ydw.user.model.vo;

import java.util.Date;

public class TokenVO {
    private String token;

    private Long time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
