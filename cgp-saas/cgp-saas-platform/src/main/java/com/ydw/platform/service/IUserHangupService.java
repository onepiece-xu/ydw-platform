package com.ydw.platform.service;

import com.ydw.platform.model.db.UserHangup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户挂机记录 服务类
 * </p>
 *
 * @author xulh
 * @since 2021-07-20
 */
public interface IUserHangupService extends IService<UserHangup> {

    int getUserOneDaySubHangupDuration(String account);

    void startHangup(String userId, String connectId, int hangupDuration);

    void endHangup(String connectId);

    long getHangupEndDuration(String connectId);
}
