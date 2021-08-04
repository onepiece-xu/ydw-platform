package com.ydw.user.controller;


import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.service.ITbUserAppsService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-04-23
 */

@Controller
@RequestMapping("/v1/userApps")
public class TbUserAppsController extends BaseController {
    @Autowired
    private ITbUserAppsService iTbUserAppsService;

    @RequestMapping(value = "/createUserApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createUserApp(HttpServletRequest request, @RequestBody TbUserApps app) {

        return iTbUserAppsService.createUserApp(request, app);
    }

    @RequestMapping(value = "/updateUserApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateUserApp(HttpServletRequest request, @RequestBody TbUserApps app) {

        return iTbUserAppsService.updateUserApp(request, app);
    }

    @RequestMapping(value = "/deleteUserApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteUserApp(HttpServletRequest request, @RequestBody List<String> ids) {

        return iTbUserAppsService.deleteUserApp(request, ids);
    }

    /**
     *列表
     * @param request
     * @param name
     * @param accessId
     * @param type
     * @param strategyName
     * @param identification
     * @param status
     * @return
     */

    @RequestMapping(value = "/getUserApps", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUserApps(HttpServletRequest request,@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String accessId,
                                 @RequestParam(required = false) Integer type,
                                 @RequestParam(required = false) String strategyName,
                                 @RequestParam(required = false) String identification,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) String schStatus) {
        return iTbUserAppsService.getUserApps(request, name, accessId, type, strategyName, identification, status, search,schStatus,buildPage());
    }

    @RequestMapping(value = "/getUserApp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUserApp(HttpServletRequest request,@PathVariable String id) {
        return iTbUserAppsService.getUserApp(request, id);
    }

    @GetMapping(value = "/getInstallApps/{id}")
    @ResponseBody
    public ResultInfo getInstallApps(HttpServletRequest request,@PathVariable String id,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize) {

        return iTbUserAppsService.getInstallApps(request,id,buildPage());
    }

    /**
     *获取未安装列表
     * @param request
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getUnInstallApps/{id}")
    @ResponseBody
    public ResultInfo getUnInstallApps(HttpServletRequest request,@PathVariable String id,
                                       @RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize) {

        return iTbUserAppsService.getUnInstallApps(request,id,buildPage());
    }

    /**
     * 禁用
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "/disableApps", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo disableApps(HttpServletRequest request, @RequestBody List<String> ids) {

        return iTbUserAppsService.disableApps(request, ids);
    }

    /**
     * 启用
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "/enableApps", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo enableApps(HttpServletRequest request, @RequestBody List<String> ids) {

        return iTbUserAppsService.enableApps(request, ids);
    }

    /**
     * 导入应用
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/fileUpload", produces = "application/json; charset=utf-8")
    public ResultInfo  UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return iTbUserAppsService.ajaxUploadExcel(request, response);
    }

    /**
     * 根据标签获取应用列表
     */
    @PostMapping(value = "/getAppListByTag")
    @ResponseBody
    public ResultInfo getAppListByTag(HttpServletRequest request,@RequestBody String body) {

        return iTbUserAppsService.getAppListByTag(request,body);

    }
    /**
     *获取云游戏图片列表
     */
    @GetMapping(value = "/getAppPictures")
    @ResponseBody
    public ResultInfo getAppPictures() {

        return iTbUserAppsService.getAppPictures();

    }


    /**
     * 应用上传apk
     * @param request
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/upload", produces = "application/json; charset=utf-8")
    public ResultInfo  appFileUpload(HttpServletRequest request,@RequestParam String appId) throws Exception {
        return iTbUserAppsService.appFileUpload(request, appId);
    }

    /**
     * 获取应用平台类型列表
     */
    @GetMapping(value = "/getPlatformList")
    @ResponseBody
    public ResultInfo getPlatformList() {

        return iTbUserAppsService.getPlatformList();

    }

}

