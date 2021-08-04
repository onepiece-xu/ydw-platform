package com.ydw.resource.service;

import com.ydw.resource.utils.ResultInfo;

import java.util.HashMap;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/7 16:06
 */
public interface IAppService {

    ResultInfo issueApp(HashMap<String,String> parameter);

    ResultInfo issuedApp(String appId, String clusterId);

    ResultInfo getApps(String identification);
}
