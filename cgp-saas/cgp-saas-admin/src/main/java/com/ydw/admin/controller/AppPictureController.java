package com.ydw.admin.controller;


import com.ydw.admin.model.db.AppPicture;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.UploadPictureVO;
import com.ydw.admin.service.IAppPictureService;
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
 * @author xulh
 * @since 2021-02-18
 */
@Controller
@RequestMapping("/appPicture")
public class AppPictureController extends BaseController{
    @Autowired
    private IAppPictureService iAppPictureService;

    /**
     * 单张添加logo图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadLogoPicture")
    public ResultInfo uploadLogoPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadLogoPicture(request,uploadPictureVO);
    }
    /**
     * 删除图片接口
     * @param
     * @param
     * @return
     */
    @PostMapping("/deletePicture")
    public ResultInfo deletePicture(@RequestBody UploadPictureVO uploadPictureVO){
        return  iAppPictureService.deletePicture(uploadPictureVO);
    }
    /**
     * 单张添加轮播图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadCarouselPicture")
    public ResultInfo uploadCarouselPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadCarouselPicture(request,uploadPictureVO);
    }
    /**
     * 单张添加背景图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadBackgroundPicture")
    public ResultInfo uploadBackgroundPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadBackgroundPicture(request,uploadPictureVO);
    }
    /**
     * 单张添加banner图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadBannerPicture")
    public ResultInfo uploadBannerPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadBannerPicture(request,uploadPictureVO);
    }

    /**
     * 单张添加首页展示的card图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadCardPicture")
    public ResultInfo uploadCardPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadCardPicture(request,uploadPictureVO);
    }
    /**
     * 单张添加推荐图片
     * @param request
     * @param
     * @return
     */
    @PostMapping("/uploadRecommendedPicture")
    public ResultInfo uploadRecommendedPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO){
        return  iAppPictureService.uploadRecommendedPicture(request,uploadPictureVO);
    }
}

