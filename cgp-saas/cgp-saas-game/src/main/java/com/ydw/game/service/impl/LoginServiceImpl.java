package com.ydw.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.mysql.cj.util.StringUtils;
import com.ydw.game.dao.TbUserAuthsMapper;
import com.ydw.game.dao.TbUserInfoMapper;
import com.ydw.game.model.constant.SystemConstants;
import com.ydw.game.model.db.TbUserAuths;
import com.ydw.game.model.db.TbUserInfo;
import com.ydw.game.model.vo.*;
import com.ydw.game.service.IEnterpriseService;
import com.ydw.game.util.HttpClientUtils;
import com.ydw.game.util.PasswordUtil;
import com.ydw.game.util.SequenceGenerator;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.game.config.jwt.JwtUtil;
import com.ydw.game.dao.EnterpriseMapper;
import com.ydw.game.dao.UserMapper;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.Enterprise;
import com.ydw.game.model.db.User;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.ILoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-07-28
 */
@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private EnterpriseMapper enterpriseMapper;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TbUserInfoMapper tbUserInfoMapper;
	@Autowired
	private TbUserAuthsMapper tbUserAuthsMapper;

	
	@Autowired
	private RedisUtil redisUtil;
	
	@Value("${shiro.accessTokenExpireTime}")
	private Integer accessTokenExpireTime;
	@Autowired
	private IEnterpriseService iEnterpriseService;


	// 短信应用 SDK AppID
	int appid = 1400262730; // SDK AppID 以1400开头
	// 短信应用 SDK AppKey
	String appkey = "640a07cb6b0d0c41f500a07bbf4ab083";
	// 短信模板 ID，需要在短信应用中申请
	int templateId = 430765; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
	// 签名
	String smsSign = "海云捷迅"; // NOTE:
	// 默认验证码
	String str = "000000";



//	@Override
//	public ResultInfo mockLogin(User user) {
//		QueryWrapper<User> qw = new QueryWrapper<>();
//		qw.eq("loginname", user.getLoginname());
//		qw.eq("password", user.getPassword());
//		User one = userMapper.selectOne(qw);
//		Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
//		String account = one.getId();
//		String token = JwtUtil.sign(one.getId(), enterprise.getIdentification(), enterprise.getSecretKey());
//        redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + account, token, accessTokenExpireTime);
//		return ResultInfo.success("登录认证成功！", token);
//	}

//	@Override
//	public ResultInfo mockLoginout(String account) {
//		redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + account);
//		return ResultInfo.success();
//	}

	@Override
	public ResultInfo login(User user) {
		Integer type = user.getType();
		QueryWrapper<User> qw = new QueryWrapper<>();
		qw.eq("loginname", user.getLoginname());
		qw.eq("password", user.getPassword());
		qw.eq("type", type);
		User one = userMapper.selectOne(qw);
		if(null==one){

			return ResultInfo.fail("管理员登录失败，请确认账号与密码！");
		}
		Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
		String account = one.getId();
		String token = JwtUtil.sign(one.getId(), enterprise.getIdentification(), enterprise.getSecretKey());
		redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + account, token, accessTokenExpireTime);

		redisUtil.set(Constant.USER + account, JSON.toJSONString(one));
		String enterpriseId = iEnterpriseService.getEnterpriseId();
		EnterpriseAndTokenVO vo = new EnterpriseAndTokenVO();
		vo.setToken(token);
		vo.setEnterpriseId(enterpriseId);
		return ResultInfo.success("登录认证成功！", vo );
	}

	@Override
	public ResultInfo logout(String account) {
		redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + account);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo updatePassword(HttpServletRequest request,updatePasswordVO user) {
		String token = request.getHeader("Authorization");
		String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
		String userString = (String) redisUtil.get(Constant.USER + account);
		User one = JSON.parseObject(userString, User.class);

//		QueryWrapper<User> qw = new QueryWrapper<>();
//		qw.eq("loginname", user.getLoginname());
//		User one = userMapper.selectOne(qw);
		if(!one.getPassword().equals(user.getOldPassword())){
			return ResultInfo.fail("原密码错误！");
		}else{
			one.setPassword(user.getNewpassword());
			userMapper.updateById(one);
			return ResultInfo.success("修改成功！");
		}

	}

	@Override
	public ResultInfo getQRCode(HttpServletRequest request) {
		return null;
	}

	@Override
	public ResultInfo userLogout(HttpServletRequest request) {
		TbUserInfo userInfoFromRequest = getUserInfoFromRequest(request);
		TbUserInfo userinfo = new TbUserInfo();
		userinfo.setId(userInfoFromRequest.getId());
		userinfo.setOnilneStatus(false);
		tbUserInfoMapper.updateSelective(userinfo);
		String id = userInfoFromRequest.getId();
		Object token = redisUtil.get(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id);
		System.out.println(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id + "登录时的token:" + token);

		redisUtil.del(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id);
		redisUtil.del(Constant.USER + id);
		return ResultInfo.success();
	}

	public TbUserInfo getUserInfoFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
		String userString = (String) redisUtil.get(Constant.USER + account);
		TbUserInfo tbUserInfo = JSON.parseObject(userString, TbUserInfo.class);
		return tbUserInfo;
	}

	@Override
	public ResultInfo addUserByLocal(HttpServletRequest request, RegistUserModel user) {
		TbUserInfo tbUserInfo = new TbUserInfo();
		String number = user.getMobileNumber();
		// 验 验证码
		String code = user.getMessageCode();
		Object validateCode = redisUtil.get(Constant.PREFIX_lOCALREGISTCODE_SESSION + number);
		// if (!code.equals(validateCode) || null == validateCode.toString()) {
		// return ResultInfo.fail("validateCode Error!");
		// }
		if (null == validateCode) {
			return ResultInfo.fail(SystemConstants.CodeExpire);
		}
		if (!code.equals(validateCode)) {
			return ResultInfo.fail(SystemConstants.CodeError);
		}

		// 推荐人信息
//		String recommender = user.getRecommender();
//		if (!StringUtils.isNullOrEmpty(recommender)) {
//			tbUserInfo.setRecommender(recommender);
//			TbUserInfo selectByInfoId = tbUserInfoMapper.selectByInfoId(recommender);
//			if (null == selectByInfoId) {
//				ResultInfo.fail(SystemConstants.UnrecognizedReferences);
//			}

			//推荐人奖励
//			BigDecimal recommenderDecimal = rechargeActiveRuleService.checkRegisterRecommenderAmount();
//			BigDecimal userBalance = selectByInfoId.getUserBalance();
//			selectByInfoId.setUserBalance(userBalance.add(recommenderDecimal));
//			selectByInfoId.setModifiedTime(new Date());
//			tbUserInfoMapper.updateSelective(selectByInfoId);

//		}

		// 设置默认值
		tbUserInfo.setUserPoint(0);
		tbUserInfo.setUserLevel(0);
		tbUserInfo.setUserBalance(null);
		tbUserInfo.setAccountStatus("0");
		// 账号&密码
		String mobileNumber = user.getMobileNumber();
		String password = user.getPassword();
		if (!StringUtils.isNullOrEmpty(password)) {
			tbUserInfo.setPassword(PasswordUtil.md5(password));
		}
		if (!StringUtils.isNullOrEmpty(mobileNumber)) {
			tbUserInfo.setMobileNumber(mobileNumber);
			int i = tbUserInfoMapper.selectCountByMobileNumber(tbUserInfo);
			if (i > 0) {
				return ResultInfo.fail(SystemConstants.AccountNameUsed);
			}
		}
		tbUserInfo.setMobileNumber(user.getMobileNumber());
		tbUserInfo.setNickname(user.getNickName());
		tbUserInfo.setRegisterTime(new Date());
		tbUserInfo.setId(SequenceGenerator.sequence());
		tbUserInfoMapper.insertSelective(tbUserInfo);

		//首次注冊送30平台币
//		BigDecimal decimal = rechargeActiveRuleService.checkRegisterAmount();
//		UpdateUser updateUser = new UpdateUser();
//		updateUser.setId(tbUserInfo.getId());
//		updateUser.setUserBalance(decimal.negate());
//		updateUserAccount(request, updateUser);


		// 入auth表
		TbUserAuths tbUserAuths = new TbUserAuths();
		tbUserAuths.setId(SequenceGenerator.sequence());
		tbUserAuths.setIdentifier(tbUserInfo.getMobileNumber());
		// local注册1
		tbUserAuths.setIdentityType(1);
		tbUserAuths.setUserId(tbUserInfo.getId());

		tbUserAuthsMapper.insertSelective(tbUserAuths);
		return ResultInfo.success(SystemConstants.RegistSuccess);
	}

	@Override
	public ResultInfo smsSend(HttpServletRequest request, RegistUserModel user) {
		String num = user.getMobileNumber();
		// SendMessageUtil sendMessageUtil = new SendMessageUtil();
		// sendMessageUtil.sendMessage(num);
		Integer type = user.getType();
		String mobileNumber = user.getMobileNumber();
		if (null == type) {
			return ResultInfo.fail(SystemConstants.UnkonwCodeType);
		}
		if (type == 5) {
			// if (type == 5 || type == 4) {
			TbUserInfo selectByMobileNumber = tbUserInfoMapper.selectByMobileNumber(mobileNumber);
			if (null == selectByMobileNumber) {
				return ResultInfo.fail(SystemConstants.NotRegist);
			}
		}
		if (type == 3 || type == 1) {
			TbUserInfo selectByMobileNumber = tbUserInfoMapper.selectByMobileNumber(mobileNumber);
			if (null != selectByMobileNumber) {
				return ResultInfo.fail(SystemConstants.Exist);
			}
		}
		sendMessageCode(num, type);
		return ResultInfo.success("success");
	}

	@Override
	public ResultInfo loginLocal(HttpServletRequest request, HttpServletResponse response, LoginUserVO user) {
		String token = null;
		String account = user.getAccount();
		// 输入的密码与已注册用户密码比对
		String password = user.getPassword();
		TbUserInfo selectByMobileNumber = tbUserInfoMapper.selectByMobileNumber(account);
		if (!PasswordUtil.md5(password).equals(selectByMobileNumber.getPassword())) {
			return ResultInfo.fail(SystemConstants.ErrorPassword);
		}

		TbUserInfo userInfo = tbUserInfoMapper.selectByMobileNumber(account);
		if (null == userInfo) {
			TbUserInfo selectByMobileNumberFreeze = tbUserInfoMapper.selectByMobileNumberFreeze(account);
			if(null!=selectByMobileNumberFreeze){
				return ResultInfo.fail(SystemConstants.Disable);
			}
			return ResultInfo.fail(SystemConstants.PleaseRegist);
		}
		TbUserAuths selectByIdentifier = tbUserAuthsMapper.selectByIdentifier(account, 1);
		if (null == selectByIdentifier) {
			String id = userInfo.getId();
			TbUserAuths tbUserAuths = new TbUserAuths();
			tbUserAuths.setId(SequenceGenerator.sequence());
			tbUserAuths.setIdentityType(1);
			tbUserAuths.setIdentifier(account);
			tbUserAuths.setUserId(id);
			tbUserAuthsMapper.insertSelective(tbUserAuths);
		}

		TbUserInfo tbUserInfo = new TbUserInfo();
		tbUserInfo.setMobileNumber(account);
		// 被禁用的账户登录
		String status = tbUserInfoMapper.selectStatusByMobileNumber(account);
		if (!StringUtils.isNullOrEmpty(status) && status.equals("1")) {
			return ResultInfo.fail("Account has been disabled,Please contact the administrator!");
		}
		int mobileNumber = tbUserInfoMapper.selectCountByMobileNumber(tbUserInfo);
		if (mobileNumber == 0) {
			return ResultInfo.fail(SystemConstants.PleaseRegist);
		}

//		boolean isOnline = redisUtil.sHasKey(Constant.WS_ONLINE, userInfo.getId());
//		if(isOnline){
//
//			WSMsg msg = new WSMsg();
//			msg.setReceiver(userInfo.getId());
//			JSONObject object = new JSONObject();
//			object.put("title", "登录");
//			object.put("msg", "您已经在其他设备登录！将被迫下线！");
//			object.put("channel", "exception");
//			msg.setData(object);
//			msg.setType(ChannelEnum.NOTICE.getChannel());
//			redisUtil.sendMsg(ChannelEnum.NOTICE.getChannel(), msg);
//
//
//			//return null;
//		}else{
//
//		}

		// 插入登录信息
		selectByMobileNumber.setLoginTime(new Date());
		selectByMobileNumber.setOnilneStatus(true);
		tbUserInfoMapper.updateSelective(selectByMobileNumber);
		//
		selectByMobileNumber.setPassword(null);
		response.setHeader("Authorization", token);
		response.setHeader("Access-Control-Request-Headers", "Authorization");

		String id = userInfo.getId();
		token = JwtUtil.sign(id, "user");
		redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);

		redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByMobileNumber));
		selectByMobileNumber.setToken(token);


		return ResultInfo.success(selectByMobileNumber);
	}

	@Override
	public ResultInfo addUserByPhone(HttpServletRequest request, HttpServletResponse response, RegistUserModel user) {
		// token
		String token = null;
		// 待绑定的账号
		String mobileNumber = user.getMobileNumber();
		// 验证码
		String code = user.getMessageCode();
		Object validateCode = redisUtil.get(Constant.PREFIX_MOBILEREGISTCODE_SESSION + mobileNumber);
		if (null == validateCode) {
			return ResultInfo.fail(SystemConstants.CodeExpire);
		}
		if (!code.equals(validateCode)) {
			return ResultInfo.fail(SystemConstants.CodeError);
		}

		// 判断是否已注册
		TbUserInfo selectByMobileNumber = tbUserInfoMapper.selectByMobileNumber(mobileNumber);
		// 老用户新登录
		if (null != selectByMobileNumber) {
			Integer identityType = 3;
			TbUserAuths selectByIdentifier = tbUserAuthsMapper.selectByIdentifier(mobileNumber, identityType);
			if (null != selectByIdentifier) {
				String userId = selectByIdentifier.getUserId();
				if (!StringUtils.isNullOrEmpty(userId)) {
					TbUserInfo selectByInfoId = tbUserInfoMapper.selectByInfoId(userId);
					if (null == selectByInfoId) {
						return ResultInfo.fail(SystemConstants.Exist);
					}
					String accountStatus = selectByInfoId.getAccountStatus();
					if (!StringUtils.isNullOrEmpty(accountStatus) && accountStatus.equals("1")) {
						return ResultInfo.fail(SystemConstants.Disable);
					}
				}

			}
			if (null == selectByIdentifier) {
				String id = selectByMobileNumber.getId();
				TbUserAuths tbUserAuths = new TbUserAuths();
				tbUserAuths.setId(SequenceGenerator.sequence());
				tbUserAuths.setUserId(id);
				tbUserAuths.setIdentityType(3);
				tbUserAuths.setIdentifier(mobileNumber);
				tbUserAuthsMapper.insertSelective(tbUserAuths);

			}
			// 老用户老登陆 刷新登录时间
			selectByMobileNumber.setLoginTime(new Date());
			selectByMobileNumber.setOnilneStatus(true);
			tbUserInfoMapper.updateSelective(selectByMobileNumber);

			String id = selectByMobileNumber.getId();
			token = JwtUtil.sign(id, "user");
			redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);
			redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByMobileNumber));
			selectByMobileNumber.setToken(token);
			return ResultInfo.success(selectByMobileNumber);

		}

		// 新用户注册
		TbUserAuths tbUserAuths = new TbUserAuths();
		tbUserAuths.setId(SequenceGenerator.sequence());
		tbUserAuths.setIdentifier(mobileNumber);
		tbUserAuths.setIdentityType(3);

		TbUserInfo tbUserInfo = new TbUserInfo();
		tbUserInfo.setAccountStatus("0");
		tbUserInfo.setMobileNumber(mobileNumber);
		tbUserInfo.setId(SequenceGenerator.sequence());
		tbUserInfo.setRegisterTime(new Date());
		// 默认昵称为手机号
		tbUserInfo.setNickname(mobileNumber);
		// 默认初始密码为123456
		tbUserInfo.setPassword(PasswordUtil.md5("123456"));
		tbUserInfoMapper.insert(tbUserInfo);

		// 首次注冊送300平台币
//		BigDecimal decimal = rechargeActiveRuleService.checkRegisterAmount();
//		UpdateUser updateUser = new UpdateUser();
//		updateUser.setId(tbUserInfo.getId());
//		updateUser.setUserBalance(decimal.negate());
//		updateUserAccount(request, updateUser);

		// 用户info表id
		String id = tbUserInfo.getId();
		tbUserAuths.setUserId(id);
		tbUserAuthsMapper.insert(tbUserAuths);

		// 注册
		token = JwtUtil.sign(id, "user");
		redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);
		redisUtil.set(Constant.USER + id, JSON.toJSONString(tbUserInfo));
		tbUserInfo.setToken(token);
		tbUserInfo.setOnilneStatus(true);
		tbUserInfo.setPassword(null);
		return ResultInfo.success(tbUserInfo);
	}

	@Override
	public ResultInfo loginWechat(HttpServletRequest request, LoginUserVO user) {
		// token
		String token = null;
		// 待绑定的账号
		String mobileNumber = user.getMobileNumber();
		String openid = user.getIdentifier();
		// 验证码
		String code = user.getMessageCode();
		Object validateCode = redisUtil.get(Constant.PREFIX_WXREGISTCODE_SESSION + mobileNumber);
		if (null == validateCode) {
			return ResultInfo.fail(SystemConstants.CodeExpire);
		}
		if (!code.equals(validateCode)) {
			return ResultInfo.fail(SystemConstants.CodeError);
		}

//		Integer identityType = 2;
		// if (null!=identityType&&identityType != 2) {
		// ResultInfo.fail("login type error");
		// }
//		TbUserAuths selectByIdentifier = tbUserAuthsMapper.selectByIdentifier(openid, 2);
		TbUserInfo select = tbUserInfoMapper.selectByMobileNumber(mobileNumber);
		if(null==openid){
			return ResultInfo.fail("微信号不能为空");
		}

		if(null!=select){
			String wechatOpenId = select.getWechatOpenId();
			if(null!=wechatOpenId){
				String accountStatus = select.getAccountStatus();
				if(accountStatus.equals("0")){
					return ResultInfo.fail("手机号已绑定微信，请勿重复绑定");
				}
			}
			//绑定接口老用户老登录
			TbUserAuths selectByIdentifier = tbUserAuthsMapper.selectByIdentifier(openid, 2);
			if(null!=selectByIdentifier){
//				String id = selectByIdentifier.getUserId();
//				//更新info表
//				select.setLoginTime(new Date());
//				select.setOnilneStatus(true);
//				select.setWechatOpenId(openid);
//				tbUserInfoMapper.updateSelective(select);
//
//				token = JwtUtil.sign(id, "user");
//				redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);
//				redisUtil.set(Constant.USER + id, JSON.toJSONString(select));
//				select.setToken(token);
//				select.setPassword(null);
				return ResultInfo.fail("微信号已绑定，请勿重复绑定");
			}
//			老用户新登录
			String id = select.getId();
			TbUserAuths tbUserAuths = new TbUserAuths();
			//插入auth表
			tbUserAuths.setUserId(id);
			tbUserAuths.setIdentifier(openid);
			//微信注册类型为2
			tbUserAuths.setIdentityType(2);
			tbUserAuths.setId(SequenceGenerator.sequence());
			tbUserAuthsMapper.insert(tbUserAuths);
			//更新info表
			select.setLoginTime(new Date());
			select.setOnilneStatus(true);

			select.setWechatOpenId(openid);
			tbUserInfoMapper.updateSelective(select);

			token = JwtUtil.sign(id, "user");
			redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);
			redisUtil.set(Constant.USER + id, JSON.toJSONString(select));
			select.setToken(token);
			select.setPassword(null);
			return ResultInfo.success(select);

		} else {
			//新用户新登录
			TbUserInfo tbUserInfo = new TbUserInfo();
//			tbUserInfo.setWechat(openid);
			// 插info表
			tbUserInfo.setMobileNumber(mobileNumber);
			tbUserInfo.setNickname(mobileNumber);
			tbUserInfo.setUserPoint(0);
			tbUserInfo.setUserLevel(0);
			tbUserInfo.setId(SequenceGenerator.sequence());
			tbUserInfo.setRegisterTime(new Date());
			tbUserInfo.setPassword(PasswordUtil.md5("123456"));
			tbUserInfo.setAccountStatus("0");
			tbUserInfo.setWechatOpenId(openid);
			tbUserInfoMapper.insertSelective(tbUserInfo);
			String id = tbUserInfo.getId();

			//首次注冊送30平台币
//			BigDecimal decimal = rechargeActiveRuleService.checkRegisterAmount();
//			UpdateUser updateUser = new UpdateUser();
//			updateUser.setId(id);
//			updateUser.setUserBalance(decimal.negate());
//			updateUserAccount(request, updateUser);

			TbUserAuths tbUserAuths = new TbUserAuths();
			//插入auth表
			tbUserAuths.setUserId(id);
			tbUserAuths.setIdentifier(openid);
			tbUserAuths.setIdentityType(2);
			tbUserAuths.setId(SequenceGenerator.sequence());
			//登录信息
			tbUserInfo.setLoginTime(new Date());
			tbUserInfo.setOnilneStatus(true);
			tbUserAuthsMapper.insertSelective(tbUserAuths);

			tbUserInfoMapper.updateSelective(tbUserInfo);
			token = JwtUtil.sign(id, "user");
			TbUserInfo selectByMobileNumber = tbUserInfoMapper.selectByMobileNumber(mobileNumber);
			redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);
			redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByMobileNumber));
			selectByMobileNumber.setToken(token);
			selectByMobileNumber.setPassword(null);
			return ResultInfo.success(selectByMobileNumber);
		}
	}

	@Override
	public ResultInfo addUserByWechat(HttpServletRequest request, RegistUserModel user) {
		String  result=null;
		//获取微信授权token
		String accessToken = user.getAccessToken();
		String openId=user.getOpenId();
		// 拼接url
		StringBuilder url = new StringBuilder();
		// access_token=ACCESS_TOKEN&openid=OPENID
		url.append("https://api.weixin.qq.com/sns/userinfo?");
		// appid
		url.append("access_token=" + accessToken);
		url.append("&" + "openid=" + openId);

		try {
			result = HttpClientUtils.get(url.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(result!=null){
			JSONObject obj = new JSONObject();
			obj = obj.parseObject(result);
//			String nickname  = obj.getString("nickname");
//			String headimgurl =obj.getString("headimgurl");
			String openid=obj.getString("openid");
			//查微信类型的identityType=，是否有注册过
			TbUserAuths byIdentifier = tbUserAuthsMapper.selectByIdentifier(openid, 2);
			//若为新登录(老用户新登录和新用户新登录)
			if(null==byIdentifier){
				TbUserInfo tbUserInfo = new TbUserInfo();
				tbUserInfo.setWechatOpenId(openid);
			/*	// 插info表
				tbUserInfo.setId(SequenceGenerator.sequence());
				tbUserInfo.setRegisterTime(new Date());
				tbUserInfo.setNickname(nickname);
				tbUserInfo.setAvatar(headimgurl);
				tbUserInfo.setPassword(PasswordUtil.md5("123456"));
				tbUserInfo.setAccountStatus("0");
				tbUserInfo.setWechat(openid);
				tbUserInfoMapper.insert(tbUserInfo);
				String id = tbUserInfo.getId();
				TbUserAuths tbUserAuths = new TbUserAuths();
				//插入auth表
				tbUserAuths.setUserId(id);
				tbUserAuths.setIdentifier(openid);
				tbUserAuths.setIdentityType(2);
				tbUserAuths.setId(SequenceGenerator.sequence());
				tbUserAuthsMapper.insert(tbUserAuths);*/
				return ResultInfo.success("New",tbUserInfo);
			}else{
				//老用户老登陆
				String token="";
				String id = byIdentifier.getUserId();
				TbUserInfo selectByInfoId = tbUserInfoMapper.selectByInfoId(id);
				//登录信息
				selectByInfoId.setLoginTime(new Date());
				selectByInfoId.setOnilneStatus(true);

				token = JwtUtil.sign(id, "user");
				redisUtil.set(Constant.PREFIX_SHIRO_REFRESH_TOKEN + id, token, accessTokenExpireTime);

				redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByInfoId));
				selectByInfoId.setToken(token);
				selectByInfoId.setPassword(null);
				return ResultInfo.success(selectByInfoId);
			}
		}else{
			return ResultInfo.fail("扫码超时,请重新扫码");
		}
	}


	/**
	 * 发送短信验证码
	 *
	 * @param phoneNum
	 *            需要发送给哪个手机号码
	 * @return 验证码，若为000000，则发送失败
	 */
	private String sendMessageCode(String phoneNum, Integer type) {
		String key = null;
		if (type == 1) {
			key = Constant.PREFIX_lOCALREGISTCODE_SESSION;
		}
		if (type == 2) {
			key = Constant.PREFIX_WXREGISTCODE_SESSION;
		}
		if (type == 3) {
			key = Constant.PREFIX_QQREGISTCODE_SESSION;
		}
		if (type == 4) {
			key = Constant.PREFIX_MOBILEREGISTCODE_SESSION;
		}
		if (type == 5) {
			key = Constant.PREFIX_FORGETCODE_SESSION;
		}
		try {
			// 生成的6位数赋值验证码
			String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";
			redisUtil.set(key + phoneNum, strTemp, 60 * 5);
			Object validateCode = redisUtil.get(key + phoneNum);
			System.out.println(validateCode.toString());
			// 数组具体的元素个数和模板中变量个数必须一致
			// 比如你模板中需要填写验证码和有效时间，{1}，{2}
			// 那你这里的参数就应该填两个
			String[] params = { strTemp, "1" };
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			// 签名参数未提供或者为空时，会使用默认签名发送短信
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, smsSign, "", "");
			System.out.println("result = " + result);
			// 发送成功则给验证码赋值
			if (result.result == 0) {
				str = strTemp;
			}
		} catch (HTTPException e1) {
			// HTTP响应码错误
			e1.printStackTrace();
		} catch (JSONException e2) {
			// json解析错误
			e2.printStackTrace();
		} catch (IOException e3) {
			// 网络IO错误
			e3.printStackTrace();
		}
		return str;
	}


}