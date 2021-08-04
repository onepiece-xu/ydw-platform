package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.BannerMapper;
import com.ydw.admin.dao.BannerTypeMapper;
import com.ydw.admin.model.db.Banner;
import com.ydw.admin.model.db.BannerType;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IBannerService;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.SequenceGenerator;
import io.netty.util.internal.StringUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.net.ftp.FtpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-06-10
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Autowired
    private BannerMapper tbBannerMapper;

    @Value("${url.pics}")
    private String pics;

//    @Value("${url.customizeDomain}")
//    private String customizeDomain;

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

    @Autowired
    private BannerTypeMapper bannerTypeMapper;

    @Override
    public ResultInfo addBanner(HttpServletRequest request, Banner tbBanner) {
        String uid = SequenceGenerator.sequence();
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid",1);
        Integer bannerType = tbBanner.getType();
        Integer platform = tbBanner.getPlatform();
        queryWrapper.eq("type",bannerType);
        queryWrapper.eq("platform",platform);
        List<Banner> list = tbBannerMapper.selectList(queryWrapper);
        if(list.size()>=5){
            return  ResultInfo.fail("已达到Banner创建上限，请删除后再新建！");
        }
        Integer orderNum = tbBanner.getOrderNum();
        Integer type = tbBanner.getType();

        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("order_num",orderNum);
        wrapper.eq("valid",1);
        wrapper.eq("type",type);
        wrapper.eq("platform",platform);
        Banner banners = tbBannerMapper.selectOne(wrapper);
        if(null!=banners){
            return  ResultInfo.fail("已存在相同位置banner！请确认后再添加！");
        }else{
            tbBanner.setValid(true);
//            tbBanner.setPicPath(pics+"game/banner/banner_" +orderNum +"_"+type+ ".jpg");
            tbBanner.setPicPath(pics+"game/banner/banner_"+uid+".jpg");
            tbBanner.setDelPath("pics/game/banner/banner_"+uid+".jpg");
            tbBannerMapper.insert(tbBanner);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");

        if(null!=file){
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//   如果上传出现问题，可启用ftp的被动模式就可以解决         ftpClient.enterLocalPassiveMode();
            try {
//                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +orderNum +"_"+type+ ".jpg", file.getInputStream());
                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +uid+".jpg", file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateBanner(HttpServletRequest request, Banner tbBanner) {
        String uid = SequenceGenerator.sequence();
        Integer id = tbBanner.getId();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        wrapper.eq("valid",1);
        Banner banner = tbBannerMapper.selectOne(wrapper);
        try {
            if (tbBanner.getPlatform()!=banner.getPlatform()){
                return  ResultInfo.fail("编辑失败,不支持编辑图片的端类型！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String delPath = banner.getDelPath();
        if(null!=file){
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();
            try {
//                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +orderNum +"_"+type+ ".jpg", file.getInputStream());
                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +uid+".jpg", file.getInputStream());
                ftpClient.deleteFile(delPath);
                tbBanner.setPicPath(pics+"game/banner/banner_"+uid+".jpg");
                tbBanner.setDelPath("pics/game/banner/banner_"+uid+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tbBannerMapper.updateById(tbBanner);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBanners(HttpServletRequest request, Integer type, Page page) {
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        if(null!=type){
            wrapper.eq("type",type);
        }
        wrapper.eq("valid",1);
        Page<Banner> banners = tbBannerMapper.selectPage(page,wrapper);
        return ResultInfo.success(banners);

    }



    @Override
    public ResultInfo addBanners(HttpServletRequest request, String body) {
        System.out.println(body);
        JSONObject object = JSONObject.parseObject(body);
//        JSONObject data = object.getJSONObject("data");
        Banner tbBanner = new Banner();
        JSONArray banners = object.getJSONArray("banners");
        List<Banner> getList = tbBannerMapper.getBanners();
        for (int i = 0; i <getList.size() ; i++) {
            tbBannerMapper.deleteById(getList.get(i).getId());
        }
        for (int i = 0; i < banners.size(); i++) {
            if (banners.getJSONObject(i).get("picPath").equals("")) {
                continue;
            } else {
                String description = banners.getJSONObject(i).get("description").toString();
                Integer orderNumber = (Integer) banners.getJSONObject(i).get("orderNumber");

                String picPath = banners.getJSONObject(i).get("picPath").toString();
                String accessPath = banners.getJSONObject(i).get("accessPath").toString();
                if (!StringUtil.isNullOrEmpty(description)) {
                    tbBanner.setDescription(description);
                }

                if (!StringUtil.isNullOrEmpty(accessPath)) {
                    tbBanner.setAccessPath(accessPath);
                }
                if (!StringUtil.isNullOrEmpty(picPath)) {
                    tbBanner.setPicPath(picPath);
                }
                tbBanner.setOrderNum(orderNumber);
                tbBanner.setValid(true);
                tbBannerMapper.insert(tbBanner);
            }
        }


        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteBanners(HttpServletRequest request, List<Integer> ids) {
        Banner banner = new Banner();
        for ( Integer i :ids) {
            banner.setId(i);
            tbBannerMapper.deleteById(banner);
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo addBannerType(BannerType bannerType) {
        bannerTypeMapper.insert(bannerType);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBannerTypes() {
        QueryWrapper<BannerType> wrapper = new QueryWrapper<>();
        List<BannerType> bannerTypes = bannerTypeMapper.selectList(wrapper);
        return ResultInfo.success(bannerTypes);
    }

    @Override
    public ResultInfo updateBannerType(BannerType bannerType) {
        bannerTypeMapper.updateById(bannerType);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteBannerType(List<Integer> ids) {
        for(Integer id:ids){
            bannerTypeMapper.deleteById(id);
        }
        return ResultInfo.success();
    }
}
