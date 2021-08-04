package com.ydw.admin.model.vo;

import java.util.List;

public class TagList {
    private String tagTypeName;
    private List<TagVO> list;

    public String getTagTypeName() {
        return tagTypeName;
    }

    public void setTagTypeName(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }

    public List<TagVO> getList() {
        return list;
    }

    public void setList(List<TagVO> list) {
        this.list = list;
    }
}
