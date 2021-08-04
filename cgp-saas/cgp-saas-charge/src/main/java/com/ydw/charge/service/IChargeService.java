package com.ydw.charge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.charge.model.db.CardBag;
import com.ydw.charge.model.db.Charge;
import com.ydw.charge.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface IChargeService extends IService<Charge> {

    void doCharge();

    ResultInfo startCharge(String connectId, String userId);

    ResultInfo endCharge(String connectId);

    ResultInfo chargeAble(String userId);

    ResultInfo playCardChargeAble(String userId);

    ResultInfo startPlayCardCharge(String connectId, String userId);

    ResultInfo endPlayCardCharge(String connectId, String userId);
}
