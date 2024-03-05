package com.dooleen.service.biz.apparch.bizfunction.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2020-08-28 11:30:33
 * @Description : 业务功能点管理实体
 * @Author : name
 * @Update : 2020-08-28 11:30:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_biz_function")
@ApiModel
@ToString(callSuper = true)
public class BizApparchBizFunctionEntity  extends BaseEntity implements Serializable  {

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
	* 业务场景编号
	*/
	@DataName(name = "业务场景编号", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景编号", position = 2)
	@Length(max=30,message="业务场景编号长度不能大于30")
	@NotBlank(message = "业务场景编号不能为空")
	private String bizSceneNo;	  
   
	/**
	* 业务功能点编号
	*/
	@DataName(name = "业务功能点编号", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点编号", position = 3)
	@Length(max=30,message="业务功能点编号长度不能大于30")
	private String bizFunctionNo;	  
   
	
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
	* 业务场景名称
	*/
	@DataName(name = "业务场景名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景名称", position = 6)
	@Length(max=50,message="业务场景名称长度不能大于50")
	private String bizSceneName;	  
   
	
	/**
	* 业务功能点名称
	*/
	@DataName(name = "业务功能点名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点名称", position = 7)
	@Length(max=50,message="业务功能点名称长度不能大于50")
	@NotBlank(message = "业务功能点名称不能为空")
	private String bizFunctionName;	  
   
	
	/**
	* 业务功能点类型
	*/
	@DataName(name = "业务功能点类型", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点类型", position = 8)
	@Length(max=30,message="业务功能点类型长度不能大于30")
	private String bizFunctionType;	  
   
	
	/**
	* 处理方式
	*/
	@DataName(name = "处理方式", isRecordHistory = true)
	@ApiModelProperty(value = "处理方式", position = 9)
	@Length(max=30,message="处理方式长度不能大于30")
	private String processWay;	  
   
	
	/**
	* 业务序号
	*/
	@DataName(name = "业务序号", isRecordHistory = true)
	@ApiModelProperty(value = "业务序号", position = 10)
    @DecimalMax(value="9999999999",message="业务序号不能大于9999999999")
	@DecimalMin(value="0",message="业务序号不能小于0")
	private int bizSeq;	  
   
	
	/**
	* 项目ID
	*/
	@DataName(name = "项目ID", isRecordHistory = true)
	@ApiModelProperty(value = "项目ID", position = 11)
	@Length(max=30,message="项目ID长度不能大于30")
	private String projectId;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 12)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	


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
	* 业务功能点描述
	*/
	@DataName(name = "业务功能点描述", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点描述", position = 16)
	@Length(max=2000,message="业务功能点描述长度不能大于2000")
	private String bizFunctionDesc;	  
   
	
	/**
	* 变更日期
	*/
	@DataName(name = "变更日期", isRecordHistory = true)
	@ApiModelProperty(value = "变更日期", position = 17)
	@Length(max=100,message="变更日期长度不能大于100")
	private String changeDate;	  
   
	
	/**
	* 业务功能点状态
	*/
	@DataName(name = "业务功能点状态", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点状态", position = 18)
	@Length(max=30,message="业务功能点状态长度不能大于30")
	private String bizFunctionStatus;	  
	


	/**
	* 标签信息
	*/
	@DataName(name = "标签信息")
	@ApiModelProperty(value = "标签信息", position = 6)
	@Length(max=2000,message="标签信息长度不能大于2000")
	private String labelInfo;
 }
