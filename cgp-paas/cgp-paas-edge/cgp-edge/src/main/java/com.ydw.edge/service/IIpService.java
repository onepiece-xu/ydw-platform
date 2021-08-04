package com.ydw.edge.service;

import com.ydw.edge.model.vo.ResultInfo;

public interface IIpService {

    ResultInfo updateIp(String gateway,String newIp,int type);
}
