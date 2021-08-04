package com.ydw.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.authentication.model.db.User;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.ModifyPasswordVO;
import com.ydw.authentication.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户关系树 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IUserInfoService extends IService<UserInfo> {

    ResultInfo getUserShareInfo(String id);

    ResultInfo userWithdrawSuccess(String userId, BigDecimal amount);

    ResultInfo userDistributionAward(String userId, BigDecimal amount);

    ResultInfo getUserRecommender(String userId);

    ResultInfo disableUser(List<String> userId, Integer type);

    ResultInfo enableUser(List<String> userId);

    UserInfo getUserByMobileNumber(String mobileNumber);

    ResultInfo updatePassword(ModifyPasswordVO user,String token);

    ResultInfo addUser(User user);

    ResultInfo cancel(HttpServletRequest request, String userId,String code);
}
