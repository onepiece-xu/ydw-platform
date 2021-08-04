package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.platform.dao.BannerMapper;

import com.ydw.platform.model.db.Banner;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IBannerService;


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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Autowired
    private BannerMapper tbBannerMapper;

    @Override
    public ResultInfo getBanners(HttpServletRequest request, Integer type, Integer platform,Page page) {

        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        if(null!=type){
            wrapper.eq("type",type);
        }
        if(null!=platform){
            wrapper.eq("platform",platform);
        }
        wrapper.orderByAsc("order_num");
        wrapper.eq("valid",1);
        List<Banner> banners = tbBannerMapper.selectList(wrapper);
        return ResultInfo.success(banners);
    }
}
