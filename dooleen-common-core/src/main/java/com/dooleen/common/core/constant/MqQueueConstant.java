package com.dooleen.common.core.constant;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-08-14 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:zoujin
 * @Update:
 */
public class MqQueueConstant {

    /**
     * 操作日志队列
     */
	public static final String LOG_QUEUE = "log_queue";

    /**
     * 历史记录新增队列
     */
	public static final String HISTORY_ADD_QUEUE = "history_add_queue";

	/**
	 * 历史记录修改队列
	 */
	public static final String HISTORY_UPDATE_QUEUE = "history_update_queue";

    /**
     * 发送邮件队列
     */
	public static final String EMAIL_QUEUE = "email_queue";

	/**
	 * 发送微信队列
	 */
	public static final String WECHAT_QUEUE = "wechat_queue";

	/**
	 * 发送短信队列
	 */
	public static final String SMS_QUEUE = "sms_queue";

    /**
     * 推送租户数据队列
     */
	public static final String PUSH_DATA_QUEUE = "push_data_queue";

	/**
	 * 推送APP数据队列
	 */
	public static final String PUSH_APP_MESSAGE = "push_app_message";


}

