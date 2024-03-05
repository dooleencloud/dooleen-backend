//package com.dooleen.common.core.enums;
//
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Stream;
//
//public enum MsgSendSenceEnum {
//
//	// 修改标准变量通知
//	UPDATE_DICT_FIELD("0","修改标准变量通知"),
//	// 微信发送测试场景
//	WECHAT_TEST("i-AixHiGmgwswVrerQrIet9fVKj-0ZKY-JLM6x79mL4","微信测试场景"),
//	// 微信通知中奖
//	WECHAT_WINNER("a2LvB1AV47Dexa_E1Nr4LPF43-IYZvy7h5o7WdyhJFU","微信中奖通知"),
//	// 微信测试模板2
//	WECHAT_TEST_T("5v4Xofmc0H4yfN01Xfmi8EBcODhx8DOxHxSps9Lwwhg","微信测试模板2"),
//	//独领创新
//	WECHAT_TEST_1("MyGdZ5NKm90oD8qLMcn65j4116cCR4R99BMSNuiNhKI","独领创新")
//	;
//
//	private String id;
//	private String name;
//
//	MsgSendSenceEnum(String id, String name) {
//		this.id = id;
//		this.name = name;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public static MsgSendSenceEnum getTypeById(String id){
//		AtomicReference<MsgSendSenceEnum> tmp = new AtomicReference<>();
//		Stream.of(MsgSendSenceEnum.values()).forEach(em -> {
//			if(em.getId().equals(id)){
//				tmp.set(em);
//			}
//		});
//		return tmp.get();
//	}
//}
