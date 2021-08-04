package com.ydw.charge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.charge.model.db.CardBag;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户资产汇总表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface ICardBagService extends IService<CardBag> {

    /**
     * 获取用户可消费卡包并按照消费顺序
     * @param userId
     * @return
     */
    List<CardBag> getUseAbleCardBag(String userId);

    /**
     * 获取用户可消费卡包并按照消费顺序
     * @param userId
     * @return
     */
    List<CardBag> getUseAbleCardBagOrderByConsume(String userId);

    /**
     * 获取连续可计费时长
     * @param userId
     * @return
     */
    long getChargeAbleContinuityTime(String userId);

    /**
     * 获取连续可计费时长
     * @param canUseCardBag
     * @return
     */
    long getChargeAbleContinuityTime(List<CardBag> canUseCardBag);

    /**
     * 消费用户卡包
     * @param userId
     * @param chargeDuration
     * @return
     */
    List<CardBag> consumeCardBags(String userId,long chargeDuration);

    /**
     * 获取用户可用时长（分钟）
     * @param userId
     * @return
     */
    long getUserUseableTime(String userId);

    /**
     * 是否可以计费
     * @param cardBag
     * @param localDateTime
     * @return
     */
    boolean chargeAble(CardBag cardBag, LocalDateTime localDateTime);

    /**
     * 获取用户可消费卡包并按照消费顺序(畅玩卡)
     * @param userId
     * @return
     */
    List<CardBag> getUseAblePlayCardBag(String userId);

    /**
     * 获取用户可消费卡包并按照消费顺序(畅玩卡)
     * @param userId
     * @return
     */
    List<CardBag> getUseAblePlayCardBagOrderByConsume(String userId);

    /**
     * 消费用户卡包（畅玩卡）
     * @param userId
     * @param chargeDuration
     * @return
     */
    List<CardBag> consumePlayCardBags(String userId,long chargeDuration);
}
