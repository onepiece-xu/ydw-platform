package com.ydw.resource.controller;

import com.ydw.resource.service.IClustersService;
import com.ydw.resource.utils.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/clusters")
public class ClustersController extends BaseController{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IClustersService clustersServiceImpl;

	@GetMapping("/getClusters")
	public ResultInfo getClusters(@RequestParam(required = false) String appId){
        String identification = super.getEnterpriseId();
        return clustersServiceImpl.getClusters(identification, appId);
	}

	@PostMapping(value = "/updateExternalIp")
	public ResultInfo updateExternalIp(@RequestBody HashMap<String,String> map){
		String oldIp = map.get("oldIp");
		String newIp = map.get("newIp");
		logger.info("oldIp:{},newIp:{}",oldIp,newIp);
		return clustersServiceImpl.updateExternalIp(oldIp, newIp);
	}
}

