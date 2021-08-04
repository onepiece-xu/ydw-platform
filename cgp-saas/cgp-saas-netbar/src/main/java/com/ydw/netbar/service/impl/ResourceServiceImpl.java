package com.ydw.netbar.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.netbar.dao.TbClustersMapper;
import com.ydw.netbar.dao.TbCustomConfigureMapper;
import com.ydw.netbar.model.constant.Constant;
import com.ydw.netbar.model.db.TbClusters;
import com.ydw.netbar.model.db.TbCustomConfigure;
import com.ydw.netbar.model.vo.ApplyParameter;
import com.ydw.netbar.model.vo.ConnectVO;
import com.ydw.netbar.model.vo.DeviceInfo;
import com.ydw.netbar.model.vo.HangupVO;
import com.ydw.netbar.model.vo.ResultInfo;
import com.ydw.netbar.service.IResourceService;
import com.ydw.netbar.util.DateUtil;
import com.ydw.netbar.util.HttpUtil;

@Service
public class ResourceServiceImpl implements IResourceService{
	
	@Autowired
	private TbClustersMapper tbClustersMapper;
	
	@Autowired
	private TbCustomConfigureMapper tbCustomConfigureMapper;
	
	@Autowired
	private YdwPaasApiService ydwPaasApiService;

	@Value("${url.charge}")
	private String chargeUrl;

	@Value("${url.paas}")
	private String paasUrl;
	
	@Value("${url.schedulejob}")
	private String schedulejobUrl;
	
	@Value("${url.netbar}")
	private String netbarUrl;

	@Override
	public ResultInfo apply(ApplyParameter param) {
		param.setSaas(Constant.ApplyParamConfig.SAAS_TYPE_NETBAR);
		String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_APPLY, 
				ydwPaasApiService.buildHeaders(), param);
		ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
		if(resultInfo.getCode() == 200){
			DeviceInfo info = JSON.parseObject(JSON.toJSONString(resultInfo.getData()), DeviceInfo.class);
			//计费
			HttpUtil.doJsonPost(chargeUrl + Constant.Url.URL_CHARGE_DOCHARGE, info.getConnectId());
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
	public ResultInfo reconnect(String account) {
		Map<String, String> params = new HashMap<>();
		params.put("account", account);
		String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_CONNECTINFO,
				ydwPaasApiService.buildHeaders(), params);
		return JSON.parseObject(result, ResultInfo.class);
	}

	@Override
	public ResultInfo release(ConnectVO vo) {
		Map<String,String> params = new HashMap<>();
		params.put("connectId", vo.getConnectId());
		params.put("deviceId", vo.getDeviceId());
		String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_RELEASE, 
				ydwPaasApiService.buildHeaders() , params);
		return JSON.parseObject(result, ResultInfo.class);
	}

	@Override
	public ResultInfo getRegions() {
		List<TbClusters> selectList = tbClustersMapper.selectList(new QueryWrapper<>());
		return ResultInfo.success(selectList);
	}

	@Override
	public ResultInfo getConfigure() {
		List<TbCustomConfigure> selectList = tbCustomConfigureMapper.selectList(new QueryWrapper<>());
		return ResultInfo.success(selectList);
	}

	@Override
	public ResultInfo hangup(HangupVO hangupvo) {
		Map<String,String> job = new HashMap<>();
    	job.put("cron", DateUtil.parseCron(hangupvo.getHangupTime()));
    	job.put("jobGroup","cgp-charge");
    	job.put("url", netbarUrl + Constant.Url.URL_NETBAR_RELEASE);
    	job.put("method","post");
    	DeviceInfo d = new DeviceInfo();
    	d.setConnectId(hangupvo.getConnectId());
    	job.put("body",JSON.toJSONString(d));
    	String doJsonPost = HttpUtil.doJsonPost(schedulejobUrl + Constant.Url.URL_SCHEDULEJOB_ADDJOB, job);
    	HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_HANGUP, hangupvo);
		return JSON.parseObject(doJsonPost, ResultInfo.class);
	}

}
