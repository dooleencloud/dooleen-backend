package com.dooleen.common.core.app.system.msg.config.entity;

import java.io.Serializable;

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
 * @CreateDate : 2020-07-13 19:12:18
 * @Description : 消息配置管理实体
 * @Author : name
 * @Update : 2020-07-13 19:12:18
 */
@Data
@TableName("sys_msg_config")
@ApiModel
@ToString(callSuper = true)
public class SysMsgConfigEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true)
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
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
	* 发送场景
	*/
	@DataName(name = "发送场景", isRecordHistory = true)
	@ApiModelProperty(value = "发送场景", position = 2)
	@Length(max=10,message="发送场景长度不能大于10")
	@NotBlank(message = "发送场景不能为空")
	private String sendScene;
   
	
	/**
	* 消息类型
	*/
	@DataName(name = "消息类型", isRecordHistory = true)
	@ApiModelProperty(value = "消息类型", position = 3)
	@Length(max=10,message="消息类型长度不能大于10")
	@NotBlank(message = "消息类型不能为空")
	private String msgType;
   
	/**
	* 模板ID
	*/
	@DataName(name = "模板ID", isRecordHistory = true)
	@ApiModelProperty(value = "模板ID", position = 4)
	@Length(max=100,message="模板ID长度不能大于100")
	private String templateId;



	/**
	 * 跳转地址
	 */
	@DataName(name = "跳转地址", isRecordHistory = true)
	@ApiModelProperty(value = "跳转地址", position = 6)
	@Length(max=100,message="跳转地址长度不能大于100")
	private String gotoAddress;

	/**
	 * 小程序ID
	 */
	@DataName(name = "小程序ID", isRecordHistory = true)
	@ApiModelProperty(value = "小程序ID", position = 6)
	@Length(max=20,message="小程序ID长度不能大于20")
	private String miniProgramId;

	/**
	 * 小程序地址
	 */
	@DataName(name = "小程序地址", isRecordHistory = true)
	@ApiModelProperty(value = "小程序地址", position = 6)
	@Length(max=100,message="小程序地址长度不能大于100")
	private String miniProgramAddress;
	
	/**
	* 消息模板内容
	*/
	@DataName(name = "消息模板内容", isRecordHistory = true)
	@ApiModelProperty(value = "消息模板内容", position = 5)
	private String msgTemplateContent;	  
   
	
	/**
	* 消息模板示例内容
	*/
	@DataName(name = "消息模板示例内容", isRecordHistory = true)
	@ApiModelProperty(value = "消息模板示例内容", position = 6)
	private String msgTemplateEgContent;	  
 }
