package com.dooleen.service.biz.plan.milestone.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2021-04-28 22:02:22
 * @Description : 里程碑信息维护实体
 * @Author : name
 * @Update : 2021-04-28 22:02:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_plan_milestone")
@ApiModel
@ToString(callSuper = true)
public class BizPlanMilestoneEntity  extends BaseEntity implements Serializable  {

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
	* 迭代编号
	*/
	@DataName(name = "迭代编号", isRecordHistory = true)
	@ApiModelProperty(value = "迭代编号", position = 4)
	@Length(max=30,message="迭代编号长度不能大于30")
	@NotBlank(message = "迭代编号不能为空")
	private String iterationNo;	  
   
	
	/**
	* 迭代名称
	*/
	@DataName(name = "迭代名称", isRecordHistory = true)
	@ApiModelProperty(value = "迭代名称", position = 5)
	@Length(max=50,message="迭代名称长度不能大于50")
	@NotBlank(message = "迭代名称不能为空")
	private String iterationName;	  
   
	
	/**
	* 里程碑名称
	*/
	@DataName(name = "里程碑名称", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑名称", position = 6)
	@Length(max=50,message="里程碑名称长度不能大于50")
	@NotBlank(message = "里程碑名称不能为空")
	private String milestoneName;	  
   
	
	/**
	* 里程碑描述
	*/
	@DataName(name = "里程碑描述", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑描述", position = 7)
	@Length(max=2000,message="里程碑描述长度不能大于2000")
	private String milestoneDesc;	  
   
	
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号", isRecordHistory = true)
	@ApiModelProperty(value = "排序序号", position = 8)
    @DecimalMax(value="9999999999",message="排序序号不能大于9999999999")
	@DecimalMin(value="0",message="排序序号不能小于0")
	private int orderSeq;	  
   
	
	/**
	* 计划开始日期
	*/
	@DataName(name = "计划开始日期", isRecordHistory = true)
	@ApiModelProperty(value = "计划开始日期", position = 9)
	@Length(max=20,message="计划开始日期长度不能大于20")
	@NotBlank(message = "计划开始日期不能为空")
	private String planBeginDate;	  
   
	
	/**
	* 计划结束日期
	*/
	@DataName(name = "计划结束日期", isRecordHistory = true)
	@ApiModelProperty(value = "计划结束日期", position = 10)
	@Length(max=20,message="计划结束日期长度不能大于20")
	@NotBlank(message = "计划结束日期不能为空")
	private String planEndDate;



	/**
	 * 投入用时
	 */
	@DataName(name = "投入用时", isRecordHistory = true)
	@ApiModelProperty(value = "投入用时", position = 6)
	@Length(max=100,message="投入用时长度不能大于100")
	private String investmentTime;


	/**
	 * 进度
	 */
 	@DataName(name = "进度", isRecordHistory = true)
	@ApiModelProperty(value = "进度", position = 6)
	@Length(max=11,message="进度长度不能大于11")
 	private String progress;
	/**
	 * 颜色
	 */
	@DataName(name = "颜色", isRecordHistory = true)
	@ApiModelProperty(value = "颜色", position = 6)
	@Length(max=50,message="颜色长度不能大于50")
	private String color;


	/**
	 * 添加计划标志
	 */
	@DataName(name = "添加计划标志", isRecordHistory = true)
	@ApiModelProperty(value = "添加计划标志", position = 6)
	@Length(max=1,message="添加计划标志长度不能大于1")
	private String addPlanFlag;



	/**
	 * 宽度值
	 */
	@DataName(name = "宽度值", isRecordHistory = true)
	@ApiModelProperty(value = "宽度值", position = 6)
	@Length(max=10,message="宽度值长度不能大于10")
		private String widthValue;

	/**
	 * 高度值
	 */
	@DataName(name = "高度值", isRecordHistory = true)
	@ApiModelProperty(value = "高度值", position = 6)
	@Length(max=10,message="高度值长度不能大于10")
	private String heightValue;



	/**
	 * 拖动标志
	 */
	@DataName(name = "拖动标志", isRecordHistory = true)
	@ApiModelProperty(value = "拖动标志", position = 6)
	@Length(max=1,message="拖动标志长度不能大于1")
	private String dragFlag;



	/**
	 * 标志名称
	 */
	@DataName(name = "标志名称", isRecordHistory = true)
	@ApiModelProperty(value = "标志名称", position = 6)
	@Length(max=50,message="标志名称长度不能大于50")
	private String flagName;



	/**
	 * 标志样式名称
	 */
	@DataName(name = "标志样式名称", isRecordHistory = true)
	@ApiModelProperty(value = "标志样式名称", position = 6)
	@Length(max=50,message="标志样式名称长度不能大于50")
	private String flagStyleName;

	@TableField(exist = false)
	private List planList;
 }
