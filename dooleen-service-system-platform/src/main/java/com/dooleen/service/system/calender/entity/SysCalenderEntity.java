package com.dooleen.service.system.calender.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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
 * @CreateDate : 2021-07-02 20:34:51
 * @Description : 系统日历管理实体
 * @Author : name
 * @Update : 2021-07-02 20:34:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_calender")
@ApiModel
@ToString(callSuper = true)
public class SysCalenderEntity  extends BaseEntity implements Serializable  {
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
	 * 年份
	 */
	@Excel(name ="年份")
	@DataName(name = "年份", isRecordHistory = true)
	@ApiModelProperty(value = "年份", position = 6)
	private String year;

	/**
	* 节假日日期
	*/
	@Excel(name ="节假日日期")
	@DataName(name = "节假日日期", isRecordHistory = true)
	@ApiModelProperty(value = "节假日日期", position = 2)
	@Length(max=20,message="节假日日期长度不能大于20")
	@NotBlank(message = "节假日日期不能为空")
	private String holidayDate;
   
	
	/**
	* 节假日名称
	*/
	@Excel(name ="节假日名称")
	@DataName(name = "节假日名称", isRecordHistory = true)
	@ApiModelProperty(value = "节假日名称", position = 3)
	@Length(max=50,message="节假日名称长度不能大于50")
	@NotBlank(message = "节假日名称不能为空")
	private String holidayName;	  
   
	
	/**
	* 节假日备注
	*/
	@Excel(name ="备注")
	@DataName(name = "备注", isRecordHistory = true)
	@ApiModelProperty(value = "备注", position = 4)
	@Length(max=5000,message="备注长度不能大于5000")
	private String holidayRemark;
 }
