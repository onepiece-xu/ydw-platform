package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.config.ConfigProperties;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.*;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.IAppService;
import com.ydw.admin.service.ITagService;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.HttpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

import static com.ydw.admin.model.constant.UrlConstant.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {
    @Value("${url.paasApi}")
    private String paasApi;

    @Value("${url.pics}")
    private String pics;

//    @Value("${url.customizeDomain}")
//    private String customizeDomain;

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

    @Value("${config.back}")
    private String back;

    @Value("${passFtp.address}")
    private String addressPaas;

    @Value("${passFtp.port}")
    private int portPaas;

    @Value("${passFtp.userName}")
    private String userNamePaas;

    @Value("${passFtp.passWord}")
    private String passWordPaas;

    @Autowired
    private TagTypeMapper tagTypeMapper;
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private  AppPlatformMapper appPlatformMapper;

    @Autowired
    private YdwPaasTokenService ydwPaasTokenService;

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private GameGroupMapper tbGameGroupMapper;
    @Autowired
    private AppTagMapper appTagMapper;
    @Autowired
    private AppPicturesMapper appPicturesMapper;
    @Autowired
    private ConfigProperties configProperties;

    @Override
    public ResultInfo getAllApps() {
        List<App> list = list(new QueryWrapper<App>().orderByAsc("order_num"));
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getSyncApps(String body) {


        try {
//            syncFtpPics();
        } catch (Exception e) {
            System.out.println("同步FTP 图片 失败！");
            e.printStackTrace();
        }


//        String url = paasApi + URL_GetOwnerAppList;
        String url = paasApi + URL_GetSyncAppList;
//        String urlPic = paasApi + URL_GetAppPictures;

        Map<String, String> headers = new HashMap<>();
        String enterprisePaasToken = ydwPaasTokenService.getPaasToken();
        headers.put("Authorization", enterprisePaasToken);
        Map<String, Object> params = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String enterpriseId = object.getString("enterpriseId");

        params.put("enterpriseId", enterpriseId);

//        String doGetPics = HttpUtil.doGet(urlPic, headers, null);
        String doGet = HttpUtil.doJsonPost(url, headers, params);


//        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        JSONObject jsonObject = JSON.parseObject(doGet);
//        JSONObject picsObj = JSON.parseObject(doGetPics);
//        System.out.println(doGetPics + "Pic++++++++++++++++++++");
        System.out.println(doGet + "App______________");
        JSONArray records = jsonObject.getJSONArray("data");
//        JSONArray oldPics = picsObj.getJSONArray("data");
//        List<AppPictures> newPics = JSONArray.parseArray(oldPics.toJSONString(), AppPictures.class);


//        try {
//            syncFtpPics();
//        } catch (Exception e) {
//            System.out.println("同步FTP 图片 失败！");
//            e.printStackTrace();
//        }

//同步应用：
        //查到新 应用列表和 原应用列表
        List<SyncAppVO> newApps = JSONArray.parseArray(records.toJSONString(), SyncAppVO.class);

        int add =0;
        int del=0;
       //此时得到的是增量应用
        for (SyncAppVO  a: newApps ) {
            String action = a.getAction();
            String accessId = a.getAccessId();
            Date createTime = a.getCreateTime();
            String description = a.getDescription();
            Integer type = a.getType();
            String name = a.getName();
            String id = a.getId();
            Integer platform = a.getPlatform();
            Integer free = a.getFree();

            if(action.equals("add")){
                add++;
                App byId = appMapper.selectById(id);
                if(null!=byId){
                    App app = new App();
                    BeanUtils.copyProperties(a,app);
                    appMapper.updateById(app);
                }else{
                    App app = new App();
                    BeanUtils.copyProperties(a,app);
                    app.setAccessId(accessId);
                    app.setCreateTime(createTime);
                    app.setDescription(description);
                    app.setName(name);
                    app.setId(id);
                    app.setValid(false);
                    app.setType(type);
                    app.setPlatform(platform);
                    app.setFree(free);
                    appMapper.insert(app);

                    QueryWrapper<AppPictures> wrapper = new QueryWrapper<>();
                    wrapper.eq("app_id",id);
                    wrapper.eq("valid",1);
                    AppPictures appPictures = appPicturesMapper.selectOne(wrapper);
                    if(null!=appPictures){

                    }else {
                        AppPictures pictures = new AppPictures();
                        pictures.setValid(true);
                        pictures.setAppId(id);
                        pictures.setLogoPic(pics + "game/logo/"+id+".jpg");
                        pictures.setMidPic(pics + "game/middle/"+id+".jpg");
                        pictures.setSmallPic(pics + "game/small/"+id+".jpg");
                        pictures.setBigPic(pics + "game/large/"+id+".jpg");
                        appPicturesMapper.insert(pictures);
                    }


                }
            }
            if(action.equals("delete")){
                del++;
                //删除应用和游戏组关联表
                List<GameGroup> game_id = tbGameGroupMapper.selectList(new QueryWrapper<GameGroup>().eq("game_id", id));
                for (int i = 0; i < game_id.size(); i++) {
                    tbGameGroupMapper.deleteById(game_id.get(i));
                    System.out.println(game_id.get(i) + "----删除关联表应用组-----" + i);
                }

                //删标签应用关联表
                List<AppTag> app_id = appTagMapper.selectList(new QueryWrapper<AppTag>().eq("app_id", id));
                for (int i = 0; i < app_id.size(); i++) {
                    appTagMapper.deleteById(app_id.get(i));
                    System.out.println(app_id.get(i) + "---删除关联表标签------" + i);
                }
                //删除应用表
                appMapper.deleteById(id);
                System.out.println("删除应用表-------------" + id);

                //删除图片
                QueryWrapper<AppPictures> wrapper = new QueryWrapper<>();
                wrapper.eq("app_id",id);
                appPicturesMapper.delete(wrapper);
            }

        }
        //删除同步的数据
        String urlDel = paasApi + URL_SyncAppListDelete;
        headers.put("Authorization", enterprisePaasToken);

        params.put("enterpriseId", enterpriseId);
        String doGetSync = HttpUtil.doJsonPost(urlDel, headers, params);







//        QueryWrapper<App> wrapper = new QueryWrapper<>();
//        List<App> oldAppList = appMapper.selectList(wrapper);
//
//        //取ID对比
//        List<String> newAppIds = new ArrayList<>();
//        List<String> oldIds = new ArrayList<>();
//
//
//        for (App newApp : newApps) {
//            newAppIds.add(newApp.getId());
//        }
//        for (App oldApp : oldAppList) {
//            oldIds.add(oldApp.getId());
//        }
//        //找到需要增加的应用 id和 需要删除的应用id
//        Set set1 = new HashSet(newAppIds);
//        Set set2 = new HashSet(oldIds);
//        set1.removeAll(set2);//set1 : 新增加的
//        Set set3 = new HashSet(newAppIds);
//        set2.removeAll(set3);//set2 : 需要删除掉的
//
//        List<String> add = new ArrayList<>(set1);
//
//        List<String> del = new ArrayList<>(set2);


//        //新加的应用插入应用表
//        for (String id : add) {
//            for (App newApp : newApps) {
//                if (id.equals(newApp.getId())) {
//                    appMapper.insert(newApp);
//                }
//            }
//        }
        // 需要删除的应用id
//        for (String id : del) {
//            //删除应用和游戏组关联表
//            List<GameGroup> game_id = tbGameGroupMapper.selectList(new QueryWrapper<GameGroup>().eq("game_id", id));
//            for (int i = 0; i < game_id.size(); i++) {
//                tbGameGroupMapper.deleteById(game_id.get(i));
//                System.out.println(game_id.get(i) + "----删除关联表应用组-----" + i);
//            }
//
//            //删标签应用关联表
//            List<AppTag> app_id = appTagMapper.selectList(new QueryWrapper<AppTag>().eq("app_id", id));
//            for (int i = 0; i < app_id.size(); i++) {
//                appTagMapper.deleteById(app_id.get(i));
//                System.out.println(app_id.get(i) + "---删除关联表标签------" + i);
//            }
//            //删除应用表
//            appMapper.deleteById(id);
//            System.out.println("删除应用表-------------" + id);
//
//        }
        // 更新处理相同的游戏id
//        newAppIds.retainAll(oldIds);
//        if (newAppIds.size() > 0) {
//            for (String id : newAppIds) {
//                for (App a : newApps) {
//                    if (id.equals(a.getId())) {
//                        appMapper.updateById(a);
//                    }
//                }
//            }

//        }
//        + addPic.size() + "个！删除应用图片" + delPic.size() + "个！"
        return ResultInfo.success("同步成功！共新增应用" + add + "个,删除应用" + del + "个！ " );

    }

    @Override
    public ResultInfo getApps(String search, String valid, Integer type, Page buildPage) {
//        Page<App> list = appMapper.selectPage(buildPage,new QueryWrapper<App>());

        Page<AppPictureVO> list = appMapper.getAppsAndPics(search,valid,type,buildPage);
        List<AppPictureVO> records = list.getRecords();

        for (AppPictureVO vo : records) {
            vo.setBigPic(vo.getBigPic());
            vo.setLogoPic(vo.getLogoPic());
            vo.setMidPic(vo.getMidPic());
            vo.setSmallPic(vo.getSmallPic());
        }
        list.setRecords(records);
        return ResultInfo.success(list);
    }

    /**
     * 获取最近玩过的历史游戏记录
     *
     * @return
     */
    @Override
    public ResultInfo getRecordPlayList(String userId) {
        String url = paasApi + "/cgp-paas-admin/v1/deviceUsed/getRecordPlayList";
        Map<String, String> headers = new HashMap<>();
        String enterprisePaasToken = ydwPaasTokenService.getPaasToken();
        headers.put("Authorization", enterprisePaasToken);
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);
//        JSONObject object = JSONObject.parseObject(body);
//        String enterpriseId = object.getString("enterpriseId");
//
//        params.put("enterpriseId",enterpriseId);
//
//        String doGet = HttpUtil.doJsonPost(url, headers, params);
//
//        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        String doGet = HttpUtil.doGet(url, headers, params);
//        JSONObject jsonObject = JSON.parseObject(doGet);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);

        String jsonString = JSON.toJSONString(info.getData());
        List<RecordListVO> data = JSONObject.parseArray(jsonString, RecordListVO.class);
        List<RecordListVO> datas = new ArrayList<>();

        for (RecordListVO vo : data) {
            String appId = vo.getAppId();
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            if (appPicDetails != null) {
                String bigPic = appPicDetails.getBigPic();
                String midPic = appPicDetails.getMidPic();
                String smallPic = appPicDetails.getSmallPic();
                String logoPic = appPicDetails.getLogoPic();
                Integer type = appPicDetails.getType();
                vo.setType(type);
                vo.setBigPic(pics + bigPic);
                vo.setMidPic(pics + midPic);
                vo.setSmallPic(pics + smallPic);
                vo.setLogoPic(pics + logoPic);
            }
            datas.add(vo);
        }
        return ResultInfo.success(datas);
    }

    @Override
    public ResultInfo getRecordPlayListAndroid(String userId) {
        String url = paasApi + URL_GetRecordPlayList;
        Map<String, String> headers = new HashMap<>();
        String enterprisePaasToken = ydwPaasTokenService.getPaasToken();
        headers.put("Authorization", enterprisePaasToken);
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);

        String doGet = HttpUtil.doGet(url, headers, params);
//        JSONObject jsonObject = JSON.parseObject(doGet);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);

        String jsonString = JSON.toJSONString(info.getData());
        List<RecordListVO> data = JSONObject.parseArray(jsonString, RecordListVO.class);
        List<RecordListVO> datas = new ArrayList<>();

        for (RecordListVO vo : data) {
            String appId = vo.getAppId();
            //获取app标签列表
            List<TagVO> appTagNameByAppId = appTagMapper.getAppTagNameByAppId(appId);
            //存标签list
            List<String> tags = new ArrayList<>();
            for (TagVO t : appTagNameByAppId) {
                String tagName = t.getTagName();
                tags.add(tagName);
            }
            vo.setTags(tags);
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            if (appPicDetails != null) {
                String bigPic = appPicDetails.getBigPic();
                String midPic = appPicDetails.getMidPic();
                String smallPic = appPicDetails.getSmallPic();
                String logoPic = appPicDetails.getLogoPic();
                Integer type = appPicDetails.getType();
                vo.setType(type);
                vo.setBigPic(bigPic);
                vo.setMidPic(midPic);
                vo.setSmallPic(smallPic);
                vo.setLogoPic(logoPic);
            }
            datas.add(vo);
        }
        return ResultInfo.success(datas);
    }

    @Override
    public ResultInfo getAppBind(String appId, Page buildPage) {

        Page<AppBindTagVO> data = appTagMapper.getAppBindTag(appId, buildPage);
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo getAppUnBind(String appId, Page buildPage) {
        Page<AppBindTagVO> data = appTagMapper.getAppUnBindTag(appId, buildPage);
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo bindTags(HttpServletRequest request, String body) {

        JSONObject object = JSONObject.parseObject(body);
        String type = object.getString("type");
        String appId = object.getString("appId");
        JSONArray tagIds = object.getJSONArray("tagIds");
        AppTag tbAppTag = new AppTag();
        if (type.equals("bind")) {
            for (Object id : tagIds) {
                AppTag tat = appTagMapper.getAppTag(Integer.valueOf(id.toString()), appId);
                if (null != tat) {
                    tat.setValid(true);
                    appTagMapper.updateById(tat);
                } else {
                    tbAppTag.setAppId(appId);
                    tbAppTag.setTagId(Integer.valueOf(id.toString()));
                    tbAppTag.setValid(true);
                    appTagMapper.insert(tbAppTag);
                }
            }
            return ResultInfo.success("应用绑定成功！");
        } else {
            for (Object id : tagIds) {
                AppTag tat = appTagMapper.getAppTag(Integer.valueOf(id.toString()), appId);
                if (null != tat) {
                    tat.setValid(false);
                    appTagMapper.updateById(tat);
                } else {
                    tbAppTag.setAppId(appId);
                    tbAppTag.setTagId(Integer.valueOf(id.toString()));
                    tbAppTag.setValid(false);
                    appTagMapper.insert(tbAppTag);
                }
            }

            return ResultInfo.success("应用解绑成功！");
        }
    }

    @Override
    public ResultInfo appPictureUpload(HttpServletRequest request, String appId) {
        String type = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile logoPic = multipartRequest.getFile("logoPic");
        MultipartFile middlePic = multipartRequest.getFile("midPic");
        MultipartFile smallPic = multipartRequest.getFile("smallPic");
        MultipartFile largePic = multipartRequest.getFile("bigPic");
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        String uploadPath = configProperties.getUploadPath();
        if (logoPic != null) {
            type = "logoPic";
            try {
//                logoPic.transferTo(file)
                FTPUtil.uploadFiles(ftpClient, uploadPath + "logo/" + appId + ".jpg", logoPic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (smallPic != null) {
            try {
                type = "smallPic";
                FTPUtil.uploadFiles(ftpClient, uploadPath + "small/" + appId + ".jpg", smallPic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (middlePic != null) {
            try {
                type = "middlePic";
                FTPUtil.uploadFiles(ftpClient, uploadPath + "middle/" + appId + ".jpg", middlePic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (largePic != null) {
            try {
                type = "largePic";
                FTPUtil.uploadFiles(ftpClient, uploadPath + "large/" + appId + ".jpg", largePic.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        QueryWrapper<AppPictures> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", appId);
        queryWrapper.eq("valid", 1);
        List<AppPictures> appPictures = appPicturesMapper.selectList(queryWrapper);
        if (appPictures.size() == 0) {
            AppPictures ap = new AppPictures();
            ap.setAppId(appId);
            ap.setMidPic(pics + "game/middle/" + appId + ".jpg");
            ap.setBigPic(pics + "game/large/" + appId + ".jpg");
            ap.setLogoPic(pics + "game/logo/" + appId + ".jpg");
            ap.setSmallPic(pics + "game/small/" + appId + ".jpg");
            ap.setValid(true);
            appPicturesMapper.insert(ap);
        }
        Integer id = appPictures.get(0).getId();
        AppPictures appPic = new AppPictures();
        appPic.setId(id);
        appPic.setValid(true);
        if (type.equals("logoPic")) {
            appPic.setLogoPic(pics + "game/logo/" + appId + ".jpg");
        }
        if (type.equals("middle")) {
            appPic.setMidPic(pics + "game/middle/" + appId + ".jpg");
        }
        if (type.equals("small")) {
            appPic.setSmallPic(pics + "game/small/" + appId + ".jpg");
        }
        if (type.equals("large")) {
            appPic.setBigPic(pics + "game/large/" + appId + ".jpg");
        }
        appPicturesMapper.updateById(appPic);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo enableApps(EnableApps vo) {
        Integer type = vo.getType();
        List<String> ids = vo.getIds();
        for(String id:ids ){
            App app = appMapper.selectById(id);
            if(type==1){
                app.setValid(true);
                appMapper.updateById(app);
            }else{
                app.setValid(false);
                appMapper.updateById(app);
            }
        }

        return ResultInfo.success("操作成功!");

    }

    @Override
    public ResultInfo checkTags(HttpServletRequest request,String appId) {

        List<Integer> bindTagId = tagMapper.getBindTagId(appId);


        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        List<Tag> tags = tagMapper.selectList(wrapper);
        ArrayList<Tag> ls = new ArrayList<>();
        for ( Tag  t:tags){
            Integer id = t.getId();
            t.setValid(false);
            for (Integer i :bindTagId){
                if(i==id){
                    t.setValid(true);
                }
            }
            ls.add(t);
        }

        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo Tagging(HttpServletRequest request, CheckTagsVO vo) {
        String appId = vo.getAppId();
        List<Integer> ids = vo.getIds();
        //根据应用id找到标签全部解绑
//        AppTag tbAppTag = new AppTag();

            List<AppTag> at = appTagMapper.getAppTagByAppId(appId);
            for(AppTag t:at){
                Integer id = t.getId();
                appTagMapper.deleteById(id);
//                t.setValid(false);
//                appTagMapper.updateById(t);
            }

            AppTag tbAppTag = new AppTag();
        //绑定标签
        for (Integer id : ids) {
            AppTag tat = appTagMapper.getAppTag(id, appId);
            if (null != tat) {
                tat.setValid(true);
                appTagMapper.updateById(tat);
            } else {
                tbAppTag.setAppId(appId);
                tbAppTag.setTagId(Integer.valueOf(id.toString()));
                tbAppTag.setValid(true);
                appTagMapper.insert(tbAppTag);
            }
        }


        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateApp(App app) {
        appMapper.updateById(app);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo getPlatformList() {
        QueryWrapper<AppPlatform> wrapper = new QueryWrapper<>();
        List<AppPlatform> appPlatforms = appPlatformMapper.selectList(wrapper);
        return ResultInfo.success(appPlatforms);
    }

    /**
     * 从paas ftp 服务器 上更新图片到saas ftp 服务器
     */
    public String syncFtpPics() {
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);

        FTPClient ftpClientPaas = FTPUtil.connectFtpServer(addressPaas, portPaas, userNamePaas, passWordPaas);

        ftpClient.setBufferSize(1024 * 1024);
        ftpClientPaas.setBufferSize(1024 * 1024);
//        ftpClientPaas.enterLocalPassiveMode();


        try {

            ftpClientPaas.changeWorkingDirectory(uploadPath + "small/");
            ftpClient.changeWorkingDirectory(back + "small/");
            ftpClientPaas.enterLocalPassiveMode();
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClientPaas.listFiles();
            FTPFile[] saasfiles = ftpClient.listFiles();
            if (saasfiles.length > 0) {

                for (FTPFile file : files) {
                    String name = file.getName();
                    boolean canUpload = true;
                    for (FTPFile saasfile : saasfiles) {
                        String saasname = saasfile.getName();
                        if (name.equalsIgnoreCase(saasname)) {
                            System.out.println(name + "文件已存在！");
                            canUpload = false;
                            break;
                        }
                    }
                    if (canUpload) {
                        File downloadFile = new File(name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }
                }
            } else {
                for (FTPFile file : files) {
                    String name = file.getName();
                    File downloadFile = new File(name);
                    if (file.isFile()) {
                        System.out.println(name);
//                    FTPUtil.downloadSingleFile(ftpClientPaas, back + "logo/", uploadPath + "logo/" + name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }

                }

            }
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            ftpClientPaas.changeWorkingDirectory(uploadPath + "logo/");
            ftpClient.changeWorkingDirectory(back + "logo/");
            ftpClientPaas.enterLocalPassiveMode();
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClientPaas.listFiles();
            FTPFile[] saasfiles = ftpClient.listFiles();
            if (saasfiles.length > 0) {

                for (FTPFile file : files) {
                    String name = file.getName();
                    boolean canUpload = true;
                    for (FTPFile saasfile : saasfiles) {
                        String saasname = saasfile.getName();
                        if (name.equalsIgnoreCase(saasname)) {
                            System.out.println(name + "文件已存在！");
                            canUpload = false;
                            break;
                        }
                    }
                    if (canUpload) {
                        File downloadFile = new File(name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }
                }
            } else {
                for (FTPFile file : files) {
                    String name = file.getName();

                    File downloadFile = new File(name);
                    if (file.isFile()) {
                        System.out.println(name);
//                    FTPUtil.downloadSingleFile(ftpClientPaas, back + "logo/", uploadPath + "logo/" + name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }

                }

            }
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            ftpClientPaas.changeWorkingDirectory(uploadPath + "middle/");
            ftpClient.changeWorkingDirectory(back + "middle/");
            ftpClientPaas.enterLocalPassiveMode();
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClientPaas.listFiles();
            FTPFile[] saasfiles = ftpClient.listFiles();
            if (saasfiles.length > 0) {

                for (FTPFile file : files) {
                    String name = file.getName();
                    boolean canUpload = true;
                    for (FTPFile saasfile : saasfiles) {
                        String saasname = saasfile.getName();
                        if (name.equalsIgnoreCase(saasname)) {
                            System.out.println(name + "文件已存在！");
                            canUpload = false;
                            break;
                        }
                    }
                    if (canUpload) {
                        File downloadFile = new File(name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }
                }
            } else {
                for (FTPFile file : files) {
                    String name = file.getName();

                    File downloadFile = new File(name);
                    if (file.isFile()) {
                        System.out.println(name);
//                    FTPUtil.downloadSingleFile(ftpClientPaas, back + "logo/", uploadPath + "logo/" + name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }

                }

            }
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            ftpClientPaas.changeWorkingDirectory(uploadPath + "large/");
            ftpClient.changeWorkingDirectory(back + "large/");
            ftpClientPaas.enterLocalPassiveMode();
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClientPaas.listFiles();
            FTPFile[] saasfiles = ftpClient.listFiles();
            if (saasfiles.length > 0) {

                for (FTPFile file : files) {
                    String name = file.getName();
                    boolean canUpload = true;
                    for (FTPFile saasfile : saasfiles) {
                        String saasname = saasfile.getName();
                        if (name.equalsIgnoreCase(saasname)) {
                            System.out.println(name + "文件已存在！");
                            canUpload = false;
                            break;
                        }
                    }
                    if (canUpload) {
                        File downloadFile = new File(name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }
                }
            } else {
                for (FTPFile file : files) {
                    String name = file.getName();

                    File downloadFile = new File(name);
                    if (file.isFile()) {
                        System.out.println(name);
//                    FTPUtil.downloadSingleFile(ftpClientPaas, back + "logo/", uploadPath + "logo/" + name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }

                }

            }
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            ftpClientPaas.changeWorkingDirectory(uploadPath + "banner/");
            ftpClient.changeWorkingDirectory(back + "banner/");
            ftpClientPaas.enterLocalPassiveMode();
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClientPaas.listFiles();
            FTPFile[] saasfiles = ftpClient.listFiles();
            if (saasfiles.length > 0) {

                for (FTPFile file : files) {
                    String name = file.getName();
                    boolean canUpload = true;
                    for (FTPFile saasfile : saasfiles) {
                        String saasname = saasfile.getName();
                        if (name.equalsIgnoreCase(saasname)) {
                            System.out.println(name + "文件已存在！");
                            canUpload = false;
                            break;
                        }
                    }
                    if (canUpload) {
                        File downloadFile = new File(name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }
                }
            } else {
                for (FTPFile file : files) {
                    String name = file.getName();

                    File downloadFile = new File(name);
                    if (file.isFile()) {
                        System.out.println(name);
//                    FTPUtil.downloadSingleFile(ftpClientPaas, back + "logo/", uploadPath + "logo/" + name);
                        OutputStream is = new FileOutputStream(downloadFile);
                        FTPUtil.downloadSingleFile(ftpClientPaas, name, is);
                        InputStream in = new FileInputStream(downloadFile);
                        FTPUtil.uploadFiles(ftpClient, name, in);
                    }

                }

            }
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClient.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
            ftpClientPaas.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }


}
