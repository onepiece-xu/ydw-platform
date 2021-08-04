package com.ydw.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.ydw.platform.model.vo.ApplyParameter;
import com.ydw.platform.model.vo.ConnectVO;
import com.ydw.platform.model.vo.PrepareParams;
import com.ydw.platform.model.vo.ResultInfo;

import java.util.Date;

public interface IResourceService {

	ResultInfo apply(ApplyParameter param);

    ResultInfo queueOut(String account);

	ResultInfo reconnect(ConnectVO vo);

	ResultInfo release(ConnectVO vo);

    ResultInfo release(String connectId);

	ResultInfo saasRelease(String connectId, String account);

	ResultInfo getUserConnectStatus(String account);

    void replacePics(JSONObject jsonObject, String appId);

    ResultInfo hangup(String account, String connectId, int hangupDuration);

    ResultInfo hangupEnd(String connectId, Date hangupEndTime);

    ResultInfo cancelHangup(String connectId);

    ResultInfo releaseUserRes(String userId);

    ResultInfo openApp(String account, String connectId, String deviceId, String appId);

    ResultInfo getHangupEndTime(String connectId);

    ResultInfo prepare(PrepareParams param);

    ResultInfo applyByNetbar(ApplyParameter param);

    ResultInfo getRank(String account);

    ResultInfo cancelConnect(PrepareParams param);

    ResultInfo queueMessageNotice(String account, String appName, String connectId);

    ResultInfo rentalApply(ApplyParameter param);

    ResultInfo rentalApplyAble(String account, String appId);
}
