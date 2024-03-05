package com.dooleen.service.system.tool.dict.root.entity;

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
 * @CreateDate : 2020-05-30 14:12:02
 * @Description : 系统标准词根管理实体
 * @Author : name
 * @Update : 2020-05-30 14:12:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tool_dict_root")
@ApiModel
@ToString(callSuper = true)
public class SysToolDictRootEntity extends BaseEntity implements Serializable  {

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
	* 词根类型
	*/
	@DataName(name = "词根类型")
	@ApiModelProperty(value = "词根类型", position = 2)
	@NotBlank(message = "词根类型不能为空")
	private String rootType;	  
   
	/**
	* 词根名称
	*/
	@DataName(name = "词根名称")
	@ApiModelProperty(value = "词根名称", position = 3)
	@NotBlank(message = "词根名称不能为空")
	private String rootName;	  
   
	/**
	* 字段类型
	*/
	@DataName(name = "字段类型")
	@ApiModelProperty(value = "字段类型", position = 4)
	@NotBlank(message = "字段类型不能为空")
	private String dataType;	  
   
	/**
	* 字段长度
	*/
	@DataName(name = "字段长度")
	@ApiModelProperty(value = "字段长度", position = 5)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int length;	  
   
	/**
	* 小数点长度
	*/
	@DataName(name = "小数点长度")
	@ApiModelProperty(value = "小数点长度", position = 6)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int decimalLength;	  
   
	/**
	* 词根英文全名
	*/
	@DataName(name = "词根英文全名")
	@ApiModelProperty(value = "词根英文全名", position = 7)
	@NotBlank(message = "词根英文全名不能为空")
	private String rootFullName;	  
   
	/**
	* 字段说明
	*/
	@DataName(name = "字段说明")
	@ApiModelProperty(value = "字段说明", position = 8)
	@NotBlank(message = "字段说明不能为空")
	private String comment;	  
   
	/**
	* 备注说明
	*/
	@DataName(name = "备注说明")
	@ApiModelProperty(value = "备注说明", position = 9)
	private String remark;	  
   
	/**
	* 枚举值
	*/
	@DataName(name = "枚举值")
	@ApiModelProperty(value = "枚举值", position = 10)
	private String enumValue;	  
   
	/**
	* 申请系统
	*/
	@DataName(name = "申请系统")
	@ApiModelProperty(value = "申请系统", position = 11)
	@NotBlank(message = "申请系统不能为空")
	private String applySystem;	  
   
	/**
	* 申请应用
	*/
	@DataName(name = "申请应用")
	@ApiModelProperty(value = "申请应用", position = 12)
	@NotBlank(message = "申请应用不能为空")
	private String applyApp;	  
   
	/**
	* 申请人姓名
	*/
	@DataName(name = "申请人姓名")
	@ApiModelProperty(value = "申请人姓名", position = 13)
	@NotBlank(message = "申请人姓名不能为空")
	private String applyUserName;	  
 }
