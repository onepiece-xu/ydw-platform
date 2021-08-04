package com.ydw.user.model.db.constants;

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
     * 账号密码注册验证码
     */
    public final static String PREFIX_lOCALREGISTCODE_SESSION = "localRegistCode:session:";
    
    public final static String PREFIX_WXREGISTCODE_SESSION = "wxRegistCode:session:";

    public final static String PREFIX_QQREGISTCODE_SESSION = "qqRegistCode:session:";

    public final static String PREFIX_MOBILEREGISTCODE_SESSION = "mobileRegistCode:session:";

    public final static String PREFIX_FORGETCODE_SESSION = "forgetCode:session:";

    
    
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


    public static final String  API_QUEUE_NUM    = "/cgp-paas-schedule/queue/getAppQueueNum";           ///10.	获取各个应用的排队情况

    public static final String  API_CLEAN_QUEUE_CORPSE    = "/cgp-paas-schedule/queue/clearQueueCorpse";           ///11 清理游戏对应区服中的排队僵尸

    public static final String  API_MAINTAIN_CLEAN    ="/cgp-paas-schedule/maintain/clean";  //12.	内存数据的同步清理（慎用）
}
