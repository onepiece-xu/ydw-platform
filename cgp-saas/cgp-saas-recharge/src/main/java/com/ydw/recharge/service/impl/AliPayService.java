package com.ydw.recharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.util.generic.Client;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.ydw.recharge.model.constants.Constant;
import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.enums.PayTypeEnum;
import com.ydw.recharge.model.vo.CouponCardBagVO;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.*;
import com.ydw.recharge.util.RedisUtil;
import com.ydw.recharge.util.SequenceGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("alipayService")
public class AliPayService implements IPayService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRechargeCardService rechargeCardService;

    @Autowired
    private IRechargeService rechargeService;

    @Autowired
    private ICouponBagService couponBagService;

    @Autowired
    private RedisUtil redisUtil;

    //检查用户是否有未支付的订单，有则直接关闭订单
    @Override
    public void doUncompletedOrders(String account){
        String key = MessageFormat.format(Constant.USER_UNCOMPLETED_ORDER, account);
        Set<Object> objects = redisUtil.sGet(key);
        if (objects == null || objects.isEmpty()){
            return;
        }
        for (Object object : objects) {
            String rechargeId = (String)object;
            rechargeService.doUncompletedOrder(rechargeId);
        }
    }

    @Override
    public void doUncompletedOrder(Recharge recharge) {
        try {
            AlipayTradeQueryResponse query = Factory.Payment.Common().query(recharge.getId());
            logger.info("获取支付宝订单{}返回结果：{}", recharge.getId(), JSON.toJSONString(query));
            // 处理响应或异常
            if (ResponseChecker.success(query)) {
                AlipayTradeCloseResponse closeResponse = Factory.Payment.Common().close(recharge.getId());
                logger.info("关闭支付宝订单{}返回结果：{}！", recharge.getId(), JSON.toJSONString(query));// 处理响应或异常
                if (ResponseChecker.success(closeResponse)) {
                    rechargeService.closeOrder(recharge);
                }
            }else {
                logger.info("获取支付宝订单{}详情失败！", recharge.getId());
                rechargeService.closeOrder(recharge);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getPayUrl(String cardId, String account, String bagId) {
        //检查是否有未支付的订单
        doUncompletedOrders(account);
        String qrcode = null;
        RechargeCard rechargeCard = rechargeCardService.getById(cardId);
        if(rechargeCard != null && rechargeCard.getValid()){
            //支付宝支付
            try {
                String orderNum = SequenceGenerator.sequence();
                BigDecimal finalCost = rechargeCard.getFinalCost();
                if (StringUtils.isNotBlank(bagId)){
                    CouponCardBagVO couponCardBagVO = couponBagService.preConsumeCouponBag(cardId, bagId);
                    if (couponCardBagVO != null){
                        if (couponCardBagVO.getPromotionType() == 0){
                            //折扣
                            finalCost = finalCost.multiply(couponCardBagVO.getDiscount()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        }else if (couponCardBagVO.getPromotionType() == 1){
                            //减免
                            finalCost = finalCost.subtract(couponCardBagVO.getReduction()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        }
                    }else{
                        bagId = null;
                    }
                }
                // 发起API调用（创建当面付收款二维码）
                AlipayTradePrecreateResponse alipayResponse = Factory.Payment.FaceToFace()
                        .preCreate(rechargeCard.getName(), orderNum, finalCost.toString());
                // 处理响应或异常
                if (ResponseChecker.success(alipayResponse)) {
                    qrcode = alipayResponse.qrCode;
                    rechargeService.createRecharge(alipayResponse.outTradeNo, null, account, rechargeCard, PayTypeEnum.alipay.code, bagId, finalCost);
                    logger.info("获取支付宝二维码链接成功！{}", qrcode);
                } else {
                    logger.error("调用失败，原因：" + alipayResponse.msg + "，" + alipayResponse.subMsg);
                }
            } catch (Exception e) {
                System.err.println("调用遭遇异常，原因：" + e.getMessage());
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return qrcode;
    }

    @Override
    public ResultInfo createOrder(String cardId, String account, String bagId) {
        //检查是否有未支付的订单
        doUncompletedOrders(account);
        RechargeCard rechargeCard = rechargeCardService.getById(cardId);
        if(rechargeCard != null && rechargeCard.getValid()){
            //支付宝支付
            try {
                String orderNum = SequenceGenerator.sequence();
                BigDecimal finalCost = rechargeCard.getFinalCost();
                if (StringUtils.isNotBlank(bagId)){
                    CouponCardBagVO couponCardBagVO = couponBagService.preConsumeCouponBag(cardId,bagId);
                    if (couponCardBagVO != null){
                        if (couponCardBagVO.getPromotionType() == 0){
                            //折扣
                            finalCost = finalCost.multiply(couponCardBagVO.getDiscount()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        }else if (couponCardBagVO.getPromotionType() == 1){
                            //减免
                            finalCost = finalCost.subtract(couponCardBagVO.getReduction()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        }
                    }else{
                        bagId = null;
                    }
                }
                // 生成app支付订单
                AlipayTradeAppPayResponse alipayResponse = Factory.Payment.App()
                        .pay(rechargeCard.getName(), orderNum, finalCost.toString());
                // 处理响应或异常
                if (ResponseChecker.success(alipayResponse)) {
                    String order = alipayResponse.body;
                    rechargeService.createRecharge(orderNum,null,account,rechargeCard, PayTypeEnum.alipay.code, bagId, finalCost);
                    logger.info("获取创建支付宝订单成功！{}", order);
                    return ResultInfo.success("订单创建成功！",order);
                } else {
                    logger.error("创建订单调用失败！{}" , alipayResponse.body);
                }

            } catch (Exception e) {
                logger.error("调用遭遇异常，原因：" + e.getMessage());
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return ResultInfo.fail();
    }

    @Override
    public String payNotify(Map<String, String> map) {
        try {
            boolean aBoolean = Factory.Payment.Common().verifyNotify(map);
            if(aBoolean) {
                String out_trade_no = map.get("out_trade_no");
                String trade_no = map.get("trade_no");
                rechargeService.successRecharge(out_trade_no, trade_no);
                return JSON.toJSONString(ResultInfo.success());
            }else{
                logger.error("此回调验证失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultInfo transfer(Map<String, Object> map) {
        try {
            Client generic = Factory.Util.Generic();
            HashMap<String,Object> bizParams = new HashMap<>();
            //商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一。
            bizParams.put("out_biz_no",map.get("orderId"));
            //订单总金额，单位为元，精确到小数点后两位，STD_RED_PACKET产品取值范围[0.01,100000000]；
            //TRANS_ACCOUNT_NO_PWD产品取值范围[0.1,100000000]
            bizParams.put("trans_amount",map.get("withdrawAmount"));
            //业务产品码，单笔无密转账到支付宝账户固定为:TRANS_ACCOUNT_NO_PWD；单笔无密转账到银行卡固定为:TRANS_BANKCARD_NO_PWD;收发现金红包固定为:STD_RED_PACKET；
            bizParams.put("product_code","TRANS_ACCOUNT_NO_PWD");
            //描述特定的业务场景，可传的参数如下：DIRECT_TRANSFER：单笔无密转账到支付宝/银行卡, B2C现金红包;PERSONAL_COLLECTION：C2C现金红包-领红包
            bizParams.put("biz_scene","DIRECT_TRANSFER");
            //转账业务的标题，用于在支付宝用户的账单里显示
            bizParams.put("order_title","易点玩云游戏提现");
            //收款方信息
            HashMap<String,Object> payee_info = new HashMap<>();
            //参与方的唯一标识
            payee_info.put("identity",map.get("payAccount"));
            //参与方的标识类型，目前支持如下类型：1、ALIPAY_USER_ID 支付宝的会员ID2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式
            payee_info.put("identity_type","ALIPAY_LOGON_ID");
            //参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当identity_type=ALIPAY_LOGON_ID时，本字段必填。
            payee_info.put("name",map.get("payeeName"));
            //收款方信息
            bizParams.put("payee_info",payee_info);
            logger.info("本次转账 {} 开始",JSON.toJSONString(bizParams));
            AlipayOpenApiGenericResponse response = generic.execute("alipay.fund.trans.uni.transfer", null, bizParams);
            logger.info("本次转账返回结果为：{}",JSON.toJSONString(response));
            // 处理响应或异常
            if (ResponseChecker.success(response)) {
                String httpBody = response.getHttpBody();
                logger.info("本次转账成功：{}", httpBody);

                return ResultInfo.success("ok",JSON.parseObject(httpBody).getJSONObject("alipay_fund_trans_uni_transfer_response").getString("order_id"));
            } else {
                logger.error("本次转账失败：{}", response.getHttpBody());
                return ResultInfo.fail(response.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.fail();
    }
}
