package com.ydw.message.service;

import com.ydw.message.model.vo.CgpMessage;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/2216:51
 */
public interface IMessageService {
    void send(CgpMessage msg);

    void receive(CgpMessage msg);

    void checkBind();
}
