package com.dooleen.common.core.app.system.module.entity;

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
 * @CreateDate : 2020-07-22 10:20:14
 * @Description : 系统数据表管理实体
 * @Author : name
 * @Update : 2020-07-22 10:20:14
 */
@Data
@TableName("sys_module_config_info")
@ApiModel
@ToString(callSuper = true)
public class SysModuleConfigInfoEntity extends BaseEntity implements Serializable  {

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
	* 模块名称
	*/
	@DataName(name = "模块名称")
	@ApiModelProperty(value = "模块名称", position = 2)
	@Length(max=50,message="模块名称长度不能大于50")
	@NotBlank(message = "模块名称不能为空")
	private String moduleName;	  
   
	
	/**
	* 关联表名列表
	*/
	@DataName(name = "关联表名列表")
	@ApiModelProperty(value = "关联表名列表", position = 3)
	@Length(max=2000,message="关联表名列表长度不能大于2000")
	@NotBlank(message = "关联表名列表不能为空")
	private String assocTableNameList;	  
 }
