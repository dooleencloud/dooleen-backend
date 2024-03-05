package com.dooleen.service.biz.sixteen.user.pf.exch.entity;

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
 * @CreateDate : 2021-05-08 21:12:00
 * @Description : 用户16要素结果管理实体
 * @Author : name
 * @Update : 2021-05-08 21:12:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_user_pf_exch")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenUserPfExchEntity  extends BaseEntity implements Serializable  {
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
	@Excel(name ="因子ID")
	@DataName(name = "因子ID", isRecordHistory = true)
	@ApiModelProperty(value = "因子ID", position = 3)
	@Length(max=10,message="因子ID长度不能大于10")
	@NotBlank(message = "因子ID不能为空")
	private String factorId;	  
   
	
	/**
	* 用户姓名
	*/
	@Excel(name ="用户姓名")
	@DataName(name = "用户姓名", isRecordHistory = true)
	@ApiModelProperty(value = "用户姓名", position = 4)
	@Length(max=50,message="用户姓名长度不能大于50")
	private String realName;	  
   
	
	/**
	* 性别
	*/
	@Excel(name ="性别")
	@DataName(name = "性别", isRecordHistory = true)
	@ApiModelProperty(value = "性别", position = 5)
	@Length(max=10,message="性别长度不能大于10")
	private String sex;	  
   
	
	/**
	* 年龄
	*/
	@Excel(name ="年龄")
	@DataName(name = "年龄", isRecordHistory = true)
	@ApiModelProperty(value = "年龄", position = 6)
    @DecimalMax(value="9999999999",message="年龄不能大于9999999999")
	@DecimalMin(value="0",message="年龄不能小于0")
	private int age;	  
   
	
	/**
	* 原始分值
	*/
	@Excel(name ="原始分值")
	@DataName(name = "原始分值", isRecordHistory = true)
	@ApiModelProperty(value = "原始分值", position = 7)
    @DecimalMax(value="9999999999",message="原始分值不能大于9999999999")
	@DecimalMin(value="0",message="原始分值不能小于0")
	private int originScore;	  
   
	
	/**
	* 复合分值
	*/
	@Excel(name ="复合分值")
	@DataName(name = "复合分值", isRecordHistory = true)
	@ApiModelProperty(value = "复合分值", position = 8)
    @DecimalMax(value="9999999999",message="复合分值不能大于9999999999")
	@DecimalMin(value="0",message="复合分值不能小于0")
	private int compaScore;	  
   
	
	/**
	* 状态分值
	*/
	@Excel(name ="状态分值")
	@DataName(name = "状态分值", isRecordHistory = true)
	@ApiModelProperty(value = "状态分值", position = 9)
    @DecimalMax(value="9999999999",message="状态分值不能大于9999999999")
	@DecimalMin(value="0",message="状态分值不能小于0")
	private int statusScore;	  
   
	
	/**
	* 日期
	*/
	@Excel(name ="日期")
	@DataName(name = "日期", isRecordHistory = true)
	@ApiModelProperty(value = "日期", position = 10)
	@Length(max=10,message="日期长度不能大于10")
	private String date;	  
 }
