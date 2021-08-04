package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.UserInfo;
import com.ydw.admin.model.db.VirtualkeyConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/1/13 13:13
 */
public class VirtualkeyConfigVO extends VirtualkeyConfig {

    private String appId;

    private String appName;

    private String userId;

    private String userName;

    private String[] appIds;

    private List<UserInfo> users;

    private List<App> apps;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String[] getAppIds() {
        return appIds;
    }

    public void setAppIds(String[] appIds) {
        this.appIds = appIds;
    }

    public void buildApp(){
        apps = new ArrayList<>();
        if (StringUtils.isNotBlank(appId)){
            String[] appIds = appId.split(",");
            String[] appNames = appName.split(",");
            for (int i = 0 ; i < appIds.length ; i++){
                App app = new App();
                app.setId(appIds[i]);
                app.setName(appNames[i]);
                this.apps.add(app);
            }
        }

    }

    public void buildUser(){
        users = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)){
            String[] userIds = userId.split(",");
            String[] userNames = new String[0];
            if (StringUtils.isNotBlank(userName)){
                userNames = userName.split(",");
            }
            for (int i = 0 ; i < userIds.length ; i++){
                UserInfo user = new UserInfo();
                user.setId(userIds[i]);
                if (i >= userNames.length){
                    user.setNickname(userIds[i]);
                }else{
                    user.setNickname(userNames[i]);
                }
                this.users.add(user);
            }
        }
    }

}
