package com.dooleen.service.general.note.book.entity;

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
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-23 17:01:01
 * @Description : 业务需求管理实体
 * @Author : name
 * @Update : 2020-07-23 17:01:01
 */
@Data
@TableName("general_note_book")
@ApiModel
@ToString(callSuper = true)
public class GeneralNoteBookEntity extends BaseEntity implements Serializable  {

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
	* 用户ID
	*/
	@DataName(name = "用户ID")
	@ApiModelProperty(value = "用户ID", position = 2)
	@Length(max=20,message="用户ID长度不能大于20")
	@NotBlank(message = "用户ID不能为空")
	private String userId;	  
   
	
	/**
	* 分类
	*/
	@DataName(name = "分类")
	@ApiModelProperty(value = "分类", position = 3)
	@Length(max=30,message="分类长度不能大于30")
	@NotBlank(message = "分类不能为空")
	private String category;	  
   
	
	/**
	* 标题
	*/
	@DataName(name = "标题")
	@ApiModelProperty(value = "标题", position = 4)
	@Length(max=200,message="标题长度不能大于200")
	private String title;	  
   
	

 
	/**
	* 标题颜色
	*/
	@DataName(name = "标题颜色")
	@ApiModelProperty(value = "标题颜色", position = 6)
	@Length(max=20,message="标题颜色长度不能大于20")
	private String titleColor;
	/**
	* 内容
	*/
	@DataName(name = "内容")
	@ApiModelProperty(value = "内容", position = 5)
	private String content;	  
   
	
	/**
	* 状态
	*/
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	private String status;	  
 }
