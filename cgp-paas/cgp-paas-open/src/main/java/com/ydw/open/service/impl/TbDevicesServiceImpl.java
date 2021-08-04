package com.ydw.open.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.dao.ClusterNetbarMapper;
import com.ydw.open.dao.TbClustersMapper;
import com.ydw.open.dao.TbDevicesMapper;
import com.ydw.open.dao.TbUserAppsMapper;
import com.ydw.open.model.db.*;
import com.ydw.open.model.vo.AppDevicesVO;
import com.ydw.open.model.vo.DeviceVO;
import com.ydw.open.model.vo.NetBarDeviceVO;
import com.ydw.open.service.ITbDevicesService;
import com.ydw.open.service.ITbUserInfoService;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.ExcelUtil;
import com.ydw.open.utils.ResultInfo;
import com.ydw.open.utils.SequenceGenerator;
import com.ydw.open.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
@Service
public class TbDevicesServiceImpl extends ServiceImpl<TbDevicesMapper, TbDevices> implements ITbDevicesService {

    @Autowired
    private TbDevicesMapper tbDevicesMapper;
    @Autowired
    private TbClustersMapper tbClustersMapper;

    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;
    
    @Autowired
    private ITbUserInfoService iTbUserInfoService;
    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createDevice(HttpServletRequest request, TbDevices device) {
        device.setId(SequenceGenerator.sequence());
        device.setCreateTime(new Date());
      //设备新建是禁用
        device.setSchStatus(false);
        device.setValid(true);
        device.setStatus(1);
        tbDevicesMapper.insert(device);

        //node num +1
        String clusterId = device.getClusterId();
        TbClusters tbClusters = tbClustersMapper.selectById(clusterId);
        tbClusters.setDeviceNum(tbClusters.getDeviceNum()+1);
        tbClustersMapper.updateById(tbClusters);

        logger.info(device.getName()+"设备创建成功！");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateDevice(HttpServletRequest request, TbDevices device) {
        device.setUpdateTime(new Date());
        tbDevicesMapper.updateById(device);
        logger.info(device.getName()+"设备编辑成功！");
        return ResultInfo.success("Update success");
    }

    @Override
    public ResultInfo deleteDevices(HttpServletRequest request, List<String> ids) {
        TbDevices tbDevices = new TbDevices();
        for (String id : ids) {
            TbDevices td = tbDevicesMapper.selectById(id);
            String clusterId = td.getClusterId();
            TbClusters tbClusters = tbClustersMapper.selectById(clusterId);
            tbClusters.setDeviceNum(tbClusters.getDeviceNum()-1);
            tbDevices.setId(id);
            //软删除
            tbDevices.setValid(false);
            tbDevices.setUpdateTime(new Date());
            tbDevicesMapper.updateById(tbDevices);
            tbClustersMapper.updateById(tbClusters);
            logger.info(tbDevices.getName()+"设备删除成功！");
        }
        return ResultInfo.success("Delete success.");
    }

    @Override
    public ResultInfo getDevices(HttpServletRequest request, String id,String name, Integer baseType, String clusterId,Integer groupId,String idc
            , String cabinet, String location, String slot, String managerIp, Integer managerPort
            , String innerIp, Integer innerPort, String ip, Integer port, String status,String schStatus,String search, Page buildPage) {

        Page<DeviceVO> vo = tbDevicesMapper.getDevicesVoByClusterId(id,name, baseType, clusterId,groupId, idc,cabinet, location, slot
                , managerIp, managerPort, innerIp, innerPort, ip, port, status,schStatus,search,buildPage);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getDevice(HttpServletRequest request, String id) {

        if(StringUtil.nullOrEmpty(id)){
            return  ResultInfo.fail("Id is null");
        }
        DeviceVO td = tbDevicesMapper.selectByDeviceId(id);
        List<String> relatedGroupName = tbDevicesMapper.getRelatedGroupName(id);
        if(relatedGroupName.size()==0){
            td.setGroupName(null);
            return  ResultInfo.success(td);
        }
        if(relatedGroupName.size()==1){
            td.setGroupName(relatedGroupName.get(0));
        }else {
            String name = "";
            for (String groupName : relatedGroupName) {
                name += groupName + ",";
            }
            String substringMame = name.substring(0, name.length() - 1);
            td.setGroupName(substringMame);
        }
        return  ResultInfo.success(td);
    }

    @Override
    public Integer insertDb(TbDevices device) {
        return   tbDevicesMapper.insert(device);

    }

    @Override
    public ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("file");
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            TbDevices vo = new TbDevices();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了




            vo.setId(SequenceGenerator.sequence());     // 表格的第一列   注意数据格式需要对应实体类属性
            vo.setName(String.valueOf(lo.get(0)));
            vo.setBaseId(Integer.valueOf(String.valueOf(lo.get(1))));   // 表格的第3列
            vo.setClusterId(String.valueOf(lo.get(2)));
            vo.setIdc(String.valueOf(lo.get(3)));
            vo.setCabinet(String.valueOf(lo.get(4)));
            vo.setLocation(String.valueOf(lo.get(5)));
            vo.setSlot(String.valueOf(lo.get(6)));

            vo.setManagerIp(String.valueOf(lo.get(7)));
            vo.setManagerPort(Integer.valueOf(String.valueOf(lo.get(8))));
            vo.setInnerIp(String.valueOf(lo.get(9)));
            vo.setInnerPort(Integer.valueOf(String.valueOf(lo.get(10))));
            vo.setIp(String.valueOf(lo.get(11)));
            vo.setPort(Integer.valueOf(String.valueOf(lo.get(12))));
            vo.setInnerMac(String.valueOf(lo.get(13)));
            vo.setCreateTime(new Date());
            vo.setValid(true);
            vo.setSchStatus(false);
            vo.setStatus(1);

            //num +1
            TbClusters tbClusters = tbClustersMapper.selectById(String.valueOf(lo.get(2)));
            tbClusters.setDeviceNum(tbClusters.getDeviceNum()+1);
            tbClustersMapper.updateById(tbClusters);
            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
            //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
//            if (lo.get(2) != null && lo.get(2) != "") {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                vo.setRegTime(sdf.parse(String.valueOf(lo.get(2))));
//            }else {
////                vo.setRegTime(new Date());
//            }
            System.out.println("从excel中读取的实体类对象："+ vo);
            tbDevicesMapper.insert(vo);
			/*if(j == null)
			{
		            tbDevicesMapper.insert(vo);
			}
			else
			{
		            tbDevicesMapper.updateById(vo);
			}*/
        }
        System.out.println("文件导入成功！");
        return ResultInfo.success("文件导入成功！");
   }

    @Override
    public ResultInfo getDeviceBases(HttpServletRequest request) {


        List<TbDeviceBase> tb= tbDevicesMapper.getDeviceBases();
        return ResultInfo.success(tb);

    }

    @Override
    public ResultInfo getInstallDevicesByAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        TbUserApps tbUserApps = tbUserAppsMapper.selectById(appId);
        Integer type = tbUserApps.getType();
        Page<AppDevicesVO> vo = tbDevicesMapper.getInstallDevicesByAppId(appId,clusterId,groupId,type,buildPage);
//        PageInfo<AppDevicesVO> pageInfo = new PageInfo<AppDevicesVO>(vo);
        return ResultInfo.success(vo);

    }

    @Override
    public ResultInfo getUninstallDevicesByAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<AppDevicesVO> vo = tbDevicesMapper.getUninstallDevicesByAppId(appId,clusterId,groupId,buildPage);
//        PageInfo<AppDevicesVO> pageInfo = new PageInfo<AppDevicesVO>(vo);
        return ResultInfo.success(vo);
    }

     @Override
    public ResultInfo getDevicesList(HttpServletRequest request, String id, String name, String innerMac, String clusterName, String idc, String cabinet, String location, String slot, String groupName,
                                     String managerIp, Integer managerPort, String innerIp, Integer innerPort, String ip, Integer port, String status, String description,
                                     Integer schStatus,Integer baseType,String search,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
         String token = request.getHeader("Authorization");
         ResultInfo identificationInfo = iYdwAuthenticationService.getIdentification(token);
         String identification = identificationInfo.getMsg();
        Page<DeviceVO> vo = tbDevicesMapper.getDevicesList(id,name,innerMac,clusterName,idc,cabinet,location,slot,groupName, managerIp,managerPort,innerIp,innerPort,ip,port,
                description,schStatus,status,baseType,search,identification,buildPage);
//        PageInfo<DeviceVO> pageInfo = new PageInfo<DeviceVO>(vo);
        return ResultInfo.success(vo);
////        return null;
//         return ResultInfo.success();
    }

    @Override
    public ResultInfo getDevicesByInstallAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<AppDevicesVO> vo = tbDevicesMapper.getDevicesByInstallAppId(appId,clusterId,groupId,buildPage);
//        PageInfo<AppDevicesVO> pageInfo = new PageInfo<AppDevicesVO>(vo);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getAddDevices(HttpServletRequest request, String clusterId, Integer groupId,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<DeviceVO> vo = tbDevicesMapper.getAddDevices(clusterId,groupId,buildPage);
//        PageInfo<DeviceVO> pageInfo = new PageInfo<>(vo);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getGroupDevices(HttpServletRequest request, String clusterId, Integer groupId, String id,String name, String innerMac,Integer baseType, String clusterName,
                                      String idc, String cabinet, String location, String slot, String managerIp, Integer managerPort, String innerIp, Integer innerPort,
                                      String ip, Integer port, String status, String description, Integer schStatus, String search,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<DeviceVO> devicesVoByGroupId = tbDevicesMapper.getDevicesVoByGroupId(clusterId, groupId,id,name, innerMac,baseType,clusterName ,idc,cabinet, location, slot
                , managerIp, managerPort, innerIp, innerPort, ip, port, status,description,schStatus,search,buildPage);
//        PageInfo<DeviceVO> pageInfo = new PageInfo<DeviceVO>(devicesVoByGroupId);
        return ResultInfo.success(devicesVoByGroupId);
    }

    @Override
    public ResultInfo getNetBarDevices(String clusterId, String name, Page buildPage) {

        /* QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
        wrapper.eq("netbar_id",netbarId);
        ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper);
        String clusterId = clusterNetbar.getClusterId();
        if(!StringUtil.nullOrEmpty(clusterId)){
            Page<NetBarDeviceVO> netBarDevices = tbDevicesMapper.getNetBarDevices(netbarId, buildPage);
            return ResultInfo.success(netBarDevices);
        }else{
            return ResultInfo.success();
        }*/

        Page<NetBarDeviceVO> netBarDevices = tbDevicesMapper.getNetBarDevices(clusterId,buildPage);
        List<NetBarDeviceVO> records = netBarDevices.getRecords();
        for(NetBarDeviceVO v:records){
            v.setNetbarName(name);
        }
        return ResultInfo.success(netBarDevices);
    }


}
