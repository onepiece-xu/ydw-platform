package com.ydw.platform.service;

import com.ydw.platform.model.vo.ResultInfo;

import java.util.SortedMap;

public interface IDadagameService {

    boolean checkSign(SortedMap<String, Object> params);

    ResultInfo borrowNum(String account, String appId);

    ResultInfo returnNum(String startGameNo);

    void returnNumNotice(String startGameNo);

    void saveStartGameNoToConnectId(String startGameNo, String connectId);
}