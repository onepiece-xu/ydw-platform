package com.ydw.user.service.impl;


import com.alibaba.fastjson.JSON;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.user.dao.ClusterOwnerMapper;
import com.ydw.user.dao.TbUserApproveMapper;
import com.ydw.user.model.db.ClusterOwner;
import com.ydw.user.model.db.TbUserApprove;
import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.dao.TbUserInfoMapper;
import com.ydw.user.model.db.constants.Constant;
import com.ydw.user.model.vo.AppVO;
import com.ydw.user.model.vo.CreateUserVO;
import com.ydw.user.model.vo.UserVO;
import com.ydw.user.model.vo.modifyPasswordVO;
import com.ydw.user.service.ITbUserInfoService;

import com.ydw.user.service.IYdwAuthenticationService;
import com.ydw.user.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */
@Service
public class TbUserInfoServiceImpl extends ServiceImpl<TbUserInfoMapper, TbUserInfo> implements ITbUserInfoService {
    private Logger logger = LoggerFactory.getLogger(TbUserInfoServiceImpl.class);

    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;
    @Autowired
    private ClusterOwnerMapper clusterOwnerMapper;
    @Autowired
    private TbUserApproveMapper tbUserApproveMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultInfo createUser(HttpServletRequest request, CreateUserVO user) {

        UserVO userVO = new UserVO();
        user.setId(SequenceGenerator.sequence());
        user.setValid(true);
        user.setCreateTime(new Date());
        userVO.setCreateTime(new Date());
        user.setSchStatus(true);//启用状态
        user.setType(2);//企业用户
        user.setPayAmount(new BigDecimal(0));//初始为0
        user.setAmount(0);//初始余额0
        //生成一个key
        String key =PasswordUtil.md5(SequenceGenerator.sequence())+PasswordUtil.md5("yidianwan");
        String enterpriseName = user.getEnterpriseName();
        String loginName = user.getLoginName();
        //登录名校验
        List<TbUserInfo> byLoginName = tbUserInfoMapper.getByLoginName(loginName);
        if(byLoginName.size()>0){
            return ResultInfo.fail("用户名已存在");
        }
        String password = user.getPassword();
        user.setPassword(PasswordUtil.md5(password));
//        Integer amount = user.getAmount();
        String description = user.getDescription();
        String email = user.getEmail();
        String identification = user.getIdentification();

        String telNumber = user.getTelNumber();
//        BigDecimal payAmount = user.getPayAmount();
        String mobileNumber = user.getMobileNumber();

        if (StringUtils.isNullOrEmpty(enterpriseName)) {
            return ResultInfo.fail(SystemConstants.AccountNameNull);
        }
        userVO.setId(user.getId());
        userVO.setAmount(0);//初始余额0
        userVO.setDescription(description);
        userVO.setEmail(email);
        userVO.setIdentification(identification);
        userVO.setType(user.getType());
        userVO.setTelNumber(telNumber);
        userVO.setMobileNumber(mobileNumber);
        userVO.setPayAmount(new BigDecimal(0));//初始为0
        userVO.setSecretKey(key);
        userVO.setValid(true);
        userVO.setSchStatus(true);
        userVO.setLoginName(loginName);
        userVO.setEnterpriseName(enterpriseName);
        user.setSecretKey(key);
        TbUserInfo tbUserInfo = new TbUserInfo();


        List<String> clusterIds = user.getClusterIds();

        for (String id :clusterIds){
            ClusterOwner clusterOwner = new ClusterOwner();
            clusterOwner.setOwnerId(identification);
            clusterOwner.setClusterId(id);
            clusterOwner.setValid(true);
            clusterOwnerMapper.insert(clusterOwner);
        }

        //插入
        BeanUtils.copyProperties(user,tbUserInfo);
        tbUserInfoMapper.insertSelective(tbUserInfo);

        //
        TbUserApprove approve = new TbUserApprove();
        approve.setId(SequenceGenerator.sequence());
        approve.setServiceId("1,2,3,4");
        BeanUtils.copyProperties(tbUserInfo,approve);
        tbUserApproveMapper.insert(approve);
        return ResultInfo.success(userVO);
    }

    @Override
    public ResultInfo updateUser(HttpServletRequest request, CreateUserVO user) {
        List<String> clusterIdsNew = user.getClusterIds();
        String identification = user.getIdentification();
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("owner_id",identification);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        for (ClusterOwner co: clusterOwners){
            co.setValid(false);
            clusterOwnerMapper.deleteById(co);
        }
        for (String  id: clusterIdsNew ) {
            ClusterOwner clusterOwner = new ClusterOwner();
            clusterOwner.setClusterId(id);
            clusterOwner.setOwnerId(identification);
            clusterOwner.setValid(true);
            clusterOwnerMapper.insert(clusterOwner);
        }


        TbUserInfo tbUserInfo = new TbUserInfo();
        BeanUtils.copyProperties(user,tbUserInfo);
        user.setModifiedTime(new Date());
        tbUserInfoMapper.updateSelective(tbUserInfo);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteUser(HttpServletRequest request, List<String> ids) {
        String result="";
        String clusterResult="";
        TbUserInfo tbUserInfo = new TbUserInfo();
        for (String id : ids) {
            tbUserInfo.setId(id);
            //需要确保用户名下的应用被删除，否则提示企业用户不能删除
            TbUserInfo tb = tbUserInfoMapper.selectById(id);
            List<String> clusterByUser = clusterOwnerMapper.getClusterByUser(tb.getIdentification());
            List<AppVO> appsByUseId = tbUserInfoMapper.getAppsByIdentification(tb.getIdentification());
            if(appsByUseId.size()>0){
                result=result+tb.getEnterpriseName()+",";
            }
            if(clusterByUser.size()>0){
                clusterResult="用户已绑定集群，请取消绑定后再操作！";
            }
            if(clusterByUser.size()==0&&appsByUseId.size()==0) {
                //软删除
                tbUserInfo.setValid(false);
                tbUserInfo.setModifiedTime(new Date());
                tbUserInfoMapper.updateSelective(tbUserInfo);
            }
        }

        if(result!=""){
            result=result+"用户存在未删除的应用,用户删除失败!";
            return ResultInfo.fail(result);
        }
        if(clusterResult!=""){
            return ResultInfo.fail(clusterResult);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserList(HttpServletRequest request, String enterpriseName, String loginName, String identification, Integer type, Integer schStatus, String mobileNumber, String telNumber,
                                  String search, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
//        Page<TbUserInfo> list =tbUserInfoMapper.getUserList(enterpriseName, loginName,identification,type, schStatus,mobileNumber,telNumber,search,buildPage);
//        List<UserVO> voList = new ArrayList<>();
//
//        for (TbUserInfo user:list.getRecords()) {
//            UserVO userVO = new UserVO();
//            BeanUtils.copyProperties(user,userVO);
//            voList.add(userVO);
//        }
        Page<TbUserInfo> list =tbUserInfoMapper.getUserList(enterpriseName, loginName,identification,type, schStatus,mobileNumber,telNumber,search,buildPage);
//        Page page=new Page();
//        PageInfo<UserVO> pageInfo = new PageInfo<>(voList);
        List<TbUserInfo> records = list.getRecords();
        List<UserVO> data=new ArrayList<>();
        for (TbUserInfo info: records) {
            String infoIdentification = info.getIdentification();
            List<String> clusters = clusterOwnerMapper.getClusterByUser(infoIdentification);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(info,userVO);
            userVO.setClusterIds(clusters);
            data.add(userVO);
        }
//        List<UserVO> vos = new ArrayList<>();
//        for (int i = 0; i <records.size() ; i++) {
//            String voIdentification = records.get(i).getIdentification();
//            List<String> clusters = clusterOwnerMapper.getClusterByUser(voIdentification);
//            new UserVO();
//            records.get(i).setClusterIds(clusters);
//        }
        Page page = new Page();
        page.setRecords(data);
        page.setCurrent(list.getCurrent());
        page.setSize(list.getSize());
        page.setTotal(list.getTotal());

        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo resetPassword(HttpServletRequest request, TbUserInfo user) {
        String id = user.getId();
        TbUserInfo tbUserInfo = tbUserInfoMapper.selectByInfoId(id);
        String password = user.getPassword();
        if(StringUtil.nullOrEmpty(password)){
            tbUserInfo.setPassword(PasswordUtil.md5("yidianwan"));
        }else{
            tbUserInfo.setPassword(PasswordUtil.md5(password));
        }
        tbUserInfo.setModifiedTime(new Date());
        tbUserInfoMapper.updateById(tbUserInfo);
        return ResultInfo.success("Reset password success.");
    }

    @Override
    public ResultInfo getUserInfo(HttpServletRequest request, String id) {
        UserVO userVO = new UserVO();
        TbUserInfo tbUserInfo = tbUserInfoMapper.selectByInfoId(id);
        if(null==tbUserInfo){
            return ResultInfo.fail("用户不存在");
        }
        BeanUtils.copyProperties(tbUserInfo,userVO);
        return ResultInfo.success(userVO);
    }

    @Override
    public ResultInfo disableUser(HttpServletRequest request,  Integer type, List<String> ids) {

        for (String id : ids) {
            TbUserInfo tbUserInfo = tbUserInfoMapper.selectByInfoId(id);
            if (type == 0) {
                tbUserInfo.setSchStatus(true);//启用

            } else {
                tbUserInfo.setSchStatus(false);//禁用
            }
            tbUserInfo.setModifiedTime(new Date());
            tbUserInfoMapper.updateById(tbUserInfo);
        }
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo updatePassword(HttpServletRequest request, modifyPasswordVO user) {
//        String token = request.getHeader("Authorization");
//        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
//        String userString = (String) redisUtil.get(Constant.USER + account);
//        TbUserInfo tbUserInfo = JSON.parseObject(userString, TbUserInfo.class);
//        String id= tbUserInfo.getId();
//        TbUserInfo tu = tbUserInfoMapper.selectById(id);
//        if(!(PasswordUtil.md5(user.getOldPassword())).equals(tu.getPassword())){
//            return ResultInfo.fail("原密码错误");
//        }
//        tbUserInfo.setPassword(PasswordUtil.md5(user.getNewPassword()));
//        tbUserInfoMapper.updateById(tbUserInfo);
//
//        redisUtil.set(Constant.USER + id, JSON.toJSONString(tbUserInfo));
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getUserInfo(token);
        Object data = res.getData();
        TbUserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), TbUserInfo.class);
        String id = userInfo.getId();
        TbUserInfo tu = tbUserInfoMapper.selectById(id);
        if(!(PasswordUtil.md5(user.getOldPassword())).equals(tu.getPassword())){
            return ResultInfo.fail("原密码错误");
        }
        tu.setPassword(PasswordUtil.md5(user.getNewPassword()));
        tbUserInfoMapper.updateById(tu);

        redisUtil.set(Constant.USER + id, JSON.toJSONString(tu));
        return ResultInfo.success("密码修改成功！");
    }

    @Override
    public ResultInfo serviceUserList() {
        List<UserVO> userVOS = tbUserInfoMapper.serviceUserList();
        return ResultInfo.success(userVOS);
    }


}
