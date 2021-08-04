package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.NetBarInfo;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * @since 2020-12-09
 */
public interface INetBarInfoService extends IService<NetBarInfo> {


    ResultInfo addNetBarInfo(HttpServletRequest request,NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3,
                             MultipartFile file4, MultipartFile file5, MultipartFile logo);

    ResultInfo getNetBarList(HttpServletRequest request,String name, String province, String city, String district, Page buildPage);

    ResultInfo getNetBarInfo(HttpServletRequest request,String id);

    ResultInfo updateNetBar(HttpServletRequest request, NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4, MultipartFile file5, MultipartFile logo);

    ResultInfo getAllNetBarList(HttpServletRequest request,Page buildPage);

    ResultInfo deleteNetbar(HttpServletRequest request,List<String> ids);

    ResultInfo getBaseStation(HttpServletRequest request, Page buildPage);

    ResultInfo getMatchStation(HttpServletRequest request, Page buildPage);
}
