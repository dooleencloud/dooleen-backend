package com.dooleen.common.core.app.system.msg.log.entity;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-24 17:47:37
 * @Description : 消息日志管理实体
 * @Author : name
 * @Update : 2020-07-24 17:47:37
 */
@Data
@TableName("sys_msg_log")
@ApiModel
@ToString(callSuper = true)
public class SysMsgLogEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	
	/**
	* 消息类型
	*/
	@DataName(name = "消息类型")
	@ApiModelProperty(value = "消息类型", position = 2)
	@Length(max=30,message="消息类型长度不能大于30")
	@NotBlank(message = "消息类型不能为空")
	private String msgType;	  
   
	
	/**
	* 收件人姓名列表
	*/
	@DataName(name = "收件人姓名列表")
	@ApiModelProperty(value = "收件人姓名列表", position = 3)
	@Length(max=2000,message="收件人姓名列表长度不能大于2000")
	@NotBlank(message = "收件人姓名列表不能为空")
	private String receiverNameList;	  
   
	
	/**
	* 收件人邮箱列表
	*/
	@DataName(name = "收件人邮箱列表")
	@ApiModelProperty(value = "收件人邮箱列表", position = 4)
	@Length(max=2000,message="收件人邮箱列表长度不能大于2000")
	@NotBlank(message = "收件人邮箱列表不能为空")
	private String receiverEmailList;	  
   
	
	/**
	* 抄送人姓名列表
	*/
	@DataName(name = "抄送人姓名列表")
	@ApiModelProperty(value = "抄送人姓名列表", position = 5)
	@Length(max=2000,message="抄送人姓名列表长度不能大于2000")
	private String ccpNameList;	  
   
	
	/**
	* 抄送人邮箱列表
	*/
	@DataName(name = "抄送人邮箱列表")
	@ApiModelProperty(value = "抄送人邮箱列表", position = 6)
	@Length(max=2000,message="抄送人邮箱列表长度不能大于2000")
	private String ccpEmailList;	  
   
	
	/**
	* 密送人姓名列表
	*/
	@DataName(name = "密送人姓名列表")
	@ApiModelProperty(value = "密送人姓名列表", position = 7)
	@Length(max=2000,message="密送人姓名列表长度不能大于2000")
	private String bccpNameList;	  
   
	
	/**
	* 密送人邮箱列表
	*/
	@DataName(name = "密送人邮箱列表")
	@ApiModelProperty(value = "密送人邮箱列表", position = 8)
	@Length(max=2000,message="密送人邮箱列表长度不能大于2000")
	private String bccpEmailList;	  
   
	
	/**
	* 消息主题
	*/
	@DataName(name = "消息主题")
	@ApiModelProperty(value = "消息主题", position = 9)
	@Length(max=1000,message="消息主题长度不能大于1000")
	@NotBlank(message = "消息主题不能为空")
	private String msgSubject;	  
   
	
	/**
	* 消息内容
	*/
	@DataName(name = "消息内容")
	@ApiModelProperty(value = "消息内容", position = 10)
	@Length(max=100,message="消息内容长度不能大于100")
	@NotBlank(message = "消息内容不能为空")
	private String msgContent;	  
   
	
	/**
	* 附件列表
	*/
	@DataName(name = "附件列表")
	@ApiModelProperty(value = "附件列表", position = 11)
	@Length(max=2000,message="附件列表长度不能大于2000")
	private String attList;	  
   
	
	/**
	* 消息发送时间
	*/
	@DataName(name = "消息发送时间")
	@ApiModelProperty(value = "消息发送时间", position = 12)
	@Length(max=100,message="消息发送时间长度不能大于100")
	@NotBlank(message = "消息发送时间不能为空")
	private String msgSendDatetime;	  
   
	
	/**
	* 发送状态
	*/
	@DataName(name = "发送状态")
	@ApiModelProperty(value = "发送状态", position = 13)
	@Length(max=30,message="发送状态长度不能大于30")
	@NotBlank(message = "发送状态不能为空")
	private String sendStatus;	  
   
	
	/**
	* 发送状态描述
	*/
	@DataName(name = "发送状态描述")
	@ApiModelProperty(value = "发送状态描述", position = 14)
	@Length(max=2000,message="发送状态描述长度不能大于2000")
	@NotBlank(message = "发送状态描述不能为空")
	private String sendStatusDesc;	  
   
	
	/**
	* 发送成功次数
	*/
	@DataName(name = "发送成功次数")
	@ApiModelProperty(value = "发送成功次数", position = 15)
    @DecimalMax(value="9999999999",message="发送成功次数不能大于9999999999")
	@DecimalMin(value="0",message="发送成功次数不能小于0")
	private int sendSuccessTimes;	  
   
	
	/**
	* 发送失败次数
	*/
	@DataName(name = "发送失败次数")
	@ApiModelProperty(value = "发送失败次数", position = 16)
    @DecimalMax(value="9999999999",message="发送失败次数不能大于9999999999")
	@DecimalMin(value="0",message="发送失败次数不能小于0")
	private int sendFailTimes;	  
 }
