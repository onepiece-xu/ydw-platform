package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.dao.SyncAppMapper;
import com.ydw.open.dao.TbUserAppsMapper;
import com.ydw.open.model.db.SyncApp;
import com.ydw.open.model.db.TbUserApps;
import com.ydw.open.model.db.TbUserAppsRelated;
import com.ydw.open.dao.TbUserAppsRelatedMapper;
import com.ydw.open.model.vo.App;
import com.ydw.open.model.vo.AppUseApproveVO;
import com.ydw.open.service.ITbUserAppsRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.ResultInfo;
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
 * @since 2020-08-06
 */
@Service
public class TbUserAppsRelatedServiceImpl extends ServiceImpl<TbUserAppsRelatedMapper, TbUserAppsRelated> implements ITbUserAppsRelatedService {

    @Autowired
    private TbUserAppsRelatedMapper tbUserAppsRelatedMapper;
    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;
    @Autowired
    private SyncAppMapper syncAppMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;



    @Override
    public ResultInfo getAppList(String id,Page buildPage) {
//        QueryWrapper<TbUserApps> queryWrapper = new QueryWrapper<>();
//        Page page = tbUserAppsMapper.selectPage(buildPage, queryWrapper);

        Page<TbUserApps> gameAppList = tbUserAppsMapper.getGameAppList(buildPage, id);
        return ResultInfo.success(gameAppList);
    }

    @Override
    public ResultInfo addApps(String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String enterpriseId = jsonObject.getString("enterpriseId");

        JSONArray appIds = jsonObject.getJSONArray("appIds");
        TbUserAppsRelated tbUserAppsRelated = new TbUserAppsRelated();

        for (Object o: appIds) {

            Integer id = tbUserAppsRelatedMapper.getRelatedIdByAppIdAndEnterpriseId(o.toString(), enterpriseId);
            TbUserAppsRelated byId = tbUserAppsRelatedMapper.selectById(id);
            if(null!=byId){
                byId.setValid(0);//审批中
                tbUserAppsRelatedMapper.updateById(byId);
            }else{
                tbUserAppsRelated.setValid(0);//审批中
                tbUserAppsRelated.setAppId(o.toString());
                tbUserAppsRelated.setEnterpriseId(enterpriseId);
                tbUserAppsRelatedMapper.insert(tbUserAppsRelated);
              }


        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getAppApproveList(String body,String search,Page buildPage) {
        Page<AppUseApproveVO> appApproveList = tbUserAppsRelatedMapper.getAppApproveList(body,search,buildPage);
        return ResultInfo.success(appApproveList);
    }

    @Override
    public ResultInfo appApprove(HttpServletRequest request, String body) {
//        String token = request.getHeader("Authorization");
//        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
//        String identification = res.getMsg();
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer id = jsonObject.getInteger("id");
        Integer type = jsonObject.getInteger("type");
//        String enterpriseId = jsonObject.getString("enterpriseId");
//
//        JSONArray appIds = jsonObject.getJSONArray("appIds");

        TbUserAppsRelated tbUserAppsRelated = new TbUserAppsRelated();

        tbUserAppsRelated.setId(id);

        tbUserAppsRelated.setValid(type);//成功審批 或者拒絕審批
        tbUserAppsRelatedMapper.updateById(tbUserAppsRelated);

        TbUserAppsRelated selectById = tbUserAppsRelatedMapper.selectById(id);
        String enterpriseId = selectById.getEnterpriseId();
        String appId = selectById.getAppId();
        //审批完毕之后加入同步表数据
        QueryWrapper<SyncApp> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id",appId);
        wrapper.eq("identification",enterpriseId);
        SyncApp sync = syncAppMapper.selectOne(wrapper);
        if(null!=sync){
            String action = sync.getAction();
            if(action.equals("add")){
                return ResultInfo.success();
            }
            if(action.equals("delete")){
                sync.setAction("add");
                syncAppMapper.updateById(sync);
                return ResultInfo.success();
            }
        }else{
            SyncApp syncApp = new SyncApp();
            syncApp.setAppId(appId);
            syncApp.setIdentification(enterpriseId);
            syncApp.setAction("add");
            syncAppMapper.insert(syncApp);

        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getAppApproves(String body, Page buildPage,String id) {
        Page<AppUseApproveVO> appApproveList = tbUserAppsRelatedMapper.getAppApproves(body,buildPage,id);
        return ResultInfo.success(appApproveList);
    }

    @Override
    public ResultInfo getOwnerAppList(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        String enterpriseId = jsonObject.getString("enterpriseId");
        List<App> ownerAppList = tbUserAppsRelatedMapper.getOwnerAppList(enterpriseId);
        return ResultInfo.success(ownerAppList);
    }

    @Override
    public ResultInfo getGameListApproved(String id,String search ,Page buildPage) {
//        JSONObject jsonObject = JSON.parseObject(body);
//        String enterpriseId = jsonObject.getString("enterpriseId");
        Page<AppUseApproveVO> appApproveList = tbUserAppsRelatedMapper.getGameListApproved(search,buildPage,id);
        return ResultInfo.success(appApproveList);
    }

    @Override
    public ResultInfo cancelApproved(HttpServletRequest request,List<Integer> body) {
//        JSONObject jsonObject = JSON.parseObject(body);
//        String enterpriseId = jsonObject.getString("enterpriseId");
//        JSONArray ids = jsonObject.getJSONArray("ids");
//        TbUserAppsRelated tbUserAppsRelated = new TbUserAppsRelated();
//        String token = request.getHeader("Authorization");
//        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
//        String identification = res.getMsg();
        QueryWrapper<TbUserAppsRelated> queryWrapper = new QueryWrapper<>();
        for (Integer id:body ) {
//            queryWrapper.eq("app_id",id.toString());
//            queryWrapper.eq("enterprise_id",enterpriseId);
            TbUserAppsRelated one = tbUserAppsRelatedMapper.selectById(id);
            String appId = one.getAppId();
            String enterpriseId = one.getEnterpriseId();
            tbUserAppsRelatedMapper.deleteById(one);


            QueryWrapper<SyncApp> wrapper = new QueryWrapper<>();
            wrapper.eq("app_id",appId);
            wrapper.eq("identification",enterpriseId);
            SyncApp sync = syncAppMapper.selectOne(wrapper);
            if(null!=sync){
            String action = sync.getAction();
            if(action.equals("add")){
                syncAppMapper.deleteById(sync);
            }
        }else{
                SyncApp syncApp = new SyncApp();
                syncApp.setAction("delete");
                syncApp.setIdentification(enterpriseId);
                syncApp.setAppId(appId);
                syncAppMapper.insert(syncApp);
            }
        }
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo getAppApproved(String body,String search, Page buildPage) {
        Page<AppUseApproveVO> appApproveList = tbUserAppsRelatedMapper.getAppApproved(body,search,buildPage);
        return ResultInfo.success(appApproveList);
    }
}
