package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.TbUserApprove;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.utils.ResultInfo;
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

    ResultInfo register(HttpServletRequest request, HttpServletResponse response,MultipartFile file, TbUserApprove user);

    ResultInfo login(HttpServletRequest request, HttpServletResponse response, TbUserApprove user);

    ResultInfo updateUserApprove(HttpServletRequest request,HttpServletResponse response,MultipartFile file, TbUserApprove user);

    /**
     * 审批用户
     * @param request
     * @param approve
     * @return
     */
    ResultInfo approve(HttpServletRequest request, TbUserApprove approve) ;

    ResultInfo getServices(HttpServletRequest request);

    ResultInfo getUserApproveList(HttpServletRequest request, String startTime, String endTime, Page buildPage, String enterpriseName);

    ResultInfo getUserInfo(HttpServletRequest request, String id);
}
