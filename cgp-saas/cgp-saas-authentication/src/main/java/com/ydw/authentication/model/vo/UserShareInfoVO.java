package com.ydw.authentication.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2810:49
 */
public class UserShareInfoVO implements Serializable {

    /**
     * 邀请人数
     */
    private int inviteNum;

    /**
     * 总收益
     */
    private BigDecimal profit;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 分享码链接
     */
    private String shareLink;

    /**
     * 分享码
     */
    private String shareCode;

    public int getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(int inviteNum) {
        this.inviteNum = inviteNum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
