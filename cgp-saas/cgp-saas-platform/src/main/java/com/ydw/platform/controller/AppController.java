package com.ydw.platform.controller;


import com.ydw.platform.model.vo.EnableApps;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IAppService;
import com.ydw.platform.service.IEnterpriseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	 * 获取pc游戏列表，用来选择虚拟按键
	 * @return
	 */
	@GetMapping("/getPCAppsForVirtualKey")
	public ResultInfo getPCAppsForVirtualKey(){
		return appService.getPCAppsForVirtualKey(super.buildPage());
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
	/**
	 * 上下架SAAS 云游戏
	 */
	@PostMapping("/enable")
	@ResponseBody
	public ResultInfo enableApps(@RequestBody EnableApps vo){
		return appService.enableApps(vo);
	}
	/**
	 * 获取应用平台类型列表
	 */
	@GetMapping(value = "/getPlatformList")
	@ResponseBody
	public ResultInfo getPlatformList() {
		return appService.getPlatformList();
	}

	/**
	 * 游戏详情页数据
	 */
	@GetMapping("/getAppInfo")
	@ResponseBody
	public ResultInfo getAppInfo(@Param("id") String id) {
		return appService.getAppInfo(id);
	}

}

