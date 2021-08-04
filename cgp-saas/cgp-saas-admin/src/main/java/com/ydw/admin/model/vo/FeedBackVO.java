package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.FeedBack;

public class FeedBackVO extends FeedBack {
    private String nickname;
    private String questionName;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
