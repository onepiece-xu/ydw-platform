package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.Connect;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IConnectService extends IService<Connect> {

	void abnormalConnect(String connectId);

    ResultInfo getConnectInfo(String connectId);

    void offlineScan();

    void connectCallback(String connectId, String account, int status, Object detail);

    void removeOffline(String connectId);

    void addOffline(String connectId);

    void delayConnectNotice(String account, String appName, String connectId);

    void cancelConnectNotice(String connectId);
}
