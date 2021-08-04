package com.ydw.netbar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.netbar.model.vo.ApplyParameter;
import com.ydw.netbar.model.vo.ConnectVO;
import com.ydw.netbar.model.vo.HangupVO;
import com.ydw.netbar.model.vo.ResultInfo;
import com.ydw.netbar.service.IResourceService;


@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 申请设备
     * @param param
     * @return
     */
    @PostMapping(value = "/apply")
    public ResultInfo apply(@RequestBody ApplyParameter param) {
        param.setAccount(getAccount());
        param.setCustomIp(getIpAddr());
        param.setEnterpriseId(getEnterpriseId());
        return resourceService.apply(param);
    }

    /**
     * 退出排队
     * @return
     */
    @GetMapping(value = "/queueOut")
    public ResultInfo applyout() {
        return resourceService.queueOut(super.getAccount());
    }

    /**
     * 重连设备
     * @return
     */
    @GetMapping(value = "/reconnect")
    public ResultInfo reconnect() {
        return resourceService.reconnect(super.getAccount());
    }

    /**
     * 释放设备
     * @param device
     * @return
     */
    @PostMapping(value = "/release")
    public ResultInfo release(@RequestBody ConnectVO vo) {
        return resourceService.release(vo);
    }

    /**
     * 获取区服信息
     * @return
     */
    @GetMapping(value = "/getRegions")
    public ResultInfo getRegions() {
        return resourceService.getRegions();
    }

    /**
     * 获取配置
     * @return
     */
    @GetMapping(value = "/getConfigure")
    public ResultInfo getConfigure() {
        return resourceService.getConfigure();
    }
    
    /**
     * 挂机
     * @return
     */
    @PostMapping(value = "/hangup")
    public ResultInfo hangup(@RequestBody HangupVO hangupvo) {
        return resourceService.hangup(hangupvo);
    }
}
