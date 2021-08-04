package com.ydw.resource.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-10-22
 */
@TableName("tb_user_service")
public class UserService implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    private String identification;

    private Integer serviceId;

    private String connectCallbackUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getConnectCallbackUrl() {
        return connectCallbackUrl;
    }

    public void setConnectCallbackUrl(String connectCallbackUrl) {
        this.connectCallbackUrl = connectCallbackUrl;
    }

    @Override
    public String toString() {
        return "UserService{" +
        "id=" + id +
        ", identification=" + identification +
        ", serviceId=" + serviceId +
        ", connectCallbackUrl=" + connectCallbackUrl +
        "}";
    }
}
