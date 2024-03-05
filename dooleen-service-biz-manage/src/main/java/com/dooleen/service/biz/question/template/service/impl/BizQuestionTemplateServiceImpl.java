package com.dooleen.service.biz.question.template.service.impl;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.app.system.custom.user.mapper.SysCustomUserInfoMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.biz.question.send.list.entity.BizQuestionSendListEntity;
import com.dooleen.service.biz.question.send.list.mapper.BizQuestionSendListMapper;
import com.dooleen.service.biz.question.send.record.entity.BizQuestionSendRecordEntity;
import com.dooleen.service.biz.question.send.record.mapper.BizQuestionSendRecordMapper;
import com.dooleen.service.biz.question.send.subject.entity.BizQuestionSendSubjectEntity;
import com.dooleen.service.biz.question.send.subject.mapper.BizQuestionSendSubjectMapper;
import com.dooleen.service.biz.question.subject.config.entity.BizQuestionSubjectConfigEntity;
import com.dooleen.service.biz.question.subject.config.mapper.BizQuestionSubjectConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.dooleen.service.biz.question.template.entity.BizQuestionTemplateEntity;
import com.dooleen.service.biz.question.template.mapper.BizQuestionTemplateMapper;
import com.dooleen.service.biz.question.template.service.BizQuestionTemplateService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:31:07
 * @Description : 问卷模板管理服务实现
 * @Author : apple
 * @Update: 2021-02-22 11:31:07
 */
/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizQuestionTemplateServiceImpl extends ServiceImpl<BizQuestionTemplateMapper, BizQuestionTemplateEntity> implements BizQuestionTemplateService {

	public static final String CHECK_CONTENT = "checkContent";
	public static final String DELETE = "delete";

	@Autowired
	private BizQuestionTemplateMapper bizQuestionTemplateMapper;

	@Autowired
	private BizQuestionSubjectConfigMapper bizQuestionSubjectConfigMapper;

	@Autowired
	private BizQuestionSendListMapper bizQuestionSendListMapper;

	@Autowired
	private BizQuestionSendSubjectMapper bizQuestionSendSubjectMapper;

	@Autowired
	private BizQuestionSendRecordMapper bizQuestionSendRecordMapper;

	@Autowired
	private SysCustomUserInfoMapper sysCustomUserInfoMapper;

	private  static String sendRecordSeqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_RECORD.getSeqPrefix();
	private  static String sendRecordTableName = TableEntityORMEnum.BIZ_QUESTION_SEND_RECORD.getEntityName();

	private  static String sendSeqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_LIST.getSeqPrefix();
	private  static String sendTableName = TableEntityORMEnum.BIZ_QUESTION_SEND_LIST.getEntityName();

	private  static String subjectSeqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getSeqPrefix();
	private  static String subjectTableName = TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getEntityName();

	private  static String seqPrefix= TableEntityORMEnum.BIZ_QUESTION_TEMPLATE.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_QUESTION_TEMPLATE.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "bizQuestionTemplate", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> queryBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizQuestionTemplateEntity bizQuestionTemplateEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

		commonMsg.getBody().setListBody(nullEntityList);

		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizQuestionTemplateEntity,commonMsg);


		commonMsg.getBody().setSingleBody(bizQuestionTemplateEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizQuestionTemplateEntity> queryBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizQuestionTemplateEntity> bizQuestionTemplateEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizQuestionTemplateEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizQuestionTemplateEntity, BizQuestionTemplateEntity> queryBizQuestionTemplateListPage(
			CommonMsg<BizQuestionTemplateEntity, BizQuestionTemplateEntity> commonMsg) {
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
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0") || entry.getValue().equals(0)))) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					if(entry.getKey().equals("questionnaireTitle")){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<BizQuestionTemplateEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionTemplateEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionTemplateEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizQuestionTemplateEntity.class);
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
		Page<BizQuestionTemplateEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizQuestionTemplateEntity> list =  super.page(pages, queryWrapper);

		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizQuestionTemplateEntity, BizQuestionTemplateEntity> queryBizQuestionTemplateListMap(
			CommonMsg<BizQuestionTemplateEntity, BizQuestionTemplateEntity> commonMsg) {
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
		Collection<BizQuestionTemplateEntity> bizQuestionTemplateEntityList =  super.listByMap(conditionMap);
		List<BizQuestionTemplateEntity> bizQuestionTemplateMapList = new ArrayList<BizQuestionTemplateEntity>(bizQuestionTemplateEntityList);
		commonMsg.getBody().setListBody(bizQuestionTemplateMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizQuestionTemplate", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> saveBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizQuestionTemplateEntity> saveBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg) {
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
	@CachePut(value = "bizQuestionTemplate", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> updateBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizQuestionTemplateEntity, NullEntity> queryCommonMsg = new CommonMsg<BizQuestionTemplateEntity, NullEntity>();
		//定义singleBody
		BizQuestionTemplateEntity singleBody = new BizQuestionTemplateEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizQuestionTemplateEntity, NullEntity> mutBean= new MutBean<BizQuestionTemplateEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(BizQuestionTemplateServiceImpl.class).queryBizQuestionTemplate(queryCommonMsg);

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
	@CachePut(value = "bizQuestionTemplate", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> saveOrUpdateBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizQuestionTemplateEntity, NullEntity> queryCommonMsg = new CommonMsg<BizQuestionTemplateEntity, NullEntity>();
		//定义singleBody
		BizQuestionTemplateEntity singleBody = new BizQuestionTemplateEntity();

		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<BizQuestionTemplateEntity, NullEntity> mutBean= new MutBean<BizQuestionTemplateEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(BizQuestionTemplateServiceImpl.class).queryBizQuestionTemplate(queryCommonMsg);

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
	public CommonMsg<NullEntity, BizQuestionTemplateEntity> saveOrUpdateBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg) {
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
	@CacheEvict(value = "bizQuestionTemplate", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> deleteBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizQuestionTemplateEntity> deleteBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg) {
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
	public void exportBizQuestionTemplateExcel(CommonMsg<BizQuestionTemplateEntity, BizQuestionTemplateEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<BizQuestionTemplateEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizQuestionTemplateEntity.class);

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
		Page<BizQuestionTemplateEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizQuestionTemplateEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());

		/**
		 * 生成excel并输出
		 */
		Date start = new Date();
		String sheetName = "问卷模板";
		String fileName = "问卷模板-"+start.getTime();
		ExportParams params = new ExportParams();
		Workbook workbook = ExcelExportUtil.exportExcel(params, BizQuestionTemplateEntity.class, commonMsg.getBody().getListBody());
		//导出Excel数据流
		try {
			ExcelUtil.exportPoi(response,workbook,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("====exportBizQuestionTemplateExcel service end == " + RespStatus.json.getString("S0000"));
	}

	/**
	 * 导入excel新增
	 */
	@Override
	public CommonMsg<BizQuestionTemplateEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
		CommonMsg<BizQuestionTemplateEntity,NullEntity> commonMsg = new CommonMsg<BizQuestionTemplateEntity,NullEntity>();
		HeadEntity head =new HeadEntity();
		commonMsg.setHead(head);
		commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
		commonMsg.getHead().setUserName(request.getHeader("username"));
		ImportParams params = new ImportParams();
		params.setTitleRows(0);
		List<BizQuestionTemplateEntity> list = new ArrayList<>();
		try {
			commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
			list = ExcelImportUtil.importExcel(
					file.getInputStream(),
					BizQuestionTemplateEntity.class, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//批量导入数据并做校验
		List<BizQuestionTemplateEntity> tmpList = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			BizQuestionTemplateEntity bizQuestionTemplateEntity = list.get(i);
			System.out.println(ReflectionToStringBuilder.toString(bizQuestionTemplateEntity));

			//检查字段的有效性
			ValidationResult validationResult = ValidationUtils.validateEntity(bizQuestionTemplateEntity);
			boolean isError = validationResult.isHasErrors();
			// 处理检查错误
			if (isError == true) {
				String errStr = "";
				for(String value : validationResult.getErrorMsg().values()){
					errStr += value+";";
				}
				map.put("第"+i+"行",errStr);
			}
			else if(map.isEmpty()){
				bizQuestionTemplateEntity.setDataSign(DooleenMD5Util.md5(bizQuestionTemplateEntity.toString(),  ConstantValue.md5Key));
				// 执行保存
				bizQuestionTemplateEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizQuestionTemplateEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				tmpList.add(bizQuestionTemplateEntity);
			}
		}
		if(!map.isEmpty()){
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			return commonMsg;
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 立即下发问卷
	 */
	@Override
	public CommonMsg<BizQuestionTemplateEntity, NullEntity>  sendQuestionNow(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		//判断处理频率
		BizQuestionTemplateEntity bizQuestionTemplateEntity= commonMsg.getBody().getSingleBody();
		String[] frequency = bizQuestionTemplateEntity.getGenerateFrequency().split("-");
		String validDateStr="";
		//按天处理
		if(frequency[1].equals("D")){
			//计算问卷的有效期
			DateUtilPro.DateTime validDate = DateUtilPro.date().addDays(Integer.parseInt(frequency[0]));
			validDateStr= validDate.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
		}
		//按周处理
		else if(frequency[1].equals("W")){

			//计算问卷的有效期
			DateUtilPro.DateTime validDate = DateUtilPro.date().addDays(Integer.parseInt(frequency[0])*7);
			validDateStr= validDate.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
		}
		//按月处理
		else if(frequency[1].equals("M")){

			//计算问卷的有效期
			DateUtilPro.DateTime validDate = DateUtilPro.date().addMonths(Integer.parseInt(frequency[0]));
			validDateStr= validDate.toString().substring(0, 8)+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
		}
		//按年处理
		else if(frequency[1].equals("Y")){
			//计算问卷的有效期
			DateUtilPro.DateTime validDate = DateUtilPro.date().addYears(Integer.parseInt(frequency[0]));
			validDateStr= validDate.toString().substring(0, 5) + bizQuestionTemplateEntity.getGenerateMonth() +"-"+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
		}

		//如果批次编号存在则新建机构下发记录则可
		if(StringUtil.isEmpty(bizQuestionTemplateEntity.getBatchNo())){
			bizQuestionTemplateEntity.setBatchNo(String.valueOf(new Date().getTime()));
			copySendRecord(bizQuestionTemplateEntity,validDateStr);
		}
		else{
			log.info("===批次号:"+bizQuestionTemplateEntity.getBatchNo()+"已存在，直接下发用户记录！====");
		}
		copySendData(bizQuestionTemplateEntity,validDateStr);

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 问卷下发-批量下发
	 */
	@Override
	public void sendQuestionToUser() {
		//获取问卷模板列表
		QueryWrapper<BizQuestionTemplateEntity> templateQueryWrapper = new QueryWrapper<BizQuestionTemplateEntity>();
		templateQueryWrapper.lambda().ge(BizQuestionTemplateEntity::getValidDate, DateUtils.getShortDateStr());
		List<BizQuestionTemplateEntity> templateEntityList = bizQuestionTemplateMapper.selectList(templateQueryWrapper);
		for(int i = 0; i<templateEntityList.size(); i++){
			//判断处理频率
			BizQuestionTemplateEntity bizQuestionTemplateEntity= templateEntityList.get(i);
			String[] frequency = bizQuestionTemplateEntity.getGenerateFrequency().split("-");
			//按天处理
			if(frequency[1].equals("D")){
				Date lastSendDateTime = DateUtilPro.parseDate(bizQuestionTemplateEntity.getSendDatetime());
				DateUtilPro.DateTime date2 = DateUtilPro.date(lastSendDateTime).addDays(Integer.parseInt(frequency[0]));
				String handleDate= date2.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				System.out.println("bay month handleDate="+handleDate+" ;DateUtils.getLongDateStr()="+DateUtils.getLongDateStr());

				if(DateUtilPro.parseDate(handleDate).getTime() < DateUtilPro.parseDate(DateUtils.getLongDateStr()).getTime()){
					//开始下发
					System.out.println("开始下发1");
					//计算问卷的有效期
					DateUtilPro.DateTime validDate = DateUtilPro.date().addDays(Integer.parseInt(frequency[0]));
					String validDateStr= validDate.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
					bizQuestionTemplateEntity.setBatchNo(String.valueOf(new Date().getTime()));
					copySendData(bizQuestionTemplateEntity,validDateStr);
					copySendRecord(bizQuestionTemplateEntity,validDateStr);
				}
			}
			//按周处理
			else if(frequency[1].equals("W")){
				Date lastSendDateTime = DateUtilPro.parseDate(bizQuestionTemplateEntity.getSendDatetime());
				DateUtilPro.DateTime date2 = DateUtilPro.date(lastSendDateTime).addDays(Integer.parseInt(frequency[0])*7);
				String handleDate= date2.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				System.out.println("bay month handleDate="+handleDate+" ;DateUtils.getLongDateStr()="+DateUtils.getLongDateStr());

				if(DateUtilPro.parseDate(handleDate).getTime() < DateUtilPro.parseDate(DateUtils.getLongDateStr()).getTime()){
					//开始下发
					System.out.println("开始下发2");
					//计算问卷的有效期
					DateUtilPro.DateTime validDate = DateUtilPro.date().addDays(Integer.parseInt(frequency[0])*7);
					String validDateStr= validDate.toString().substring(0, 10) + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				}
			}
			//按月处理
			else if(frequency[1].equals("M")){
				Date lastSendDateTime = DateUtilPro.parseDate(bizQuestionTemplateEntity.getSendDatetime());
				DateUtilPro.DateTime date2 = DateUtilPro.date(lastSendDateTime).addMonths(Integer.parseInt(frequency[0]));
				String handleDate= date2.toString().substring(0, 8)+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				System.out.println("bay month handleDate="+handleDate+" ;DateUtils.getLongDateStr()="+DateUtils.getLongDateStr());
				if(DateUtilPro.parseDate(handleDate).getTime() < DateUtilPro.parseDate(DateUtils.getLongDateStr()).getTime()){
					//开始下发
					System.out.println("开始下发3");
					//计算问卷的有效期
					DateUtilPro.DateTime validDate = DateUtilPro.date().addMonths(Integer.parseInt(frequency[0]));
					String validDateStr= date2.toString().substring(0, 8)+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				}
			}
			//按年处理
			else if(frequency[1].equals("Y")){
				Date lastSendDateTime = DateUtilPro.parseDate(bizQuestionTemplateEntity.getSendDatetime());
				DateUtilPro.DateTime date2 = DateUtilPro.date(lastSendDateTime).addYears(Integer.parseInt(frequency[0]));
				String handleDate= date2.toString().substring(0, 5)+ bizQuestionTemplateEntity.getGenerateMonth() +"-"+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				System.out.println("bay month handleDate="+handleDate+" ;DateUtils.getLongDateStr()="+DateUtils.getLongDateStr());
				if(DateUtilPro.parseDate(handleDate).getTime() < DateUtilPro.parseDate(DateUtils.getLongDateStr()).getTime()){
					//开始下发
					System.out.println("开始下发4");
					//计算问卷的有效期
					DateUtilPro.DateTime validDate = DateUtilPro.date().addYears(Integer.parseInt(frequency[0]));
					String validDateStr= date2.toString().substring(0, 5) + bizQuestionTemplateEntity.getGenerateMonth() +"-"+ bizQuestionTemplateEntity.getGenerateDay() + " "+bizQuestionTemplateEntity.getGenerateDatetime();
				}
			}
		}
	}

	/**
	 * 生成发送记录
	 * @param bizQuestionTemplateEntity
	 * @param validDate
	 */
	public void copySendRecord(BizQuestionTemplateEntity bizQuestionTemplateEntity,String validDate){
		//构建head信息
		HeadEntity headEntity = new HeadEntity();
		headEntity.setTenantId(bizQuestionTemplateEntity.getTenantId());
		headEntity.setRealName(bizQuestionTemplateEntity.getCreateRealName());
		headEntity.setUserName(bizQuestionTemplateEntity.getCreateUserName());
		//生成下发问卷列表
		BizQuestionSendRecordEntity bizQuestionSendRecordtEntity = new BizQuestionSendRecordEntity();
		bizQuestionSendRecordtEntity.setTemplateId(bizQuestionTemplateEntity.getId());
		bizQuestionSendRecordtEntity.setBatchNo(bizQuestionTemplateEntity.getBatchNo());
		bizQuestionSendRecordtEntity.setGenerateFrequency(bizQuestionTemplateEntity.getGenerateFrequency());
		bizQuestionSendRecordtEntity.setGenerateDay(bizQuestionTemplateEntity.getGenerateDay());
		bizQuestionSendRecordtEntity.setGenerateDatetime(bizQuestionTemplateEntity.getGenerateDatetime());
		bizQuestionSendRecordtEntity.setGenerateMonth(bizQuestionTemplateEntity.getGenerateMonth());
		bizQuestionSendRecordtEntity.setSendDatetime(DateUtils.getLongDateStr());
		bizQuestionSendRecordtEntity.setProjectName(bizQuestionTemplateEntity.getProjectName());
		bizQuestionSendRecordtEntity.setProjectNo(bizQuestionTemplateEntity.getProjectNo());
		bizQuestionSendRecordtEntity.setQuestionnaireTitle(bizQuestionTemplateEntity.getQuestionnaireTitle());
		bizQuestionSendRecordtEntity.setQuestionnaireType(bizQuestionTemplateEntity.getQuestionnaireType());
		bizQuestionSendRecordtEntity.setQuestionnaireNotice(bizQuestionTemplateEntity.getQuestionnaireNotice());
		bizQuestionSendRecordtEntity.setGenerateFrequency(bizQuestionTemplateEntity.getGenerateFrequency());
		bizQuestionSendRecordtEntity.setValidFlag("1");
		bizQuestionSendRecordtEntity.setValidDate(validDate);
		bizQuestionSendRecordtEntity.setDataSign(DooleenMD5Util.md5(bizQuestionSendRecordtEntity.toString(),  ConstantValue.md5Key));
		// 初始化数据
		bizQuestionSendRecordtEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizQuestionSendRecordtEntity, headEntity,sendRecordTableName, sendRecordSeqPrefix, redisTemplate);
		int result = bizQuestionSendRecordMapper.insert(bizQuestionSendRecordtEntity);
		if(result == 1){
			log.info("===生成问卷信息成功！！====");
		}
		else{
			log.info("===XX生成问卷信息失败====");
		}
	}
	//遍历用户列表，下发问卷
	public void copySendData(BizQuestionTemplateEntity bizQuestionTemplateEntity,String validDate){
		for(int i = 0; i<bizQuestionTemplateEntity.getSendUserList().size(); i++){
			//=====复制题目===========
			QueryWrapper<SysCustomUserInfoEntity> sysCustomUserInfoQueryWrapper = new QueryWrapper<SysCustomUserInfoEntity>();
			sysCustomUserInfoQueryWrapper.lambda().eq(SysCustomUserInfoEntity::getTenantId, bizQuestionTemplateEntity.getTenantId());
			sysCustomUserInfoQueryWrapper.lambda().eq(SysCustomUserInfoEntity::getUserName, bizQuestionTemplateEntity.getSendUserList().get(i));
			SysCustomUserInfoEntity sysCustomUserInfoEntity =  sysCustomUserInfoMapper.selectOne(sysCustomUserInfoQueryWrapper);
			if(sysCustomUserInfoEntity != null){
				GenSendData(bizQuestionTemplateEntity, validDate,sysCustomUserInfoEntity.getUserName(),sysCustomUserInfoEntity.getOrgName());
			}
			else{
				log.info("用户ID："+bizQuestionTemplateEntity.getSendUserList().get(i)+"不存在！");
			}
		}
	}
	//按照用户列表下发 - 生成问卷发送列表
	public void GenSendData(BizQuestionTemplateEntity bizQuestionTemplateEntity,String validDate,String userName, String orgName){
		//构建head信息
		HeadEntity headEntity = new HeadEntity();
		headEntity.setTenantId(bizQuestionTemplateEntity.getTenantId());
		headEntity.setRealName(bizQuestionTemplateEntity.getCreateRealName());
		headEntity.setUserName(bizQuestionTemplateEntity.getCreateUserName());
		//生成下发问卷列表
		BizQuestionSendListEntity bizQuestionSendListEntity = new BizQuestionSendListEntity();
		bizQuestionSendListEntity.setBatchNo(bizQuestionTemplateEntity.getBatchNo());
		bizQuestionSendListEntity.setFillInUserName(userName);
		bizQuestionSendListEntity.setFillInRealName(orgName);
		bizQuestionSendListEntity.setQuestionnaireTemplateId(bizQuestionTemplateEntity.getId());
		bizQuestionSendListEntity.setProjectName(bizQuestionTemplateEntity.getProjectName());
		bizQuestionSendListEntity.setProjectNo(bizQuestionTemplateEntity.getProjectNo());
		bizQuestionSendListEntity.setQuestionnaireTitle(bizQuestionTemplateEntity.getQuestionnaireTitle());
		bizQuestionSendListEntity.setQuestionnaireType(bizQuestionTemplateEntity.getQuestionnaireType());
		bizQuestionSendListEntity.setQuestionnaireNotice(bizQuestionTemplateEntity.getQuestionnaireNotice());
		bizQuestionSendListEntity.setGenerateFrequency(bizQuestionTemplateEntity.getGenerateFrequency());
		bizQuestionSendListEntity.setSendDatetime(DateUtils.getLongDateStr());
		bizQuestionSendListEntity.setProgressRate("0");
		bizQuestionSendListEntity.setValidFlag("1");
		bizQuestionSendListEntity.setStatus("0");
		bizQuestionSendListEntity.setValidDate(validDate);
		bizQuestionSendListEntity.setDataSign(DooleenMD5Util.md5(bizQuestionSendListEntity.toString(),  ConstantValue.md5Key));
		// 初始化数据
		bizQuestionSendListEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizQuestionSendListEntity, headEntity,sendTableName, sendSeqPrefix, redisTemplate);
		int result = bizQuestionSendListMapper.insert(bizQuestionSendListEntity);
		if(result == 1){
			log.info("===生成问卷信息成功！！====");
		}
		else{
			log.info("===XX生成问卷信息失败====");
		}
		//=====复制题目===========
		QueryWrapper<BizQuestionSubjectConfigEntity> subjectQueryWrapper = new QueryWrapper<BizQuestionSubjectConfigEntity>();
		subjectQueryWrapper.lambda().eq(BizQuestionSubjectConfigEntity::getTenantId, bizQuestionTemplateEntity.getTenantId());
		subjectQueryWrapper.lambda().eq(BizQuestionSubjectConfigEntity::getQuestionnaireTemplateId, bizQuestionTemplateEntity.getId());
		List<BizQuestionSubjectConfigEntity> subjectEntityList = bizQuestionSubjectConfigMapper.selectList(subjectQueryWrapper);
		List<BizQuestionSendSubjectEntity> bizQuestionSendSubjectEntityList =new ArrayList<>();
		//存储跳转ID map
		Map<String,String> gotoId = new HashMap<>();
		for(int i = 0; i<subjectEntityList.size(); i++){
			BizQuestionSubjectConfigEntity bizQuestionSubjectConfigEntity = subjectEntityList.get(i);
			BizQuestionSendSubjectEntity bizQuestionSendSubjectEntity = new BizQuestionSendSubjectEntity();
			bizQuestionSendSubjectEntity.setProjectName(bizQuestionSendListEntity.getProjectName());
			bizQuestionSendSubjectEntity.setProjectNo(bizQuestionSendListEntity.getProjectNo());
			bizQuestionSendSubjectEntity.setBatchNo(bizQuestionSendListEntity.getBatchNo());
			bizQuestionSendSubjectEntity.setFillInRealName(bizQuestionSendListEntity.getFillInRealName());
			bizQuestionSendSubjectEntity.setFillInUserName(bizQuestionSendListEntity.getFillInUserName());
			bizQuestionSendSubjectEntity.setShowSeq(bizQuestionSubjectConfigEntity.getShowSeq());
			bizQuestionSendSubjectEntity.setQuestionnaireId(bizQuestionSendListEntity.getId());
			bizQuestionSendSubjectEntity.setQuestionnaireTitle(bizQuestionSendListEntity.getQuestionnaireTitle());
			bizQuestionSendSubjectEntity.setSubjectConfigId(bizQuestionSubjectConfigEntity.getId());
			bizQuestionSendSubjectEntity.setSubjectTitle(bizQuestionSubjectConfigEntity.getSubjectTitle());
			bizQuestionSendSubjectEntity.setSubjectType(bizQuestionSubjectConfigEntity.getSubjectType());
			bizQuestionSendSubjectEntity.setSubjectAnswer(bizQuestionSubjectConfigEntity.getSubjectAnswer());
			bizQuestionSendSubjectEntity.setSubjectContent(bizQuestionSubjectConfigEntity.getSubjectContent());
			bizQuestionSendSubjectEntity.setIsRequired(bizQuestionSubjectConfigEntity.getIsRequired());
			bizQuestionSendSubjectEntity.setScore(bizQuestionSubjectConfigEntity.getScore());
			bizQuestionSendSubjectEntity.setSubjectProperties(bizQuestionSubjectConfigEntity.getSubjectProperties());
			bizQuestionSendSubjectEntity.setQuestionnaireType(bizQuestionSendListEntity.getQuestionnaireType());
			bizQuestionSendSubjectEntity.setValidFlag("1");
			if(bizQuestionSendSubjectEntity.getSubjectType().equals("多选") || bizQuestionSendSubjectEntity.getSubjectType().equals("地区") || bizQuestionSendSubjectEntity.getSubjectType().equals("文件")	) {
				bizQuestionSendSubjectEntity.setAnswerResult("[]");
			}
			else{
				bizQuestionSendSubjectEntity.setAnswerResult("");
			}
			bizQuestionSendSubjectEntity.setDataSign(DooleenMD5Util.md5(bizQuestionSendSubjectEntity.toString(),  ConstantValue.md5Key));
			// 初始化数据
			bizQuestionSendSubjectEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizQuestionSendSubjectEntity, headEntity,subjectTableName, subjectSeqPrefix, redisTemplate);
			bizQuestionSendSubjectEntityList.add(bizQuestionSendSubjectEntity);
			gotoId.put(bizQuestionSendSubjectEntity.getSubjectConfigId(),bizQuestionSendSubjectEntity.getId());
		}
		//循环写入数据
		for(int j = 0; j < bizQuestionSendSubjectEntityList.size(); j++) {
			JSONArray contents = JSON.parseArray(bizQuestionSendSubjectEntityList.get(j).getSubjectContent());
			for(int k=0;k<contents.size(); k++){
				JSONObject item = (JSONObject) JSON.parse (contents.get(k).toString());
				if(!item.getString("gotoSubject").equals("")){
					if(gotoId.containsKey(item.getString("gotoSubject"))){
						bizQuestionSendSubjectEntityList.get(j).setSubjectContent(bizQuestionSendSubjectEntityList.get(j).getSubjectContent().replaceAll(item.getString("gotoSubject"),gotoId.get(item.getString("gotoSubject"))));
					}
				}
			}
			bizQuestionSendSubjectMapper.insert(bizQuestionSendSubjectEntityList.get(j));
		}
	}
}
