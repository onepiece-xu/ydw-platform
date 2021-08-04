package com.ydw.admin.model.vo;

import java.io.Serializable;

public class SignInDto implements Serializable {

    /**
     * 表示签到的天数
     */
    private Integer day;

    /**
     * 1表示已经签到, 0表示未签到
     */
    private Integer flag;

    private String  pcAward;


    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getFlag() {
        return flag;
    }

    public String getPcAward() {
        return pcAward;
    }

    public void setPcAward(String pcAward) {
        this.pcAward = pcAward;
    }


    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public SignInDto(Integer day, Integer flag,String pcAward) {
        this.day = day;
        this.flag = flag;
        this.pcAward=pcAward;
    }

}
