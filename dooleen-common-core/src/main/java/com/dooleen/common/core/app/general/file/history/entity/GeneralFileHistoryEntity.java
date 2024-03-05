package com.dooleen.common.core.app.general.file.history.entity;

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
 * @CreateDate : 2020-06-21 07:48:23
 * @Description : 文档历史管理实体
 * @Author : name
 * @Update : 2020-06-21 07:48:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_file_history")
@ApiModel
@ToString(callSuper = true)
public class GeneralFileHistoryEntity extends BaseEntity implements Serializable  {

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
	* 文档ID
	*/
	@DataName(name = "文档ID")
	@ApiModelProperty(value = "文档ID", position = 2)
	@NotBlank(message = "文档ID不能为空")
	private String fileId;	  
   
	/**
	* 文档名称
	*/
	@DataName(name = "文档名称")
	@ApiModelProperty(value = "文档名称", position = 3)
	@NotBlank(message = "文档名称不能为空")
	private String fileName;	  
   
	/**
	* 文档路径
	*/
	@DataName(name = "文档路径")
	@ApiModelProperty(value = "文档路径", position = 4)
	@NotBlank(message = "文档路径不能为空")
	private String filePath;	  
   
	/**
	* 文档版本号
	*/
	@DataName(name = "文档版本号")
	@ApiModelProperty(value = "文档版本号", position = 5)
	@NotBlank(message = "文档版本号不能为空")
	private String fileVersionNo;	  
   
	/**
	* 文档签名
	*/
	@DataName(name = "文档签名")
	@ApiModelProperty(value = "文档签名", position = 6)
	private String fileSign;



	/**
	 * 文档大小
	 */
	@DataName(name = "文档大小", isRecordHistory = true)
	@ApiModelProperty(value = "文档大小", position = 6)
	private String fileSize;
 }
