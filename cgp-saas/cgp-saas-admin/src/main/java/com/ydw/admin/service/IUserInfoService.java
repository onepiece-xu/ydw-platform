package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.Msg;
import com.ydw.admin.model.vo.MsgVO;
import com.ydw.admin.model.vo.ResultInfo;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
public interface IUserInfoService extends IService<UserInfo> {


    ResultInfo getUserList(String mobileNumber, String search, Page buildPage);

    ResultInfo getOnlineList(String mobileNumber, String search, Integer client,Page buildPage);

    ResultInfo rechargeList(String mobileNumber, String search, String startTime, String endTime,Page buildPage);

    ResultInfo chargeList(Integer client, String search, Page buildPage);

    ResultInfo getDistributionUserList(String search, Page buildPage);

    ResultInfo getDistributionRecommender(String userId);

    ResultInfo getDistributionInferior(String userId, Page buildPage);

    ResultInfo getUserPay(String userId);

    ResultInfo rechargeSummary( String startTime, String endTime,String search);

    ResultInfo chargeSummary(String search);

    ResultInfo getNewUserList(String search, String startTime, String endTime,Page buildPage);

    ResultInfo getNewUserRecharge(String startTime, String endTime);

    ResultInfo getUserTotalRecharge(String startTime, String endTime);

    ResultInfo getOldUserRecharge(String startTime, String endTime);

    ResultInfo getUserActivity(String startTime, String endTime);

    ResultInfo getOnlineListByEnterprise(String enterpriseId, String search, Integer client, Page buildPage);

    ResultInfo getNewRegisterByEnterprise(String enterpriseId, String startTime, String endTime, Page buildPage);

    ResultInfo getNewRegisterCountByEnterprise(String enterpriseId, String startTime, String endTime);

    ResultInfo getRechargeListByEnterprise(String enterpriseId, String search, Page buildPage);

    ResultInfo getRechargeCountByEnterprise(String enterpriseId, String search);

    ResultInfo getChannelBalanceByEnterprise(String enterpriseId);

    ResultInfo applyWithdrawByEnterprise(String enterpriseId, BigDecimal amount);

    ResultInfo getWithdrawRecordByEnterprise(String enterpriseId, Integer status, String beginDate, String endDate, Page page);

    ResultInfo getUserPayByEnterprise(String enterpriseId);

    ResultInfo bindBalancePayByEnterprise(String enterpriseId, String payAccount);

    ResultInfo getRechargeCardsByType(int type);

    ResultInfo awardTime(HashMap<String, String> params);

    ResultInfo sendMessage(MsgVO msg);

    ResultInfo adminAwardTime(HashMap<String, String> params);

    ResultInfo sendMessageAll(MsgVO msg);

    ResultInfo IllegalUserInspection();
}
