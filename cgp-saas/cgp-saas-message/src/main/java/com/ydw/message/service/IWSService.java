package com.ydw.message.service;

import com.ydw.message.model.vo.CgpMessage;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1310:23
 */
public interface IWSService {

    public void send(CgpMessage msg);

    public void sendClient(CgpMessage msg);
}
