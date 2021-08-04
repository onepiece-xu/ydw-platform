package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ydw.admin.dao.WithdrawRecordMapper;
import com.ydw.admin.model.db.UserInfo;
import com.ydw.admin.model.db.WithdrawRecord;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.WithdrawSummaryVO;
import com.ydw.admin.model.vo.WithdrawVO;
import com.ydw.admin.service.IUserInfoService;
import com.ydw.admin.service.IWithdrawRecordService;
import com.ydw.admin.service.IYdwPlatformService;
import com.ydw.admin.service.IYdwRechargeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private WithdrawRecordMapper withdrawRecordMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IYdwRechargeService ydwRechargeService;

    @Autowired
    private IYdwPlatformService ydwPlatformService;

    @Value("${sms.appId}")
    private int smsAppId;

    @Value("${sms.appKey}")
    private String smsAppKey;

    @Value("${sms.sign}")
    private String smsSign;

    @Value("${sms.templateIds.apply_withdraw_success}")
    private int apply_withdraw_success;

    @Value("${sms.templateIds.apply_withdraw_fail}")
    private int apply_withdraw_fail;

    @Override
    public ResultInfo getUserWithdrawSummary(String userId) {
        WithdrawSummaryVO vo = withdrawRecordMapper.getUserWithdrawSummary(userId);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getUserWithdrawRecord(Page buildPage,String userId, Date beginDate, Date endDate) {
        QueryWrapper<WithdrawRecord> qw = new QueryWrapper<>();
        qw.eq("payee", userId);
        if (beginDate != null){
            qw.gt("create_time",beginDate);
        }
        if (endDate != null){
            qw.lt("create_time",endDate);
        }
        Page page = super.page(buildPage, qw);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getWithdrawRecord(Page buildPage, String search, Date beginDate, Date endDate, Integer status) {
        IPage<WithdrawVO> page = withdrawRecordMapper.getWithdrawRecord(buildPage, search, beginDate, endDate,status);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo approvalWithdraw(WithdrawRecord withdrawRecord) {
        if (StringUtils.isBlank(withdrawRecord.getId()) || null == withdrawRecord.getStatus()){
            return ResultInfo.fail("提交参数不正确");
        }
        WithdrawRecord record = getById(withdrawRecord.getId());
        if (record == null || record.getStatus() != 0){
            return ResultInfo.fail("无此申请或此申请状态不是申请中！");
        }
        UserInfo info = userInfoService.getById(record.getPayee());
        if (info.getBalance().compareTo(record.getWithdrawAmount()) < 0){
            return ResultInfo.fail("用户余额不足不允许提现！");
        }
        record.setStatus(withdrawRecord.getStatus());
        record.setRemark(withdrawRecord.getRemark());
        record.setApprovalTime(new Date());
        if (withdrawRecord.getStatus() == 2){
            //发短信和站内信
            SmsSingleSender ssender = new SmsSingleSender(smsAppId, smsAppKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            try {
                ArrayList<String> par = new ArrayList<>();
                SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                par.add(s.format(record.getCreateTime()));
                par.add(record.getWithdrawAmount().toString());
                SmsSingleSenderResult result = ssender.sendWithParam("86", info.getMobileNumber(), apply_withdraw_success, par, smsSign, "", "");
                logger.info("发送短信返回结果！{}",JSON.toJSONString(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String templateIdJson = ydwPlatformService.getTemplateId("apply_withdraw_success");
            String templateId = JSON.parseObject(templateIdJson).getString("data");
            HashMap<String,Object> param = new HashMap<>();
            param.put("userId",info.getId());
            param.put("templateId",templateId);
            HashMap<String,Object> msgData = new HashMap<>();
            msgData.put("date",record.getCreateTime());
            msgData.put("amount",record.getWithdrawAmount());
            param.put("data",msgData);
            logger.info("新增一条站内信！user={},templateId={}", info.getId(), templateId);
            ydwPlatformService.postMessage(param);
            param = new HashMap<>();
            param.put("orderId",record.getId());
            param.put("payee",record.getPayee());
            param.put("withdrawAmount",record.getWithdrawAmount());
            param.put("payId",record.getPayId());
            param.put("mobileNumber",info.getMobileNumber());
            ResultInfo s = ydwRechargeService.userWithdraw(param);
            if (s.getCode() == 200){
                record.setStatus(4);
                record.setOrderNum((String)s.getData());
            }else{
                record.setStatus(3);
            }
        }else{
            SmsSingleSender ssender = new SmsSingleSender(smsAppId, smsAppKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            try {
                ArrayList<String> par = new ArrayList<>();
                SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
                par.add(s.format(record.getCreateTime()));
                par.add(record.getWithdrawAmount().toString());
                SmsSingleSenderResult result = ssender.sendWithParam("86", info.getMobileNumber(), apply_withdraw_fail, par, smsSign, "", "");
                logger.info("发送短信返回结果！{}",JSON.toJSONString(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String templateIdJson = ydwPlatformService.getTemplateId("apply_withdraw_fail");
            String templateId = JSON.parseObject(templateIdJson).getString("data");
            HashMap<String,Object> param = new HashMap<>();
            param.put("userId",info.getId());
            param.put("templateId",templateId);
            HashMap<String,Object> msgData = new HashMap<>();
            msgData.put("date",record.getCreateTime());
            msgData.put("amount",record.getWithdrawAmount());
            param.put("data",msgData);
            logger.info("新增一条站内信！user={},templateId={}", info.getId(), templateId);
            ydwPlatformService.postMessage(param);
        }
        updateById(record);
        return ResultInfo.success();
    }
}
