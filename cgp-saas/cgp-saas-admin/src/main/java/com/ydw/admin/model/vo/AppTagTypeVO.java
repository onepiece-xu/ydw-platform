package com.ydw.admin.model.vo;

public class AppTagTypeVO {
    private Integer id;
    private String tagName;
    private String tagTypeName;
    private  Boolean isBind;

    public Boolean getBind() {
        return isBind;
    }

    public void setBind(Boolean bind) {
        isBind = bind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagTypeName() {
        return tagTypeName;
    }

    public void setTagTypeName(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }
}
