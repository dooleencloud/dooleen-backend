package com.dooleen.service.system.tool.dict.attr.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-29 23:28:51
 * @Description : 系统标准定语管理实体
 * @Author : name
 * @Update : 2020-05-29 23:28:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tool_dict_attr")
@ApiModel
@ToString(callSuper = true)
public class SysToolDictAttrEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true)
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
	* 定语缩写名
	*/
	@DataName(name = "定语缩写名", isRecordHistory = true)
	@ApiModelProperty(value = "定语缩写名", position = 2)
	@NotBlank(message = "定语缩写名不能为空")
	private String attrName;	  
   
	/**
	* 定语全称
	*/
	@DataName(name = "定语全称", isRecordHistory = true)
	@ApiModelProperty(value = "定语全称", position = 3)
	@NotBlank(message = "定语全称不能为空")
	private String attrFullName;	  
   
	/**
	* 定语说明
	*/
	@DataName(name = "定语说明", isRecordHistory = true)
	@ApiModelProperty(value = "定语说明", position = 4)
	@NotBlank(message = "定语说明不能为空")
	private String attrComment;	  
   
	/**
	* 申请系统
	*/
	@DataName(name = "申请系统", isRecordHistory = true)
	@ApiModelProperty(value = "申请系统", position = 5)
	@NotBlank(message = "申请系统不能为空")
	private String applySystem;	  
   
	/**
	* 申请应用
	*/
	@DataName(name = "申请应用", isRecordHistory = true)
	@ApiModelProperty(value = "申请应用", position = 6)
	@NotBlank(message = "申请应用不能为空")
	private String applyApp;	  
   
	/**
	* 申请用户
	*/
	@DataName(name = "申请用户", isRecordHistory = true)
	@ApiModelProperty(value = "申请用户", position = 7)
	@NotBlank(message = "申请用户不能为空")
	private String applyUserName;	  
 }
