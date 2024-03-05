package com.dooleen.common.core.enums;

import com.dooleen.common.core.global.exception.assertion.BizExceptionAssert;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>返回结果</p>
 *
 * @author liqh
 * @date 2019.09.17
 */
@Getter
@AllArgsConstructor
public enum BizResponseEnum implements BizExceptionAssert {

    /**
     *
     */
	PROCESS_SUCCESS("S0000", "error:处理成功"),
	DATA_INVALID("E0001", "error:数据校验未通过"),
	DATA_NOT_FOUND("E0002", "error:未找到记录"),
	SAVE_DATA_FAIL("E0003", "error:保存记录失败"),
	DELETE_DATA_FAIL("E0004", "error:删除记录失败"),
	INPUT_DATA_EMPTY("E0005", "error:输入数据为空"),
	DATA_HAS_MODIFY("E0007", "error:数据已被篡改"),
	INPUT_EXT_DATA_EMPTY("E0008", "error:输入扩展数据为空"),
	USER_ID_EMPTY("E0009", "error:用户ID为空"),
	DATA_HAS_EXIST("E0010", "error:记录已存在"),
	ADMIN_HAS_EXIST("E0011", "error:管理员已存在"),
	TENANT_ID_EMPTY("E0012", "error:租户号为空"),
	TENANT_NOT_FOUND("E0013", "error:租户不存在"),
	USER_INFO_NOT_EXIST("E1001", "error:用户信息不存在"),
	USER_INPUT_PWD_ERROR("E1002", "error:用户密码输入错误"),
	USER_HAS_LOG_OFF("E1003", "error:用户已注销！"),
	USER_HAS_EXIST("E1004", "error:用户已存在！"),
	BIZ_PARAM_NOT_FOUND("E1005", "error:业务参数未找到"),
	BIZ_DICT_NOT_FOUND("E1006", "error:业务字典未找到"),
	SYS_PARAM_NOT_FOUND("E1005", "error:系统参数未找到"),
	SYS_DICT_NOT_FOUND("E1006", "error:系统字典未找到"),
	BTCH_NO_IS_EMPTY("E1007", "error:批次号不能为空'"),
	SUBJECT_TITLE_IS_EMPTY("E1008", "error:问卷题目不能为空"),
	USER_HAS_EXIST_CHANGE_PHONE("E1009", "error:该用户手机号已存在！"),
	USER_INFO_NOT_EXIST_SIXTEEN("E1010", "error:用户信息不存在"),
	USER_HAS_SUBMIT_SIXTEEN("E1011", "error:用户已提交过试卷"),
	DATA_SIGN_ERROR("E4000", "error:数据签名错误！"),
	LOGIN_USER_NAME_IS_EMPTY("E4001", "error:登录用户名为空！"),
	LOGIN_PARAM_IS_ERROR("E4002", "error:登录参数错误！"),
	UNKNOWN_LOGIN_TYPE("E4003", "error:不可识别的登录方式！"),

	DONOT_HAS_API_PRIVILEGE("E4004", "error:没有接口访问权限！"),
	TOKEN_EXPIRE("E4005", "error:登录令牌过期，请重新登录再试！"),
	LICENSE_INVALID("E4006", "error:您的证书无效，请检查服务器是否已取得授权或重新申请证书！"),
	TENANT_SETTINGS_EXPIRE("E4007", "error:租户已过有效期，请联系管理员！"),
	LICENSE_GENERAL_ERROR("E4008", "error:证书生成失败！"),
	FLOW_DATA_NOT_EXIST("E8001", "error:流程记录不存在！"),
	FLOW_NODE_DATA_NOT_EXIST("E8002", "error:流程节点记录不存在！"),
	FLOW_HAS_BEEN_DONE("E8003", "error:流程已处理过，无需重复提交！"),
	FLOW_NODE_PROCESS_DATA_NOT_EXIST("E8004", "error:流程节点处理记录不存在！"),
	CURRENT_NODE_CANNOT_REBACK("E8005", "error:该节点不支持撤回！"),
	ALL_SIGN_CANNOT_REBACK("E8006", "error:会签不支持撤回！"),
	NEXT_NODE_HAS_BEGIN("E8007", "error:下一节点已开始处理，不能撤回！"),
	PROCESS_OPITION_CANNOT_EMPTY("E8008", "error:处理意见不能为空！"),
	NEXT_NODE_IS_SUB_FLOW("E8009", "error:下一节为子流程，不能撤回！"),
	TRANS_DE_USER_CANNOT_ALL_EMPTY("E8010", "error:转给、转派、委派人员不能同时为空！"),
	NEXT_NODE_HAS_BEGIN_CANNOT_REJECT("E8011", "error:下一节点已开始处理，不能驳回！"),
	CALL_MICRO_SERVICE_ERROR("E9001", "error:调微服务失败，进入断路器"),
	REQUEST_FAST("E9002", "error:请求太频繁！"),
	THIRD_PARTY_NOT_CONFIG("E9003", "error:三方信息未配置！"),
	CONFERENCE_OCCUPIED("E9004", "error:会议室在该时间段内被占用！"),
	SYS_PARAM_NOT_CONFIG("E9005", "error:系统参数未配置！"),
	TENANT_HAS_EXIST("E9006", "error:租户已存在！"),
	ORG_HAS_EXIST("E9007", "error:机构已存在！"),
	STAFF_ID_HAS_EXIST("E9008", "error:员工ID已存在！"),
	ENCODE_EXCEPTION("E9996", "error:报文输出加密错误,请检查是否需要加密！"),
	DECODE_EXCEPTION("E9997", "error:输入报文解密异常,请检查输入！"),
	DONE_EXCEPTION("E9998", "error:处理异常"),
	OTHER_ERROR("E9999", "error:其他错误'"),
	SENTINEL_ERROR_FLOW("E9994", "error:前方太拥堵，请稍后再试！'"),
	SENTINEL_ERROR_DEGRADE("E9995", "error:前方太拥堵，请稍后再试！'")
	;


    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
}
