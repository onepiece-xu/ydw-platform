package com.ydw.resource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.resource.model.db.DeviceUsed;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
public interface IDeviceUsedService extends IService<DeviceUsed> {

	boolean endUse(String connectId);

    void updateConnectMethod(String connectId, int client, int type);
}
