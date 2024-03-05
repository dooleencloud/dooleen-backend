package com.dooleen.service.biz.apparch.system.function.entity;

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
 * @CreateDate : 2020-09-02 11:54:57
 * @Description : 系统功能点管理实体
 * @Author : name
 * @Update : 2020-09-02 11:54:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_system_function")
@ApiModel
@ToString(callSuper = true)
public class BizApparchSystemFunctionEntity  extends BaseEntity implements Serializable  {

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
	* 业务功能点编号
	*/
	@DataName(name = "业务功能点编号", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点编号", position = 2)
	@Length(max=30,message="业务功能点编号长度不能大于30")
	@NotBlank(message = "业务功能点编号不能为空")
	private String bizFunctionNo;	  
   
	
	/**
	* 系统功能点编号
	*/
	@DataName(name = "系统功能点编号", isRecordHistory = true)
	@ApiModelProperty(value = "系统功能点编号", position = 3)
	@Length(max=30,message="系统功能点编号长度不能大于30")
	private String systemFunctionNo;	  
   
	
	/**
	* 版本号
	*/
	@DataName(name = "版本号", isRecordHistory = true)
	@ApiModelProperty(value = "版本号", position = 4)
	@Length(max=30,message="版本号长度不能大于30")
	@NotBlank(message = "版本号不能为空")
	private String versionNo;	  
   
	
	/**
	* 资产标志
	*/
	@DataName(name = "资产标志", isRecordHistory = true)
	@ApiModelProperty(value = "资产标志", position = 5)
	@Length(max=1,message="资产标志长度不能大于1")
	private String assetFlag;	  


	/**
	* 系统类型
	*/
	@DataName(name = "系统类型")
	@ApiModelProperty(value = "系统类型", position = 6)
	@Length(max=30,message="系统类型长度不能大于30")
	private String systemType;
	
	/**
	* 系统编号
	*/
	@DataName(name = "系统编号", isRecordHistory = true)
	@ApiModelProperty(value = "系统编号", position = 6)
	@Length(max=30,message="系统编号长度不能大于30")
	@NotBlank(message = "系统编号不能为空")
	private String systemNo;	  
   
	
	/**
	* 系统名称
	*/
	@DataName(name = "系统名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统名称", position = 7)
	@Length(max=50,message="系统名称长度不能大于50")
	@NotBlank(message = "系统名称不能为空")
	private String systemName;	  
   
	
	/**
	* 系统状态
	*/
	@DataName(name = "系统状态", isRecordHistory = true)
	@ApiModelProperty(value = "系统状态", position = 8)
	@Length(max=30,message="系统状态长度不能大于30")
	private String systemStatus;	  
   
	
	/**
	* 功能点类型
	*/
	@DataName(name = "功能点类型", isRecordHistory = true)
	@ApiModelProperty(value = "功能点类型", position = 9)
	@Length(max=30,message="功能点类型长度不能大于30")
	@NotBlank(message = "功能点类型不能为空")
	private String functionType;	  
   
	
	/**
	* 业务功能点名称
	*/
	@DataName(name = "业务功能点名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务功能点名称", position = 10)
	@Length(max=50,message="业务功能点名称长度不能大于50")
	private String bizFunctionName;	  
   
	
	/**
	* 系统功能点名称
	*/
	@DataName(name = "系统功能点名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统功能点名称", position = 11)
	@Length(max=50,message="系统功能点名称长度不能大于50")
	@NotBlank(message = "系统功能点名称不能为空")
	private String systemFunctionName;	  
   
	
	/**
	* 系统功能点序号
	*/
	@DataName(name = "系统功能点序号", isRecordHistory = true)
	@ApiModelProperty(value = "系统功能点序号", position = 12)
    @DecimalMax(value="9999999999",message="系统功能点序号不能大于9999999999")
	@DecimalMin(value="0",message="系统功能点序号不能小于0")
	private int systemFunctionSeq;	  
   
	
	/**
	* 是否改造标志
	*/
	@DataName(name = "是否改造标志", isRecordHistory = true)
	@ApiModelProperty(value = "是否改造标志", position = 13)
	@Length(max=1,message="是否改造标志长度不能大于1")
	private String isReformFlag;	  
   
	
	/**
	* 需求变更编号
	*/
	@DataName(name = "需求变更编号", isRecordHistory = true)
	@ApiModelProperty(value = "需求变更编号", position = 14)
	@Length(max=30,message="需求变更编号长度不能大于30")
	private String demandChangeNo;	  
   
	
	/**
	* 项目编号
	*/
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 15)
	@Length(max=30,message="项目编号长度不能大于30")
	private String projectNo;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 16)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	
	/**
	* 变更日期
	*/
	@DataName(name = "变更日期", isRecordHistory = true)
	@ApiModelProperty(value = "变更日期", position = 17)
	@Length(max=20,message="变更日期长度不能大于20")
	private String changeDate;	  
   
	
	/**
	* 功能点状态
	*/
	@DataName(name = "功能点状态", isRecordHistory = true)
	@ApiModelProperty(value = "功能点状态", position = 18)
	@Length(max=30,message="功能点状态长度不能大于30")
	private String functionStatus;	  
   
	
	/**
	* 标签信息
	*/
	@DataName(name = "标签信息", isRecordHistory = true)
	@ApiModelProperty(value = "标签信息", position = 19)
	@Length(max=2000,message="标签信息长度不能大于2000")
	private String labelInfo;	  
   
	
	/**
	* 接口标志
	*/
	@DataName(name = "接口标志", isRecordHistory = true)
	@ApiModelProperty(value = "接口标志", position = 20)
	@Length(max=1,message="接口标志长度不能大于1")
	private String interfaceFlag;	  
   
	
	/**
	* 接口编号
	*/
	@DataName(name = "接口编号", isRecordHistory = true)
	@ApiModelProperty(value = "接口编号", position = 21)
	@Length(max=30,message="接口编号长度不能大于30")
	private String interfaceNo;	  
   
	
	/**
	* 接口名称
	*/
	@DataName(name = "接口名称", isRecordHistory = true)
	@ApiModelProperty(value = "接口名称", position = 22)
	@Length(max=50,message="接口名称长度不能大于50")
	private String interfaceName;	  
   
	
	/**
	* 连接方式
	*/
	@DataName(name = "连接方式", isRecordHistory = true)
	@ApiModelProperty(value = "连接方式", position = 23)
	@Length(max=30,message="连接方式长度不能大于30")
	private String linkWay;	  
   
	
	/**
	* 交互类型
	*/
	@DataName(name = "交互类型", isRecordHistory = true)
	@ApiModelProperty(value = "交互类型", position = 24)
	@Length(max=30,message="交互类型长度不能大于30")
	private String interactiveType;	  
   
	
	/**
	* 服务方系统编号
	*/
	@DataName(name = "服务方系统编号", isRecordHistory = true)
	@ApiModelProperty(value = "服务方系统编号", position = 25)
	@Length(max=30,message="服务方系统编号长度不能大于30")
	private String serviceSystemNo;	  
   
	
	/**
	* 服务方系统名称
	*/
	@DataName(name = "服务方系统名称", isRecordHistory = true)
	@ApiModelProperty(value = "服务方系统名称", position = 26)
	@Length(max=50,message="服务方系统名称长度不能大于50")
	private String serviceSystemName;	  
   
	
	/**
	* 服务方系统状态
	*/
	@DataName(name = "服务方系统状态", isRecordHistory = true)
	@ApiModelProperty(value = "服务方系统状态", position = 27)
	@Length(max=30,message="服务方系统状态长度不能大于30")
	private String serviceSystemStatus;	  
   
	
	/**
	* 服务方系统功能点编号
	*/
	@DataName(name = "服务方系统功能点编号", isRecordHistory = true)
	@ApiModelProperty(value = "服务方系统功能点编号", position = 28)
	@Length(max=30,message="服务方系统功能点编号长度不能大于30")
	private String serviceSystemFunctionNo;	  
   
	
	/**
	* 服务方系统功能点名称
	*/
	@DataName(name = "服务方系统功能点名称", isRecordHistory = true)
	@ApiModelProperty(value = "服务方系统功能点名称", position = 29)
	@Length(max=50,message="服务方系统功能点名称长度不能大于50")
	private String serviceSystemFunctionName;	  
   
	
	/**
	* 服务方接口编号
	*/
	@DataName(name = "服务方接口编号", isRecordHistory = true)
	@ApiModelProperty(value = "服务方接口编号", position = 30)
	@Length(max=30,message="服务方接口编号长度不能大于30")
	private String serviceInterfaceNo;	  
   
	
	/**
	* 服务方接口名称
	*/
	@DataName(name = "服务方接口名称", isRecordHistory = true)
	@ApiModelProperty(value = "服务方接口名称", position = 31)
	@Length(max=50,message="服务方接口名称长度不能大于50")
	private String serviceInterfaceName;	  
 }
