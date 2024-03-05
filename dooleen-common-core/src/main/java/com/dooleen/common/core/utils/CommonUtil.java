package com.dooleen.common.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dooleen.common.core.enums.BizResponseEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.ValidationResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:06:34
 * @Description : 公共处理及校验工具
 * @Author : apple
 * @Update: 2020年5月9日 下午10:06:34
 */
@Slf4j
public class CommonUtil {
	/**
	 * 服务公共日志输出
	 *
	 * @param commonMsg
	 * @return
	 */
	public static void service(CommonMsg commonMsg) {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement method = (StackTraceElement) temp[2];
		log.info("====" + method.getMethodName() + " service begin===");
		log.info(">>" + method.getMethodName() + " service 传入参数head："
				+ ReflectionToStringBuilder.toString(commonMsg.getHead(), ToStringStyle.MULTI_LINE_STYLE));
		if (commonMsg.getBody().getSingleBody() != null) {
			log.info(">>" + method.getMethodName() + " service 传入参数singleBody：" + ReflectionToStringBuilder.toString(commonMsg.getBody().getSingleBody(),ToStringStyle.MULTI_LINE_STYLE));
		}
		if (commonMsg.getBody().getListBody() != null) {
			log.info(">>" + method.getMethodName() + " service 传入参数listBody：" + ReflectionToStringBuilder.toString(commonMsg.getBody().getListBody(),ToStringStyle.MULTI_LINE_STYLE));
		}
	}

	/**
	 * 服务公共日志输出Mongodb
	 *
	 * @param commonMsg
	 * @return
	 */
	public static void serviceJson(CommonMsg commonMsg) {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement method = (StackTraceElement) temp[2];
		log.info("====" + method.getMethodName() + " service begin===");
		log.debug(">>" + method.getMethodName() + " service 传入参数head："
				+ ReflectionToStringBuilder.toString(commonMsg.getHead(), ToStringStyle.MULTI_LINE_STYLE));
		if (commonMsg.getBody().getSingleBody() != null) {
			log.debug(">>" + method.getMethodName() + " service 传入参数singleBody：" +  commonMsg.getBody().getSingleBody().toString());
		}
		if (commonMsg.getBody().getListBody() != null) {
			log.debug(">>" + method.getMethodName() + " service 传入参数listBody：" +  commonMsg.getBody().getListBody().toString());
		}
		if (commonMsg.getBody().getExtBody() != null) {
			log.debug(">>" + method.getMethodName() + " service 传入参数extBody：" + ReflectionToStringBuilder.toString(commonMsg.getBody().getExtBody(), ToStringStyle.MULTI_LINE_STYLE));
		}
	}
	/**
	 * 控制器公共日志输出
	 *
	 * @param commonMsg
	 * @return
	 */
	public static void controller(CommonMsg commonMsg) {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement method = (StackTraceElement) temp[2];
		// 获取交易编号
		GenTransSeqNo.SetTransSeqNo(commonMsg);
		log.info("====" + method.getMethodName() + " begin==== TransSeqNo:" + commonMsg.getHead().getTransSeqNo()
				+ "，path=" + method);
		log.debug(">>" + method.getMethodName() + " controller 传入参数head："
				+ ReflectionToStringBuilder.toString(commonMsg.getHead(), ToStringStyle.MULTI_LINE_STYLE));
		if (commonMsg.getBody().getSingleBody() != null) {
			log.debug(">>" + method.getMethodName() + " controller 传入参数singleBody：" + ReflectionToStringBuilder.toString(commonMsg.getBody().getSingleBody(),ToStringStyle.MULTI_LINE_STYLE));
		}
		if (commonMsg.getBody().getListBody() != null) {
			log.debug(">>" + method.getMethodName() + " controller 传入参数listBody：" + ReflectionToStringBuilder.toString(commonMsg.getBody().getListBody(),ToStringStyle.MULTI_LINE_STYLE));
		}
		// 设置链路信息
		SetChainPathUtils.setChainPathInfo(commonMsg, method.toString());
	}
	/**
	 * 控制器公共日志输出mongodb
	 *
	 * @param commonMsg
	 * @return
	 */
	public static void controllerJson(CommonMsg commonMsg) {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement method = (StackTraceElement) temp[2];
		// 获取交易编号
		GenTransSeqNo.SetTransSeqNo(commonMsg);
		log.info("====" + method.getMethodName() + " begin==== TransSeqNo:" + commonMsg.getHead().getTransSeqNo()
				+ "，path=" + method);
		log.debug(">>" + method.getMethodName() + " controller 传入参数head："
				+ ReflectionToStringBuilder.toString(commonMsg.getHead(), ToStringStyle.MULTI_LINE_STYLE));
		if (commonMsg.getBody().getSingleBody() != null) {
			log.debug(">>" + method.getMethodName() + " controller 传入参数singleBody：" + commonMsg.getBody().getSingleBody().toString());
		}
		if (commonMsg.getBody().getListBody() != null) {
			log.debug(">>" + method.getMethodName() + " controller 传入参数listBody：" + commonMsg.getBody().getListBody().toString());
		}
		// 设置链路信息
		SetChainPathUtils.setChainPathInfo(commonMsg, method.toString());
	}
	/**
	 * 判断输入内容是否为空
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgSingleBodyIsNotNull(CommonMsg commonMsg) {
		Map<String, String> map = new HashMap<String, String>();
		// 如果内容为空返回错误信息
		if (commonMsg.getBody().getSingleBody() == null) {
			// 设置失败返回信息
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		}
		return true;
	}
	/**
	 * 判断输入扩展内容是否为空
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgExtBodyIsNotNull(CommonMsg commonMsg) {
		Map<String, String> map = new HashMap<String, String>();
		// 如果内容为空返回错误信息
		if (commonMsg.getBody().getExtBody() == null) { 
			// 设置失败返回信息 
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		}
		return true;
	}	
	
	/**
	 * 判断输入内容是否为空
	 *
	 * @param commonMsg
	 * @return boolean 
	 */
	public static boolean commonMsgBizCodeIsNotNull(CommonMsg commonMsg) {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = (JSONObject)commonMsg.getBody().getSingleBody();
		// 如果内容为空返回错误信息
		if (jsonObject.get("bizCode") == null) {
			// 设置失败返回信息
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		}
		return true;
	}

	/**
	 * listBody长度是否为0
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgListBodyLength(CommonMsg commonMsg, String type) {
		Map<String, String> map = new HashMap<String, String>();
		// 如果内容为空返回错误信息
		if (commonMsg.getBody().getListBody().size() == 0) {
			// 设置失败返回信息
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		} else if (type.equals("checkContent")) {
			for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
				// 校验实体字段
				ValidationResult validationResult = ValidationUtils
						.validateEntity(commonMsg.getBody().getListBody().get(i));
				boolean isError = validationResult.isHasErrors();
				// 检查前端传入字段
				if (isError == true) {
					map = validationResult.getErrorMsg();
					// 设置失败返回信息
					map.put("E0001", "第" + i + "条" + RespStatus.json.getString("E0001"));
					// 设置失败返回值
					commonMsg.getHead().setRespCode(RespStatus.errorCode);
					commonMsg.getHead().setRespMsg(map);
					log.error(">> 第" + i + "条数据校验未通过", map);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验数据合法性
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgValidation(CommonMsg commonMsg) {
		Map<String, String> map = new HashMap<String, String>();
		// 校验输入字段
		ValidationResult validationResult = ValidationUtils.validateEntity(commonMsg.getBody().getSingleBody());
		boolean isError = validationResult.isHasErrors();
		// 检查前端传入字段
		if (isError == true) {
			map = validationResult.getErrorMsg();
			List<String> msgList = new ArrayList<>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				msgList.add(entry.getValue());
			}
			log.error(">> 数据校验未通过:"+map.toString());
			BizResponseEnum.DATA_INVALID.assertIsTrue(false,commonMsg,msgList);

			return false;
		} else {
			// 校验头信息输入字段
			validationResult = ValidationUtils.validateEntity(commonMsg.getHead());
			isError = validationResult.isHasErrors();
			// 检查前端传入字段
			if (isError == true) {
				map = validationResult.getErrorMsg();
				List<String> msgList = new ArrayList<>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					msgList.add(entry.getValue());
				}
				log.error(">> 数据校验未通过:"+map.toString());
				BizResponseEnum.DATA_INVALID.assertIsTrue(false,commonMsg,msgList);
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否修改内容
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgIsUpdateContent(CommonMsg commonMsg, String oldSign) {
		Map<String, String> map = new HashMap<String, String>();
		// 通过dataSign判断singleBody内容是否有修改, 若没修改直接返回
		log.info("update = === "+commonMsg.getBody().getSingleBody().toString());
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		log.info("update22 = === "+ bodySign);
		if (bodySign.equals(oldSign)) {
			log.info("===内容未做更改,直接返回成功！sign:" + bodySign + " =====");
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			map.put("S0000", RespStatus.json.getString("S0000"));
			commonMsg.getHead().setRespMsg(map);
			log.info("====updateSysTables service end == " + RespStatus.json.getString("S0000"));
			return false;
		}
		return true;
	}

	/**
	 * 判断修改内容是否被其他用户修改
	 *
	 * @param commonMsg
	 * @return boolean
	 */
	public static boolean commonMsgIsChangedByOthers(CommonMsg commonMsg, CommonMsg queryCommonMsg) {
		Map<String, String> map = new HashMap<String, String>();
		// 记录不存在返回错误
		if (queryCommonMsg.getBody().getSingleBody() == null) {
			// 设置失败返回信息
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		} else {
			// 根据数据签名判断数据是否已做更改,如果已更改则报错。
			JSONObject queryCommonMsgJson = (JSONObject) JSON.toJSON(queryCommonMsg);
			JSONObject commonMsgJson = (JSONObject) JSON.toJSON(commonMsg);
			if (!queryCommonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("dataSign").equals(commonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("dataSign"))) {
				//log.info("===queryCommonMsgJson sign === "+queryCommonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("dataSign"));
				//log.info("===commonMsgJson sign ===" + commonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("dataSign"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				BizResponseEnum.DATA_HAS_MODIFY.assertIsTrue(false,commonMsg,"[修改人："+queryCommonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("updateRealName")+"("+queryCommonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("updateUserName")+")]");
				return false;
			}
		}
		return true;
	}
	/**
	 * 设置成功返回码
	 * @param commonMsg
	 */
	public static void successReturn(CommonMsg commonMsg) {
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement method = (StackTraceElement) temp[2];
		log.info("====" + method.getMethodName() + " service end == " + RespStatus.json.getString("S0000"));
	}

	public static boolean commonMsgValidationId(CommonMsg commonMsg) {
		// 检查前端传入字段
		Map<String, String> map = new HashMap<String, String>(2);
		JSONObject commonMsgJson = (JSONObject) JSON.toJSON(commonMsg);
		if (StringUtil.isEmpty(commonMsgJson.getJSONObject("body").getJSONObject("singleBody").getString("id"))) {
			BizResponseEnum.INPUT_DATA_EMPTY.assertIsTrue(false,commonMsg);
			return false;
		}		
		return true;
	}
}