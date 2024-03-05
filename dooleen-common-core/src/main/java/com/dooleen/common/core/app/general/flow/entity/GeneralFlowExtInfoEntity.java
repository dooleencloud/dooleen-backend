package com.dooleen.common.core.app.general.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程扩展信息表
 * @Author : name
 * @Update : 2020-06-28 18:24:13
 */
@Data
public class GeneralFlowExtInfoEntity {

    /**
    * 业务数据id
    */
	public String bizId;

	/**
	* 表单ID
	*/
	private String formId;

	/**
	 * 流程ID，默认通过业务编码从字典中获取。支持外面传入
	 */
	private String flowId;

	/**
	 * 业务编码，自定义别重名
	 */
	private String bizCode;

	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 业务名称 如：业务需求、计划管理等
	 */
	private String bizName;

	/**
	 * 表单类型 1-常规表单， 2-动态表单
	 */
	private String formType = "1";
 }
