package com.ydw.meterage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.meterage.model.db.TbDeviceUsed;
import com.ydw.meterage.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
public interface ITbDeviceUsedService extends IService<TbDeviceUsed> {

	ResultInfo beginUse(TbDeviceUsed vo);

	ResultInfo endUse(String connectId);

	ResultInfo getConnectDetail(String connectId);

    ResultInfo updateConnectMethod(String connectId, int client, int type);
}
