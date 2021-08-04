package com.ydw.recharge.service.impl;

import com.ydw.recharge.config.WXPayConfig;
import com.ydw.recharge.model.constants.Constant;
import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.enums.PayTypeEnum;
import com.ydw.recharge.model.vo.CouponCardBagVO;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICouponBagService;
import com.ydw.recharge.service.IPayService;
import com.ydw.recharge.service.IRechargeCardService;
import com.ydw.recharge.service.IRechargeService;
import com.ydw.recharge.util.HttpUtil;
import com.ydw.recharge.util.IpUtil;
import com.ydw.recharge.util.RedisUtil;
import com.ydw.recharge.util.SequenceGenerator;
import com.ydw.recharge.util.wxpay.WXPayConstants;
import com.ydw.recharge.util.wxpay.WXPayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("wxpayService")
public class WXPayService implements IPayService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRechargeCardService rechargeCardService;

    @Autowired
    private IRechargeService rechargeService;

    @Autowired
    private WXPayConfig wxpayConfig;

    @Autowired
    private ICouponBagService couponBagService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void doUncompletedOrders(String account) {
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
    public void doUncompletedOrder(Recharge recharge){
        try {
            SortedMap<String,String> packageParams = new TreeMap<>();
            packageParams.put("appid", wxpayConfig.getAppId());
            packageParams.put("mch_id", wxpayConfig.getMchId());
            packageParams.put("out_trade_no", recharge.getId());
            packageParams.put("nonce_str", WXPayUtil.generateNonceStr());
            String requestXML = WXPayUtil.generateSignedXml(packageParams, wxpayConfig.getApiKey());
            String resXml = HttpUtil.doPostXml(WXPayConstants.DOMAIN_API + WXPayConstants.ORDERQUERY_URL_SUFFIX,
                    requestXML);
            Map<String,String> map = WXPayUtil.xmlToMap(resXml);
            logger.info("获取微信订单{}返回结果：{}",recharge.getId(), map.toString());
            String returnCode = map.get("return_code");
            if ("SUCCESS".equalsIgnoreCase(returnCode)){
                resXml = HttpUtil.doPostXml(WXPayConstants.DOMAIN_API + WXPayConstants.CLOSEORDER_URL_SUFFIX,
                        requestXML);
                map = WXPayUtil.xmlToMap(resXml);
                logger.info("关闭微信订单{}返回结果：{}",recharge.getId(), map.toString());
                returnCode = map.get("return_code");
                if ("SUCCESS".equalsIgnoreCase(returnCode)){
                    rechargeService.closeOrder(recharge);
                }
            }else{
                logger.info("获取微信订单{}详情失败！", recharge.getId());
                rechargeService.closeOrder(recharge);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getPayUrl(String rechargeCardId, String account, String bagId) {
        //检查是否有未支付的订单
        doUncompletedOrders(account);
        String codeUrl = null;
        RechargeCard rechargeCard = rechargeCardService.getById(rechargeCardId);
        if(rechargeCard != null && rechargeCard.getValid()){
            BigDecimal finalCost = rechargeCard.getFinalCost();
            if (StringUtils.isNotBlank(bagId)){
                CouponCardBagVO couponCardBagVO = couponBagService.preConsumeCouponBag(rechargeCardId, bagId);
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

            SortedMap<String,String> packageParams = new TreeMap<>();
            packageParams.put("appid", wxpayConfig.getAppId());
            packageParams.put("mch_id", wxpayConfig.getMchId());
            packageParams.put("nonce_str", WXPayUtil.generateNonceStr());
            packageParams.put("body", rechargeCard.getName());  //（调整为自己的名称）
            String tradeNo = SequenceGenerator.sequence();
            packageParams.put("out_trade_no", tradeNo);
            packageParams.put("total_fee", String.valueOf(finalCost
                    .multiply(new BigDecimal(100)).intValue())); //价格的单位为分
            packageParams.put("spbill_create_ip", IpUtil.getRequestIp());
            packageParams.put("notify_url", wxpayConfig.getNotifyUrl());
            packageParams.put("trade_type", "NATIVE");
            try {
                logger.info("开始获取微信预支付订单！{}",packageParams.toString());
                String requestXML = WXPayUtil.generateSignedXml(packageParams, wxpayConfig.getApiKey());
                String resXml = HttpUtil.doPostXml(WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX,
                        requestXML);
                Map<String,String> map = WXPayUtil.xmlToMap(resXml);
                logger.info("获取微信预支付订单返回值！{}",map.toString());
                codeUrl = map.get("code_url");
                if(StringUtils.isNotBlank(codeUrl)){
                    logger.info("获取微信二维码链接成功！{}", codeUrl);
                }
                rechargeService.createRecharge(tradeNo,null,account,rechargeCard, PayTypeEnum.wxpay.code, bagId, finalCost);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("获取微信预支付订单失败！{}",e.getMessage());
            }
        }
        return codeUrl;
    }

    @Override
    public ResultInfo createOrder(String cardId, String account, String bagId) {
        //检查是否有未支付的订单
        doUncompletedOrders(account);
        RechargeCard rechargeCard = rechargeCardService.getById(cardId);
        if(rechargeCard != null && rechargeCard.getValid()){

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

            SortedMap<String,String> packageParams = new TreeMap<>();
            packageParams.put("appid", wxpayConfig.getAppId1());
            packageParams.put("mch_id", wxpayConfig.getMchId());
            packageParams.put("nonce_str", WXPayUtil.generateNonceStr());
            packageParams.put("body", rechargeCard.getName());  //（调整为自己的名称）
            String tradeNo = SequenceGenerator.sequence();
            packageParams.put("out_trade_no", tradeNo);
            packageParams.put("total_fee", String.valueOf(finalCost
                    .multiply(new BigDecimal(100)).intValue())); //价格的单位为分
            packageParams.put("spbill_create_ip", IpUtil.getRequestIp());
            packageParams.put("notify_url", wxpayConfig.getNotifyUrl());
            packageParams.put("trade_type", "APP");
            try {
                String requestXML = WXPayUtil.generateSignedXml(packageParams, wxpayConfig.getApiKey());
                String resXml = HttpUtil.doPostXml(WXPayConstants.DOMAIN_API + WXPayConstants.UNIFIEDORDER_URL_SUFFIX,
                        requestXML);
                Map<String,String> map = WXPayUtil.xmlToMap(resXml);
                map.put("partnerid",wxpayConfig.getMchId());
                rechargeService.createRecharge(tradeNo,null,account,rechargeCard, PayTypeEnum.wxpay.code, bagId, finalCost);
                return ResultInfo.success("创建订单成功！",map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultInfo.fail();
    }

    @Override
    public String payNotify(Map<String, String> map) {
        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        try {
            if (WXPayUtil.isPayResultNotifySignatureValid(map, wxpayConfig.getApiKey())) {
                String returnCode = map.get("return_code");  //状态
                String outTradeNo = map.get("out_trade_no");//商户订单号
                String transactionId = map.get("transaction_id");
                if (returnCode.equals("SUCCESS")) {
                    if (StringUtils.isNotBlank(outTradeNo)) {
                        logger.info("微信手机支付回调成功,订单号:{}", outTradeNo);
                        xmlBack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                        rechargeService.successRecharge(outTradeNo,transactionId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlBack;
    }

    @Override
    public ResultInfo transfer(Map<String, Object> map) {
        return null;
    }
}
