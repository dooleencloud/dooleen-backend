package com.dooleen.common.core.app.system.dict.entity;

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
 * @CreateDate : 2020-06-20 11:14:37
 * @Description : 系统字典管理实体
 * @Author : name
 * @Update : 2020-06-20 11:14:37
 */
@Data
@TableName("sys_dict")
@ApiModel(value = "系统字典管理实体", description= "返回响应数据")
@ToString(callSuper = true)
public class SysDictEntity extends BaseEntity implements Serializable  {

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
	@ApiModelProperty(value = "租户ID", required = true , notes = "租户好不为空测试", position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;


	/**
	* 字典分类
	*/
	@DataName(name = "字典分类")
	@ApiModelProperty(value = "字典分类", position = 2)
	@Length(max=30,message="字典分类长度不能大于30")
	private String dictCategory;
	
	/**
	* 字典编号
	*/
	@DataName(name = "字典编号")
	@ApiModelProperty(value = "字典编号", position = 2)
	@NotBlank(message = "字典编号不能为空")
	private String dictNo;	  
   
	/**
	* 字典名称
	*/
	@DataName(name = "字典名称")
	@ApiModelProperty(value = "字典名称", position = 3)
	@NotBlank(message = "字典名称不能为空")
	private String dictName;	  
   
	/**
	* 字典列表
	*/
	@DataName(name = "字典列表")
	@ApiModelProperty(value = "字典列表", position = 4)
	@NotBlank(message = "字典列表不能为空")
	private String dictList;	  
   
	/**
	* 字典状态
	*/
	@DataName(name = "字典状态")
	@ApiModelProperty(value = "字典状态", position = 5)
	private String dictStatus;	  
 }
