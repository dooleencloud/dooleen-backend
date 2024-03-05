package com.dooleen.common.core.mq.msg;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dooleen.common.core.mq.msg.email.EmailMsg;

import lombok.Data;

@Data
public class EmailMq implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 邮件消息
	 */
	private List<EmailMsg> emailMsgList;
	
	/**
	 * 消息模板内容
	 */
	private String msgTemplateContent;
	
	/**
	 * 创建时间
	 */
	private Date createDatetime;
}
