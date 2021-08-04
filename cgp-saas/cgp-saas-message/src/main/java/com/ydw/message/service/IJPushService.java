package com.ydw.message.service;

import com.ydw.message.model.vo.CgpMessage;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1310:16
 */
public interface IJPushService {

    void bind(String token, String registrationId);

    void unbind(String token, String registrationId);

    void send(CgpMessage msg);
}
