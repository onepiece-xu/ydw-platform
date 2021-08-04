package com.ydw.recharge.controller;


import com.alibaba.fastjson.JSONObject;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.WithdrawVO;
import com.ydw.recharge.service.IWithdrawRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/withdrawRecord")
public class WithdrawRecordController extends BaseController{

    @Autowired
    private IWithdrawRecordService withdrawRecordService;

    /**
     * 用户申请提现
     * @param withdrawVO
     * @return
     */
    @PostMapping("/applyWithraw")
    public ResultInfo applyWithraw(@RequestBody WithdrawVO withdrawVO){
        if (/**StringUtils.isBlank(withdrawVO.getValidateCode()) || **/
                StringUtils.isBlank(withdrawVO.getPayId()) ){
            return ResultInfo.fail("验证码为空或者提现方式为空！");
        }
        JSONObject userInfo = super.getUserInfo();
        withdrawVO.setPayee(userInfo.getString("id"));
        withdrawVO.setMobileNumber(userInfo.getString("mobileNumber"));
        return withdrawRecordService.applyWithraw(withdrawVO);
    }

    /**
     * 获取用户提现记录
     * @return
     */
    @GetMapping("/getUserWithdrawRecord")
    public ResultInfo getUserWithdrawRecord(@RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return ResultInfo.fail("无效用户查询！");
        }
        return withdrawRecordService.getUserWithdrawRecord(userId, super.buildPage());
    }

    /**
     * 获取自己提现记录
     * @return
     */
    @GetMapping("/getOwnerWithdrawRecord")
    public ResultInfo getOwnerWithdrawRecord(){
        return withdrawRecordService.getUserWithdrawRecord(super.getAccount(),super.buildPage());
    }

    /**
     * 用户提现
     * @return
     */
    @PostMapping("/userWithdraw")
    public ResultInfo userWithdraw(@RequestBody HashMap<String,Object> param){
        String orderId = (String)param.get("orderId");
        String payee = (String)param.get("payee");
        BigDecimal withdrawAmount = BigDecimal.valueOf((double)param.get("withdrawAmount"));
        String payId = (String)param.get("payId");
        String mobileNumber = (String)param.get("mobileNumber");
        return withdrawRecordService.userWithdraw(orderId, payee,withdrawAmount,payId,mobileNumber);
    }

    /**
     * 用户可提现信息
     * @return
     */
    @GetMapping("/getOwnerinfoToWithdraw")
    public ResultInfo getOwnerinfoToWithdraw(){
        JSONObject userInfo = super.getUserInfo();
        return withdrawRecordService.getOwnerinfoToWithdraw(userInfo);
    }

    /**
     * 判断用户是否可
     * @return
     */
    @GetMapping("/userCanWithdraw")
    public ResultInfo userCanWithdraw(){
        return withdrawRecordService.userCanWithdraw(super.getAccount());
    }
}

