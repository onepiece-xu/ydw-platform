package com.ydw.open.controller;


import com.ydw.open.model.db.TbAppApprove;
import com.ydw.open.service.ITbAppApproveService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
@Controller
@RequestMapping("/v1/appApprove")
public class TbAppApproveController extends BaseController {

    @Autowired
    private ITbAppApproveService iTbAppApproveService;

    /**应用的审批的提交 保存
     *
     * @param request
     * @param accessIdPic
     * @param name
     * @param englishName
     * @param description
     * @param gameMaker
     * @param gamePublisher
     * @param gameType
     * @param cooperationType
     * @param authPeriod
     * @param packageFileName
     * @param packageName
     * @param size
     * @param realSize
     * @param type
     * @param accountPath
     * @param dataPath
     * @param packagePath
     * @param hasAccessId
     * @param accessId
     * @param isSave  0 审批提交 1 审批保存
     * @return
     */
    @RequestMapping(value = "/createUserApprove", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createUserAppApprove(HttpServletRequest request,
                                        @RequestParam(value = "accessIdPic", required = false) MultipartFile accessIdPic,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "englishName", required = false) String englishName,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam(value = "gameMaker", required = false) String gameMaker,
                                        @RequestParam(value = "gamePublisher", required = false) String gamePublisher,
                                        @RequestParam(value = "gameType", required = false) String gameType,
                                        @RequestParam(value = "cooperationType", required = false) Integer cooperationType,
                                        @RequestParam(value = "authPeriod", required = false) String authPeriod,
                                        @RequestParam(value = "packageFileName", required = false) String packageFileName,
                                        @RequestParam(value = "packageName", required = false) String packageName,
                                        @RequestParam(value = "size", required = false) Integer size,
                                        @RequestParam(value = "realSize", required = false) Integer realSize,
                                        @RequestParam(value = "type", required = false) Integer type,
                                        @RequestParam(value = "accountPath", required = false) String accountPath,
                                        @RequestParam(value = "dataPath", required = false) String dataPath,
                                        @RequestParam(value = "packagePath", required = false) String packagePath,
                                        @RequestParam(value = "hasAccessId", required = false) Integer hasAccessId,
                                        @RequestParam(value = "accessId", required = false) String accessId,
                                        @RequestParam(value = "schInstall", required = false) Integer schInstall,
                                        @RequestParam(value = "installMaxNumber", required = false) Integer installMaxNumber,
                                        @RequestParam(value = "identification", required = false) String identification,
                                        @RequestParam(value = "screen", required = false) Integer screen,
                                        @RequestParam(value = "showTime", required = false) Integer showTime,
                                        @RequestParam(value = "hasPage", required = false) Integer hasPage,
                                        @RequestParam(value = "region", required = false) String region,
                                           @RequestParam(value = "maxConnect", required = false) Integer maxConnect,
                                           @RequestParam(value = "page", required = false) MultipartFile page,
                                           @RequestParam(value = "isSave", required = false) Integer isSave) {

        return iTbAppApproveService.createUserApprove(request,accessIdPic,name,englishName,description,gameMaker,gamePublisher,gameType,cooperationType,authPeriod,packageFileName,packageName,size,realSize,type,
                accountPath,dataPath,packagePath,hasAccessId,accessId,schInstall,installMaxNumber,identification,screen,showTime,hasPage,region,maxConnect,page,isSave);
    }

    /**
     *应用审批列表
     */
    @RequestMapping(value = "/getAppApproveList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getList(HttpServletRequest request,
                              @RequestParam(required = false) Integer status,
                              @RequestParam(required = false) Integer  cooperationType,
                              @RequestParam(required = false) String  search,
                              @RequestParam(required = false) Integer pageNum,
                              @RequestParam(required = false) Integer pageSize) {

        return iTbAppApproveService.getList(request,status,cooperationType,search,buildPage());
    }
    /**
     *应用审批详情
     */
    @RequestMapping(value = "/getAppApprove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getAppApprove(HttpServletRequest request,@PathVariable String id) {

        return iTbAppApproveService.getAppApprove(request,id);
    }





    /**
     *应用刪除
     */
    @RequestMapping(value = "/deleteApproves", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteApproves(HttpServletRequest request,@RequestBody List<String> ids ) {

        return iTbAppApproveService.deleteApproves(request,ids);
    }

    /**
     *应用修改
     */
    @RequestMapping(value = "/updateApproves", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateApproves(HttpServletRequest request,
                                     @RequestParam(value = "id") String id,
                                     @RequestParam(value = "accessIdPic", required = false) MultipartFile accessIdPic,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "englishName", required = false) String englishName,
                                     @RequestParam(value = "description", required = false) String description,
                                     @RequestParam(value = "gameMaker", required = false) String gameMaker,
                                     @RequestParam(value = "gamePublisher", required = false) String gamePublisher,
                                     @RequestParam(value = "gameType", required = false) String gameType,
                                     @RequestParam(value = "cooperationType", required = false) Integer cooperationType,
                                     @RequestParam(value = "authPeriod", required = false) String authPeriod,
                                     @RequestParam(value = "packageFileName", required = false) String packageFileName,
                                     @RequestParam(value = "packageName", required = false) String packageName,
                                     @RequestParam(value = "size", required = false) Integer size,
                                     @RequestParam(value = "realSize", required = false) Integer realSize,
                                     @RequestParam(value = "type", required = false) Integer type,
                                     @RequestParam(value = "accountPath", required = false) String accountPath,
                                     @RequestParam(value = "dataPath", required = false) String dataPath,
                                     @RequestParam(value = "packagePath", required = false) String packagePath,
                                     @RequestParam(value = "hasAccessId", required = false) Integer hasAccessId,
                                     @RequestParam(value = "accessId", required = false) String accessId,
                                     @RequestParam(value = "schInstall", required = false) Integer schInstall,
                                     @RequestParam(value = "installMaxNumber", required = false) Integer installNumber,
                                     @RequestParam(value = "identification", required = false) String identification,
                                     @RequestParam(value = "screen", required = false) Integer screen,
                                     @RequestParam(value = "showTime", required = false) Integer showTime,
                                     @RequestParam(value = "hasPage", required = false) Integer hasPage,
                                     @RequestParam(value = "region", required = false) String region,
                                     @RequestParam(value = "maxConnect", required = false) Integer maxConnect,
                                     @RequestParam(value = "page", required = false) MultipartFile page,
                                     @RequestParam(value = "isSave") Integer isSave) {

        return iTbAppApproveService.updateApproves(request,id,accessIdPic,name,englishName,description,gameMaker,gamePublisher,gameType,cooperationType,authPeriod,packageFileName,packageName,size,realSize,type,
                accountPath,dataPath,packagePath,hasAccessId,accessId,schInstall,installNumber,identification,screen,showTime,hasPage,region,maxConnect,page,isSave);
    }

    /**
     * 撤销审核
     */
    @RequestMapping(value = "/revert", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo revert(HttpServletRequest request,@RequestBody List<String> ids){
        return iTbAppApproveService.revert(request,ids);
    }

    /**
     * 获取应用游戏类型
     */

    @RequestMapping(value = "/getGameType", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getGameType(HttpServletRequest request){
        return iTbAppApproveService.getGameType(request);
    }


    /**
     * 获取用户上架应用
     */
    @RequestMapping(value = "/getAppsInUse", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getAppsInUse(HttpServletRequest request){
        return iTbAppApproveService.getAppsInUse(request);
    }


    /**
     *  应用审批
     */
    @RequestMapping(value = "/approveApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo approveApp(HttpServletRequest request, @RequestBody TbAppApprove appApprove) {

        return iTbAppApproveService.approveApp(request,appApprove);
    }


    /**
     * 获取集群信息
     */
    @RequestMapping(value = "/getRegion", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getClusterInfo() {

        return iTbAppApproveService.getClusterInfo();
    }

}

