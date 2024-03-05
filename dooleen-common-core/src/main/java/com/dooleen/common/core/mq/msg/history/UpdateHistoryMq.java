package com.dooleen.common.core.mq.msg.history;

import java.io.Serializable;

public class UpdateHistoryMq implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 处理类型：0-新增   1-修改
	 */
	private String dealType;
	
	/**
	 * 历史记录数据
	 */
	private String historyRecordData;

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getHistoryRecordData() {
		return historyRecordData;
	}

	public void setHistoryRecordData(String historyRecordData) {
		this.historyRecordData = historyRecordData;
	}
	
}
