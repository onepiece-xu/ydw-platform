package com.ydw.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.game.model.vo.ApplyParameter;
import com.ydw.game.model.vo.ConnectVO;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.IResourceService;


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
    public ResultInfo queueOut() {
        return resourceService.queueOut(super.getAccount());
    }

    /**
     * 重连设备
     * @return
     */
    @PostMapping(value = "/reconnect")
    public ResultInfo reconnect(@RequestBody ConnectVO vo) {
    	vo.setAccount(getAccount());
        return resourceService.reconnect(vo);
    }

    /**
     * 释放设备
     * @param device
     * @return
     */
    @PostMapping(value = "/release")
    public ResultInfo release(@RequestBody ConnectVO vo) {
                      	vo.setAccount(getAccount());
        return resourceService.release(vo);
    }

    /**
     * 获取用户连接状态
     * @return
     */
    @GetMapping(value = "/getUserConnectStatus")
    public ResultInfo getUserConnectStatus() {
        return resourceService.getUserConnectStatus(super.getAccount());
    }

    /**
     * websocket状态扫描
     * @param vo
     * @return
     */
    @GetMapping(value = "/wsStatusScan")
    public void wsStatusScan() {
        resourceService.wsStatusScan();
    }
}
