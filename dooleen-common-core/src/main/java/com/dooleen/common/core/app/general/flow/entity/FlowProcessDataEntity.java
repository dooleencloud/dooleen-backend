package com.dooleen.common.core.app.general.flow.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程信息管理实体
 * @Author : name
 * @Update : 2020-06-28 18:24:13
 */
@Data
@ApiModel(value="流程处理报文体")
public class FlowProcessDataEntity implements Serializable  {

	private static final long serialVersionUID = 1L;
	/**
	* 是否流程标志
	*/
	@ApiModelProperty(value = "是否流程标志", position = 1)
	@TableField(exist = false)
	private String isFlow;
	
	/**
	* 流程ID
	*/
	@ApiModelProperty(value = "流程ID", position = 1)
	@TableField(exist = false)
	private String flowId;

	/**
	* 流程启动方式0-手工 1-自动
	*/
	@ApiModelProperty(value = "流程启动方式0-手工、1-自动", position = 1)
	@TableField(exist = false)
	private String flowBeginWay;
	
	/**
	* 流程处理记录ID
	*/
	@ApiModelProperty(value = "流程处理记录ID", position = 1)
	@TableField(exist = false)
	private String processRecordId;
	
	/**
	* 流程操作标志 1-开始启动 2-处理流程 3-回退流程
	*/
	@ApiModelProperty(value = "流程操作标志 1-开始启动 2-处理流程 3-回退流程", position = 1)
	@TableField(exist = false)
	private String flowOptFlag;
	
	/**
	* 0-开始流程，1-过程流程 2-终点
	*/
	@ApiModelProperty(value = "流程结束状态0-开始流程，1-过程流程 2-终点", position = 1)
	@TableField(exist = false)
	private String flowEndFlag;
	
	/**
	* 动态表单定义扩展区域
	*/
	@ApiModelProperty(value = "动态表单定义扩展区域", position = 1)
	@TableField(exist = false)
	private JSONObject formExtData;
	
	/**
	* 审批扩展区域
	*/
	@ApiModelProperty(value = "流程审批扩展区域", position = 1)
	@TableField(exist = false)
	private JSONObject flowExtData;
	
 }
