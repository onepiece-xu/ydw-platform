package com.ydw.platform.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuessYouLikeVO {
    private  String appName;
    private  String appId;
    private  String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GuessYouLikeVO s = (GuessYouLikeVO) o;

        return appId.equals(s.appId) && appName.equals(s.appName)&& description.equals(s.description);
    }

    @Override
    public int hashCode() {
        String in = appName + appId+description;
        return in.hashCode();
    }
}

