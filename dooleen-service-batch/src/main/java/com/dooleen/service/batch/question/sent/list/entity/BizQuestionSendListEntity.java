package com.dooleen.service.batch.question.sent.list.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:29:58
 * @Description : 下发问卷管理实体
 * @Author : name
 * @Update : 2021-02-22 11:29:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_question_send_list")
@ApiModel
@ToString(callSuper = true)
public class BizQuestionSendListEntity extends BaseEntity implements Serializable  {

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
	* 项目编号
	*/
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 2)
	@Length(max=30,message="项目编号长度不能大于30")
	@NotBlank(message = "项目编号不能为空")
	private String projectNo;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	
	/**
	* 批次号
	*/
	@DataName(name = "批次号", isRecordHistory = true)
	@ApiModelProperty(value = "批次号", position = 4)
	@Length(max=30,message="批次号长度不能大于30")
	@NotBlank(message = "批次号不能为空")
	private String batchNo;	  
   
	
	/**
	* 填报人
	*/
	@DataName(name = "填报人", isRecordHistory = true)
	@ApiModelProperty(value = "填报人", position = 5)
	@Length(max=50,message="填报人长度不能大于50")
	@NotBlank(message = "填报人不能为空")
	private String fillInUserName;	  
   
	
	/**
	* 填报人姓名
	*/
	@DataName(name = "填报人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "填报人姓名", position = 6)
	@Length(max=50,message="填报人姓名长度不能大于50")
	@NotBlank(message = "填报人姓名不能为空")
	private String fillInRealName;	  
   
	
	/**
	* 问卷模板ID
	*/
	@DataName(name = "问卷模板ID", isRecordHistory = true)
	@ApiModelProperty(value = "问卷模板ID", position = 7)
	@Length(max=20,message="问卷模板ID长度不能大于20")
	@NotBlank(message = "问卷模板ID不能为空")
	private String questionnaireTemplateId;	  
   
	
	/**
	* 问卷类型
	*/
	@DataName(name = "问卷类型", isRecordHistory = true)
	@ApiModelProperty(value = "问卷类型", position = 8)
	@Length(max=30,message="问卷类型长度不能大于30")
	@NotBlank(message = "问卷类型不能为空")
	private String questionnaireType;	  
   
	
	/**
	* 问卷标题
	*/
	@DataName(name = "问卷标题", isRecordHistory = true)
	@ApiModelProperty(value = "问卷标题", position = 9)
	@Length(max=200,message="问卷标题长度不能大于200")
	@NotBlank(message = "问卷标题不能为空")
	private String questionnaireTitle;	  
   
	
	/**
	* 问卷公告
	*/
	@DataName(name = "问卷公告", isRecordHistory = true)
	@ApiModelProperty(value = "问卷公告", position = 10)
	@Length(max=500,message="问卷公告长度不能大于500")
	@NotBlank(message = "问卷公告不能为空")
	private String questionnaireNotice;	  
   
	
	/**
	* 手机号码
	*/
	@DataName(name = "手机号码", isRecordHistory = true)
	@ApiModelProperty(value = "手机号码", position = 11)
	@Length(max=30,message="手机号码长度不能大于30")
	private String mobileNo;	  
   
	
	/**
	* 生成频率
	*/
	@DataName(name = "生成频率", isRecordHistory = true)
	@ApiModelProperty(value = "生成频率", position = 12)
	@Length(max=10,message="生成频率长度不能大于10")
	private String generateFrequency;	  
   
	
	/**
	* 有效日期
	*/
	@DataName(name = "有效日期", isRecordHistory = true)
	@ApiModelProperty(value = "有效日期", position = 13)
	@Length(max=20,message="有效日期长度不能大于20")
	private String validDate;



	/**
	 * 状态
	 */
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	@NotBlank(message = "状态不能为空")
	private String status;
 }
