package com.dooleen.service.general.schedule.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
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
public class GeneralScheduleInfoDto extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
	public String id; 
	
	/**
	* 租户ID
	*/
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	
	/**
	* 日历ID
	*/
	private String calendarId;	  
   
	
	/**
	* 日程主题
	*/
	private String scheduleSubject;	  
   
	
	/**
	* 日程开始时间
	*/
	private String scheduleBeginDatetime;	  
   
	
	/**
	* 日程结束时间
	*/
	private String scheduleEndDatetime;	  
   
	
	/**
	* 日程内容
	*/
	private String scheduleContent;	  
	
	/**
	 * 颜色
	 */
	private String color;	  
	
	/**
	* 全天显示标志
	*/
	private String allDayShowFlag;	  
	
	/**
	 * 只读标识
	 */
	private String readonly;
 }
