package com.ydw.recharge.service;

import com.ydw.recharge.model.db.RechargeCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.vo.ResultInfo;

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

    RechargeCard getSevenSignCard();

    RechargeCard getsendDurationCard();

    ResultInfo getPlayRechargeCards();

    ResultInfo getComboRechargeCards();
}
