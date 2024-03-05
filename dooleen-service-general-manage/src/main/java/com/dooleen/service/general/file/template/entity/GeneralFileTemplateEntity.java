package com.dooleen.service.general.file.template.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
 * @CreateDate : 2020-06-25 11:07:35
 * @Description : 文档模板管理实体
 * @Author : name
 * @Update : 2020-06-25 11:07:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_file_template")
@ApiModel
@ToString(callSuper = true)
public class GeneralFileTemplateEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键")
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
	* 类型ID
	*/
	@DataName(name = "类型ID")
	@ApiModelProperty(value = "类型ID", position = 2)
	@NotBlank(message = "类型ID不能为空")
	private String typeId;	  
   
	/**
	* 类型名称
	*/
	@DataName(name = "类型名称")
	@ApiModelProperty(value = "类型名称", position = 3)
	private String typeName;	  
   
	/**
	* 模板名称
	*/
	@DataName(name = "模板名称")
	@ApiModelProperty(value = "模板名称", position = 4)
	@NotBlank(message = "模板名称不能为空")
	private String templateName;	  
   
	/**
	* 图片链接
	*/
	@DataName(name = "图片链接")
	@ApiModelProperty(value = "图片链接", position = 5)
	@NotBlank(message = "图片链接不能为空")
	private String imgUrl;	  
   
	/**
	* 文档ID
	*/
	@DataName(name = "文档ID")
	@ApiModelProperty(value = "文档ID", position = 6)
	@NotBlank(message = "文档ID不能为空")
	private String fileId;	  
   
	/**
	* 查看标志
	*/
	@DataName(name = "查看标志")
	@ApiModelProperty(value = "查看标志", position = 7)
	@NotBlank(message = "查看标志不能为空")
	private String viewFlag;	  
	
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号")
	@ApiModelProperty(value = "排序序号", position = 7)
	private String orderSeq;	  
	
 }
