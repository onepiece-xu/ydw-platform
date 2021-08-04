package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.CardBag;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.vo.ResultInfo;

import java.util.HashMap;

/**
 * <p>
 * 用户资产汇总表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface ICardBagService extends IService<CardBag> {

    ResultInfo createCardBag(String userId, String cardId);

    ResultInfo createCardBag(String userId, RechargeCard rechargeCard);

    ResultInfo getUserUseableTime(String userId);

    ResultInfo getCardBagNum(String userId, String cardId);

    ResultInfo sendDuration(String userId, String time);
}
