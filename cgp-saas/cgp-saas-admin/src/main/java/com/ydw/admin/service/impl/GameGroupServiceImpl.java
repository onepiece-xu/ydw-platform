package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.GameGroup;
import com.ydw.admin.model.db.GameGroupInfo;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.IGameGroupService;
import com.ydw.admin.util.HttpUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class GameGroupServiceImpl extends ServiceImpl<GameGroupMapper, GameGroup> implements IGameGroupService {

    @Autowired
    private GameGroupMapper tbGameGroupMapper;
    @Value("${url.paasApi}")
    private String paasApi;

    @Value("${url.pics}")
    private String pics;

    @Autowired
    private GameGroupInfoMapper gameGroupInfoMapper;

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AppTagMapper tbAppTagMapper;
    @Autowired
    private AppPicturesMapper appPicturesMapper;
    @Autowired
    private TagMapper tagMapper;


    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public ResultInfo getGameGroupAppList(HttpServletRequest request, String body) {

        JSONObject obj= JSON.parseObject(body);
        Integer gameGroupId = obj.getInteger("gameGroupId");

        List<GameGroup> appIdsByGameGroupId = tbGameGroupMapper.getAppIdsByGameGroupId(gameGroupId);
        List<Object> data =new ArrayList<>();
        for (GameGroup t:appIdsByGameGroupId ) {
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
        List<HotGameListVO> hotGameList = new ArrayList();
        Integer i=1;
        for(AppsConnectionsVO vo:list){
//            vo.setOrder();
            HotGameListVO hotGameListVO = new HotGameListVO();
            String count = vo.getCount();
            String appId = vo.getAppId();
            String appName = vo.getAppName();
//            Integer order = vo.getOrder();
            String percentage = vo.getPercentage();
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            String bigPic = appPicDetails.getBigPic();
            Date createTime = appPicDetails.getCreateTime();

            String description = appPicDetails.getDescription();

            String midPic = appPicDetails.getMidPic();

            String smallPic = appPicDetails.getSmallPic();

            String logoPic = appPicDetails.getLogoPic();

            Integer type = appPicDetails.getType();
            hotGameListVO.setBigPic(pics+bigPic);
            hotGameListVO.setMidPic(pics+midPic);
            hotGameListVO.setSmallPic(pics+smallPic);
            hotGameListVO.setLogoPic(pics+logoPic);
            hotGameListVO.setCount(count);
            hotGameListVO.setCreateTime(createTime);
            hotGameListVO.setDescription(description);
            hotGameListVO.setOrder(i++);
            hotGameListVO.setType(type);
            hotGameListVO.setPercentage(percentage);
            hotGameListVO.setAppId(appId);
            hotGameListVO.setAppName(appName);
            hotGameList.add(hotGameListVO);

        }
//        List<AppsConnectionsVO>  list  = tbDeviceUsedMapper.getAppsConnectTimeStatisticsTopTen( startDay, dqrq);
//        Integer i=1;
//        for(AppsConnectionsVO vo :list){
//            vo.setOrder(i++);
//        }
        if(list.size()<=5){
            return ResultInfo.success(hotGameList);
        }else{
            return ResultInfo.success( hotGameList.subList(0,5));
        }
    }

    @Override
    public ResultInfo getNewGameList(HttpServletRequest request) {

        List<AppTagVO> list = tbGameGroupMapper.getNewGameList();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

//        List<GameListVO> ls = new ArrayList<>();
        Integer i=1;
        List<HotGameListVO> newGameList = new ArrayList();

        for (AppTagVO vo:list) {
//            GameListVO gameListVO = new GameListVO();
            HotGameListVO hotGameListVO = new HotGameListVO();
            String appId = vo.getAppId();
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
           if(appPicDetails!=null){
               String bigPic = appPicDetails.getBigPic();

               String description = appPicDetails.getDescription();

               String midPic = appPicDetails.getMidPic();

               String smallPic = appPicDetails.getSmallPic();

               String logoPic = appPicDetails.getLogoPic();

               Integer type = appPicDetails.getType();
               hotGameListVO.setType(type);
               hotGameListVO.setBigPic(pics+bigPic);
               hotGameListVO.setMidPic(pics+midPic);
               hotGameListVO.setSmallPic(pics+smallPic);
               hotGameListVO.setLogoPic(pics+logoPic);
               hotGameListVO.setDescription(description);
           }

            Date createTime = vo.getCreateTime();
            hotGameListVO.setCreateTime(createTime);
            hotGameListVO.setAppId(vo.getAppId());
            hotGameListVO.setAppName(vo.getAppName());
            hotGameListVO.setDescription(vo.getDescription());
            hotGameListVO.setOrder(i++);


            newGameList.add(hotGameListVO);
        }
        if(newGameList.size()<=5){
            return ResultInfo.success(newGameList);
        }else{
            return ResultInfo.success( newGameList.subList(0,5));
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



        GameGroup tbGameGroup = new GameGroup();
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
        GameGroup tbGameGroup = new GameGroup();
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
        GameGroup tbGameGroup = new GameGroup();
        for (Integer i :ids){
            tbGameGroup.setId(i);
            tbGameGroupMapper.deleteById(tbGameGroup);
       }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo getGameGroupLists(HttpServletRequest request) {
        List<Object> total = new ArrayList<>();
        //获取除了默认游戏组之外的游戏组
//        List<GameListsVO> gameGroupListsExceptDefault = tbGameGroupMapper.getGameGroupListsExceptDefault();
        List<GameGroupInfo> gameGroupInfos = gameGroupInfoMapper.selectList(new QueryWrapper<>());
//        List<GameGroupInfo> records = gameGroupInfos.getRecords();
        for (GameGroupInfo r: gameGroupInfos) {
            if(r.getGamegroupId()==1){
                continue;
            }
            HashMap<Object, Object> map = new HashMap<>();
            String gamegroupName = r.getGamegroupName();
            String gamegroupPic = r.getGamegroupPic();
            Page<GameGroupAppVO> appsByGameGroupId = tbGameGroupMapper.getAppsByGameGroupId(r.getGamegroupId(), new Page());
            List<GameGroupAppVO> records = appsByGameGroupId.getRecords();
            List<Object> details = new ArrayList<>();
            for(GameGroupAppVO vo :records ){
                String appId = vo.getAppId();
                String appName = vo.getAppName();
                Integer gameOrder = vo.getGameOrder();
                String weight1 = vo.getWeight1();
                String weight2 = vo.getWeight2();
                String weight3 = vo.getWeight3();
                GameDetailVO gameDetailVO = new GameDetailVO();
                gameDetailVO.setAppId(appId);
                gameDetailVO.setAppName(appName);
                if(null!=gameOrder){
                    gameDetailVO.setGameOrder(gameOrder);
                }
                gameDetailVO.setWeight1(weight1);
                gameDetailVO.setWeight2(weight2);
                gameDetailVO.setWeight3(weight3);
                AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
                String bigPic = appPicDetails.getBigPic();
                String midPic = appPicDetails.getMidPic();
                String smallPic = appPicDetails.getSmallPic();
                String logoPic = appPicDetails.getLogoPic();
                Date createTime = appPicDetails.getCreateTime();
                String description = appPicDetails.getDescription();
                Integer type = appPicDetails.getType();
                List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(appId);
                List<String> ls =new ArrayList<>();
                for (TagVO v:appTagNameByAppId) {
                    ls.add(v.getTagName());
                }
                gameDetailVO.setTags(ls);
                gameDetailVO.setType(type);
                gameDetailVO.setCreateTime(createTime);
                gameDetailVO.setDescription(description);
                gameDetailVO.setBigPic(pics+bigPic);
                gameDetailVO.setSmallPic(pics+smallPic);
                gameDetailVO.setMidPic(pics+midPic);
                gameDetailVO.setLogoPic(pics+logoPic);
                details.add(gameDetailVO);
            }

            map.put("gameGroupName",gamegroupName);
            map.put("gameGroupPic",gamegroupPic);
            map.put("list", details );
            total.add(map);
        }


//        for (GameListsVO vo: gameGroupListsExceptDefault ) {
//            List<Object> list = new ArrayList<>();
//            Integer gameGroupId = vo.getGameGroupId();
//            GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(gameGroupId);
//            String gamegroupName = gameGroupInfo.getGamegroupName();
//            Page<GameGroupAppVO> appsByGameGroupId = tbGameGroupMapper.getAppsByGameGroupId(gameGroupId, new Page());
//            list.add(appsByGameGroupId.getRecords());
//            map.put("gameGroupName",gamegroupName);
//            map.put("list",list);
//            total.add(map);
//        }

        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getRecommendList(HttpServletRequest request) {

        HashMap<Object, Object> map = new HashMap<>();
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        Page<GameGroupAppVO> appsByGameGroupId = tbGameGroupMapper.getAppsByGameGroupId(1, new Page());
        List<GameGroupAppVO> records = appsByGameGroupId.getRecords();
        List<Object> details = new ArrayList<>();
        List<Object> total = new ArrayList<>();
        for (GameGroupAppVO vo: records) {
            String appId = vo.getAppId();
            String appName = vo.getAppName();
            Integer gameOrder = vo.getGameOrder();
            String weight1 = vo.getWeight1();
            String weight2 = vo.getWeight2();
            String weight3 = vo.getWeight3();
            GameDetailVO gameDetailVO = new GameDetailVO();
            gameDetailVO.setAppId(appId);
            gameDetailVO.setAppName(appName);
            if(null!=gameOrder){
                gameDetailVO.setGameOrder(gameOrder);
            }
            gameDetailVO.setWeight1(weight1);
            gameDetailVO.setWeight2(weight2);
            gameDetailVO.setWeight3(weight3);
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            String bigPic = appPicDetails.getBigPic();
            String midPic = appPicDetails.getMidPic();
            String smallPic = appPicDetails.getSmallPic();
            String logoPic = appPicDetails.getLogoPic();
            Date createTime = appPicDetails.getCreateTime();
            String description = appPicDetails.getDescription();
            Integer type = appPicDetails.getType();
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(appId);
            List<String> ls =new ArrayList<>();
            for (TagVO v:appTagNameByAppId) {
                ls.add(v.getTagName());
            }
            gameDetailVO.setTags(ls);
            gameDetailVO.setType(type);
            gameDetailVO.setCreateTime(createTime);
            gameDetailVO.setDescription(description);
            gameDetailVO.setBigPic(bigPic);
            gameDetailVO.setSmallPic(smallPic);
            gameDetailVO.setMidPic(midPic);
            gameDetailVO.setLogoPic(logoPic);
            details.add(gameDetailVO);
        }

        map.put("gameGroupName",gamegroupName);
        map.put("gameGroupPic",gamegroupPic);
        map.put("list", details );
        total.add(map);
        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getTopGames(HttpServletRequest request) {
        HashMap<Object, Object> map = new HashMap<>();
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        Page<GameGroupAppVO> appsByGameGroupId = tbGameGroupMapper.getAppsByGameGroupId(7, new Page());
        List<GameGroupAppVO> records = appsByGameGroupId.getRecords();
        List<Object> details = new ArrayList<>();
        List<Object> total = new ArrayList<>();
        for (GameGroupAppVO vo: records) {
            String appId = vo.getAppId();
            String appName = vo.getAppName();
            Integer gameOrder = vo.getGameOrder();
            String weight1 = vo.getWeight1();
            String weight2 = vo.getWeight2();
            String weight3 = vo.getWeight3();
            GameDetailVO gameDetailVO = new GameDetailVO();
            gameDetailVO.setAppId(appId);
            gameDetailVO.setAppName(appName);
            if(null!=gameOrder){
                gameDetailVO.setGameOrder(gameOrder);
            }
            gameDetailVO.setWeight1(weight1);
            gameDetailVO.setWeight2(weight2);
            gameDetailVO.setWeight3(weight3);
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            String bigPic = appPicDetails.getBigPic();
            String midPic = appPicDetails.getMidPic();
            String smallPic = appPicDetails.getSmallPic();
            String logoPic = appPicDetails.getLogoPic();
            Date createTime = appPicDetails.getCreateTime();
            String description = appPicDetails.getDescription();
            Integer type = appPicDetails.getType();
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(appId);
            List<String> ls =new ArrayList<>();
            for (TagVO v:appTagNameByAppId) {
                ls.add(v.getTagName());
            }
            gameDetailVO.setTags(ls);
            gameDetailVO.setType(type);
            gameDetailVO.setCreateTime(createTime);
            gameDetailVO.setDescription(description);
            gameDetailVO.setBigPic(pics+bigPic);
            gameDetailVO.setSmallPic(pics+smallPic);
            gameDetailVO.setMidPic(pics+midPic);
            gameDetailVO.setLogoPic(pics+logoPic);
            details.add(gameDetailVO);
        }

        map.put("gameGroupName",gamegroupName);
        map.put("gameGroupPic",gamegroupPic);
        map.put("list", details );
        total.add(map);
        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getHotGameListAndroid(HttpServletRequest request, String body) {
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
        List<HotGameListVO> hotGameList = new ArrayList();
        Integer i=1;
        for(AppsConnectionsVO vo:list){
//            vo.setOrder();
            HotGameListVO hotGameListVO = new HotGameListVO();
            String count = vo.getCount();
            String appId = vo.getAppId();
            String appName = vo.getAppName();
//            Integer order = vo.getOrder();
            String percentage = vo.getPercentage();
            //获取app标签列表
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(vo.getAppId());

            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            String bigPic = appPicDetails.getBigPic();
            Date createTime = appPicDetails.getCreateTime();

            String description = appPicDetails.getDescription();

            String midPic = appPicDetails.getMidPic();

            String smallPic = appPicDetails.getSmallPic();

            String logoPic = appPicDetails.getLogoPic();

            Integer type = appPicDetails.getType();
            hotGameListVO.setBigPic(pics+bigPic);
            hotGameListVO.setMidPic(pics+midPic);
            hotGameListVO.setSmallPic(pics+smallPic);
            hotGameListVO.setLogoPic(pics+logoPic);
            hotGameListVO.setCount(count);
            hotGameListVO.setCreateTime(createTime);
            hotGameListVO.setDescription(description);
            hotGameListVO.setOrder(i++);
            hotGameListVO.setType(type);
            hotGameListVO.setPercentage(percentage);
            hotGameListVO.setAppId(appId);
            hotGameListVO.setAppName(appName);
            //存标签list
            List<String> tags = new ArrayList<>();
            for(TagVO  t:appTagNameByAppId){
                String tagName = t.getTagName();
                tags.add(tagName);
            }
            hotGameListVO.setTags(tags);
            hotGameList.add(hotGameListVO);

        }

        if(list.size()<=5){
            return ResultInfo.success(hotGameList);
        }else{
            return ResultInfo.success( hotGameList.subList(0,5));
        }
    }

    @Override
    public ResultInfo getNewGameListAndroid(HttpServletRequest request) {
        List<AppTagVO> list = tbGameGroupMapper.getNewGameList();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");

//        List<GameListVO> ls = new ArrayList<>();
        Integer i=1;
        List<HotGameListVO> newGameList = new ArrayList();

        for (AppTagVO vo:list) {
//            GameListVO gameListVO = new GameListVO();
            HotGameListVO hotGameListVO = new HotGameListVO();
            String appId = vo.getAppId();
            //获取app标签列表
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(appId);
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            if(appPicDetails!=null){
                String bigPic = appPicDetails.getBigPic();

                String description = appPicDetails.getDescription();

                String midPic = appPicDetails.getMidPic();

                String smallPic = appPicDetails.getSmallPic();

                String logoPic = appPicDetails.getLogoPic();

                Integer type = appPicDetails.getType();
                hotGameListVO.setType(type);
                hotGameListVO.setBigPic(pics+bigPic);
                hotGameListVO.setMidPic(pics+midPic);
                hotGameListVO.setSmallPic(pics+smallPic);
                hotGameListVO.setLogoPic(pics+logoPic);
                hotGameListVO.setDescription(description);
            }

            Date createTime = vo.getCreateTime();
            hotGameListVO.setCreateTime(createTime);
            hotGameListVO.setAppId(vo.getAppId());
            hotGameListVO.setAppName(vo.getAppName());
            hotGameListVO.setDescription(vo.getDescription());
            hotGameListVO.setOrder(i++);
            //存标签list
            List<String> tags = new ArrayList<>();
            for(TagVO  t:appTagNameByAppId){
                String tagName = t.getTagName();
                tags.add(tagName);
            }
            hotGameListVO.setTags(tags);
            newGameList.add(hotGameListVO);
        }
        if(newGameList.size()<=5){
            return ResultInfo.success(newGameList);
        }else{
            return ResultInfo.success( newGameList.subList(0,5));
        }
    }
}
