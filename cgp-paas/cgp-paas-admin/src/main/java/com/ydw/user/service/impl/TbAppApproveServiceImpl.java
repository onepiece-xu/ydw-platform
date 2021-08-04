package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.ydw.user.dao.TbAppApproveMapper;
import com.ydw.user.dao.TbAppStrategyMapper;
import com.ydw.user.dao.TbUserAppsMapper;
import com.ydw.user.model.db.TbAppApprove;

import com.ydw.user.model.db.TbGameType;
import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.service.ITbAppApproveService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
@Service
public class TbAppApproveServiceImpl extends ServiceImpl<TbAppApproveMapper, TbAppApprove> implements ITbAppApproveService {

    @Autowired
    private TbAppApproveMapper tbAppApproveMapper;

    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;


    @Autowired
    private TbAppStrategyMapper tbAppStrategyMapper;


    @Override
    public ResultInfo getList(HttpServletRequest request,Integer status,Integer cooperationType,String search,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TbAppApprove> list = tbAppApproveMapper.getList(status,cooperationType,search,buildPage);
//        PageInfo<TbAppApprove> pageInfo = new PageInfo<>(list);

        return ResultInfo.success(list);
    }

//    @Override
//    public ResultInfo deleteApproves(HttpServletRequest request, List<String> ids) {
//        String result="";
//        TbAppApprove tbAppApprove = new TbAppApprove();
//        for (String id: ids) {
//            TbAppApprove taa = tbAppApproveMapper.selectById(id);
//            Integer status = taa.getStatus();
//            if(status!=2 &&status!=0){
//                result=result+taa.getName()+",";
////                ResultInfo.fail("删除失败，仅待提交和审批失败状态申请可被删除！");
//            }else {
//                tbAppApprove.setId(id);
//                tbAppApprove.setValid(false);
//                tbAppApproveMapper.updateById(tbAppApprove);
//            }
//        }
//        if(result!=""){
//            result=result+"删除失败，仅待提交和审批失败状态申请可被删除！";
//            return ResultInfo.fail(result);
//        }
//        return ResultInfo.success();
//    }


    @Override
    public ResultInfo getAppApprove(HttpServletRequest request, String id) {
        TbAppApprove appApprove = tbAppApproveMapper.getAppApprove(id);
        return ResultInfo.success(appApprove);
    }
//
//    @Override
//    public ResultInfo createUserApprove(HttpServletRequest request, MultipartFile accessIdPic, String name, String englishName, String description,
//                                        String gameMaker, String gamePublisher, String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName,
//                                        Integer size, Integer realSize, Integer type, String accountPath, String dataPath, String packagePath, Integer hasAccessId, String accessId, Integer isSave) {
////        //文件存放
////        String uploadDir = request.getSession().getServletContext().getRealPath("/") + "/upload/";
////        Map<String, String> map = new HashMap<>();
////        try {
////            File dir = new File(uploadDir);
////            //如果文件夹不存在，创建文件夹
////            if (!dir.exists()) {
////                dir.mkdirs();
////            }
////
////            //遍历文件上传
////            for (int i = 0; i < files.length; i++) {
////                if (files[i] != null) {
////                    String url = executeUplaod(request,uploadDir, files[i]);
////                    map.put("url", url);
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        TbAppApprove appApprove = new TbAppApprove();
//        appApprove.setId(SequenceGenerator.sequence());
//        if(isSave==1){
//            //1 提交保存
//            appApprove.setStatus(0);//待提交
//        }else {
//            //0 提交确定
//            appApprove.setStatus(1);//审核中
//        }
//        appApprove.setValid(true);
//
//        if(!StringUtil.nullOrEmpty(name)){
//            appApprove.setName(name);
//        }
//        if(!StringUtil.nullOrEmpty(englishName)){
//            appApprove.setEnglishName(englishName);
//        }
//        if(!StringUtil.nullOrEmpty(description)){
//            appApprove.setDescription(description);
//        }
//        if(!StringUtil.nullOrEmpty(gameMaker)){
//            appApprove.setGameMaker(gameMaker);
//        }
//        if(!StringUtil.nullOrEmpty(gamePublisher)){
//            appApprove.setGamePublisher(gamePublisher);
//        }
//        if(!StringUtil.nullOrEmpty(gameType)){
//            appApprove.setGameType(gameType);
//        }
//        if(null!=cooperationType){
//        appApprove.setCooperationType(cooperationType);
//        }
//        if(!StringUtil.nullOrEmpty(authPeriod)){
//            appApprove.setAuthPeriod(authPeriod);
//        }
//        if(!StringUtil.nullOrEmpty(packageFileName)){
//            appApprove.setPackageFileName(packageFileName);
//        }
//        if(!StringUtil.nullOrEmpty(packageName)){
//            appApprove.setPackageName(packageName);
//        }
//        if(null!=size){
//            appApprove.setSize(size);
//        }
//        if(null!=realSize){
//            appApprove.setRealSize(realSize);
//        }
//        if(null!=type){
//            appApprove.setType(type);
//        }
//        if(!StringUtil.nullOrEmpty(accountPath)){
//            appApprove.setAccountPath(accountPath);
//        }
//        if(!StringUtil.nullOrEmpty(dataPath)){
//            appApprove.setDataPath(dataPath);
//        }
//        if(!StringUtil.nullOrEmpty(packagePath)){
//            appApprove.setPackagePath(packagePath);
//        }
//        if(null!=hasAccessId){
//            appApprove.setHasAccessId(hasAccessId);
//        }
//        if(!StringUtil.nullOrEmpty(accessId)){
//            appApprove.setAccessId(accessId);
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        File targetFile=null;
//        String url="";//返回存储路径
//
//       if (null!=accessIdPic) {
//           String fileName = accessIdPic.getOriginalFilename();//获取文件名加后缀
//           upload(request, accessIdPic, appApprove, fileName);
//       }
//        appApprove.setCreateTime(new Date());
//        tbAppApproveMapper.insert(appApprove);
//        return ResultInfo.success();
//
//    }
//
//    @Override
//    public ResultInfo updateApproves(HttpServletRequest request, String id, MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher,
//                                     String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize,
//                                     Integer type, String accountPath, String dataPath, String packagePath, Integer hasAccessId, String accessId, Integer isSave) {
//        TbAppApprove appApprove = new TbAppApprove();
//        appApprove.setId(id);
//        appApprove.setCreateTime(new Date());
//        if(isSave==1){
//            //1 提交保存
//            appApprove.setStatus(0);//待提交
//        }else {
//            //0 提交确定
//            appApprove.setStatus(1);//审核中
//        }
//        if(!StringUtil.nullOrEmpty(name)){
//            appApprove.setName(name);
//        }
//        if(!StringUtil.nullOrEmpty(englishName)){
//            appApprove.setEnglishName(englishName);
//        }
//        if(!StringUtil.nullOrEmpty(description)){
//            appApprove.setDescription(description);
//        }
//        if(!StringUtil.nullOrEmpty(gameMaker)){
//            appApprove.setGameMaker(gameMaker);
//        }
//        if(!StringUtil.nullOrEmpty(gamePublisher)){
//            appApprove.setGamePublisher(gamePublisher);
//        }
//        if(!StringUtil.nullOrEmpty(gameType)){
//            appApprove.setGameType(gameType);
//        }else{
//            appApprove.setGameType(null);
//        }
//        if(null!=cooperationType){
//            appApprove.setCooperationType(cooperationType);
//        }
//        if(!StringUtil.nullOrEmpty(authPeriod)){
//            appApprove.setAuthPeriod(authPeriod);
//        }
//        if(!StringUtil.nullOrEmpty(packageFileName)){
//            appApprove.setPackageFileName(packageFileName);
//        }
//        if(!StringUtil.nullOrEmpty(packageName)){
//            appApprove.setPackageName(packageName);
//        }
//        if(null!=size){
//            appApprove.setSize(size);
//        }
//        if(null!=realSize){
//            appApprove.setRealSize(realSize);
//        }
//        if(null!=type){
//            appApprove.setType(type);
//        }
//        if(!StringUtil.nullOrEmpty(accountPath)){
//            appApprove.setAccountPath(accountPath);
//        }
//        if(!StringUtil.nullOrEmpty(dataPath)){
//            appApprove.setDataPath(dataPath);
//        }
//        if(!StringUtil.nullOrEmpty(packagePath)){
//            appApprove.setPackagePath(packagePath);
//        }
//
//        appApprove.setHasAccessId(hasAccessId);
//
//        if(!StringUtil.nullOrEmpty(accessId)){
//            appApprove.setAccessId(accessId);
//        }else{
//            appApprove.setAccessId(null);
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        File targetFile=null;
//        String url="";//返回存储路径
//
//        if (null!=accessIdPic) {
//            String fileName = accessIdPic.getOriginalFilename();//获取文件名加后缀
//            upload(request, accessIdPic, appApprove, fileName);
//        }else{
//            appApprove.setAccessIdPic(null);
//        }
//        appApprove.setCreateTime(new Date());
//        tbAppApproveMapper.updateSelective(appApprove);
//
//        return ResultInfo.success();
//    }

//    @Override
//    public ResultInfo revert(HttpServletRequest request, List<String> ids) {
//        TbAppApprove tbAppApprove = new TbAppApprove();
//        for (String id:ids) {
//            tbAppApprove.setId(id);
//            tbAppApprove.setStatus(0);//修改为待提交
//            tbAppApprove.setCreateTime(new Date());
//            tbAppApproveMapper.updateById(tbAppApprove);
//        }
//        return ResultInfo.success();
//    }

    @Override
    public ResultInfo approveApp(HttpServletRequest request, TbAppApprove appApprove) {
        Integer type = appApprove.getType();
        String id = appApprove.getId();
        String result = appApprove.getResult();
        TbAppApprove tbAppApprove = new TbAppApprove();
        tbAppApprove.setResult(result);
        tbAppApprove.setId(id);
        if (type == 0) {
            tbAppApprove.setStatus(2);//不通过
        } else {
            tbAppApprove.setStatus(3);//审核通过
        }
        tbAppApprove.setModifiedTime(new Date());
        tbAppApproveMapper.updateById(tbAppApprove);


        return ResultInfo.success();
    }




    @Override
    public ResultInfo getGameType(HttpServletRequest request) {
        List<TbGameType> ls= tbAppApproveMapper.getGameType();
        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo launchApps(HttpServletRequest request,TbUserApps tbUserApps) {
        String id = tbUserApps.getId();
        TbAppApprove tbAppApprove = tbAppApproveMapper.getAppApprove(id);
                tbUserApps.setUploadTime(new Date());
                tbUserApps.setStatus(2);//审批通过
                tbUserApps.setSchStatus(false);
                tbUserApps.setValid(true);
//                TbAppStrategy ts = tbAppStrategyMapper.getDefaultStrategy();
////                tbUserApps.setStrategyId(ts.getId());
                tbUserAppsMapper.insert(tbUserApps);

                tbAppApprove.setStatus(4);//已上架
                tbAppApproveMapper.updateSelective(tbAppApprove);

        return ResultInfo.success();
    }

    private void upload(HttpServletRequest request, MultipartFile accessIdPic, TbAppApprove appApprove, String fileName) {
        File targetFile;
        String url;
        if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/pic/";//存储路径
            String path=null;//configProperties.getUploadPath();//线上文件存储位置
//          String path = request.getSession().getServletContext().getRealPath("/"); //文件存储位置
//            String path="D:\\Pic\\";//本地文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀

//            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
            fileName=appApprove.getName()+fileF;//新的文件名以申请名称

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd+appApprove.getName());
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                accessIdPic.transferTo(targetFile);
//                url=returnUrl+fileAdd+"/"+fileName;
                url=path+fileName;
                appApprove.setAccessIdPic(url);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
