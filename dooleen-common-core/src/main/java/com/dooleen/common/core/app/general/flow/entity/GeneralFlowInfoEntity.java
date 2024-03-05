package com.dooleen.common.core.app.general.flow.entity;

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
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程信息管理实体
 * @Author : name
 * @Update : 2020-06-28 18:24:13
 */
@Data
@TableName("general_flow_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralFlowInfoEntity extends BaseEntity implements Serializable  {

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
	* 流程编号
	*/
	@DataName(name = "流程编号")
	@ApiModelProperty(value = "流程编号", position = 2)
	@Length(max=30,message="流程编号长度不能大于30")
	@NotBlank(message = "流程编号不能为空")
	private String flowNo;	  
   
	
	/**
	* 业务类型
	*/
	@DataName(name = "业务类型")
	@ApiModelProperty(value = "业务类型", position = 3)
	@Length(max=10,message="业务类型长度不能大于10")
	@NotBlank(message = "业务类型不能为空")
	private String bizType;	  
   
	
	/**
	* 流程名称
	*/
	@DataName(name = "流程名称")
	@ApiModelProperty(value = "流程名称", position = 4)
	@Length(max=50,message="流程名称长度不能大于50")
	@NotBlank(message = "流程名称不能为空")
	private String flowName;	  
   
	
	/**
	 * 流程类型
	 * 1-审批流程
	 * 2-工作流程
	*/
	@DataName(name = "流程类型")
	@ApiModelProperty(value = "流程类型", position = 5)
	@Length(max=10,message="流程类型长度不能大于10")
	@NotBlank(message = "流程类型不能为空")
	private String flowType;	  
   
	
	/**
	* 表单类型
	*/
	@DataName(name = "表单类型")
	@ApiModelProperty(value = "表单类型", position = 6)
	@Length(max=10,message="表单类型长度不能大于10")
	@NotBlank(message = "表单类型不能为空")
	private String formType;	  
   
	
	/**
	* 表单ID
	*/
	@DataName(name = "表单ID")
	@ApiModelProperty(value = "表单ID", position = 7)
	@Length(max=20,message="表单ID长度不能大于20")
	private String formId;	  


	 
	/**
	* 节点状态编号
	*/
	@DataName(name = "节点状态编号")
	@ApiModelProperty(value = "节点状态编号", position = 6)
	@Length(max=30,message="表单类型长度不能大于30")
	private String nodeStatusNo;

	/**
	* 节点阶段状态编号
	*/
	@DataName(name = "节点阶段状态编号")
	@ApiModelProperty(value = "节点阶段状态编号", position = 6)
	@Length(max=30,message="表单类型长度不能大于30")
	private String nodeStageStatusNo;

	/**
	* 角色类别
	*/
	@DataName(name = "角色类别")
	@ApiModelProperty(value = "角色类别", position = 6)
	@Length(max=30,message="表单类型长度不能大于30")
	private String roleCategory;
	
	/**
	* 角色类型
	*/
	@DataName(name = "角色类型")
	@ApiModelProperty(value = "角色类型", position = 7)
	@Length(max=20,message="角色类型长度不能大于20")
	private String roleType;



	/**
	 * 审批树名称
	 */
	@DataName(name = "审批树编号", isRecordHistory = true)
	@ApiModelProperty(value = "审批树编号", position = 6)
	@Length(max=20,message="审批树编号长度不能大于20")
	private String approveTreeNo;

	/**
	* 数据表列表
	*/
	@DataName(name = "数据表列表")
	@ApiModelProperty(value = "数据表列表", position = 6)
	@Length(max=2000,message="数据表列表长度不能大于2000")
	private String tableList;
 }
