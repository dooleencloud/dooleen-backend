package com.dooleen.common.core.app.general.flow.entity;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
 * @CreateDate : 2020-06-29 09:17:12
 * @Description : 流程节点配置管理实体
 * @Author : name
 * @Update : 2020-06-29 09:17:12
 */
@Data
@TableName("general_flow_node_config")
@ApiModel
@ToString(callSuper = true)
public class GeneralFlowNodeConfigEntity extends BaseEntity implements Serializable  {

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
	* 流程ID
	*/
	@DataName(name = "流程ID")
	@ApiModelProperty(value = "流程ID", position = 2)
	@Length(max=20,message="流程ID长度不能大于20")
	@NotBlank(message = "流程ID不能为空")
	private String flowId;	  
	
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号")
	@ApiModelProperty(value = "排序序号", position = 2)
    @Range(min = 1, max = 100, message = "排序序号必须在1到100之间")
	private int orderSeq;	  
	
	/**
	* 节点编号
	*/
	@DataName(name = "节点编号")
	@ApiModelProperty(value = "节点编号", position = 3)
	@Length(max=30,message="节点编号长度不能大于30")
	@NotBlank(message = "节点编号不能为空")
	private String nodeNo;	  
   
	
	/**
	* 节点名称
	*/
	@DataName(name = "节点名称")
	@ApiModelProperty(value = "节点名称", position = 4)
	@Length(max=50,message="节点名称长度不能大于50")
	@NotBlank(message = "节点名称不能为空")
	private String nodeName;	  


	/**
	* 简单模式标志
	*/
	@DataName(name = "简单模式标志")
	@ApiModelProperty(value = "简单模式标志", position = 6)
	@Length(max=1,message="简单模式标志长度不能大于1")
	private String easyModeFlag;
	
	/**
	* 是否子流程标志
	*/
	@DataName(name = "是否子流程标志")
	@ApiModelProperty(value = "是否子流程标志", position = 5)
	@Length(max=1,message="是否子流程标志长度不能大于1")
	@NotBlank(message = "是否子流程标志不能为空")
	private String isSubFlowFlag;	  
   
	
	/**
	* 子流程ID
	*/
	@DataName(name = "子流程ID")
	@ApiModelProperty(value = "子流程ID", position = 6)
	@Length(max=20,message="子流程ID长度不能大于20")
	private String subFlowId;	  
   
	
	/**
	* 跳转类型
	*/
	@DataName(name = "跳转类型")
	@ApiModelProperty(value = "跳转类型", position = 7)
	@Length(max=10,message="跳转类型长度不能大于10")
	private String gotoType;	  
   
	/**
	* 通过比例
	*/
	@DataName(name = "通过比例")
	@ApiModelProperty(value = "通过比例", position = 7)
	@Digits(integer=5,fraction=2, message = "通过比例请填999.99以内数字")
	private String passRate;	
	
	/**
	* 跳转条件
	*/
	@DataName(name = "跳转条件")
	@ApiModelProperty(value = "跳转条件", position = 8)
	@Length(max=500,message="跳转条件长度不能大于500")
	private String gotoCondition;	  


	/**
	* 控制方式
	*/
	@DataName(name = "控制方式")
	@ApiModelProperty(value = "控制方式", position = 8)
	@Length(max=30,message="控制方式长度不能大于30")
	private String controlWay;
	
	/**
	* java类名
	*/
	@DataName(name = "JAVA类名")
	@ApiModelProperty(value = "JAVA类名", position = 8)
	@Length(max=200,message="JAVA类名不能大于200")
	private String javaClassName;	  
   
	
	/**
	* 是否转派标志
	*/
	@DataName(name = "是否转派标志")
	@ApiModelProperty(value = "是否转派标志", position = 9)
	@Length(max=1,message="是否转派标志长度不能大于1")
	private String isTransferFlag;	  
   
	
	/**
	* 是否委托标志
	*/
	@DataName(name = "是否委托标志")
	@ApiModelProperty(value = "是否委托标志", position = 10)
	@Length(max=1,message="是否委托标志长度不能大于1")
	private String isDelegateFlag;	  
   
	
	/**
	* 执行状态列表
	*/
	@DataName(name = "执行状态列表")
	@ApiModelProperty(value = "执行状态列表", position = 11)
	@Length(max=2000,message="执行状态列表长度不能大于2000")
	private String execStatusList;

	/**
	 * 通知处理类名
	 */
	@DataName(name = "通知处理类名", isRecordHistory = true)
	@ApiModelProperty(value = "通知处理类名", required= true , position = 6)
	@Length(max=200,message="通知处理类名长度不能大于200")
	private String noticeProcessClassName;

	/**
	 * 是否超时通知标志
	 */
	@DataName(name = "是否超时通知标志")
	@ApiModelProperty(value = "是否超时通知标志", position = 6)
	@Length(max=1,message="是否超时通知标志长度不能大于1")
	private String isOvertimeNoticeFlag;

	/**
	* 通知方式
	*/
	@DataName(name = "通知方式")
	@ApiModelProperty(value = "通知方式", position = 12)
	@Length(max=30,message="通知方式长度不能大于30")
	private String noticeWay;	  
   
	
	/**
	* 经办人列表
	*/
	@DataName(name = "经办人列表")
	@ApiModelProperty(value = "经办人列表", position = 13)
	@Length(max=2000,message="经办人列表长度不能大于2000")
	private String optUserList;	  


	/**
	* 抄送用户列表
	*/
	@DataName(name = "抄送用户列表")
	@ApiModelProperty(value = "抄送用户列表", position = 6)
	@Length(max=2000,message="抄送用户列表长度不能大于2000")
	private String ccUserList;

	/**
	* 抄送角色名称
	*/
	@DataName(name = "抄送角色名称")
	@ApiModelProperty(value = "抄送角色名称", position = 6)
	@Length(max=50,message="抄送角色名称长度不能大于50")
	private String ccRoleName;

	
	/**
	* 是否回退标志
	*/
	@DataName(name = "是否回退标志")
	@ApiModelProperty(value = "是否回退标志", position = 14)
	@Length(max=1,message="是否回退标志长度不能大于1")
	private String isRollbackFlag;	  
   
	
	/**
	* 表单ID
	*/
	@DataName(name = "表单ID")
	@ApiModelProperty(value = "表单ID", position = 15)
	@Length(max=20,message="表单ID长度不能大于20")
	private String formId;	  
   

	/**
	* 隐藏表单组列表
	*/
	@DataName(name = "隐藏表单组列表")
	@ApiModelProperty(value = "隐藏表单组列表", position = 6)
	@Length(max=2000,message="隐藏表单组列表长度不能大于2000")
	private String hideFormGroupList;	
	/**
	* 表单权限列表
	*/
	@DataName(name = "表单权限列表")
	@ApiModelProperty(value = "表单权限列表", position = 16)
	@Length(max=2000,message="表单权限列表长度不能大于2000")
	private String formPrivilegeList;	  
   
	
	/**
	* 节点等待时长
	*/
	@DataName(name = "节点等待时长")
	@ApiModelProperty(value = "节点等待时长", position = 17)
	@Length(max=30,message="节点等待时长长度不能大于30")
	private String nodeWaitDuration;	  
   
	
	/**
	* 超时通知方式
	*/
	@DataName(name = "超时通知方式")
	@ApiModelProperty(value = "超时通知方式", position = 18)
	@Length(max=30,message="超时通知方式长度不能大于30")
	private String overtimeNoticeWay;	  
   
	
	/**
	* 超时通知列表
	*/
	@DataName(name = "超时通知列表")
	@ApiModelProperty(value = "超时通知列表", position = 19)
	@Length(max=2000,message="超时通知列表长度不能大于2000")
	private String overtimeNoticeList;
	
	/**
	* 超时通知角色名称
	*/
	@DataName(name = "超时通知角色名称")
	@ApiModelProperty(value = "超时通知角色名称", position = 6)
	@Length(max=50,message="超时通知角色名称长度不能大于50")
	private String overtimeNoticeRoleName;
	

	/**
	* 时间单位
	*/
	@DataName(name = "时间单位")
	@ApiModelProperty(value = "时间单位", position = 6)
	@Length(max=30,message="时间单位长度不能大于30")
	private String timeUnit;

 }
