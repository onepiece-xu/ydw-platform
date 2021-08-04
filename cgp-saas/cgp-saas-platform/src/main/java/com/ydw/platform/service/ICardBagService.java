package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.CardBag;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 * 用户资产汇总表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface ICardBagService extends IService<CardBag> {

    ResultInfo getUserUseableTime(String userId);

    ResultInfo getCardBag(String userId);
}
