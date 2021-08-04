package com.ydw.admin.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2418:16
 */
public class DistributionAwardVO implements Serializable {

    private Date createTime;

    private String nickName;

    private String shareCode;

    private String amount;

    private String remark;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
