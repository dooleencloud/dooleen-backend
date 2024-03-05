package com.dooleen.common.core.mq.msg.email;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmailMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 租户Id
	 */
	private String tenantId;
	
	/**
	 * 收件人id集合
	 */
	private List<String> receiverIdList;
	
	/**
	 * 抄送人id集合
	 */
	private List<String> ccIdList;
	
	/**
	 * 密送人id集合
	 */
	private List<String> bccIdList;
	
	/**
	 * 收件人email集合
	 */
	private List<String> receiverEmailAddrs;
	
	/**
	 * 抄送人email集合
	 */
	private List<String> ccEmailAddrs;
	
	/**
	 * 密送人email集合
	 */
	private List<String> bccEmailAddrs;
	
	/**
	 * 邮件主题
	 */
	private String emailSubject;
	
	/**
	 * 邮件内容
	 */
	private String emailContent;
	
	/**
	 * 附件集合
	 */
	private List<String> attachments;

}
