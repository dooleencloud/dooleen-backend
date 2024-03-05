package com.dooleen.common.core.common.entity;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 发送邮件日志表
 * </p>
 */
@Data
@TableName("sys_msg_log")
@ApiModel
public class SysMsgLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	private String id;
	
	/**
	 * 租户ID
	 */
	private String tenantId;
	
	/**
	 * 收件人姓名
	 */
	private String receiverName;
	
	/**
	 * 消息类型
	 */
	private String msgType;
	
	/**
	 * 收件人邮箱地址
	 */
	private String receiverEmailAddress;
	
	/**
	 * 抄送人邮箱地址
	 */
	private String ccEmailAddress;
	
	/**
	 * 密送人邮箱地址
	 */
	private String bccEmailAddress;
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String content;
	
	/**
	 * 附件名称
	 */
	private String attachment;
	
	/**
	 * 邮件发送时间
	 */
	private String sendTime;
	
	/**
	 * 邮件发送状态
	 */
	private String status;
	
	/**
	 * 邮件发送状态描述
	 */
	private String statusDesc;
	
	/**
	 * 发送成功次数
	 */
	private Integer successNum;
	
	/**
	 * 发送失败次数
	 */
	private Integer failNum;
	
	private String createDatetime;
	
	private String updateDatetime;
}
