package com.dooleen.service.system.tool.dict.field.entity;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2020-05-29 23:28:56
 * @Description : 数据标准变量服务实体
 * @Author : name
 * @Update : 2020-05-29 23:28:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tool_dict_field")
@ApiModel
@ToString(callSuper = true)
public class SysToolDictFieldEntity extends BaseEntity implements Serializable  {

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
	* 字段名
	*/
	@DataName(name = "字段名", isRecordHistory = true)
	@ApiModelProperty(value = "字段名", position = 2)
	@NotBlank(message = "字段名不能为空")
	private String columnName;	  
   
	/**
	* 字段说明
	*/
	@DataName(name = "字段说明", isRecordHistory = true)
	@ApiModelProperty(value = "字段说明", position = 3)
	@NotBlank(message = "字段说明不能为空")
	private String columnComment;	  
   
	/**
	* 数据类型
	*/
	@DataName(name = "数据类型", isRecordHistory = true)
	@ApiModelProperty(value = "数据类型", position = 4)
	@NotBlank(message = "数据类型不能为空")
	private String dataType;	  
   
	/**
	* 字段长度
	*/
	@DataName(name = "字段长度", isRecordHistory = true)
	@ApiModelProperty(value = "字段长度", position = 5)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int length;	  
   
	/**
	* 小数点长度
	*/
	@DataName(name = "小数点长度", isRecordHistory = true)
	@ApiModelProperty(value = "小数点长度", position = 6)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int decimalLength;	  
   
	/**
	* 字段全名
	*/
	@DataName(name = "字段全名", isRecordHistory = true)
	@ApiModelProperty(value = "字段全名", position = 7)
	@NotBlank(message = "字段全名不能为空")
	private String columnFullName;	  
   
	/**
	* 申请系统
	*/
	@DataName(name = "申请系统", isRecordHistory = true)
	@ApiModelProperty(value = "申请系统", position = 8)
	@NotBlank(message = "申请系统不能为空")
	private String applySystem;	  
   
	/**
	* 申请应用
	*/
	@DataName(name = "申请应用", isRecordHistory = true)
	@ApiModelProperty(value = "申请应用", position = 9)
	@NotBlank(message = "申请应用不能为空")
	private String applyApp;	  
   
	/**
	* 申请人
	*/
	@DataName(name = "申请人", isRecordHistory = true)
	@ApiModelProperty(value = "申请人", position = 10)
	@NotBlank(message = "申请人不能为空")
	private String applyUserName;	  
 }
