package com.dooleen.service.biz.apparch.demand.entity;

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
 * @CreateDate : 2020-08-25 17:29:32
 * @Description : 业务需求管理实体
 * @Author : name
 * @Update : 2020-08-25 17:29:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_demand")
@ApiModel
@ToString(callSuper = true)
public class BizApparchDemandEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true, isRecordHistory = true)
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
	* 项目ID
	*/
	@DataName(name = "项目ID", isRecordHistory = true)
	@ApiModelProperty(value = "项目ID", position = 2)
	@Length(max=30,message="项目ID长度不能大于30")
	private String projectId;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	
	/**
	* 需求名称
	*/
	@DataName(name = "需求名称", isRecordHistory = true)
	@ApiModelProperty(value = "需求名称", position = 4)
	@Length(max=50,message="需求名称长度不能大于50")
	@NotBlank(message = "需求名称不能为空")
	private String demandName;	  
   
	
	/**
	* 需求来源
	*/
	@DataName(name = "需求来源", isRecordHistory = true)
	@ApiModelProperty(value = "需求来源", position = 5)
	@Length(max=100,message="需求来源长度不能大于100")
	@NotBlank(message = "需求来源不能为空")
	private String demandSource;	  
   
	
	/**
	* 需求类别
	*/
	@DataName(name = "需求类别", isRecordHistory = true)
	@ApiModelProperty(value = "需求类别", position = 6)
	@Length(max=30,message="需求类别长度不能大于30")
	@NotBlank(message = "需求类别不能为空")
	private String demandCategory;	  
   
	
	/**
	* 需求文档地址
	*/
	@DataName(name = "需求文档地址", isRecordHistory = true)
	@ApiModelProperty(value = "需求文档地址", position = 7)
	@Length(max=2000,message="需求文档地址长度不能大于2000")
	private String demandFileAddress;	  
   
	
	/**
	* 需求描述
	*/
	@DataName(name = "需求描述", isRecordHistory = true)
	@ApiModelProperty(value = "需求描述", position = 8)
	@Length(max=2000,message="需求描述长度不能大于2000")
	private String demandDesc;	  
   
	
	/**
	* 优先级
	*/
	@DataName(name = "优先级", isRecordHistory = true)
	@ApiModelProperty(value = "优先级", position = 9)
	@Length(max=30,message="优先级长度不能大于30")
	private String priorLevel;	  
   
	
	/**
	* 主责部门
	*/
	@DataName(name = "主责部门", isRecordHistory = true)
	@ApiModelProperty(value = "主责部门", position = 10)
	@Length(max=30,message="主责部门长度不能大于30")
	private String masterDeptName;	  
   
	
	/**
	* 协办部门
	*/
	@DataName(name = "协办部门", isRecordHistory = true)
	@ApiModelProperty(value = "协办部门", position = 11)
	@Length(max=30,message="协办部门长度不能大于30")
	private String assistDeptName;	  
   
	
	/**
	* 需求提出人
	*/
	@DataName(name = "需求提出人", isRecordHistory = true)
	@ApiModelProperty(value = "需求提出人", position = 12)
	@Length(max=50,message="需求提出人长度不能大于50")
	private String demandProposeUserName;	  
   
	
	/**
	* 需求提出人名
	*/
	@DataName(name = "需求提出人名", isRecordHistory = true)
	@ApiModelProperty(value = "需求提出人名", position = 13)
	@Length(max=50,message="需求提出人名长度不能大于50")
	private String demandProposeRealName;	  
   
	
	/**
	* 需求提出日期
	*/
	@DataName(name = "需求提出日期", isRecordHistory = true)
	@ApiModelProperty(value = "需求提出日期", position = 14)
	@Length(max=20,message="需求提出日期长度不能大于20")
	private String demandProposeDate;	  
   
	
	/**
	* 需求受理人
	*/
	@DataName(name = "需求受理人", isRecordHistory = true)
	@ApiModelProperty(value = "需求受理人", position = 15)
	@Length(max=50,message="需求受理人长度不能大于50")
	private String demandAcceptUserName;	  
   
	
	/**
	* 需求受理人名
	*/
	@DataName(name = "需求受理人名", isRecordHistory = true)
	@ApiModelProperty(value = "需求受理人名", position = 16)
	@Length(max=50,message="需求受理人名长度不能大于50")
	private String demandAcceptRealName;	  
   
	
	/**
	* 需求受理日期
	*/
	@DataName(name = "需求受理日期", isRecordHistory = true)
	@ApiModelProperty(value = "需求受理日期", position = 17)
	@Length(max=20,message="需求受理日期长度不能大于20")
	private String demandAcceptDate;	  
   
	
	/**
	* 评审编号
	*/
	@DataName(name = "评审编号", isRecordHistory = true)
	@ApiModelProperty(value = "评审编号", position = 18)
	@Length(max=30,message="评审编号长度不能大于30")
	private String reviewNo;	  
   
	
	/**
	* 评审状态
	*/
	@DataName(name = "评审状态", isRecordHistory = true)
	@ApiModelProperty(value = "评审状态", position = 19)
	@Length(max=30,message="评审状态长度不能大于30")
	private String reviewStatus;	  
   
	
	/**
	* 评审通过日期
	*/
	@DataName(name = "评审通过日期", isRecordHistory = true)
	@ApiModelProperty(value = "评审通过日期", position = 20)
	@Length(max=20,message="评审通过日期长度不能大于20")
	private String reviewPassDate;	  
   
	
	/**
	* 窗口编号
	*/
	@DataName(name = "窗口编号", isRecordHistory = true)
	@ApiModelProperty(value = "窗口编号", position = 21)
	@Length(max=30,message="窗口编号长度不能大于30")
	private String windowNo;	  
   
	
	/**
	* 计划上线日期
	*/
	@DataName(name = "计划上线日期", isRecordHistory = true)
	@ApiModelProperty(value = "计划上线日期", position = 22)
	@Length(max=20,message="计划上线日期长度不能大于20")
	private String planOnlineDate;	  
   
	
	/**
	* 实际上线日期
	*/
	@DataName(name = "实际上线日期", isRecordHistory = true)
	@ApiModelProperty(value = "实际上线日期", position = 23)
	@Length(max=20,message="实际上线日期长度不能大于20")
	private String actualOnlineDate;	  
   
	
	/**
	* 业务分析人
	*/
	@DataName(name = "业务分析人", isRecordHistory = true)
	@ApiModelProperty(value = "业务分析人", position = 24)
	@Length(max=50,message="业务分析人长度不能大于50")
	private String bizAnalyseUserName;	  
   
	
	/**
	* 业务分析人名
	*/
	@DataName(name = "业务分析人名", isRecordHistory = true)
	@ApiModelProperty(value = "业务分析人名", position = 25)
	@Length(max=50,message="业务分析人名长度不能大于50")
	private String bizAnalyseRealName;	  
   
	
	/**
	* 系统分析人
	*/
	@DataName(name = "系统分析人", isRecordHistory = true)
	@ApiModelProperty(value = "系统分析人", position = 26)
	@Length(max=50,message="系统分析人长度不能大于50")
	private String systemAnalyseUserName;	  
   
	
	/**
	* 系统分析人名
	*/
	@DataName(name = "系统分析人名", isRecordHistory = true)
	@ApiModelProperty(value = "系统分析人名", position = 27)
	@Length(max=50,message="系统分析人名长度不能大于50")
	private String systemAnalyseRealName;	  
   
	
	/**
	* 版本号
	*/
	@DataName(name = "版本号", isRecordHistory = true)
	@ApiModelProperty(value = "版本号", position = 28)
	@Length(max=30,message="版本号长度不能大于30")
	private String versionNo;	  
   
	
	/**
	* 变更日期
	*/
	@DataName(name = "变更日期", isRecordHistory = true)
	@ApiModelProperty(value = "变更日期", position = 29)
	@Length(max=20,message="变更日期长度不能大于20")
	private String changeDate;	  
   
	
	/**
	* 状态
	*/
	@DataName(name = "需求状态", isRecordHistory = true)
	@ApiModelProperty(value = "需求状态", position = 30)
	@Length(max=30,message="需求状态长度不能大于30")
	private String demandStatus;
	
	/**
	* 标签信息
	*/
	@DataName(name = "标签信息")
	@ApiModelProperty(value = "标签信息", position = 6)
	@Length(max=2000,message="标签信息长度不能大于2000")
	private String labelInfo;
 }
