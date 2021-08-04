package com.ydw.recharge.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.WithdrawRecord;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.WithdrawVO;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IWithdrawRecordService extends IService<WithdrawRecord> {

    ResultInfo applyWithraw(WithdrawVO withdrawVO);

    ResultInfo getUserWithdrawRecord(String userId, Page page);

    ResultInfo userWithdraw(String orderId, String payee, BigDecimal withdrawAmount, String payId,String mobileNumber);

    ResultInfo getOwnerinfoToWithdraw(JSONObject userInfo);

    ResultInfo userCanWithdraw(String userAccount);
}
