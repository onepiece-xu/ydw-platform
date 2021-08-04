package com.ydw.admin.model.constant;

public class Constant {

	/**
	 * paas层服务请求头token
	 */
	public static String PAAS_AUTHORIZATION_HEADER_NAME = "Authorization";

	/**
	 *生成报表任务
     */
	public final static String GENERATE_DAILY_REPORT = "generateDailyReport";
	/**
	 *生成留存率报表任务
	 */
	public final static String GENERATE_REATAINED_RATE = "generateRetainedRate";

	/**
	 *生成活跃度表
	 */
	public final static String GENERATE_PERIOD_ACTIVITY = "generatePeriodActivity";
	/**
	 *生成排队和连接数统计
	 */
	public final static String GENERATE_CONNECTIONS_AND_QUEUES = "generateConnectionsAndQueues";
	/**
	 *生成用户点击统计
	 */
	public final static String GENERATE_USER_CLICK = "generateUserClick";

	/**
	 * 非法用户巡检
	 */
	public final static String ILLEGAL_USER_INSPECTION = "IllegalUserInspection";

	/**
	 * 清理用户任务
	 */
	public final static String CLEAR_MISSION = "clearMission";
}
