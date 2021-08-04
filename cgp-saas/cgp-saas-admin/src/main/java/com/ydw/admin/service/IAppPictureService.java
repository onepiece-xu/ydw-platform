package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.AppPicture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.UploadPictureVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-02-18
 */
public interface IAppPictureService extends IService<AppPicture> {


    /**
     * logo图片上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadLogoPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);

    /**
     * 删除图片
     * @param uploadPictureVO
     * @return
     */
    ResultInfo deletePicture(UploadPictureVO uploadPictureVO);

    /**
     * 轮播图上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadCarouselPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);

    /**
     * 背景图上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadBackgroundPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);

    /**
     * banner图片上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadBannerPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);

    /**
     * 卡片图上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadCardPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);

    /**
     * 推荐图上传
     * @param request
     * @param uploadPictureVO
     * @return
     */
    ResultInfo uploadRecommendedPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO);
}
