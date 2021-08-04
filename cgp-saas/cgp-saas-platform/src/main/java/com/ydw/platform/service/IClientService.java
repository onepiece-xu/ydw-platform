package com.ydw.platform.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/2310:47
 */
public interface IClientService {

    void getUpgradeFile(String channelId, HttpServletResponse response);
}
