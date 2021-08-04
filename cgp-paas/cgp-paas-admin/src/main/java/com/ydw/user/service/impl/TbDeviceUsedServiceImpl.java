package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.TbClustersMapper;
import com.ydw.user.dao.TbDevicesMapper;
import com.ydw.user.model.db.TbClusters;
import com.ydw.user.model.db.TbDeviceUsed;
import com.ydw.user.dao.TbDeviceUsedMapper;
import com.ydw.user.model.db.constants.Constant;
import com.ydw.user.model.vo.*;
import com.ydw.user.service.ITbDeviceUsedService;

import com.ydw.user.utils.HttpUtil;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
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

    @Autowired
    private TbDeviceUsedMapper tbDeviceUsedMapper;

    @Autowired
    private TbDevicesMapper tbDevicesMapper;
    @Autowired
    private TbClustersMapper tbClustersMapper;

    @Override
    public ResultInfo createUsed(HttpServletRequest request, TbDeviceUsed used) {
        tbDeviceUsedMapper.insert(used);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUsedList(HttpServletRequest request, String deviceName, String appName, String enterpriseName, String customId, String fromIp, String startTime, String endTime, Integer type,
            Page buildPage) {

//        if (null == pageNum && null == pageSize) {
//            pageNum = 1;
//            pageSize = 10;
//        } else {
//            pageNum = (pageNum - 1) * pageSize;
//        }
//        String total = tbDeviceUsedMapper.getTotal();
        if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
            startTime=startTime+" 00:00:00";
            endTime=endTime+" 23:59:59";
        }
        Page<DeviceUsedVO> list = tbDeviceUsedMapper.getUsedList(deviceName, appName, enterpriseName, customId, fromIp, startTime, endTime, type,buildPage);

//        PageInfo<DeviceUsedVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getClusterConnectionsStatistics(HttpServletRequest request, String startTime, String endTime) {

        List<ClusterConnectionsVO> nodesConnectionsStatistic = tbDeviceUsedMapper.getClusterConnectionsStatistics(startTime, endTime);
        return ResultInfo.success(nodesConnectionsStatistic);
    }

    @Override
    public ResultInfo getClusterConnectTimeStatistics(HttpServletRequest request, String startTime, String endTime) {
        List<ClusterConnectionsVO> nodesConnectionsStatistic = tbDeviceUsedMapper.getClusterConnectTimeStatistics(startTime, endTime);
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

            List<DeviceUsageTrendVO> dayDeviceUsage = tbDeviceUsedMapper.getDayDeviceUsage(timeList.get(i));
            for (DeviceUsageTrendVO vo : dayDeviceUsage) {
                all.add(vo);
            }
        }
        HashMap<Object, Object> obj = new HashMap<>();
        for (DeviceUsageTrendVO one : all) {
            obj.put(one.getDayTime() + "-" + one.getClusterId(), one.getPercentage());
        }
        List<TbClusters> allClusters = tbClustersMapper.getAllClusters();


        for (TbClusters tc : allClusters) {
            HashMap<Object, Object> map = new HashMap<>();
            List<Object> ls = new ArrayList<>();
            map.put("clusterName", tc.getName());
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

//        String url = scheduleUrl + Constant.API_QUEUE_NUM;
//        String token = request.getHeader("Authorization");
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", token);
//
//        String result = HttpUtil.doGet(url, headers);
//
//        ResultInfo info = JSON.parseObject(result, ResultInfo.class);
//        // logger.error("app is [" + parameter.getGameid() + "] " +  result.toString());
//        List<AppQueueVO> ls = JSON.parseObject(JSON.toJSONString(info.getData()), List.class);
//        List<AppQueueVO> newLs = new ArrayList<>();
//        if (search != null) {
//            for (int index = 0; index < ls.size(); index++) {
//                JSONObject jsonObject = new JSONObject((Map<String, Object>) ls.get(index));
//                if (jsonObject.getString("appName").contains(search)) {
//                    newLs.add(ls.get(index));
//                }
//            }
//        } else {
//            newLs = ls;
//        }
//        Integer pageSize = (int) buildPage.getSize();
//        Integer pageNum = (int) buildPage.getCurrent();
////        int pageNum = 2;//相当于pageNo
////        int pageSize = 20;//相当于pageSize
//        if (null == pageNum && null == pageSize) {
////            PageInfo<AppQueueVO> pageInfo = new PageInfo(newLs);
//            Page<AppQueueVO> page = new Page();
//            page.setRecords(newLs);
//            return ResultInfo.success(page);
//        }
//        int size = newLs.size();
//        int pageCount = size / pageSize;
//        int fromIndex = pageSize * (pageNum - 1);
//        int toIndex = fromIndex + pageSize;
//        if (toIndex >= size) {
//            toIndex = size;
//        }
//        if (pageNum > pageCount + 1) {
//            fromIndex = 0;
//            toIndex = 0;
//        }
//
//
////
////        List<AppQueueVO> currentPageList = new ArrayList<>();
////        if (ls != null && ls.size() > 0) {
////            int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
////            for (int i = 0; i < pageSize && i < ls.size() - currIdx; i++) {
////                AppQueueVO data = (AppQueueVO)ls.get(currIdx + i);
////                currentPageList.add(data);
////            }
////        }
//
//        Page<AppQueueVO> page = new Page();
//        page.setRecords(newLs.subList(fromIndex, toIndex));
//        page.setTotal(newLs.size());
////        PageInfo<AppQueueVO> pageInfo = new PageInfo(newLs.subList(fromIndex, toIndex));
////        pageInfo.setTotal(newLs.size());
////        System.out.println(pageInfo);
////        return ResultInfo.success( pageInfo);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo clearQueueCorpse(HttpServletRequest request, List<AppQueueVO> vos) {
//        String url = scheduleUrl + Constant.API_CLEAN_QUEUE_CORPSE;
//        String token = request.getHeader("Authorization");
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", token);
//
//        HashMap<String, Object> params = new HashMap<>();
//        List<AppQueueVO> list = new ArrayList<>();
//        for (int i = 0; i < vos.size(); i++) {
//            AppQueueVO queueVO = new AppQueueVO();
//            String clusterId = vos.get(i).getClusterId();
//            String appId = vos.get(i).getAppId();
//            queueVO.setClusterId(clusterId);
//            queueVO.setAppId(appId);
//            list.add(queueVO);
//        }
//
//
//        String result = HttpUtil.doJsonPost(url, headers, list);
//        ResultInfo info = JSON.parseObject(result, ResultInfo.class);
//        return info;
        return ResultInfo.success();
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
               return new Double(o1.getCount()).compareTo(new Double(o2.getCount()));

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
                return  new Integer(o1.getCount()).compareTo(new Integer(o2.getCount()));
//                if (Integer.parseInt(o1.getCount()) > Integer.parseInt(o2.getCount())) {
//                    return 1;
//                }
//                if (o1.getCount() == o2.getCount()) {
//                    return 0;
//                }
//                return -1;
            }
        });
    }

    @Override
    public ResultInfo getDeviceStatusCount(HttpServletRequest request, String clusterId) {
        List<DeviceStatusCountVO> ls = tbDevicesMapper.getDeviceStatusCount(clusterId);
        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo getDeviceUsageRate(HttpServletRequest request, String clusterId, Page buildPage) {


        Page<DeviceUsageCountVO> deviceUsageRate = tbDevicesMapper.getDeviceUsageRate(clusterId, buildPage);
//        PageInfo<DeviceUsageCountVO> pageInfo = new PageInfo<>(deviceUsageRate);
        return ResultInfo.success(deviceUsageRate);
    }

    @Override
    public ResultInfo getDeviceUsageHistory(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage) {


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
        Page<DeviceUsageCountHistoryVO> list = tbDevicesMapper.getDeviceUsageHistory(startTime, endTime, clusterId, day, buildPage);
//        PageInfo<DeviceUsageCountHistoryVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getUsedListById(HttpServletRequest request, String id) {
        DeviceUsedVO tbDeviceUsed = tbDeviceUsedMapper.getUsedById(id);
        return ResultInfo.success(tbDeviceUsed);
    }

    @Override
    public ResultInfo getClusterConnectionsRealTime(HttpServletRequest request) {
        List<ClusterConnectionsVO> clusterConnectionsRealTime = tbDeviceUsedMapper.getClusterConnectionsRealTime();
        return ResultInfo.success(clusterConnectionsRealTime);
    }

    @Override
    public ResultInfo getTotalUsage(HttpServletRequest request, String startTime, String endTime) {
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

        String totalUsage = tbDevicesMapper.getTotalUsage(startTime, endTime, day);

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
        List<ClusterDevicesStatusVO> ls = new ArrayList<>();
        //获取集群列表
        List<TbClusters> allClusters = tbClustersMapper.getAllClusters();
        for (TbClusters tc : allClusters) {
            String id = tc.getId();
            String name = tc.getName();
            ClusterDevicesStatusVO clusterDevicesStatus = tbDevicesMapper.getClusterDevicesStatus(id);
            if (clusterDevicesStatus != null) {
                clusterDevicesStatus.setClusterName(name);
                ls.add(clusterDevicesStatus);
            }
        }
        return ResultInfo.success(ls);
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

    @Override
    public ResultInfo getUsedSummary(String deviceName, String appName, String enterpriseName,
                                     String customId, String fromIp, String startTime, String endTime, Integer type) {
        if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
            startTime=startTime+" 00:00:00";
            endTime=endTime+" 23:59:59";
        }
        ConnectSummaryVO usedSummary = tbDeviceUsedMapper.getUsedSummary(deviceName, appName, enterpriseName, customId, fromIp, startTime, endTime,type);
        return ResultInfo.success(usedSummary);
    }
}
