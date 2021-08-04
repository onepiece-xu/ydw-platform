package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.dao.ActivityCouponMapper;
import com.ydw.admin.model.db.ActivityCenter;
import com.ydw.admin.dao.ActivityCenterMapper;
import com.ydw.admin.model.db.ActivityCoupon;
import com.ydw.admin.model.db.Banner;
import com.ydw.admin.model.db.CouponCard;
import com.ydw.admin.model.vo.ActivityCenterVO;
import com.ydw.admin.model.vo.FixActivityVO;
import com.ydw.admin.model.vo.FixBindCoupon;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IActivityCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
@Service
public class ActivityCenterServiceImpl extends ServiceImpl<ActivityCenterMapper, ActivityCenter> implements IActivityCenterService {
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
    @Value("${url.pics}")
    private String pics;

    @Autowired
    private ActivityCenterMapper activityCenterMapper;
    @Autowired
    private ActivityCouponMapper activityCouponMapper;

    @Override
    public ResultInfo getList(Page buildPage) {
        QueryWrapper<ActivityCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("valid", 1);
        Page page = activityCenterMapper.selectPage(buildPage, wrapper);
//        Page<ActivityCenterVO> page = activityCenterMapper.getList(buildPage);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addActivity(HttpServletRequest request, ActivityCenterVO activityCenter) {
//        String type = activityCenter.getType();
        //设置禁用状态
        activityCenter.setValid(true);
        activityCenter.setType(0);
        Integer orderNum = activityCenter.getOrderNum();
        String uid = SequenceGenerator.sequence();
        QueryWrapper<ActivityCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_num", orderNum);
        queryWrapper.eq("valid", 1);
        ActivityCenter activityCenter1 = activityCenterMapper.selectOne(queryWrapper);
        if (null != activityCenter1) {
            return ResultInfo.fail("已存在相同位置的活动！请确认后再添加！");
        }
        activityCenter.setValid(true);

        activityCenter.setPicPath(pics + "game/activity/activity_" + uid + ".jpg");
//        activityCenter.setAccessPath(pics+"game/activity/activity_"+uid+".jpg");
        activityCenter.setDelPath("pics/game/activity/activity_" + uid + ".jpg");
        activityCenterMapper.insert(activityCenter);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");

        if (null != file) {
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
            //   如果上传出现问题，可启用ftp的被动模式就可以解决
            ftpClient.enterLocalPassiveMode();
            try {
//                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +orderNum +"_"+type+ ".jpg", file.getInputStream());
                FTPUtil.uploadFiles(ftpClient, uploadPath + "activity/activity_" + uid + ".jpg", file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //活动绑定优惠券
        int id = activityCenter.getId();
        String couponId = activityCenter.getCouponId();
        ActivityCoupon activityCoupon = new ActivityCoupon();
        activityCoupon.setActivityId(id);
        activityCoupon.setCouponId(couponId);
        activityCouponMapper.insert(activityCoupon);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo delete(List<Integer> ids) {
        for (Integer id : ids) {
            ActivityCenter activityCenter = new ActivityCenter();
            activityCenter.setId(id);
            activityCenter.setValid(false);
            activityCenterMapper.updateById(activityCenter);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateActivity(HttpServletRequest request, ActivityCenterVO activityCenter) {
        Integer id = activityCenter.getId();
        String couponId = activityCenter.getCouponId();
        String uid = SequenceGenerator.sequence();
        //重新绑定
        QueryWrapper<ActivityCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id", id);
        ActivityCoupon activityCoupon = activityCouponMapper.selectOne(queryWrapper);
        activityCouponMapper.deleteById(activityCoupon);

        ActivityCoupon coupon = new ActivityCoupon();
        coupon.setCouponId(couponId + "");
        coupon.setActivityId(id);
        activityCouponMapper.insert(coupon);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        QueryWrapper<ActivityCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("valid", 1);
        ActivityCenter activityCenter1 = activityCenterMapper.selectOne(wrapper);
        String delPath = activityCenter1.getDelPath();
        if (null != file) {
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();
            try {
//                FTPUtil.uploadFiles(ftpClient, uploadPath + "banner/banner_" +orderNum +"_"+type+ ".jpg", file.getInputStream());
                FTPUtil.uploadFiles(ftpClient, uploadPath + "activity/activity_" + uid + ".jpg", file.getInputStream());
                ftpClient.deleteFile(delPath);
                activityCenter.setPicPath(pics + "game/activity/activity_" + uid + ".jpg");
                activityCenter.setDelPath("pics/game/activity/activity_" + uid + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        activityCenterMapper.updateById(activityCenter);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo fixActivity(FixActivityVO fixActivityVO) {
        List<Integer> ids = fixActivityVO.getIds();
        Integer type = fixActivityVO.getType();
        for(Integer id:ids){
            ActivityCenter activityCenter = activityCenterMapper.selectById(id);

            if (1 == type) {
                activityCenter.setType(1);
            }
            if (0 == type) {
                activityCenter.setType(0);
            }
            activityCenterMapper.updateById(activityCenter);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBindCoupon(Integer id, Page buildPage) {

        Page<CouponCard> bindCoupon = activityCouponMapper.getBindCoupon(id,buildPage);
        return ResultInfo.success(bindCoupon);
    }

    @Override
    public ResultInfo getUnbindCoupon(Integer id, Page buildPage) {
        Page<CouponCard> bindCoupon = activityCouponMapper.getUnbindCoupon(id,buildPage);
        return ResultInfo.success(bindCoupon);
    }

    @Override
    public ResultInfo fixBindCoupon(FixBindCoupon fixBindCoupon) {
        Integer id = fixBindCoupon.getId();
        Integer type = fixBindCoupon.getType();
        List<String> ids = fixBindCoupon.getIds();
        for (String couponId :ids){
            QueryWrapper<ActivityCoupon> wrapper = new QueryWrapper<>();
            wrapper.eq("coupon_id",couponId);
            wrapper.eq("activity_id",id);
            ActivityCoupon one = activityCouponMapper.selectOne(wrapper);
            if(type==0){
                //解绑
                activityCouponMapper.deleteById(one);
            }else{
                if(one==null){
                    ActivityCoupon activityCoupon = new ActivityCoupon();
                    activityCoupon.setActivityId(id);
                    activityCoupon.setCouponId(couponId);
                    activityCouponMapper.insert(activityCoupon);
                }

            }
        }
        return ResultInfo.success();
    }


}
