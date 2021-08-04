package com.ydw.netbar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.netbar.config.jwt.JwtUtil;
import com.ydw.netbar.dao.EnterpriseMapper;
import com.ydw.netbar.dao.UserMapper;
import com.ydw.netbar.model.constant.Constant;
import com.ydw.netbar.model.db.Enterprise;
import com.ydw.netbar.model.db.User;
import com.ydw.netbar.model.vo.ResultInfo;
import com.ydw.netbar.service.ILoginService;
import com.ydw.netbar.util.RedisUtil;

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
	private RedisUtil redisUtil;
	
	@Value("${shiro.accessTokenExpireTime}")
	private Integer accessTokenExpireTime;

	@Override
	public ResultInfo localLogin(User user) {
		QueryWrapper<User> qw = new QueryWrapper<>();
		qw.eq("loginname", user.getLoginname());
		qw.eq("password", user.getPassword());
		User one = userMapper.selectOne(qw);
		Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
		String userId = one.getId();
		String token = JwtUtil.sign(one.getId(), enterprise.getIdentification(), enterprise.getSecretKey());
        redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userId, token, accessTokenExpireTime);
		return ResultInfo.success("登录认证成功！", token);
	}

}
