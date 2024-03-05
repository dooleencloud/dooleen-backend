package com.dooleen.service.system.burying.point.entity;

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
 * @CreateDate : 2021-05-29 22:18:15
 * @Description : 埋点数据管理实体
 * @Author : name
 * @Update : 2021-05-29 22:18:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_burying_point")
@ApiModel
@ToString(callSuper = true)
public class SysBuryingPointEntity  extends BaseEntity implements Serializable  {
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
	* 请求渠道
	*/
	@Excel(name ="请求渠道")
	@DataName(name = "请求渠道", isRecordHistory = true)
	@ApiModelProperty(value = "请求渠道", position = 2)
	@Length(max=50,message="请求渠道长度不能大于50")
	@NotBlank(message = "请求渠道不能为空")
	private String requestChannel;	  
   
	
	/**
	* 统计类型
	*/
	@Excel(name ="统计类型")
	@DataName(name = "统计类型", isRecordHistory = true)
	@ApiModelProperty(value = "统计类型", position = 3)
	@Length(max=30,message="统计类型长度不能大于30")
	@NotBlank(message = "统计类型不能为空")
	private String statisticsType;	  
   
	
	/**
	* 菜单ID
	*/
	@Excel(name ="菜单ID")
	@DataName(name = "菜单ID", isRecordHistory = true)
	@ApiModelProperty(value = "菜单ID", position = 4)
	@Length(max=20,message="菜单ID长度不能大于20")
	private String menuId;	  
   
	
	/**
	* 菜单名称
	*/
	@Excel(name ="菜单名称")
	@DataName(name = "菜单名称", isRecordHistory = true)
	@ApiModelProperty(value = "菜单名称", position = 5)
	@Length(max=50,message="菜单名称长度不能大于50")
	private String menuName;	  
   
	
	/**
	* 菜单编码
	*/
	@Excel(name ="菜单编码")
	@DataName(name = "菜单编码", isRecordHistory = true)
	@ApiModelProperty(value = "菜单编码", position = 6)
	@Length(max=30,message="菜单编码长度不能大于30")
	private String menuCode;	  
   
	
	/**
	* 请求地址
	*/
	@Excel(name ="请求地址")
	@DataName(name = "请求地址", isRecordHistory = true)
	@ApiModelProperty(value = "请求地址", position = 7)
	@Length(max=100,message="请求地址长度不能大于100")
	private String requestAddress;	  
   
	
	/**
	* 父级菜单ID
	*/
	@Excel(name ="父级菜单ID")
	@DataName(name = "父级菜单ID", isRecordHistory = true)
	@ApiModelProperty(value = "父级菜单ID", position = 8)
	@Length(max=20,message="父级菜单ID长度不能大于20")
	private String parentMenuId;	  
   
	
	/**
	* 请求用户名
	*/
	@Excel(name ="请求用户名")
	@DataName(name = "请求用户名", isRecordHistory = true)
	@ApiModelProperty(value = "请求用户名", position = 9)
	@Length(max=50,message="请求用户名长度不能大于50")
	private String requestUserName;	  
   
	
	/**
	* 请求姓名
	*/
	@Excel(name ="请求姓名")
	@DataName(name = "请求姓名", isRecordHistory = true)
	@ApiModelProperty(value = "请求姓名", position = 10)
	@Length(max=50,message="请求姓名长度不能大于50")
	private String requestRealName;	  
   
	
	/**
	* 请求部门
	*/
	@Excel(name ="请求部门")
	@DataName(name = "请求部门", isRecordHistory = true)
	@ApiModelProperty(value = "请求部门", position = 11)
	@Length(max=30,message="请求部门长度不能大于30")
	private String requestDeptName;	  
 }
