package com.ydw.platform.model.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ip黑名单
 * </p>
 *
 * @author xulh
 * @since 2021-05-10
 */
public class IpBlack implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 远端ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "IpBlack{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
