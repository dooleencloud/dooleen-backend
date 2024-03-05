package com.dooleen.service.biz.sixteen.topic.db.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @CreateDate : 2021-05-08 21:06:08
 * @Description : 16PF题目管理实体
 * @Author : name
 * @Update : 2021-05-08 21:06:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_topic_db")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenTopicDbEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
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
	* 题目编号
	*/
	@Excel(name ="题目编号")
	@DataName(name = "题目编号", isRecordHistory = true)
	@ApiModelProperty(value = "题目编号", position = 2)
    @DecimalMax(value="99999",message="题目编号不能大于99999")
	@DecimalMin(value="0",message="题目编号不能小于0")
	@NotBlank(message = "题目编号不能为空")
	private int topicNum;	  
   
	
	/**
	* 因子ID
	*/
	@Excel(name ="因子ID")
	@DataName(name = "因子ID", isRecordHistory = true)
	@ApiModelProperty(value = "因子ID", position = 3)
	@Length(max=10,message="因子ID长度不能大于10")
	@NotBlank(message = "因子ID不能为空")
	private String factorId;	  
   
	
	/**
	* 题目内容
	*/
	@Excel(name ="题目内容")
	@DataName(name = "题目内容", isRecordHistory = true)
	@ApiModelProperty(value = "题目内容", position = 4)
	@Length(max=1000,message="题目内容长度不能大于1000")
	@NotBlank(message = "题目内容不能为空")
	private String topicContent;	  
   
	
	/**
	* 选项
	*/
	@Excel(name ="选项")
	@DataName(name = "选项", isRecordHistory = true)
	@ApiModelProperty(value = "选项", position = 5)
	@Length(max=2000,message="选项长度不能大于2000")
	@NotBlank(message = "选项不能为空")
	private String options;	  
   
	
	/**
	* 正确答案
	*/
	@Excel(name ="正确答案")
	@DataName(name = "正确答案", isRecordHistory = true)
	@ApiModelProperty(value = "正确答案", position = 6)
	@Length(max=10,message="正确答案长度不能大于10")
	private String rightAnswer;	  
   
	
	/**
	* 正确答案分数
	*/
	@Excel(name ="正确答案分数")
	@DataName(name = "正确答案分数", isRecordHistory = true)
	@ApiModelProperty(value = "正确答案分数", position = 7)
    @DecimalMax(value="9999999999",message="正确答案分数不能大于9999999999")
	@DecimalMin(value="0",message="正确答案分数不能小于0")
	private int rightAnswerScore;	  
   
	
	/**
	* 备选答案
	*/
	@Excel(name ="备选答案")
	@DataName(name = "备选答案", isRecordHistory = true)
	@ApiModelProperty(value = "备选答案", position = 8)
	@Length(max=10,message="备选答案长度不能大于10")
	private String preAnswer;	  
   
	
	/**
	* 备选答案分数
	*/
	@Excel(name ="备选答案分数")
	@DataName(name = "备选答案分数", isRecordHistory = true)
	@ApiModelProperty(value = "备选答案分数", position = 9)
    @DecimalMax(value="9999999999",message="备选答案分数不能大于9999999999")
	@DecimalMin(value="0",message="备选答案分数不能小于0")
	private int preAnswerScore;	  
 }
