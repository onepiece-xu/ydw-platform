package com.ydw.platform.model.vo;

import com.ydw.platform.model.db.AppComment;

public class AppCommentVO extends AppComment {
    private String image;
    private String nickName;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
