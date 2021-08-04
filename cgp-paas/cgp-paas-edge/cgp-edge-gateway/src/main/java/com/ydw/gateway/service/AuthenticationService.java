package com.ydw.gateway.service;

import com.ydw.gateway.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Value("${AuthenticationUrl}")
    private String authenticationUrl;

    public String authentic(String token) {
        Map<String,String> params = new HashMap<>();
        params.put("token", token);
        return HttpUtil.doGet(authenticationUrl, params);
    }
}
