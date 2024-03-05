package com.dooleen.common.core.app.system.module.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Transient;

import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-22 10:20:14
 * @Description : information_schema.`TABLES`
 * @Author : name
 * @Update : 2020-07-22 10:20:14
 */
@Data
@TableName("TABLES")
@ApiModel
public class TableInfo {
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@Transient
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
	
	/**
	* 数据库
	*/
	@DataName(name = "数据库")
	@ApiModelProperty(value = "数据库", position = 2)
	public String tableSchema;
	
	/**
	* 表名
	*/
	@DataName(name = "表名")
	@ApiModelProperty(value = "表名", position = 3)
	public String tableName;
}
