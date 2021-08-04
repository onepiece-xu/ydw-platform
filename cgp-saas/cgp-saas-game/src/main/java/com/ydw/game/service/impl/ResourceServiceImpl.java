package com.ydw.game.service.impl;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.Connect;
import com.ydw.game.model.enums.MessageTopicEnum;
import com.ydw.game.model.vo.ApplyParameter;
import com.ydw.game.model.vo.ConnectMsg;
import com.ydw.game.model.vo.ConnectVO;
import com.ydw.game.model.vo.DeviceConnectInfo;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.model.vo.WSMsg;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.IConnectService;
import com.ydw.game.service.IResourceService;
import com.ydw.game.util.HttpUtil;
import com.ydw.game.websocket.service.ConnectMessageService;

@Service
public class ResourceServiceImpl implements IResourceService{
	
	@Autowired
	private YdwPaasTokenService ydwPaasApiService;
	
	@Autowired
	private IConnectService connectServiceImpl;

	@Value("${url.charge}")
	private String chargeUrl;

	@Value("${url.paasApi}")
	private String paasUrl;
	
	@Value("${url.schedulejob}")
	private String schedulejobUrl;
	
	@Value("${url.game}")
	private String gameUrl;
	
	@Value("${timeout}")
	private Integer timeout;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ConnectMessageService connectMessageService;

	@Override
	public ResultInfo apply(ApplyParameter param) {
		param.setSaas(Constant.ApplyParamConfig.SAAS_TYPE_GAME);
		String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_APPLY, 
				ydwPaasApiService.buildHeaders(), param);
		ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
		if(resultInfo.getCode() == 200){
			DeviceConnectInfo info = JSON.parseObject(JSON.toJSONString(resultInfo.getData()), DeviceConnectInfo.class);
			Connect connect = connectServiceImpl.getById(info.getConnectId());
			if(connect == null){
				//创建连接
				connect = new Connect();
				connect.setId(info.getConnectId());
				connect.setAbnormalTime(null);
				connect.setAppId(param.getAppId());
				connect.setBeginTime(LocalDateTime.now());
				connect.setClient(param.getClient());
				connect.setUserId(param.getAccount());
				connect.setDeviceId(info.getDeviceId());
				connect.setFromIp(param.getCustomIp());
				connect.setType(param.getType());
				connectServiceImpl.save(connect);
				//计费
//				HttpUtil.doJsonPost(chargeUrl + Constant.Url.URL_CHARGE_DOCHARGE, info.getConnectId());
			}
		}
		return resultInfo;
	}

	@Override
	public ResultInfo queueOut(String account) {
		Map<String, String> params = new HashMap<>();
		params.put("account", account);
		String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_QUEUEOUT, 
				ydwPaasApiService.buildHeaders(), params);
		return JSON.parseObject(result, ResultInfo.class);
	}

	@Override
	public ResultInfo reconnect(ConnectVO vo) {
		ResultInfo userConnectStatus = getUserConnectStatus(vo.getAccount());
		if(userConnectStatus.getCode() == 200){
			connectServiceImpl.update(new UpdateWrapper<Connect>().set("abnormal_time", null).eq("id", vo.getConnectId()));
		}
		return userConnectStatus;
	}

	@Override
	public ResultInfo release(ConnectVO vo) {
		Map<String,String> params = new HashMap<>();
		params.put("connectId", vo.getConnectId());
		params.put("deviceId", vo.getDeviceId());
		HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_RELEASE, 
			ydwPaasApiService.buildHeaders() , params);
		Connect c = connectServiceImpl.getById(vo.getConnectId());
		LocalDateTime now = LocalDateTime.now();
		c.setEndTime(now);
		c.setTotalTime(Duration.between(c.getBeginTime(), c.getEndTime()).getSeconds());
		connectServiceImpl.updateById(c);
		WSMsg wsMsg = new WSMsg();
		ConnectMsg connect = new ConnectMsg();
		connect.setConnectSatus(3);
		wsMsg.setData(connect);
		wsMsg.setDestination(MessageTopicEnum.CONNECT.getTopic());
		wsMsg.setReceiver(vo.getAccount());
		wsMsg.setSender(vo.getAccount());
		wsMsg.setType(0);
		connectMessageService.send(wsMsg);
		redisUtil.setRemove(Constant.ZSET_WS_OFFLINE, vo.getAccount());
		return ResultInfo.success();
	}

	@Override
	public ResultInfo getUserConnectStatus(String account) {
		Map<String, String> params = new HashMap<>();
		params.put("account", account);
		String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_USERCONNECTSTATUS,
				ydwPaasApiService.buildHeaders(), params);
		ResultInfo info = JSON.parseObject(result, ResultInfo.class);
		if(info.getCode() == 200){
			if(info.getData() == null){
				info.setCode(202);
			}else{
				JSONObject data = JSON.parseObject(JSON.toJSONString(info.getData()));
				int intValue = data.getIntValue("status");
				if(intValue == 1){
					info.setCode(201);
				}
			}
		}
		return info;
	}

	@Override
	public void wsAbnormal(String account) {
		ResultInfo userConnectStatus = getUserConnectStatus(account);
		if(userConnectStatus.getCode() == 200){
			DeviceConnectInfo info = JSON.parseObject(JSON.toJSONString(userConnectStatus.getData()), DeviceConnectInfo.class);
			ConnectVO connectVO = new ConnectVO();
			connectVO.setAccount(account);
			connectVO.setAppId(info.getAppId());
			connectVO.setConnectId(info.getConnectId());
			connectVO.setDeviceId(info.getDeviceId());
			release(connectVO);
		}
	}

	@Override
	public void wsStatusScan() {
		//获取所有正在连接人数
		Set<Object> sGet = redisUtil.zRange(Constant.ZSET_WS_OFFLINE, Double.MIN_VALUE, Double.MAX_VALUE);
		List<Object> list = new LinkedList<>();
		for(Object obj : sGet){
			String userSessionKey = MessageFormat.format(Constant.SET_WS_ONLINE, (String)obj);
			if(!redisUtil.hasKey(userSessionKey)){
				//确实已经不在线了,比较离线时间
				Double offLineTime = redisUtil.zScore(Constant.ZSET_WS_OFFLINE, obj);
				//离线时间已经到达默认挂机时长
				if((System.currentTimeMillis() - offLineTime) / 1000 >= timeout){
					wsAbnormal((String)obj);
					list.add(obj);
				}
			}else{
				list.add(obj);
			}
		}
		redisUtil.zRemove(Constant.ZSET_WS_OFFLINE, list.toArray());
	}

}
