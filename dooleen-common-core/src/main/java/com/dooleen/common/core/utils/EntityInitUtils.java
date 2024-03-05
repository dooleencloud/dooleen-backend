package com.dooleen.common.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.common.entity.ExtFormEntity;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.SysHistoryInfo;
import com.dooleen.common.core.aop.annos.DataName;

/**
 * @ClassName: EntityUtils
 * @Description: 实体操作工具类
 * @author zoujin
 * @date 2019年7月18日
 * 
 */
public class EntityInitUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(EntityInitUtils.class);
	
	public static <T> T initEntityNullToEmpty(T entity) {
		if (null != entity) { 
			// 通过实例得到类
			@SuppressWarnings("rawtypes")
			// entityClass
			Class entityClass = (Class) entity.getClass().getSuperclass();
			/*
			 * 得到类中的所有属性集合
			 */
			Field[] field = entityClass.getDeclaredFields();

			for (int i = 0; i < field.length; i++) {
				Field f = field[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				String type = f.getType().toString();// 得到此属性的类型
				Object value = null;// 得到此属性的值
				try {
					value = f.get(entity);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// 如果为string类型，并且值为null，则赋值为""
				if ("class java.lang.String".equals(type)) {
					if (null == value) {
						try {
							f.set(entity, "");
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else if ("class java.lang.Integer".equals(type)) {
					if (null == value) {
						try {
							f.set(entity, 0);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return entity;
		} else {
			return null;
		}
	}

	// 更新表反设置null
	public static <T> T initEntityEmptyToNull(T entity) {
		if (null != entity) {
			// 通过实例得到类
			@SuppressWarnings("rawtypes")
			// entityClass
			Class entityClass = (Class) entity.getClass().getSuperclass();
			/*
			 * 得到类中的所有属性集合
			 */
			Field[] field = entityClass.getDeclaredFields();

			for (int i = 0; i < field.length; i++) {
				Field f = field[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				String type = f.getType().toString();// 得到此属性的类型
				Object value = null;// 得到此属性的值
				try {
					value = f.get(entity);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// 如果为string类型，并且值为null，则赋值为""
				if ("class java.lang.String".equals(type)) {
					if (null != value && value.equals("")) {
						try {
							f.set(entity, null);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else if ("class java.lang.Integer".equals(type)) {
					if (null != value && value.equals(0)) {
						try {
							f.set(entity, null);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 新增时初始化公共信息
	 * @param entity
	 * @param headEntity
	 * @param tableName
	 * @param prefix
	 * @param redisTemplate
	 * @return
	 */
	public static <T> T initEntityPublicInfoForInsertOrUpdate(T entity, HeadEntity headEntity, String tableName,String prefix,
			RedisTemplate redisTemplate) { 
		String nowDate = DateUtils.getCurTimestamp().toString();
		if (null != entity) {
			// 通过实例得到类
			@SuppressWarnings("rawtypes")
			// entityClass
			Class currEntityClass = (Class) entity.getClass();
			Class entityClass = (Class) entity.getClass().getSuperclass();
			try {
				Object idValue = currEntityClass.getDeclaredField("id").get(entity);
				// 新增时时初始化公共信息
				if (StringUtil.isEmptyAndZero(String.valueOf(idValue))) {
					currEntityClass.getDeclaredField("id").set(entity,
							GenerateNo.getId(headEntity.getTenantId(), tableName, prefix, redisTemplate));
					currEntityClass.getDeclaredField("tenantId").set(entity, headEntity.getTenantId());
					if(StringUtil.isEmpty(entityClass.getDeclaredField("validFlag").get(entity))){
						entityClass.getDeclaredField("validFlag").set(entity, "1");
					}
					entityClass.getDeclaredField("createUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("createRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("createDatetime").set(entity, nowDate);

					entityClass.getDeclaredField("updateUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("updateRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("updateDatetime").set(entity, nowDate);
				}
				// 修改时初始化公共信息
				else {
					if(StringUtil.isEmpty(currEntityClass.getDeclaredField("tenantId").get(entity))){
						currEntityClass.getDeclaredField("tenantId").set(entity, headEntity.getTenantId());						
					}
					if(StringUtil.isEmpty(entityClass.getDeclaredField("createUserName").get(entity))){
						entityClass.getDeclaredField("createUserName").set(entity, headEntity.getUserName());
						entityClass.getDeclaredField("createRealName").set(entity, headEntity.getRealName());
						entityClass.getDeclaredField("createDatetime").set(entity, nowDate);
					}
					
					entityClass.getDeclaredField("updateUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("updateRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("updateDatetime").set(entity, nowDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return entity;
		} else {
			return null;
		}
	}

	// 超级用户新增时初始化公共信息
	public static <T> T initEntityPublicInfoForInsertOrUpdateForSuper(T entity, HeadEntity headEntity, String tableName,String prefix,
			RedisTemplate redisTemplate) {
		String nowDate = DateUtils.getCurTimestamp().toString();
		if (null != entity) {
			// 通过实例得到类
			@SuppressWarnings("rawtypes")
			// entityClass
			Class currEntityClass = (Class) entity.getClass();
			Class entityClass = (Class) entity.getClass().getSuperclass();
			try {
				Object idValue = currEntityClass.getDeclaredField("id").get(entity);
				// 新增时时初始化公共信息
				if (idValue == null || idValue.equals("0")) {
					currEntityClass.getDeclaredField("id").set(entity,
							GenerateNo.getId(currEntityClass.getDeclaredField("tenantId").get(entity).toString(), tableName, prefix, redisTemplate));
					
					if(StringUtil.isEmpty(entityClass.getDeclaredField("validFlag").get(entity))){
						entityClass.getDeclaredField("validFlag").set(entity, "1");
					}
					
					entityClass.getDeclaredField("createUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("createRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("createDatetime").set(entity, nowDate);

					entityClass.getDeclaredField("updateUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("updateRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("updateDatetime").set(entity, nowDate);
				}
				// 修改时初始化公共信息
				else {
					entityClass.getDeclaredField("updateUserName").set(entity, headEntity.getUserName());
					entityClass.getDeclaredField("updateRealName").set(entity, headEntity.getRealName());
					entityClass.getDeclaredField("updateDatetime").set(entity, nowDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return entity;
		} else {
			return null;
		}
	}

	// 新增或修改时初始化公共信息
	public static <T> T initEntityPublicInfo(T entity, HeadEntity headEntity) {
		String nowDate = DateUtils.getCurTimestamp().toString();
		if (null != entity) {
			// 通过实例得到类
			@SuppressWarnings("rawtypes")
			// entityClass
			Class currEntityClass = (Class) entity.getClass();
			// Class entityClass = (Class) entity.getClass().getSuperclass();
			try {
				Object idValue = currEntityClass.getDeclaredField("id").get(entity);
				Object dataSignValue = currEntityClass.getSuperclass().getDeclaredField("dataSign").get(entity);
				// 新增时时初始化公共信息
				if (idValue == null || idValue.equals("0") ||  idValue.equals("")) {
					currEntityClass.getDeclaredField("id").set(entity, "0");
					currEntityClass.getDeclaredField("tenantId").set(entity, headEntity.getTenantId());

				}
				// 新增时时初始化dataSign
				if (dataSignValue == null || dataSignValue.equals("")) {
					currEntityClass.getSuperclass().getDeclaredField("dataSign").set(entity, "0");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return entity;
		} else {
			return null;
		}
	}

	// 新增时初始化公共信息MongoDB
	public static JSONObject initEntityPublicInfoForInsertOrUpdate(JSONObject jsonObject, HeadEntity headEntity,
			String bizCode, RedisTemplate redisTemplate) {
		String nowDate = DateUtils.getCurTimestamp().toString();
		if (!jsonObject.containsKey("id") || jsonObject.get("id").equals("")) {
			String id = GenerateNo.getId(headEntity.getTenantId(), bizCode, "DF", redisTemplate);
			jsonObject.put("_id", id);
			jsonObject.put("id", id);
			jsonObject.put("tenantId", headEntity.getTenantId());
			jsonObject.put("createUserName", headEntity.getUserName());
			jsonObject.put("createRealName", headEntity.getRealName());
			jsonObject.put("createDatetime", nowDate);

			jsonObject.put("updateUserName", headEntity.getUserName());
			jsonObject.put("updateRealName", headEntity.getRealName());
			jsonObject.put("updateDatetime", nowDate);
		}
		// 修改时初始化公共信息 
		else {
			jsonObject.put("_id", jsonObject.get("id"));
			jsonObject.put("tenantId", headEntity.getTenantId());
			jsonObject.put("updateUserName", headEntity.getUserName());
			jsonObject.put("updateRealName", headEntity.getRealName());
			jsonObject.put("updateDatetime", nowDate);
		}
		return jsonObject;
	}

	// 新增或修改时初始化公共信息MongoDB
	public static JSONObject initEntityPublicInfo(JSONObject jsonObject, ExtFormEntity extFormEntity,
			HeadEntity headEntity) {
		if (extFormEntity.getId() == null || extFormEntity.getId().equals("")) {
			extFormEntity.setBizCode(jsonObject.getString("bizCode"));
			extFormEntity.setId(jsonObject.getString("id"));
		}
		return jsonObject;
	}
	
	// 根据修改前和修改后的数据,返回历史记录实例
	public static String initHistoryRecord(JSONObject beforeData, JSONObject afterData, Class<?> entityClass, String updateRealName, String updateUserName, String sourceDataSign) throws InstantiationException, IllegalAccessException {
		
		String nowDate = DateUtils.getCurTimestamp().toString();
		
		// 历史记录
		SysHistoryInfo historyInfo = new SysHistoryInfo();
		
		// 修改前、修改后的数据（只存有变动的字段）
		Map<String, Object> beforeMap = new HashMap<String, Object>();
		Map<String, Object> afterMap = new HashMap<String, Object>();

		// 获取实体类需要记录历史的字段
		Object entity = entityClass.newInstance();
		List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
		// 需要记录历史的字段列表
		List<String> basicFileds = new ArrayList<String>();
		Map<String, String> basicFiledsMap = new HashMap<String, String>();
		// 主键列表
		List<String> keyFields = new ArrayList<String>();
		
		fields.forEach(f -> {
			DataName anno = f.getAnnotation(DataName.class);
			// 如果字段的 DataName 注解有 isRecordHistory=true,表示该字段有变更时,需要记录历史
			if(null != anno && anno.isRecordHistory()) {
				basicFileds.add(f.getName());
				basicFiledsMap.put(f.getName(), anno.name());
			}
			
			if(null != anno && anno.isKeyId()) {
				keyFields.add(f.getName());
			}
		});
		 
		// 用于判断是否有数据修改
		boolean[] isChange = {false};
		StringBuilder updateContent = new StringBuilder();
		
		beforeData.forEach((k, beforeValue) -> {
			Object afterValue = afterData.get(k);
			if(!StringUtil.isNull(afterValue) && !StringUtil.isNull(beforeValue)) {
				if((StringUtil.isEmpty(afterValue) && !StringUtil.isEmpty(beforeValue)) 
						|| (!StringUtil.isEmpty(afterValue) && StringUtil.isEmpty(beforeValue))
						|| (!afterValue.equals(beforeValue)
						&& basicFileds.contains(k))) {
					// 获取字段在实体类中的中文: String.valueOf(basicFiledsMap.get(k))
					beforeMap.put(String.valueOf(basicFiledsMap.get(k)), beforeValue);
					afterMap.put(String.valueOf(basicFiledsMap.get(k)), afterValue);
	
					updateContent.append("【").append(String.valueOf(basicFiledsMap.get(k))).append("】从【").append(beforeValue).append("】改为了【")
					   			 .append(afterValue).append("】;\n");
					
					isChange[0] = true;
				}
			}
			
			// 数据主键ID
			if(keyFields.contains(k)) {				
				historyInfo.setUpdateDataId(String.valueOf(beforeValue));
			}
			
			// 租户ID
			if("tenantId".equals(k)) {				
				historyInfo.setTenantId(String.valueOf(beforeValue));
			}
		});
		
		historyInfo.setClassName(entityClass.getSimpleName());
		historyInfo.setUpdateContent(updateContent.toString());
		historyInfo.setUpdateBeforeSourceDataObj(JSONObject.toJSONString(beforeData));
		historyInfo.setUpdateAfterSourceDataObj(JSONObject.toJSONString(afterData));
		historyInfo.setUpdateUserName(updateUserName);
		historyInfo.setUpdateRealName(updateRealName);
		historyInfo.setUpdateDatetime(nowDate);
		historyInfo.setSourceDataSign(sourceDataSign);
		
		if(isChange[0]) {			
			// 处理 修改前、修改后的数据为 json string
			historyInfo.setUpdateBeforeDataObj(JSON.toJSONString(beforeMap));
			historyInfo.setUpdateAfterDataObj(JSON.toJSONString(afterMap));
			return JSON.toJSONString(historyInfo);
		}else {
			return null;
		}		
	}
	
	/**
	 * 获取实体的id字段数据
	 */
	public static JSONObject getEntityIdAndTenantId(JSONObject data, Class<?> entityClass) {
		
		// 获取实体类需要记录历史的字段
		Object entity;
		try {
			entity = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("EntityInitUtils==>getEntityId: 实例化失败！！！");
			e.printStackTrace();
			return null;
		}
		
		List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
		List<String> keyFields = new ArrayList<String>();
		
		fields.forEach(f -> {
			DataName anno = f.getAnnotation(DataName.class);
			// 判断是否是主键
			if(null != anno && anno.isKeyId()) {
				keyFields.add(f.getName());
			}
		});
		
		JSONObject jsonObject = new JSONObject();
		
		data.forEach((k, v) -> {			
			// 数据主键ID
			if(keyFields.contains(k)) {				
				jsonObject.put("dataId", String.valueOf(v));
			}
			
			// 租户ID
			if("tenantId".equals(k)) {				
				jsonObject.put("tenantId", String.valueOf(v));
			}
			
			// 签名
			if("dataSign".equals(k)) {				
				jsonObject.put("sourceDataSign", String.valueOf(v));
			}
		});
		
		return jsonObject;
	}
}
