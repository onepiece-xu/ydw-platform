package com.ydw.platform.util;

import org.springframework.stereotype.Component;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1114:04
 */
@Component
public class TokenUtil {

    private ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public String getToken(){
        return threadLocal.get();
    }

    public void setToken(String token){
        threadLocal.set(token);
    }

    public void removeToken(){
        threadLocal.remove();
    }
}
