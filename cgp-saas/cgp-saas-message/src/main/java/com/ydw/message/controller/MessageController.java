package com.ydw.message.controller;

import com.ydw.message.model.vo.CgpMessage;
import com.ydw.message.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/239:33
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMessageService messageService;

    @PostMapping("/sendMsg")
    public void sendMessage(@RequestBody CgpMessage vo){
        messageService.send(vo);
    }

    @PostMapping("/receiveMsg")
    public void receiveMsg(@RequestBody CgpMessage vo){
        messageService.receive(vo);
    }

    /**
     * 检查绑定关系是否过期
     */
    @GetMapping("/checkBind")
    public void checkBind(){
        messageService.checkBind();
    }
}
