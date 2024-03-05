package com.dooleen.common.core.app.general.file.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-21 07:40:13
 * @Description : 文档信息管理实体
 * @Author : name
 * @Update : 2020-06-21 07:40:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_file_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralFileInfoEntity extends BaseEntity implements Serializable  {

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
	* 目录ID
	*/
	@DataName(name = "目录ID")
	@ApiModelProperty(value = "目录ID", position = 2)
	@NotBlank(message = "目录ID不能为空")
	private String catalogId;	  
   
	/**
	* 文档名称
	*/
	@DataName(name = "文档名称")
	@ApiModelProperty(value = "文档名称", position = 3)
	@NotBlank(message = "文档名称不能为空")
	private String fileName;	  
   
	/**
	* 文档类型
	*/
	@DataName(name = "文档类型")
	@ApiModelProperty(value = "文档类型", position = 4)
	@NotBlank(message = "文档类型不能为空")
	private String fileType;	  
   
	/**
	* 文档大小
	*/
	@DataName(name = "文档大小")
	@ApiModelProperty(value = "文档大小", position = 5)
	@NotBlank(message = "文档大小不能为空")
	private String fileSize;	  
   
	/**
	* 文档路径
	*/
	@DataName(name = "文档路径")
	@ApiModelProperty(value = "文档路径", position = 6)
	@NotBlank(message = "文档路径不能为空")
	private String filePath;	  
   
	/**
	* 文档签名
	*/
	@DataName(name = "文档签名")
	@ApiModelProperty(value = "文档签名", position = 7)
	private String fileSign;	  
   
	/**
	* 最后版本号
	*/
	@DataName(name = "最后版本号")
	@ApiModelProperty(value = "最后版本号", position = 8)
	private String lastVersionNo;	  
   
	/**
	* 有效日期
	*/
	@DataName(name = "有效日期")
	@ApiModelProperty(value = "有效日期", position = 9)
	private String validDate;	  
   
	/**
	* 分享次数
	*/
	@DataName(name = "分享次数")
	@ApiModelProperty(value = "分享次数", position = 10)
	private int shareTimes;	  

	/**
	* 分享标志
	*/
	@DataName(name = "分享标志")
	@ApiModelProperty(value = "分享标志", position = 10)
	private int shareFlag;	  
	
	/**
	* 分享有效期
	*/
	@DataName(name = "分享有效期")
	@ApiModelProperty(value = "分享有效期", position = 11)
	private String shareValidDate;	  
   
	/**
	* 拥有人ID
	*/
	@DataName(name = "拥有人ID")
	@ApiModelProperty(value = "拥有人ID", position = 12)
	private String ownerId;	  
 }
