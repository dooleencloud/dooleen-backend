package com.dooleen.service.general.conference.info.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotation.TableField;
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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-26 14:09:14
 * @Description : 会议信息表实体
 * @Author : name
 * @Update : 2021-04-26 14:09:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_conference_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralConferenceInfoEntity  extends BaseEntity implements Serializable  {

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
	* 会议类型
	*/
	@DataName(name = "会议类型", isRecordHistory = true)
	@ApiModelProperty(value = "会议类型", position = 2)
	@Length(max=30,message="会议类型长度不能大于30")
	@NotBlank(message = "会议类型不能为空")
	private String conferenceType;



	/**
	 * 会议室ID
	 */
	@DataName(name = "会议室ID", isRecordHistory = true)
	@ApiModelProperty(value = "会议室ID", position = 6)
	@Length(max=20,message="会议室ID长度不能大于20")
	private String conferenceRoomId;

	/**
	* 会议室
	*/
	@DataName(name = "会议室", isRecordHistory = true)
	@ApiModelProperty(value = "会议室", position = 3)
	@Length(max=100,message="会议室长度不能大于100")
	@NotBlank(message = "会议室不能为空")
	private String conferenceRoom;	  
   
	
	/**
	* 会议主题
	*/
	@DataName(name = "会议主题", isRecordHistory = true)
	@ApiModelProperty(value = "会议主题", position = 4)
	@Length(max=1000,message="会议主题长度不能大于1000")
	@NotBlank(message = "会议主题不能为空")
	private String conferenceSubject;	  
   
	
	/**
	* 主持人
	*/
	@DataName(name = "召集人", isRecordHistory = true)
	@ApiModelProperty(value = "召集人", position = 5)
	@Length(max=50,message="召集人长度不能大于50")
	@NotBlank(message = "召集人不能为空")
	private String convenerUserName;	  
   
	
	/**
	* 主持人名
	*/
	@DataName(name = "召集人名", isRecordHistory = true)
	@ApiModelProperty(value = "召集人名", position = 6)
	@Length(max=50,message="召集人名长度不能大于50")
	@NotBlank(message = "召集人名不能为空")
	private String convenerRealName;



	/**
	 * 项目编号
	 */
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 6)
	@Length(max=30,message="项目编号长度不能大于30")
	private String projectNo;

	/**
	 * 项目名称
	 */
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 6)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;

	/**
	* 参与人列表
	*/
	@DataName(name = "参与人列表", isRecordHistory = true)
	@ApiModelProperty(value = "参与人列表", position = 7)
	@Length(max=2000,message="参与人列表长度不能大于2000")
	@NotBlank(message = "参与人列表不能为空")
	private String participantsList;	  
   
	
	/**
	* 会议开始时间
	*/
	@DataName(name = "会议开始时间", isRecordHistory = true)
	@ApiModelProperty(value = "会议开始时间", position = 8)
	@Length(max=100,message="会议开始时间长度不能大于100")
	@NotBlank(message = "会议开始时间不能为空")
	private String conferenceBeginDatetime;	  
   
	
	/**
	* 会议结束时间
	*/
	@DataName(name = "会议结束时间", isRecordHistory = true)
	@ApiModelProperty(value = "会议结束时间", position = 9)
	@Length(max=100,message="会议结束时间长度不能大于100")
	@NotBlank(message = "会议结束时间不能为空")
	private String conferenceEndDatetime;	  
   
	
	/**
	* 会议议题
	*/
	@DataName(name = "会议议题", isRecordHistory = true)
	@ApiModelProperty(value = "会议议题", position = 10)
	@Length(max=5000,message="会议议题长度不能大于100")
	private String conferenceIssue;	  
   
	
	/**
	* 会议号
	*/
	@DataName(name = "会议号", isRecordHistory = true)
	@ApiModelProperty(value = "会议号", position = 11)
	@Length(max=30,message="会议号长度不能大于30")
	private String conferenceNo;	  
   
	
	/**
	* 会议链接
	*/
	@DataName(name = "会议链接", isRecordHistory = true)
	@ApiModelProperty(value = "会议链接", position = 12)
	@Length(max=100,message="会议链接长度不能大于100")
	private String conferenceUrl;	  
   
	
	/**
	* 会议附件列表
	*/
	@DataName(name = "会议附件列表", isRecordHistory = true)
	@ApiModelProperty(value = "会议附件列表", position = 13)
	@Length(max=2000,message="会议附件列表长度不能大于2000")
	private String conferenceAttList;	  
   
	
	/**
	* 颜色
	*/
	@DataName(name = "颜色", isRecordHistory = true)
	@ApiModelProperty(value = "颜色", position = 14)
	@Length(max=20,message="颜色长度不能大于20")
	private String color;	  
   
	
	/**
	* 全天显示标志
	*/
	@DataName(name = "全天显示标志", isRecordHistory = true)
	@ApiModelProperty(value = "全天显示标志", position = 15)
	@Length(max=1,message="全天显示标志长度不能大于1")
	private String allDayShowFlag;



	/**
	 * 生成频率
	 */
	@DataName(name = "生成频率", isRecordHistory = true)
	@ApiModelProperty(value = "生成频率", position = 6)
	@Length(max=10,message="生成频率长度不能大于10")
	private String generateFrequency;

	/**
	 * 生成月
	 */
	@DataName(name = "生成月", isRecordHistory = true)
	@ApiModelProperty(value = "生成月", position = 6)
	@Length(max=10,message="生成月长度不能大于10")
	private String generateMonth;

	/**
	 * 生成日
	 */
	@DataName(name = "生成日", isRecordHistory = true)
	@ApiModelProperty(value = "生成日", position = 6)
	@Length(max=10,message="生成日长度不能大于10")
	private String generateDay;

	/**
	 * 生成日期
	 */
	@DataName(name = "生成日期", isRecordHistory = true)
	@ApiModelProperty(value = "生成日期", position = 6)
	@Length(max=20,message="生成日期长度不能大于20")
	private String generateDate;

	/**
	 * 生成时间
	 */
	@DataName(name = "生成时间", isRecordHistory = true)
	@ApiModelProperty(value = "生成时间", position = 6)
	@Length(max=100,message="生成时间长度不能大于100")
	private String generateDatetime;

	/**
	 * 有效日期
	 */
	@DataName(name = "有效日期", isRecordHistory = true)
	@ApiModelProperty(value = "有效日期", position = 6)
	@Length(max=20,message="有效日期长度不能大于20")
	private String validDate;

	@TableField(exist =false)
	private String conflictMsg;
 }
