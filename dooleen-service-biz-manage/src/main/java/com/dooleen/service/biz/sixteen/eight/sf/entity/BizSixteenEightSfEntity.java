package com.dooleen.service.biz.sixteen.eight.sf.entity;

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
 * @CreateDate : 2021-05-08 20:36:05
 * @Description : 8SF要素管理实体
 * @Author : name
 * @Update : 2021-05-08 20:36:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_eight_sf")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenEightSfEntity  extends BaseEntity implements Serializable  {
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
	* 因子ID
	*/
	@Excel(name ="因子ID")
	@DataName(name = "因子ID", isRecordHistory = true)
	@ApiModelProperty(value = "因子ID", position = 2)
	@Length(max=10,message="因子ID长度不能大于10")
	@NotBlank(message = "因子ID不能为空")
	private String factorId;	  
   
	
	/**
	* 因子名称
	*/
	@Excel(name ="因子名称")
	@DataName(name = "因子名称", isRecordHistory = true)
	@ApiModelProperty(value = "因子名称", position = 3)
	@Length(max=50,message="因子名称长度不能大于50")
	@NotBlank(message = "因子名称不能为空")
	private String factor;	  
   
	
	/**
	* 向下最小值
	*/
	@Excel(name ="向下最小值")
	@DataName(name = "向下最小值", isRecordHistory = true)
	@ApiModelProperty(value = "向下最小值", position = 4)
    @DecimalMax(value="9999999999",message="向下最小值不能大于9999999999")
	@DecimalMin(value="0",message="向下最小值不能小于0")
	private int lowScoreMin;	  
   
	
	/**
	* 向下最大值
	*/
	@Excel(name ="向下最大值")
	@DataName(name = "向下最大值", isRecordHistory = true)
	@ApiModelProperty(value = "向下最大值", position = 5)
    @DecimalMax(value="9999999999",message="向下最大值不能大于9999999999")
	@DecimalMin(value="0",message="向下最大值不能小于0")
	private int lowScoreMax;	  
   
	
	/**
	* 向下因子内容
	*/
	@Excel(name ="向下因子内容")
	@DataName(name = "向下因子内容", isRecordHistory = true)
	@ApiModelProperty(value = "向下因子内容", position = 6)
	@Length(max=0,message="向下因子内容长度不能大于0")
	private String lowFactorContent;	  
   
	
	/**
	* 向上最小值
	*/
	@Excel(name ="向上最小值")
	@DataName(name = "向上最小值", isRecordHistory = true)
	@ApiModelProperty(value = "向上最小值", position = 7)
    @DecimalMax(value="9999999999",message="向上最小值不能大于9999999999")
	@DecimalMin(value="0",message="向上最小值不能小于0")
	private int hightScoreMin;	  
   
	
	/**
	* 向上最大值
	*/
	@Excel(name ="向上最大值")
	@DataName(name = "向上最大值", isRecordHistory = true)
	@ApiModelProperty(value = "向上最大值", position = 8)
    @DecimalMax(value="9999999999",message="向上最大值不能大于9999999999")
	@DecimalMin(value="0",message="向上最大值不能小于0")
	private int hightScoreMax;	  
   
	
	/**
	* 向上因子内容
	*/
	@Excel(name ="向上因子内容")
	@DataName(name = "向上因子内容", isRecordHistory = true)
	@ApiModelProperty(value = "向上因子内容", position = 9)
	@Length(max=0,message="向上因子内容长度不能大于0")
	private String hightFactorContent;	  
 }
