package com.ydw.schedule.model.constant;

/**
 * redisKey常量
 * @author xulh
 * @date 2018/9/3 16:03
 */
public class RedisConstant {
	
	/**-------------------------------------schedule--------------------------------------*/
	
	/**
     * 在 节点和app组合key 中设备的集合 --边缘节点中安装了某一个app的所有设备
     */
    public final static String SCH_SET_CLUSTER_APP = "sch_set_cluster_app:{0}:{1}";
    
    /**
     * 在 节点 中可使用的设备的集合
     */
    public final static String SCH_ZSET_CLUSTER_IDLE = "sch_zset_cluster_idle:{0}";

    /**
     * 建立连接id和设备的对应关系
     */
    public final static String SCH_MAP_CONNECT_DEVICE = "sch_map_connect_device";
    
    /**
     * 建立设备和连接id的对应关系
     */
    public final static String SCH_MAP_DEVICE_CONNECT = "sch_map_device_connect";

    /**
     * 设备锁(获取设备后将其锁住)
     */
    public final static String SCH_LOCK_USE_DEVICE = "sch_lock_use_device:{0}";

    /**
     * 添加在 节点和app组合key 中设备的集合时加锁
     */
    public final static String SCH_LOCK_ADD_CLUSTER_UNUSED_DEVICE = "sch_lock_add_cluster_unused_device:{0}";
    
    /**-------------------------------------queue--------------------------------------*/

	/**
	 * 消费队列锁
	 */
	public static final String QUE_LOCK_CONSUME_CLUSTER = "que_lock_consume_cluster:{0}";

	/**
	 * 某一个集群的排队
	 */
	public static final String QUE_ZSET_CLUSTER = "que_zset_cluster:{0}";

	/**
	 * 排队keys
	 */
	public static final String QUE_ZSET_KEYS = "que_zset_cluster*";

    /**
     * 用户账号与排队对应关系
     */
    public static final String QUE_MAP_ACCOUNT_APPLY = "que_map_account_apply";

	/**
	 * 用户账号与连接的对应关系
	 */
	public static final String QUE_STR_ACCOUNT_CONNECT = "que_str_account_connect:{0}";
	
	/**
	 * 连接与用户账号的对应关系
	 */
	public static final String QUE_STR_CONNECT_ACCOUNT = "que_str_connect_account:{0}";

	/**
	 * 用户总排队列
	 */
	public static final String QUE_ZSET_GLOBAL = "que_zset_global";

	/**
	 * 用户申请的锁
	 */
	public static final String QUE_LOCK_ACCOUNT_APPLY = "que_lock_account_apply:{0}";

	/**
	 * 在 节点 中可使用的设备的survivor指针
	 */
	public final static String QUE_INT_CLUSTER_SURVIVORPOINT = "que_int_cluster_survivorpoint:{0}";
}
