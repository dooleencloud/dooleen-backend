package com.dooleen.common.core.mq.msg;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class PushDataMq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 源租户id
	 */
	private String sourceTenantId;
	
	/**
	 * 目标租户id列表
	 */
	private List<String> targetTenantIdList;
	
	/**
	 * 模块关联表列表
	 */
	private List<String> tableNameList;
	
	/**
	 * 源租户数据
	 */
	private Map<String, List<LinkedHashMap<String, Object>>> sourceData;
}
