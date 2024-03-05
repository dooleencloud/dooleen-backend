package com.dooleen.service.system.log.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SysLogType implements Serializable {

	private static final long serialVersionUID = 1L;

	// 日志类型
	List<Map<String, String>> sysLogTypeList;

}
