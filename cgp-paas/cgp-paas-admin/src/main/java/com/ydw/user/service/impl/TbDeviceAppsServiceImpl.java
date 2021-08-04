package com.ydw.user.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mysql.cj.util.StringUtils;
import com.ydw.user.model.db.TbDeviceApps;
import com.ydw.user.dao.TbDeviceAppsMapper;
import com.ydw.user.service.ITbDeviceAppsService;

import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import com.ydw.user.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
@Service
public class TbDeviceAppsServiceImpl extends ServiceImpl<TbDeviceAppsMapper, TbDeviceApps> implements ITbDeviceAppsService {

    @Autowired
    private TbDeviceAppsMapper tbDeviceAppsMapper;

    @Override
    public ResultInfo createApp(HttpServletRequest request, String deviceId,
                                 List<String> appIds) {


        TbDeviceApps app = new TbDeviceApps();
//        app.setDeviceId(deviceId);
        for (String id : appIds ) {
            TbDeviceApps deviceApp = tbDeviceAppsMapper.getDeviceApp(deviceId, id);
            InstallToDevice(id, deviceId, app, deviceApp);

        }

        return ResultInfo.success("安装中");
    }

    @Override
    public ResultInfo updateApp(HttpServletRequest request,String deviceId,
                                List<String> appIds) {
        TbDeviceApps app = new TbDeviceApps();
//        app.setDeviceId(deviceId);
        for (String id : appIds ) {
            TbDeviceApps deviceApp = tbDeviceAppsMapper.getDeviceApp(deviceId, id);
            app.setId(deviceApp.getId());
            app.setValid(false);//状态//软删除
            app.setUninstallTime(new Date());
            tbDeviceAppsMapper.updateSelective(app);
        }
//        Integer id = app.getId();
//        if (null == id) {
//            return ResultInfo.fail("id is null");
//        }
//
//        String appId = app.getAppId();
//        if (!StringUtils.isNullOrEmpty(deviceId)) {
//            app.setDeviceId(deviceId);
//        }
//        if (null != appId) {
//           app.setAppId(appId);
//        }
//        tbDeviceAppsMapper.updateSelective(app);
        return ResultInfo.success("卸载中");
    }

    @Override
    public ResultInfo deleteApp(HttpServletRequest request, List<Integer> ids) {
        TbDeviceApps app = new TbDeviceApps();
        for (Integer id : ids) {
            app.setId(id);
            //软删除
            app.setValid(false);
            tbDeviceAppsMapper.updateSelective(app);

        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getAppList(HttpServletRequest request,String deviceId,Integer appId,Integer status,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TbDeviceApps> list =tbDeviceAppsMapper.getAppList( deviceId ,appId,status,buildPage);
//        PageInfo<TbDeviceApps> pageInfo = new PageInfo<TbDeviceApps>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo InstallToDevices(HttpServletRequest request, String appId, List<String> deviceIds) {

        for (String id : deviceIds ) {
            TbDeviceApps app = new TbDeviceApps();
            TbDeviceApps deviceApp = tbDeviceAppsMapper.getDeviceApp(id,appId);
            InstallToDevice(appId, id, app, deviceApp);

        }

        return ResultInfo.success("安装中");
    }

    private void InstallToDevice(String appId, String id, TbDeviceApps app, TbDeviceApps deviceApp) {
        if(null==deviceApp){
            app.setAppId(appId);
            //TODO
            app.setValid(true);//状态启用
            app.setSetupTime(new Date());
            app.setDeviceId(id);
            tbDeviceAppsMapper.insertSelective(app);
        }else{
            app.setId(deviceApp.getId());
            app.setValid(true);//状态启用
            //TODO
            app.setSetupTime(new Date());
            app.setDeviceId(id);
            app.setAppId(appId);
            tbDeviceAppsMapper.updateSelective(app);
        }
    }

    @Override
    public ResultInfo UninstallToDevices(HttpServletRequest request, String appId, List<String> deviceIds) {


        for (String id : deviceIds ) {
            TbDeviceApps app = new TbDeviceApps();
            TbDeviceApps deviceApp = tbDeviceAppsMapper.getDeviceApp(id,appId);
            app.setId(deviceApp.getId());
            //TODO 
            app.setValid(false);//状态//软删除
            app.setUninstallTime(new Date());
            tbDeviceAppsMapper.updateSelective(app);
        }

        return ResultInfo.success("卸载中");
    }
}
