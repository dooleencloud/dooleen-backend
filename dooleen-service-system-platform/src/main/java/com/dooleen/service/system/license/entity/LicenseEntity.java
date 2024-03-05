package com.dooleen.service.system.license.entity;

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

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-12 06:08:42
 * @Description : 系统用户组管理实体
 * @Author : name
 * @Update : 2020-06-12 06:08:42
 */
@Data
@ApiModel
public class LicenseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	* 返回信息
	*/
	public String returnMsg;

	/**
	* 生效日期
	*/
	public String effectDate;

	/**
	* 失效日期
	*/
	public String invalidDate;

 }
