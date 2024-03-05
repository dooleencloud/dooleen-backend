package com.dooleen.service.general.smart.report.catalog.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.general.smart.report.catalog.entity.GeneralReportCatalogEntity;
import com.dooleen.service.general.smart.report.catalog.mapper.GeneralReportCatalogMapper;
import com.dooleen.service.general.smart.report.catalog.service.GeneralReportCatalogService;
import com.dooleen.service.general.util.BuildCatlogTree;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-05 14:49:49
 * @Description : 报表目录管理服务实现
 * @Author : apple
 * @Update: 2020-08-05 14:49:49
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralReportCatalogServiceImpl extends ServiceImpl<GeneralReportCatalogMapper, GeneralReportCatalogEntity> implements GeneralReportCatalogService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  GeneralReportCatalogMapper generalReportCatalogMapper;
	
	@Autowired
	private BuildCatlogTree buildCatlogTree;
	
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//GENERAL_REPORT_CATALOG("general_report_catalog", "GeneralReportCatalog", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.GENERAL_REPORT_CATALOG.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_REPORT_CATALOG.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> queryGeneralReportCatalog(CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralReportCatalogEntity generalReportCatalogEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalReportCatalogEntity,commonMsg);
	    /** 不需要流程请自行删除  开始 */
		//判断是否需要查流程信息
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = generalFlowProcessRecordMapper.selectById(commonMsg.getBody().getFlowArea().getProcessRecordId());
			if(generalFlowProcessRecordEntity != null) {
				commonMsg.getBody().getFlowArea().setProcessRecordId(generalFlowProcessRecordEntity.getId());
				commonMsg.getBody().getFlowArea().setFlowExtData((JSONObject) JSON.parse(generalFlowProcessRecordEntity.getDataArea()));
				commonMsg.getBody().getFlowArea().setFlowEndFlag(generalFlowProcessRecordEntity.getFlowEndFlag());
			}
		}
		/** 不需要流程请自行删除  结束 */
		
		commonMsg.getBody().setSingleBody(generalReportCatalogEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	//@Cacheable(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+#commonMsg.body.singleBody.orgNo")
	public CommonMsg<GeneralReportCatalogEntity,JSONObject> queryReportCatalogTree(CommonMsg<GeneralReportCatalogEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		

		// 查询条件
		GeneralReportCatalogEntity generalReportCatalog = new GeneralReportCatalogEntity();
		generalReportCatalog.setTenantId(commonMsg.getHead().getTenantId());
		generalReportCatalog.setId(commonMsg.getHead().getUserId());
		
		// 获取用户所在的角色组
		List<String> userRoleList = generalReportCatalogMapper.queryRoleListByUserId(commonMsg.getHead().getTenantId(),commonMsg.getHead().getUserId());
		// 获取用户所在用户组的角色组
		List<String> userGroupRoleList = generalReportCatalogMapper.queryRoleListByUserGroupUserId(commonMsg.getHead().getTenantId(),commonMsg.getHead().getUserId());
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = new ArrayList();
		//去重角色id
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i))) {
				roleIdList.add(userRoleList.get(i));
			}
		}
		// 如果角色为空，给一个空值，否则会抛出异常 
		if(roleIdList.size() == 0) {
			roleIdList.add("");
		}		
		log.info("====roleIdList === {}", roleIdList.toString());
		// 根据用户角色获取一级目录id
		List<GeneralReportCatalogEntity>  userCatlogList= generalReportCatalogMapper.queryCatalogByRoleList(roleIdList,commonMsg.getHead().getTenantId());

		// 查询出当前用户所有的一级目录
		List<GeneralReportCatalogEntity>  catlogList= generalReportCatalogMapper.queryByParentCatalogId(generalReportCatalog);
		//合并
		catlogList.addAll(userCatlogList);
		//去重一级目录
		List<GeneralReportCatalogEntity> resultCatalogList = new ArrayList();
		for(int i=0; i<catlogList.size(); i++) {
			if(!resultCatalogList.contains(catlogList.get(i))) {
				resultCatalogList.add(catlogList.get(i));
			}
		}
		log.info("====resultCatalogList === {}", resultCatalogList.toString());
		// 处理目录树
		List<JSONObject> list = new ArrayList<>();
		for (GeneralReportCatalogEntity generalReportCatalogEntity : resultCatalogList) {
			JSONObject treeObject = new JSONObject();
			treeObject.put("id", generalReportCatalogEntity.getId());
			treeObject.put("catalogName", generalReportCatalogEntity.getCatalogName());
			treeObject.put("parentCatalogId", generalReportCatalogEntity.getParentCatalogId());
			treeObject.put("readFlag", generalReportCatalogEntity.getReadFlag());
			treeObject.put("ownerId", generalReportCatalogEntity.getOwnerId());
			treeObject.put("children", buildCatlogTree.getReportChildren(generalReportCatalogEntity,generalReportCatalogEntity.getReadFlag()));
			list.add(treeObject);
		}
		
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(99999);
		commonMsg.getBody().setTotal(99999);

		BizResponseEnum.DATA_NOT_FOUND.assertNotEmpty(catlogList);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogList(CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralReportCatalogEntity> generalReportCatalogEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalReportCatalogEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogListPage(
			CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> commonMsg) {
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
		QueryWrapper queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralReportCatalogEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralReportCatalogEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralReportCatalogEntity.class);
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
		Page<GeneralReportCatalogEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralReportCatalogEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogListMap(
			CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> commonMsg) {
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
		Collection<GeneralReportCatalogEntity> generalReportCatalogEntityList =  super.listByMap(conditionMap);
		List<GeneralReportCatalogEntity> generalReportCatalogMapList = new ArrayList<GeneralReportCatalogEntity>(generalReportCatalogEntityList);
		commonMsg.getBody().setListBody(generalReportCatalogMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> saveGeneralReportCatalog(CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> saveGeneralReportCatalogList(CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
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
	@CachePut(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> updateGeneralReportCatalog(CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		
		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		//由树形菜单发起的修改不做更改校验
		if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getDataSign())) {
			// 根据Key值查询修改记录，需进行深拷贝！！
			log.debug("====开始调用查询方法 ====== ");
			CommonMsg<GeneralReportCatalogEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralReportCatalogEntity, NullEntity>();
			//定义singleBody
			GeneralReportCatalogEntity singleBody = new GeneralReportCatalogEntity();
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
			//定义body
			MutBean<GeneralReportCatalogEntity, NullEntity> mutBean= new MutBean<GeneralReportCatalogEntity, NullEntity>();
			FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
			mutBean.setSingleBody(singleBody);
			mutBean.setFlowArea(flowArea);
			//深拷贝数据进行AOP查询
			queryCommonMsg.setHead(commonMsg.getHead());
			queryCommonMsg.setBody(mutBean); 
			queryCommonMsg = SpringUtil.getBean(GeneralReportCatalogServiceImpl.class).queryGeneralReportCatalog(queryCommonMsg); 
			
			//判断修改内容是否被其他用户修改
			if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
				return commonMsg;
			} 
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
	@CachePut(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> saveOrUpdateGeneralReportCatalog(CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralReportCatalogEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralReportCatalogEntity, NullEntity>();
		//定义singleBody
		GeneralReportCatalogEntity singleBody = new GeneralReportCatalogEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralReportCatalogEntity, NullEntity> mutBean= new MutBean<GeneralReportCatalogEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralReportCatalogServiceImpl.class).queryGeneralReportCatalog(queryCommonMsg); 
		
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
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> saveOrUpdateGeneralReportCatalogList(CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		

		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "generalReportCatalog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> deleteGeneralReportCatalog(CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> deleteGeneralReportCatalogList(CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
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
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportGeneralReportCatalogExcel(CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
		
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		sqlConditionList.add(sqlConditionEntity);
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
		}

		QueryWrapper queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralReportCatalogEntity.class);
		
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
		Page<GeneralReportCatalogEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralReportCatalogEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "报表目录";
		String fileName = "报表目录";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("目录名称");
		headList.add("上级目录ID");
		headList.add("上级目录名称");
		headList.add("拥有人ID");
		headList.add("排序序号");
		excelData.add(headList);
		for (GeneralReportCatalogEntity generalReportCatalogEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalReportCatalogEntity.getId());	    		
			lists.add(generalReportCatalogEntity.getTenantId());	    		
			lists.add(generalReportCatalogEntity.getCatalogName());	    		
			lists.add(generalReportCatalogEntity.getParentCatalogId());	    		
			lists.add(generalReportCatalogEntity.getParentCatalogName());	    		
			lists.add(generalReportCatalogEntity.getOwnerId());	    		
			lists.add(String.valueOf(generalReportCatalogEntity.getOrderSeq()));	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
