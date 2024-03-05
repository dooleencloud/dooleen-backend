package com.dooleen.service.system.form.general.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowExtInfoEntity;
import com.dooleen.common.core.app.general.flow.util.GeneralFlowProcessUtil;
import com.dooleen.common.core.app.system.form.entity.SysDynamicFormEntity;
import com.dooleen.common.core.app.system.form.mapper.SysDynamicFormMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.config.db.mongodb.MongoTemplateUtil;
import com.dooleen.common.core.config.utils.PageModel;
import com.dooleen.common.core.config.utils.SpringPageable;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.system.form.general.service.GeneralDynamicFormService;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-24 21:41:40
 * @Description : 系统动态表单管理服务实现
 * @Author : apple
 * @Update: 2020-05-24 21:41:40
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralDynamicFormServiceImpl implements GeneralDynamicFormService {

	public static final String CHECK_CONTENT = "checkContent";
	public static final String DELETE = "delete";
	private static String bizCode = "";
	@Autowired
	private GeneralFlowProcessUtil generalFlowProcessUtil;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Autowired
	private MongoTemplateUtil mongoTemplateUtil;

	@Autowired
	private  SysDynamicFormMapper sysDynamicFormMapper;
  	
	@Override
	@Cacheable(value = "DynamicForm", key = "#commonMsg.body.extBody.bizCode+#commonMsg.body.extBody.id", condition = "#commonMsg.body.flowArea.isFlow ne 'true'")
	public CommonMsg<JSONObject, ExtFormEntity> queryDynamicForm(CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		bizCode = commonMsg.getBody().getExtBody().getBizCode();
		
		// 查询记录
		JSONObject jsonObject = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).findById(commonMsg.getBody().getExtBody().getId(),JSONObject.class, bizCode);
		if (jsonObject == null) {
			// 设置失败返回信息
			map.put("E0002", RespStatus.json.getString("E0002"));
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			log.error("===error: "+map.toString());
			return commonMsg;
		} else {
			//获取流程数据
			generalFlowProcessUtil.queryExtData(commonMsg);

			//设置返回数据
			commonMsg.getBody().setSingleBody(jsonObject);
			commonMsg.getBody().setListBody(null);
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			map.put("S0000", RespStatus.json.getString("S0000"));
			commonMsg.getHead().setRespMsg(map);
		}
		
		return commonMsg;
	}

	@Override
	public CommonMsg<JSONObject, JSONObject> queryDynamicFormListPage(CommonMsg<JSONObject, JSONObject> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		if(!CommonUtil.commonMsgBizCodeIsNotNull(commonMsg)) {
			return commonMsg;
		}
		bizCode = commonMsg.getBody().getSingleBody().get("bizCode").toString();
		
		// 默认以传入的sqlCondition查询
		Criteria criteria = new Criteria();
		// 设定只查当前租户ID条件
		criteria.and("tenantId").is(commonMsg.getHead().getTenantId());
		criteria.and("bizCode").is(bizCode);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = (Map<String, Object>)commonMsg.getBody().getSingleBody();

		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if (entry.getValue() != null && !entry.getKey().equals("bizCode")) {
				if (!(StringUtil.isNumeric(entry.getValue().toString())
						&& Integer.parseInt(entry.getValue().toString()) == 0)) {
					criteria.and(entry.getKey()).is(String.valueOf(entry.getValue()));
					isInSingleBody = true;
				}
			}
		}

		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			log.debug(commonMsg.getBody().getSqlCondition().size()+ "========解析"+commonMsg.getBody().getSqlCondition()+"组装查询条件==");
			for (int i = 0; i < commonMsg.getBody().getSqlCondition().size(); i++) {
				switch (commonMsg.getBody().getSqlCondition().get(i).getType()) {
				case "=":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.is(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case ">":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.gt(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "≥":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.gte(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "<":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.lt(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "≤":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.lte(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "like":
					 //模糊匹配
			        Pattern pattern = Pattern.compile("^.*"+ commonMsg.getBody().getSqlCondition().get(i).getValue() +".*$", Pattern.CASE_INSENSITIVE);
			        criteria.andOperator(Criteria.where(commonMsg.getBody().getSqlCondition().get(i).getColumn()).regex(pattern));
					break;
				}
			}
		}
		// 处理排序
		Sort.Order order = Sort.Order.asc("createDatetime");
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					order = Sort.Order.asc(sqlOrderList.get(i).getProp());
				}
				else
				{
					order = Sort.Order.desc(sqlOrderList.get(i).getProp());
				}
			}
		}
		//翻页查询处理
		SpringPageable pageable = new SpringPageable();
		PageModel pm = new PageModel();
		Query query = new Query(criteria);
		Sort sort = Sort.by(order);
		pm.setPagenumber(commonMsg.getBody().getCurrentPage());
		pm.setPagesize(commonMsg.getBody().getPageSize());
		pm.setSort(sort);
		pageable.setPage(pm);
		Long total = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).count(query, JSONObject.class, bizCode);
		List<JSONObject> list = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).find(query.with(pageable), JSONObject.class, bizCode);
		for (int i = 0; i < list.size(); i++) {
			log.info("============== {}", list.get(i).toString());
		}
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setTotal(total);
		if (list.size() == 0) {
			// 设置失败返回信息
			map.put("E0002", RespStatus.json.getString("E0002"));
			// 设置失败返回值 RespStatus.errorCode可以返回查询为空的数据
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			commonMsg.getHead().setRespMsg(map);
			log.error("===error: ", map);
			return commonMsg;
		} else {
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			map.put("S0000", RespStatus.json.getString("S0000"));
			commonMsg.getHead().setRespMsg(map);
		}
		
		return commonMsg;
	}

	@Override
	@CachePut(value = "DynamicForm", key = "#commonMsg.body.extBody.bizCode+#commonMsg.body.extBody.id", condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<JSONObject, ExtFormEntity> saveDynamicForm(CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (!CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		//获取bizCode
		if(null != commonMsg.getBody().getExtBody().getBizCode() &&  !commonMsg.getBody().getExtBody().getBizCode().equals("")) {
			bizCode = commonMsg.getBody().getExtBody().getBizCode();
		}
		else
		{  
			bizCode = commonMsg.getBody().getSingleBody().getString("bizCode");
		}
		// 如果是自动启动流程将流程状态置为1
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true") && commonMsg.getBody().getFlowArea().getFlowBeginWay().equals("1")) {
			commonMsg.getBody().getSingleBody().put("flowStatus","1");
		}
		
		// 初始化公共值
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),commonMsg.getHead(), bizCode, redisTemplate));
		// md5不包含dataSign
		commonMsg.getBody().getSingleBody().put("dataSign","");
		commonMsg.getBody().getSingleBody().put("dataSign", DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(), ConstantValue.md5Key));
		// 添加记录
		JSONObject jsonObject = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId())
				.insert(commonMsg.getBody().getSingleBody(), bizCode);
		//将ID,bizCode赋值给extBody
		if(jsonObject.containsKey("id")) {
			commonMsg.getBody().getExtBody().setId(jsonObject.get("id").toString());
			if(null == commonMsg.getBody().getExtBody().getBizCode() ||  commonMsg.getBody().getExtBody().getBizCode().equals("")) {
				commonMsg.getBody().getExtBody().setBizCode(jsonObject.get("bizCode").toString());
			} 
		}
		//自动启动流程
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true") && commonMsg.getBody().getFlowArea().getFlowBeginWay().equals("1")) {
			GeneralFlowExtInfoEntity generalFlowExtInfoEntity = new GeneralFlowExtInfoEntity();
			generalFlowExtInfoEntity.setBizType("dynamic");
			generalFlowExtInfoEntity.setFormType("2");
			generalFlowExtInfoEntity.setBizId(jsonObject.get("id").toString());
			generalFlowExtInfoEntity.setBizName(jsonObject.get("title").toString());
			//generalFlowExtInfoEntity.setBizCode(jsonObject.get("bizCode").toString());
			generalFlowExtInfoEntity.setFormId(commonMsg.getBody().getSingleBody().get("formId").toString());
			generalFlowExtInfoEntity.setFlowId(commonMsg.getBody().getFlowArea().getFlowId());
			//处理流程
			generalFlowProcessUtil.handleProccess(commonMsg, generalFlowExtInfoEntity);
			//判断流程是否处理成功，失败直接返回
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
		}

		commonMsg.getHead().setRespCode(RespStatus.succCode);// RespStatus.succCode
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, JSONObject> saveDynamicFormList(CommonMsg<NullEntity, JSONObject> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果listBody为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		// 初始化数据
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i),commonMsg.getHead(), bizCode, redisTemplate));
			// md5不包含dataSign
			commonMsg.getBody().getListBody().get(i).put("dataSign","");
			commonMsg.getBody().getListBody().get(i).put("dataSign",
					DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ""));
			mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId())
					.insert(commonMsg.getBody().getListBody().get(i), bizCode);
		}

		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		
		return commonMsg;
	}

	@Override
	@CachePut(value = "DynamicForm", key = "#commonMsg.body.extBody.bizCode+#commonMsg.body.extBody.id", condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<JSONObject, ExtFormEntity> updateDynamicForm(CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		// 如果ExtBody为空返回错误信息
		if (!CommonUtil.commonMsgExtBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		//处理流程
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			GeneralFlowExtInfoEntity generalFlowExtInfoEntity = new GeneralFlowExtInfoEntity();
			generalFlowExtInfoEntity.setBizType("dynamic");
			generalFlowExtInfoEntity.setFormType("2");
			generalFlowExtInfoEntity.setBizId(commonMsg.getBody().getSingleBody().get("id").toString());
			generalFlowExtInfoEntity.setBizName(commonMsg.getBody().getSingleBody().get("title").toString());
			//generalFlowExtInfoEntity.setBizCode(commonMsg.getBody().getSingleBody().get("bizCode").toString());
			generalFlowExtInfoEntity.setFormId(commonMsg.getBody().getSingleBody().get("formId").toString());
			generalFlowExtInfoEntity.setFlowId(commonMsg.getBody().getFlowArea().getFlowId());

			generalFlowProcessUtil.handleProccess(commonMsg, generalFlowExtInfoEntity);
			//判断流程是否处理成功，失败直接返回
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
		}

		String oldSign = commonMsg.getBody().getSingleBody().getString("dataSign");
		// 清空dataSign
		commonMsg.getBody().getSingleBody().put("dataSign","");
		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, oldSign)) {
			commonMsg.getBody().getSingleBody().put("dataSign",oldSign);
			return commonMsg;
		}
		// 恢复dataSign
		commonMsg.getBody().getSingleBody().put("dataSign",oldSign);
		
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<JSONObject, ExtFormEntity> queryCommonMsg = new CommonMsg<JSONObject, ExtFormEntity>();
		// 定义singleBody
		JSONObject singleBody = commonMsg.getBody().getSingleBody();
		// 定义body
		MutBean<JSONObject, ExtFormEntity> mutBean = new MutBean<JSONObject, ExtFormEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		flowArea.setIsFlow("false");
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		mutBean.setExtBody(commonMsg.getBody().getExtBody());
		// 深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(GeneralDynamicFormServiceImpl.class).queryDynamicForm(queryCommonMsg);
		// 判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}

		Map<String, String> map = new HashMap<String, String>(2);
		
		// 初始化公共值
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),commonMsg.getHead(), bizCode, redisTemplate));
		// 设置需要修改的签名，dataSign值不参与签名
		commonMsg.getBody().getSingleBody().put("dataSign", "");
		commonMsg.getBody().getSingleBody().put("dataSign", DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(), ConstantValue.md5Key));
		bizCode = commonMsg.getBody().getExtBody().getBizCode();
		// 保存记录
		JSONObject jsonObject = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId())
				.save(commonMsg.getBody().getSingleBody(), bizCode);
		
		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		
		return commonMsg;
	}

	@Override
	@CachePut(value = "DynamicForm", key = "#commonMsg.body.extBody.bizCode+#commonMsg.body.extBody.id", condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<JSONObject, ExtFormEntity> saveOrUpdateDynamicForm(CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		String oldSign = commonMsg.getBody().getSingleBody().getString("dataSign");
		// 移除$index
		commonMsg.getBody().getSingleBody().remove("$index");
		// 清空dataSign
		commonMsg.getBody().getSingleBody().put("dataSign","");
		// 判断内容是否发生更改
	  if (!commonMsg.getBody().getSingleBody().containsKey("id")&& !CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getString("dataSign"))) {
			return commonMsg;
		}
		// 恢复dataSign
		commonMsg.getBody().getSingleBody().put("dataSign",oldSign);
		
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<JSONObject, ExtFormEntity> queryCommonMsg = new CommonMsg<JSONObject, ExtFormEntity>();
		// 定义singleBody
		JSONObject singleBody = new JSONObject();
		// 定义body
		MutBean<JSONObject, ExtFormEntity> mutBean = new MutBean<JSONObject, ExtFormEntity>();
		mutBean.setSingleBody(singleBody);
		mutBean.setExtBody(commonMsg.getBody().getExtBody());
		// 深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(GeneralDynamicFormServiceImpl.class).queryDynamicForm(queryCommonMsg);

		// 判断修改内容是否被其他用户修改
		if (commonMsg.getBody().getSingleBody().containsKey("id") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}

		Map<String, String> map = new HashMap<String, String>(2);
		
		// 初始化公共值
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),commonMsg.getHead(), bizCode, redisTemplate));
		// 设置需要修改的签名，dataSign值不参与签名
		commonMsg.getBody().getSingleBody().put("dataSign", "");
		commonMsg.getBody().getSingleBody().put("dataSign", DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(), ConstantValue.md5Key));
		bizCode = commonMsg.getBody().getExtBody().getBizCode();
		// 添加记录
		JSONObject jsonObject = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId())
				.save(commonMsg.getBody().getSingleBody(), bizCode);
		
		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, JSONObject> saveOrUpdateDynamicFormList(CommonMsg<NullEntity, JSONObject> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}

		Map<String, String> map = new HashMap<String, String>(2);
		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			// 初始化公共值
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i),commonMsg.getHead(), bizCode, redisTemplate));
			// 设置需要修改的签名，dataSign值不参与签名
			commonMsg.getBody().getListBody().get(i).put("dataSign", "");
			commonMsg.getBody().getListBody().get(i).put("dataSign", DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(), ConstantValue.md5Key));
			bizCode = commonMsg.getBody().getListBody().get(i).getString("bizCode");
			// 添加记录
			mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId())
					.save(commonMsg.getBody().getListBody().get(i), bizCode);
		}
		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("S0000", RespStatus.json.getString("S0000"));
		commonMsg.getHead().setRespMsg(map);
		
		return commonMsg;
	}

	@Override
	@CacheEvict(value = "DynamicForm", key = "#commonMsg.body.extBody.bizCode+#commonMsg.body.extBody.id", condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<JSONObject, ExtFormEntity> deleteDynamicForm(CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		bizCode = commonMsg.getBody().getExtBody().getBizCode();
		// 删除记录
		DeleteResult result= mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).remove(commonMsg.getBody().getSingleBody(), bizCode);
		// 删除失败
		if (result.getDeletedCount() == 0) {
			// 设置失败返回信息
			map.put("E0006", RespStatus.json.getString("E0006"));
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("===error: ", map);
			return commonMsg;
		} else {
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			map.put("S0000", RespStatus.json.getString("S0000"));
			commonMsg.getHead().setRespMsg(map);
		}
		
		return commonMsg;
	}

	@Override
	public CommonMsg<ExtFormEntity, JSONObject> deleteDynamicFormList(CommonMsg<ExtFormEntity, JSONObject> commonMsg) {
		CommonUtil.serviceJson(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		bizCode = commonMsg.getBody().getSingleBody().getBizCode();
		String[] keylist = new String[50];
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist[i] = commonMsg.getBody().getListBody().get(i).getString("id");
		}
		Query query = Query.query(Criteria.where("id").in(keylist));
		DeleteResult result= mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).remove(query, bizCode);
		// 执行删除
		// 删除失败
		if (result.getDeletedCount() == 0) {
			// 设置失败返回信息
			map.put("E0006", RespStatus.json.getString("E0006"));
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("===error: ", map);
			return commonMsg;
		} else {
			commonMsg.getHead().setRespCode(RespStatus.succCode);
			map.put("S0000", RespStatus.json.getString("S0000"));
			commonMsg.getHead().setRespMsg(map);
		}
		
		return commonMsg;
	}

	@Override
	public void exportDynamicFormExcel(CommonMsg<JSONObject, JSONObject> commonMsg, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>(2);
		if(!CommonUtil.commonMsgBizCodeIsNotNull(commonMsg)) {
			return;
		}
		bizCode = commonMsg.getBody().getSingleBody().get("bizCode").toString();
		
		// 默认以传入的sqlCondition查询
		Criteria criteria = new Criteria();
		// 设定只查当前租户ID条件
		criteria.and("tenantId").is(commonMsg.getHead().getTenantId());
		criteria.and("bizCode").is(bizCode);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = (Map)commonMsg.getBody().getSingleBody();

		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if (entry.getValue() != null && !entry.getKey().equals("bizCode")  && !entry.getKey().equals("formId")) {
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0)) {
					criteria.and(entry.getKey()).is(String.valueOf(entry.getValue()));
					isInSingleBody = true;
				}
			}
		}

		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			log.debug(commonMsg.getBody().getSqlCondition().size()+ "========解析"+commonMsg.getBody().getSqlCondition()+"组装查询条件==");
			for (int i = 0; i < commonMsg.getBody().getSqlCondition().size(); i++) {
				switch (commonMsg.getBody().getSqlCondition().get(i).getType()) {
				case "=":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.is(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case ">":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.gt(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "≥":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.gte(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "<":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.lt(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "≤":
					criteria.and(commonMsg.getBody().getSqlCondition().get(i).getColumn())
							.lte(commonMsg.getBody().getSqlCondition().get(i).getValue());
					break;
				case "like":
					 //模糊匹配
			        Pattern pattern = Pattern.compile("^.*"+ commonMsg.getBody().getSqlCondition().get(i).getValue() +".*$", Pattern.CASE_INSENSITIVE);
			        criteria.andOperator(Criteria.where(commonMsg.getBody().getSqlCondition().get(i).getColumn()).regex(pattern));
					break;
				}
			}
		}
		// 处理排序
		Sort.Order order = Sort.Order.asc("createDatetime");
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					order = Sort.Order.asc(sqlOrderList.get(i).getProp());
				}
				else
				{
					order = Sort.Order.desc(sqlOrderList.get(i).getProp());
				}
			}
		}
		//翻页查询处理
		SpringPageable pageable = new SpringPageable();
		PageModel pm = new PageModel();
		Query query = new Query(criteria);
		Sort sort = Sort.by(order);
		pm.setPagenumber(commonMsg.getBody().getCurrentPage());
		pm.setPagesize(commonMsg.getBody().getPageSize());
		pm.setSort(sort);
		pageable.setPage(pm);
		Long total = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).count(query, JSONObject.class, bizCode);
		List<JSONObject> list = mongoTemplateUtil.getMongoTemplate(commonMsg.getHead().getTenantId()).find(query.with(pageable), JSONObject.class, bizCode);
		for (int i = 0; i < list.size(); i++) {
			log.info("============== {}", list.get(i).toString());
		} 
		commonMsg.getBody().setListBody(list);
		
		/**
		 * 查询动态表单获取导出属性
		 */
		SysDynamicFormEntity sysDynamicFormEntiry=  sysDynamicFormMapper.selectById(commonMsg.getBody().getSingleBody().getString("formId"));
		// System.out.println("============== " + sysDynamicFormEntiry.getFormJson());
		JSONObject formObject = JSONObject.parseObject(sysDynamicFormEntiry.getFormJson());
		JSONArray columbArray = formObject.getJSONArray("column");
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = sysDynamicFormEntiry.getBizName();
		String fileName = sysDynamicFormEntiry.getBizName();
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		List<String> bodyList = new ArrayList<>();
		// 遍历JSONArray,构建head要素
		for (Iterator<Object> iterator = columbArray.iterator(); iterator.hasNext(); ) {
			JSONObject columbObject = (JSONObject) iterator.next();
			if(columbObject.containsKey("excel") && columbObject.getBoolean("excel")) {
				headList.add(columbObject.getString("label"));
				bodyList.add(columbObject.getString("prop"));
			}
		}  
		excelData.add(headList);
		// 构建body 要素
		for (JSONObject jsonObject : commonMsg.getBody().getListBody()) {
			List<String> lists = new ArrayList<String>();
			for(String columns : bodyList) {
				lists.add(jsonObject.getString(columns));
			}
			excelData.add(lists);
		}

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
