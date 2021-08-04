package com.ydw.netbar.service;

import com.ydw.netbar.model.db.User;
import com.ydw.netbar.model.vo.ResultInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-07-28
 */
public interface ILoginService{

	ResultInfo localLogin(User user);

}
