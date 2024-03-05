package com.dooleen.service.general.project.privilege.service.impl;
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
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.general.project.privilege.entity.GeneralProjectPrivilegeRelationEntity;
import com.dooleen.service.general.project.privilege.mapper.GeneralProjectPrivilegeRelationMapper;
import com.dooleen.service.general.project.privilege.service.GeneralProjectPrivilegeRelationService;

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
public class GeneralProjectPrivilegeRelationServiceImpl extends ServiceImpl<GeneralProjectPrivilegeRelationMapper, GeneralProjectPrivilegeRelationEntity> implements GeneralProjectPrivilegeRelationService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private  GeneralProjectPrivilegeRelationMapper generalProjectPrivilegeRelationMapper;
	
	private  static String seqPrefix= "";
	private  static String tableName = "GeneralProjectPrivilegeRelation";
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalProjectPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> queryGeneralProjectPrivilegeRelation(CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralProjectPrivilegeRelationEntity generalProjectPrivilegeRelationEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setSingleBody(generalProjectPrivilegeRelationEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalProjectPrivilegeRelationEntity,commonMsg);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationList(CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralProjectPrivilegeRelationEntity> generalProjectPrivilegeRelationEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalProjectPrivilegeRelationEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationListPage(
			CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
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
		QueryWrapper queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList,GeneralProjectPrivilegeRelationEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList,GeneralProjectPrivilegeRelationEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList,GeneralProjectPrivilegeRelationEntity.class);
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
		Page<GeneralProjectPrivilegeRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		log.info("======queryWrapper==== {}", queryWrapper.toString());
		IPage<GeneralProjectPrivilegeRelationEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationListMap(
			CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
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
		Collection<GeneralProjectPrivilegeRelationEntity> generalProjectPrivilegeRelationEntityList =  super.listByMap(conditionMap);
		List<GeneralProjectPrivilegeRelationEntity> generalProjectPrivilegeRelationMapList = new ArrayList<GeneralProjectPrivilegeRelationEntity>(generalProjectPrivilegeRelationEntityList);
		commonMsg.getBody().setListBody(generalProjectPrivilegeRelationMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalProjectPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> saveGeneralProjectPrivilegeRelation(CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> saveGeneralProjectPrivilegeRelationList(CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
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
	@CachePut(value = "generalProjectPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> updateGeneralProjectPrivilegeRelation(CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity>();
		//定义singleBody
		GeneralProjectPrivilegeRelationEntity singleBody = new GeneralProjectPrivilegeRelationEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralProjectPrivilegeRelationEntity, NullEntity> mutBean= new MutBean<GeneralProjectPrivilegeRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralProjectPrivilegeRelationServiceImpl.class).queryGeneralProjectPrivilegeRelation(queryCommonMsg); 
		
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
	@CachePut(value = "generalProjectPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> saveOrUpdateGeneralProjectPrivilegeRelation(CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity>();
		//定义singleBody
		GeneralProjectPrivilegeRelationEntity singleBody = new GeneralProjectPrivilegeRelationEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralProjectPrivilegeRelationEntity, NullEntity> mutBean= new MutBean<GeneralProjectPrivilegeRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralProjectPrivilegeRelationServiceImpl.class).queryGeneralProjectPrivilegeRelation(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> saveOrUpdateGeneralProjectPrivilegeRelationList(CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		

		// 批量保存
		List<String> deleteIdList = new ArrayList();
		List<GeneralProjectPrivilegeRelationEntity> addList = new ArrayList();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setResourceId(commonMsg.getBody().getListBody().get(i).getTenantId()+ commonMsg.getBody().getListBody().get(i).getResourceId().substring(8,commonMsg.getBody().getListBody().get(i).getResourceId().length()));
			//删除
			if(commonMsg.getBody().getListBody().get(i).getValidFlag().equals("0")) {
				QueryWrapper<GeneralProjectPrivilegeRelationEntity> queryWrapper = new QueryWrapper<GeneralProjectPrivilegeRelationEntity>();
				queryWrapper.lambda().eq(GeneralProjectPrivilegeRelationEntity::getTenantId, commonMsg.getBody().getListBody().get(i).getTenantId());
				queryWrapper.lambda().eq(GeneralProjectPrivilegeRelationEntity::getProjectId, commonMsg.getBody().getListBody().get(i).getProjectId());
				queryWrapper.lambda().eq(GeneralProjectPrivilegeRelationEntity::getResourceType, commonMsg.getBody().getListBody().get(i).getResourceType());
				queryWrapper.lambda().eq(GeneralProjectPrivilegeRelationEntity::getResourceId, commonMsg.getBody().getListBody().get(i).getResourceId());
				GeneralProjectPrivilegeRelationEntity generalProjectPrivilegeRelationEntity = generalProjectPrivilegeRelationMapper.selectOne(queryWrapper);
				if(generalProjectPrivilegeRelationEntity != null) {
					deleteIdList.add(generalProjectPrivilegeRelationEntity.getId());
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
    @CacheEvict(value = "generalProjectPrivilegeRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> deleteGeneralProjectPrivilegeRelation(CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> deleteGeneralProjectPrivilegeRelationList(CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
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
	public void exportGeneralProjectPrivilegeRelationExcel(CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList,GeneralProjectPrivilegeRelationEntity.class);
		
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
		Page<GeneralProjectPrivilegeRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralProjectPrivilegeRelationEntity> list =  super.page(pages, queryWrapper);
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
		headList.add("项目ID");
		headList.add("资源类型");
		headList.add("资源ID");
		excelData.add(headList);
		for (GeneralProjectPrivilegeRelationEntity generalProjectPrivilegeRelationEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalProjectPrivilegeRelationEntity.getId());	    		
			lists.add(generalProjectPrivilegeRelationEntity.getTenantId());	    		
			lists.add(generalProjectPrivilegeRelationEntity.getProjectId());	    		
			lists.add(generalProjectPrivilegeRelationEntity.getResourceType());	    		
			lists.add(generalProjectPrivilegeRelationEntity.getResourceId());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}

