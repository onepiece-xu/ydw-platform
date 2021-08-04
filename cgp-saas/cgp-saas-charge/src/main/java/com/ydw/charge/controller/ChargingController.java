package com.ydw.charge.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ydw.charge.model.vo.ResultInfo;
import com.ydw.charge.service.IChargeService;

@RestController
@RequestMapping("/charge")
public class ChargingController extends BaseController{

    @Autowired
    private IChargeService chargeService;

    /**
     * 开始计费
     * @param connectId
     * @return
     */
    @GetMapping("/startCharge")
    public ResultInfo startCharge(@RequestParam String connectId,@RequestParam String userId){
        return chargeService.startCharge(connectId, userId);
    }

    /**
     * 计费
     * @return
     */
    @GetMapping("/doCharge")
    public void doCharge(){
        chargeService.doCharge();
    }

    /**
     * 结束计费
     * @param connectId
     * @return
     */
    @GetMapping("/endCharge")
    public ResultInfo endCharge(@RequestParam String connectId){
        return chargeService.endCharge(connectId);
    }

    /**
     * 是否能计费
     * @param userId
     * @return
     */
    @GetMapping("/chargeAble")
    public ResultInfo chargeAble(@RequestParam(required = false) String userId){
        if (StringUtils.isBlank(userId)){
            userId = super.getAccount();
        }
        return chargeService.chargeAble(userId);
    }

    /**
     * 是否有可以使用的畅玩卡
     * @param userId
     */
    @GetMapping("playCardChargeAble")
    public ResultInfo playCardChargeAble(@RequestParam(value = "userId")String userId){
        return chargeService.playCardChargeAble(userId);
    }

    /**
     * 畅玩卡开始计费
     * @param connectId
     * @param userId
     */
    @GetMapping("/startRentalNumCharge")
    public ResultInfo startPlayCardCharge(@RequestParam(value = "connectId")String connectId,
                             @RequestParam(value = "userId")String userId){
        return chargeService.startPlayCardCharge(connectId, userId);
    }

    /**
     * 畅玩卡停止计费
     * @param connectId
     * @param userId
     */
    @GetMapping("/endRentalNumCharge")
    public ResultInfo endPlayCardCharge(@RequestParam(value = "connectId")String connectId,
                           @RequestParam(value = "userId")String userId){
        return chargeService.endPlayCardCharge(connectId, userId);
    }
}
