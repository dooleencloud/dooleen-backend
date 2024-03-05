package com.dooleen.service.system.tenant.privilege.service.impl;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
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
import com.dooleen.service.system.tenant.privilege.entity.SysTenantPrivilegeRelationEntity;
import com.dooleen.service.system.tenant.privilege.mapper.SysTenantPrivilegeRelationMapper;
import com.dooleen.service.system.tenant.privilege.service.SysTenantPrivilegeRelationService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 17:17:34
 * @Description : 系统租户权限关系管理服务实现
 * @Author : apple
 * @Update: 2020-06-11 17:17:34
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysTenantPrivilegeRelationServiceImpl extends ServiceImpl<SysTenantPrivilegeRelationMapper, SysTenantPrivilegeRelationEntity> implements SysTenantPrivilegeRelationService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private  SysTenantPrivilegeRelationMapper sysTenantPrivilegeRelationMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_TENANT_PRIVILEGE_RELATION.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_TENANT_PRIVILEGE_RELATION.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysTenantPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> querySysTenantPrivilegeRelation(CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setSingleBody(sysTenantPrivilegeRelationEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysTenantPrivilegeRelationEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysTenantPrivilegeRelationEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationList(CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysTenantPrivilegeRelationEntity> sysTenantPrivilegeRelationEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysTenantPrivilegeRelationEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationListPage(
			CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		}
		else {
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
		QueryWrapper<SysTenantPrivilegeRelationEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantPrivilegeRelationEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantPrivilegeRelationEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysTenantPrivilegeRelationEntity.class);
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
		Page<SysTenantPrivilegeRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysTenantPrivilegeRelationEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationListMap(
			CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
		Collection<SysTenantPrivilegeRelationEntity> sysTenantPrivilegeRelationEntityList =  super.listByMap(conditionMap);
		List<SysTenantPrivilegeRelationEntity> sysTenantPrivilegeRelationMapList = new ArrayList<SysTenantPrivilegeRelationEntity>(sysTenantPrivilegeRelationEntityList);
		commonMsg.getBody().setListBody(sysTenantPrivilegeRelationMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysTenantPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> saveSysTenantPrivilegeRelation(CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> saveSysTenantPrivilegeRelationList(CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	@CachePut(value = "sysTenantPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> updateSysTenantPrivilegeRelation(CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity>();
		//定义singleBody
		SysTenantPrivilegeRelationEntity singleBody = new SysTenantPrivilegeRelationEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysTenantPrivilegeRelationEntity, NullEntity> mutBean= new MutBean<SysTenantPrivilegeRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysTenantPrivilegeRelationServiceImpl.class).querySysTenantPrivilegeRelation(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		Map<String, String> map = new HashMap<String, String>(2);
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
	@CachePut(value = "sysTenantPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> saveOrUpdateSysTenantPrivilegeRelation(CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity>();
		//定义singleBody
		SysTenantPrivilegeRelationEntity singleBody = new SysTenantPrivilegeRelationEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysTenantPrivilegeRelationEntity, NullEntity> mutBean= new MutBean<SysTenantPrivilegeRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysTenantPrivilegeRelationServiceImpl.class).querySysTenantPrivilegeRelation(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		Map<String, String> map = new HashMap<String, String>(2);
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
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> saveOrUpdateSysTenantPrivilegeRelationList(CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		
		Map<String, String> map = new HashMap<String, String>(2);
		// 批量保存
		List<String> deleteIdList = new ArrayList();
		List<SysTenantPrivilegeRelationEntity> addList = new ArrayList();		
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setResourceId(commonMsg.getBody().getListBody().get(i).getTenantId()+ commonMsg.getBody().getListBody().get(i).getResourceId().substring(8,commonMsg.getBody().getListBody().get(i).getResourceId().length()));
			log.info("-=============----{}", commonMsg.getBody().getListBody().get(i).toString());
			//删除
			if(commonMsg.getBody().getListBody().get(i).getValidFlag().equals("0")) {
				QueryWrapper<SysTenantPrivilegeRelationEntity> queryWrapper = new QueryWrapper<SysTenantPrivilegeRelationEntity>();
				queryWrapper.lambda().eq(SysTenantPrivilegeRelationEntity::getTenantId, commonMsg.getBody().getListBody().get(i).getTenantId());
				queryWrapper.lambda().eq(SysTenantPrivilegeRelationEntity::getUserTenantId, commonMsg.getBody().getListBody().get(i).getUserTenantId());
				queryWrapper.lambda().eq(SysTenantPrivilegeRelationEntity::getResourceType, commonMsg.getBody().getListBody().get(i).getResourceType());
				queryWrapper.lambda().eq(SysTenantPrivilegeRelationEntity::getResourceId, commonMsg.getBody().getListBody().get(i).getResourceId());
				SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity = sysTenantPrivilegeRelationMapper.selectOne(queryWrapper);
				if(sysTenantPrivilegeRelationEntity != null) {
					deleteIdList.add(sysTenantPrivilegeRelationEntity.getId());
				}
			}
			// 新增关系记录
			else {
				commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
				addList.add(EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
			}
		}		
		//如果删除ID不为空，批量删除记录
		if(deleteIdList.size() > 0) {
			super.removeByIds(deleteIdList);
		}
		//如果添加记录不为空，批量添加记录
		if(addList.size() > 0) {
			super.saveBatch(addList);
		}
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}


	@Override
    @CacheEvict(value = "sysTenantPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> deleteSysTenantPrivilegeRelation(CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> deleteSysTenantPrivilegeRelationList(CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
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
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysTenantPrivilegeRelationExcel(CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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

		QueryWrapper<SysTenantPrivilegeRelationEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantPrivilegeRelationEntity.class);
		
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
		Page<SysTenantPrivilegeRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysTenantPrivilegeRelationEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统租户权限关系表";
		String fileName = "系统租户权限关系表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("用户租户ID");
		headList.add("资源类型");
		headList.add("资源ID");
		excelData.add(headList);
		for (SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysTenantPrivilegeRelationEntity.getId());	    		
			lists.add(sysTenantPrivilegeRelationEntity.getTenantId());	    		
			lists.add(sysTenantPrivilegeRelationEntity.getUserTenantId());	    		
			lists.add(sysTenantPrivilegeRelationEntity.getResourceType());	    		
			lists.add(sysTenantPrivilegeRelationEntity.getResourceId());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
