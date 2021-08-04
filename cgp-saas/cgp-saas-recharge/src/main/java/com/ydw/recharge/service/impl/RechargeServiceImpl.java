package com.ydw.recharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.RechargeMapper;
import com.ydw.recharge.model.constants.Constant;
import com.ydw.recharge.model.db.DistributionAward;
import com.ydw.recharge.model.db.DistributionAwardRule;
import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.enums.MessageTopicEnum;
import com.ydw.recharge.model.enums.OrderStatusEnum;
import com.ydw.recharge.model.enums.PayTypeEnum;
import com.ydw.recharge.model.vo.MsgVO;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.*;
import com.ydw.recharge.util.RedisUtil;
import com.ydw.recharge.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 充值记录表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-09-24
 */
@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements IRechargeService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRechargeCardService rechargeCardService;

	@Autowired
	private ICardBagService cardBagService;

	@Autowired
	private ICouponBagService couponBagService;

	@Autowired
	private IDistributionAwardService distributionAwardService;

	@Autowired
	private IDistributionAwardRuleService distributionAwardRuleService;

	@Autowired
	private IYdwMessageService ydwMessageService;

    @Autowired
    private IYdwPlatformService ydwPlatformService;

    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name = "alipayService")
    private IPayService alipayService;

    @Resource(name = "wxpayService")
    private IPayService wxpayService;

    /**
     * 创建充值记录
     *
     * @param id
     * @param orderNum
     * @param account
     * @param cardId
     * @param payType
     */
    @Override
    public Recharge createRecharge(String id, String orderNum, String account, String cardId, int payType, String couponId, BigDecimal finalCost) {
        RechargeCard rechargeCard = rechargeCardService.getById(cardId);
        return createRecharge(id,orderNum,account,rechargeCard,payType,couponId, finalCost);
    }

    /**
     * 创建充值记录
     *
     * @param id
     * @param orderNum
     * @param account
     * @param card
     * @param payType
     */
    @Override
    public Recharge createRecharge(String id, String orderNum, String account, RechargeCard card, int payType, String couponBagId, BigDecimal finalCost) {
        Recharge recharge = new Recharge();
        recharge.setAccount(account);
        recharge.setCost(card.getFinalCost());
        recharge.setFinalCost(finalCost);
        recharge.setCreateTime(new Date());
        recharge.setOrderNum(orderNum);
        recharge.setId(id);
        recharge.setPayType(payType);
        recharge.setCardId(card.getId());
        recharge.setCouponBagId(couponBagId);
        recharge.setStatus(OrderStatusEnum.unpay.code);
        recharge.setSubject(card.getName());
        save(recharge);
        redisUtil.sSet(MessageFormat.format(Constant.USER_UNCOMPLETED_ORDER, account), id);
        return recharge;
    }

    /**
     * 充值成功
     *
     * @param id
     * @param orderNum
     */
    @Override
    public void successRecharge(String id, String orderNum) {
        //充值卡
        QueryWrapper<Recharge> qw = new QueryWrapper<>();
        qw.eq("id",id);
        Recharge recharge = this.getById(id);
        if(recharge == null){
            logger.error("此充值记录不存在！id={}，order_num={}",id, orderNum);
            return;
        }
        if(recharge.getStatus() != OrderStatusEnum.unpay.code){
            logger.error("此充值记录不是未支付状态！id={}，order_num={}，status={}",id, orderNum, recharge.getStatus());
            return;
        }
        if (StringUtils.isNotBlank(recharge.getCouponBagId())){
            couponBagService.consumeCouponBag(recharge.getCouponBagId());
        }
        Recharge entity = new Recharge();
        if(StringUtils.isNotBlank(orderNum)){
            entity.setOrderNum(orderNum);
        }
        entity.setUpdateTime(new Date());
        entity.setStatus(OrderStatusEnum.payed.code);
        update(entity,qw);
        cardBagService.createCardBag(recharge.getAccount(),recharge.getCardId());
        redisUtil.setRemove(MessageFormat.format(Constant.USER_UNCOMPLETED_ORDER, recharge.getAccount()),id);
        //充值奖励 TODO
        QueryWrapper<DistributionAwardRule> distributionAwardRuleQw = new QueryWrapper<>();
        distributionAwardRuleQw.eq("event",2);
        DistributionAwardRule one = distributionAwardRuleService.getOne(distributionAwardRuleQw);
        if (one != null){
            ResultInfo userRecommender = ydwAuthenticationService.getUserRecommender(recharge.getAccount());
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(userRecommender.getData()));
            if (jsonObject != null){
                BigDecimal amount = recharge.getCost().multiply(one.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
                HashMap<String, Object> param = new HashMap<>();
                param.put("userId", jsonObject.getString("id"));
                param.put("amount",amount);
                ydwAuthenticationService.userDistributionAward(param);
                DistributionAward distributionAward = new DistributionAward();
                distributionAward.setAmount(amount);
                distributionAward.setContribution(recharge.getAccount());
                distributionAward.setCreateTime(new Date());
                distributionAward.setEvent("2");
                distributionAward.setId(SequenceGenerator.sequence());
                distributionAward.setIncome(jsonObject.getString("id"));
                distributionAward.setValid(true);
                distributionAwardService.save(distributionAward);
            }
        }
        //发送消息
        MsgVO msg = new MsgVO();
        HashMap<String,Object> m = new HashMap<>();
        m.put("orderNum",orderNum);
        m.put("status",OrderStatusEnum.payed.code);
        msg.setData(JSON.toJSONString(m));
        String result = ydwAuthenticationService.getUserOnlineTokens(recharge.getAccount());
        JSONArray data = JSON.parseObject(result).getJSONArray("data");
        List<String> strings = data.toJavaList(String.class);
        msg.setReceivers(strings);
        msg.setType(MessageTopicEnum.RECAHRGE.getTopic());
        ydwMessageService.sendMsg(msg);
        //发送站内信
        String templateIdJson = ydwPlatformService.getTemplateId("payed");
        String templateId = JSON.parseObject(templateIdJson).getString("data");
        HashMap<String,Object> param = new HashMap<>();
        param.put("userId",recharge.getAccount());
        param.put("templateId",templateId);
        HashMap<String,Object> msgData = new HashMap<>();
        msgData.put("cardName",recharge.getSubject());
        param.put("data",msgData);
        logger.info("新增一条站内信！user={},templateId={}", recharge.getAccount(), templateId);
        ydwPlatformService.postMessage(param);
    }

    /**
     * 签到获取充值
     *
     * @param userId
     * @throws
     * @author xulh 2020/10/14 13:35
     **/
    @Override
    public ResultInfo signRecharge(String userId) {
        RechargeCard card = rechargeCardService.getSignCard();
        cardBagService.createCardBag(userId,card);
        return ResultInfo.success();
    }

    /**
     * 获取用户充值记录
     *
     * @param userId
     * @throws
     * @author xulh 2020/10/14 13:48
     **/
    @Override
    public ResultInfo getUserRechargeRecord(String userId) {
        QueryWrapper<Recharge> qw = new QueryWrapper<>();
        qw.eq("status",OrderStatusEnum.payed.code);
        qw.eq("user_id",userId);
        List<Recharge> list = list(qw);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo continueSignRecharge(String userId) {
        RechargeCard card = rechargeCardService.getSevenSignCard();
        cardBagService.createCardBag(userId,card);
        return ResultInfo.success();
    }

    @Override
    public void closeOrder(Recharge recharge) {
        UpdateWrapper<Recharge> uw = new UpdateWrapper<>();
        uw.eq("id", recharge.getId());
        uw.set("status", OrderStatusEnum.cancelpay.code);
        update(uw);
        redisUtil.setRemove(MessageFormat.format(Constant.USER_UNCOMPLETED_ORDER, recharge.getAccount()),recharge.getId());
        if (StringUtils.isNotBlank(recharge.getCouponBagId())){
            couponBagService.releaseCouponBag(recharge.getCouponBagId());
        }
    }

    @Override
    public void doUncompletedOrder(String rechargeId) {
        Recharge recharge = getById(rechargeId);
        if (recharge.getPayType() == PayTypeEnum.alipay.code){
            alipayService.doUncompletedOrder(recharge);
        }else if (recharge.getPayType() == PayTypeEnum.wxpay.code){
            wxpayService.doUncompletedOrder(recharge);
        }
    }
}
