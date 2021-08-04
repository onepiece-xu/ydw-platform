package com.ydw.game.service;

import com.ydw.game.model.db.Connect;
import com.ydw.game.model.vo.ResultInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IConnectService extends IService<Connect> {

	void abnormalNotice(String connectId);

	ResultInfo abnormalConnect(String connectId);

}
