package com.ydw.resource.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.resource.service.IYdwAuthenticationService;
import com.ydw.resource.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/1417:57
 */
@Service
public class YdwClusterServiceImpl{

    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;

    public Map<String,String> buildHeader(String clusterId){
        String resultInfo = ydwAuthenticationService.clusterLogin(clusterId);
        if(resultInfo == null && JSON.parseObject(resultInfo).getIntValue("code") != 200){
            throw  new RuntimeException("边缘节点登录失败！");
        }
        String token = JSON.parseObject(resultInfo).getString("data");
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",token);
        return headers;
    }
}
