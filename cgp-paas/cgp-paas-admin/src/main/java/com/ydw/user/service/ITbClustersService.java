package com.ydw.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbClusters;

import com.ydw.user.model.vo.CreateClusterVO;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
public interface ITbClustersService extends IService<TbClusters> {

    ResultInfo createCluster(HttpServletRequest request, CreateClusterVO cluster);

    ResultInfo updateCluster(HttpServletRequest request, CreateClusterVO cluster);

    ResultInfo deleteClusters(HttpServletRequest request, List<String> ids);

//    ResultInfo getClusters(HttpServletRequest request, String name, Integer type, Integer isLocal, String description, String apiUrl, String accessIp, Integer schStatus,String search, Integer pageNum, Integer pageSize);

    ResultInfo getClusterById(HttpServletRequest request, String id);

    ResultInfo getClusters(HttpServletRequest request, String name, Integer type, Integer isLocal, String description, String apiUrl, String accessIp, Integer schStatus, String search, Page buildPage);

    ResultInfo bindConfig(String body);

    ResultInfo getTurn(HttpServletRequest request, String id);

    ResultInfo getSignal(HttpServletRequest request, String id);

    ResultInfo getServiceClusters();

    ResultInfo getClusterStatus(HttpServletRequest request, String id);
}
