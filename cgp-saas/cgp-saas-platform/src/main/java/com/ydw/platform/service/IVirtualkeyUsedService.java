package com.ydw.platform.service;

import com.ydw.platform.model.db.VirtualkeyUsed;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-03-11
 */
public interface IVirtualkeyUsedService extends IService<VirtualkeyUsed> {

    ResultInfo applyVirtualkeyConfig(String configId, String appId, String userId);

    ResultInfo getDefaultVirtualkeyConfig(String userId, String appId);
}
