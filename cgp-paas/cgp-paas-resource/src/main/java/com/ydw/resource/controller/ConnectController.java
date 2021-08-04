package com.ydw.resource.controller;

import com.ydw.resource.model.vo.*;
import com.ydw.resource.service.IConnectService;
import com.ydw.resource.utils.ResultInfo;
import com.ydw.resource.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/connect")
public class ConnectController extends BaseController{

	@Autowired
	private IConnectService connectServiceImpl;
	
	/**
	 * 申请连接
	 * @param param
	 * @return
	 */
    @PostMapping(value = "/apply")
    public ResultInfo apply(@RequestBody ApplyParams param) {
        param.setEnterpriseId(getEnterpriseId());
        param.setSaas(getSaas());
        return connectServiceImpl.apply(param);
    }

    /**
     * 获取排名
     * @return
     */
    @GetMapping(value = "/getRank")
    public ResultInfo getRank(@RequestParam String account) {
        return connectServiceImpl.getRank(account);
    }

    /**
     * 取消连接
     * @param param
     * @return
     */
    @PostMapping(value = "/cancelConnect")
    public ResultInfo cancelConnect(@RequestBody ConnectParams param) {
        param.setEnterpriseId(getEnterpriseId());
        param.setSaas(getSaas());
        return connectServiceImpl.cancelConnect(param);
    }

    /**
     * 退出排队
     * @return
     */
    @GetMapping(value = "/queueOut")
    public ResultInfo queueOut(@RequestParam String account) {
        return connectServiceImpl.queueOut(account);
    }

    /**
     * 准备初始化资源
     * @param param
     * @return
     */
    @PostMapping(value = "/connect")
    public ResultInfo connect(@RequestBody ConnectParams param) {
        param.setEnterpriseId(getEnterpriseId());
        param.setSaas(getSaas());
        return connectServiceImpl.connect(param);
    }

    /**
     * 打开游戏
     * @param param
     * @return
     */
    @PostMapping(value = "/openApp")
    public ResultInfo openApp(@RequestBody AppOperateParams param) {
        return connectServiceImpl.openApp(param);
    }

    /**
     * 重新连接
     * @return
     */
    @PostMapping(value = "/reconnect")
    public ResultInfo reconnect(@RequestBody ReConnectVO vo) {
        vo.setEnterpriseId(getEnterpriseId());
        vo.setSaas(getSaas());
        return connectServiceImpl.reconnect(vo);
    }
    
    /**
     * 释放连接
     * @return
     */
    @PostMapping(value = "/release")
    public ResultInfo release(@RequestBody ConnectVO vo) {
        vo.setEnterpriseId(getEnterpriseId());
        vo.setSaas(getSaas());
        return connectServiceImpl.release(vo);
    }

    /**
     * 关闭游戏
     * @param param
     * @return
     */
    @PostMapping(value = "/closeApp")
    public ResultInfo closeApp(@RequestBody AppOperateParams param) {
        return connectServiceImpl.closeApp(param);
    }
    
    /**
     * 异常上报
     * @return
     */
    @PostMapping("/abnormal")
    public ResultInfo abnormal(@RequestBody Map<String, String> map) {
    	String errCode = StringUtil.isBlank(map.get("errCode")) ? "0" : map.get("errCode");
    	String connectId = map.get("connectId");
        return connectServiceImpl.abnormal(errCode, connectId);
    }

    /**
     * 异常上报
     * @return
     */
    @PostMapping("/normal")
    public ResultInfo normal(@RequestBody Map<String, String> map) {
        String connectId = map.get("connectId");
        return connectServiceImpl.normal(connectId);
    }
    
    /**
     * 获取用户连接状态
     * @return
     */
    @GetMapping("/getUserConnectStatus")
    public ResultInfo getUserConnectStatus(@RequestParam String account) {
        account = new SaasCommonParams(getEnterpriseId(),getSaas(), account).getAccount();
        return connectServiceImpl.getUserConnectStatus(account);
    }

    /**
     * 获取获取连接详情
     * @param connectId
     * @return
     */
    @GetMapping("/getConnectDetail")
    public ResultInfo getConnectDetail(@RequestParam String connectId) {
        return connectServiceImpl.getConnectDetail(connectId);
    }

    /**
     * 资源申请到通知
     * @param vo
     * @return
     */
    @PostMapping(value = "/noticeResourceApplyed")
    public ResultInfo noticeResourceApplyed(@RequestBody ConnectVO vo){
        return connectServiceImpl.noticeResourceApplyed(vo);
    }
}
