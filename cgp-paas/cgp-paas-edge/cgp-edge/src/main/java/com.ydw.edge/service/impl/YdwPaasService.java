package com.ydw.edge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.edge.model.vo.DeviceAppOperateResultVO;
import com.ydw.edge.service.IYdwPaasService;
import com.ydw.edge.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class YdwPaasService implements IYdwPaasService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${clusterId}")
	private String clusterId;

	@Value("${centre.domain}")
	private String centreDomain;
	
	/**
	 * 获取本节点的token
	 * @return
	 */
	@Override
	public String getToken(){
		String token = null;
        String url = centreDomain + "/cgp-paas-authentication/login/clusterLogin";
        HashMap<String,String> params = new HashMap<>();
        params.put("clusterId",clusterId);
        String result = HttpUtil.doParamPost(url, null, params,null);
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            int code = jsonObject.getIntValue("code");
            if(code == 200){
                token = jsonObject.getString("data");
            }
        }catch (Exception e){
            logger.error("获取paas平台token失败！");
        }
		return token;
	}

    /**
     * 上报设备状态
     * @param devId
     * @param status
     * @return
     */
    @Override
	public String reportDevStatus(String devId, String macAddr, Integer status){
        String url = centreDomain + "/cgp-paas-resource/device/reportStatus";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,String> params = new HashMap<>();
        params.put("deviceId",devId);
        params.put("macAddr",macAddr);
        params.put("status", status.toString());
        return HttpUtil.doGet(url, headers, params);
    }

    /**
     * 连接异常上报
     * @param connectId
     * @param errCode
     * @return
     */
    @Override
    public String abnormal(String connectId, String errCode){
        String url = centreDomain + "/cgp-paas-resource/connect/abnormal";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,Object> params = new HashMap<>();
        params.put("connectId",connectId);
        params.put("errCode", errCode);
        return HttpUtil.doJsonPost(url, headers,  params);
    }

    /**
     * 连接正常上报
     * @param connectId
     * @return
     */
    @Override
    public String normal(String connectId) {
        String url = centreDomain + "/cgp-paas-resource/connect/normal";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,Object> params = new HashMap<>();
        params.put("connectId",connectId);
        return HttpUtil.doJsonPost(url, headers,  params);
    }


    /**
     * app安装卸载状态上报
     * @param resultvo
     * @return
     */
    @Override
    public String reportDeviceAppResult(DeviceAppOperateResultVO resultvo){
        String url = centreDomain + "/cgp-paas-resource/deviceApp/reportDeviceAppResult";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        return HttpUtil.doJsonPost(url, headers, resultvo);
    }

    /**
     * 修改节点外网ip
     * @param oldIp
     * @param newIp
     * @return
     */
    @Override
    public String updateClusterIp(String oldIp, String newIp){
        String url = centreDomain + "/cgp-paas-resource/clusters/updateExternalIp";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,Object> params = new HashMap<>();
        params.put("oldIp",oldIp);
        params.put("newIp",newIp);
        return HttpUtil.doJsonPost(url, headers, params);
    }

    /**
     * 修改设备外网ip
     * @param gateway
     * @param newIp
     * @return
     */
    @Override
    public String updateDevicesIp(String gateway, String newIp){
        String url = centreDomain + "/cgp-paas-resource/device/updateExternalIp";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,Object> params = new HashMap<>();
        params.put("gateway",gateway);
        params.put("newIp",newIp);
        return HttpUtil.doJsonPost(url,headers, params);
    }

    /**
     * app下发完成回调
     * @param appId
     * @return
     */
    @Override
    public String issuedApp(String appId){
        String url = centreDomain + "/cgp-paas-resource/app/issuedApp";
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",getToken());
        HashMap<String,Object> params = new HashMap<>();
        params.put("appId",appId);
        params.put("clusterId",clusterId);
        return HttpUtil.doJsonPost(url,headers, params);
    }
}
