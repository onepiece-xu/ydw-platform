package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.TbUserApproveMapper;
import com.ydw.user.dao.TbUserInfoMapper;
import com.ydw.user.model.db.TbUserApprove;
import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.service.ITbUserApproveService;
import com.ydw.user.utils.PasswordUtil;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
@Service
public class TbUserApproveServiceImpl extends ServiceImpl<TbUserApproveMapper, TbUserApprove> implements ITbUserApproveService {
    @Autowired
    private TbUserApproveMapper tbUserApproveMapper;
    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;

    @Override
    public ResultInfo approve(HttpServletRequest request, TbUserApprove approve) {
        String id = approve.getId();
        String result = approve.getResult();
        Integer type = approve.getType();
        TbUserApprove userApprove = new TbUserApprove();
        userApprove.setId(id);
        userApprove.setResult(result);
        if(type==1){
            userApprove.setSchStatus(1);//审批通过
        }else {
            userApprove.setSchStatus(2);//审批失败
        }
        userApprove.setModifiedTime(new Date());
        tbUserApproveMapper.updateById(userApprove);
        if(type==1){
            TbUserApprove tbUserApprove = tbUserApproveMapper.selectById(userApprove);

            //将用户审批通过录入到用户表
            TbUserInfo newUser = new TbUserInfo();
            newUser.setId(tbUserApprove.getId());
            newUser.setValid(true);
            newUser.setCreateTime(new Date());
            newUser.setLoginName(tbUserApprove.getLoginName());
            newUser.setSchStatus(true);//启用状态
            newUser.setType(2);//企业用户
            newUser.setPayAmount(new BigDecimal(0));//初始为0
            newUser.setAmount(0);//初始余额0
            newUser.setPassword(tbUserApprove.getPassword());
            //生成一个key
            String key = PasswordUtil.md5(SequenceGenerator.sequence()) + PasswordUtil.md5("yidianwan");
            newUser.setSecretKey(key);
            newUser.setIdentification(tbUserApprove.getIdentification());
            if (null != tbUserApprove.getEnterpriseName()) {
                newUser.setEnterpriseName(tbUserApprove.getEnterpriseName());
            }
            if (null != tbUserApprove.getMobileNumber()) {
                newUser.setMobileNumber(tbUserApprove.getMobileNumber());
            }
            if (null != tbUserApprove.getTelNumber()) {
                newUser.setTelNumber(tbUserApprove.getTelNumber());
            }
            if (null != tbUserApprove.getDescription()) {
                newUser.setDescription(tbUserApprove.getDescription());
            }
            if (null != tbUserApprove.getEmail()) {
                newUser.setEmail(tbUserApprove.getEmail());
            }
            tbUserInfoMapper.insertSelective(newUser);
        }


       return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserApproveList(HttpServletRequest request, String search, String schStatus,Page  buildPage, String enterpriseName) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }

        Page<TbUserApprove> deviceUsageRate = tbUserApproveMapper.getUserApproveList(search,schStatus,enterpriseName,buildPage);
//        PageInfo<TbUserApprove> pageInfo = new PageInfo<>(deviceUsageRate);
        return ResultInfo.success(deviceUsageRate);
    }

    @Override
    public ResultInfo getUserInfo(HttpServletRequest request, String id) {

        TbUserApprove userInfo = tbUserApproveMapper.getUserInfo(id);

        return  ResultInfo.success(userInfo);
    }

    @Override
    public ResultInfo updateUserApprove(HttpServletRequest request,HttpServletResponse response,MultipartFile file, TbUserApprove user) {

        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
//        if(null==file){
//            return ResultInfo.fail("请上传相关文件");
//        }
        if(null!=file){
            String fileName=file.getOriginalFilename();//获取文件名加后缀
            upload(request, file, user, fileName);
        }


        user.setCreateTime(new Date());
        user.setSchStatus(0);
        user.setPassword(PasswordUtil.md5(user.getPassword()));
        tbUserApproveMapper.updateById(user);
        return ResultInfo.success();
    }

    private void upload(HttpServletRequest request, MultipartFile file, TbUserApprove user, String fileName) {
        File targetFile;
        String url;
        if(fileName!=null&&fileName!=""){
//            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/pic/";//存储路径

//            String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
//            String path="D:\\tempcode\\5.27\\";
            String path=null;//configProperties.getUploadPath();//线上文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀

            fileName=user.getEnterpriseName()+fileF;//新的文件名以申请名称
            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            ;
            File file1 =new File(path+"/"+fileAdd+user.getEnterpriseName());
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
//                url=returnUrl+fileAdd+"/"+fileName;
                url="/pictures/"+fileAdd+user.getEnterpriseName()+"/"+fileName;
                user.setUserPic(url);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
