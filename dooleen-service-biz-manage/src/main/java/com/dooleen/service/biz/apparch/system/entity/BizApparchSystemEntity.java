package com.dooleen.service.biz.apparch.system.entity;

import java.math.BigDecimal;
import java.util.List;
import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2020-08-31 20:02:53
 * @Description : 应用系统管理实体
 * @Author : name
 * @Update : 2020-08-31 20:02:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_system")
@ApiModel
@ToString(callSuper = true)
public class BizApparchSystemEntity  extends BaseEntity implements Serializable  {

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
	* 上级系统ID
	*/
	@DataName(name = "上级系统ID", isRecordHistory = true)
	@ApiModelProperty(value = "上级系统ID", position = 2)
	@Length(max=20,message="上级系统ID长度不能大于20")
	@NotBlank(message = "上级系统ID不能为空")
	private String parentSystemId;	  


	/**
	* 上级系统名称
	*/
	@DataName(name = "上级系统名称")
	@ApiModelProperty(value = "上级系统名称", position = 6)
	@Length(max=50,message="上级系统名称长度不能大于50")
	private String parentSystemName;
	/**
	* 系统名称
	*/
	@DataName(name = "系统名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统名称", position = 3)
	@Length(max=50,message="系统名称长度不能大于50")
	@NotBlank(message = "系统名称不能为空")
	private String systemName;	  
   

	/**
	* 系统名称
	*/
	@DataName(name = "系统名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统名称", position = 3)
	@Length(max=50,message="系统名称长度不能大于50")
	@TableField(exist = false)
	private String systemNameTmp;	 
	
	/**
	* 系统简称
	*/
	@DataName(name = "系统简称", isRecordHistory = true)
	@ApiModelProperty(value = "系统简称", position = 4)
	@Length(max=30,message="系统简称长度不能大于30")
	private String systemShortName;	  
   
	
	/**
	* 系统英文名称
	*/
	@DataName(name = "系统英文名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统英文名称", position = 5)
	@Length(max=50,message="系统英文名称长度不能大于50")
	private String systemEnglishName;	  
   


	/**
	* 系统类别
	*/
	@DataName(name = "系统类别")
	@ApiModelProperty(value = "系统类别", position = 6)
	@Length(max=30,message="系统类别长度不能大于30")
	private String systemCategory;
	
	/**
	* 系统级别
	*/
	@DataName(name = "系统级别", isRecordHistory = true)
	@ApiModelProperty(value = "系统级别", position = 6)
	@Length(max=30,message="系统级别长度不能大于30")
	private String systemGrade;	  
   
	
	/**
	* 系统类型
	*/
	@DataName(name = "系统类型", isRecordHistory = true)
	@ApiModelProperty(value = "系统类型", position = 7)
	@Length(max=30,message="系统类型长度不能大于30")
	private String systemType;	  
   
	
	/**
	* 所属部门名称
	*/
	@DataName(name = "所属部门名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属部门名称", position = 8)
	@Length(max=50,message="所属部门名称长度不能大于50")
	private String belongDeptName;	  
   
	
	/**
	* 开发部门名称
	*/
	@DataName(name = "开发部门名称", isRecordHistory = true)
	@ApiModelProperty(value = "开发部门名称", position = 9)
	@Length(max=50,message="开发部门名称长度不能大于50")
	private String developDeptName;	  
   
	
	/**
	* 开发团队名称
	*/
	@DataName(name = "开发团队名称", isRecordHistory = true)
	@ApiModelProperty(value = "开发团队名称", position = 10)
	@Length(max=50,message="开发团队名称长度不能大于50")
	private String developTeamName;	  
   
	
	/**
	* 系统负责人
	*/
	@DataName(name = "系统负责人", isRecordHistory = true)
	@ApiModelProperty(value = "系统负责人", position = 11)
	@Length(max=50,message="系统负责人长度不能大于50")
	private String systemRespsbUserName;	  
   
	
	/**
	* 系统负责人名
	*/
	@DataName(name = "系统负责人名", isRecordHistory = true)
	@ApiModelProperty(value = "系统负责人名", position = 12)
	@Length(max=50,message="系统负责人名长度不能大于50")
	private String systemRespsbRealName;	  
   
	
	/**
	* 建设方式
	*/
	@DataName(name = "建设方式", isRecordHistory = true)
	@ApiModelProperty(value = "建设方式", position = 13)
	@Length(max=30,message="建设方式长度不能大于30")
	private String buildWay;	  
   
	
	/**
	* 建设类型
	*/
	@DataName(name = "建设类型", isRecordHistory = true)
	@ApiModelProperty(value = "建设类型", position = 14)
	@Length(max=30,message="建设类型长度不能大于30")
	private String buildType;	  
   
	
	/**
	* 系统版本号
	*/
	@DataName(name = "系统版本号", isRecordHistory = true)
	@ApiModelProperty(value = "系统版本号", position = 15)
	@Length(max=30,message="系统版本号长度不能大于30")
	private String systemVersionNo;	  
   
	
	/**
	* 系统状态
	*/
	@DataName(name = "系统状态", isRecordHistory = true)
	@ApiModelProperty(value = "系统状态", position = 16)
	@Length(max=30,message="系统状态长度不能大于30")
	private String systemStatus;	  
   
	
	/**
	* 上线日期
	*/
	@DataName(name = "上线日期", isRecordHistory = true)
	@ApiModelProperty(value = "上线日期", position = 17)
	@Length(max=20,message="上线日期长度不能大于20")
	private String onlineDate;	  
   
	
	/**
	* 下线日期
	*/
	@DataName(name = "下线日期", isRecordHistory = true)
	@ApiModelProperty(value = "下线日期", position = 18)
	@Length(max=100,message="下线日期长度不能大于100")
	private String offlineDate;	  
   
	
	/**
	* 系统功能描述
	*/
	@DataName(name = "系统功能描述", isRecordHistory = true)
	@ApiModelProperty(value = "系统功能描述", position = 19)
	@Length(max=2000,message="系统功能描述长度不能大于2000")
	private String systemFunctionDesc;	  
   
	
	/**
	* 架构层级
	*/
	@DataName(name = "架构层级", isRecordHistory = true)
	@ApiModelProperty(value = "架构层级", position = 20)
	@Length(max=100,message="架构层级长度不能大于100")
	@NotBlank(message = "架构层级不能为空")
	private String architectureLevel;	  
   
	
	/**
	* 应用群组
	*/
	@DataName(name = "应用群组", isRecordHistory = true)
	@ApiModelProperty(value = "应用群组", position = 21)
	@Length(max=100,message="应用群组长度不能大于100")
	@NotBlank(message = "应用群组不能为空")
	private String appGroup;	  
   
	
	/**
	* 功能组
	*/
	@DataName(name = "功能组", isRecordHistory = true)
	@ApiModelProperty(value = "功能组", position = 22)
	@Length(max=2000,message="功能组长度不能大于2000")
	private String functionGroup;	  
   
	
	/**
	* 重要级别
	*/
	@DataName(name = "重要级别", isRecordHistory = true)
	@ApiModelProperty(value = "重要级别", position = 23)
	@Length(max=30,message="重要级别长度不能大于30")
	private String importantGrade;	  
   
	
	/**
	* 安全等级
	*/
	@DataName(name = "安全等级", isRecordHistory = true)
	@ApiModelProperty(value = "安全等级", position = 24)
	@Length(max=10,message="安全等级长度不能大于10")
	private String securityLevel;	  
   
	
	/**
	* RTO分值
	*/
	@DataName(name = "RTO分值", isRecordHistory = true)
	@ApiModelProperty(value = "RTO分值", position = 25)
	@Digits(integer=6,fraction=2, message = "RTO分值请填999999以内数字")
	private BigDecimal rtoScore;	  
   
	
	/**
	* RPO分值
	*/
	@DataName(name = "RPO分值", isRecordHistory = true)
	@ApiModelProperty(value = "RPO分值", position = 26)
	@Digits(integer=6,fraction=2, message = "RPO分值请填999999以内数字")
	private BigDecimal rpoScore;	  
   
	
	/**	
	* 系统运行日期
	*/
	@DataName(name = "系统运行日期", isRecordHistory = true)
	@ApiModelProperty(value = "系统运行日期", position = 27)
	@Length(max=20,message="系统运行日期长度不能大于20")
	private String systemRunDate;	  
   
	
	/**
	* 系统运行方式
	*/
	@DataName(name = "系统运行方式", isRecordHistory = true)
	@ApiModelProperty(value = "系统运行方式", position = 28)
	@Length(max=30,message="系统运行方式长度不能大于30")
	private String systemRunWay;	  
   
	
	/**
	* 可单独部署标志
	*/
	@DataName(name = "可单独部署标志", isRecordHistory = true)
	@ApiModelProperty(value = "可单独部署标志", position = 29)
	@Length(max=1,message="可单独部署标志长度不能大于1")
	private String mayAloneDeployFlag;	  
   
	
	/**
	* 中间件列表
	*/
	@DataName(name = "中间件列表", isRecordHistory = true)
	@ApiModelProperty(value = "中间件列表", position = 30)
	@Length(max=2000,message="中间件列表长度不能大于2000")
	private String middlewareList;	  
   
	/**
	* 数据库列表
	*/
	@DataName(name = "数据库列表")
	@ApiModelProperty(value = "数据库列表", position = 6)
	@Length(max=2000,message="数据库列表长度不能大于2000")
	private String databaseList;



	/**
	* 标签信息
	*/
	@DataName(name = "标签信息")
	@ApiModelProperty(value = "标签信息", position = 6)
	@Length(max=2000,message="标签信息长度不能大于2000")
	private String labelInfo;
	
	/**
	* 项目编号
	*/
	@DataName(name = "项目编号")
	@ApiModelProperty(value = "项目编号", position = 6)
	@Length(max=30,message="项目编号长度不能大于30")
	private String projectNo;

	/**
	* 项目名称
	*/
	@DataName(name = "项目名称")
	@ApiModelProperty(value = "项目名称", position = 6)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;
	
	/**
	* 供应商名称
	*/
	@DataName(name = "供应商名称", isRecordHistory = true)
	@ApiModelProperty(value = "供应商名称", position = 31)
	@Length(max=50,message="供应商名称长度不能大于50")
	private String supplierName;	  
   
	
	/**
	* 供应商负责人
	*/
	@DataName(name = "供应商负责人", isRecordHistory = true)
	@ApiModelProperty(value = "供应商负责人", position = 32)
	@Length(max=50,message="供应商负责人长度不能大于50")
	private String supplierRespsbName;	  
   
	
	/**
	* 供应商联系电话
	*/
	@DataName(name = "供应商联系电话", isRecordHistory = true)
	@ApiModelProperty(value = "供应商联系电话", position = 33)
	@Length(max=30,message="供应商联系电话长度不能大于30")
	private String supplierContactPhoneNo;	  
	
	/**
	 * 是否有子项
	 */
	@TableField(exist = false)
	private String hasChildren;
	
	/**
	 * 子系统列表
	 */
	@TableField(exist = false)
	private List<BizApparchSystemEntity> children;
 }
