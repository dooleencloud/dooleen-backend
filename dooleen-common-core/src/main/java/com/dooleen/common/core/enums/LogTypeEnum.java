package com.dooleen.common.core.enums;

public enum LogTypeEnum {

	NORMAL("0", "正常"), EXCEPTION("9", "异常");

	private final String value;
	private final String label;

	LogTypeEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static LogTypeEnum getEnum(String value) {
		LogTypeEnum[] values = LogTypeEnum.values();
		for (LogTypeEnum logTypeEnum : values) {
			if (logTypeEnum.getValue().equals(value)) {
				return logTypeEnum;
			}
		}
		return null;
	}
}
