package com.ydw.platform.service;

import com.ydw.platform.model.db.SignIn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 * 用户签到表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
public interface ISignInService extends IService<SignIn> {

    ResultInfo signIn(SignIn sign);

    ResultInfo signInList(SignIn sign);
}
