package com.dooleen.service.general.calendar.info.entity;

import java.io.Serializable;
import java.util.List;

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
 * @CreateDate : 2020-07-30 14:39:13
 * @Description : 日历管理实体
 * @Author : name
 * @Update : 2020-07-30 14:39:13
 */
@Data
@TableName("general_calendar_info")
@ApiModel
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GeneralCalendarInfoEntity extends BaseEntity implements Serializable  {

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
	* 日历名称
	*/
	@DataName(name = "日历名称", isRecordHistory = true)
	@ApiModelProperty(value = "日历名称", position = 2)
	@Length(max=50,message="日历名称长度不能大于50")
	@NotBlank(message = "日历名称不能为空")
	private String calendarName;	  
   
	
	/**
	* 日历描述
	*/
	@DataName(name = "日历描述", isRecordHistory = true)
	@ApiModelProperty(value = "日历描述", position = 3)
	@Length(max=2000,message="日历描述长度不能大于2000")
	private String calendarDesc;	  
   
	
	/**
	* 日历类型
	*/
	@DataName(name = "日历类型", isRecordHistory = true)
	@ApiModelProperty(value = "日历类型", position = 4)
	@Length(max=30,message="日历类型长度不能大于30")
	private String calendarType;	  
   
	
	/**
	* 拥有人ID
	*/
	@DataName(name = "拥有人ID", isRecordHistory = true)
	@ApiModelProperty(value = "拥有人ID", position = 5)
	@Length(max=20,message="拥有人ID长度不能大于20")
	private String ownerId;	  
   
	
	/**
	* 分享标志
	*/
	@DataName(name = "分享标志", isRecordHistory = true)
	@ApiModelProperty(value = "分享标志", position = 6)
	@Length(max=1,message="分享标志长度不能大于1")
	private String shareFlag;	
	
	/**
	* 读取标志
	*/
	@TableField(exist = false)
	@DataName(name = "读取标志")
	@ApiModelProperty(value = "读取标志", position = 7)
	private String readFlag;
	
	/**
	* 有日程的日期列表
	*/
	@TableField(exist = false)
	@DataName(name = "有日程的日期列表")
	@ApiModelProperty(value = "读取标志", position = 8)
	private List<String> dateList;
 }
