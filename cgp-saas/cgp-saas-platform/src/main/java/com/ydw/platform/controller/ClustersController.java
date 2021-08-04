package com.ydw.platform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IClustersService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/clusters")
public class ClustersController extends BaseController{
	
	@Autowired
	private IClustersService clustersServiceImpl;

	/**
     * 获取区服信息
     * @return
     */
    @GetMapping(value = "/getRegions")
    public ResultInfo getRegions(@RequestParam(required = false) String appId) {
        return clustersServiceImpl.getUseableRegions(appId);
    }
}

