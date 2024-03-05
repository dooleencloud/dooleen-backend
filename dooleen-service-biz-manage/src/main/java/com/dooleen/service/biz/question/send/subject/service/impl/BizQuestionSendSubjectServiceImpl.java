package com.dooleen.service.biz.question.send.subject.service.impl;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.biz.question.send.subject.entity.BizQuestionSendSubjectEntity;
import com.dooleen.service.biz.question.send.subject.mapper.BizQuestionSendSubjectMapper;
import com.dooleen.service.biz.question.send.subject.service.BizQuestionSendSubjectService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:37:04
 * @Description : 问卷下发题目管理服务实现
 * @Author : apple
 * @Update: 2021-02-22 11:37:04
 */
/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizQuestionSendSubjectServiceImpl extends ServiceImpl<BizQuestionSendSubjectMapper, BizQuestionSendSubjectEntity> implements BizQuestionSendSubjectService {

	public static final String CHECK_CONTENT = "checkContent";
	public static final String DELETE = "delete";

	@Autowired
	public GeneralFlowProcessService  generalFlowProcessService;

	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

	@Autowired
	private  BizQuestionSendSubjectMapper bizQuestionSendSubjectMapper;

	@Autowired
	private  GetBizParamsService getBizParamsService;


	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_QUESTION_SEND_SUBJECT("biz_question_send_subject", "BizQuestionSendSubject", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "bizQuestionSendSubject", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> queryBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizQuestionSendSubjectEntity bizQuestionSendSubjectEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

		commonMsg.getBody().setListBody(nullEntityList);

		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizQuestionSendSubjectEntity,commonMsg);


		commonMsg.getBody().setSingleBody(bizQuestionSendSubjectEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizQuestionSendSubjectEntity> bizQuestionSendSubjectEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizQuestionSendSubjectEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListPage(
			CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		}
		else
		{
			sqlConditionEntity.setValue(commonMsg.getBody().getSingleBody().getTenantId());
		}
		sqlConditionList.add(sqlConditionEntity);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString()))
			{
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<BizQuestionSendSubjectEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionSendSubjectEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionSendSubjectEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizQuestionSendSubjectEntity.class);
		}

		// 处理排序
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
				else
				{
					queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
			}
		}

		// 定义分页查询
		Page<BizQuestionSendSubjectEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizQuestionSendSubjectEntity> list =  super.page(pages, queryWrapper);

		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListMap(
			CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 解析Single body 组装查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>(16);
		conditionMap.put("tenantId", commonMsg.getHead().getTenantId());
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if (entry.getValue() != null) {
				// kay是字段名 value是字段值
				conditionMap.put(entry.getKey(), entry.getValue());
			}
		}
		Collection<BizQuestionSendSubjectEntity> bizQuestionSendSubjectEntityList =  super.listByMap(conditionMap);
		List<BizQuestionSendSubjectEntity> bizQuestionSendSubjectMapList = new ArrayList<BizQuestionSendSubjectEntity>(bizQuestionSendSubjectEntityList);
		commonMsg.getBody().setListBody(bizQuestionSendSubjectMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizQuestionSendSubject", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> saveBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		// 初始化数据
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
			commonMsg.getBody().getListBody().set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		// 批量保存
		boolean result =  super.saveBatch(commonMsg.getBody().getListBody());

		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizQuestionSendSubject", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> updateBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}


		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<BizQuestionSendSubjectEntity, NullEntity> queryCommonMsg = new CommonMsg<BizQuestionSendSubjectEntity, NullEntity>();
		//定义singleBody
		BizQuestionSendSubjectEntity singleBody = new BizQuestionSendSubjectEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizQuestionSendSubjectEntity, NullEntity> mutBean= new MutBean<BizQuestionSendSubjectEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(BizQuestionSendSubjectServiceImpl.class).queryBizQuestionSendSubject(queryCommonMsg);

		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}
		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);

		// 保存数据
		boolean result =  super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizQuestionSendSubject", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveOrUpdateBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}
		// 判断内容是否发生更改
		if (!commonMsg.getBody().getSingleBody().getId().equals("0") && !CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<BizQuestionSendSubjectEntity, NullEntity> queryCommonMsg = new CommonMsg<BizQuestionSendSubjectEntity, NullEntity>();
		//定义singleBody
		BizQuestionSendSubjectEntity singleBody = new BizQuestionSendSubjectEntity();

		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<BizQuestionSendSubjectEntity, NullEntity> mutBean= new MutBean<BizQuestionSendSubjectEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(BizQuestionSendSubjectServiceImpl.class).queryBizQuestionSendSubject(queryCommonMsg);

		//判断修改内容是否被其他用户修改
		if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}

		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);

		// 保存数据
		boolean result =  super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),
				commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		// 保存失败
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> saveOrUpdateBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}

		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CacheEvict(value = "bizQuestionSendSubject", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> deleteBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> deleteBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}

		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
		}
		// 执行删除
		boolean result =  super.removeByIds(keylist);
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public void exportBizQuestionSendSubjectExcel(CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		sqlConditionList.add(sqlConditionEntity);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if(entry.getValue() != null)
			{
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
		}

		QueryWrapper<BizQuestionSendSubjectEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionSendSubjectEntity.class);

		// 处理排序
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
				else
				{
					queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
			}
		}

		// 定义分页查询
		Page<BizQuestionSendSubjectEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizQuestionSendSubjectEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());

		/**
		 * 生成excel并输出
		 */
		String sheetName = "问卷下发题目明细";
		String fileName = "问卷下发题目明细";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("项目编号");
		headList.add("问卷ID");
		headList.add("问卷标题");
		headList.add("批次号");
		headList.add("填报人");
		headList.add("填报人姓名");
		headList.add("题目配置ID");
		headList.add("题目标题");
		headList.add("题目类型");
		headList.add("题目内容");
		headList.add("题目答案");
		headList.add("答题结果");
		headList.add("是否必填");
		headList.add("项目名称");
		excelData.add(headList);
		for (BizQuestionSendSubjectEntity bizQuestionSendSubjectEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(bizQuestionSendSubjectEntity.getProjectNo());
			lists.add(bizQuestionSendSubjectEntity.getQuestionnaireId());
			lists.add(bizQuestionSendSubjectEntity.getQuestionnaireTitle());
			lists.add(bizQuestionSendSubjectEntity.getBatchNo());
			lists.add(bizQuestionSendSubjectEntity.getFillInUserName());
			lists.add(bizQuestionSendSubjectEntity.getFillInRealName());
			lists.add(bizQuestionSendSubjectEntity.getSubjectConfigId());
			lists.add(bizQuestionSendSubjectEntity.getSubjectTitle());
			lists.add(bizQuestionSendSubjectEntity.getSubjectType());
			lists.add(bizQuestionSendSubjectEntity.getSubjectContent());
			lists.add(bizQuestionSendSubjectEntity.getSubjectAnswer());
			lists.add(bizQuestionSendSubjectEntity.getAnswerResult());
			lists.add(bizQuestionSendSubjectEntity.getIsRequired());
			lists.add(bizQuestionSendSubjectEntity.getProjectName());
			excelData.add(lists);
		}

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportBizQuestionSendSubjectExcel service end == " + RespStatus.json.getString("S0000"));
	}

	/**
	 * 导出
	 * @param tenantId
	 * @param projectName
	 * @param batchNo
	 * @param subjectTitle
	 * @param flag
	 * @return
	 */
	@Override
	public JSONObject questionReport(String tenantId,String projectName, String batchNo, String subjectTitle, String flag) {
		//查询当前批次下，当前题目完成情况
		BizResponseEnum.SUBJECT_TITLE_IS_EMPTY.assertIsFalse(StringUtil.isEmpty(subjectTitle));
		BizResponseEnum.BTCH_NO_IS_EMPTY.assertIsFalse(StringUtil.isEmpty(batchNo));
		//正常
		QueryWrapper<BizQuestionSendSubjectEntity> queryWrapper = new QueryWrapper<BizQuestionSendSubjectEntity>();
		queryWrapper.lambda().eq(BizQuestionSendSubjectEntity::getTenantId, tenantId);
		queryWrapper.lambda().eq(BizQuestionSendSubjectEntity::getBatchNo, batchNo);
		queryWrapper.lambda().eq(BizQuestionSendSubjectEntity::getSubjectTitle, subjectTitle);
		List<BizQuestionSendSubjectEntity>  subjectEntityList= bizQuestionSendSubjectMapper.selectList(queryWrapper);
		//循环处理报表数据
		BizQuestionSendSubjectEntity bizQuestionSendSubjectEntity = new BizQuestionSendSubjectEntity();
		Map<String, String> optionMap = new HashMap();
		Map<String, Integer> optionCntMap = new HashMap();

		int indx = 0;
		for (BizQuestionSendSubjectEntity subjectEntity : subjectEntityList) {
			if(indx == 0){
				bizQuestionSendSubjectEntity = subjectEntity;
				//处理选项列表
				if(bizQuestionSendSubjectEntity.getSubjectType().equals("多选") || bizQuestionSendSubjectEntity.getSubjectType().equals("单选")){
					JSONArray optionArray = JSONArray.parseArray(bizQuestionSendSubjectEntity.getSubjectContent());
 					for(int i=0; i<optionArray.size(); i++){
 						JSONObject optionJson = JSONObject.parseObject(optionArray.get(i).toString());
						optionMap.put(optionJson.getString("value"),optionJson.getString("label"));
						if(subjectEntity.getQuestionnaireType().equals("问卷")) {
							optionCntMap.put(optionJson.getString("value"), 0);
						}
						else if(subjectEntity.getQuestionnaireType().equals("试卷")) {
							optionCntMap.put(optionJson.getString("label")+","+optionJson.getString("value"), 0);
						}
 					}
				}
			}
			// 开始计数
			// 问卷处理 value
			if(subjectEntity.getQuestionnaireType().equals("问卷")){
				if(subjectEntity.getSubjectType().equals("多选")){
					//处理多选答案数组
					JSONArray answerArray = JSONArray.parseArray(subjectEntity.getAnswerResult());
					for(int i=0; i<answerArray.size(); i++){
						if(optionCntMap.containsKey(answerArray.getString(i))){
							optionCntMap.put(answerArray.getString(i), optionCntMap.get(answerArray.getString(i))+1);
						}
					}
				}
				if(subjectEntity.getSubjectType().equals("单选")){
					//处理多选答案数组
					if(optionCntMap.containsKey(subjectEntity.getAnswerResult())){
						optionCntMap.put(subjectEntity.getAnswerResult(), optionCntMap.get(subjectEntity.getAnswerResult())+1);
					}
				}
			}
			// 试卷处理答案 label
			//System.out.println("====optionCntMap==="+optionCntMap);
			if(subjectEntity.getQuestionnaireType().equals("试卷")){
				if(subjectEntity.getSubjectType().equals("多选")){
					//处理多选答案数组
					JSONArray answerArray = JSONArray.parseArray(subjectEntity.getAnswerResult());
					for(int i=0; i<answerArray.size(); i++){
						for (String key : optionMap.keySet()) {
							if(optionMap.get(key).equals(answerArray.getString(i))){
								optionCntMap.put(answerArray.getString(i)+","+key, optionCntMap.get(answerArray.getString(i)+","+key)+1);
							}
						}
					}
				}
				else if(subjectEntity.getSubjectType().equals("单选")){
					//处理多选答案数组
					for (String key : optionMap.keySet()) {
						if(optionMap.get(key).equals(subjectEntity.getAnswerResult())){
							optionCntMap.put(subjectEntity.getAnswerResult()+","+key, optionCntMap.get(key) + 1);
						}
					}
				}
				else {
					if(optionCntMap.containsKey(subjectEntity.getAnswerResult())){
						optionCntMap.put(subjectEntity.getAnswerResult(), optionCntMap.get(subjectEntity.getAnswerResult()) + 1);
					}
					else if(!StringUtil.isEmpty(subjectEntity.getAnswerResult())) {
						optionCntMap.put(subjectEntity.getAnswerResult(),0);
					}
				}
			}
			indx++;
		}
		//返回数据
		JSONObject returnJson = new JSONObject();
		//柱状数据
		//返回JSON
		JSONObject barJson = new JSONObject();
		List<String> categories = new ArrayList<>();
		List<Integer> seriesData = new ArrayList<>();
		//将Key值排序
		List<String> keyList =new ArrayList<>();
		for (String key : optionCntMap.keySet()) {
			keyList.add(key);
		}
		Collections.sort(keyList);
		for (String key : keyList) {
			categories.add(key);
			seriesData.add(optionCntMap.get(key));
		}

		JSONArray seriesArray = new JSONArray();
		JSONObject seriesJson = new JSONObject();
		seriesJson.put("name",subjectTitle);
		seriesJson.put("data",seriesData);
		seriesArray.add(seriesJson);
		barJson.put("categories",categories);
		barJson.put("series",seriesArray);
		returnJson.put("bar",barJson);

		//处理饼状数据
		JSONArray pieArray = new JSONArray();
		for (String key : keyList) {
			pieArray.add(JSONObject.parse("{\"name\":\""+key+"\",\"value\":"+optionCntMap.get(key)+"}"));
		}

		//pieArray.add(JSONObject.parse("{\"name\":\"空置\",\"value\":"+empty+"}"));
		returnJson.put("pie",pieArray);
		return returnJson;
	}
}
