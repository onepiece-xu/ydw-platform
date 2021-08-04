package com.ydw.recharge.controller;

import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description //TODO
 * @Author xulh
 * @date 2020/10/14 13:28
 **/
@RestController
@RequestMapping("/recharge")
public class RechargeController extends BaseController{
	
	@Autowired
	private IRechargeService rechargeServiceImpl;

    /**
     * @Author xulh
     * @Description 签到获得充值卡
     * @Param userId 
     * @return com.ydw.recharge.model.vo.ResultInfo
     * @throws
     * @date 2020/10/14 13:25
     **/
    @PostMapping("/signRecharge")
    public ResultInfo signRecharge(@RequestParam String userId){
        return rechargeServiceImpl.signRecharge(userId);
    }

    /**
     * 连续签到
     * @param userId
     * @return
     */
    @PostMapping("/continueSignRecharge")
    public ResultInfo continueSignRecharge(@RequestParam String userId){
        return rechargeServiceImpl.continueSignRecharge(userId);
    }

    /**
     * c端用户获取自己充值记录
     * @param
     * @throws
     * @author xulh 2020/10/14 13:46
     **/
    @GetMapping("/getOwnRechargeRecord")
    public ResultInfo getOwnRechargeRecord(){
        String account = super.getAccount();
        return rechargeServiceImpl.getUserRechargeRecord(account);
    }

    /**
     * 获取用户充值记录
     * @param userId
     * @throws
     * @author xulh 2020/10/14 13:47
     **/
    @GetMapping("/getUserRechargeRecord")
    public ResultInfo getUserRechargeRecord(@RequestParam String userId){
        return rechargeServiceImpl.getUserRechargeRecord(userId);
    }
}
