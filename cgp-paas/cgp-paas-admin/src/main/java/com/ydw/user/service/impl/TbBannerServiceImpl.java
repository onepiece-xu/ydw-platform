package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.model.db.TbBanner;
import com.ydw.user.dao.TbBannerMapper;
import com.ydw.user.service.ITbBannerService;

import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class TbBannerServiceImpl extends ServiceImpl<TbBannerMapper, TbBanner> implements ITbBannerService {
    @Autowired
    private TbBannerMapper tbBannerMapper;
    @Override
    public ResultInfo addBanner(HttpServletRequest request, TbBanner tbBanner) {

        tbBannerMapper.insert(tbBanner);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateBanner(HttpServletRequest request, TbBanner tbBanner) {
        tbBannerMapper.updateById(tbBanner);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBanners(HttpServletRequest request) {
        List<TbBanner> banners = tbBannerMapper.getBanners();
        return ResultInfo.success(banners);
    }

    @Override
    public ResultInfo addBanners(HttpServletRequest request, String body) {
        System.out.println(body);
        JSONObject object = JSONObject.parseObject(body);
//        JSONObject data = object.getJSONObject("data");
        TbBanner tbBanner = new TbBanner();
        JSONArray banners = object.getJSONArray("banners");
        List<TbBanner> getList = tbBannerMapper.getBanners();
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
                if (!StringUtil.nullOrEmpty(description)) {
                    tbBanner.setDescription(description);
                }

                if (!StringUtil.nullOrEmpty(accessPath)) {
                    tbBanner.setAccessPath(accessPath);
                }
                if (!StringUtil.nullOrEmpty(picPath)) {
                    tbBanner.setPicPath(picPath);
                }
                tbBanner.setOrderNum(orderNumber);
                tbBanner.setValid(true);
                tbBannerMapper.insert(tbBanner);
            }
        }

//        for (int i = 0; i < banners.size(); i++) {
//
//            String description = banners.getJSONObject(i).get("description").toString();
//            Integer orderNumber = (Integer) banners.getJSONObject(i).get("orderNumber");
//
//            String picPath = banners.getJSONObject(i).get("picPath").toString();
//            String accessPath = banners.getJSONObject(i).get("accessPath").toString();
//            List<TbBanner> byOrderNumber = tbBannerMapper.getByOrderNumber(orderNumber);
////            if (byOrderNumber.size() > 0) {
////                TbBanner banner = byOrderNumber.get(0);
////                banner.setOrderNum(orderNumber);
////                if (!StringUtil.nullOrEmpty(description)) {
////                    banner.setDescription(description);
////                }
////
////                if (!StringUtil.nullOrEmpty(accessPath)) {
////                    banner.setAccessPath(accessPath);
////                }
////                if (!StringUtil.nullOrEmpty(picPath)) {
////                    banner.setPicPath(picPath);
////                }
////
////                tbBannerMapper.updateById(banner);
////            } else {
////                if (!StringUtil.nullOrEmpty(description)) {
////                    tbBanner.setDescription(description);
////                }
////
////                if (!StringUtil.nullOrEmpty(accessPath)) {
////                    tbBanner.setAccessPath(accessPath);
////                }
////                if (!StringUtil.nullOrEmpty(picPath)) {
////                    tbBanner.setPicPath(picPath);
////                }
////                tbBanner.setOrderNum(orderNumber);
////                tbBanner.setValid(true);
////                tbBannerMapper.insert(tbBanner);
////            }
//        }
        return ResultInfo.success();
    }
}
