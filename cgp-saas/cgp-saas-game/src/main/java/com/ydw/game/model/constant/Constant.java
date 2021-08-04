package com.ydw.game.model.constant;

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
     * JWT-saas:
     */
    public final static String SAAS = "saas";

    /**
     * JWT-time
     */
    public final static String TIME = "time";
    
    /**
     * redis-key-前缀-shiro:token:
     */
    public final static String PREFIX_SHIRO_TOKEN = "shiro:token:";
    
    /**
     * saas和paas的token的对应关系
     */
    public final static String TOKEN_SAAS_PAAS = "token_saas_paas:{0}";
    
    /**
     * saas和paas的token的对应关系
     */
    public final static String TOKEN_PAAS = "token_paas";
    
    /**
     * 在线
     */
    public final static String SET_WS_ONLINE = "set_ws_online_{0}";
    
    /**
     * 离线
     */
    public final static String ZSET_WS_OFFLINE = "zset_ws_offline";
	
	/**
	 * 用户信息
	 */
	public final static String USER = "user:";
	
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
		 * 用户登录paas平台url
		 */
		public static String URL_PAAS_USER_LOGIN = "/cgp-paas-authentication/login/userLogin";
		
		/**
		 * 用户登录paas平台url
		 */
		public static String URL_PAAS_ENTERPRISE_LOGIN = "/cgp-paas-authentication/login/enterpriseLogin";
		
		/**
		 * 申请连接url
		 */
		public static String URL_PAAS_APPLY = "/cgp-paas-resource/connect/apply";
		
		/**
		 * 退出排队url
		 */
		public static String URL_PAAS_QUEUEOUT = "/cgp-paas-schedule/queue/queueOut";
		
		/**
		 * 释放连接url
		 */
		public static String URL_PAAS_RELEASE = "/cgp-paas-resource/connect/release";
		
		/**
		 * 获取连接详情url
		 */
		public static String URL_PAAS_USERCONNECTSTATUS = "/cgp-paas-resource/connect/getUserConnectStatus";
		
		/**
		 * 获取连接详情url
		 */
		public static String URL_PAAS_MESSAGE = "/cgp-paas-message/ws";
		
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
		public static String URL_GAME_ABNORMALCONNECT = "/cgp-saas-game/connect/abnormalConnect";


	}
	public final static String STR_MOCK_LOGIN = "str_mock_login";


	/**
	 * 账号密码注册验证码
	 */
	public final static String PREFIX_lOCALREGISTCODE_SESSION = "localRegistCode:session:";

	public final static String PREFIX_WXREGISTCODE_SESSION = "wxRegistCode:session:";

	public final static String PREFIX_QQREGISTCODE_SESSION = "qqRegistCode:session:";

	public final static String PREFIX_MOBILEREGISTCODE_SESSION = "mobileRegistCode:session:";

	public final static String PREFIX_FORGETCODE_SESSION = "forgetCode:session:";

	public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
}
