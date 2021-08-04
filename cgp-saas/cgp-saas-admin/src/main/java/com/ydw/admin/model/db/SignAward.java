package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hea
 * @since 2020-10-14
 */
public class SignAward implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String pcAward;


    private Integer continueDays;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPcAward() {
        return pcAward;
    }

    public void setPcAward(String pcAward) {
        this.pcAward = pcAward;
    }


    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    @Override
    public String toString() {
        return "SignAward{" +
                "id=" + id +
                ", pcAward='" + pcAward + '\'' +
                ", continueDays=" + continueDays +
                '}';
    }
}
