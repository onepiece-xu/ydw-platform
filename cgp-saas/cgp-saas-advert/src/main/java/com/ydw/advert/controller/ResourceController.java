package com.ydw.advert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.advert.model.vo.ApplyParameter;
import com.ydw.advert.model.vo.ConnectVO;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.service.IResourceService;


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
     * 释放设备
     * @return
     */
    @PostMapping(value = "/release")
    public ResultInfo release(@RequestBody ConnectVO vo) {
    	vo.setAccount(getAccount());
        return resourceService.release(vo);
    }

}
