//package com.dooleen.common.core.enums;
//
//public enum MsgTypeEnum {
//
//	EMAIL_TYPE("1", "邮件"), WECHAT_TYPE("2", "微信"), SMS_TYPE("3", "短信"),
//	NOTICE_TYPE("4", "公告通知"), MYADVICE_TYPE("5", "@我的通知");
//
//	private final String value;
//	private final String label;
//
//	MsgTypeEnum(String value, String label) {
//		this.value = value;
//		this.label = label;
//	}
//
//	public String getValue() {
//		return value;
//	}
//
//	public String getLabel() {
//		return label;
//	}
//
//	public static MsgTypeEnum getEnum(String value) {
//		MsgTypeEnum[] msgTypeEnums = MsgTypeEnum.values();
//		for (MsgTypeEnum msgTypeEnum : msgTypeEnums) {
//			if (msgTypeEnum.getValue().equals(value)) {
//				return msgTypeEnum;
//			}
//		}
//		return null;
//	}
//}
