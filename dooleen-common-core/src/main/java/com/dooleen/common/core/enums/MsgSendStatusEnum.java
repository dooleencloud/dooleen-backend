package com.dooleen.common.core.enums;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-24 00:05:24
 * @Description : 消息发送状态枚举类
 * @Author : zoujin
 * @Update: 2020-07-24 00:05:24
 */
public enum MsgSendStatusEnum {
	
	// 发送成功
	SUCCESS("0","发送成功"),
	
	// 邮件服务器域名异常
	SERVER_DOMAIN_ERROR("1","邮件服务器域名异常"),
	
	// 编码异常
	ENCODE_ERROR("2","编码异常"),
	
	// 邮件内容异常
	CONTENT_ERROR("3","邮件内容异常");
	
	private final String value;
	private final String label;
	
	MsgSendStatusEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
}
