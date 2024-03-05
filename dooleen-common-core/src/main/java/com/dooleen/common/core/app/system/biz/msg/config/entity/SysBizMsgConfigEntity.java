package com.dooleen.common.core.app.system.biz.msg.config.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @CreateDate : 2021-05-28 14:47:34
 * @Description : 业务消息配置管理实体
 * @Author : name
 * @Update : 2021-05-28 14:47:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_biz_msg_config")
@ApiModel
@ToString(callSuper = true)
public class SysBizMsgConfigEntity  extends BaseEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true,  isRecordHistory = true)
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
	* 业务名称
	*/
	@Excel(name ="业务名称")
	@DataName(name = "业务名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务名称", position = 2)
	@Length(max=50,message="业务名称长度不能大于50")
	@NotBlank(message = "业务名称不能为空")
	private String bizName;	  
   
	
	/**
	* 消息类型列表
	*/
	@Excel(name ="消息类型列表")
	@DataName(name = "消息类型列表", isRecordHistory = true)
	@ApiModelProperty(value = "消息类型列表", position = 3)
	@Length(max=2000,message="消息类型列表长度不能大于2000")
	@NotBlank(message = "消息类型列表不能为空")
	private String msgTypeList;	  
 }
