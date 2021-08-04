package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.model.db.TbAppApprove;


import com.ydw.open.utils.ResultInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface ITbAppApproveService extends IService<TbAppApprove> {




    ResultInfo getList(HttpServletRequest request, Integer status, Integer  cooperationType, String search, Page buildPage);

    ResultInfo deleteApproves(HttpServletRequest request, List<String> ids);



    ResultInfo getAppApprove(HttpServletRequest request, String id);

    /**
     * 审批 新建提交& 新建保存
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
     * @param isSave
     * @return
     */
    ResultInfo createUserApprove(HttpServletRequest request, MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher, String gameType,
                                 Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize, Integer type, String accountPath, String dataPath,
                                 String packagePath, Integer hasAccessId, String accessId,Integer schInstall,Integer installMaxNumber,String identification,Integer screen,Integer showTime,Integer hasPage,String region,Integer maxConnect,MultipartFile page,Integer isSave);

    ResultInfo updateApproves(HttpServletRequest request, String id,MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher
            , String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize, Integer type, String accountPath
            , String dataPath, String packagePath, Integer hasAccessId, String accessId,Integer schInstall,Integer installMaxNumber,String identification,Integer screen, Integer showTime,Integer hasPage,String region,Integer maxConnect,MultipartFile page
            ,Integer isSave);


    ResultInfo revert(HttpServletRequest request, List<String> ids);

    ResultInfo getGameType(HttpServletRequest request);

    ResultInfo getAppsInUse(HttpServletRequest request);



    ResultInfo approveApp(HttpServletRequest request, TbAppApprove appApprove);

    ResultInfo getClusterInfo();
}
