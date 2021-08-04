package com.ydw.advert.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ydw.advert.config.jwt.JwtUtil;
import com.ydw.advert.model.constant.Constant;
import com.ydw.advert.model.vo.Enterprise;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.redis.RedisUtil;
import com.ydw.advert.service.ILoginService;
import com.ydw.advert.util.HttpUtil;
import com.ydw.advert.util.SequenceGenerator;

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
	private RedisUtil redisUtil;
	
	@Value("${shiro.accessTokenExpireTime}")
	private Integer accessTokenExpireTime;
	
	@Value("${url.paasApi}")
	private String paasUrl;

	@Value("${paas.identification}")
	private String identification;

	@Value("${paas.secretKey}")
	private String secretKey;

	@Override
	public ResultInfo login(Enterprise enterprise) {
	    if (!identification.equals(enterprise.getIdentification()) || !secretKey.equals(enterprise.getSecretKey())){
	        return ResultInfo.fail("认证信息错误！");
        }
		//无用户信息，这里先用随机数代替
		String account = SequenceGenerator.sequence();
		String saasToken = JwtUtil.sign(account, enterprise.getIdentification(), enterprise.getSecretKey());
		redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + account, saasToken, accessTokenExpireTime);
		return ResultInfo.success("登录认证成功！", saasToken);
	}

	@Override
	public ResultInfo refreshToken(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        Object o = redisUtil.get(account);
        if (o != null){
            redisUtil.expire(Constant.PREFIX_SHIRO_TOKEN + account, accessTokenExpireTime);
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
	}
}