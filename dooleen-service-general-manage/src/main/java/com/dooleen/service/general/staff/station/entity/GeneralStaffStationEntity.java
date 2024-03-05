package com.dooleen.service.general.staff.station.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2020-10-10 10:15:53
 * @Description : 工位信息表实体
 * @Author : name
 * @Update : 2020-10-10 10:15:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_staff_station")
@ApiModel
@ToString(callSuper = true)
public class GeneralStaffStationEntity  extends BaseEntity implements Serializable  {

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
	* 办公区编号
	*/
	@DataName(name = "办公区编号", isRecordHistory = true)
	@ApiModelProperty(value = "办公区编号", position = 2)
	@Length(max=30,message="办公区编号长度不能大于30")
	private String officeAreaNo;
	/**
	 * 办公区编号
	 */
	@DataName(name = "办公区", isRecordHistory = true)
	@ApiModelProperty(value = "办公区", position = 2)
	@Length(max=30,message="办公区长度不能大于30")
	@NotBlank(message = "办公区不能为空")
	private String officeAreaName;


	/**
	* 楼层编号
	*/
	@DataName(name = "楼层编号", isRecordHistory = true)
	@ApiModelProperty(value = "楼层编号", position = 3)
	@Length(max=30,message="楼层编号长度不能大于30")
	@NotBlank(message = "楼层编号不能为空")
	private String floorNo;	  
   
	
	/**
	* 工位编号
	*/
	@DataName(name = "工位编号", isRecordHistory = true)
	@ApiModelProperty(value = "工位编号", position = 4)
	@Length(max=30,message="工位编号长度不能大于30")
	@NotBlank(message = "工位编号不能为空")
	private String staffStationNo;	  
   
	
	/**
	* 工位性质
	*/
	@DataName(name = "工位性质", isRecordHistory = true)
	@ApiModelProperty(value = "工位性质", position = 5)
	@Length(max=30,message="工位性质长度不能大于30")
	@NotBlank(message = "工位性质不能为空")
	private String staffStationProperties;	  
   
	
	/**
	* 工位类型
	*/
	@DataName(name = "工位类型", isRecordHistory = true)
	@ApiModelProperty(value = "工位类型", position = 6)
	@Length(max=30,message="工位类型长度不能大于30")
	@NotBlank(message = "工位类型不能为空")
	private String staffStationType;	  
   
	
	/**
	* 所属项目编号
	*/
	@DataName(name = "所属项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "所属项目编号", position = 7)
	@Length(max=30,message="所属项目编号长度不能大于30")
	private String belongProjectNo;	  
   
	
	/**
	* 所属项目名称
	*/
	@DataName(name = "所属项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属项目名称", position = 8)
	@Length(max=50,message="所属项目名称长度不能大于50")
	private String belongProjectName;	  
   
	
	/**
	* 所属部门编号
	*/
	@DataName(name = "所属部门编号", isRecordHistory = true)
	@ApiModelProperty(value = "所属部门编号", position = 9)
	@Length(max=30,message="所属部门编号长度不能大于30")
	private String belongDeptNo;	  
   
	
	/**
	* 所属部门名称
	*/
	@DataName(name = "所属部门名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属部门名称", position = 10)
	@Length(max=50,message="所属部门名称长度不能大于50")
	private String belongDeptName;	  
   
	
	/**
	* 使用用户名
	*/
	@DataName(name = "使用用户名", isRecordHistory = true)
	@ApiModelProperty(value = "使用用户名", position = 11)
	@Length(max=50,message="使用用户名长度不能大于50")
	private String useUserName;	  
   
	
	/**
	* 使用人名
	*/
	@DataName(name = "使用人名", isRecordHistory = true)
	@ApiModelProperty(value = "使用人名", position = 12)
	@Length(max=50,message="使用人名长度不能大于50")
	private String useRealName;	  
   
	
	/**
	* 使用状态列表
	*/
	@DataName(name = "使用状态列表", isRecordHistory = true)
	@ApiModelProperty(value = "使用状态列表", position = 13)
	@Length(max=2000,message="使用状态列表长度不能大于2000")
	private String useStatusList;	  
   
	
	/**
	* 管理员用户名
	*/
	@DataName(name = "管理员用户名", isRecordHistory = true)
	@ApiModelProperty(value = "管理员用户名", position = 14)
	@Length(max=50,message="管理员用户名长度不能大于50")
	private String managerUserName;	  
   
	
	/**
	* 管理员人名
	*/
	@DataName(name = "管理员人名", isRecordHistory = true)
	@ApiModelProperty(value = "管理员人名", position = 15)
	@Length(max=50,message="管理员人名长度不能大于50")
	private String managerRealName;	  
   
	
	/**
	* 联系电话
	*/
	@DataName(name = "联系电话", isRecordHistory = true)
	@ApiModelProperty(value = "联系电话", position = 16)
	@Length(max=30,message="联系电话长度不能大于30")
	private String contactPhoneNo;	  
   
	
	/**
	* 状态
	*/
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 17)
	@Length(max=30,message="状态长度不能大于30")
	private String status;




	/**
	 * 员工类型
	 */
	@DataName(name = "员工类型")
	@ApiModelProperty(value = "员工类型", position = 6)
	@Length(max=10,message="员工类型长度不能大于10")
	private String staffType;

	/**
	 * 出勤状态
	 */
	@DataName(name = "出勤状态")
	@ApiModelProperty(value = "出勤状态", position = 6)
	@Length(max=30,message="出勤状态长度不能大于30")
	private String attendanceStatus;
 }
