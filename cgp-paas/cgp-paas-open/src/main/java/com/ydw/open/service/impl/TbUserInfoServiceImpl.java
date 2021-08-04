package com.ydw.open.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;

import com.ydw.open.dao.TbUserInfoMapper;
import com.ydw.open.model.db.TbUserInfo;
import com.ydw.open.model.db.constants.Constant;
import com.ydw.open.model.vo.AppVO;
import com.ydw.open.model.vo.UserVO;
import com.ydw.open.model.vo.modifyPasswordVO;
import com.ydw.open.service.ITbUserInfoService;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private RedisUtil redisUtil;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;

    @Value("${saas.url}")
    private String saasUrl;

    @Override
    public ResultInfo createUser(HttpServletRequest request, TbUserInfo user) {
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
        String key = PasswordUtil.md5(SequenceGenerator.sequence())+PasswordUtil.md5("yidianwan");
        String enterpriseName = user.getEnterpriseName();
        String loginName = user.getLoginName();
        //登录名校验
       TbUserInfo byLoginName = tbUserInfoMapper.getByLoginName(loginName);
        if(null!=byLoginName){
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
        tbUserInfoMapper.insertSelective(user);
        return ResultInfo.success(userVO);
    }

    @Override
    public ResultInfo updateUser(HttpServletRequest request, TbUserInfo user) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getUserInfo(token);
        Object data = res.getData();
        TbUserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), TbUserInfo.class);
        String id = userInfo.getId();
        user.setId(id);
        user.setModifiedTime(new Date());
        tbUserInfoMapper.updateSelective(user);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteUser(HttpServletRequest request, List<String> ids) {
        String result="";
        TbUserInfo tbUserInfo = new TbUserInfo();
        for (String id : ids) {
            tbUserInfo.setId(id);
            //需要确保用户名下的应用被删除，否则提示企业用户不能删除
            TbUserInfo tb = tbUserInfoMapper.selectById(id);
            List<AppVO> appsByUseId = tbUserInfoMapper.getAppsByIdentification(tb.getIdentification());
            if(appsByUseId.size()>0){

                result=result+tb.getEnterpriseName()+",";
            }else {
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
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserList(HttpServletRequest request, String enterpriseName, String loginName,
                                  String identification, Integer type, Integer schStatus, String mobileNumber,
                                  String telNumber, String search, Page buildPage) {
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String getIdentification = id.getMsg();
        Page<UserVO> list =tbUserInfoMapper.getUserList(enterpriseName, loginName,getIdentification,type, schStatus,mobileNumber,telNumber,search,buildPage);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo resetPassword(HttpServletRequest request, TbUserInfo user) {
        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getUserInfo(token);
        Object data = res.getData();
        TbUserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), TbUserInfo.class);
        String id = userInfo.getId();
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
    public ResultInfo disableUser(HttpServletRequest request, Integer type, List<String> ids) {
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
    public ResultInfo getList(HttpServletRequest request, String userName, String loginName, String identification, Integer type, Integer status, String mobileNumber, String telNumber, Page buildPage) {

        List<TbUserInfo> list =tbUserInfoMapper.getList(userName, loginName,identification,type, status,mobileNumber,telNumber,buildPage);
        List<UserVO> voList = new ArrayList<>();

        for (TbUserInfo user:list) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            voList.add(userVO);
        }

        return ResultInfo.success(voList);
    }

    @Override
    public ResultInfo getInfo(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        ResultInfo res = iYdwAuthenticationService.getUserInfo(token);
        Object data = res.getData();
        TbUserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), TbUserInfo.class);
        UserVO userVO = new UserVO();
        if(null!=userInfo){
            String id = userInfo.getId();

            TbUserInfo tu = tbUserInfoMapper.selectById(id);
            BeanUtils.copyProperties(tu,userVO);
        }
        return ResultInfo.success(userVO);
    }

    @Override
    public ResultInfo getNewRegister(String identification, String startTime, String endTime
            , Integer pageNum, Integer pageSize) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getNewRegisterByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getNewRegisterCount(String identification, String startTime, String endTime) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getNewRegisterCountByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getOnlineList(String identification, String search, Integer client, Integer pageNum, Integer pageSize) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getOnlineListByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("search",search);
        params.put("client",client);
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getRechargeList(String identification, String search, Integer pageNum, Integer pageSize) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getRechargeListByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("search",search);
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getRechargeCount(String identification, String search) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getRechargeCountByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("search",search);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getUserBalance(String identification) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getChannelBalanceByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo applyWithdraw(String identification, BigDecimal amount) {
        String url = saasUrl + "cgp-saas-admin/userInfo/applyWithdrawByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("amount",amount);
        String s = HttpUtil.doJsonPost(url, null, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getWithdrawRecord(String identification, Integer status, String beginDate, String endDate) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getWithdrawRecordByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("status",status);
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getUserPay(String identification) {
        String url = saasUrl + "cgp-saas-admin/userInfo/getUserPayByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        String s = HttpUtil.doGet(url, params);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo bindUserPay(String identification, String payAccount) {
        String url = saasUrl + "cgp-saas-admin/userInfo/bindBalancePayByEnterprise";
        HashMap<String, Object> params = new HashMap<>();
        params.put("enterpriseId",identification);
        params.put("payAccount",payAccount);
        String s = HttpUtil.doJsonPost(url, null, params);
        return JSON.parseObject(s, ResultInfo.class);
    }
}
