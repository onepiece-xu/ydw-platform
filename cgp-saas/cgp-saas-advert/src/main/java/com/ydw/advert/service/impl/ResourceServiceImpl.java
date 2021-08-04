package com.ydw.advert.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ydw.advert.model.constant.Constant;
import com.ydw.advert.model.vo.ApplyParameter;
import com.ydw.advert.model.vo.ConnectVO;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.service.IResourceService;
import com.ydw.advert.util.HttpUtil;

@Service
public class ResourceServiceImpl implements IResourceService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private YdwPaasTokenService ydwPaasApiService;

	@Value("${url.paasApi}")
	private String paasUrl;
	
	@Value("${timeout}")
	private Integer timeout;
	
	@Override
	public ResultInfo apply(ApplyParameter param) {
		param.setSaas(Constant.ApplyParamConfig.SAAS_TYPE_ADVERT);
		logger.info("开始申请游戏: {}", JSON.toJSONString(param));
		String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_APPLY, 
				ydwPaasApiService.buildHeaders(), param);
		ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
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
	public ResultInfo release(ConnectVO vo) {
		HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_RELEASE,
			ydwPaasApiService.buildHeaders() , vo);
		return ResultInfo.success();
	}

}
