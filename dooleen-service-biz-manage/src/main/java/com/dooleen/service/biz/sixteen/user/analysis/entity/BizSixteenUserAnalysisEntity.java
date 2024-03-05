package com.dooleen.service.biz.sixteen.user.analysis.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
 * @CreateDate : 2021-05-08 21:28:50
 * @Description : 用户结果分析管理实体
 * @Author : name
 * @Update : 2021-05-08 21:28:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_user_analysis")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenUserAnalysisEntity  extends BaseEntity implements Serializable  {
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
	* 用户名
	*/
	@Excel(name ="用户名")
	@DataName(name = "用户名", isRecordHistory = true)
	@ApiModelProperty(value = "用户名", position = 2)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	
	/**
	* 因子ID 
	*/
	@Excel(name ="因子ID ")
	@DataName(name = "因子ID ", isRecordHistory = true)
	@ApiModelProperty(value = "因子ID ", position = 3)
	@Length(max=10,message="因子ID 长度不能大于10")
	@NotBlank(message = "因子ID 不能为空")
	private String factorId;	  
   
	
	/**
	* 因子类型
	*/
	@Excel(name ="因子类型")
	@DataName(name = "因子类型", isRecordHistory = true)
	@ApiModelProperty(value = "因子类型", position = 4)
	@Length(max=10,message="因子类型长度不能大于10")
	@NotBlank(message = "因子类型不能为空")
	private String factorType;	  
   
	
	/**
	* 因子
	*/
	@Excel(name ="因子")
	@DataName(name = "因子", isRecordHistory = true)
	@ApiModelProperty(value = "因子", position = 5)
	@Length(max=100,message="因子长度不能大于100")
	@NotBlank(message = "因子不能为空")
	private String factor;	  
   
	
	/**
	* 分值
	*/
	@Excel(name ="分值")
	@DataName(name = "分值", isRecordHistory = true)
	@ApiModelProperty(value = "分值", position = 6)
	private float score;
   
	
	/**
	* 状态分值
	*/
	@Excel(name ="状态分值")
	@DataName(name = "状态分值", isRecordHistory = true)
	@ApiModelProperty(value = "状态分值", position = 7)
	private float statusScore;
   
	
	/**
	* 状态
	*/
	@Excel(name ="状态")
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 8)
	@Length(max=10,message="状态长度不能大于10")
	private String status;	  
   
	
	/**
	* 向下分特征
	*/
	@Excel(name ="向下分特征")
	@DataName(name = "向下分特征", isRecordHistory = true)
	@ApiModelProperty(value = "向下分特征", position = 9)
	@Length(max=1000,message="向下分特征长度不能大于1000")
	private String lowScoreFeatures;	  
   
	
	/**
	* 向上分特征
	*/
	@Excel(name ="向上分特征")
	@DataName(name = "向上分特征", isRecordHistory = true)
	@ApiModelProperty(value = "向上分特征", position = 10)
	@Length(max=1000,message="向上分特征长度不能大于1000")
	private String highScoreFeatures;
   
	
	/**
	* 时间
	*/
	@Excel(name ="时间")
	@DataName(name = "时间", isRecordHistory = true)
	@ApiModelProperty(value = "时间", position = 11)
	@Length(max=20,message="时间长度不能大于20")
	private String date;	  
   
	
	/**
	* 回答耗时
	*/
	@Excel(name ="回答耗时")
	@DataName(name = "回答耗时", isRecordHistory = true)
	@ApiModelProperty(value = "回答耗时", position = 12)
	@Length(max=30,message="回答耗时长度不能大于30")
	private String answerDuration;	  
 }
