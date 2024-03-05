package com.dooleen.service.app.oauth.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

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
	private String effectDate;

	/**
	* 失效日期
	*/
	private String invalidDate;

 }
