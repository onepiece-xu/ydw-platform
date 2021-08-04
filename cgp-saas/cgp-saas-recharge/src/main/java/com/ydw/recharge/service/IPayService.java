package com.ydw.recharge.service;

import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.model.vo.ResultInfo;

import java.util.Map;

public interface IPayService {

    //处理未结束的用户订单
    void doUncompletedOrders(String account);

    //处理未结束的用户订单
    void doUncompletedOrder(Recharge recharge);

    String getPayUrl(String rechargeCardId, String account, String couponBagId);

    ResultInfo createOrder(String rechargeCardId, String account, String couponBagId);

    String payNotify(Map<String,String> map);

    ResultInfo transfer(Map<String, Object> map);
}
