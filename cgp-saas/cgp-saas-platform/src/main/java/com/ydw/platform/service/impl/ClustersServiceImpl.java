package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IClustersService;
import com.ydw.platform.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class ClustersServiceImpl implements IClustersService {

	@Value("${url.paasApi}")
	private String paasUrl;

	@Autowired
	private FeginClientService feginClientService;

	@Override
	public ResultInfo getUseableRegions(String appId) {
		HashMap<String,String> params = new HashMap<>();
		params.put("appId", appId);
		String s = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_CLUSTERS,feginClientService.buildHeaders(),params);
		return JSON.parseObject(s, ResultInfo.class);
	}

}
