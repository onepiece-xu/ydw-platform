package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.platform.dao.ClusterNetbarMapper;
import com.ydw.platform.dao.NetBarInfoMapper;
import com.ydw.platform.dao.NetbarPicMapper;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.db.ClusterNetbar;
import com.ydw.platform.model.db.Clusters;
import com.ydw.platform.model.db.NetBarInfo;
import com.ydw.platform.model.db.NetbarPic;
import com.ydw.platform.model.vo.*;
import com.ydw.platform.service.INetBarInfoService;
import com.ydw.platform.service.IYdwAuthenticationService;
import com.ydw.platform.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
@Service
public class NetBarInfoServiceImpl extends ServiceImpl<NetBarInfoMapper, NetBarInfo> implements INetBarInfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.netbarPic}")
    private String netbarPicPath;

    @Value("${url.pics}")
    private String pics;
    @Value("${url.paasApi}")
    private String paasUrl;

    @Autowired
    private NetBarInfoMapper netBarInfoMapper;

    @Autowired
    private NetbarPicMapper netbarPicMapper;

    @Autowired
    private FeginClientService feginClientService;

//    @Autowired
//    private NetbarEnterpriseMapper netbarEnterpriseMapper;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;
    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;
    @Value("${config.appId}")
    private String appId;
    @Value("${url.paasApi}")
    private String paasApi;

    @Override
    public ResultInfo getNetBarList(HttpServletRequest request, String name, String province, String city, String district, Page buildPage) {
//        String token = request.getHeader("Authorization");
//        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
//        String identification = res.getMsg();
        String url = paasApi + "/cgp-paas-admin/v1/clusters/getClusters";
        String enterprisePaasToken = feginClientService.getPaasToken();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", enterprisePaasToken);
        params.put("pageNum","1");
        params.put("pageSize","1000");
        String doGet = HttpUtil.doGet(url, headers,params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        Object data = info.getData();
        logger.info(data.toString()+"___________________data_");
        List<Clusters> clusterVOS = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), Clusters.class);


        Map<String, String> map = new HashMap<>();
        Map<String, Integer> portMap = new HashMap<>();
        for(Clusters vo:clusterVOS) {
            map.put(vo.getId(), vo.getAccessIp());
            portMap.put(vo.getId(), vo.getAccessPort());
            logger.info(vo.getId()+"-------------------"+vo.getAccessIp());
        }

//        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
//        String identification = enterprise.getIdentification();
        Page<NetbarInfoVO> netBarList = netBarInfoMapper.getNetBarList(null, name, province, city, district,buildPage);
        List<NetbarInfoVO> records = netBarList.getRecords();
        List<NetbarInfoVO> newrecords = new ArrayList<>();
        for (NetbarInfoVO v : records) {
            //count 数量
            String id = v.getId();
            QueryWrapper<ClusterNetbar> wrapper0 = new QueryWrapper<>();
            wrapper0.eq("netbar_id", id);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper0);
            v.setAppId(appId);
            if (null != clusterNetbar) {
                String clusterId = clusterNetbar.getClusterId();
                v.setClusterId(clusterId);
                String accessip = map.get(clusterId);
                if(!StringUtils.isNullOrEmpty(accessip)){
                    v.setIp(accessip);
                    v.setPort(portMap.get(clusterId));
                    newrecords.add(v);
                }

            }

            QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
            wrapper.eq("valid", 1);
            wrapper.eq("netbar_id", id);
            List<NetbarPic> netbarPics = netbarPicMapper.selectList(wrapper);
//            int size = netbarPics.size();
            List<String> picList = new ArrayList<>();
            for (int i = 0; i < netbarPics.size(); i++) {
                picList.add(pics + netbarPics.get(i).getPic());
            }
            v.setPics(picList);
        }

        netBarList.setRecords(newrecords);
        return ResultInfo.success(netBarList);
    }

    @Override
    public ResultInfo getNetBarInfo(String id) {
        String url = paasApi + "/cgp-paas-admin/v1/clusters/getClusters";
        String enterprisePaasToken = feginClientService.getPaasToken();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", enterprisePaasToken);
        params.put("pageNum","1");
        params.put("pageSize","1000");
        String doGet = HttpUtil.doGet(url, headers,params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        Object data = info.getData();
        logger.info(data.toString()+"___________________data_");
        List<Clusters> clusterVOS = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), Clusters.class);
        Map<String, String> map = new HashMap<>();
        for(Clusters vo:clusterVOS) {
            map.put(vo.getId(), vo.getAccessIp());
            logger.info(vo.getId()+"-------------------"+vo.getAccessIp());
        }





        QueryWrapper<NetBarInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("valid", 1);
        wrapper.eq("id", id);
        NetBarInfo netBarInfo = netBarInfoMapper.selectOne(wrapper);
        NetbarInfoVO v = new NetbarInfoVO();
        BeanUtils.copyProperties(netBarInfo, v);
        QueryWrapper<NetbarPic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid", 1);
        queryWrapper.eq("netbar_id", id);
        List<NetbarPic> netbarPics = netbarPicMapper.selectList(queryWrapper);
//        for (int i = 0; i < netbarPics.size(); i++) {
//            if (i == 0) {
//                v.setFirstPic(pics + netbarPics.get(i).getPic());
//            }
//            if (i == 1) {
//
//                v.setSecondPic(pics + netbarPics.get(i).getPic());
//            }
//            if (i == 2) {
//                v.setThirdPic(pics + netbarPics.get(i).getPic());
//            }
//            if (i == 3) {
//
//                v.setFourthPic(pics + netbarPics.get(i).getPic());
//            }
//            if (i == 4) {
//
//                v.setFifthPic(pics + netbarPics.get(i).getPic());
//            }
//        }
        List<String> list = new ArrayList<>();
        for(NetbarPic bar:netbarPics){
            list.add(pics+bar.getPic());
        }
        v.setPics(list);
        QueryWrapper<ClusterNetbar> wrap = new QueryWrapper<>();
        wrap.eq("netbar_id", id);
        ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrap);
        v.setAppId(appId);
        if(null!=clusterNetbar){
            String clusterId = clusterNetbar.getClusterId();
            String ip = map.get(clusterId);
            v.setIp(ip);
            v.setClusterId(clusterId);
        }
        return ResultInfo.success(v);
    }


    @Override
    public ResultInfo getAllNetBarList(HttpServletRequest request, Page buildPage) {
        Page<NetbarListVO> allNetBarList = netBarInfoMapper.getAllNetBarList(buildPage);
        return ResultInfo.success(allNetBarList);
    }

    @Override
    public ResultInfo getAllNetBarStatus(HttpServletRequest request, Page buildPage) {
        String url = paasApi + "/cgp-paas-admin/v1/clusters/getClusters";
        String enterprisePaasToken = feginClientService.getPaasToken();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", enterprisePaasToken);
        params.put("pageNum","1");
        params.put("pageSize","1000");
        String doGet = HttpUtil.doGet(url, headers,params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        Object data = info.getData();
        logger.info(data.toString()+"___________________data_");
        List<Clusters> clusterVOS = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), Clusters.class);
        logger.error(clusterVOS.size()+"-------------------------size");
//        QueryWrapper<Clusters> wrapper = new QueryWrapper<>();
//        wrapper.eq("valid", 1);
//        List<Clusters> clusters = clustersMapper.selectList(wrapper);
        List<NetBarStatusVO> list = new ArrayList<>();
        for (Clusters cl : clusterVOS) {
            NetBarStatusVO vo = new NetBarStatusVO();
            String clId = cl.getId();
            QueryWrapper<ClusterNetbar> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("cluster_id", clId);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper1);
            if (null != clusterNetbar) {
                String netbarId = clusterNetbar.getNetbarId();
                vo.setClusterId(clId);
                vo.setNetbarId(netbarId);
                list.add(vo);
                Map<String, String> paramss = new HashMap<>();
                paramss.put("id", clId);
                String result = HttpUtil.doGet(paasUrl + Constant.CLUSTER_STATUS,
                        feginClientService.buildHeaders(), paramss);
                JSONObject jsonObject = JSON.parseObject(result);
                String datas = jsonObject.getString("data");
                logger.error(datas+"-------------------------datas");
                JSONObject obj = JSON.parseObject(datas);
                logger.error(obj+"-------------------------obj");
                Integer idleCount = obj.getInteger("idleCount");
                Integer totalCount = obj.getInteger("totalCount");
                if (totalCount != 0) {
                    Float num = (float) idleCount * 100 / totalCount;
                    if (num > 50) {
                        vo.setStatus("空闲");
                    }
                    if (num < 20) {
                        vo.setStatus("繁忙");
                    }
                    if (num > 20 && num < 50) {
                        vo.setStatus("拥挤");
                    }
//               DecimalFormat df = new DecimalFormat("0.00");//格式化小数 ， 其实这里还可以这样写 DecimalFormat("0.00%");   这样就不用在最后输出时还要加。
//               String status = df.format(num);
                }
            }


        }
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getNetBarListAndroid(HttpServletRequest request, String name, String province, String city, String district, Page buildPage) {
        String url = paasApi + "/cgp-paas-admin/v1/clusters/getClusters";
        String enterprisePaasToken = feginClientService.getPaasToken();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", enterprisePaasToken);
        params.put("pageNum","1");
        params.put("pageSize","1000");
        String doGet = HttpUtil.doGet(url, headers,params);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        Object data = info.getData();
        logger.info(data.toString()+"___________________data_");
        List<Clusters> clusterVOS = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), Clusters.class);


        Map<String, String> map = new HashMap<>();
        for(Clusters vo:clusterVOS) {
            map.put(vo.getId(), vo.getAccessIp());
            logger.info(vo.getId()+"-------------------"+vo.getAccessIp());
        }


        Page<NetbarListAppVO> netBarList = netBarInfoMapper.getNetBarListAndroid(null, name, province, city, district,buildPage);
        List<NetbarListAppVO> records = netBarList.getRecords();
        List<NetbarListAppVO> newrecords = new ArrayList<>();
        for (NetbarListAppVO v : records) {
            v.setAppId(appId);
            //count 数量
            String id = v.getId();

            List<String> netbarPics = netbarPicMapper.getPics(id);
            List<String> ls = new ArrayList<>();
            for(String vo :netbarPics){
                ls.add(pics+vo);

            }
            v.setPics(ls);
            //临时 将任意图片返回到logo字段
            v.setLogoPic(ls.get(0));
            QueryWrapper<ClusterNetbar> wrapper0 = new QueryWrapper<>();
            wrapper0.eq("netbar_id", id);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper0);
//            v.setAppId(appId);
            if (null != clusterNetbar) {
                String clusterId = clusterNetbar.getClusterId();
//                v.setClusterId(clusterId);
                String accessip = map.get(clusterId);
                if (!StringUtils.isNullOrEmpty(accessip)) {
                    v.setIp(accessip);
                    newrecords.add(v);
                }
            }
        }
        netBarList.setRecords(newrecords);
        return ResultInfo.success(netBarList);
    }

    @Override
    public ResultInfo getNetBarStatus(HttpServletRequest request, String name, String province, String city, String district, Page buildPage) {

        List<NetbarClusterVO> netbarClusterIds = netBarInfoMapper.getNetbarClusterIds(name, province, city, district, buildPage);
        for (NetbarClusterVO vo : netbarClusterIds) {
            String clusterId = vo.getClusterId();
            Map<String, String> paramss = new HashMap<>();
            paramss.put("id", clusterId);
            String result = HttpUtil.doGet(paasUrl + Constant.CLUSTER_STATUS,
                   feginClientService.buildHeaders(), paramss);
            JSONObject jsonObject = JSON.parseObject(result);
            String datas = jsonObject.getString("data");
            logger.error(datas + "-------------------------datas");
            JSONObject obj = JSON.parseObject(datas);
            logger.error(obj + "-------------------------obj");
            Integer idleCount = obj.getInteger("idleCount");
            Integer totalCount = obj.getInteger("totalCount");
            if (totalCount != 0) {
                Float num = (float) idleCount * 100 / totalCount;
                if (num > 50) {
                    vo.setStatus(1);
                }
                if (num < 20) {
                    vo.setStatus(3);
                }
                if (num > 20 && num < 50) {
                    vo.setStatus(2);
                }
            }else{
                vo.setStatus(3);
            }
        }
        return ResultInfo.success(netbarClusterIds);
    }
}
