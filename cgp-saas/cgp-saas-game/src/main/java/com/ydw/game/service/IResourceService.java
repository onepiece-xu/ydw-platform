package com.ydw.game.service;

import com.ydw.game.model.vo.ApplyParameter;
import com.ydw.game.model.vo.ConnectVO;
import com.ydw.game.model.vo.ResultInfo;

public interface IResourceService {

	ResultInfo apply(ApplyParameter param);

	ResultInfo queueOut(String account);

	ResultInfo reconnect(ConnectVO vo);

	ResultInfo release(ConnectVO vo);

	ResultInfo getUserConnectStatus(String account);

	void wsAbnormal(String account);

	void wsStatusScan();
}
