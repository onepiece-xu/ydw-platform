package com.ydw.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.CardBagMapper;
import com.ydw.recharge.model.db.CardBag;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICardBagService;
import com.ydw.recharge.service.IRechargeCardService;
import com.ydw.recharge.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户资产汇总表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@Service
public class CardBagServiceImpl extends ServiceImpl<CardBagMapper, CardBag> implements ICardBagService {

    @Autowired
    private IRechargeCardService rechargeCardService;

    @Autowired
    private CardBagMapper cardBagMapper;

    @Override
    public ResultInfo createCardBag(String userId, String cardId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(cardId)) {
            return ResultInfo.fail("用户id或者充值卡id为空！");
        }
        RechargeCard rechargeCard = rechargeCardService.getById(cardId);
        return createCardBag(userId, rechargeCard);
    }

    @Override
    public ResultInfo createCardBag(String userId, RechargeCard rechargeCard) {
        if (StringUtils.isBlank(userId) || rechargeCard == null) {
            return ResultInfo.fail("用户id或者充值卡为空！");
        }
        Integer type = rechargeCard.getType();
        //套餐卡需要特殊处理
        if (type == 5){
            String packageCardIds = rechargeCard.getComboCardIds();
            QueryWrapper<RechargeCard> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", packageCardIds.split(","));
            List<RechargeCard> list = rechargeCardService.list(queryWrapper);
            for (RechargeCard card : list) {
                createCardBag(userId, card);
            }
            return ResultInfo.success();
        }
        LocalDateTime beginTime = LocalDateTime.now();
        //周期卡和畅玩卡的开始时长要顺延
        if (rechargeCard.getObtainType() == 0 && (type == 1 || type == 4)) {
            //查询是否存在已周期卡
            List<CardBag> cardBags = cardBagMapper.getCardsByUserIdAndType(userId, type);
            if (cardBags.size() > 0) {
                CardBag lastCardBag = cardBags.get(0);
                beginTime = lastCardBag.getEndTime();
            }
        }
        LocalDateTime endTime = rechargeCard.getValidityTime() > -1 ? (
                rechargeCard.getValidityTime() == 0 ?
                        beginTime.with(LocalTime.MAX) :
                        beginTime.plusDays(rechargeCard.getValidityTime()
                        )
        ) : null;
        //加卡
        CardBag cardBag = new CardBag();
        cardBag.setCardId(rechargeCard.getId());
        cardBag.setCreateTime(beginTime);
        cardBag.setEndTime(endTime);
        cardBag.setId(SequenceGenerator.sequence());
        //充值卡是小时单位，卡包是分钟单位
        cardBag.setDuration(rechargeCard.getDuration() * 60);
        cardBag.setUsedDuration(0);
        cardBag.setUserId(userId);
        cardBag.setBeginPeriod(rechargeCard.getBeginPeriod());
        cardBag.setEndPeriod(rechargeCard.getEndPeriod());
        boolean save = save(cardBag);
        if (save) {
            return ResultInfo.success();
        } else {
            return ResultInfo.fail();
        }
    }

    @Override
    public ResultInfo getUserUseableTime(String userId) {
        Long time = 0L;
        QueryWrapper<CardBag> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("valid", true);
        List<CardBag> list = list(qw);
        if (list != null && !list.isEmpty()) {
            for (CardBag c : list) {
                time += c.getDuration() - c.getUsedDuration();
            }
        }
        return ResultInfo.success(time);
    }

    @Override
    public ResultInfo getCardBagNum(String userId, String cardId) {
        QueryWrapper<CardBag> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("card_id", cardId);
        int count = super.count(qw);
        return ResultInfo.success(count);
    }

    @Override
    public ResultInfo sendDuration(String userId, String time) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(time)) {
            return ResultInfo.fail("用户id或者充值时间为空！");
        }
        int parseInt = 0;
        try {
            parseInt = Integer.parseInt(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        CardBag cardBag = new CardBag();
        LocalDateTime now = LocalDateTime.now();
        cardBag.setBeginTime(now);
        cardBag.setCreateTime(now);
        //有效时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);//这里改为1
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        cal.set(Calendar.MINUTE, 0);
        // 秒
        cal.set(Calendar.SECOND, 0);
        // 毫秒
        cal.set(Calendar.MILLISECOND, 0);

        Date tomorrow = cal.getTime();
//  String end = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(tomorrow);
        Instant instant = tomorrow.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime end = instant.atZone(zoneId).toLocalDateTime();
        //给默认送时间卡的id
        RechargeCard card = rechargeCardService.getsendDurationCard();
        String cardId = card.getId();
        cardBag.setCardId(cardId);
        cardBag.setValid(true);
        cardBag.setEndTime(end);
        cardBag.setUserId(userId);
        cardBag.setDuration(parseInt);
        cardBag.setId(SequenceGenerator.sequence());
        cardBag.setUsedDuration(0);
        boolean save = save(cardBag);
        if (save) {
            return ResultInfo.success();
        } else {
            return ResultInfo.fail();
        }
    }
}
