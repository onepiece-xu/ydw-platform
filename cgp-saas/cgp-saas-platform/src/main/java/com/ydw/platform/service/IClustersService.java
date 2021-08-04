package com.ydw.platform.service;

import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IClustersService{

	ResultInfo getUseableRegions(String appId);

}
