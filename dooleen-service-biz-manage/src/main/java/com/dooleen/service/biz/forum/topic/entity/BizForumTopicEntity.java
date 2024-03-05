package com.dooleen.service.biz.forum.topic.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
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
 * @CreateDate : 2021-07-21 10:17:22
 * @Description : 话题管理实体
 * @Author : name
 * @Update : 2021-07-21 10:17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_topic")
@ApiModel
@ToString(callSuper = true)
public class BizForumTopicEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
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
	* 话题标题
	*/
	@Excel(name ="话题标题")
	@DataName(name = "话题标题", isRecordHistory = true)
	@ApiModelProperty(value = "话题标题", position = 2)
	@Length(max=200,message="话题标题长度不能大于200")
	@NotBlank(message = "话题标题不能为空")
	private String topicTitle;	  
   
	
	/**
	* 话题标志
	*/
	@Excel(name ="话题标志")
	@DataName(name = "话题标志", isRecordHistory = true)
	@ApiModelProperty(value = "话题标志", position = 3)
	@Length(max=1,message="话题标志长度不能大于1")
	private String topicFlag;	  
   
	
	/**
	* 话题贴子数量1
	*/
	@Excel(name ="话题贴子数量")
	@DataName(name = "话题贴子数量", isRecordHistory = true)
	@ApiModelProperty(value = "话题贴子数量", position = 4)
    @DecimalMax(value="9999999999",message="话题贴子数量不能大于9999999999")
	@DecimalMin(value="0",message="话题贴子数量不能小于0")
	private int topicPostsCount;	  
   
	
	/**
	* 话题描述
	*/
	@Excel(name ="话题描述")
	@DataName(name = "话题描述", isRecordHistory = true)
	@ApiModelProperty(value = "话题描述", position = 5)
	@Length(max=2000,message="话题描述长度不能大于2000")
	private String topicDesc;

 }
