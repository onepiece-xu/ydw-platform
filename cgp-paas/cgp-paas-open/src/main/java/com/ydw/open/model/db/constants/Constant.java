package com.ydw.open.model.db.constants;

/**
 * 常量
 * @author xulh
 * @date 2018/9/3 16:03
 */
public class Constant {

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    
    /**
     * redis-key-前缀    wx:session
     */
    public final static String PREFIX_WX_SESSION = "wx:session:";
    
    /**
     * 账号密码注册验证码
     */
    public final static String PREFIX_lOCALREGISTCODE_SESSION = "localRegistCode:session:";
    
    public final static String PREFIX_WXREGISTCODE_SESSION = "wxRegistCode:session:";

    public final static String PREFIX_QQREGISTCODE_SESSION = "qqRegistCode:session:";

    public final static String PREFIX_MOBILEREGISTCODE_SESSION = "mobileRegistCode:session:";

    public final static String PREFIX_FORGETCODE_SESSION = "forgetCode:session:";


    public static final String  API_QUEUE_NUM    = "/cgp-paas-schedule/queue/getAppQueueNum";           ///10.	获取各个应用的排队情况

    public static final String  API_CLEAN_QUEUE_CORPSE    = "/cgp-paas-schedule/queue/clearQueueCorpse";           ///11 清理游戏对应区服中的排队僵尸
    
    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-role:
     */
    public final static String ROLE = "role";
    
    /**
     * JWT-sessionKey:
     */
    public final static String SESSION_KEY = "session_key";

    /**
     * JWT-time
     */
    public final static String TIME = "time";
    
    /**
     * 用户信息
     */
    public final static String USER = "user:";
    
    /**
     * websocket连接在线人
     */
    public static final String WS_ONLINE = "ws:online";
    
    /**  
    * @ClassName: UserPayType  
    * @Description: 充值类型
    * @author xiaoleiguo
    * @date 2019年10月21日 下午6:39:16  
    *    
    */
    public enum UserPayType {
        ALIPAY,
        WEXPAY,
        LOCAL,
        ACTIVE
    }
    
    public enum UserRechargeType {
        PAY,
        LOCAL,
        ACTIVE
    }
    
    public enum CommonStatus {
        USABLE, //可用
        UNUSABLE,//不可用
        DELETE //删除
    }

    public final static String STR_MOCK_LOGIN = "str_mock_login";

    /**
     * paas层服务请求头token
     */
    public static String PAAS_AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * redis-key-前缀-shiro:token:
     */
    public final static String PREFIX_SHIRO_TOKEN = "shiro:token:";



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



    public static String OPEN_PLATFORM_USER = "platform_user:";

    public static String BACK_STAGE_USER = "back_stage_user:";
}
