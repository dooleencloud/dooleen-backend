package com.dooleen.service.system.form.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @CreateDate : 2020-05-23 17:56:43
 * @Description : 系统动态表单管理实体
 * @Author : name
 * @Update : 2020-05-23 17:56:43
 */
@Data
@TableName("sys_dynamic_form")
@ApiModel
@ToString(callSuper = true)
public class SysDynamicFormEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	public String id; 

	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 6)
	@Length(max=10,message="租户ID长度不能大于10")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;

	/**
	* 业务类型
	*/
	@DataName(name = "业务类型")
	@ApiModelProperty(value = "业务类型", position = 6)
	@Length(max=10,message="业务类型长度不能大于10")
	@NotBlank(message = "业务类型不能为空")
	private String bizType;

	/**
	* 业务名称
	*/
	@DataName(name = "业务名称")
	@ApiModelProperty(value = "业务名称", position = 6)
	@Length(max=50,message="业务名称长度不能大于50")
	@NotBlank(message = "业务名称不能为空")
	private String bizName;

	/**
	* 业务编码
	*/
	@DataName(name = "业务编码")
	@ApiModelProperty(value = "业务编码", position = 6)
	@Length(max=30,message="业务编码长度不能大于30")
	@NotBlank(message = "业务编码不能为空")
	private String bizCode;

	/**
	* 表单编码
	*/
	@DataName(name = "表单编码")
	@ApiModelProperty(value = "表单编码", position = 6)
	@Length(max=30,message="表单编码长度不能大于30")
	private String formCode;

	/**
	* 表单名称
	*/
	@DataName(name = "表单名称")
	@ApiModelProperty(value = "表单名称", position = 6)
	@Length(max=50,message="表单名称长度不能大于50")
	private String formName;

	/**
	* 上级ID
	*/
	@DataName(name = "上级ID")
	@ApiModelProperty(value = "上级ID", position = 6)
	@Length(max=20,message="上级ID长度不能大于20")
	private String parentId;

	/**
	* 表单JSON
	*/
	@DataName(name = "表单JSON")
	@ApiModelProperty(value = "表单JSON", position = 6)
	@Length(max=5000,message="表单JSON长度不能大于5000")
	private String formJson;

	/**
	* 表单JSONV1
	*/
	@JSONField(serialize = false)
	@DataName(name = "表单JSONV1")
	@ApiModelProperty(value = "表单JSONV1", position = 6)
	@Length(max=5000,message="表单JSONV1长度不能大于5000")
	private String formJsonV1;

	/**
	* 表单JSONV2
	*/
	@JSONField(serialize = false)
	@DataName(name = "表单JSONV2")
	@ApiModelProperty(value = "表单JSONV2", position = 6)
	@Length(max=5000,message="表单JSONV2长度不能大于5000")
	private String formJsonV2;

	/**
	* 是否流程标志
	*/
	@DataName(name = "是否流程标志")
	@ApiModelProperty(value = "是否流程标志", position = 6)
	@Length(max=1,message="是否流程标志长度不能大于1")
	private String isFlowFlag;

	/**
	* 流程ID
	*/
	@DataName(name = "流程ID")
	@ApiModelProperty(value = "流程ID", position = 6)
	@Length(max=20,message="流程ID长度不能大于20")
	private String flowId;

	/**
	* 流程开始方式
	*/
	@DataName(name = "流程开始方式")
	@ApiModelProperty(value = "流程开始方式", position = 6)
	@Length(max=30,message="流程开始方式长度不能大于30")
	private String flowBeginWay;  
   
 }
