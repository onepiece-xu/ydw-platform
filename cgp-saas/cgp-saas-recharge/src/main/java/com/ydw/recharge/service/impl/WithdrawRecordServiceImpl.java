package com.ydw.recharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ydw.recharge.dao.UserPayMapper;
import com.ydw.recharge.dao.WithdrawRecordMapper;
import com.ydw.recharge.model.db.UserPay;
import com.ydw.recharge.model.db.WithdrawRecord;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.ToWithdrawInfo;
import com.ydw.recharge.model.vo.WithdrawSummary;
import com.ydw.recharge.model.vo.WithdrawVO;
import com.ydw.recharge.service.*;
import com.ydw.recharge.util.RedisUtil;
import com.ydw.recharge.util.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class WithdrawRecordServiceImpl extends ServiceImpl<WithdrawRecordMapper, WithdrawRecord> implements IWithdrawRecordService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserPayMapper userPayMapper;

    @Resource(name = "alipayService")
    private IPayService alipayService;

    @Resource(name = "wxpayService")
    private IPayService wxpayService;

    @Autowired
    private IUserPayService userPayService;

    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IYdwMessageService ydwMessageService;

    @Autowired
    private IYdwPlatformService ydwPlatformService;

    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;

    @Value("${sms.appId}")
    private int smsAppId;

    @Value("${sms.appKey}")
    private String smsAppKey;

    @Value("${sms.sign}")
    private String smsSign;

    @Value("${sms.templateIds.apply_withdraw}")
    private int apply_withdraw;

    @Value("${sms.templateIds.withdraw_pay_success}")
    private int withdraw_pay_success;

    @Value("${sms.templateIds.withdraw_pay_fail}")
    private int withdraw_pay_fail;

    @Override
    public ResultInfo applyWithraw(WithdrawVO withdrawVO) {
//        Object validateCode = redisUtil.get(Constant.PREFIX_BINDPAYCODE_SESSION + withdrawVO.getMobileNumber());
//        if (validateCode != null && !validateCode.equals(withdrawVO.getValidateCode())){
//            return ResultInfo.fail("验证码不正确或者已失效！");
//        }
        QueryWrapper<WithdrawRecord> qw = new QueryWrapper<>();
        qw.eq("payee", withdrawVO.getPayee());
        qw.eq("status",0);
        int count = super.count(qw);
        if (count > 0){
            return ResultInfo.fail("已有正在提现的订单未完成，请完成后再申请！");
        }
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        withdrawRecord.setCreateTime(new Date());
        withdrawRecord.setId(SequenceGenerator.sequence());
        withdrawRecord.setPayee(withdrawVO.getPayee());
        withdrawRecord.setPayId(withdrawVO.getPayId());
        withdrawRecord.setStatus(0);
        withdrawRecord.setWithdrawAmount(withdrawVO.getWithdrawAmount());
        boolean save = save(withdrawRecord);
        if (save){
            SmsSingleSender ssender = new SmsSingleSender(smsAppId, smsAppKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            try {
                ArrayList<String> par = new ArrayList<>();
                SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                par.add(s.format(withdrawRecord.getCreateTime()));
                par.add(withdrawRecord.getWithdrawAmount().toString());
                SmsSingleSenderResult result = ssender.sendWithParam("86", withdrawVO.getMobileNumber(), apply_withdraw, par, smsSign, "", "");
                logger.info("发送短信返回结果！{}",JSON.toJSONString(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String templateIdJson = ydwPlatformService.getTemplateId("apply_withdraw");
            String templateId = JSON.parseObject(templateIdJson).getString("data");
            HashMap<String,Object> param = new HashMap<>();
            param.put("userId",withdrawVO.getPayee());
            param.put("templateId",templateId);
            HashMap<String,Object> msgData = new HashMap<>();
            msgData.put("date",withdrawRecord.getCreateTime());
            msgData.put("amount",withdrawRecord.getWithdrawAmount().toString());
            param.put("data",msgData);
            logger.info("新增一条站内信！user={},templateId={}", withdrawVO.getPayee(), templateId);
            ydwPlatformService.postMessage(param);
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
    }

    @Override
    public ResultInfo getUserWithdrawRecord(String userId, Page buildPage) {
        WithdrawSummary withdrawSummary = withdrawRecordMapper.getUserWithdrawSummary(userId);
        IPage<WithdrawVO> page = withdrawRecordMapper.getUserWithdrawRecord(buildPage, userId);
        withdrawSummary.setData(page);
        return ResultInfo.success(withdrawSummary);
    }

    @Override
    public ResultInfo userWithdraw(String orderId, String payee, BigDecimal withdrawAmount, String payId, String mobileNumber) {
        QueryWrapper<UserPay> qw = new QueryWrapper<>();
        qw.eq("id", payId);
        qw.eq("payee",payee);
        qw.eq("valid",true);
        UserPay userPay = userPayMapper.selectOne(qw);
        if (userPay == null){
            return ResultInfo.fail("支付方式无效！");
        }
        SmsSingleSender ssender = new SmsSingleSender(smsAppId, smsAppKey);
        if (userPay.getPayType() == 1){
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId",orderId);
            map.put("withdrawAmount",withdrawAmount);
            map.put("payAccount", userPay.getPayAccount());
            map.put("payeeName",userPay.getPayeeName());
            ResultInfo transfer = alipayService.transfer(map);
            WithdrawRecord withdrawRecord = super.getById(orderId);
            if (transfer.getCode() == 200){
                try {
                    ArrayList<String> par = new ArrayList<>();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                    par.add(s.format(withdrawRecord.getCreateTime()));
                    par.add(withdrawRecord.getWithdrawAmount().toString());
                    par.add(userPay.getPayAccount());
                    SmsSingleSenderResult result = ssender.sendWithParam("86", mobileNumber, withdraw_pay_success, par, smsSign, "", "");
                    logger.info("发送短信返回结果！{}",JSON.toJSONString(result));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String templateIdJson = ydwPlatformService.getTemplateId("withdraw_pay_success");
                String templateId = JSON.parseObject(templateIdJson).getString("data");
                HashMap<String,Object> param = new HashMap<>();
                param.put("userId",payee);
                param.put("templateId",templateId);
                HashMap<String,Object> msgData = new HashMap<>();
                msgData.put("date",withdrawRecord.getCreateTime());
                msgData.put("amount",withdrawAmount);
                msgData.put("payAccount",userPay.getPayAccount());
                param.put("data",msgData);
                logger.info("新增一条站内信！user={},templateId={}", payee, templateId);
                ydwPlatformService.postMessage(param);
            }else{
                try {
                    ArrayList<String> par = new ArrayList<>();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                    par.add(s.format(withdrawRecord.getCreateTime()));
                    par.add(withdrawRecord.getWithdrawAmount().toString());
                    par.add(transfer.getMsg());
                    SmsSingleSenderResult result = ssender.sendWithParam("86", mobileNumber, withdraw_pay_fail, par, smsSign, "", "");
                    logger.info("发送短信返回结果！{}",JSON.toJSONString(result));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String templateIdJson = ydwPlatformService.getTemplateId("withdraw_pay_fail");
                String templateId = JSON.parseObject(templateIdJson).getString("data");
                HashMap<String,Object> param = new HashMap<>();
                param.put("userId",payee);
                param.put("templateId",templateId);
                HashMap<String,Object> msgData = new HashMap<>();
                msgData.put("date",withdrawRecord.getCreateTime());
                msgData.put("amount",withdrawAmount);
                msgData.put("errorCode",transfer.getMsg());
                param.put("data",msgData);
                logger.info("新增一条站内信！user={},templateId={}", payee, templateId);
                ydwPlatformService.postMessage(param);
            }
            return transfer;
        }else{
            return wxpayService.transfer(null);
        }
    }

    @Override
    public ResultInfo getOwnerinfoToWithdraw(JSONObject userInfo) {
        ToWithdrawInfo toWithdrawInfo = new ToWithdrawInfo();
        toWithdrawInfo.setBalance(userInfo.getBigDecimal("balance"));
        String userId = userInfo.getString("id");
        List<UserPay> data = (List<UserPay>) userPayService.getUserPay(userId).getData();
        UserPay userPay = data.stream().filter((t) -> t.getIsdefault()).findFirst().get();
        toWithdrawInfo.setPayAccount(userPay.getPayAccount());
        toWithdrawInfo.setPayeeName(userPay.getPayeeName());
        toWithdrawInfo.setPayId(userPay.getId());
        return ResultInfo.success(toWithdrawInfo);
    }

    @Override
    public ResultInfo userCanWithdraw(String userAccount) {
        //有无绑定
        List<UserPay> data = (List<UserPay>) userPayService.getUserPay(userAccount).getData();
        if (data == null || data.size() == 0){
            return ResultInfo.success(false);
        }
        //有无余额 TODO
        //有无申请中的
        QueryWrapper<WithdrawRecord> qw = new QueryWrapper<>();
        qw.eq("payee", userAccount);
        qw.eq("status", 0);
        int count = super.count(qw);
        if (count > 0){
            return ResultInfo.success(false);
        }else{
            return ResultInfo.success(true);
        }
    }
}
