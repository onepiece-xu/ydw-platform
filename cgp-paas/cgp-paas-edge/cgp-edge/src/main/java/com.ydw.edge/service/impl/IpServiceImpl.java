package com.ydw.edge.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.edge.model.vo.ResultInfo;
import com.ydw.edge.service.IIpService;
import com.ydw.edge.service.IYdwPaasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpServiceImpl implements IIpService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IYdwPaasService ydwPaasService;

    @Override
    public ResultInfo updateIp(String gateway, String newIp, int type) {
        logger.info("gateway:【{}】，newIp:【{}】，type:【{}】", gateway, newIp, type);
        String result = null;
        if(type == 1){
            result = ydwPaasService.updateClusterIp(gateway,newIp);
        }else if(type == 2){
            result = ydwPaasService.updateDevicesIp(gateway,newIp);
        }
        if(result != null){
            return JSON.parseObject(result,ResultInfo.class);
        }
        return ResultInfo.fail();
    }
}
