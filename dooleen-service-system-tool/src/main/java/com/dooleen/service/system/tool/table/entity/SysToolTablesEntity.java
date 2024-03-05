package com.dooleen.service.system.tool.table.entity;

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
 * @CreateDate : 2020-05-21 23:45:59
 * @Description : 系统表实体
 * @Author : name
 * @Update : 2020-05-21 23:45:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tool_tables")
@ApiModel
@ToString(callSuper = true)
public class SysToolTablesEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	/**
	* 系统名
	*/
	@DataName(name = "系统名")
	@ApiModelProperty(value = "系统名", position = 2)
	@NotBlank(message = "系统名不能为空")
	private String tableCatalog;
   
	/**
	* 数据库
	*/
	@DataName(name = "数据库")
	@ApiModelProperty(value = "数据库", position = 3)
	@NotBlank(message = "数据库不能为空")
	private String tableSchema;
   
	/**
	* 表名
	*/
	@DataName(name = "表名")
	@ApiModelProperty(value = "表名", position = 4)
	@NotBlank(message = "表名不能为空")
	private String tableName;
   
	/**
	* 表备注
	*/
	@DataName(name = "表备注")
	@ApiModelProperty(value = "表备注", position = 5)
	@NotBlank(message = "表备注不能为空")
	private String tableComment;
   
	/**
	* 表类型
	*/
	@DataName(name = "表类型")
	@ApiModelProperty(value = "表类型", position = 6)
	private String tableType;
   
	/**
	* 主键
	*/
	@DataName(name = "主键")
	@ApiModelProperty(value = "主键", position = 7)
	private String primaryKey;
   
	/**
	* 服务名称
	*/
	@DataName(name = "服务名称")
	@ApiModelProperty(value = "服务名称", position = 8)
	private String serviceName;
   
	/**
	* 工程名
	*/
	@DataName(name = "工程名")
	@ApiModelProperty(value = "工程名", position = 9)
	private String projectName;
   
	/**
	* 模块名称
	*/
	@DataName(name = "模块名称")
	@ApiModelProperty(value = "模块名称", position = 10)
	private String moduleName;
   
	/**
	* 后端包名
	*/
	@DataName(name = "后端包名")
	@ApiModelProperty(value = "后端包名", position = 11)
	private String packageName;
   
	/**
	* 前后端标志
	*/
	@DataName(name = "前后端标志")
	@ApiModelProperty(value = "前后端标志", position = 12)
	private String backFrontFlag;
   
	/**
	* 后端路径
	*/
	@DataName(name = "后端路径")
	@ApiModelProperty(value = "后端路径", position = 13)
	private String backendPath;
   
	/**
	* 前端路径
	*/
	@DataName(name = "前端路径")
	@ApiModelProperty(value = "前端路径", position = 14)
	private String frontendPath;
   
	/**
	* 版本
	*/
	@DataName(name = "版本")
	@ApiModelProperty(value = "版本", position = 15)
	private int version;
   
	/**
	* 是否已删除
	*/
	@DataName(name = "是否已删除")
	@ApiModelProperty(value = "是否已删除", position = 16)
	private String isDeleted;
	/**
	* 菜单编号
	*/
	@DataName(name = "菜单编号")
	@ApiModelProperty(value = "菜单编号", position = 21)
	private String menuNo;
	/**
	* 索引1
	*/
	@DataName(name = "索引1")
	@ApiModelProperty(value = "索引1", position = 17)
	private String index1;
   
	/**
	* 索引2
	*/
	@DataName(name = "索引2")
	@ApiModelProperty(value = "索引2", position = 18)
	private String index2;
   
	/**
	* 索引3
	*/
	@DataName(name = "索引3")
	@ApiModelProperty(value = "索引3", position = 19)
	private String index3;
   
	/**
	* 索引4
	*/
	@DataName(name = "索引4")
	@ApiModelProperty(value = "索引4", position = 20)
	private String index4;
   
	/**
	* 索引5
	*/
	@DataName(name = "索引5")
	@ApiModelProperty(value = "索引5", position = 21)
	private String index5;
   
 }
