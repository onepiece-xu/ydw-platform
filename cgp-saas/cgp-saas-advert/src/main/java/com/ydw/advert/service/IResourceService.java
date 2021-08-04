package com.ydw.advert.service;

import com.ydw.advert.model.vo.ApplyParameter;
import com.ydw.advert.model.vo.ConnectVO;
import com.ydw.advert.model.vo.ResultInfo;

public interface IResourceService {

	ResultInfo apply(ApplyParameter param);

	ResultInfo queueOut(String account);

	ResultInfo release(ConnectVO vo);
}
