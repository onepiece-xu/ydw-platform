package com.ydw.platform.model.constant;

public class Constant {
	
	/**
	 * 请求头token
	 */
	public final static String AUTHORIZATION_HEADER_NAME = "Authorization";

	/**
	 * 请求头account
	 */
	public final static String ACCOUNT_HEADER_NAME = "account";
    
    /**
     * 离线
     */
    public final static String ZSET_OFFLINE = "zset_offline";

	/**
	 * 离线
	 */
	public final static String STR_CLOSING = "str_closing:{0}";

	/**
	 * 申请参数
	 */
	public final static String STR_APPLYPARAMETER = "str_applyparameter:{0}";

	/**
	 * 申请设备参数配置
	 * @author xulh
	 *
	 */
	public static class ApplyParamConfig{
		public static final String  CONNECT_TYPE_APK      = "apk";         ///客户端为apk类型
	    public static final String  CONNECT_TYPE_WEBRTC   = "webrtc";      ///客户端为webrtc类型

		public static final Integer  SAAS_TYPE_PLATFORM   = 0;        ///平台
	    public static final Integer  SAAS_TYPE_ADVERT     = 1;        ///试玩 saas
	    public static final Integer  SAAS_TYPE_GAME       = 2;        ///云游戏
	    public static final Integer  SAAS_TYPE_NETBAR	  = 3;    	  ///云电脑

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
		 * 获取集群信息
		 */
		public static String URL_PAAS_CLUSTERS = "/cgp-paas-resource/clusters/getClusters";

		/**
		 * 申请连接url
		 */
		public static String URL_PAAS_APPLY = "/cgp-paas-resource/connect/apply";

		/**
		 * 申请连接url
		 */
		public static String URL_PAAS_GETRANK = "/cgp-paas-resource/connect/getRank";

		/**
		 * 准备资源url
		 */
		public static String URL_PAAS_CANCELCONNECT = "/cgp-paas-resource/connect/cancelConnect";

		/**
		 * 准备资源url
		 */
		public static String URL_PAAS_PREPARE = "/cgp-paas-resource/connect/connect";

		/**
		 * 打开游戏
		 */
		public static String URL_PAAS_OPENAPP = "/cgp-paas-resource/connect/openApp";

		/**
		 * 打开游戏
		 */
		public static String URL_PAAS_CLOSEAPP = "/cgp-paas-resource/connect/closeApp";


		/**
		 * 获取连接详情
		 */
		public static String URL_PAAS_CONNECTDETAIL = "/cgp-paas-resource/connect/getConnectDetail";
		
		/**
		 * 退出排队url
		 */
		public static String URL_PAAS_QUEUEOUT = "/cgp-paas-resource/connect/queueOut";
		
		/**
		 * 释放连接url
		 */
		public static String URL_PAAS_RELEASE = "/cgp-paas-resource/connect/release";
		
		/**
		 * 获取连接详情url
		 */
		public static String URL_PAAS_USERCONNECTSTATUS = "/cgp-paas-resource/connect/getUserConnectStatus";
		
		/**
		 * 释放连接url
		 */
		public static String URL_PLATFROM_ABNORMALCONNECT = "/connect/abnormalConnect";

		/**
		 * 重连url
		 */
		public static String URL_PAAS_RECONNECT = "/cgp-paas-resource/connect/reconnect";

	}

    /**
     * 掉线扫描任务
     */
	public final static String OFFLINE_SCAN_JOB = "offlineScanJob";

	/**
	 * 异常连接任务
	 */
	public final static String ABNORMAL_CONNECT_JOB = "abnormalConnectJob";

	/**
	 * 挂机任务
	 */
	public final static String HANGUP_JOB = "hangupJob";

	/**
	 * 排队短信任务
	 */
	public final static String QUEUE_MESSAGE_JOB = "queueMessageJob";

	/**
	 * 查询设备空闲状态和启用状态数
	 */
	public final static String CLUSTER_STATUS = "/cgp-paas-admin/v1/clusters/getClusterStatus";
}
