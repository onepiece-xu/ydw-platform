package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.*;
import com.ydw.user.model.db.*;
import com.ydw.user.model.vo.*;
import com.ydw.user.service.ITbUserAppsService;

import com.ydw.user.service.IYdwResourceService;
import com.ydw.user.utils.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-04-23
 */
@Service
public class TbUserAppsServiceImpl extends ServiceImpl<TbUserAppsMapper, TbUserApps> implements ITbUserAppsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.uploadPath}")
    private String uploadPath;

    @Value("${config.uploadBackPath}")
    private String uploadBackPath;
    @Value("${config.apkPath}")
    private String apkPath;

    @Value("${config.apkBackPath}")
    private String apkBackPath;
    @Value("${url.pics}")
    private String pics;

    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;

    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;

    @Autowired
    private TbAppStrategyMapper tbAppStrategyMapper;

    @Autowired
    private TbDeviceAppsMapper tbDeviceAppsMapper;
    @Autowired
    private TbAppTagMapper tbAppTagMapper;
    @Autowired
    private TbTagMapper tbTagMapper;
    @Autowired
    private TbTagTypeMapper tbTagTypeMapper;

    @Autowired
    private IYdwResourceService iYdwResourceService;

    @Autowired
    private AppPlatformMapper appPlatformMapper;


    @Override
    public ResultInfo createUserApp(HttpServletRequest request, TbUserApps app) {
        app.setId(SequenceGenerator.sequence());
        app.setUploadTime(new Date());
        app.setStatus(2);//默认审批通过
        app.setSchStatus(false);
        app.setValid(true);
        tbUserAppsMapper.insert(app);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateUserApp(HttpServletRequest request, TbUserApps app) {
        if(StringUtil.nullOrEmpty(app.getId())){
            return ResultInfo.fail("Id is null");
        }
        app.setApprovalTime(new Date());
        tbUserAppsMapper.updateById(app);

        return  ResultInfo.success("Update Success");
    }

    @Override
    public ResultInfo deleteUserApp(HttpServletRequest request, List<String> ids) {
        String result="";
        TbUserApps app = new TbUserApps();
        for (String id : ids) {

            //判断此应用是否在设备上
            List<TbDeviceApps> list=tbDeviceAppsMapper.getDevicesByAppId(id);
            if(list.size()>0){
                TbUserApps tbUserApps = tbUserAppsMapper.selectById(id);
                result=result+tbUserApps.getName()+",";
            }else{
                app.setId(id);
                //软删除
                app.setValid(false);
                app.setDeleteTime(new Date());
                tbUserAppsMapper.updateById(app);
            }
            if(result!="") {
                result = result + "存在设备未卸载 ,删除失败!";
                return ResultInfo.fail(result);
            }
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserApps(HttpServletRequest request, String name, String accessId, Integer type, String strategyName, String identification, String status,String search
            , String schStatus,Page   buildPage) {

        Page<AppVO> apps = tbUserAppsMapper.getUserAppsVo(name, accessId,type ,strategyName, identification,status,search,schStatus,buildPage);
        List<AppVO> records = apps.getRecords();
        for(AppVO vo:records){
            vo.setMidPic(pics+vo.getMidPic());
            vo.setLogoPic(pics+vo.getLogoPic());
            vo.setSmallPic(pics+vo.getSmallPic());
            vo.setBigPic(pics+vo.getBigPic());
        }
        apps.setRecords(records);
        return ResultInfo.success(apps);
    }

    @Override
    public ResultInfo getUserApp(HttpServletRequest request, String id) {
        AppVO appVO = new AppVO();
        TbUserApps tbUserApps = tbUserAppsMapper.selectById(id);
        BeanUtils.copyProperties(tbUserApps,appVO);

        TbUserInfo tbUserInfo = tbUserInfoMapper.getByIdentification(tbUserApps.getIdentification());
        TbAppStrategy tbAppStrategy = tbAppStrategyMapper.selectById(tbUserApps.getStrategyId());

        appVO.setEnterpriseName(tbUserInfo.getEnterpriseName());
        appVO.setStrategyName(tbAppStrategy.getName());
        return ResultInfo.success(appVO);
    }

    @Override
    public ResultInfo getInstallApps(HttpServletRequest request,String id,Page   buildPage) {

        Page<TbUserApps> installApps = tbUserAppsMapper.getInstallApps(id,buildPage);

        return ResultInfo.success(installApps);
    }

    @Override
    public ResultInfo getUnInstallApps(HttpServletRequest request,String id, Page   buildPage) {

        Page<TbUserApps> unInstallApps = tbUserAppsMapper.getUnInstallApps(id,buildPage);

        return ResultInfo.success(unInstallApps);
    }

    @Override
    public ResultInfo disableApps(HttpServletRequest request, List<String> ids) {
        TbUserApps app = new TbUserApps();
        for (String id : ids) {
            app.setId(id);
            //改禁用状态
            app.setSchStatus(false);
            tbUserAppsMapper.updateById(app);

        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo enableApps(HttpServletRequest request, List<String> ids) {
        TbUserApps app = new TbUserApps();
        for (String id : ids) {
            app.setId(id);
            //改启状态
            app.setSchStatus(true);
            tbUserAppsMapper.updateById(app);

        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) {
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
            TbUserApps vo = new TbUserApps();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
            vo.setId(SequenceGenerator.sequence());
            vo.setIdentification(String.valueOf(lo.get(0)));
            vo.setName(String.valueOf(lo.get(1))); // 表格的第一列
            vo.setPackageName(String.valueOf(lo.get(2)));// 表格的第2列
//            vo.setPackageFileName(String.valueOf(lo.get(3)));

            vo.setSize(Integer.valueOf(String.valueOf(lo.get(3))));
            vo.setRealSize(Integer.valueOf(String.valueOf(lo.get(4))));
            vo.setAccessId(String.valueOf(lo.get(5)));
            vo.setType(Integer.valueOf(String.valueOf(lo.get(6))));
            vo.setDescription(String.valueOf(lo.get(7)));
//            vo.setSavePath(String.valueOf(lo.get(10)));
//            vo.setAccountPath(String.valueOf(lo.get(11)));
//            vo.setStartShell(String.valueOf(lo.get(12)));
//            vo.setCloseShell(String.valueOf(lo.get(13)));
            vo.setStrategyId(Integer.valueOf(String.valueOf(lo.get(8))));

            vo.setStatus(2);//上传中
            vo.setUploadTime(new Date());
            vo.setSchStatus(false);
            vo.setValid(true);
//            vo.setUserTel(String.valueOf(lo.get(0)));     // 表格的第一列   注意数据格式需要对应实体类属性   String.valueOf(lo.get(1))
//            vo.setIntegral(Integer.valueOf(String.valueOf(lo.get(1))));   // 表格的第二列
            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
            //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
//            if (lo.get(2) != null && lo.get(2) != "") {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                vo.setRegTime(sdf.parse(String.valueOf(lo.get(2))));
//            }else {
////                vo.setRegTime(new Date());
//            }
            System.out.println("从excel中读取的实体类对象："+ vo);
            tbUserAppsMapper.insert(vo);

        }

        return  ResultInfo.success("文件导入成功！");
    }

    @Override
    public Integer insertDb(TbUserApps app) {
        return tbUserAppsMapper.insert(app);
    }

    @Override
    public ResultInfo getAppListByTag(HttpServletRequest request, String body) {
        JSONObject object = JSONObject.parseObject(body);
//        Object o = object.get("body");
        Integer pageNum = object.getIntValue("pageNum");
        Integer pageSize = object.getIntValue("pageSize");
        String  search = object.getString("search");
        List<Object> data =new ArrayList<>();
        JSONArray tagNames = object.getJSONArray("tagNames");
        List<Integer> tagIds = new ArrayList<>();
        List<String> apps = new ArrayList<>();
        if (null==tagNames) {
            //查全部游戏时
            List<AppTagVO> webApps = tbUserAppsMapper.getWebApps(search);
            for (AppTagVO app : webApps) {
                apps.add(app.getAppId());
            }
        }else{
            for (Object i:tagNames){
                Integer tagIdByName = tbTagMapper.getTagIdByName(i.toString());
                tagIds.add(tagIdByName);
            }
            apps = tbUserAppsMapper.getAppListByTag(tagIds, tagIds.size(),search);
            System.out.println(apps);
        }
        if(apps.size()>0){
            for (String id :apps) {
                TbUserApps w = tbUserAppsMapper.selectById(id);
                List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(id);
                HashMap<Object, Object> map = new HashMap<>();
                map.put("description",w.getDescription());
                map.put("appId",w.getId());
                map.put("appName",w.getName());
                map.put("type",w.getType());


//转换为我们熟悉的日期格式
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fffsd=sd.format(w.getUploadTime());
                map.put("uploadTime",fffsd);
                List<String> ls =new ArrayList<>();
                for (TagVO v:appTagNameByAppId) {
                    ls.add(v.getTagName());
                }
                map.put("list",ls);
                data.add(map);
            }
        }

        List<Object> currentPageList = new ArrayList<>();

        if (data != null && data.size() > 0) {

            int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);

            for (int i = 0; i < pageSize && i < data.size() - currIdx; i++) {

                currentPageList.add(data.get(currIdx + i));

            }

        }

        return ResultInfo.success(data.size()+"",currentPageList);
    }

    @Override
    public ResultInfo getAppPictures() {
        List<AppPictures> appPictures = tbUserAppsMapper.getAppPictures();

        return ResultInfo.success(appPictures);
    }

    @Override
    public ResultInfo appFileUpload(HttpServletRequest request, String appId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");

        String fileName = file.getOriginalFilename();
        logger.info(fileName+"---------获取上传的文件名");
        logger.info("");
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        AppInfo appInfo = tbUserAppsMapper.getUserAppInfo(appId, "");
        String packageFileName = appInfo.getPackageFileName();
        logger.info(packageFileName+"---------获取已安装包名");
        try {
            FTPUtil.uploadFiles(ftpClient, apkBackPath  + fileName, file.getInputStream());
            System.out.println(ftpClient.printWorkingDirectory());
            ftpClient.changeWorkingDirectory(apkPath);
            System.out.println(ftpClient.printWorkingDirectory());
            FTPFile[] files = ftpClient.listFiles();
            boolean flag=false;
            for (FTPFile f :files){
                String name = f.getName();
                if (name.equalsIgnoreCase(fileName)) {
                    flag=true;
                }
            }
            if(flag){
                if(StringUtil.nullOrEmpty(packageFileName))
                ftpClient.deleteFile(packageFileName);
                logger.info("---------删除文件成功——————");
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.changeToParentDirectory();

            System.out.println(ftpClient.printWorkingDirectory());

            //改变工作目录
            ftpClient.changeWorkingDirectory(apkBackPath);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean rename = ftpClient.rename(fileName, "/home/web/files/"+apkPath + fileName);
            logger.info("---------当前文件夹——————"+ftpClient.printWorkingDirectory());
            System.out.println(ftpClient.printWorkingDirectory());
            System.out.println(rename);
            logger.info("---------上传成功还是失败？——————"+rename);
            HashMap<String, String> map = new HashMap<>();
            map.put("appId",appId);
            map.put("relativeRemoteDirectory",apkPath);
            map.put("remoteFileName",fileName);
            map.put("absoluteLocalDirectory",appInfo.getPackageFilePath());
            map.put("localFileName",fileName);
            iYdwResourceService.issueApp(map);
            logger.info("---------分发边沿节点——————");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success("上传成功！");
    }

    @Override
    public ResultInfo getPlatformList() {
        QueryWrapper<AppPlatform> wrapper = new QueryWrapper<>();
        List<AppPlatform> appPlatforms = appPlatformMapper.selectList(wrapper);
        return ResultInfo.success(appPlatforms);
    }


}
