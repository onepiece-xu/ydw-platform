package com.ydw.netbar.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-29
 */
@TableName("tb_enterprise")
public class Enterprise implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String identification;

    @TableField("secretKey")
    private String secretKey;


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

    @Override
    public String toString() {
        return "Enterprise{" +
        "id=" + id +
        ", identification=" + identification +
        ", secretKey=" + secretKey +
        "}";
    }
}
