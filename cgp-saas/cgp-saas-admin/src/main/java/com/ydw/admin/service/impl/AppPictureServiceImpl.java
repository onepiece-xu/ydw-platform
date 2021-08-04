package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.AppPicture;
import com.ydw.admin.dao.AppPictureMapper;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.UploadPictureVO;
import com.ydw.admin.service.IAppPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-02-18
 */
@Service
public class AppPictureServiceImpl extends ServiceImpl<AppPictureMapper, AppPicture> implements IAppPictureService {
    Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private AppPictureMapper appPictureMapper;
    @Value("${config.appPicture}")
    private String path;
    @Value("${url.pics}")
    private String pics;
    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;
//    @Value("${config.uploadPath}")
//    private String uploadPath;


    @Override
    public ResultInfo uploadLogoPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="logo";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);
    }


/*
    @Override
    public ResultInfo deletePicture(UploadPictureVO uploadPictureVO) {
//        String picurl = "/home/web/files/pics/app_pictures/666/666_1.jpg";
        String pictureUrl = uploadPictureVO.getPictureUrl();
        File file = new File(path+pictureUrl);
        if ( true==file.exists()) {
            boolean flag = false;
            flag = file.delete();
            if (flag) {
                logger.debug("成功删除无效图片文件:" + file.getName());
            }else{
                return ResultInfo.success("删除失败！");
            }
        }
        return ResultInfo.success("删除成功！");
    }
*/
    @Override
    public ResultInfo uploadCarouselPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="carousel";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);

    }

    @Override
    public ResultInfo uploadBackgroundPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="background";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);
    }

    @Override
    public ResultInfo uploadBannerPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="banner";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);
    }

    @Override
    public ResultInfo uploadCardPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="card";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);
    }

    @Override
    public ResultInfo uploadRecommendedPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO) {
        String type="recommended";
        String msg = uplodPicture(request, uploadPictureVO,type);
        return ResultInfo.success(msg);
    }

/*
    private String uplodPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO,String type){
        String appId = uploadPictureVO.getAppId();
        Integer number = uploadPictureVO.getNumber();
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");
        if (file.isEmpty()) {
            return "图片文件不能为空！";
        }
        File targetFile;
        String originalName = file.getOriginalFilename();
//        String fileName= UUID.randomUUID().toString().replace("-", "");
        String newName="";
        if(null!=number){
            newName = appId + "_" +type+ "_"+number+originalName.substring(originalName.lastIndexOf("."));
        }else{
            newName = appId + "_" +type+ originalName.substring(originalName.lastIndexOf("."));
        }
//        String url = "app_pictures/"+appId +"/"+ newName;
        //获取文件夹路径
        File file1 = new File(path + "/" + appId);
        //如果文件夹不存在则创建
        if (!file1.exists() && !file1.isDirectory()) {
            file1.mkdir();
        }
        //将图片存入文件夹
        targetFile = new File(file1, newName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            return "图片文件上传失败！";
        }
        return "上传成功！";
    }
*/
    private String uplodPicture(HttpServletRequest request, UploadPictureVO uploadPictureVO,String type){
        String appId = uploadPictureVO.getAppId();
        Integer number = uploadPictureVO.getNumber();
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");
        if (file.isEmpty()) {
            return "图片文件不能为空！";
        }
        String originalName = file.getOriginalFilename();

        String newName="";
        if(null!=number){
            newName = appId + "_" +type+ "_"+number+originalName.substring(originalName.lastIndexOf("."));
        }else{
            newName = appId + "_" +type+ originalName.substring(originalName.lastIndexOf("."));
        }
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.makeDirectory(path+appId);
            FTPUtil.uploadFiles(ftpClient, path +appId+"/"+ newName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传成功！";
    }

    @Override
    public ResultInfo deletePicture(UploadPictureVO uploadPictureVO) {
//        String picurl = "/home/web/files/pics/app_pictures/666/666_1.jpg";
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        String pictureUrl = uploadPictureVO.getPictureUrl();
        try {
            boolean flag = false;
            FTPFile[] ftpFiles = ftpClient.listFiles(path + pictureUrl);
            int length = ftpFiles.length;
            System.out.println(length);
            if(0!=length){
                flag = ftpClient.deleteFile(path +pictureUrl);
            }
            if (flag) {
                logger.debug("成功删除无效图片文件:" + path + pictureUrl);
            }else{
                return ResultInfo.success("删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultInfo.success("删除成功！");
    }
}
