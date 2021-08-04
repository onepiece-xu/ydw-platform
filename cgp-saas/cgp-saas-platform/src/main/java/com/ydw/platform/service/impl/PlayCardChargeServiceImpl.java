package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.platform.service.IPlayCardChargeService;
import com.ydw.platform.service.IYdwChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 畅玩卡服务
 */
@Service
public class PlayCardChargeServiceImpl implements IPlayCardChargeService {

    @Autowired
    private IYdwChargeService ydwChargeService;

    @Override
    public boolean playCardChargeAble(String account) {
        String able = ydwChargeService.playCardChargeAble(account);
        if(able != null){
            return JSON.parseObject(able).getBoolean("data");
        }
        return false;
    }

    @Override
    public void startPlayCardCharge(String connectId, String account) {
        ydwChargeService.startPlayCardCharge(connectId, account);
    }

    @Override
    public void endPlayCardCharge(String connectId, String account) {
        ydwChargeService.endPlayCardCharge(connectId, account);
    }
}
