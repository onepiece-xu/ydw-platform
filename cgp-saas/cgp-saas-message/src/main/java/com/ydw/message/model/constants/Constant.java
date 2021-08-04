package com.ydw.message.model.constants;

public class Constant {
	
	/**
	 * 请求头token
	 */
	public static String AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * 维护token与极光推送registrationId的关系
     * token与registrationId是一对一的关系
     */
	public static String STR_TOKEN_REGISTRATIONID = "str_token_registrationId:{0}";

    public static String STR_TOKEN_REGISTRATIONID_PATTERN = "str_token_registrationId*";

    /**
     * 维护token与ws的sessionId的关系
     * token与sessionId是一对多的关系
     */
	public static String SET_TOKEN_SESSIONIDS = "set_token_sessionIds:{0}";

    public static String SET_TOKEN_SESSIONIDS_PATTERN = "set_token_sessionIds*";

	/**
	 * token绑定关系扫描任务
	 */
	public static String TOKEN_BINED_SCAN_JOB = "token_bined_scan_job";
}
