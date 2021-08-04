package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.ClusterNetbar;
import com.ydw.admin.model.db.Enterprise;
import com.ydw.admin.model.db.NetBarInfo;
import com.ydw.admin.model.db.NetbarPic;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.INetBarInfoService;
import com.ydw.admin.service.IYdwAuthenticationService;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.HttpUtil;
import com.ydw.admin.util.SequenceGenerator;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
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
    @Value("${url.paasApi}")
    private String paasApi;


    @Autowired
    private NetBarInfoMapper netBarInfoMapper;

    @Autowired
    private NetbarPicMapper netbarPicMapper;

    @Autowired
    private NetbarAppMapper netbarAppMapper;

//    @Autowired
//    private NetbarEnterpriseMapper netbarEnterpriseMapper;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;
    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;


    @Override
    public ResultInfo addNetBarInfo(HttpServletRequest request, NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4
            , MultipartFile file5, MultipartFile logo) {
        String token = request.getHeader("Authorization");
        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("创建失败！");
        }

        String sequenceId = SequenceGenerator.sequence();
        info.setId(sequenceId);
        info.setValid(true);
        if (null != logo) {
            info.setLogoPic(pics + netbarPicPath + sequenceId + "_logo.jpg");
        }
        netBarInfoMapper.insert(info);

        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        ftpClient.enterLocalPassiveMode();
        try {
            if (null != logo) {
                String logoName = logo.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_logo.jpg", logo.getInputStream());

            }
            if (null != file1) {
                String file1OriginalFilename = file1.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_pic1.jpg", file1.getInputStream());
                NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(sequenceId);
                netbarPic.setOrderNum(1);
                netbarPic.setPic(netbarPicPath + sequenceId + "_pic1.jpg");
                netbarPicMapper.insert(netbarPic);
            }
            if (null != file2) {
                String file2OriginalFilename = file2.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_pic2.jpg", file2.getInputStream());
                NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(sequenceId);
                netbarPic.setOrderNum(2);
                netbarPic.setPic(netbarPicPath + sequenceId + "_pic2.jpg");
                netbarPicMapper.insert(netbarPic);
            }
            if (null != file3) {
                String file3OriginalFilename = file3.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_pic3.jpg", file3.getInputStream());
                NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(sequenceId);
                netbarPic.setOrderNum(3);
                netbarPic.setPic(netbarPicPath + sequenceId + "_pic3.jpg");
                netbarPicMapper.insert(netbarPic);
            }

            if (null != file4) {
                String file4OriginalFilename = file4.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_pic4.jpg", file4.getInputStream());
                NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(sequenceId);
                netbarPic.setOrderNum(4);
                netbarPic.setPic(netbarPicPath + sequenceId + "_pic4.jpg");
                netbarPicMapper.insert(netbarPic);
            }
            if (null != file5) {
                String file5OriginalFilename = file5.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + sequenceId + "_pic5.jpg", file5.getInputStream());
                NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(sequenceId);
                netbarPic.setOrderNum(5);
                netbarPic.setPic(netbarPicPath + sequenceId + "_pic5.jpg");
                netbarPicMapper.insert(netbarPic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultInfo.success("创建成功！");
    }

    private Boolean checkToken(String token) {
        String url=paasApi+"/cgp-paas-authentication/login/checkToken";
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        String doGet = HttpUtil.doGet(url, params);
        ResultInfo resultInfo = JSON.parseObject(doGet, ResultInfo.class);
        int code = resultInfo.getCode();
        if(code==200){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ResultInfo getNetBarList(HttpServletRequest request, String name, String province, String city, String district, String identification,Page buildPage) {
        String token = request.getHeader("Authorization");
//        ResultInfo res = iYdwAuthenticationService.getIdentification(token);
//        String identification = res.getMsg();
//        String identification = "91110108MA01QB042G";

        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("查询失败！");
        }


        Page<NetbarInfoVO> netBarList = netBarInfoMapper.getNetBarList(name, province, city, district,identification, buildPage);
        List<NetbarInfoVO> records = netBarList.getRecords();
        for (NetbarInfoVO v : records) {
            //count 数量
            String id = v.getId();
            QueryWrapper<ClusterNetbar> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("netbar_id",id);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(queryWrapper);
            if(null!=clusterNetbar){
                String clusterId = clusterNetbar.getClusterId();
                v.setClusterId(clusterId);
            }

//            CountVO deviceCount = netBarInfoMapper.getDeviceCount(id);
//            v.setTotalCount(deviceCount.getTotalCount());
//            v.setAvailableCount(deviceCount.getAvailableCount());
//            v.setIdleCount(deviceCount.getIdleCount());

            QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
            wrapper.eq("valid", 1);
            wrapper.eq("netbar_id", id);
            List<NetbarPic> netbarPics = netbarPicMapper.selectList(wrapper);
//            int size = netbarPics.size();
            for(NetbarPic netbarPic :netbarPics){
                Integer orderNum = netbarPic.getOrderNum();
                if(orderNum==1){
                    v.setFirstPic(pics + netbarPic.getPic());
                }
                if(orderNum==2){
                    v.setSecondPic(pics + netbarPic.getPic());
                }
                if(orderNum==3){
                    v.setThirdPic(pics + netbarPic.getPic());
                }
                if(orderNum==4){
                    v.setFourthPic(pics + netbarPic.getPic());
                }
                if(orderNum==5){
                    v.setFifthPic(pics + netbarPic.getPic());
                }
            }

        }
        netBarList.setRecords(records);
        return ResultInfo.success(netBarList);
    }

    @Override
    public ResultInfo getNetBarInfo(HttpServletRequest request, String id) {
        String token = request.getHeader("Authorization");
        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("查询失败！");
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
        }
        return ResultInfo.success(v);
    }

    @Override
    public ResultInfo updateNetBar(HttpServletRequest request, NetBarInfo info, MultipartFile file1, MultipartFile file2, MultipartFile file3,
                                   MultipartFile file4, MultipartFile file5, MultipartFile logo) {
        String token = request.getHeader("Authorization");
        Boolean checkToken = checkToken(token);
        if(!checkToken){
            return ResultInfo.fail("编辑失败！");
        }

        String id = info.getId();
//        String  identification="91110108MA01QB042G";
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        ftpClient.enterLocalPassiveMode();

        try {
            if (null != logo) {
                String logoName = logo.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_logo.jpg", logo.getInputStream());
                info.setLogoPic(pics + netbarPicPath + id + "_logo.jpg");

            }
            if (null != file1) {
                String file1OriginalFilename = file1.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic1.jpg", file1.getInputStream());
                QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
                wrapper.eq("order_num",1);
                wrapper.eq("netbar_id",id);
                wrapper.eq("valid",1);
                NetbarPic Pic = netbarPicMapper.selectOne(wrapper);
                if(null==Pic){
                    NetbarPic netbarPic = new NetbarPic();
                netbarPic.setValid(true);
                netbarPic.setNetbarId(id);
                netbarPic.setOrderNum(1);
                netbarPic.setPic(netbarPicPath + id + "_pic1.jpg");
                netbarPicMapper.insert(netbarPic);
                }

            }
            if (null != file2) {
                String file2OriginalFilename = file2.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic2.jpg", file2.getInputStream());
                QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
                wrapper.eq("order_num",2);
                wrapper.eq("netbar_id",id);
                wrapper.eq("valid",1);
                NetbarPic Pic = netbarPicMapper.selectOne(wrapper);
                if(null==Pic){
                    NetbarPic netbarPic = new NetbarPic();
                    netbarPic.setValid(true);
                    netbarPic.setNetbarId(id);
                    netbarPic.setOrderNum(2);
                    netbarPic.setPic(netbarPicPath + id + "_pic2.jpg");
                    netbarPicMapper.insert(netbarPic);
                }

            }
            if (null != file3) {
                String file3OriginalFilename = file3.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic3.jpg", file3.getInputStream());
                QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
                wrapper.eq("order_num",3);
                wrapper.eq("netbar_id",id);
                wrapper.eq("valid",1);
                NetbarPic Pic = netbarPicMapper.selectOne(wrapper);
                if(null==Pic){
                    NetbarPic netbarPic = new NetbarPic();
                    netbarPic.setValid(true);
                    netbarPic.setNetbarId(id);
                    netbarPic.setOrderNum(3);
                    netbarPic.setPic(netbarPicPath + id + "_pic3.jpg");
                    netbarPicMapper.insert(netbarPic);
                }
            }

            if (null != file4) {
                String file4OriginalFilename = file4.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic4.jpg", file4.getInputStream());
                QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
                wrapper.eq("order_num",4);
                wrapper.eq("netbar_id",id);
                wrapper.eq("valid",1);
                NetbarPic Pic = netbarPicMapper.selectOne(wrapper);
                if(null==Pic){
                    NetbarPic netbarPic = new NetbarPic();
                    netbarPic.setValid(true);
                    netbarPic.setNetbarId(id);
                    netbarPic.setOrderNum(4);
                    netbarPic.setPic(netbarPicPath + id + "_pic4.jpg");
                    netbarPicMapper.insert(netbarPic);
                }
            }
            if (null != file5) {
                String file5OriginalFilename = file5.getOriginalFilename();
                FTPUtil.uploadFiles(ftpClient, netbarUploadPath + id + "_pic5.jpg", file5.getInputStream());
                QueryWrapper<NetbarPic> wrapper = new QueryWrapper<>();
                wrapper.eq("order_num",5);
                wrapper.eq("netbar_id",id);
                wrapper.eq("valid",1);
                NetbarPic Pic = netbarPicMapper.selectOne(wrapper);
                if(null==Pic){
                    NetbarPic netbarPic = new NetbarPic();
                    netbarPic.setValid(true);
                    netbarPic.setNetbarId(id);
                    netbarPic.setOrderNum(5);
                    netbarPic.setPic(netbarPicPath + id + "_pic5.jpg");
                    netbarPicMapper.insert(netbarPic);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        netBarInfoMapper.updateById(info);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo getAllNetBarList(HttpServletRequest request, Page buildPage) {
//        QueryWrapper<NetBarInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("valid",1);
        String token = request.getHeader("Authorization");
        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("查询失败！");
        }
        Page<NetbarListVO> allNetBarList = netBarInfoMapper.getAllNetBarList(buildPage);
        return ResultInfo.success(allNetBarList);
    }

    @Override
    public ResultInfo deleteNetbar(HttpServletRequest request,List<String> ids) {
        String token = request.getHeader("Authorization");
        Boolean checkToken = checkToken(token);
        if(!checkToken){
            return ResultInfo.fail("删除失败！");
        }
        for (String id : ids) {
            NetBarInfo netBarInfo = new NetBarInfo();
            netBarInfo.setId(id);
            netBarInfo.setValid(false);
            netBarInfoMapper.updateById(netBarInfo);
            QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
            wrapper.eq("netbar_id", id);
            List<ClusterNetbar> clusterNetbars = clusterNetbarMapper.selectList(wrapper);
            if (clusterNetbars.size() > 0) {
                for (ClusterNetbar c : clusterNetbars) {
                    c.setClusterId(null);
                    clusterNetbarMapper.updateById(c);
                }
            }
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo getBaseStation(HttpServletRequest request, Page buildPage) {
        String token = request.getHeader("Authorization");
        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("查询失败！");
        }
        List<BaseInfo> baseStation = netBarInfoMapper.getBaseStation();
        return ResultInfo.success(baseStation);
    }

    @Override
    public ResultInfo getMatchStation(HttpServletRequest request, Page buildPage) {
        String token = request.getHeader("Authorization");
        logger.error(token+"token------------------");
        Boolean checkToken = checkToken(token);
        logger.error(checkToken+"-------------checkToken");
        if(!checkToken){
            return ResultInfo.fail("查询失败！");
        }
        List<BaseInfo> matchStation = netBarInfoMapper.getMatchStation();
        return ResultInfo.success(matchStation);
    }

    @Override
    public ResultInfo getEnterpriseIdentification(HttpServletRequest request, Page buildPage) {
        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
        String  identification=enterprise.getIdentification();
        return ResultInfo.success(identification);
    }

    @Override
    public ResultInfo getNetbarbyClusterId(HttpServletRequest request, List<String> clusterIds) {
        HashMap<String,String> map = new HashMap<>();
        for(String id:clusterIds){
            QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
            wrapper.eq("cluster_id",id);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper);
            if(null!=clusterNetbar){
                String netbarId = clusterNetbar.getNetbarId();
                NetBarInfo netBarInfo = netBarInfoMapper.selectById(netbarId);
                map.put(id,netBarInfo.getName());
            }

        }
        return ResultInfo.success(map);
    }

    @Override
    public ResultInfo getNetbarApps(String id) {
        List<NetbarAppVO> netbarApps = netbarAppMapper.getNetbarApps(id);
        return ResultInfo.success(netbarApps);
    }
}
