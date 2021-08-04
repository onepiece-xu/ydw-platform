package com.ydw.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.ydw.admin.model.vo.Msg;
import com.ydw.admin.model.vo.MsgVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */

@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends  BaseController {
    @Autowired
    private IUserInfoService iUserInfoService;

    /**
     * 查所有用户信息
     *
     * @param mobileNumber
     * @param search
     * @return
     */
    @GetMapping("/getUserList")
    public ResultInfo getUserList(@RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String search) {
        return iUserInfoService.getUserList(mobileNumber, search, buildPage());
    }

    /**
     * 查询用户分销上级
     *
     * @param userId
     * @return
     */
    @GetMapping("/getDistributionRecommender")
    public ResultInfo getDistributionRecommender(@RequestParam String userId) {
        return iUserInfoService.getDistributionRecommender(userId);
    }

    /**
     * 查询用户分销下级
     *
     * @param userId
     * @return
     */
    @GetMapping("/getDistributionInferior")
    public ResultInfo getDistributionInferior(@RequestParam String userId) {
        return iUserInfoService.getDistributionInferior(userId, buildPage());
    }

    /**
     * 查所分销用户信息
     *
     * @param search
     * @return
     */
    @GetMapping("/getDistributionUserList")
    public ResultInfo getDistributionUserList(@RequestParam(required = false) String search) {
        return iUserInfoService.getDistributionUserList(search, buildPage());
    }

    /**
     * 获取用户绑定的支付信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserPay")
    public ResultInfo getUserPay(@RequestParam String userId) {
        return iUserInfoService.getUserPay(userId);
    }

    /**
     * 查所有用户信息
     *
     * @param mobileNumber
     * @param search
     * @return
     */
    @GetMapping("/getOnlineList")
    public ResultInfo getOnlineList(@RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String search, @RequestParam(required = false) Integer client) {
        return iUserInfoService.getOnlineList(mobileNumber, search, client, buildPage());
    }

    /**
     * 根据渠道查在线用户信息
     *
     * @param enterpriseId
     * @param search
     * @return
     */
    @GetMapping("/getOnlineListByEnterprise")
    public ResultInfo getOnlineListByEnterprise(@RequestParam String enterpriseId,
                                                @RequestParam(required = false) String search,
                                                @RequestParam(required = false) Integer client) {
        return iUserInfoService.getOnlineListByEnterprise(enterpriseId, search, client, buildPage());
    }

    /**
     * 根据渠道查在线用户信息
     *
     * @param enterpriseId
     * @return
     */
    @GetMapping("/getNewRegisterByEnterprise")
    public ResultInfo getNewRegisterByEnterprise(@RequestParam String enterpriseId,
                                                 @RequestParam(required = false) String startTime,
                                                 @RequestParam(required = false) String endTime) {
        return iUserInfoService.getNewRegisterByEnterprise(enterpriseId, startTime, endTime, buildPage());
    }

    /**
     * 根据渠道查在线用户信息
     *
     * @param enterpriseId
     * @return
     */
    @GetMapping("/getNewRegisterCountByEnterprise")
    public ResultInfo getNewRegisterCountByEnterprise(@RequestParam String enterpriseId,
                                                      @RequestParam(required = false) String startTime,
                                                      @RequestParam(required = false) String endTime) {
        return iUserInfoService.getNewRegisterCountByEnterprise(enterpriseId, startTime, endTime);
    }

    /**
     * 充值记录查询
     */
    @GetMapping("/getRechargeListByEnterprise")
    public ResultInfo getRechargeListByEnterprise(@RequestParam String enterpriseId,
                                                  @RequestParam(required = false) String search) {
        return iUserInfoService.getRechargeListByEnterprise(enterpriseId, search, buildPage());
    }

    /**
     * 充值记录汇总查询
     */
    @GetMapping("/getRechargeCountByEnterprise")
    public ResultInfo getRechargeCountByEnterprise(@RequestParam String enterpriseId,
                                                   @RequestParam(required = false) String search) {
        return iUserInfoService.getRechargeCountByEnterprise(enterpriseId, search);
    }

    /**
     * 充值记录查询
     */
    @GetMapping("/rechargeList")
    public ResultInfo rechargeList(@RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
                                   @RequestParam(required = false) String search) {
        return iUserInfoService.rechargeList(mobileNumber,startTime,endTime, search, buildPage());
    }

    @GetMapping("/rechargeSummary")
    public ResultInfo rechargeSummary(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,@RequestParam(required = false) String search) {
        return iUserInfoService.rechargeSummary(startTime,endTime,search);
    }

    /**
     * 消费记录查询
     */
    @GetMapping("/chargeList")
    public ResultInfo chargeList(@RequestParam(required = false) Integer client, @RequestParam(required = false) String search) {
        return iUserInfoService.chargeList(client, search, buildPage());
    }

    @GetMapping("/chargeSummary")
    public ResultInfo chargeSummary(@RequestParam(required = false) String search) {
        return iUserInfoService.chargeSummary(search);
    }

    /**
     * 新增用户查询
     */
    @GetMapping("/getNewUserList")
    public ResultInfo getNewUserList(@RequestParam(required = false) String search, @RequestParam String startTime, @RequestParam String endTime) {
        return iUserInfoService.getNewUserList(search, startTime, endTime, buildPage());
    }

    /**
     * 新增用户付费额
     */
    @GetMapping("/getNewUserRecharge")
    public ResultInfo getNewUserRecharge(@RequestParam String startTime, @RequestParam String endTime) {
        return iUserInfoService.getNewUserRecharge(startTime, endTime);
    }

    /**
     * 用户总付费额
     */
    @GetMapping("/getUserTotalRecharge")
    public ResultInfo getUserTotalRecharge(@RequestParam String startTime, @RequestParam String endTime) {
        return iUserInfoService.getUserTotalRecharge(startTime, endTime);
    }

    /**
     * 老用户付费额
     */
    @GetMapping("/getOldUserRecharge")
    public ResultInfo getOldUserRecharge(@RequestParam String startTime, @RequestParam String endTime) {
        return iUserInfoService.getOldUserRecharge(startTime, endTime);
    }

    /**
     * 作废
     * 用户活跃度
     */
    @GetMapping("/getUserActivity")
    public ResultInfo getUserActivity(@RequestParam String startTime, @RequestParam String endTime) {
        return iUserInfoService.getUserActivity(startTime, endTime);
    }

    /**
     * 获取用户余额
     */
    @GetMapping(value = "/getChannelBalanceByEnterprise")
    public ResultInfo getChannelBalanceByEnterprise(@RequestParam String enterpriseId) {
        return iUserInfoService.getChannelBalanceByEnterprise(enterpriseId);
    }

    /**
     * 获取申请提现
     */
    @PostMapping(value = "/applyWithdrawByEnterprise")
    public ResultInfo applyWithdrawByEnterprise(@RequestBody JSONObject jsonObject) {
        String enterpriseId = jsonObject.getString("enterpriseId");
        BigDecimal amount = jsonObject.getBigDecimal("amount");
        return iUserInfoService.applyWithdrawByEnterprise(enterpriseId, amount);
    }

    /**
     * 获取用户提现记录
     */
    @GetMapping(value = "/getWithdrawRecordByEnterprise")
    public ResultInfo getWithdrawRecordByEnterprise(@RequestParam String enterpriseId,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestParam(required = false) String beginDate,
                                                    @RequestParam(required = false) String endDate) {
        return iUserInfoService.getWithdrawRecordByEnterprise(enterpriseId, status, beginDate, endDate, buildPage());
    }

    /**
     * 获取用户绑定支付宝账号
     */
    @GetMapping(value = "/getUserPayByEnterprise")
    public ResultInfo getUserPayByEnterprise(@RequestParam String enterpriseId) {
        return iUserInfoService.getUserPayByEnterprise(enterpriseId);
    }

    /**
     * 绑定支付宝
     */
    @PostMapping(value = "/bindBalancePayByEnterprise")
    public ResultInfo bindBalancePayByEnterprise(@RequestBody JSONObject jsonObject) {
        String enterpriseId = jsonObject.getString("enterpriseId");
        String payAccount = jsonObject.getString("payAccount");
        return iUserInfoService.bindBalancePayByEnterprise(enterpriseId, payAccount);
    }

    /**
     * 给用户添加奖励时长
     */
    @PostMapping("/awardTime")
    public ResultInfo awardTime(@RequestBody HashMap<String, String> params) {
        return iUserInfoService.awardTime(params);
    }

    /**
     * *根据卡类型获取补偿卡
     */
    @GetMapping("/getRechargeCardsByType")
    public ResultInfo getRechargeCardsByType(@RequestParam int type) {
        return iUserInfoService.getRechargeCardsByType(type);
    }

    /**
     * 给用户推消息
     */
    @PostMapping("/sendMessage")
    public ResultInfo sendMessage(@RequestBody MsgVO msg) {
        return iUserInfoService.sendMessage(msg);
    }

    /**
     * 给特定用户补偿用户奖励时长
     */
    @PostMapping("/adminAwardTime")
    public ResultInfo adminAwardTime(@RequestBody HashMap<String, String> params) {
        return iUserInfoService.adminAwardTime(params);
    }

    /**
     * 给所有用户发消息
     */
    @PostMapping("/sendMessageAll")
    public ResultInfo sendMessageAll(@RequestBody MsgVO msg) {
        return iUserInfoService.sendMessageAll(msg);
    }

    /**
     * 非法用户巡检
     */

    @GetMapping(value = "/IllegalUserInspection")
    public ResultInfo IllegalUserInspection() {
        return iUserInfoService.IllegalUserInspection();
    }
}

