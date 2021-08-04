package com.ydw.authentication.model.constant;

/**
 * 常量
 * @author xulh
 * @date 2018/9/3 16:03
 */
public class Constant {
	
	/**
	 * paas层服务请求头token
	 */
	public static String PAAS_AUTHORIZATION_HEADER_NAME = "Authorization";

	/**
     * redis-key-前缀-shiro:token:
     */
    public final static String PREFIX_SHIRO_TOKEN = "shiro:token:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-identification:
     */
    public final static String IDENTIFICATION = "identification";

    /**
     * JWT-secretKey:
     */
    public final static String SECRETKEY = "secretKey";
    
    /**
     * JWT-saas:
     */
    public final static String SAAS = "saas";

    /**
     * JWT-time
     */
    public final static String TIME = "time";

	public static String OPEN_PLATFORM_USER = "platform_user:";

	public static String BACK_STAGE_USER = "back_stage_user:";
}
