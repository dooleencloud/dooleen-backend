package com.dooleen.service.biz.plan.iteration.entity;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-28 22:02:01
 * @Description : 迭代计划管理实体
 * @Author : name
 * @Update : 2021-04-28 22:02:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_plan_iteration")
@ApiModel
@ToString(callSuper = true)
public class BizPlanIterationEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true,  isRecordHistory = true)
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
	* 项目编号
	*/
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 2)
	@Length(max=30,message="项目编号长度不能大于30")
	@NotBlank(message = "项目编号不能为空")
	private String projectNo;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	@NotBlank(message = "项目名称不能为空")
	private String projectName;	  
   
	
	/**
	* 迭代名称
	*/
	@DataName(name = "迭代名称", isRecordHistory = true)
	@ApiModelProperty(value = "迭代名称", position = 4)
	@Length(max=50,message="迭代名称长度不能大于50")
	@NotBlank(message = "迭代名称不能为空")
	private String iterationName;	  
   
	
	/**
	* 迭代描述
	*/
	@DataName(name = "迭代描述", isRecordHistory = true)
	@ApiModelProperty(value = "迭代描述", position = 5)
	@Length(max=2000,message="迭代描述长度不能大于2000")
	@NotBlank(message = "迭代描述不能为空")
	private String iterationDesc;	  
   
	
	/**
	* 里程碑模板ID
	*/
	@DataName(name = "里程碑模板ID", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑模板ID", position = 6)
	@Length(max=20,message="里程碑模板ID长度不能大于20")
	private String milestoneTemplateId;	  
   
	
	/**
	* 里程碑模板名称
	*/
	@DataName(name = "里程碑模板名称", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑模板名称", position = 7)
	@Length(max=50,message="里程碑模板名称长度不能大于50")
	private String milestoneTemplateName;	  
   
	
	/**
	* 版本类型
	*/
	@DataName(name = "版本类型", isRecordHistory = true)
	@ApiModelProperty(value = "版本类型", position = 8)
	@Length(max=30,message="版本类型长度不能大于30")
	private String versionType;	  
   
	
	/**
	* 发布频率
	*/
	@DataName(name = "发布频率", isRecordHistory = true)
	@ApiModelProperty(value = "发布频率", position = 9)
	@Length(max=10,message="发布频率长度不能大于10")
	private String deployFrequency;	  
   
	
	/**
	* 版本窗口日期
	*/
	@DataName(name = "版本窗口日期", isRecordHistory = true)
	@ApiModelProperty(value = "版本窗口日期", position = 10)
	@Length(max=20,message="版本窗口日期长度不能大于20")
	private String versionWindowDate;	  
   
	
	/**
	* 迭代开始日期
	*/
	@DataName(name = "迭代开始日期", isRecordHistory = true)
	@ApiModelProperty(value = "迭代开始日期", position = 11)
	@Length(max=20,message="迭代开始日期长度不能大于20")
	private String iterationBeginDate;	  
   
	
	/**
	* 迭代结束日期
	*/
	@DataName(name = "迭代结束日期", isRecordHistory = true)
	@ApiModelProperty(value = "迭代结束日期", position = 12)
	@Length(max=20,message="迭代结束日期长度不能大于20")
	private String iterationEndDate;	  
 }
