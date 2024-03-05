package com.dooleen.common.core.app.system.msg.config.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SysMsgSceneAndType implements Serializable {

	private static final long serialVersionUID = 1L;

	// 发送场景
	List<Map<String, String>> MsgSendSceneList;

	// 消息类型
	List<Map<String, String>> MsgTypeList;
}
