package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbAppApprove;
import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface ITbAppApproveService extends IService<TbAppApprove> {




    ResultInfo getList(HttpServletRequest request, Integer status,Integer  cooperationType,String search, Page buildPage );

    ResultInfo getAppApprove(HttpServletRequest request, String id);



    ResultInfo approveApp(HttpServletRequest request, TbAppApprove appApprove);



//    ResultInfo createUserApprove(HttpServletRequest request, MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher, String gameType,
//                                 Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize, Integer type, String accountPath, String dataPath,
//                                 String packagePath, Integer hasAccessId, String accessId, Integer isSave);

//    ResultInfo updateApproves(HttpServletRequest request, String id, MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher
//            , String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize, Integer type, String accountPath
//            , String dataPath, String packagePath, Integer hasAccessId, String accessId, Integer isSave);


//    ResultInfo deleteApproves(HttpServletRequest request, List<String> ids);


//    ResultInfo revert(HttpServletRequest request, List<String> ids);

    ResultInfo getGameType(HttpServletRequest request);

    ResultInfo launchApps(HttpServletRequest request, TbUserApps tbUserApps);
}
