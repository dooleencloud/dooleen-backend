package com.dooleen.service.general.biz.plan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 14:39:36
 * @Description : 会议室管理实体
 * @Author : name
 * @Update : 2020-08-31 14:39:36
 */
@Data
@TableName("biz_plan_manage")
@ApiModel
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BizPlanManageEntity  extends BaseEntity implements Serializable  {

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
	* 项目ID
	*/
	@DataName(name = "项目ID", isRecordHistory = true)
	@ApiModelProperty(value = "项目ID", position = 2)
	@Length(max=20,message="项目ID长度不能大于20")
	@NotBlank(message = "项目ID不能为空")
	private String projectId;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	@NotBlank(message = "项目名称不能为空")
	private String projectName;


	/**
	 * 计划编号
	 */
	@DataName(name = "计划编号")
	@ApiModelProperty(value = "计划编号", position = 6)
	@Length(max=30,message="计划编号长度不能大于30")
	private String planNo;
   
	
	/**
	* 计划名称
	*/
	@DataName(name = "计划名称", isRecordHistory = true)
	@ApiModelProperty(value = "计划名称", position = 4)
	@Length(max=50,message="计划名称长度不能大于50")
	@NotBlank(message = "计划名称不能为空")
	private String planName;	  
   
	
	/**
	* 计划描述
	*/
	@DataName(name = "计划描述", isRecordHistory = true)
	@ApiModelProperty(value = "计划描述", position = 5)
	private String planDesc;	  
   
	
	/**
	* 计划层级
	*/
	@DataName(name = "计划层级", isRecordHistory = true)
	@ApiModelProperty(value = "计划层级", position = 6)
	@Length(max=100,message="计划层级长度不能大于100")
	private String planLevel;	  
   
	
	/**
	* 上级计划ID
	*/
	@DataName(name = "上级计划ID", isRecordHistory = true)
	@ApiModelProperty(value = "上级计划ID", position = 7)
	@Length(max=20,message="上级计划ID长度不能大于20")
	private String parentPlanId;	  
   
	
	/**
	* 上级计划名称
	*/
	@DataName(name = "上级计划名称", isRecordHistory = true)
	@ApiModelProperty(value = "上级计划名称", position = 8)
	@Length(max=50,message="上级计划名称长度不能大于50")
	private String parentPlanName;	  
   
	
	/**
	* 计划开始时间
	*/
	@DataName(name = "计划开始时间", isRecordHistory = true)
	@ApiModelProperty(value = "计划开始时间", position = 9)
	@Length(max=100,message="计划开始时间长度不能大于100")
	@NotBlank(message = "计划开始时间不能为空")
	private String planBeginDatetime;	  
   
	
	/**
	* 计划完成时间
	*/
	@DataName(name = "计划完成时间", isRecordHistory = true)
	@ApiModelProperty(value = "计划完成时间", position = 10)
	@Length(max=100,message="计划完成时间长度不能大于100")
	@NotBlank(message = "计划完成时间不能为空")
	private String planFinishDatetime;	  
   
	
	/**
	* 进度
	*/
	@DataName(name = "进度", isRecordHistory = true)
	@ApiModelProperty(value = "进度", position = 11)
	private String progress;	  
   
	
	/**
	* 责任人ID
	*/
	@DataName(name = "责任人ID", isRecordHistory = true)
	@ApiModelProperty(value = "责任人ID", position = 12)
	@Length(max=20,message="责任人ID长度不能大于20")
	@NotBlank(message = "责任人ID不能为空")
	private String respsbltPersonId;	  
   
	
	/**
	* 责任人姓名
	*/
	@DataName(name = "责任人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "责任人姓名", position = 13)
	@Length(max=50,message="责任人姓名长度不能大于50")
//	@NotBlank(message = "责任人姓名不能为空")
	private String respsbltPersonName;	  
   
	
	/**
	* 干系人ID列表
	*/
	@DataName(name = "干系人ID列表", isRecordHistory = true)
	@ApiModelProperty(value = "干系人ID列表", position = 14)
	@Length(max=2000,message="干系人ID列表长度不能大于2000")
	private String stakeholderIdList;	  
   
	
	/**
	* 干系人姓名列表
	*/
	@DataName(name = "干系人姓名列表", isRecordHistory = true)
	@ApiModelProperty(value = "干系人姓名列表", position = 15)
	@Length(max=2000,message="干系人姓名列表长度不能大于2000")
	private String stakeholderNameList;	  
	
	/**
	 * 责任人map
	 */
	@TableField(exist = false)
	private Map<String, String> respsbltPersonMap;
	
	/**
	 * 干系人map
	 */
	@TableField(exist = false)
	private Map<String, String> stakeholderMap;
 }
