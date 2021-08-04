package com.ydw.resource.controller;

import com.ydw.resource.service.IAppService;
import com.ydw.resource.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/715:57
 */
@RestController
@RequestMapping("/app")
public class AppController extends BaseController{

    @Autowired
    private IAppService appService;

    @GetMapping("/getApps")
    public ResultInfo getApps(){
        String identification = super.getEnterpriseId();
        return appService.getApps(identification);
    }

    /**
     * 下发app
     * @param parameter
     * @return
     */
    @PostMapping("/issueApp")
    public ResultInfo issueApp(@RequestBody HashMap<String,String> parameter){
        String relativeRemoteDirectory = parameter.get("relativeRemoteDirectory");
        String remoteFileName = parameter.get("remoteFileName");
        String absoluteLocalDirectory = parameter.get("absoluteLocalDirectory");
        String localFileName = parameter.get("localFileName");
        String appId = parameter.get("appId");
        return appService.issueApp(parameter);
    }

    /**
     * app下发完成
     * @param parameter
     * @return
     */
    @PostMapping("/issuedApp")
    public ResultInfo issuedApp(@RequestBody HashMap<String,String> parameter){
        String appId = parameter.get("appId");
        String clusterId = parameter.get("clusterId");
        return appService.issuedApp(appId, clusterId);
    }
}
