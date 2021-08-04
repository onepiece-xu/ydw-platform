package com.ydw.advert.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IConnectService {

    void connectCallback(String connectId, String account, int status, Object detail);
}
