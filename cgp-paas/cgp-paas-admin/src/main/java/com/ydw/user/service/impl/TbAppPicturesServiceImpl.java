package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.user.model.db.TbAppPictures;
import com.ydw.user.dao.TbAppPicturesMapper;
import com.ydw.user.service.ITbAppPicturesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.FTPUtil;
import com.ydw.user.utils.ResultInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-12-28
 */
@Service
public class TbAppPicturesServiceImpl extends ServiceImpl<TbAppPicturesMapper, TbAppPictures> implements ITbAppPicturesService {
    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.uploadPath}")
    private String uploadPath;

    @Value("${config.uploadPathPaasPic}")
    private String uploadPathPaasPic;


    @Autowired
    private TbAppPicturesMapper tbAppPicturesMapper;

    @Override
    public ResultInfo upload(HttpServletRequest request, String appId) {
        String type = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile logoPic = multipartRequest.getFile("logoPic");
        MultipartFile middlePic = multipartRequest.getFile("midPic");
        MultipartFile smallPic = multipartRequest.getFile("smallPic");
        MultipartFile largePic = multipartRequest.getFile("bigPic");
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        if (logoPic != null) {
            type = "logoPic";
            try {
//                logoPic.transferTo(file)
                FTPUtil.uploadFiles(ftpClient, uploadPathPaasPic + "logo/" + appId + ".jpg", logoPic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (smallPic != null) {
            try {
                type = "smallPic";
                FTPUtil.uploadFiles(ftpClient, uploadPathPaasPic + "small/" + appId + ".jpg", smallPic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (middlePic != null) {
            try {
                type = "middlePic";
                FTPUtil.uploadFiles(ftpClient, uploadPathPaasPic + "middle/" + appId + ".jpg", middlePic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (largePic != null) {
            try {
                type = "largePic";
                FTPUtil.uploadFiles(ftpClient, uploadPathPaasPic + "large/" + appId + ".jpg", largePic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        QueryWrapper<TbAppPictures> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", appId);
        wrapper.eq("valid", 1);
        List<TbAppPictures> tbAppPictures = tbAppPicturesMapper.selectList(wrapper);
        if (tbAppPictures.size() == 0) {
            TbAppPictures ap = new TbAppPictures();
            ap.setAppId(appId);
            ap.setMidPic( "paas/game/middle/" + appId + ".jpg");
            ap.setBigPic( "paas/game/large/" + appId + ".jpg");
            ap.setLogoPic("paas/game/logo/" + appId + ".jpg");
            ap.setSmallPic( "paas/game/small/" + appId + ".jpg");
            ap.setValid(true);
            tbAppPicturesMapper.insert(ap);
        }
        return  ResultInfo.success("上传成功！");
    }
}
