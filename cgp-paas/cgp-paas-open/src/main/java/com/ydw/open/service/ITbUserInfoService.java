package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.model.db.TbUserInfo;
import com.ydw.open.model.vo.modifyPasswordVO;
import com.ydw.open.utils.ResultInfo;


import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */
public interface ITbUserInfoService extends IService<TbUserInfo> {

    ResultInfo createUser(HttpServletRequest request, TbUserInfo user);

    ResultInfo updateUser(HttpServletRequest request, TbUserInfo user);

    ResultInfo deleteUser(HttpServletRequest request, List<String> ids);

//    ResultInfo getUserList(HttpServletRequest request, String userName, String loginName,
//                           String identification, Integer type, Integer status, String mobileNumber,
//                           String telNumber, String search, Page buildPage);

    ResultInfo getUserList(HttpServletRequest request, String enterpriseName, String loginName,
                           String identification, Integer type, Integer schStatus, String mobileNumber,
                           String telNumber, String search, Page buildPage);

    ResultInfo resetPassword(HttpServletRequest request, TbUserInfo user);

    ResultInfo getUserInfo(HttpServletRequest request, String id);

    ResultInfo disableUser(HttpServletRequest request, Integer type, List<String> ids);

    ResultInfo updatePassword(HttpServletRequest request, modifyPasswordVO user);

    /**
     * 企业用户登录之后查询相关用户列表
     * @param request
     * @param
     * @param loginName
     * @param identification
     * @param type
     * @param
     * @param mobileNumber
     * @param telNumber
     * @param
     * @param
     * @return
     */
    ResultInfo getList(HttpServletRequest request, String enterpriseName, String loginName, String identification, Integer type, Integer schStatus, String mobileNumber, String telNumber, Page buildPage);

    ResultInfo getInfo(HttpServletRequest request);

    ResultInfo getNewRegister(String identification, String startTime, String endTime, Integer pageNum, Integer pageSize);

    ResultInfo getOnlineList(String identification, String search, Integer client, Integer pageNum, Integer pageSize);

    ResultInfo getNewRegisterCount(String identification, String startTime, String endTime);

    ResultInfo getRechargeList(String identification, String search, Integer pageNum, Integer pageSize);

    ResultInfo getRechargeCount(String identification, String search);

    ResultInfo getUserBalance(String identification);

    ResultInfo applyWithdraw(String identification, BigDecimal amount);

    ResultInfo getWithdrawRecord(String identification, Integer status, String beginDate, String endDate);

    ResultInfo getUserPay(String identification);

    ResultInfo bindUserPay(String identification, String payAccount);
}
