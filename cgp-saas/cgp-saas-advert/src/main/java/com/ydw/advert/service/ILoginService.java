package com.ydw.advert.service;

import com.ydw.advert.model.vo.Enterprise;
import com.ydw.advert.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-07-28
 */
public interface ILoginService{

	ResultInfo login(Enterprise user);

	ResultInfo refreshToken(String token);

}
