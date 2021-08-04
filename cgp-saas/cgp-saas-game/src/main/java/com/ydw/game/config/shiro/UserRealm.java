package com.ydw.game.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ydw.game.config.jwt.JwtToken;
import com.ydw.game.config.jwt.JwtUtil;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.redis.RedisUtil;


/**
 * 自定义Realm
 * @author Wang926454
 * @date 2018/8/30 14:10
 */
@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private RedisUtil redisUtil;

    @Value("${shiro.accessTokenExpireTime}")
    private Long accessTokenExpireTime;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        //判断是什么用户
//        String role = JwtUtil.getClaim(principalCollection.toString(), Constant.ROLE);
//        // 添加角色
//        simpleAuthorizationInfo.addRole(role);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
     // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken
        if (JwtUtil.verify(token) && redisUtil.hasKey(Constant.PREFIX_SHIRO_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String redisToken = redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (token.equals(redisToken)) {
            	//刷新token时效
            	redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + account, token, accessTokenExpireTime);
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
