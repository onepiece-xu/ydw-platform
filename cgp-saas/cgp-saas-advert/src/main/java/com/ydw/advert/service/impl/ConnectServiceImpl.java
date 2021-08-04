package com.ydw.advert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.advert.model.enums.ConnectStatusEnum;
import com.ydw.advert.model.enums.MessageTopicEnum;
import com.ydw.advert.model.vo.ConnectMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ydw.advert.model.vo.ConnectVO;
import com.ydw.advert.service.IConnectService;
import com.ydw.advert.service.IResourceService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class ConnectServiceImpl implements IConnectService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Lazy
	private IResourceService resourceServiceImpl;


	@Override
	public void connectCallback(String connectId, String account, int status, Object detail) {
		ConnectStatusEnum anEnum = ConnectStatusEnum.getEnum(status);
		switch (anEnum) {
			//未连接
			case UNCONNECT:
				break;
			//申请连接
			case APPLYCONNECT:
				break;
			//连接中
			case CONNECTING:
				break;
			//退出连接
			case CONNECTOUT:
				break;
			//异常连接
			case ABNORMALCONNECT:
				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(detail));
				ConnectVO vo = new ConnectVO();
				vo.setAccount(jsonObject.getString("customId"));
				vo.setAppId(jsonObject.getString("appId"));
				vo.setConnectId(connectId);
				vo.setDeviceId(jsonObject.getString("deviceId"));
				//异常退出写入掉线状态缓存中
				resourceServiceImpl.release(vo);
				break;
		}
	}
}
