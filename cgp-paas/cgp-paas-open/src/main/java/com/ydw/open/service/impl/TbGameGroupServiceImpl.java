package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.dao.TbAppTagMapper;
import com.ydw.open.dao.TbDeviceUsedMapper;
import com.ydw.open.dao.TbGameGroupMapper;
import com.ydw.open.dao.TbUserAppsMapper;
import com.ydw.open.model.db.TbGameGroup;
import com.ydw.open.model.db.TbUserApps;
import com.ydw.open.model.vo.*;
import com.ydw.open.service.ITbGameGroupService;
import com.ydw.open.utils.ResultInfo;

import com.ydw.open.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-14
 */
@Service
public class TbGameGroupServiceImpl extends ServiceImpl<TbGameGroupMapper, TbGameGroup> implements ITbGameGroupService {

    @Autowired
    private TbGameGroupMapper tbGameGroupMapper;
    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;
    @Autowired
    private TbAppTagMapper tbAppTagMapper;
    @Autowired
    private TbDeviceUsedMapper tbDeviceUsedMapper;

    @Autowired
    private TbDeviceUsedServiceImpl tbDeviceUsedServiceImpl;

    @Override
    public ResultInfo getGameGroupAppList(HttpServletRequest request, String body) {

        JSONObject obj= JSON.parseObject(body);
        Integer gameGroupId = obj.getInteger("gameGroupId");
        List<TbGameGroup> appIdsByGameGroupId = tbGameGroupMapper.getAppIdsByGameGroupId(gameGroupId);
        List<Object> data =new ArrayList<>();
        for (TbGameGroup t:appIdsByGameGroupId ) {
            TbUserApps w = tbUserAppsMapper.selectById(t.getGameId());
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(t.getGameId());
            HashMap<Object, Object> map = new HashMap<>();
            map.put("description",w.getDescription());
            map.put("appId",w.getId());
            map.put("appName",w.getName());
            map.put("type",w.getType());
            map.put("weight1",t.getWeight1());
            map.put("order",t.getGameOrder());
            //转换为我们熟悉的日期格式
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            String fffsd=sd.format(w.getUploadTime());
            map.put("uploadTime",fffsd);
            List<String> ls =new ArrayList<>();
            for (TagVO v:appTagNameByAppId) {
                ls.add(v.getTagName());
            }
            map.put("list",ls);
            data.add(map);
        }

        return ResultInfo.success(data.size()+"",data);
    }

    @Override
    public ResultInfo getHotGameList(HttpServletRequest request, String body) {
        JSONObject obj= JSON.parseObject(body);
        String name = obj.getString("name");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq= format.format(end);//当前日期
        c.add(Calendar.MONTH, -1);
        Date start = c.getTime();
        String startDay = format.format(start);//前一月

        List<AppsConnectionsVO> list = tbGameGroupMapper.getAppsConnectionsStatisticsTopTen(startDay, dqrq);
//        List<AppsConnectionsVO>  list  = tbDeviceUsedMapper.getAppsConnectTimeStatisticsTopTen( startDay, dqrq);
        Integer i=1;
        for(AppsConnectionsVO vo :list){
            vo.setOrder(i++);
        }
        if(list.size()<=5){
            return ResultInfo.success(list);
        }else{
            return ResultInfo.success( list.subList(0,5));
        }

    }

    @Override
    public ResultInfo getNewGameList(HttpServletRequest request) {
        List<AppTagVO> list = tbGameGroupMapper.getNewGameList();

        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

        List<GameListVO> ls = new ArrayList<>();
        Integer i=1;
        for (AppTagVO vo:list) {
            GameListVO gameListVO = new GameListVO();
            String time=sd.format(vo.getUploadTime());
            gameListVO.setUploadTime(time);
            gameListVO.setAppId(vo.getAppId());
            gameListVO.setAppName(vo.getAppName());
            gameListVO.setDescription(vo.getDescription());
            gameListVO.setOrder(i++);
            ls.add(gameListVO);
        }
        if(ls.size()<=5){
            return ResultInfo.success(ls);
        }else{
            return ResultInfo.success( ls.subList(0,5));
        }

    }

    @Override
    public ResultInfo searchApp(HttpServletRequest request, String body) {
        JSONObject obj=JSON.parseObject(body);
        String search = obj.getString("search");
        List<Object> data =new ArrayList<>();
        List<String> appIdBySearch = tbUserAppsMapper.getAppIdBySearch(search);
        for (String id :appIdBySearch){
            TbUserApps w = tbUserAppsMapper.selectById(id);
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(id);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("description",w.getDescription());
            map.put("appId",w.getId());
            map.put("appName",w.getName());
            map.put("type",w.getType());
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            String fffsd=sd.format(w.getUploadTime());
            map.put("uploadTime",fffsd);
            List<String> ls =new ArrayList<>();
            for (TagVO v:appTagNameByAppId) {
                ls.add(v.getTagName());
            }
            map.put("list",ls);
            data.add(map);
        }

        return ResultInfo.success(data.size()+"",data);
    }
    @Override
    public ResultInfo addApps(HttpServletRequest request, String body) {
//        tbGameGroupMapper.insert(body);
        JSONObject obj =JSON.parseObject(body);
        String appId = obj.getString("appId");
        Integer gameGroupId = obj.getInteger("gameGroupId");
        Integer order = obj.getInteger("gameOrder");

        String weight1 = obj.getString("weight1");

        String weight2 = obj.getString("weight2");

        String weight3 = obj.getString("weight3");



        TbGameGroup tbGameGroup = new TbGameGroup();
        tbGameGroup.setGamegroupId(gameGroupId);
        tbGameGroup.setGameId(appId);
        tbGameGroup.setGameOrder(order);
        if(!StringUtil.nullOrEmpty(weight1)){
            tbGameGroup.setWeight1(weight1);
        }
        if(!StringUtil.nullOrEmpty(weight2)){
            tbGameGroup.setWeight2(weight2);
        }
        if(!StringUtil.nullOrEmpty(weight3)){
            tbGameGroup.setWeight3(weight3);
        }
        tbGameGroupMapper.insert(tbGameGroup);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo getGroupAppList(HttpServletRequest request, Integer id, Page buildPage) {


        Page<GameGroupAppVO> list = tbGameGroupMapper.getAppsByGameGroupId(id,buildPage);
//        List<GameGroupAppVO> appIdsByGameGroupId = tbGameGroupMapper.getAppIdsByGameGroupId(id);
//        PageInfo<GameGroupAppVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getGameList(HttpServletRequest request) {

        List<AppVO> gameList = tbGameGroupMapper.getGameList();
        return ResultInfo.success(gameList);
    }

    @Override
    public ResultInfo updateApps(HttpServletRequest request, String body) {
        JSONObject obj =JSON.parseObject(body);
        TbGameGroup tbGameGroup = new TbGameGroup();
        Integer id = obj.getInteger("id");
        String appId = obj.getString("appId");
        Integer gameGroupId = obj.getInteger("gameGroupId");
        Integer order = obj.getInteger("gameOrder");

        String weight1 = obj.getString("weight1");

        String weight2 = obj.getString("weight2");

        String weight3 = obj.getString("weight3");
        tbGameGroup.setId(id );
        tbGameGroup.setGamegroupId(gameGroupId);
        tbGameGroup.setGameId(appId);
        tbGameGroup.setGameOrder(order);
        if(!StringUtil.nullOrEmpty(weight1)){
            tbGameGroup.setWeight1(weight1);
        }
        if(!StringUtil.nullOrEmpty(weight2)){
            tbGameGroup.setWeight2(weight2);
        }
        if(!StringUtil.nullOrEmpty(weight3)){
            tbGameGroup.setWeight3(weight3);
        }
        tbGameGroupMapper.updateById(tbGameGroup);

        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteApps(HttpServletRequest request, List<Integer> ids) {
        TbGameGroup tbGameGroup = new TbGameGroup();
        for (Integer i :ids){
            tbGameGroup.setId(i);
            tbGameGroupMapper.deleteById(tbGameGroup);
        }
        return ResultInfo.success("删除成功！");
    }


}
