package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.UserPay;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.UserPayVO;

/**
 * <p>
 * 用户支付绑定 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IUserPayService extends IService<UserPay> {

    ResultInfo bindUserPay(UserPayVO userPayVO);

    ResultInfo unbindUserPay(UserPayVO userPayVO);

    ResultInfo updateBindUserPay(UserPayVO userPayVO);

    ResultInfo getBindValidateCode(String mobileNumber);

    ResultInfo getUpdateBindValidateCode(String mobileNumber);

    ResultInfo getUserPay(String userId);
}
