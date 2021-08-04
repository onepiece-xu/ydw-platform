package com.ydw.admin.controller;


import com.ydw.admin.model.db.App;
import com.ydw.admin.model.vo.CheckTagsVO;
import com.ydw.admin.model.vo.EnableApps;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IAppService;
import com.ydw.admin.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

	/**
	 *saas后台编辑 应用信息
	 * @return
	 */
	@PostMapping("/updateApp")
	@ResponseBody
	public ResultInfo updateApp (@RequestBody App app){
		return  appService.updateApp(app);
	}


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
	 * 获取app列表
	 */
	@GetMapping("/getApps")
	@ResponseBody
	public ResultInfo getApps(@RequestParam( required=false ) String search, @RequestParam(required = false) String valid, @RequestParam(required = false) Integer type){
		return appService.getApps(search,valid,type,buildPage());
	}
	//
	@GetMapping("/getEnterpriseId")
	public String getEnterpriseId(){
		return iEnterpriseService.getEnterpriseId();
	}


	/**
	 * 根据用户获取绑定列表
	 */
	@GetMapping("/getAppBindTag")
	public ResultInfo getAppBind(@RequestParam String appId){
		return appService.getAppBind(appId,buildPage());
	}

	/**
	 * 根据用户获取绑定列表
	 */
	@GetMapping("/getAppUnBindTag")
	public ResultInfo getAppUnBind(@RequestParam String appId){
		return appService.getAppUnBind(appId,buildPage());
	}

	/**
	 * 给应用绑定标签
	 */
	@PostMapping(value = "/bindTags")
	@ResponseBody
	public ResultInfo bindTags(HttpServletRequest request, @RequestBody String body) {

		return appService.bindTags(request,body);

	}


	/**
	 * 应用上传图片
	 * @param request
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/upload", produces = "application/json; charset=utf-8")
	public ResultInfo  appPictureUpload(HttpServletRequest request,@RequestParam String appId) throws Exception {
		return appService.appPictureUpload(request, appId);
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
	展示应用对应的已绑定和未绑定的标签列表
	 */
	@GetMapping(value = "/checkTags")
	@ResponseBody
	public ResultInfo checkTags(HttpServletRequest request, @RequestParam String appId) {

		return appService.checkTags(request,appId);

	}

	/**
	 在展示应用对应的已绑定和未绑定的标签列表   绑定标签
	 */
	@PostMapping(value = "/Tagging")
	@ResponseBody
	public ResultInfo Tagging(HttpServletRequest request, @RequestBody CheckTagsVO vo) {

		return appService.Tagging(request,vo);

	}

	/**
	 * 获取应用平台类型列表
	 */
	@GetMapping(value = "/getPlatformList")
	@ResponseBody
	public ResultInfo getPlatformList() {
		return appService.getPlatformList();
	}
}


