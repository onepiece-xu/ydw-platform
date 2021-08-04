package com.ydw.netbar.model.constant;

public class Constant {
	
	/**
	 * paas层服务请求头token
	 */
	public static String PAAS_AUTHORIZATION_HEADER_NAME = "Authorization";
	
	/**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";
    
    /**
     * JWT-role:
     */
    public final static String ROLE = "role";

    /**
     * JWT-identification:
     */
    public final static String IDENTIFICATION = "identification";

    /**
     * JWT-secretKey:
     */
    public final static String SECRETKEY = "secretKey";

    /**
     * JWT-time
     */
    public final static String TIME = "time";
    
    /**
     * redis-key-前缀-shiro:token:
     */
    public final static String PREFIX_SHIRO_TOKEN = "shiro:token:";
    
    /**
     * paas和saas的token的对应关系
     */
    public final static String TOKEN_MAP_PAAS_SAAS = "token_map_paas_saas";
    
    /**
     * saas和paas的token的对应关系
     */
    public final static String TOKEN_MAP_SAAS_PAAS = "token_map_saas_paas";
	
	/**
	 * 申请设备参数配置
	 * @author xulh
	 *
	 */
	public static class ApplyParamConfig{
		public static final String  CONNECT_TYPE_APK      = "apk";         ///客户端为apk类型
	    public static final String  CONNECT_TYPE_WEBRTC   = "webrtc";      ///客户端为webrtc类型

	    public static final Integer  SAAS_TYPE_ADVERT       = 0;        ///试玩 saas
	    public static final Integer  SAAS_TYPE_GAME       = 2;        ///云游戏
	    public static final Integer  SAAS_TYPE_NETBAR   = 1;    ///云电脑

	    public static final Integer  CLIENT_TYPE_MOBILE  = 0;             ///移动客户端
	    public static final Integer  CLIENT_TYPE_PC      = 1;             ///pc 客户端
	}
	
	/**
	 * 请求接口url
	 * @author xulh
	 *
	 */
	public static class Url{
		
		/**
		 * 登录url
		 */
		public static String URL_PAAS_LOGIN = "/cgp-paas-openapi/login/openLogin";
		
		/**
		 * 申请连接url
		 */
		public static String URL_PAAS_APPLY = "/cgp-paas-openapi/resource/apply";
		
		/**
		 * 退出排队url
		 */
		public static String URL_PAAS_QUEUEOUT = "/cgp-paas-openapi/resource/queueOut";
		
		/**
		 * 释放连接url
		 */
		public static String URL_PAAS_RELEASE = "/cgp-paas-openapi/resource/release";
		
		/**
		 * 获取连接详情url
		 */
		public static String URL_PAAS_CONNECTINFO = "/cgp-paas-openapi/resource/getConnectInfo";
		
		/**
		 * 挂机url
		 */
		public static String URL_PAAS_HANGUP = "/cgp-paas-openapi/resource/hangup";
		
		/**
		 * 添加定时任务url
		 */
		public static String URL_SCHEDULEJOB_ADDJOB = "/cgp-saas-schedulejob/scheduleJob/addScheduleJob";
		
		/**
		 * 开始计费url
		 */
		public static String URL_CHARGE_DOCHARGE = "/cgp-saas-charge/charge/doCharge";
		
		/**
		 * 结束计费url
		 */
		public static String URL_CHARGE_ENDCHARGE = "/cgp-saas-charge/charge/endCharge";
		
		/**
		 * 释放连接url
		 */
		public static String URL_NETBAR_RELEASE = "/cgp-saas-netbar/resource/release";
	}

	
}
