package com.ydw.platform.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.ydw.platform.dao.*;

import com.ydw.platform.model.db.GameGroup;
import com.ydw.platform.model.db.GameGroupInfo;
import com.ydw.platform.model.vo.*;
import com.ydw.platform.service.IGameGroupService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.model.db.App;
import com.ydw.platform.util.HttpUtil;


/**
 * <p>
 * 服务实现类
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
    @Autowired
    private AppPictureMapper appPictureMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResultInfo getGameGroupAppList(HttpServletRequest request, String body) {

        JSONObject obj = JSON.parseObject(body);
        Integer gameGroupId = obj.getInteger("gameGroupId");

        List<GameGroup> appIdsByGameGroupId = tbGameGroupMapper.getAppIdsByGameGroupId(gameGroupId);
        List<Object> data = new ArrayList<>();
        for (GameGroup t : appIdsByGameGroupId) {
            App w = appMapper.selectById(t.getGameId());
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(t.getGameId());
            HashMap<Object, Object> map = new HashMap<>();
            String description = w.getDescription();
            String name = w.getName();
            Integer type = w.getType();
            String weight1 = t.getWeight1();
            Integer gameOrder = t.getGameOrder();
            if (null != description) {
                map.put("description", w.getDescription());
            }
            if (null != name) {
                map.put("appName", name);
            }
            if (null != type) {
                map.put("type", w.getType());
            }
            if (null != weight1) {
                map.put("weight1", t.getWeight1());
            }
            if (null != gameOrder) {
                map.put("order", t.getGameOrder());
            }
            if (null != w.getId()) {
                map.put("appId", w.getId());
            }
            //转换为我们熟悉的日期格式
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String fffsd = sd.format(w.getCreateTime());
            map.put("createTime", fffsd);
            List<String> ls = new ArrayList<>();
            for (TagVO v : appTagNameByAppId) {
                ls.add(v.getTagName());
            }
            map.put("list", ls);
            data.add(map);
        }

        return ResultInfo.success(data.size() + "", data);
    }

    @Override
    public ResultInfo getHotGameList(HttpServletRequest request, String body) {

        JSONObject obj = JSON.parseObject(body);
        String name = obj.getString("name");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        c.add(Calendar.MONTH, -1);
        Date start = c.getTime();
        String startDay = format.format(start);//前一月

        List<HotGameTag> list = tbGameGroupMapper.getHotGameList(startDay, dqrq);
        Integer i = 1;
        List<HotGameListVO> data = new ArrayList<>();
        for (HotGameTag vo : list) {
            HotGameListVO hotGameListVO = new HotGameListVO();
            vo.setOrder(i++);
            BeanUtils.copyProperties(vo, hotGameListVO);
            String tag = vo.getTag();
            List<String> ls = new ArrayList<>();
            if (StringUtils.isNotEmpty(tag)) {
                String[] split = new String(tag).split("[\\,\\;]");

                for (String s : split) {
                    ls.add(s);
                }
                hotGameListVO.setTags(ls);
            }
            data.add(hotGameListVO);
        }

        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo getNewGameList(HttpServletRequest request) {

        List<NewGamesVO> list = tbGameGroupMapper.newGameList();
        Integer i = 1;
        for (NewGamesVO vo : list) {
            vo.setOrder(i++);
        }

        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo searchApp(HttpServletRequest request, String body) {
        JSONObject obj = JSON.parseObject(body);
        String search = obj.getString("search");
        String get = paasApi + "/cgp-paas-admin/gameGroup/searchApp";
        Map<String, String> headers = new HashMap<>();
//        String token = tbTagServiceImpl.mockLogin();
        headers.put("Authorization", "");
        Map<String, Object> params = new HashMap<>();
        params.put("search", search);
        String doGet = HttpUtil.doJsonPost(get, headers, params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        return ResultInfo.success(info.getMsg(), info.getData());

    }

    @Override
    public ResultInfo addApps(HttpServletRequest request, String body) {
//        tbGameGroupMapper.insert(body);
        JSONObject obj = JSON.parseObject(body);
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
        if (StringUtils.isNotEmpty(weight1)) {
            tbGameGroup.setWeight1(weight1);
        }
        if (StringUtils.isNotEmpty(weight2)) {
            tbGameGroup.setWeight2(weight2);
        }
        if (StringUtils.isNotEmpty(weight3)) {
            tbGameGroup.setWeight3(weight3);
        }
        tbGameGroupMapper.insert(tbGameGroup);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo getGroupAppList(HttpServletRequest request, Integer id, Page b) {


        Page<GameGroupAppVO> list = tbGameGroupMapper.getAppsByGameGroupId(id, b);
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
        JSONObject obj = JSON.parseObject(body);
        GameGroup tbGameGroup = new GameGroup();
        Integer id = obj.getInteger("id");
        String appId = obj.getString("appId");
        Integer gameGroupId = obj.getInteger("gameGroupId");
        Integer order = obj.getInteger("gameOrder");

        String weight1 = obj.getString("weight1");

        String weight2 = obj.getString("weight2");

        String weight3 = obj.getString("weight3");
        tbGameGroup.setId(id);
        tbGameGroup.setGamegroupId(gameGroupId);
        tbGameGroup.setGameId(appId);
        tbGameGroup.setGameOrder(order);
        if (StringUtils.isNotEmpty(weight1)) {
            tbGameGroup.setWeight1(weight1);
        }
        if (StringUtils.isNotEmpty(weight2)) {
            tbGameGroup.setWeight2(weight2);
        }
        if (StringUtils.isNotEmpty(weight3)) {
            tbGameGroup.setWeight3(weight3);
        }
        tbGameGroupMapper.updateById(tbGameGroup);

        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteApps(HttpServletRequest request, List<Integer> ids) {
        GameGroup tbGameGroup = new GameGroup();
        for (Integer i : ids) {
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
        List<GameGroupInfo> gameGroupInfos = gameGroupInfoMapper.selectList(new QueryWrapper<GameGroupInfo>().eq("valid", 1).eq("type", 0).orderByAsc("group_order"));
//        List<GameGroupInfo> records = gameGroupInfos.getRecords();
        for (GameGroupInfo r : gameGroupInfos) {
            if (r.getType() == 1) {
                continue;
            }
            HashMap<Object, Object> map = new HashMap<>();
            String gamegroupName = r.getGamegroupName();
            String gamegroupPic = r.getGamegroupPic();
            Page page = new Page();
            page.setCurrent(1);
            page.setSize(100);
            Page<GameGroupListsVO> appsByGameGroupId = tbGameGroupMapper.getGameGroupLists(r.getGamegroupId(), page);

            List<GameGroupListsVO> records = appsByGameGroupId.getRecords();
            for (GameGroupListsVO vo : records) {
                String tag = vo.getTag();
                String[] split = new String(tag).split("[\\,\\;]");
                List<String> list = new ArrayList<>();
                for (String s : split) {
                    list.add(s);
                }
                vo.setTags(list);
            }
            map.put("gameGroupName", gamegroupName);
            map.put("gameGroupPic", gamegroupPic);
            map.put("list", records);
            total.add(map);


        }


        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getRecommendList(HttpServletRequest request) {

        QueryWrapper<GameGroupInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectOne(wrapper);
        Integer gamegroupId = gameGroupInfo.getGamegroupId();
        HashMap<Object, Object> map = new HashMap<>();
//        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        List<RecommendListVO> appsByGameGroupId = tbGameGroupMapper.getRecommendList(gamegroupId, new Page());
        for (RecommendListVO vo : appsByGameGroupId) {
            String tag = vo.getTag();
            String[] split = new String(tag).split("[\\,\\;]");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            vo.setTags(list);
        }
        List<Object> total = new ArrayList<>();

        map.put("gameGroupName", gamegroupName);
        map.put("gameGroupPic", gamegroupPic);
        map.put("list", appsByGameGroupId);
        total.add(map);
        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getTopGames(HttpServletRequest request) {

        QueryWrapper<GameGroupInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 2);
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectOne(wrapper);
        Integer gamegroupId = gameGroupInfo.getGamegroupId();
        HashMap<Object, Object> map = new HashMap<>();
//        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        List<RecommendListVO> appsByGameGroupId = tbGameGroupMapper.getRecommendList(gamegroupId, new Page());
        for (RecommendListVO vo : appsByGameGroupId) {
            String tag = vo.getTag();
            String[] split = new String(tag).split("[\\,\\;]");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            vo.setTags(list);
        }
        List<Object> total = new ArrayList<>();

        map.put("gameGroupName", gamegroupName);
        map.put("gameGroupPic", gamegroupPic);
        map.put("list", appsByGameGroupId);
        total.add(map);
        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getHotGameListAndroid(HttpServletRequest request, String body) {
        JSONObject obj = JSON.parseObject(body);
        String name = obj.getString("name");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        c.add(Calendar.MONTH, -1);
        Date start = c.getTime();
        String startDay = format.format(start);//前一月

        List<AppsConnectionsVO> list = tbGameGroupMapper.getAppsConnectionsStatisticsTopTen(startDay, dqrq);
        List<HotGameListVO> hotGameList = new ArrayList();
        Integer i = 1;
        for (AppsConnectionsVO vo : list) {
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
            hotGameListVO.setBigPic(bigPic);
            hotGameListVO.setMidPic(midPic);
            hotGameListVO.setSmallPic(smallPic);
            hotGameListVO.setLogoPic(logoPic);
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
            for (TagVO t : appTagNameByAppId) {
                String tagName = t.getTagName();
                tags.add(tagName);
            }
            hotGameListVO.setTags(tags);
            hotGameList.add(hotGameListVO);

        }

        if (list.size() <= 5) {
            return ResultInfo.success(hotGameList);
        } else {
            return ResultInfo.success(hotGameList.subList(0, 5));
        }
    }

    @Override
    public ResultInfo getNewGameListAndroid(HttpServletRequest request) {

        List<NewGameVOAndorid> list = tbGameGroupMapper.getNewGameList();
        Integer i = 1;
        List<HotGameListVO> newGameList = new ArrayList();
        for (NewGameVOAndorid vo : list) {
            HotGameListVO hotGameListVO = new HotGameListVO();


            BeanUtils.copyProperties(vo, hotGameListVO);
            hotGameListVO.setOrder(i++);
            String tag = vo.getTag();
            List<String> ls = new ArrayList<>();
            if (StringUtils.isNotEmpty(tag)) {
                String[] split = new String(tag).split("[\\,\\;]");

                for (String s : split) {
                    ls.add(s);
                }
            }

            hotGameListVO.setTags(ls);
            newGameList.add(hotGameListVO);
        }

        return ResultInfo.success(newGameList);
    }

    @Override
    public ResultInfo getRecommendApps(HttpServletRequest request) {
        QueryWrapper<GameGroupInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectOne(wrapper);
        Integer gamegroupId = gameGroupInfo.getGamegroupId();
        HashMap<Object, Object> map = new HashMap<>();
//        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        List<RecommendAppVO> appsByGameGroupId = tbGameGroupMapper.getRecommendApps(gamegroupId);
        for (RecommendAppVO vo : appsByGameGroupId) {
            String tag = vo.getTag();
            String[] split = new String(tag).split("[\\,\\;]");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            vo.setTags(list);
            if(null==tag){
                vo.setTags(Collections.emptyList());
            }

        }
        List<Object> total = new ArrayList<>();

        map.put("gameGroupName", gamegroupName);
        map.put("gameGroupPic", gamegroupPic);
        map.put("list", appsByGameGroupId);
        total.add(map);
        return ResultInfo.success(total);

    }

    @Override
    public ResultInfo getHotGameApps(HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        c.add(Calendar.MONTH, -1);
        Date start = c.getTime();
        String startDay = format.format(start);//前一月
        List<HotGameApps> hotGameApps = tbGameGroupMapper.getHotGameApps(startDay, dqrq);


        Integer i = 1;
        List<HotAppListVO> data = new ArrayList<>();
        for (HotGameApps vo : hotGameApps) {


            HotAppListVO hotAppListVO = new HotAppListVO();
            vo.setOrder(i++);
            BeanUtils.copyProperties(vo, hotAppListVO);
            String tag = vo.getTag();

            List<String> ls = new ArrayList<>();
            if (StringUtils.isNotEmpty(tag)&&tag!=null) {
                String[] split = new String(tag).split("[\\,\\;]");
                for (String s : split) {
                    ls.add(s);
                }
                if (ls != null && ls.size() > 0){
                    hotAppListVO.setTags(ls);
                }
            }
            if(null==tag){
                hotAppListVO.setTags(Collections.emptyList());
            }
            data.add(hotAppListVO);
        }

        return ResultInfo.success(data);

    }

    @Override
    public ResultInfo getNewApps(HttpServletRequest request) {
        List<NewAppsVO> list = tbGameGroupMapper.getNewApps();
        Integer i = 1;
        for (NewAppsVO vo : list) {
            vo.setOrder(i++);
            String tag = vo.getTag();
            if (StringUtils.isNotEmpty(tag)) {
                List<String> tags = new ArrayList<>();
                String[] split = new String(tag).split("[\\,\\;]");
                for (String s : split) {
                    tags.add(s);
                }
                vo.setTags(tags);
            }
            if(null==tag){
                vo.setTags(Collections.emptyList());
            }

        }
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getTopApps(HttpServletRequest request) {
        QueryWrapper<GameGroupInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 2);
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectOne(wrapper);
        Integer gamegroupId = gameGroupInfo.getGamegroupId();
        HashMap<Object, Object> map = new HashMap<>();
//        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        List<TopAppVO> appsByGameGroupId = tbGameGroupMapper.getTopApps(gamegroupId);
        for (TopAppVO vo : appsByGameGroupId) {
            String tag = vo.getTag();
            String[] split = new String(tag).split("[\\,\\;]");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            vo.setTags(list);

        }
        List<Object> total = new ArrayList<>();

        map.put("gameGroupName", gamegroupName);
        map.put("gameGroupPic", gamegroupPic);
        map.put("list", appsByGameGroupId);
        total.add(map);
        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getGameGroupApps(HttpServletRequest request) {
        List<Object> total = new ArrayList<>();
        //获取除了默认游戏组之外的游戏组

        List<GameGroupInfo> gameGroupInfos = gameGroupInfoMapper.selectList(new QueryWrapper<GameGroupInfo>().eq("valid", 1).eq("type", 0));
//        List<GameGroupInfo> records = gameGroupInfos.getRecords();
        for (GameGroupInfo r : gameGroupInfos) {
            if (r.getType() == 1) {
                continue;
            }
            HashMap<Object, Object> map = new HashMap<>();
            String gamegroupName = r.getGamegroupName();
            String gamegroupPic = r.getGamegroupPic();
            Integer gamegroupId = r.getGamegroupId();
            List<GameGroupAppsVO> records = tbGameGroupMapper.getGameGroupApps(gamegroupId);

//            List<GameGroupAppsVO> records = appsByGameGroupId.getRecords();
            for (GameGroupAppsVO vo : records) {
                String tag = vo.getTag();
                String[] split = new String(tag).split("[\\,\\;]");
                List<String> list = new ArrayList<>();
                for (String s : split) {
                    list.add(s);
                }
                vo.setTags(list);
            }
            map.put("gameGroupName", gamegroupName);
            map.put("gameGroupPic", gamegroupPic);
            map.put("list", records);
            total.add(map);
        }

        return ResultInfo.success(total);
    }

    @Override
    public ResultInfo getGroupById(HttpServletRequest request, Integer id) {
        QueryWrapper<GameGroupInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("gamegroup_id", id);
        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectOne(wrapper);
        Integer gamegroupId = gameGroupInfo.getGamegroupId();
        HashMap<Object, Object> map = new HashMap<>();
//        GameGroupInfo gameGroupInfo = gameGroupInfoMapper.selectById(1);
        String gamegroupName = gameGroupInfo.getGamegroupName();
        String gamegroupPic = gameGroupInfo.getGamegroupPic();
        List<RecommendAppVO> appsByGameGroupId = tbGameGroupMapper.getRecommendApps(gamegroupId);
        for (RecommendAppVO vo : appsByGameGroupId) {
            String tag = vo.getTag();
            String[] split = new String(tag).split("[\\,\\;]");
            List<String> list = new ArrayList<>();
            for (String s : split) {
                list.add(s);
            }
            vo.setTags(list);
            if(null==tag){
                vo.setTags(Collections.emptyList());
            }

        }
        List<Object> total = new ArrayList<>();

        map.put("gameGroupName", gamegroupName);
        map.put("gameGroupPic", gamegroupPic);
        map.put("list", appsByGameGroupId);
        total.add(map);
        return ResultInfo.success(total);
    }
}
