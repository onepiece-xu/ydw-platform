package com.ydw.game.controller;


import com.ydw.game.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.IAppService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/app")
public class AppController extends BaseController{

	@Autowired
	private IAppService appService;

	@Autowired
	private IEnterpriseService iEnterpriseService;

	
	@GetMapping("/getAllApps")
	public ResultInfo getAllApps(){
		return appService.getAllApps();
	}

	/**
	 * 同步获取app列表更新到数据库
	 */
	@PostMapping("/getSyncApps")
	@ResponseBody
	public ResultInfo getSyncApps(@RequestBody String body ){
		return appService.getSyncApps(body);
	}

	/**
	 * 获取app列表到数据库
	 */
	@GetMapping("/getApps")
	@ResponseBody
	public ResultInfo getApps(@RequestParam( required=false ) String body, @RequestParam( required=false ) Integer pageSize,
								  @RequestParam( required=false ) Integer pageNum){
		return appService.getApps(body,buildPage());
	}
	//
	@GetMapping("/getEnterpriseId")
	public String getEnterpriseId(){

		return iEnterpriseService.getEnterpriseId();
	}
}

