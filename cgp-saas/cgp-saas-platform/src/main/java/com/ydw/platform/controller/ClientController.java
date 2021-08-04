package com.ydw.platform.controller;

import com.ydw.platform.service.IClientService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/2310:41
 */
@RestController
@RequestMapping("/client")
public class ClientController extends BaseController{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IClientService clientService;

    @GetMapping("/getUpgradeFile")
    public void getUpgradeFile(@RequestParam(required = false) String channelId){
        logger.info("获取升级文件 channelId:【{}】", channelId);
        if (StringUtils.isBlank(channelId) || channelId.equalsIgnoreCase("null")){
            channelId = "0";
        }
        clientService.getUpgradeFile(channelId,getResponse());
    }

}
