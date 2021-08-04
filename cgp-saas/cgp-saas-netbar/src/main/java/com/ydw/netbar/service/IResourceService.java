package com.ydw.netbar.service;

import com.ydw.netbar.model.vo.ApplyParameter;
import com.ydw.netbar.model.vo.ConnectVO;
import com.ydw.netbar.model.vo.HangupVO;
import com.ydw.netbar.model.vo.ResultInfo;

public interface IResourceService {

	ResultInfo apply(ApplyParameter param);

	ResultInfo queueOut(String token);

	ResultInfo reconnect(String token);

	ResultInfo release(ConnectVO vo);

    ResultInfo getRegions();

	ResultInfo getConfigure();

	ResultInfo hangup(HangupVO hangupvo);
}
