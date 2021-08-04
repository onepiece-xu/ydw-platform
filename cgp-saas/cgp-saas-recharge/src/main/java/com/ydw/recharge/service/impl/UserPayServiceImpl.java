package com.ydw.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ydw.recharge.dao.UserPayMapper;
import com.ydw.recharge.model.constants.Constant;
import com.ydw.recharge.model.db.UserPay;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.UserPayVO;
import com.ydw.recharge.service.IUserPayService;
import com.ydw.recharge.util.RedisUtil;
import com.ydw.recharge.util.SequenceGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户支付绑定 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class UserPayServiceImpl extends ServiceImpl<UserPayMapper, UserPay> implements IUserPayService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultInfo bindUserPay(UserPayVO userPayVO) {
        Object validateCode = redisUtil.get(Constant.PREFIX_BINDPAYCODE_SESSION + userPayVO.getMobileNumber());
        if (validateCode != null && !validateCode.equals(userPayVO.getValidateCode())){
            return ResultInfo.fail("验证码不正确或者已失效！");
        }
        UserPay userPay = new UserPay();
        if (StringUtils.isNotBlank(userPayVO.getPayee())){
            QueryWrapper<UserPay> qw = new QueryWrapper<>();
            qw.eq("payee", userPayVO.getPayee());
            qw.eq("valid",true);
            int count = super.count(qw);
            if (count > 0){
                userPay.setIsdefault(false);
            }else {
                userPay.setIsdefault(true);
            }
        }
        userPay.setId(SequenceGenerator.sequence());
        userPay.setPayAccount(userPayVO.getPayAccount());
        userPay.setPayee(userPayVO.getPayee());
        userPay.setPayeeName(userPayVO.getPayeeName());
        userPay.setPayType(userPayVO.getPayType());
        userPay.setCreateTime(new Date());
        boolean save = save(userPay);
        if (save){
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
    }

    @Override
    public ResultInfo unbindUserPay(UserPayVO userPayVO) {
        Object validateCode = redisUtil.get(Constant.PREFIX_UNBINDPAYCODE_SESSION + userPayVO.getMobileNumber());
        if (validateCode != null && !validateCode.equals(userPayVO.getValidateCode())){
            return ResultInfo.fail("验证码不正确或者已失效！");
        }
        UpdateWrapper<UserPay> uw = new UpdateWrapper<>();
        uw.eq("payee", userPayVO.getPayee());
        uw.eq("pay_account", userPayVO.getPayAccount());
        uw.set("valid", false);
        boolean update = update(uw);
        if (update){
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
    }

    @Override
    @Transactional
    public ResultInfo updateBindUserPay(UserPayVO userPayVO) {
        Object validateCode = redisUtil.get(Constant.PREFIX_UNBINDPAYCODE_SESSION + userPayVO.getMobileNumber());
        if (validateCode != null && !validateCode.equals(userPayVO.getValidateCode())){
            return ResultInfo.fail("验证码不正确或者已失效！");
        }
        UpdateWrapper<UserPay> uw = new UpdateWrapper<>();
        uw.eq("id", userPayVO.getId());
        uw.eq("payee", userPayVO.getPayee());
        uw.set("valid",false);
        update(uw);
        UserPay userPay = new UserPay();
        userPay.setPayType(1);
        userPay.setCreateTime(new Date());
        userPay.setPayeeName(userPayVO.getPayeeName());
        userPay.setPayee(userPayVO.getPayee());
        userPay.setPayAccount(userPayVO.getPayAccount());
        userPay.setId(SequenceGenerator.sequence());
        userPay.setIsdefault(true);
        userPay.setValid(true);
        save(userPay);
        uw.set("payee_Name",userPayVO.getPayeeName());
        uw.set("pay_account", userPayVO.getPayAccount());
        uw.set("pay_type", userPayVO.getPayType());
        update(uw);
        return ResultInfo.success("修改成功！",userPay.getId());
    }

    @Override
    public ResultInfo getBindValidateCode(String mobileNumber) {
        String key = Constant.PREFIX_BINDPAYCODE_SESSION;
        int templateId = 819854;//模板 ID`729402`只是示例
        try {
            // 生成的6位数赋值验证码
            String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";
            // 分钟
            int expriseTime = 5;
            redisUtil.set(key + mobileNumber, strTemp, expriseTime * 60);
            // 数组具体的元素个数和模板中变量个数必须一致
            // 比如你模板中需要填写验证码和有效时间，{1}，{2}
            // 那你这里的参数就应该填两个
            String[] params = {strTemp, String.valueOf(expriseTime)};
            SmsSingleSender ssender = new SmsSingleSender(1400349137, "125873d60b2cee73ca4161ba2fb74edb");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobileNumber, templateId, params, "易点玩云游戏", "", "");
            logger.info("发送短信返回结果！{}",result.toString());
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUpdateBindValidateCode(String mobileNumber) {
        String key = Constant.PREFIX_UNBINDPAYCODE_SESSION;
        int templateId = 820030;//模板 ID`729402`只是示例
        try {
            // 生成的6位数赋值验证码
            String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";
            // 分钟
            int expriseTime = 5;
            redisUtil.set(key + mobileNumber, strTemp, expriseTime * 60);
            // 数组具体的元素个数和模板中变量个数必须一致
            // 比如你模板中需要填写验证码和有效时间，{1}，{2}
            // 那你这里的参数就应该填两个
            String[] params = {strTemp, String.valueOf(expriseTime)};
            SmsSingleSender ssender = new SmsSingleSender(1400349137, "125873d60b2cee73ca4161ba2fb74edb");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobileNumber, templateId, params, "易点玩云游戏", "", "");
            logger.info("发送短信返回结果！{}",result.toString());
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserPay(String userId) {
        QueryWrapper<UserPay> qw = new QueryWrapper<>();
        qw.eq("payee", userId);
        qw.eq("valid",true);
        List<UserPay> list = super.list(qw);
        return ResultInfo.success(list);
    }
}
