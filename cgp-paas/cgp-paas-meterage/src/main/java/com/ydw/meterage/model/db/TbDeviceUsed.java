package com.ydw.meterage.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-05-13
 */
public class TbDeviceUsed implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;
    
    /**
     * 连接id
     */
    @TableField(exist = false)
    private String connectId;

    /**
     * 资源ID
     */
    private String deviceId;

    /**
     * 应用表中的应用ID，每一个应用都需要进行申请后才可以使用，方便调度
     */
    private String appId;

    /**
     * 应用所属企业ID
     */
    private String enterpriseId;

    /**
     * 终端用户ID
     */
    private String customId;

    /**
     * 访问来源IP
     */
    private String fromIp;

    /**
     * 开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 时长（分钟）
     */
    private Integer totalTime;

    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer client;
    
    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer type;
    
    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer saas;

    /**
     * 是否有效 :   0有效  1 无效（软删除）
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public LocalDateTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(LocalDateTime beginTime) {
		this.beginTime = beginTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "TbDeviceUsed{" +
        "id=" + id +
        ", deviceId=" + deviceId +
        ", appId=" + appId +
        ", enterpriseId=" + enterpriseId +
        ", customId=" + customId +
        ", fromIp=" + fromIp +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", totalTime=" + totalTime +
        ", valid=" + valid +
        "}";
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSaas() {
		return saas;
	}

	public void setSaas(Integer saas) {
		this.saas = saas;
	}
	
}
