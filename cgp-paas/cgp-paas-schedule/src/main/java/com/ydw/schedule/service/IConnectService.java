package com.ydw.schedule.service;

import com.ydw.schedule.model.vo.ApplyConnectVO;
import com.ydw.schedule.model.vo.ApplyResultVO;
import com.ydw.schedule.model.vo.ConnectVO;
import com.ydw.schedule.model.vo.ResultInfo;

import java.util.List;

public interface IConnectService {

	ResultInfo applyConnect(ApplyConnectVO vo);

	ApplyResultVO applyFromAllIdle(String account, List<String> clusterIds, String appId, String baseId);

	ApplyResultVO applyFromSubIdle(String account, List<String> clusterIds, String appId, String baseId);

    ResultInfo releaseConnect(ConnectVO vo);

	ResultInfo getUserConnectStatus(String account);

	ResultInfo getConnectByDevice(String deviceId);

	ResultInfo getDeviceByConnect(String connectId);
}
