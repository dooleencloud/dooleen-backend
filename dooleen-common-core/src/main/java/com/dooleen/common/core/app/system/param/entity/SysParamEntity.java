package com.dooleen.common.core.app.system.param.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
 * @CreateDate : 2020-06-14 16:30:33
 * @Description : 系统参数管理实体
 * @Author : name
 * @Update : 2020-06-14 16:30:33
 */
@Data
@TableName("sys_param")
@ApiModel
@ToString(callSuper = true)
public class SysParamEntity extends BaseEntity implements Serializable  {

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
	* 参数类型
	*/
	@DataName(name = "参数类型")
	@ApiModelProperty(value = "参数类型", position = 2)
	@NotBlank(message = "参数类型不能为空")
	private String paramType;	  
   
	/**
	* 参数键
	*/
	@DataName(name = "参数键")
	@ApiModelProperty(value = "参数键", position = 3)
	@NotBlank(message = "参数键不能为空")
	private String paramKey;	  
   
	/**
	* 参数值
	*/
	@DataName(name = "参数值")
	@ApiModelProperty(value = "参数值", position = 4)
	@NotBlank(message = "参数值不能为空")
	private String paramValue;	  
   
	/**
	* 参数值1
	*/
	@DataName(name = "参数值1")
	@ApiModelProperty(value = "参数值1", position = 5)
	private String paramValueOne;	  
   
	/**
	* 参数值2
	*/
	@DataName(name = "参数值2")
	@ApiModelProperty(value = "参数值2", position = 6)
	private String paramValueTow;	  
   
	/**
	* 参数名
	*/
	@DataName(name = "参数名")
	@ApiModelProperty(value = "参数名", position = 7)
	@NotBlank(message = "参数名不能为空")
	private String paramName;	  
   
	/**
	* 备注
	*/
	@DataName(name = "备注")
	@ApiModelProperty(value = "备注", position = 8)
	private String remark;	  
 }
