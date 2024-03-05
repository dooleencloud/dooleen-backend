package com.dooleen.service.biz.apparch.scene.entity;

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
 * @CreateDate : 2020-08-27 14:56:39
 * @Description : 业务场景管理实体
 * @Author : name
 * @Update : 2020-08-27 14:56:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_scene")
@ApiModel
@ToString(callSuper = true)
public class BizApparchSceneEntity  extends BaseEntity implements Serializable  {

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
	* 需求编号
	*/
	@DataName(name = "需求编号", isRecordHistory = true)
	@ApiModelProperty(value = "需求编号", position = 2)
	@Length(max=30,message="需求编号长度不能大于30")
	@NotBlank(message = "需求编号不能为空")
	private String demandNo;	  
   
	
	/**
	* 业务场景编号
	*/
	@DataName(name = "业务场景编号", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景编号", position = 3)
	@Length(max=30,message="业务场景编号长度不能大于30")
	private String bizSceneNo;	  
   
	
	/**
	* 版本号
	*/
	@DataName(name = "版本号", isRecordHistory = true)
	@ApiModelProperty(value = "版本号", position = 4)
	@Length(max=30,message="版本号长度不能大于30")
	private String versionNo;	  
   
	
	/**
	* 资产标志
	*/
	@DataName(name = "资产标志", isRecordHistory = true)
	@ApiModelProperty(value = "资产标志", position = 5)
	@Length(max=1,message="资产标志长度不能大于1")
	private String assetFlag;	  
   
	
	/**
	* 需求名称
	*/
	@DataName(name = "需求名称", isRecordHistory = true)
	@ApiModelProperty(value = "需求名称", position = 6)
	@Length(max=50,message="需求名称长度不能大于50")
	private String demandName;	  
   
	
	/**
	* 业务场景名称
	*/
	@DataName(name = "业务场景名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景名称", position = 7)
	@Length(max=50,message="业务场景名称长度不能大于50")
	@NotBlank(message = "业务场景名称不能为空")
	private String bizSceneName;	  
   
	
	/**
	* 场景类别
	*/
	@DataName(name = "场景类别", isRecordHistory = true)
	@ApiModelProperty(value = "场景类别", position = 8)
	@Length(max=30,message="场景类别长度不能大于30")
	private String sceneCategory;	  
   
	
	/**
	* 产品编号
	*/
	@DataName(name = "产品编号", isRecordHistory = true)
	@ApiModelProperty(value = "产品编号", position = 9)
	@Length(max=30,message="产品编号长度不能大于30")
	private String productNo;	  
   
	
	/**
	* 产品名称
	*/
	@DataName(name = "产品名称", isRecordHistory = true)
	@ApiModelProperty(value = "产品名称", position = 10)
	@Length(max=50,message="产品名称长度不能大于50")
	private String productName;	  
   
	
	/**
	* 业务渠道
	*/
	@DataName(name = "业务渠道", isRecordHistory = true)
	@ApiModelProperty(value = "业务渠道", position = 11)
	@Length(max=50,message="业务渠道长度不能大于50")
	private String bizChannel;	  
   
	
	/**
	* 服务入口名称
	*/
	@DataName(name = "服务入口名称", isRecordHistory = true)
	@ApiModelProperty(value = "服务入口名称", position = 12)
	@Length(max=50,message="服务入口名称长度不能大于50")
	private String serviceEntranceName;	  
   


	/**
	* 重要性标识
	*/
	@DataName(name = "重要性标识")
	@ApiModelProperty(value = "重要性标识", position = 6)
	@Length(max=1,message="重要性标识长度不能大于1")
	private String importantFlag;

	/**
	* 重要性级别
	*/
	@DataName(name = "重要性级别")
	@ApiModelProperty(value = "重要性级别", position = 6)
	@Length(max=30,message="重要性级别长度不能大于30")
	private String importantGrade;
	
	/**
	* 特有规则描述
	*/
	@DataName(name = "特有规则描述")
	@ApiModelProperty(value = "特有规则描述", position = 6)
	@Length(max=200,message="特有规则描述长度不能大于200")
	private String specificRuleDesc;   
   
	
	/**
	* 核算规则编号
	*/
	@DataName(name = "核算规则编号", isRecordHistory = true)
	@ApiModelProperty(value = "核算规则编号", position = 14)
	@Length(max=30,message="核算规则编号长度不能大于30")
	private String accountingRuleNo;	  
   
	
	/**
	* 通用规则编号
	*/
	@DataName(name = "通用规则编号", isRecordHistory = true)
	@ApiModelProperty(value = "通用规则编号", position = 15)
	@Length(max=30,message="通用规则编号长度不能大于30")
	private String generalRuleNo;	  
   
	
	/**
	* 项目ID
	*/
	@DataName(name = "项目ID", isRecordHistory = true)
	@ApiModelProperty(value = "项目ID", position = 16)
	@Length(max=20,message="项目ID长度不能大于20")
	private String projectId;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 17)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	
	/**
	* 变更日期
	*/
	@DataName(name = "变更日期", isRecordHistory = true)
	@ApiModelProperty(value = "变更日期", position = 18)
	@Length(max=20,message="变更日期长度不能大于20")
	private String changeDate;	  
   
	
	/**
	* 场景状态
	*/
	@DataName(name = "场景状态", isRecordHistory = true)
	@ApiModelProperty(value = "场景状态", position = 19)
	@Length(max=30,message="场景状态长度不能大于30")
	private String sceneStatus;	  
	
	/**
	* 标签信息
	*/
	@DataName(name = "标签信息",isRecordHistory = true)
	@ApiModelProperty(value = "标签信息", position = 6)
	@Length(max=2000,message="标签信息长度不能大于2000")
	private String labelInfo;
 }
