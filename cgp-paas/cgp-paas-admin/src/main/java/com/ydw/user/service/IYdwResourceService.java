package com.ydw.user.service;

import com.ydw.user.utils.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/716:53
 */
@FeignClient(value = "cgp-paas-resource", path = "/cgp-paas-resource")
public interface IYdwResourceService {

    @PostMapping("/app/issueApp")
    public ResultInfo issueApp(@RequestBody HashMap<String,String> parameter);
}
