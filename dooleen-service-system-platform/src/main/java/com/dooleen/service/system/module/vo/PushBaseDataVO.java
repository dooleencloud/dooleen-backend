package com.dooleen.service.system.module.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PushBaseDataVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID列表
	 */
	@NotEmpty(message = "租户ID列表不能为空")
	private List<String> tenantIdList;
	
	/**
	 * 模块ID列表
	 */
	@NotEmpty(message = "模块ID列表不能为空")
	private List<String> moduleIdList;
	
	
}
