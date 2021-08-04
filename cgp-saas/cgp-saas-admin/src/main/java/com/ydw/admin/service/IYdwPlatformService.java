package com.ydw.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-platform", path = "/cgp-saas-platform")
public interface IYdwPlatformService {

    //获取连接信息
    @GetMapping(value = "/templateType/getTemplateId")
    String getTemplateId(@RequestParam(value = "term") String term);

    @PostMapping(value = "/postMessage/add")
    String postMessage(@RequestBody HashMap<String,Object> param);
}
