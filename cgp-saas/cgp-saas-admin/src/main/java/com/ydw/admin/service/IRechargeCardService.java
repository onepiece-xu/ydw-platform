package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.RechargeCard;
import com.ydw.admin.model.vo.ResultInfo;

/**
 * <p>
 * 充值卡 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface IRechargeCardService extends IService<RechargeCard> {

    ResultInfo getRechargeCards();

    RechargeCard getSignCard();

    ResultInfo getRechargeCardsByType(int type);

    ResultInfo addCard(RechargeCard rechargeCard);

    ResultInfo cardList(String search,Page buildPage);

    ResultInfo updateCard(RechargeCard rechargeCard);

    ResultInfo available(String body);

    ResultInfo cardInfo(String id);

    ResultInfo getSendCardList();
}
