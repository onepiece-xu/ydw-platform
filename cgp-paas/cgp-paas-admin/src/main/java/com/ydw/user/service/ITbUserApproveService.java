package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.user.model.db.TbUserApprove;
import com.ydw.user.utils.ResultInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface ITbUserApproveService extends IService<TbUserApprove> {
    ResultInfo approve(HttpServletRequest request, TbUserApprove approve) ;

    /**
     * 列表
     * @param request
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @param enterpriseName
     * @return
     */
        ResultInfo getUserApproveList(HttpServletRequest request, String startTime, String endTime, Page buildPage, String enterpriseName);

    ResultInfo getUserInfo(HttpServletRequest request, String id);

    ResultInfo updateUserApprove(HttpServletRequest request, HttpServletResponse response, MultipartFile file, TbUserApprove user);


//    ResultInfo register(HttpServletRequest request, HttpServletResponse response, MultipartFile file, TbUserApprove user);
//
//    ResultInfo login(HttpServletRequest request, HttpServletResponse response, TbUserApprove user);
//
//    ResultInfo updateUserApprove(HttpServletRequest request, HttpServletResponse response, MultipartFile file, TbUserApprove user);


}
