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
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

	/**
	 * JWT-role:
	 */
	public final static String ROLE = "role";

    /**
     * JWT-time
     */
    public final static String TIME = "time";

	public final static String PAAS_TOKEN = "paas_token:{0}";

	/**
	 * redis-key-前缀-shiro:refresh_token:
	 */
	public final static String PREFIX_SHIRO_TOKEN = "shiro:token:";

	/**
	 * 用户信息
	 */
	public final static String USER = "user:";

	/**
	 * 账号密码注册验证码
	 */
	public final static String PREFIX_lOCALREGISTCODE_SESSION = "localRegistCode:session:";

	public final static String PREFIX_WXREGISTCODE_SESSION = "wxRegistCode:session:";

	public final static String PREFIX_QQREGISTCODE_SESSION = "qqRegistCode:session:";

	public final static String PREFIX_MOBILEREGISTCODE_SESSION = "mobileRegistCode:session:";

	public final static String PREFIX_MOBILELOGINCODE_SESSION = "mobileLoginCode:session:";

	public final static String PREFIX_FORGETCODE_SESSION = "forgetCode:session:";

	// 签名
	public final static String smsSign = "易点玩云游戏";
	// 短信应用 SDK AppID
	public final static int appid = 1400349137; // SDK AppID 以1400开头
	// 短信应用 SDK AppKey
	public final static String appkey = "125873d60b2cee73ca4161ba2fb74edb";

	public final static int RegistTemplateId = 730613;

	public final static int LoginTemplateId = 729402;

	public final static String WECHATAPPID = "wx3f1f9dcf6943559e";

	public final static String  WECHATSECRETKEY="fd02d0e4dbd2d429a3c49d565399cb50";

	public final static String WECHATAPPIDAndroid = "wxa09157e830f7a422";

	public final static String  WECHATSECRETKEYAndroid="65c46ce41843a95c7ab075d5de366e98";

	/**
	 *
	 */
	public final static String VERIFICATIONCODE = "verificationCode:";


	public final static String CANCEL = "cancel:";

	public final static int CANCEL_TEMPLATE_ID = 1028865;
}
