package com.dooleen.service.general.schedule.entity;

import java.io.Serializable;

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
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-03 18:44:09
 * @Description : 日程管理实体
 * @Author : name
 * @Update : 2020-08-03 18:44:09
 */
@Data
@TableName("general_schedule_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralScheduleInfoEntity extends BaseEntity implements Serializable  {

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
	* 日历ID
	*/
	@DataName(name = "日历ID", isRecordHistory = true)
	@ApiModelProperty(value = "日历ID", position = 2)
	@Length(max=20,message="日历ID长度不能大于20")
	@NotBlank(message = "日历ID不能为空")
	private String calendarId;

	/**
	 * 所属用户
	 */
	@DataName(name = "所属用户", isRecordHistory = true)
	@ApiModelProperty(value = "所属用户", position = 2)
	@Length(max=50,message="所属用户长度不能大于50")
	private String ownerUserName;

	/**
	 * 会议ID
	 */
	@DataName(name = "会议ID", isRecordHistory = true)
	@ApiModelProperty(value = "会议ID", position = 2)
	@Length(max=50,message="会议ID长度不能大于50")
	private String conferenceId;

	/**
	* 日程主题
	*/
	@DataName(name = "日程主题", isRecordHistory = true)
	@ApiModelProperty(value = "日程主题", position = 3)
	@Length(max=1000,message="日程主题长度不能大于1000")
	@NotBlank(message = "日程主题不能为空")
	private String scheduleSubject;	  
   
	
	/**
	* 日程开始时间
	*/
	@DataName(name = "日程开始时间", isRecordHistory = true)
	@ApiModelProperty(value = "日程开始时间", position = 4)
	@NotBlank(message = "日程开始时间不能为空")
	private String scheduleBeginDatetime;	  
   
	
	/**
	* 日程结束时间
	*/
	@DataName(name = "日程结束时间", isRecordHistory = true)
	@ApiModelProperty(value = "日程结束时间", position = 5)
	@NotBlank(message = "日程结束时间不能为空")
	private String scheduleEndDatetime;	  
   
	
	/**
	* 日程内容
	*/
	@DataName(name = "日程内容", isRecordHistory = true)
	@ApiModelProperty(value = "日程内容", position = 6)
	@NotBlank(message = "日程内容不能为空")
	private String scheduleContent;	  
	
	/**
	 * 颜色
	 */
	@DataName(name = "颜色", isRecordHistory = true)
	@ApiModelProperty(value = "颜色", position = 7)
	private String color;	  
	
	/**
	* 全天显示标志
	*/
	@DataName(name = "全天显示标志", isRecordHistory = true)
	@ApiModelProperty(value = "全天显示标志", position = 8)
	private String allDayShowFlag;	  
	
	/**
	* 读取标志
	*/
	@TableField(exist = false)
	@DataName(name = "读取标志" , isRecordHistory = true)
	@ApiModelProperty(value = "读取标志", position = 9)
	private String readFlag;
 }
