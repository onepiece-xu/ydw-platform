package com.ydw.game.config.jwt;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.util.Base64ConvertUtil;

/**
 * jwt信息处理
 * @author 
 *
 */
@Component
public class JwtUtil {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 过期时间改为从配置文件获取
     */
    @Value("${shiro.accessTokenExpireTime}")
    private static String accessTokenExpireTime;

    /**
     * JWT认证加密私钥(Base64加密)
     */
    @Value("${shiro.encryptJWTKey}")
    private static String encryptJWTKey;

    @Value("${shiro.accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${shiro.encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }

    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author 
     * @date 2018/8/31 9:05
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, Constant.ACCOUNT) + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new RuntimeException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     * @author 
     * @date 2018/9/7 16:54
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            LOGGER.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new RuntimeException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 生成签名
     * @param account
     * @param identification
     * @param secretKey
     * @return
     */
    public static String sign(String account, String role) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
//            // 此处过期时间是以毫秒为单位，所以乘以1000
//            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(Constant.ACCOUNT, account)
                    .withClaim(Constant.ROLE, role)
                    .withClaim(Constant.TIME, System.currentTimeMillis())
//                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new RuntimeException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }
    
    /**
     * 生成签名
     * @param account
     * @param identification
     * @param secretKey
     * @return
     */
    public static String sign(String account, String identification, String secretKey) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
//            // 此处过期时间是以毫秒为单位，所以乘以1000
//            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(Constant.ACCOUNT, account)
                    .withClaim(Constant.IDENTIFICATION, identification)
                    .withClaim(Constant.SECRETKEY, secretKey)
                    .withClaim(Constant.TIME, System.currentTimeMillis())
//                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new RuntimeException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }
    
}
