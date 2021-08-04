package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.UserInfo;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2411:40
 */
public class DistributionUserVO extends UserInfo {

    /**
     * 推荐人名称
     */
    private String recommenderName;

    /**
     * 邀请人数
     */
    private int invitationNum;

    /**
     * 邀请人数
     */
    private int bindNum;

    public int getInvitationNum() {
        return invitationNum;
    }

    public void setInvitationNum(int invitationNum) {
        this.invitationNum = invitationNum;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public int getBindNum() {
        return bindNum;
    }

    public void setBindNum(int bindNum) {
        this.bindNum = bindNum;
    }
}
