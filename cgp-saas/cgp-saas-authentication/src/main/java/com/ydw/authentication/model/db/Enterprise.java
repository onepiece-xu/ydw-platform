package com.ydw.authentication.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@TableName("tb_enterprise")
public class Enterprise implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String identification;

    @TableField("secretKey")
    private String secretKey;

    //saas平台  0：平台，1：云试玩， 2：云游戏，3：云电脑
    private Integer saas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getSaas() {
        return saas;
    }

    public void setSaas(Integer saas) {
        this.saas = saas;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
        "id=" + id +
        ", identification=" + identification +
        ", secretKey=" + secretKey +
        "}";
    }
}
