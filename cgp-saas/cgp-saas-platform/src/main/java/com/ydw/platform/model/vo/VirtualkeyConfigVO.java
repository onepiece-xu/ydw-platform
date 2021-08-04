package com.ydw.platform.model.vo;

import com.ydw.platform.model.db.VirtualkeyConfig;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/1/13 13:13
 */
public class VirtualkeyConfigVO extends VirtualkeyConfig {

    /**
     * 关联app的id
     */
    private String bindAppId;

    /**
     * 关联用户使用（以后扩展）
     */
    private String bindUserId;

    public String getBindAppId() {
        return bindAppId;
    }

    public void setBindAppId(String bindAppId) {
        this.bindAppId = bindAppId;
    }

    public String getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
    }
}
