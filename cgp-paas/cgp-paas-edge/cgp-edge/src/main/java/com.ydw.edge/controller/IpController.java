package com.ydw.edge.controller;

import com.ydw.edge.model.vo.ResultInfo;
import com.ydw.edge.service.IIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/ip")
public class IpController {

    @Autowired
    private IIpService ipService;

    @PostMapping("/updateIp")
    public ResultInfo updateIp(@RequestBody HashMap<String,String> map){
        String gateway = map.get("gateway");
        String newIp = map.get("newIp");
        int type = Integer.valueOf(map.get("type"));
        return ipService.updateIp(gateway,newIp,type);
    }
}
