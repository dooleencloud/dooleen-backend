package com.dooleen.service.system.tool.column.entity;

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
 * @CreateDate : 2020-05-22 23:04:52
 * @Description : 数据库字段表实体
 * @Author : name
 * @Update : 2020-05-22 23:04:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tool_columns")
@ApiModel
@ToString(callSuper = true)
public class SysToolColumnsEntity extends BaseEntity implements Serializable  {

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
	* 数据库名
	*/
	@DataName(name = "数据库名")
	@ApiModelProperty(value = "数据库名", position = 2)
	@NotBlank(message = "数据库名不能为空")
	private String tableSchema;	  
   
	/**
	* 表名
	*/
	@DataName(name = "表名")
	@ApiModelProperty(value = "表名", position = 3)
	@NotBlank(message = "表名不能为空")
	private String tableName;	  
   
	/**
	* 字段名
	*/
	@DataName(name = "字段名")
	@ApiModelProperty(value = "字段名", position = 4)
	private String columnName;	  
   
	/**
	* 顺序
	*/
	@DataName(name = "顺序")
	@ApiModelProperty(value = "顺序", position = 5)
	private Long ordinalPosition;	  
   
	/**
	* 是否为空
	*/
	@DataName(name = "是否为空")
	@ApiModelProperty(value = "是否为空", position = 6)
	private String nullable;	  
   
	/**
	* 数据类型
	*/
	@DataName(name = "数据类型")
	@ApiModelProperty(value = "数据类型", position = 7)
	private String dataType;	  
   
	/**
	* 字段长度
	*/
	@DataName(name = "字段长度")
	@ApiModelProperty(value = "字段长度", position = 8)
	private int length;	  
   
	/**
	* 小数长度
	*/
	@DataName(name = "小数长度")
	@ApiModelProperty(value = "小数长度", position = 9)
	private int decimalLength;	  
   
	/**
	* 是否key值
	*/
	@DataName(name = "是否key值")
	@ApiModelProperty(value = "是否key值", position = 10)
	private String columnKey;	  
   
	/**
	* 字段说明
	*/
	@DataName(name = "字段说明")
	@ApiModelProperty(value = "字段说明", position = 11)
	private String columnComment;	  
   
	/**
	* 控件类型
	*/
	@DataName(name = "控件类型")
	@ApiModelProperty(value = "控件类型", position = 12)
	private String type;	  
   
	/**
	* 是否搜索
	*/
	@DataName(name = "是否搜索")
	@ApiModelProperty(value = "是否搜索", position = 13)
	private String search;	  
   
	/**
	* 搜索框多选
	*/
	@DataName(name = "搜索框多选")
	@ApiModelProperty(value = "搜索框多选", position = 14)
	private String searchMultiple;	  
   
	/**
	* 搜索文本宽
	*/
	@DataName(name = "搜索文本宽")
	@ApiModelProperty(value = "搜索文本宽", position = 15)
	private int searchLabelWidth;	  
   
	/**
	* 是否多选
	*/
	@DataName(name = "是否多选")
	@ApiModelProperty(value = "是否多选", position = 16)
	private String multiple;	  
	
	/**
	* 导出excel
	*/
	@DataName(name = "导出excel")
	@ApiModelProperty(value = "导出excel", position = 16)
	private String excel;   
	/**
	* 选项值
	*/
	@DataName(name = "选项值")
	@ApiModelProperty(value = "选项值", position = 17)
	private String dicData;	  
   
	/**
	* 列宽度
	*/
	@DataName(name = "列宽度")
	@ApiModelProperty(value = "列宽度", position = 18)
	private String width;	  
   
	/**
	* 控件宽度
	*/
	@DataName(name = "控件宽度")
	@ApiModelProperty(value = "控件宽度", position = 19)
	private String span;	  
   
	/**
	* 是否可排序
	*/
	@DataName(name = "是否可排序")
	@ApiModelProperty(value = "是否可排序", position = 20)
	private String sortable;	  
   
	/**
	* 是否必输
	*/
	@DataName(name = "是否必输")
	@ApiModelProperty(value = "是否必输", position = 21)
	private String required;	  
   
	/**
	* 必输提示语
	*/
	@DataName(name = "必输提示语")
	@ApiModelProperty(value = "必输提示语", position = 22)
	private String message;	  
   
	/**
	* 是否隐藏
	*/
	@DataName(name = "是否隐藏")
	@ApiModelProperty(value = "是否隐藏", position = 23)
	private String hide;	  
   
	/**
	* 默认值
	*/
	@DataName(name = "默认值")
	@ApiModelProperty(value = "默认值", position = 24)
	private String value;	  
   
	/**
	* 表单显示
	*/
	@DataName(name = "表单显示")
	@ApiModelProperty(value = "表单显示", position = 25)
	private String showInForm;	  
   
	/**
	* 超出隐藏
	*/
	@DataName(name = "超出隐藏")
	@ApiModelProperty(value = "超出隐藏", position = 26)
	private String overHidden;	  
   
	/**
	* 数据签名
	*/
	@DataName(name = "数据签名")
	@ApiModelProperty(value = "数据签名", position = 27)
	public String dataSign;
 }
