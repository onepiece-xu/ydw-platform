package com.ydw.open.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.config.ConfigProperties;
import com.ydw.open.dao.TbAppApproveMapper;
import com.ydw.open.dao.TbDeviceUsedMapper;
import com.ydw.open.model.db.TbAppApprove;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.model.db.TbClusters;
import com.ydw.open.model.db.TbGameType;
import com.ydw.open.model.vo.AppCountVO;
import com.ydw.open.service.ITbAppApproveService;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.ResultInfo;
import com.ydw.open.utils.SequenceGenerator;
import com.ydw.open.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ConfigProperties configProperties;


    @Autowired
    private TbDeviceUsedMapper tbDeviceUsedMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;


    @Override
    public ResultInfo getList(HttpServletRequest request, Integer status, Integer cooperationType, String search, Page buildPage) {
//        TbUserInfo userInfoFromRequest = loginService.getUserInfoFromRequest(request);
//        String identification = userInfoFromRequest.getIdentification();
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        if(identification.equals("administrator")){
            identification=null;
        }
        Page<TbAppApprove> list = tbAppApproveMapper.getList(identification,status,cooperationType,search,buildPage);
//        PageInfo<TbAppApprove> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo deleteApproves(HttpServletRequest request, List<String> ids) {
        String result="";
        TbAppApprove tbAppApprove = new TbAppApprove();
        for (String id: ids) {
            TbAppApprove taa = tbAppApproveMapper.selectById(id);
            Integer status = taa.getStatus();
            if(status!=2 &&status!=0){
                result=result+taa.getName()+",";
//                ResultInfo.fail("删除失败，仅待提交和审批失败状态申请可被删除！");
            }else {
                tbAppApprove.setId(id);
                tbAppApprove.setValid(false);
                tbAppApproveMapper.updateById(tbAppApprove);
            }
        }
        if(result!=""){
            result=result+"删除失败，仅待提交和审批失败状态申请可被删除！";
            return ResultInfo.fail(result);
        }
        return ResultInfo.success();
    }


    @Override
    public ResultInfo getAppApprove(HttpServletRequest request, String id) {
        TbAppApprove appApprove = tbAppApproveMapper.getAppApprove(id);
        return ResultInfo.success(appApprove);
    }



    @Override
    public ResultInfo createUserApprove(HttpServletRequest request, MultipartFile accessIdPic, String name, String englishName, String description,
                                        String gameMaker, String gamePublisher, String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName,
                                        Integer size, Integer realSize, Integer type, String accountPath, String dataPath, String packagePath, Integer hasAccessId, String accessId
                                        ,Integer schInstall,Integer installMaxNumber,String identification, Integer screen, Integer showTime,Integer hasPage,String region,Integer maxConnect,MultipartFile page,Integer isSave) {

        TbAppApprove appApprove = new TbAppApprove();
        appApprove.setId(SequenceGenerator.sequence());
        if(isSave==1){
            //1 提交保存
            appApprove.setStatus(0);//待提交
        }else {
            //0 提交确定
            appApprove.setStatus(1);//审核中
        }
        appApprove.setValid(true);
        //必填
        appApprove.setIdentification(identification);
        if(null!=schInstall){
            if(schInstall==1){
                //自动安装
                appApprove.setSchInstall(true);
            }else {
                //schInstall=2
                appApprove.setSchInstall(false);
            }
        }
        if(null!=installMaxNumber){
            appApprove.setInstallMaxNumber(installMaxNumber);
        }

        if(!StringUtil.nullOrEmpty(name)){
            appApprove.setName(name);
        }
        if(!StringUtil.nullOrEmpty(englishName)){
            appApprove.setEnglishName(englishName);
        }
        if(!StringUtil.nullOrEmpty(description)){
            appApprove.setDescription(description);
        }
        if(!StringUtil.nullOrEmpty(gameMaker)){
            appApprove.setGameMaker(gameMaker);
        }
        if(!StringUtil.nullOrEmpty(gamePublisher)){
            appApprove.setGamePublisher(gamePublisher);
        }
        if(!StringUtil.nullOrEmpty(gameType)){
            appApprove.setGameType(gameType);
        }
        if(null!=cooperationType){
        appApprove.setCooperationType(cooperationType);
        }
        if(!StringUtil.nullOrEmpty(authPeriod)){
            appApprove.setAuthPeriod(authPeriod);
        }
        if(!StringUtil.nullOrEmpty(packageFileName)){
            appApprove.setPackageFileName(packageFileName);
        }
        if(!StringUtil.nullOrEmpty(packageName)){
            appApprove.setPackageName(packageName);
        }
        if(null!=size){
            appApprove.setSize(size);
        }
        if(null!=screen){
            appApprove.setScreen(screen);
        }
        if(null!=realSize){
            appApprove.setRealSize(realSize);
        }
        if(null!=type){
            appApprove.setType(type);
        }
        if(!StringUtil.nullOrEmpty(accountPath)){
            appApprove.setAccountPath(accountPath);
        }
        if(!StringUtil.nullOrEmpty(dataPath)){
            appApprove.setDataPath(dataPath);
        }
        if(!StringUtil.nullOrEmpty(packagePath)){
            appApprove.setPackagePath(packagePath);
        }
        if(null!=hasAccessId){
            appApprove.setHasAccessId(hasAccessId);
        }
        if(!StringUtil.nullOrEmpty(accessId)){
            appApprove.setAccessId(accessId);
        }

        if(!StringUtil.nullOrEmpty(region)){
            appApprove.setRegion(region);
        }
        if(null!=hasPage){
            appApprove.setHasPage(hasPage);
        }
        if(null!=showTime){
            appApprove.setShowTime(showTime);
        }


        if (null!=accessIdPic) {
            String fileName = accessIdPic.getOriginalFilename();//获取文件名加后缀
            upload(request, accessIdPic, appApprove, fileName);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径
    
       if (null!=accessIdPic) {
           String fileName = accessIdPic.getOriginalFilename();//获取文件名加后缀
           upload(request, accessIdPic, appApprove, fileName);
       }
        if (null!=page) {
            String pageName = page.getOriginalFilename();
            uploadPage(request, page, appApprove, pageName);
        }


        appApprove.setCreateTime(new Date());
        tbAppApproveMapper.insert(appApprove);
        return ResultInfo.success();

    }

    @Override
    public ResultInfo updateApproves(HttpServletRequest request,String id, MultipartFile accessIdPic, String name, String englishName, String description, String gameMaker, String gamePublisher,
                                     String gameType, Integer cooperationType, String authPeriod, String packageFileName, String packageName, Integer size, Integer realSize,
                                     Integer type, String accountPath, String dataPath, String packagePath, Integer hasAccessId, String accessId,Integer schInstall,Integer installMaxNumber,String identification, Integer screen,
                                     Integer showTime,Integer hasPage,String region,Integer maxConnect,MultipartFile page,Integer isSave) {
        TbAppApprove appApprove = new TbAppApprove();
        appApprove.setId(id);
        appApprove.setCreateTime(new Date());
        if(isSave==1){
            //1 提交保存
            appApprove.setStatus(0);//待提交
        }else {
            //0 提交确定
            appApprove.setStatus(1);//审核中
        }
        appApprove.setIdentification(identification);
        if(null!=schInstall) {
            if (schInstall == 1) {
                appApprove.setSchInstall(true);
            } else {
                appApprove.setSchInstall(false);
            }
        }
        if(null!=installMaxNumber) {
            appApprove.setInstallMaxNumber(installMaxNumber);
        }
        if(!StringUtil.nullOrEmpty(name)){
            appApprove.setName(name);
        }
        if(!StringUtil.nullOrEmpty(englishName)){
            appApprove.setEnglishName(englishName);
        }
        if(!StringUtil.nullOrEmpty(description)){
            appApprove.setDescription(description);
        }
        if(!StringUtil.nullOrEmpty(gameMaker)){
            appApprove.setGameMaker(gameMaker);
        }
        if(!StringUtil.nullOrEmpty(gamePublisher)){
            appApprove.setGamePublisher(gamePublisher);
        }
        if(!StringUtil.nullOrEmpty(gameType)){
            appApprove.setGameType(gameType);
        }else{
            appApprove.setGameType(null);
        }
        if(null!=cooperationType){
            appApprove.setCooperationType(cooperationType);
        }
        if(!StringUtil.nullOrEmpty(authPeriod)){
            appApprove.setAuthPeriod(authPeriod);
        }
        if(!StringUtil.nullOrEmpty(packageFileName)){
            appApprove.setPackageFileName(packageFileName);
        }
        if(!StringUtil.nullOrEmpty(packageName)){
            appApprove.setPackageName(packageName);
        }
        if(null!=size){
            appApprove.setSize(size);
        }
        if(null!=realSize){
            appApprove.setRealSize(realSize);
        }
        if(null!=type){
            appApprove.setType(type);
        }
        if(!StringUtil.nullOrEmpty(accountPath)){
            appApprove.setAccountPath(accountPath);
        }
        if(!StringUtil.nullOrEmpty(dataPath)){
            appApprove.setDataPath(dataPath);
        }
        if(!StringUtil.nullOrEmpty(packagePath)){
            appApprove.setPackagePath(packagePath);
        }
        if(null!=screen){
            appApprove.setScreen(screen);
        }
        appApprove.setHasAccessId(hasAccessId);

        if(!StringUtil.nullOrEmpty(accessId)){
            appApprove.setAccessId(accessId);
        }else{
            appApprove.setAccessId(null);
        }
        if(!StringUtil.nullOrEmpty(region)){
            appApprove.setRegion(region);
        }
        if(null!=hasPage){
            appApprove.setHasPage(hasPage);
        }
        if(null!=showTime){
            appApprove.setShowTime(showTime);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径

        if (null!=accessIdPic) {
            String fileName = accessIdPic.getOriginalFilename();//获取文件名加后缀
            upload(request, accessIdPic, appApprove, fileName);
        }else{
            appApprove.setAccessIdPic(null);
        }
        if (null!=page) {
            String fileName = page.getOriginalFilename();//获取文件名加后缀
            uploadPage(request, page, appApprove, fileName);
        }else{
            appApprove.setPage(null);
        }

        appApprove.setCreateTime(new Date());
        tbAppApproveMapper.updateSelective(appApprove);

        return ResultInfo.success();
    }

    @Override
    public ResultInfo revert(HttpServletRequest request, List<String> ids) {
        TbAppApprove tbAppApprove = new TbAppApprove();
        for (String id:ids) {
            tbAppApprove.setId(id);
            tbAppApprove.setStatus(0);//修改为待提交
            tbAppApprove.setCreateTime(new Date());
            tbAppApproveMapper.updateById(tbAppApprove);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getGameType(HttpServletRequest request) {
        List<TbGameType> ls= tbAppApproveMapper.getGameType();
        return ResultInfo.success(ls);
    }

    @Override
    public ResultInfo getAppsInUse(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        List<AppCountVO> ls = tbDeviceUsedMapper.getApps(identification);
        return ResultInfo.success(ls);
    }

    private void upload(HttpServletRequest request, MultipartFile accessIdPic, TbAppApprove appApprove, String fileName) {
        File targetFile;
        String url;
        if(fileName!=null&&fileName!=""){
           String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/pic/";//存储路径
          String path=configProperties.getUploadPath();//线上文件存储位置
//          String path = request.getSession().getServletContext().getRealPath("/"); //文件存储位置
//            String path="D:\\Pic\\";//本地文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();//文件后缀
//            String path="D:\\tempcode\\5.27\\";

            fileName="Access_Id"+appApprove.getId()+fileF;//新的文件名以申请名称

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd+appApprove.getId());
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
//                url=path+fileName;
                url="/pictures/"+fileAdd+appApprove.getId()+"/"+fileName;
                appApprove.setAccessIdPic(url);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


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
    public ResultInfo getClusterInfo() {
        List<TbClusters> clusterInfo = tbAppApproveMapper.getClusterInfo();
        return ResultInfo.success(clusterInfo);
    }

    /**
     * 上传素材
     * @param request
     * @param
     * @param appApprove
     * @param fileName
     */
    private void uploadPage(HttpServletRequest request, MultipartFile page, TbAppApprove appApprove, String fileName) {
        File targetFile;
        String url;
        if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/pic/";//存储路径
            String path=configProperties.getUploadPath();//线上文件存储位置
//          String path = request.getSession().getServletContext().getRealPath("/"); //文件存储位置
//            String path="D:\\Pic\\";//本地文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();//文件后缀
//            String path="D:\\tempcode\\5.27\\";

            fileName="material"+appApprove.getId()+fileF;//新的文件名+素材区分

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd+appApprove.getId());
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                page.transferTo(targetFile);
//                url=returnUrl+fileAdd+"/"+fileName;
//                url=path+fileName;
                url="/pictures/"+fileAdd+appApprove.getId()+"/"+fileName;
                appApprove.setPage(url);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
