package com.ydw.game.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.game.dao.AppMapper;
import com.ydw.game.dao.TbAppTagMapper;
import com.ydw.game.dao.TbGameGroupMapper;
import com.ydw.game.model.db.App;
import com.ydw.game.model.db.TbGameGroup;
import com.ydw.game.model.vo.AppTagVO;
import com.ydw.game.model.vo.AppVO;
import com.ydw.game.model.vo.AppsConnectionsVO;
import com.ydw.game.model.vo.GameGroupAppVO;
import com.ydw.game.model.vo.GameListVO;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.model.vo.TagVO;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.ITbGameGroupService;
import com.ydw.game.util.HttpUtil;

import io.netty.util.internal.StringUtil;

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
    @Value("${url.paasApi}")
    private String paasApi;
    
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private TbAppTagMapper tbAppTagMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public ResultInfo getGameGroupAppList(HttpServletRequest request, String body) {

        JSONObject obj= JSON.parseObject(body);
        Integer gameGroupId = obj.getInteger("gameGroupId");

        List<TbGameGroup> appIdsByGameGroupId = tbGameGroupMapper.getAppIdsByGameGroupId(gameGroupId);
        List<Object> data =new ArrayList<>();
        for (TbGameGroup t:appIdsByGameGroupId ) {
            App w = appMapper.selectById(t.getGameId());

            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(t.getGameId());
            HashMap<Object, Object> map = new HashMap<>();
            String description = w.getDescription();
            String name = w.getName();
            Integer type = w.getType();
            String weight1 = t.getWeight1();
            Integer gameOrder = t.getGameOrder();
            if(null!=description){
                map.put("description",w.getDescription());
            }
            if(null!=name){
                map.put("appName",name);
            }
            if(null!=type){
                map.put("type",w.getType());
            }
            if(null!=weight1){
                map.put("weight1",t.getWeight1());
            }
            if(null!=gameOrder){
                map.put("order",t.getGameOrder());
            }
            if(null!=w.getId()){
                map.put("appId",w.getId());

            }

            //转换为我们熟悉的日期格式
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            String fffsd=sd.format(w.getCreateTime());
            map.put("createTime",fffsd);
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
//        JSONObject obj= JSON.parseObject(body);
//        String name = obj.getString("HotGame");
//        String getTags= paasApi+"/cgp-paas-admin/gameGroup/getHotGameList";
//        Map<String,String> headers = new HashMap<>();
////        String token = tbTagServiceImpl.mockLogin();
//        headers.put("Authorization", "");
//        Map<String,Object> params = new HashMap<>();
//        params.put("name",name);
//        String doGet = HttpUtil.doJsonPost(getTags, headers, params);
//        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
//        return ResultInfo.success(info.getMsg(),info.getData());
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

//        String get= paasApi+"/cgp-paas-admin/gameGroup/getNewGameList";
//        Map<String,String> headers = new HashMap<>();
////        String token = tbTagServiceImpl.mockLogin();
//        headers.put("Authorization", "");
//
//        String doGet = HttpUtil.doGet(get, headers, null);
//        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
//        Page<AppTagVO> page = new Page<>();
//        appMapper.selectList();
//        return ResultInfo.success(info.getMsg(),info.getData());
        List<AppTagVO> list = tbGameGroupMapper.getNewGameList();

        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

        List<GameListVO> ls = new ArrayList<>();
        Integer i=1;
        for (AppTagVO vo:list) {
            GameListVO gameListVO = new GameListVO();
            String time=sd.format(vo.getCreateTime());
            gameListVO.setCreateTime(time);
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
        JSONObject obj= JSON.parseObject(body);
        String search = obj.getString("search");
        String get= paasApi+"/cgp-paas-admin/gameGroup/searchApp";
        Map<String,String> headers = new HashMap<>();
//        String token = tbTagServiceImpl.mockLogin();
        headers.put("Authorization","");
        Map<String,Object> params = new HashMap<>();
        params.put("search",search);
        String doGet = HttpUtil.doJsonPost(get, headers, params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        return ResultInfo.success(info.getMsg(),info.getData());

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
        if(!StringUtil.isNullOrEmpty(weight1)){
            tbGameGroup.setWeight1(weight1);
        }
        if(!StringUtil.isNullOrEmpty(weight2)){
            tbGameGroup.setWeight2(weight2);
        }
        if(!StringUtil.isNullOrEmpty(weight3)){
            tbGameGroup.setWeight3(weight3);
        }
        tbGameGroupMapper.insert(tbGameGroup);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo getGroupAppList(HttpServletRequest request, Integer id,Page b) {


        Page<GameGroupAppVO> list = tbGameGroupMapper.getAppsByGameGroupId(id,b);
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
        if(!StringUtil.isNullOrEmpty(weight1)){
            tbGameGroup.setWeight1(weight1);
        }
        if(!StringUtil.isNullOrEmpty(weight2)){
            tbGameGroup.setWeight2(weight2);
        }
        if(!StringUtil.isNullOrEmpty(weight3)){
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
