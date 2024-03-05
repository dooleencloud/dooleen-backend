package com.dooleen.common.core.app.system.history.entity;

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
 * @CreateDate : 2020-07-07 10:57:34
 * @Description : 历史记录表管理实体
 * @Author : name
 * @Update : 2020-07-07 10:57:34
 */
@Data
@TableName("sys_history_info")
@ApiModel
@ToString(callSuper = true)
public class SysHistoryInfoEntity extends BaseEntity implements Serializable  {

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
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;  
   
	
	/**
	* 修改数据ID
	*/
	@DataName(name = "修改数据ID")
	@ApiModelProperty(value = "修改数据ID", position = 2)
	@Length(max=20,message="修改数据ID长度不能大于20")
	@NotBlank(message = "修改数据ID不能为空")
	private String updateDataId;	  
	
	/**
	* 类名
	*/
	@DataName(name = "类名")
	@ApiModelProperty(value = "类名", position = 3)
	@Length(max=200,message="类名长度不能大于200")
	@NotBlank(message = "类名不能为空")
	private String className;	 
   
	
	/**
	* 修改之前数据对象
	*/
	@DataName(name = "修改之前数据对象")
	@ApiModelProperty(value = "修改之前数据对象", position = 4)
	private String updateBeforeDataObj;	  
   
	
	/**
	* 修改之后数据对象
	*/
	@DataName(name = "修改之后数据对象")
	@ApiModelProperty(value = "修改之后数据对象", position = 5)
	private String updateAfterDataObj;	  
	
	/**
	* 修改内容
	*/
	@DataName(name = "修改内容")
	@ApiModelProperty(value = "修改内容", position = 6)
	private String updateContent;	
	
	/**
	* 修改之前来源数据对象
	*/
	@DataName(name = "修改之前来源数据对象")
	@ApiModelProperty(value = "修改之前来源数据对象", position = 7)
	private String updateBeforeSourceDataObj;	  
   
	
	/**
	* 修改之后来源数据对象
	*/
	@DataName(name = "修改之后来源数据对象")
	@ApiModelProperty(value = "修改之后来源数据对象", position = 8)
	private String updateAfterSourceDataObj;	  
	
	/**
	* 来源数据签名
	*/
	@DataName(name = "来源数据签名")
	@ApiModelProperty(value = "来源数据签名", position = 9)
	private String sourceDataSign;	  
 }
