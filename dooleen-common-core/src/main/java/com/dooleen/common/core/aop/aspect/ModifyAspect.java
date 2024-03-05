package com.dooleen.common.core.aop.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.aop.annos.DataName;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.aop.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rits.cloning.Cloner;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.ExtFormEntity;
import com.dooleen.common.core.constant.HistoryDealTypeConstant;
import com.dooleen.common.core.constant.MqExchangeConstant;
import com.dooleen.common.core.mq.msg.history.UpdateHistoryMq;
import com.dooleen.common.core.mq.utils.RabbitUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.CampareJson;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:24:47
 * @Description : 拦截@EnableGameleyLog注解的方法，将具体修改存储到数据库中
 * @Author : apple
 * @Update: 2020年5月9日 下午10:24:47
 */
@Slf4j
@Aspect
public class ModifyAspect {
	private static final Cloner cloner = new Cloner();
	
	@Autowired
	private RabbitUtil rabbitUtil;
	
	private String historyData;

	@Pointcut("@annotation(com.dooleen.common.core.aop.annos.EnableSystemLog)")
	private void pointcut() {
	}

	// 执行方法前的拦截方法
	@Before("pointcut()&&@annotation(enableSystemLog)")
	public void before(JoinPoint joinPoint, EnableSystemLog enableSystemLog) throws Throwable {
		Map<String, Object> newMap = new HashMap<String, Object>();
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		log.info("---------------参数列表开始-------------------------");
		// 打印请求内容
		log.info("===============请求内容===============");
		log.info("请求头信息:" + request.getHeader("user-agent"));
		log.info("请求地址:" + request.getRequestURL().toString());
		log.info("请求方式:" + request.getMethod());
		log.info("请求类方法:" + joinPoint.getSignature());
		log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));

		// 1.获取到所有的参数值的数组
		Object[] args = joinPoint.getArgs();
		// 构造参数组集合
		List<Object> argList = new ArrayList<>();
		for (Object arg : args) {
			// request/response无法使用toJSON
			if (arg instanceof HttpServletRequest) {
				argList.add("request");
			} else if (arg instanceof HttpServletResponse) {
				argList.add("response");
			} else {
				argList.add(JSON.toJSON(arg));
			}
		}
		try {
			log.info("请求类方法参数:{}", JSON.toJSON(argList));
		} catch (Exception e) {
			log.error("参数获取失败: {}", e.getMessage());
		}
		if ("UPDATE".equals(enableSystemLog.operatoinType())) {
			// 获取controller注解上的，实体类的数据类型
			Class<?> entityClass = enableSystemLog.entityClass();
			ExtFormEntity extFormEntity = new ExtFormEntity();
			CommonMsg<Object, ExtFormEntity> newCommonMsg = CreateCommonMsg.getCommonMsg(entityClass, extFormEntity);
			// 深拷贝对象
			Object[] oldJoinPoint = cloner.deepClone(joinPoint.getArgs());
			JSONObject comObject = (JSONObject) JSON.toJSON(joinPoint.getArgs()[0]);
			//处理流程标志，走到这里来的统一将流程标志置为false
			if(!StringUtil.isEmpty(comObject.getJSONObject("body").getJSONObject("flowArea")) && !StringUtil.isEmpty(comObject.getJSONObject("body").getJSONObject("flowArea").getString("isFlow")) &&  comObject.getJSONObject("body").getJSONObject("flowArea").getString("isFlow").equals("true")) {
				comObject.getJSONObject("body").getJSONObject("flowArea").put("isFlow","false");
				JSONObject singleBody = comObject.getJSONObject("body").getJSONObject("singleBody");
				JSONObject extBody = comObject.getJSONObject("body").getJSONObject("extBody");
				newCommonMsg = JSONObject.parseObject(comObject.toJSONString(), newCommonMsg.getClass());
				//单独序列化singleBody 
				Object entity = JSONObject.parseObject(singleBody.toJSONString(), entityClass);
				Object extBodyEntity = JSONObject.parseObject(extBody.toJSONString(), ExtFormEntity.class);

				newCommonMsg.getBody().setSingleBody(entity);
				newCommonMsg.getBody().setExtBody((ExtFormEntity) extBodyEntity);
				oldJoinPoint[0] = newCommonMsg;
			}
			JSON jsonObject = (JSON) JSON.toJSON(argList);
			String jsonString = jsonObject.toJSONString().substring(1);
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			JSONObject jsonObj = JSONObject.parseObject(jsonString); 
			newMap = jsonObj.getJSONObject("body").getJSONObject("singleBody");
			//如果不是新增，则处理修改记录
			if (!jsonObj.getJSONObject("body").getJSONObject("singleBody").getString("id").equals("0")) {
				// 通过反射获取旧数据
				Class<?> cls = enableSystemLog.serviceclass();
				String methodName = enableSystemLog.queryMethodName();
				Method method = cls.getDeclaredMethod(methodName, CommonMsg.class);
				Object result = method.invoke(SpringUtil.getBean(cls), oldJoinPoint);
				CommonMsg commonMsgs = (CommonMsg) result;
				
				// 修改前数据JSONObject
				JSONObject updateBeforeData = null;
				// 修改后数据
				JSONObject updateAfterData = null;
				
				// 如果是update 操作 开始比较修改记录。
				String resultStr = "";
				
				String afterSign = "";
				//动态表单为两个json比较
				if(enableSystemLog.queryMethodName().equals("queryDynamicForm")) {
					JSONObject oldJson = (JSONObject)commonMsgs.getBody().getSingleBody();
					newMap.remove("$index");
					newMap.remove("$index1");
					resultStr =  new CampareJson().campareJsonObject(oldJson.toString(),newMap.toString());
					
					// 修改前和修改后的数据
					updateBeforeData = oldJson;
					updateAfterData = JSONObject.parseObject(JSONObject.toJSON(newMap).toString());
				}
				else {
					resultStr = defaultDealUpdate(commonMsgs.getBody().getSingleBody(), newMap);
					
					// 修改前和修改后的数据
					updateBeforeData = JSONObject.parseObject(JSONObject.toJSON(commonMsgs.getBody().getSingleBody()).toString());
					updateAfterData = JSONObject.parseObject(JSONObject.toJSON(newMap).toString());
					
					afterSign = DooleenMD5Util.md5(((CommonMsg)joinPoint.getArgs()[0]).getBody().getSingleBody().toString(), ConstantValue.md5Key);
				}
				
				String userName = String.valueOf(jsonObj.getJSONObject("head").get("userName"));
				String updateName = String.valueOf(jsonObj.getJSONObject("head").get("realName"));
				
				// 处理数据，如果有数据变更，将数据推到MQ，通过交换器处理
				historyData = EntityInitUtils.initHistoryRecord(updateBeforeData, updateAfterData, entityClass, updateName, userName, afterSign);
				
				log.info("========修改历史记录========> {}", Arrays.toString(joinPoint.getArgs()));
				log.info(resultStr);
			}
		}
	}

	private String defaultDealUpdate(Object oldObject, Map<String, Object> newMap) {
		try {
			Map<String, Object> oldMap = BeanUtils.beanToMap(oldObject);
			StringBuilder str = new StringBuilder();
			Object finalOldObject = oldObject;
			newMap.forEach((k, v) -> {
				Object oldResult = oldMap.get(k);
				if (oldResult != null && !v.toString().equals(oldResult.toString())) {
					Field field = ReflectionUtils.getAccessibleField(finalOldObject, k);
					DataName dataName = field.getAnnotation(DataName.class);
					if (dataName != null) {
						str.append("【").append(dataName.name()).append("】从【").append(oldResult).append("】改为了【")
								.append(v).append("】;\n");
					} else {
						str.append("【").append(field.getName()).append("】从【").append(oldResult).append("】改为了【")
								.append(v).append("】;\n");
					}
				}

			});
			return str.toString();

		} catch (Exception e) {
			log.error("比较异常", e);
			throw new RuntimeException("比较异常", e);
		}
	}

	/**
	 * 后置返回通知 这里需要注意的是: 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
	 * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 , returning = "keys"
	 * 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
	 */
	@AfterReturning(value = "pointcut()&&@annotation(enableSystemLog)", returning = "keys")
	public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys, EnableSystemLog enableSystemLog) {
		if (!"EXPORT".equals(enableSystemLog.operatoinType())) {
			log.info("第一个后置返回通知的返回值：{}", Arrays.toString(joinPoint.getArgs()));
			log.info("enableSystemLog.operatoinType()== {}", enableSystemLog.operatoinType());
			if("UPDATE".equals(enableSystemLog.operatoinType())) {
				// 异步发送历史数据到MQ
				if(StringUtils.isNotBlank(historyData)) {
					UpdateHistoryMq mqData = new UpdateHistoryMq();
					mqData.setHistoryRecordData(historyData);
					rabbitUtil.convertAndSend(MqExchangeConstant.HISTORY_DIRECT_EXCHANGE, HistoryDealTypeConstant.ADD, mqData);
				}
			}
		}
		// throw new RuntimeException("测试比较异常");
	}
}
