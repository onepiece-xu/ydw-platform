package com.ydw.authentication.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.authentication.dao.ClustersMapper;
import com.ydw.authentication.dao.UserApproveMapper;
import com.ydw.authentication.dao.UserInfoMapper;
import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.Clusters;
import com.ydw.authentication.model.db.TbUserInfo;
import com.ydw.authentication.model.db.UserApprove;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.LoginVO;
import com.ydw.authentication.model.vo.UserVO;
import com.ydw.authentication.service.ILoginService;
import com.ydw.authentication.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService{


	@Value("${shiro.accessTokenExpireTime}")
    private Long accessTokenExpireTime;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserApproveMapper userApproveMapper;

	@Autowired
	private ClustersMapper clustersMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ResultInfo saasLogin(LoginVO loginVO) {
		QueryWrapper<UserInfo> qw = new QueryWrapper<>();
		qw.eq("identification", loginVO.getIdentification());
		qw.eq("secretKey", loginVO.getSecretKey());
		UserInfo userInfo = userInfoMapper.selectOne(qw);
        {
            //TODO 判断这个企业用户是否有这个saas
        }
		if(userInfo != null){
			String account = loginVO.getIdentification() + "-" + loginVO.getSaas();
            String token = (String) redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account);
            if (StringUtils.isBlank(token)){
            	logger.info(JSON.toJSONString(loginVO));
                token = JwtUtil.sign(account, loginVO.getIdentification(),
                        loginVO.getSecretKey(), loginVO.getSaas().toString());
				boolean setnx = redisUtil.setnx(Constant.PREFIX_SHIRO_TOKEN + account, token, accessTokenExpireTime);
				if (!setnx){
					token = (String) redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account);
				}
			}
            Map<String,Object> result = new HashMap<>();
			result.put("token",token);
			result.put("expireDate",JwtUtil.getExpiresAt(token));
			return ResultInfo.success("登录成功！", result);
		}
		return ResultInfo.fail();
	}

	@Override
	public ResultInfo checkToken(String token) {
		String account  = JwtUtil.getClaim(token,Constant.ACCOUNT);
		String redisToken = (String)(redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account));
		if(StringUtil.nullOrEmpty(redisToken)){
			ResultInfo info = new ResultInfo();
			info.setCode(401);
			info.setMsg("身份信息已失效，请重新登陆！");
			return info;
		}else{
			if (redisToken.equals(token)) {
				HashMap<String, String> hashMap = new HashMap<>();
				hashMap.put("identification",JwtUtil.getClaim(token, Constant.IDENTIFICATION));
				hashMap.put("saas",JwtUtil.getClaim(token, Constant.SAAS));
				return ResultInfo.success("登录成功！", hashMap);
			} else {
				ResultInfo info = new ResultInfo();
				info.setCode(401);
				info.setMsg("身份信息已失效，请重新登陆！");
				return info;
			}
		}
	}

	@Override
	public ResultInfo openLogin(HttpServletRequest request, HttpServletResponse response, UserApprove user) {
		{
			String token = null;
			String loginName = user.getLoginName();
			String password = user.getPassword();
			//查询该用户是否待审批用户
			UserApprove userApprove = userApproveMapper.getByLoginName(loginName);

			if (null != userApprove && userApprove.getType() == 0) {
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(userApprove, userVO);
				response.setHeader("Authorization", token);
				response.setHeader("Access-Control-Request-Headers", "Authorization");

				String id = userVO.getId();
				token = JwtUtil.sign(userVO.getId(),userVO.getIdentification(), userVO.getSecretKey());
//				token = JwtUtil.sign(user.getId(), user.getIdentification(),Constant.OPEN_PLATFORM_USER);
				redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);

				redisUtil.set(Constant.OPEN_PLATFORM_USER + id, JSON.toJSONString(userVO));
				userVO.setToken(token);
				return ResultInfo.success(userVO);
			} else {
				//查询该注册用户是否是正式用户
				QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
				wrapper.eq("login_name",loginName);
				UserInfo userInfo = userInfoMapper.selectOne(wrapper);
				if (null != userInfo) {
					if (userInfo.getType() != 2 ) {
						return ResultInfo.fail("用户不存在,请确认后登录！");
					}
					if (userInfo.getType() != 2 && userInfo.getType() != 1) {
						return ResultInfo.fail("用户不存在,请确认后登录！");
					}

				}
				//未注册用户
				if (null == userInfo && null == userApprove) {
					return ResultInfo.fail("用户不存在,请确认后登录！");
				}

				//是审批用户
				if (null != userApprove && null == userInfo) {
					if (!PasswordUtil.md5(password).equals(userApprove.getPassword())) {
						return ResultInfo.fail("密码错误");
					}
					Integer schStatus = userApprove.getSchStatus();
					if (0 == schStatus) {
						//审批中
						return ResultInfo.success("0");
					}
					if (2 == schStatus) {
						//审批失败
						return ResultInfo.success("2", userApprove);
					}
				}
				if (!PasswordUtil.md5(password).equals(userInfo.getPassword())) {
					return ResultInfo.fail("密码错误");
				}

				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(userInfo, userVO);
				response.setHeader("Authorization", token);
				response.setHeader("Access-Control-Request-Headers", "Authorization");
				String id = userVO.getId();
				token = JwtUtil.sign(userVO.getId(),userVO.getIdentification(), userVO.getSecretKey());
				redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
				redisUtil.set(Constant.OPEN_PLATFORM_USER + id, JSON.toJSONString(userVO));
				userVO.setToken(token);
//				if(null!=userInfo &&null==userApprove){
//					UserApprove approve = new UserApprove();
//					approve.setId(SequenceGenerator.sequence());
//					BeanUtils.copyProperties(userInfo,userApprove);
//					userApproveMapper.insert(approve);
//					//设置该用户全服务可用
//					userVO.setServiceId("1,2,3,4");
//				}else{
					userVO.setServiceId(userApprove.getServiceId());
//				}

				return ResultInfo.success(userVO);
			}
		}
	}

	@Override
	public ResultInfo backStageLogin(HttpServletRequest request, HttpServletResponse response, UserInfo user) {
		String token = null;
		String loginName = user.getLoginName();
		String password = user.getPassword();
		QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
		wrapper.eq("login_name",loginName);
		List<UserInfo> byLoginName = userInfoMapper.selectList(wrapper);
		if (byLoginName.size() == 0) {
			logger.error("登录名"+loginName+"账号不存在");
			return ResultInfo.fail("账号不存在");
		}

		UserInfo tbUserInfo = byLoginName.get(0);
		if (tbUserInfo.getType()!= 1) {
			logger.error("登录名"+loginName+"账号不存在");
			return ResultInfo.fail("账号不存在");
		}
		if (!PasswordUtil.md5(password).equals(tbUserInfo.getPassword())) {
			logger.error("登录名"+loginName+"账号不存在");
			return ResultInfo.fail("密码错误");
		}
		//TODO 禁用用户登录
		if (!tbUserInfo.getSchStatus()) {
			logger.error("登录名"+loginName+"账号不存在");
			return ResultInfo.fail("账号暂停使用，请联系管理员！");
		}
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(tbUserInfo, userVO);
		response.setHeader("Authorization", token);
		response.setHeader("Access-Control-Request-Headers", "Authorization");

		String id = userVO.getId();
//		token = JwtUtil.sign(id, RoleEnum.USER.getValue(), RoleEnum.USER.getValue());
		token = JwtUtil.sign(userVO.getId(),userVO.getIdentification(), userVO.getSecretKey());
		redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);

		redisUtil.set(Constant.BACK_STAGE_USER + id, JSON.toJSONString(userVO));
		userVO.setToken(token);
		return ResultInfo.success(userVO);

	}

	@Override
	public ResultInfo logout(String token) {
		String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
		redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + account);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo getUserInfo(String token) {
		String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
		logger.info("token的account值为：{}", account);
		String userString = (String) redisUtil.get(Constant.BACK_STAGE_USER+ account);
        if(StringUtil.nullOrEmpty(userString)){
            userString = (String) redisUtil.get(Constant.OPEN_PLATFORM_USER+ account);
        }
        if (StringUtil.isBlank(userString)){
            String identification = account.split("-")[0];
            QueryWrapper<UserInfo> qw = new QueryWrapper<UserInfo>().eq("identification", identification);
            UserInfo userInfo = userInfoMapper.selectOne(qw);
            return ResultInfo.success(userInfo);
        }
		return ResultInfo.success(JSON.parseObject(userString, TbUserInfo.class));
	}

	@Override
	public ResultInfo clusterLogin(String clusterId) {
        String token = (String) redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + clusterId);
        if(StringUtils.isBlank(token)){
            Clusters clusters = clustersMapper.selectById(clusterId);
            if(clusters != null){
                token = JwtUtil.sign(clusterId);
				boolean setnx = redisUtil.setnx(Constant.PREFIX_SHIRO_TOKEN + clusterId, token, accessTokenExpireTime);
				if (!setnx){
					token = (String) redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + clusterId);
				}
			}
        }else{
			long expire = redisUtil.getExpire(Constant.PREFIX_SHIRO_TOKEN + clusterId);
			if (expire < accessTokenExpireTime/4){
				token = JwtUtil.sign(clusterId);
				redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + clusterId, token, accessTokenExpireTime);
			}
		}
        return ResultInfo.success("获取边缘节点token成功！",token);
	}

	@Override
	public ResultInfo getIdentification(String token) {
//		String token = request.getHeader("Authorization");
		String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
		logger.info(account+" ------------account");
		String userString = (String) redisUtil.get(Constant.BACK_STAGE_USER+ account);
		logger.info(userString+" ------------userString");
		if(StringUtil.nullOrEmpty(userString)){
			if(StringUtil.nullOrEmpty(userString)){
				userString = (String) redisUtil.get(Constant.OPEN_PLATFORM_USER+ account);
				logger.info(userString+" ------------userString");
			}
		}
		TbUserInfo tbUserInfo = JSON.parseObject(userString, TbUserInfo.class);
		logger.info(tbUserInfo+" ------------tbUserInfo");
		String identification = tbUserInfo.getIdentification();
		return ResultInfo.success(identification);
	}
}
