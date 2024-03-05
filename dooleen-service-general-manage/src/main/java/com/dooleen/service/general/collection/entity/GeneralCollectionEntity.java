package com.dooleen.service.general.collection.entity;

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
 * @CreateDate : 2021-07-30 19:27:28
 * @Description : 收藏管理实体
 * @Author : name
 * @Update : 2021-07-30 19:27:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_collection")
@ApiModel
@ToString(callSuper = true)
public class GeneralCollectionEntity  extends BaseEntity implements Serializable  {
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
	* 资源ID
	*/
	@Excel(name ="资源ID")
	@DataName(name = "资源ID", isRecordHistory = true)
	@ApiModelProperty(value = "资源ID", position = 3)
	@Length(max=20,message="资源ID长度不能大于20")
	@NotBlank(message = "资源ID不能为空")
	private String resourceId;	  
   
	
	/**
	* 资源标题
	*/
	@Excel(name ="资源标题")
	@DataName(name = "资源标题", isRecordHistory = true)
	@ApiModelProperty(value = "资源标题", position = 4)
	@Length(max=200,message="资源标题长度不能大于200")
	private String resourceTitle;	  
   
	
	/**
	* 资源来源
	*/
	@Excel(name ="资源来源")
	@DataName(name = "资源来源", isRecordHistory = true)
	@ApiModelProperty(value = "资源来源", position = 5)
	@Length(max=100,message="资源来源长度不能大于100")
	private String resourceSource;	  
   
	
	/**
	* 图片列表
	*/
	@Excel(name ="图片列表")
	@DataName(name = "图片列表", isRecordHistory = true)
	@ApiModelProperty(value = "图片列表", position = 6)
	@Length(max=2000,message="图片列表长度不能大于2000")
	private String imgList;	  
   
	
	/**
	* 收藏类型
	*/
	@Excel(name ="收藏类型")
	@DataName(name = "收藏类型", isRecordHistory = true)
	@ApiModelProperty(value = "收藏类型", position = 7)
	@Length(max=30,message="收藏类型长度不能大于30")
	@NotBlank(message = "收藏类型不能为空")
	private String collectType;	  
   
	
	/**
	* 收藏时间
	*/
	@Excel(name ="收藏时间")
	@DataName(name = "收藏时间", isRecordHistory = true)
	@ApiModelProperty(value = "收藏时间", position = 8)
	@Length(max=100,message="收藏时间长度不能大于100")
	@NotBlank(message = "收藏时间不能为空")
	private String collectDatetime;



	/**
	 * 状态
	 */
	@Excel(name ="状态")
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	@NotBlank(message = "状态不能为空")
	private String status;
 }
