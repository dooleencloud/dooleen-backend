package com.dooleen.service.biz.apparch.out.system.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
 * @CreateDate : 2020-09-01 16:24:16
 * @Description : 外联系统管理实体
 * @Author : name
 * @Update : 2020-09-01 16:24:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_apparch_out_system")
@ApiModel
@ToString(callSuper = true)
public class BizApparchOutSystemEntity  extends BaseEntity implements Serializable  {

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
	* 系统类别
	*/
	@DataName(name = "系统类别", isRecordHistory = true)
	@ApiModelProperty(value = "系统类别", position = 2)
	@Length(max=30,message="系统类别长度不能大于30")
	@NotBlank(message = "系统类别不能为空")
	private String systemCategory;	  
   
	
	/**
	* 系统名称
	*/
	@DataName(name = "系统名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统名称", position = 3)
	@Length(max=50,message="系统名称长度不能大于50")
	@NotBlank(message = "系统名称不能为空")
	private String systemName;	  
   
	
	/**
	* 系统简称
	*/
	@DataName(name = "系统简称", isRecordHistory = true)
	@ApiModelProperty(value = "系统简称", position = 4)
	@Length(max=30,message="系统简称长度不能大于30")
	private String systemShortName;	  
   
	
	/**
	* 系统英文名称
	*/
	@DataName(name = "系统英文名称", isRecordHistory = true)
	@ApiModelProperty(value = "系统英文名称", position = 5)
	@Length(max=50,message="系统英文名称长度不能大于50")
	private String systemEnglishName;	  
   
	
	/**
	* 系统功能描述
	*/
	@DataName(name = "系统功能描述", isRecordHistory = true)
	@ApiModelProperty(value = "系统功能描述", position = 6)
	@Length(max=2000,message="系统功能描述长度不能大于2000")
	private String systemFunctionDesc;	  
   
	
	/**
	* 对接日期
	*/
	@DataName(name = "对接日期", isRecordHistory = true)
	@ApiModelProperty(value = "对接日期", position = 7)
	@Length(max=20,message="对接日期长度不能大于20")
	private String joinDate;	  
   
	
	/**
	* 下线日期
	*/
	@DataName(name = "下线日期", isRecordHistory = true)
	@ApiModelProperty(value = "下线日期", position = 8)
	@Length(max=20,message="下线日期长度不能大于20")
	private String offlineDate;	  
   
	
	/**
	* 境内外标志
	*/
	@DataName(name = "境内外标志", isRecordHistory = true)
	@ApiModelProperty(value = "境内外标志", position = 9)
	@Length(max=1,message="境内外标志长度不能大于1")
	private String domesticAbroadFlag;	  
   
	
	/**
	* 所属单位
	*/
	@DataName(name = "所属单位", isRecordHistory = true)
	@ApiModelProperty(value = "所属单位", position = 10)
	@Length(max=50,message="所属单位长度不能大于50")
	private String belongCompanyName;	  
   
	
	/**
	* 联系人
	*/
	@DataName(name = "联系人", isRecordHistory = true)
	@ApiModelProperty(value = "联系人", position = 11)
	@Length(max=50,message="联系人长度不能大于50")
	private String contactUserName;	  
   
	
	/**
	* 联系电话
	*/
	@DataName(name = "联系电话", isRecordHistory = true)
	@ApiModelProperty(value = "联系电话", position = 12)
	@Length(max=30,message="联系电话长度不能大于30")
	private String contactPhoneNo;	  
   
	
	/**
	* 是否本地部署标志
	*/
	@DataName(name = "是否本地部署标志", isRecordHistory = true)
	@ApiModelProperty(value = "是否本地部署标志", position = 13)
	@Length(max=1,message="是否本地部署标志长度不能大于1")
	private String isLocalDeployFlag;	  
   
	
	/**
	* 数据中心名称
	*/
	@DataName(name = "数据中心名称", isRecordHistory = true)
	@ApiModelProperty(value = "数据中心名称", position = 14)
	@Length(max=50,message="数据中心名称长度不能大于50")
	private String dataCenterName;	  
 }
