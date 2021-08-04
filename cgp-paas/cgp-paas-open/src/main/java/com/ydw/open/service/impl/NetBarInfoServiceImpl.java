package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.ydw.open.dao.*;
import com.ydw.open.model.db.*;
import com.ydw.open.model.vo.BaseInfo;
import com.ydw.open.model.vo.CountVO;
import com.ydw.open.model.vo.NetbarInfoVO;
import com.ydw.open.model.vo.NetbarListVO;
import com.ydw.open.service.INetBarInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    @Value("${config.netbarUpload}")
    private String netbarUploadPath;

    @Value("${url.pics}")
    private String pics;
    @Value("${saas.url}")
    private String saasUrl;

//    @Value("${config.temp}")
//    private String temp;

    @Autowired
    private NetBarInfoMapper netBarInfoMapper;

    @Autowired
    private NetbarPicMapper netbarPicMapper;

    @Autowired
    private NetbarEnterpriseMapper netbarEnterpriseMapper;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;
    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;
    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;

    @Autowired
    private TbClustersMapper tbClustersMapper;

    @Override
    public ResultInfo addNetBarInfo(HttpServletRequest request, NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4
            , MultipartFile file5, MultipartFile logo) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();
//       String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/addNetBar";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        HashMap<String, Object> params = new HashMap<>();
//        String temp ="D:\\test\\";
//        byte[] bytes1 = file2.getBytes();
        if (null != logo) {
//            String str = temp + logo.getOriginalFilename();
//            File file = new File(str);

            try {
//                logo.transferTo(file);
                byte[] logoBytes = logo.getBytes();
                params.put("logo", logoBytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file1) {


            try {
//                file1.transferTo(file);
                byte[] file1Bytes = file1.getBytes();
                params.put("file1", file1Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file2) {


            try {
//                file2.transferTo(file);
                byte[] file2Bytes = file2.getBytes();
                params.put("file2", file2Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file3) {


            try {
//                file3.transferTo(file);
                byte[] file3Bytes = file3.getBytes();
                params.put("file3", file3Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file4) {


            try {
//                file4.transferTo(file);
                byte[] file4Bytes = file4.getBytes();
                params.put("file4", file4Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file5) {


            try {
//                file5.transferTo(file);
                byte[] file5Bytes = file5.getBytes();
                params.put("file5", file5Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String baseStation = info.getBaseStation();
        String matchStation = info.getMatchStation();
        String city = info.getCity();
        String district = info.getDistrict();
        String province = info.getProvince();
        String businessHours = info.getBusinessHours();
        String location = info.getLocation();
        String specialService = info.getSpecialService();
        String name = info.getName();
        params.put("name", name);
        params.put("businessHours", businessHours);
        params.put("province", province);
        params.put("city", city);
        params.put("district", district);
        params.put("location", location);
        params.put("baseStation", baseStation);
        params.put("matchStation", matchStation);
        params.put("specialService", specialService);
        params.put("identification", identification);


        byte[] bytes = HttpUtil.doFilesPost(url, headers, params);
        ResultInfo resultInfo = JSON.parseObject(bytes, ResultInfo.class);



        return resultInfo;
    }

    @Override
    public ResultInfo getNetBarList(HttpServletRequest request, String name, String province, String city, String district, Page buildPage) {

        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();
        logger.error(identification+"-------------------identification");
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
//        logger.error(saasToken+"+------------saasToken-----------");
        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetBarList";
        logger.error(url+"+------------getNetBarList列表请求url-----------");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        HashMap<String,Object> params = new HashMap<>();
        long size0 = buildPage.getSize();
        int size1 = (int)size0;
        long current0 = buildPage.getCurrent();
        int current1 = (int)current0;
        params.put("pageSize",size1+"");
        params.put("pageNum",current1+"");
        params.put("identification",identification);
        if(!StringUtils.isNullOrEmpty(name)){
            params.put("name",name);
        }

        String result = HttpUtil.doGet(url, headers, params);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        logger.error(resultInfo+"+------------getNetBarList列表请求结果-----------");
        Object data = resultInfo.getData();
        if(data==null){
            return  ResultInfo.fail("登录失效请重新登录");
        }
        List<NetbarInfoVO> records = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), NetbarInfoVO.class);
        JSONObject object = JSON.parseObject(data.toString());
//        List<NetbarInfoVO> records = (List<NetbarInfoVO>) object.get("records");
        Integer current = object.getInteger("current");
        Integer size = object.getInteger("size");
        Integer total = object.getInteger("total");
        logger.error(records+"+------------getNetBarList列表-----------");
//        List<NetbarInfoVO> datas = new ArrayList<>();
//        List<NetbarInfoVO> studentList1 = JSON.parseArray(JSON.parseObject(data).getString("studentList"), NetbarInfoVO.class);
//        for(NetbarInfoVO v:records){
//
//            NetbarInfoVO netbarInfoVO = new NetbarInfoVO();
//            BeanUtils.copyProperties(v,netbarInfoVO);
//            datas.add(v);
//        }

        for(NetbarInfoVO vo:records){
            String clusterId = vo.getClusterId();
            if(!StringUtil.nullOrEmpty(clusterId)){
                CountVO deviceCount = netBarInfoMapper.getDeviceCount(clusterId);
                vo.setTotalCount(deviceCount.getTotalCount());
                vo.setAvailableCount(deviceCount.getAvailableCount());
                vo.setIdleCount(deviceCount.getIdleCount());
            }
        }

        Page<NetbarInfoVO> netBarList = new Page<>();
        netBarList.setRecords(records);
        netBarList.setTotal(total);
        netBarList.setCurrent(current);
        netBarList.setSize(size);


        return ResultInfo.success(netBarList);
    }

    @Override
    public ResultInfo getNetBarInfo(HttpServletRequest request, String id) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/getNetBarInfo/" + id;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String,String> params = new HashMap<>();
        String result = HttpUtil.doGet(url, headers, null);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        JSONObject object = JSON.parseObject(data.toString());
        NetbarInfoVO netbarInfoVO = JSONObject.toJavaObject(object, NetbarInfoVO.class);
//        NetbarInfoVO netbarInfoVO =(NetbarInfoVO)object;
/*        QueryWrapper<NetBarInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("valid", 1);
        wrapper.eq("id", id);
        NetBarInfo netBarInfo = netBarInfoMapper.selectOne(wrapper);
        NetbarInfoVO v = new NetbarInfoVO();
        BeanUtils.copyProperties(netBarInfo, v);
        QueryWrapper<NetbarPic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid", 1);
        queryWrapper.eq("netbar_id", id);
        List<NetbarPic> netbarPics = netbarPicMapper.selectList(queryWrapper);
        for (int i = 0; i < netbarPics.size(); i++) {
            if (i == 0) {

                v.setFirstPic(pics + netbarPics.get(i).getPic());
            }
            if (i == 1) {

                v.setSecondPic(pics + netbarPics.get(i).getPic());
            }
            if (i == 2) {

                v.setThirdPic(pics + netbarPics.get(i).getPic());
            }
            if (i == 3) {

                v.setFourthPic(pics + netbarPics.get(i).getPic());
            }
            if (i == 4) {

                v.setFifthPic(pics + netbarPics.get(i).getPic());
            }
        }*/
        return ResultInfo.success(netbarInfoVO);
    }

    @Override
    public ResultInfo updateNetBar(HttpServletRequest request, NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3,
                                   MultipartFile file4, MultipartFile file5, MultipartFile logo) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/updateNetBar";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        HashMap<String, Object> params = new HashMap<>();
//        String temp ="D:\\test\\";
        if (null != logo) {
//            String str = temp + logo.getOriginalFilename();
//            File file = new File(str);

            try {
//                logo.transferTo(file);
                byte[] logoBytes = logo.getBytes();
                params.put("logo", logoBytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file1) {
//            String str1 = temp + file1.getOriginalFilename();
//            File file = new File(str1);

            try {
//                file1.transferTo(file);
                byte[] file1Bytes = file1.getBytes();
                params.put("file1", file1Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file2) {
//            String str2 = temp + file2.getOriginalFilename();
//            File file = new File(str2);

            try {
//                file2.transferTo(file);
                byte[] file2Bytes = file2.getBytes();
                params.put("file2", file2Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file3) {
//            String str3 = temp + file3.getOriginalFilename();
//            File file = new File(str3);

            try {
//                file3.transferTo(file);
                byte[] file3Bytes = file3.getBytes();
                params.put("file3", file3Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file4) {
//            String str4 = temp + file4.getOriginalFilename();
//            File file = new File(str4);

            try {
//                file4.transferTo(file);
                byte[] file4Bytes = file4.getBytes();
                params.put("file4", file4Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file5) {
//            String str5 = temp + file5.getOriginalFilename();
//            File file = new File(str5);

            try {
//                file5.transferTo(file);
                byte[] file5Bytes = file5.getBytes();
                params.put("file5", file5Bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String baseStation = info.getBaseStation();
        String matchStation = info.getMatchStation();
        String city = info.getCity();
        String district = info.getDistrict();
        String province = info.getProvince();
        String businessHours = info.getBusinessHours();
        String location = info.getLocation();
        String specialService = info.getSpecialService();
        String name = info.getName();
        String id = info.getId();
        params.put("id", id);
        params.put("name", name);
        params.put("businessHours", businessHours);
        params.put("province", province);
        params.put("city", city);
        params.put("district", district);
        params.put("location", location);
        params.put("baseStation", baseStation);
        params.put("matchStation", matchStation);
        params.put("specialService", specialService);

        byte[] bytes = HttpUtil.doFilesPost(url, headers, params);
        ResultInfo resultInfo = JSON.parseObject(bytes, ResultInfo.class);

        /*netBarInfoMapper.updateById(info);
        String id = info.getId();
//        String  identification="91110108MA01QB042G";
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        ftpClient.enterLocalPassiveMode();

        try {
            if (null != logo) {
                String logoName = logo.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_logo.jpg", logo.getInputStream());

            }
            if (null != file1) {
                String file1OriginalFilename = file1.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic1.jpg", file1.getInputStream());

            }
            if (null != file2) {
                String file2OriginalFilename = file2.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic2.jpg", file2.getInputStream());

            }
            if (null != file3) {
                String file3OriginalFilename = file3.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic3.jpg", file3.getInputStream());

            }

            if (null != file4) {
                String file4OriginalFilename = file4.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic4.jpg", file4.getInputStream());

            }
            if (null != file5) {
                String file5OriginalFilename = file5.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic5.jpg", file5.getInputStream());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    @Override
    public ResultInfo getAllNetBarList(HttpServletRequest request, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();

        logger.info(token+"_____________token");
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/getAllNetBarList";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        long size0 = buildPage.getSize();
        int size1 = (int)size0;
        long current0 = buildPage.getCurrent();
        int current1 = (int)current0;
        HashMap<String,Object> params = new HashMap<>();
        params.put("pageSize",size1+"");
        params.put("pageNum",current1+"");
        String result = HttpUtil.doGet(url, headers, params);
        logger.info(result+"------result-----------------");
        logger.info(url+"------url-----------------");

        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        Object data = resultInfo.getData();
        logger.info(data+"---------------");
        JSONObject object = JSON.parseObject(data.toString());
        List<NetbarListVO> records = JSON.parseArray(JSON.parseObject(data.toString()).getString("records"), NetbarListVO.class);
        Integer current = object.getInteger("current");
        Integer size = object.getInteger("size");
        Integer total = object.getInteger("total");
        Page<NetbarListVO> netBarList = new Page<>();
        for(NetbarListVO vo:records){
            String clusterId = vo.getClusterId();
            TbClusters tbClusters = tbClustersMapper.selectById(clusterId);
            if(null!=tbClusters){
                String name = tbClusters.getName();
                vo.setClusterName(name);
            }
        }
        netBarList.setRecords(records);
        netBarList.setTotal(total);
        netBarList.setCurrent(current);
        netBarList.setSize(size);
        /*
//        QueryWrapper<NetBarInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("valid",1);
        Page<NetbarListVO> allNetBarList = netBarInfoMapper.getAllNetBarList(buildPage);
        return ResultInfo.success(allNetBarList);*/
        return ResultInfo.success(netBarList);
    }

    @Override
    public ResultInfo deleteNetbar(HttpServletRequest request, List<String> ids) {

        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
        String identification = res.getMsg();
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/deleteNetbar";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String,String> params = new HashMap<>();
        String result = HttpUtil.doPost(url, headers, ids);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        System.out.println(resultInfo);
       /* for (String id : ids) {
            NetBarInfo netBarInfo = new NetBarInfo();
            netBarInfo.setId(id);
            netBarInfo.setValid(false);
            netBarInfoMapper.updateById(netBarInfo);
            QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
            wrapper.eq("netbar_id", id);
            wrapper.eq("valid", 1);
            List<ClusterNetbar> clusterNetbars = clusterNetbarMapper.selectList(wrapper);
            if (clusterNetbars.size() > 0) {
                for (ClusterNetbar c : clusterNetbars) {
                    c.setValid(false);
                    clusterNetbarMapper.updateById(c);
                }
            }
        }*/
        return resultInfo;
    }

    @Override
    public ResultInfo getBaseStation(HttpServletRequest request, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        //获取saastoken
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/getBaseStation";
        logger.error(url+"----getBaseStation------------url");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        HashMap<String,String> params = new HashMap<>();
        String result = HttpUtil.doGet(url, headers, null);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        logger.error(resultInfo+"----getBaseStation------------res");
        List<BaseInfo> baseStation = (List<BaseInfo>) resultInfo.getData();


//        List<BaseInfo> baseStation = netBarInfoMapper.getBaseStation();
        return ResultInfo.success(baseStation);
    }

    @Override
    public ResultInfo getMatchStation(HttpServletRequest request, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        //获取saastoken
//        String identification = "91110108MA01QB042G";
//        String saasToken = getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/netBarInfo/getMatchStation";
        logger.info(url+"----------------------");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        logger.info(token+"----------------------");
//        HashMap<String,String> params = new HashMap<>();
        String result = HttpUtil.doGet(url, headers, null);
        logger.info(result+"----------------------");
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        List<BaseInfo> matchStation = (List<BaseInfo>) resultInfo.getData();
//        List<BaseInfo> matchStation = netBarInfoMapper.getMatchStation();
        return ResultInfo.success(matchStation);
    }

    public String getSaasToken(String id) {
        QueryWrapper<TbUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("identification", id);
        wrapper.eq("valid", 1);

        TbUserInfo tbUserInfo = tbUserInfoMapper.selectOne(wrapper);
        String secretKey = tbUserInfo.getSecretKey();
        String url = saasUrl + "cgp-saas-authentication/login/getTokenByIdentification";
        HashMap<String, Object> params = new HashMap<>();
//        HashMap<String, String> headers = new HashMap<>();
        params.put("identification", id);
        params.put("secretKey", secretKey);
        String result = HttpUtil.doJsonPost(url, null, params);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        String saasToken = resultInfo.getMsg();
        return saasToken;
    }
}
