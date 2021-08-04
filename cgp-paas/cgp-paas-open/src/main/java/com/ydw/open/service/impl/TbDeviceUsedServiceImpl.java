package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.open.dao.ClusterOwnerMapper;
import com.ydw.open.dao.TbClustersMapper;
import com.ydw.open.dao.TbDeviceUsedMapper;
import com.ydw.open.dao.TbDevicesMapper;
import com.ydw.open.model.db.ClusterOwner;
import com.ydw.open.model.db.TbClusters;
import com.ydw.open.model.db.TbDeviceUsed;
import com.ydw.open.model.db.TbDevices;
import com.ydw.open.model.db.constants.Constant;
import com.ydw.open.model.vo.*;
import com.ydw.open.service.ITbDeviceUsedService;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.HttpUtil;
import com.ydw.open.utils.ResultInfo;
//import com.ydw.platform.dao.TbDevicesMapper;
import com.ydw.open.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-05-06
 */
@Service
public class TbDeviceUsedServiceImpl extends ServiceImpl<TbDeviceUsedMapper, TbDeviceUsed> implements ITbDeviceUsedService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TbDeviceUsedMapper tbDeviceUsedMapper;

    @Autowired
    private TbDevicesMapper tbDevicesMapper;
    @Autowired
    private TbClustersMapper tbClustersMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;

    @Value("${saas.url}")
    private String  saasUrl;
//    @Value("${url.schedule}")
    private String scheduleUrl;

    @Autowired
    private ClusterOwnerMapper clusterOwnerMapper;



    @Override
    public ResultInfo createUsed(HttpServletRequest request, TbDeviceUsed used) {
        tbDeviceUsedMapper.insert(used);
        return ResultInfo.success();
    }


    @Override
    public ResultInfo getUsedList(HttpServletRequest request, String  deviceName, String appName, String enterpriseName,
                                  String customId, String fromIp,String startTime, String endTime,Integer type,Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        Page<DeviceUsedVO> list = tbDeviceUsedMapper.getUsedList(deviceName, appName, enterpriseName, customId, fromIp, startTime, endTime, type,identification,buildPage );



        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getAppsConnectionCount(HttpServletRequest request, String startTime, String endTime, List<String> ids) {
//        TbUserInfo userInfoFromRequest = loginService.getUserInfoFromRequest(request);
        String token = request.getHeader("Authorization");
        ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
        String identification = identificationInfo.getMsg();
        List<String> timeList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long s = Long.valueOf(sdf.parse(startTime).getTime());
            long e = Long.valueOf(sdf.parse(endTime).getTime());
            //只有结束时间大于开始时间时才进行查询
            if (s < e) {
                timeList = findDates(startTime, endTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<TrendVO> appsConnectionCount = tbDeviceUsedMapper.getAppsConnectionCount(identification, ids, timeList);

        HashMap<Object, Object> obj = new HashMap<>();
        for (TrendVO vo : appsConnectionCount) {
            obj.put(vo.getDay() + "-" + vo.getAppId(), vo.getUseCount());
        }
        List<AppCountVO> apps = tbDeviceUsedMapper.getApps(identification);
        List<AppTrendVO> list = new ArrayList<>();
//        for (int i = 0; i < apps.size(); i++) {
//            HashMap<Object, Object> vo = new HashMap<>();
//            List<Object> ls = new ArrayList<>();
//            for (int j = 0; j < timeList.size(); j++) {
//                if (obj.get(timeList.get(j) + "-" + apps.get(i).getId()) != null) {
//                    ls.add(obj.get(timeList.get(j) + "-" + apps.get(i).getId()));
//                } else {
//                    ls.add("0");
//                }
//            }
//            vo.put("name", apps.get(i).getName());
//            vo.put("id",apps.get(i).getId());
//            vo.put("count", ls);
//            list.add(vo);
//        }
        for (int i = 0; i < apps.size(); i++) {
//                    HashMap<Object, Object> vo = new HashMap<>();
            List<Object> ls = new ArrayList<>();
            for (int j = 0; j < timeList.size(); j++) {
                if (obj.get(timeList.get(j) + "-" + apps.get(i).getId()) != null) {
                    ls.add(obj.get(timeList.get(j) + "-" + apps.get(i).getId()));
                } else {
                    ls.add("0");
                }
            }
            AppTrendVO appTrendVO = new AppTrendVO();
            appTrendVO.setCount(ls);
            appTrendVO.setId(apps.get(i).getId());
            appTrendVO.setName(apps.get(i).getName());
            list.add(appTrendVO);
        }
        JSONObject json = new JSONObject();
        List<AppTrendVO> newList = new ArrayList<>();
        //儅查詢某幾個应用时候
        if(ids.size()>0){

            for (String id:ids) {
                for ( AppTrendVO ls:list) {
                    if(ls.getId().equals(id)){
                        newList.add(ls);
                    }
                }
            }

        }
        if(newList.size()>0){
            json.put("list", newList);
        }else{
            json.put("list", list);
        }

        json.put("time", timeList);
//        json.put("list",appsConnectionCount);
        return ResultInfo.success(json);
    }

    @Override
    public ResultInfo getClusterConnectionsStatistics(HttpServletRequest request, String startTime, String endTime) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();


        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");

        List<ClusterConnectionsVO> nodesConnectionsStatistic = tbDeviceUsedMapper.getClusterConnectionsStatistics(identification,startTime, endTime);
        for (ClusterConnectionsVO vo: nodesConnectionsStatistic){
            String clusterId = vo.getClusterId();
            String netbarName = resmap.get(clusterId);
            vo.setNetbarName(netbarName);
            vo.setClusterName(netbarName);
        }
        return ResultInfo.success(nodesConnectionsStatistic);
    }

    @Override
    public ResultInfo getClusterConnectTimeStatistics(HttpServletRequest request, String startTime, String endTime) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();


        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");


        List<ClusterConnectionsVO> nodesConnectionsStatistic = tbDeviceUsedMapper.getClusterConnectTimeStatistics(startTime, endTime,identification);

        for(ClusterConnectionsVO vo:nodesConnectionsStatistic){
            String clusterId = vo.getClusterId();
            String netbarName = resmap.get(clusterId);
            vo.setNetbarName(netbarName);
            vo.setClusterName(netbarName);
        }
        return ResultInfo.success(nodesConnectionsStatistic);
    }

    @Override
    public ResultInfo getAppsConnectTimeStatistics(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage) {

        Page<AppsConnectionsVO> appsConnectTimeStatistics = tbDeviceUsedMapper.getAppsConnectTimeStatistics(startTime, endTime, clusterId, buildPage);
//        PageInfo<AppsConnectionsVO> pageInfo = new PageInfo<>(appsConnectTimeStatistics);
        return ResultInfo.success(appsConnectTimeStatistics);
    }

    @Override
    public ResultInfo getAppsConnectTimeStatisticsTopTen(HttpServletRequest request, String startTime, String endTime) {
        List<AppsConnectionsVO> list = tbDeviceUsedMapper.getAppsConnectTimeStatisticsTopTen(startTime, endTime);
        sortDoubleLowToUp(list);
        if (list.size() <= 10) {
            return ResultInfo.success(list);
        } else {
            return ResultInfo.success(list.subList(list.size() - 10, list.size()));
        }
    }

    @Override
    public ResultInfo getUsageTrend(HttpServletRequest request, String startTime, String endTime) {
        String token = request.getHeader("Authorization");
        ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
        String identification = identificationInfo.getMsg();
        List<String> timeList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long s = Long.valueOf(sdf.parse(startTime).getTime());
            long e = Long.valueOf(sdf.parse(endTime).getTime());
            //只有结束时间大于开始时间时才进行查询
            if (s < e) {
                timeList = findDates(startTime, endTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (String time : timeList) {
            System.out.println(time);
        }

        JSONObject json = new JSONObject();
        json.put("time", timeList);
        List<Object> data = new ArrayList<>();


        List<DeviceUsageTrendVO> all = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++) {

            List<DeviceUsageTrendVO> dayDeviceUsage = tbDeviceUsedMapper.getDayDeviceUsage(identification,timeList.get(i));
            for (DeviceUsageTrendVO vo : dayDeviceUsage) {
                all.add(vo);
            }
        }
        HashMap<Object, Object> obj = new HashMap<>();
        for (DeviceUsageTrendVO one : all) {
            obj.put(one.getDayTime() + "-" + one.getClusterId(), one.getPercentage());
        }
        List<TbClusters> allClusters = tbClustersMapper.getAllClusters(identification);

            //查网吧名
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object datas = resultInfo.getData();
        logger.info(datas+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(datas), HashMap.class);
        logger.info(resmap+"-------------res map");


        for (TbClusters tc : allClusters) {
            HashMap<Object, Object> map = new HashMap<>();
            List<Object> ls = new ArrayList<>();
            String tcId = tc.getId();
            String netbarName = resmap.get(tcId);
//            map.put("clusterName", tc.getName());
            map.put("clusterName", netbarName);
            for (int i = 0; i < timeList.size(); i++) {
                if (obj.get(timeList.get(i) + "-" + tc.getId()) != null) {
                    ls.add(obj.get(timeList.get(i) + "-" + tc.getId()));

                } else {
                    ls.add("0");
                }
            }
            map.put("deviceUsage", ls);
            data.add(map);
        }
        json.put("list", data);
        json.put("time", timeList);
        return ResultInfo.success(json);
    }

    @Override
    public ResultInfo getAppQueueNum(HttpServletRequest request, Page buildPage, String search) {

        String url = scheduleUrl + Constant.API_QUEUE_NUM;
        String token = request.getHeader("Authorization");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        String result = HttpUtil.doGet(url, headers,null);

        ResultInfo info = JSON.parseObject(result, ResultInfo.class);
        // logger.error("app is [" + parameter.getGameid() + "] " +  result.toString());
        List<AppQueueVO> ls = JSON.parseObject(JSON.toJSONString(info.getData()), List.class);
        List<AppQueueVO> newLs = new ArrayList<>();
        if (search != null) {
            for (int index = 0; index < ls.size(); index++) {
                JSONObject jsonObject = new JSONObject((Map<String, Object>) ls.get(index));
                if (jsonObject.getString("appName").contains(search)) {
                    newLs.add(ls.get(index));
                }
            }
        } else {
            newLs = ls;
        }
        Integer pageSize = (int) buildPage.getSize();
        Integer pageNum = (int) buildPage.getCurrent();
//        int pageNum = 2;//相当于pageNo
//        int pageSize = 20;//相当于pageSize
        if (null == pageNum && null == pageSize) {
//            PageInfo<AppQueueVO> pageInfo = new PageInfo(newLs);
            Page<AppQueueVO> page = new Page();
            page.setRecords(newLs);
            return ResultInfo.success(page);
        }
        int size = newLs.size();
        int pageCount = size / pageSize;
        int fromIndex = pageSize * (pageNum - 1);
        int toIndex = fromIndex + pageSize;
        if (toIndex >= size) {
            toIndex = size;
        }
        if (pageNum > pageCount + 1) {
            fromIndex = 0;
            toIndex = 0;
        }


//
//        List<AppQueueVO> currentPageList = new ArrayList<>();
//        if (ls != null && ls.size() > 0) {
//            int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
//            for (int i = 0; i < pageSize && i < ls.size() - currIdx; i++) {
//                AppQueueVO data = (AppQueueVO)ls.get(currIdx + i);
//                currentPageList.add(data);
//            }
//        }

        Page<AppQueueVO> page = new Page();
        page.setRecords(newLs.subList(fromIndex, toIndex));
        page.setTotal(newLs.size());
//        PageInfo<AppQueueVO> pageInfo = new PageInfo(newLs.subList(fromIndex, toIndex));
//        pageInfo.setTotal(newLs.size());
//        System.out.println(pageInfo);
//        return ResultInfo.success( pageInfo);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo clearQueueCorpse(HttpServletRequest request, List<AppQueueVO> vos) {
        String url = scheduleUrl + Constant.API_CLEAN_QUEUE_CORPSE;
        String token = request.getHeader("Authorization");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        HashMap<String, Object> params = new HashMap<>();
        List<AppQueueVO> list = new ArrayList<>();
        for (int i = 0; i < vos.size(); i++) {
            AppQueueVO queueVO = new AppQueueVO();
            String clusterId = vos.get(i).getClusterId();
            String appId = vos.get(i).getAppId();
            queueVO.setClusterId(clusterId);
            queueVO.setAppId(appId);
            list.add(queueVO);
        }


        String result = HttpUtil.doJsonPost(url, headers, list);
        ResultInfo info = JSON.parseObject(result, ResultInfo.class);
        return info;
    }

    @Override
    public ResultInfo getOnlineUsers(HttpServletRequest request, Page buildPage, String search) {

        Page<OnlineUserVO> ls = tbDeviceUsedMapper.getOnlineUsers(search, buildPage);
//        PageInfo<OnlineUserVO> pageInfo = new PageInfo<>(ls);
        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo getRecordPlayList(HttpServletRequest request, String id) {
        List<RecordListVO> recordPlayList = tbDeviceUsedMapper.getRecordPlayList(id);
        return ResultInfo.success(recordPlayList);
    }




    @Override
    public ResultInfo getAppsConnectionsStatistics(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage) {

        Page<AppsConnectionsVO> appsConnectTimeStatistics = tbDeviceUsedMapper.getAppsConnectionsStatistics(startTime, endTime, clusterId, buildPage);
//        PageInfo<AppsConnectionsVO> pageInfo = new PageInfo<>(appsConnectTimeStatistics);
        return ResultInfo.success(appsConnectTimeStatistics);
    }

    @Override
    public ResultInfo getAppsConnectionsRealTime(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage) {

        List<AppsConnectionsVO> list = tbDeviceUsedMapper.getAppsConnectionsRealTime(startTime, endTime, clusterId, buildPage);
//        PageInfo<AppsConnectionsVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getAppsConnectionsRealTimeTopTen(HttpServletRequest request, String startTime, String endTime) {
        Page buildPage = new Page();
        List<AppsConnectionsVO> list = tbDeviceUsedMapper.getAppsConnectionsRealTime(startTime, endTime, null, buildPage);

        //top10
        sortLowToUp(list);
//        System.out.println("排序后：" + List);
        if (list.size() <= 10) {
            return ResultInfo.success(list);
        } else {
            return ResultInfo.success(list.subList(list.size() - 10, list.size()));
        }
    }

    @Override
    public ResultInfo getAppsConnectionsStatisticsTopTen(HttpServletRequest request, String startTime, String endTime) {
        List<AppsConnectionsVO> list = tbDeviceUsedMapper.getAppsConnectionsStatisticsTopTen(startTime, endTime);

        sortDoubleLowToUp(list);
//        System.out.println("排序后：" + List);
        if (list.size() <= 10) {
            return ResultInfo.success(list);
        } else {
            return ResultInfo.success(list.subList(list.size() - 10, list.size()));
        }
    }


    private void sortDoubleLowToUp(List<AppsConnectionsVO> list) {
        Collections.sort(list, new Comparator<AppsConnectionsVO>() {
            /*
28              * int compare(Student o1, Student o2) 返回一个基本类型的整型，
29              * 返回负数表示：o1 小于o2，
30              * 返回0 表示：o1和o2相等，
31              * 返回正数表示：o1大于o2。
32              */
            public int compare(AppsConnectionsVO o1, AppsConnectionsVO o2) {

                //按照count进行升序排列
                if (Double.parseDouble(o1.getCount()) > Double.parseDouble(o2.getCount())) {
                    return 1;
                }
                if (o1.getCount() == o2.getCount()) {
                    return 0;
                }
                return -1;
            }
        });
    }

    private void sortLowToUp(List<AppsConnectionsVO> list) {
        Collections.sort(list, new Comparator<AppsConnectionsVO>() {
            /*
28              * int compare(Student o1, Student o2) 返回一个基本类型的整型，
29              * 返回负数表示：o1 小于o2，
30              * 返回0 表示：o1和o2相等，
31              * 返回正数表示：o1大于o2。
32              */
            public int compare(AppsConnectionsVO o1, AppsConnectionsVO o2) {

                //按照count进行升序排列
                if (Integer.parseInt(o1.getCount()) > Integer.parseInt(o2.getCount())) {
                    return 1;
                }
                if (o1.getCount() == o2.getCount()) {
                    return 0;
                }
                return -1;
            }
        });
    }

    @Override
    public ResultInfo getDeviceStatusCount(HttpServletRequest request, String clusterId) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        logger.info(id+"-----id---");
        String identification = id.getMsg();
        logger.info(identification+"-----identification---");
        List<DeviceStatusCountVO> ls = tbDevicesMapper.getDeviceStatusCount(clusterId,identification);
        logger.info(ls+"-----ls---");
        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo getDeviceUsageRate(HttpServletRequest request, String clusterId, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();

        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");


        Page<DeviceUsageCountVO> deviceUsageRate = tbDevicesMapper.getDeviceUsageRate(clusterId, identification,buildPage);
//        PageInfo<DeviceUsageCountVO> pageInfo = new PageInfo<>(deviceUsageRate);
        List<DeviceUsageCountVO> records = deviceUsageRate.getRecords();

        for (DeviceUsageCountVO  vo:records){
            String voClusterId = vo.getClusterId();
            String netbarName = resmap.get(voClusterId);
            vo.setNetbarName(netbarName);
            vo.setClusterName(netbarName);
        }
        deviceUsageRate.setRecords(records);
        return ResultInfo.success(deviceUsageRate);
    }

    @Override
    public ResultInfo getDeviceUsageHistory(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
        String identification = identificationInfo.getMsg();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long to = 0;
        long from = 0;
        try {
            to = df.parse(endTime).getTime();
            from = df.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = (to - from) / (1000 * 60 * 60 * 24) + 1;

//        System.out.println((to - from) / (1000 * 60 * 60 * 24));

        //查询网吧，名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");


        Page<DeviceUsageCountHistoryVO> list = tbDevicesMapper.getDeviceUsageHistory(startTime, endTime, clusterId, day, identification,buildPage);
//        PageInfo<DeviceUsageCountHistoryVO> pageInfo = new PageInfo<>(list);
        List<DeviceUsageCountHistoryVO> records = list.getRecords();
        for(DeviceUsageCountHistoryVO vo:records){
            String voClusterId = vo.getClusterId();
            String netbarName = resmap.get(voClusterId);
            vo.setClusterName(netbarName);
        }
        list.setRecords(records);

        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getUsedListById(HttpServletRequest request, String id) {
        DeviceUsedVO tbDeviceUsed = tbDeviceUsedMapper.getUsedById(id);
        return ResultInfo.success(tbDeviceUsed);
    }

    @Override
    public ResultInfo getClusterConnectionsRealTime(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();


        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");


        List<ClusterConnectionsVO> clusterConnectionsRealTime = tbDeviceUsedMapper.getClusterConnectionsRealTime(identification);
        for (ClusterConnectionsVO vo: clusterConnectionsRealTime ) {
            String clusterId = vo.getClusterId();
            String netbarName = resmap.get(clusterId);
            vo.setNetbarName(netbarName);
            vo.setClusterName(netbarName);
        }

        return ResultInfo.success(clusterConnectionsRealTime);
    }

    @Override
    public ResultInfo getTotalUsage(HttpServletRequest request, String startTime, String endTime) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        long day = 1;
        if (!StringUtil.nullOrEmpty(startTime) && !StringUtil.nullOrEmpty(endTime)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            long to = 0;
            long from = 0;

            try {
                to = df.parse(endTime).getTime();
                from = df.parse(startTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            day = (to - from) / (1000 * 60 * 60 * 24) + 1;
//           System.out.println((to - from) / (1000 * 60 * 60 * 24)+"----------------------------day");

        }

        String totalUsage = tbDevicesMapper.getTotalUsage(startTime, endTime, day,identification);

        return ResultInfo.success(totalUsage);
    }

    @Override
    public ResultInfo getAppInstallCount(HttpServletRequest request, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }

        List<AppDeviceCountVO> List = tbDevicesMapper.getAppInstallCount(buildPage);

        Collections.sort(List, new Comparator<AppDeviceCountVO>() {
            /*
28              * int compare(Student o1, Student o2) 返回一个基本类型的整型，
29              * 返回负数表示：o1 小于o2，
30              * 返回0 表示：o1和o2相等，
31              * 返回正数表示：o1大于o2。
32              */
            public int compare(AppDeviceCountVO o1, AppDeviceCountVO o2) {

                //按照count进行升序排列
                if (o1.getCount() < o2.getCount()) {
                    return 1;
                }
                if (o1.getCount() == o2.getCount()) {
                    return 0;
                }
                return -1;
            }
        });
//        System.out.println("排序后：" + List);
        if (List.size() <= 10) {
            return ResultInfo.success(List);
        } else {
            return ResultInfo.success(List.subList(0, 10));
        }


    }

    @Override
    public ResultInfo getClusterDevicesStatus(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
        String identification = identificationInfo.getMsg();


        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterid = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterid)){
                clusterIds.add(clusterid);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap<String,String> resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");



        List<ClusterDevicesStatusVO> ls = new ArrayList<>();
        //获取集群列表
        List<TbClusters> allClusters = tbClustersMapper.getAllClusters(identification);
        for (TbClusters tc : allClusters) {
            String id = tc.getId();
            String name = tc.getName();
            String netbarName = resmap.get(id);
            ClusterDevicesStatusVO clusterDevicesStatus = tbDevicesMapper.getClusterDevicesStatus(id);
            if (clusterDevicesStatus != null) {
                clusterDevicesStatus.setClusterName(netbarName);
                clusterDevicesStatus.setNetbarName(netbarName);
                ls.add(clusterDevicesStatus);
            }
        }
        return ResultInfo.success(ls);
    }



    @Override
    public ResultInfo getUsedListNetbar(HttpServletRequest request, String netbarName, String startTime, String endTime,Integer type, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
        String identification = identificationInfo.getMsg();
//       String identification = "91110108MA01QB042G";
        //查询网吧名称
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        List<String> clusterIds = new ArrayList<>();
        for (ClusterOwner co: clusterOwners) {
            String clusterId = co.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterId)){
                clusterIds.add(clusterId);
            }
        }

        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetbarbyClusterId";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("clusterIds",clusterIds);
        String result = HttpUtil.doPost(url, headers, clusterIds);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"-------------res map");
        HashMap resmap = JSONObject.parseObject(JSONObject.toJSONString(data), HashMap.class);
        logger.info(resmap+"-------------res map");
        if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
            startTime=startTime+" 00:00:00";
            endTime=endTime+" 23:59:59";
        }
        Page<NetbarUsedListVO> usedListNetbar = tbDeviceUsedMapper.getUsedListNetbar(identification, netbarName, startTime, endTime, type,buildPage);
        List<NetbarUsedListVO> records = usedListNetbar.getRecords();
//        List<Object> objects = new ArrayList<>();
        for(NetbarUsedListVO vo:records){
            String deviceId = vo.getDeviceId();
            QueryWrapper<TbDevices> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",deviceId);
            queryWrapper.eq("valid",1);
            TbDevices tbDevices = tbDevicesMapper.selectOne(queryWrapper);
            if(null!=tbDevices){
                String clusterId = tbDevices.getClusterId();
                Object name = resmap.get(clusterId);
                if(null!=name){
                    vo.setNetbarName(name.toString());
                }


            }
        }
        usedListNetbar.setRecords(records);
        String totalTime = tbDeviceUsedMapper.getTotalTime(identification,netbarName,startTime,endTime,type );
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",usedListNetbar);
        map.put("totalTime",totalTime);
        return ResultInfo.success(map);
    }


    /**
     * 查询时间
     *
     * @param stime
     * @param etime
     * @return
     * @throws ParseException
     */
    public static List<String> findDates(String stime, String etime)
            throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }
}
