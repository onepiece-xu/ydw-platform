package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.vo.ResultInfo;

import java.math.BigDecimal;

/**
 * <p>
 * 充值记录表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-09-24
 */
public interface IRechargeService extends IService<Recharge> {

    /**
     * 创建充值记录
     * @param id
     * @param orderNum
     * @param account
     * @param cardId
     * @param payType
     */
    Recharge createRecharge(String id, String orderNum, String account, String cardId, int payType, String couponId, BigDecimal finalCost);

    /**
     * 创建充值记录
     * @param id
     * @param orderNum
     * @param account
     * @param card
     * @param payType
     */
    Recharge createRecharge(String id, String orderNum, String account, RechargeCard card, int payType, String couponId, BigDecimal finalCost);

    /**
     * 充值成功
     * @param id
     * @param orderNum
     */
    void successRecharge(String id, String orderNum);

    /**
     * 签到获取充值
     * @param userId
     * @throws
     * @author xulh 2020/10/14 13:35
     **/
    ResultInfo signRecharge(String userId);

    /**
     * 获取用户充值记录
     * @param userId
     * @throws
     * @author xulh 2020/10/14 13:48
     **/
    ResultInfo getUserRechargeRecord(String userId);

    /**
     * 连续签到7天
     * @param userId
     * @return
     */
    ResultInfo continueSignRecharge(String userId);

    /**
     * 关闭订单
     * @param recharge
     */
    void closeOrder(Recharge recharge);

    /**
     * 处理未完成的订单
     * @param rechargeId
     */
    void doUncompletedOrder(String rechargeId);
}
