package com.ydw.advert.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ydw.advert.model.constant.Constant;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.service.IClustersService;
import com.ydw.advert.util.HttpUtil;

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
	private YdwPaasTokenService ydwPaasTokenService;

	@Value("${paas.identification}")
	private String identification;

	@Override
	public ResultInfo getRegions() {
		Map<String, String> params = new HashMap<>();
		params.put("identification", identification);	
		String doGet = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_CLUSTERS,
				ydwPaasTokenService.buildHeaders(), params);
		return JSON.parseObject(doGet, ResultInfo.class);
	}

}
