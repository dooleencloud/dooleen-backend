package com.dooleen.service.general.biz.plan.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.biz.plan.entity.BizPlanManageEntity;
import com.dooleen.service.general.biz.plan.mapper.BizPlanManageMapper;
import com.dooleen.service.general.biz.plan.service.BizPlanManageService;
import com.dooleen.service.general.biz.plan.utils.BuildPlanTree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 14:39:36
 * @Description : 会议室管理服务实现
 * @Author : apple
 * @Update: 2020-08-31 14:39:36
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizPlanManageServiceImpl extends ServiceImpl<BizPlanManageMapper, BizPlanManageEntity> implements BizPlanManageService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";


	private  static String seqPrefix= TableEntityORMEnum.BIZ_PLAN_MANAGE.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_PLAN_MANAGE.getEntityName();
	
	@Autowired
	private BizPlanManageMapper bizPlanManageMapper;
	
	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
	
	@Autowired
	private BuildPlanTree buildPlanTree; 

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Override
	@Cacheable(value = "bizPlanManage", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizPlanManageEntity, NullEntity> queryBizPlanManage(CommonMsg<BizPlanManageEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizPlanManageEntity bizPlanManageEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizPlanManageEntity,commonMsg);

		List<BizPlanManageEntity> planList = new ArrayList<>();
        planList.add(bizPlanManageEntity);
        planList = dealRespsbltPersonAndStakeholder(planList);
		
		commonMsg.getBody().setSingleBody(planList.get(0));
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizPlanManageEntity> queryBizPlanManageList(CommonMsg<NullEntity, BizPlanManageEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizPlanManageEntity> bizPlanManageEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizPlanManageEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizPlanManageEntity, BizPlanManageEntity> queryBizPlanManageListPage(
			CommonMsg<BizPlanManageEntity, BizPlanManageEntity> commonMsg) {
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
		QueryWrapper<BizPlanManageEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizPlanManageEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizPlanManageEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizPlanManageEntity.class);
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
		Page<BizPlanManageEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizPlanManageEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizPlanManageEntity, BizPlanManageEntity> queryBizPlanManageListMap(
			CommonMsg<BizPlanManageEntity, BizPlanManageEntity> commonMsg) {
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
		Collection<BizPlanManageEntity> bizPlanManageEntityList =  super.listByMap(conditionMap);
		List<BizPlanManageEntity> bizPlanManageMapList = new ArrayList<BizPlanManageEntity>(bizPlanManageEntityList);
		commonMsg.getBody().setListBody(bizPlanManageMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizPlanManage", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizPlanManageEntity, NullEntity> saveBizPlanManage(CommonMsg<BizPlanManageEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		
		// 处理干系人
		String stakeholderStr = commonMsg.getBody().getSingleBody().getStakeholderIdList();
		if(StringUtils.isNotBlank(stakeholderStr)) {
			List<String> stakeholderIdList = JSONArray.parseArray(stakeholderStr, String.class);
			if(null != stakeholderIdList && !stakeholderIdList.isEmpty()) {
				List<SysUserInfoEntity> userList = sysUserInfoMapper.selectBatchIds(stakeholderIdList);
				JSONArray stakeholderNameArr = new JSONArray();
				for(String stakeholderId: stakeholderIdList) {
					for(SysUserInfoEntity user: userList) {
						if(stakeholderId.equals(user.getId())) {
							stakeholderNameArr.add(user.getRealName());
							break;
						}
					}
				}
				commonMsg.getBody().getSingleBody().setStakeholderNameList(stakeholderNameArr.toJSONString());
			}
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
	public CommonMsg<NullEntity, BizPlanManageEntity> saveBizPlanManageList(CommonMsg<NullEntity, BizPlanManageEntity> commonMsg) {
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
	@CachePut(value = "bizPlanManage", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizPlanManageEntity, NullEntity> updateBizPlanManage(CommonMsg<BizPlanManageEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizPlanManageEntity, NullEntity> queryCommonMsg = new CommonMsg<BizPlanManageEntity, NullEntity>();
		//定义singleBody
		BizPlanManageEntity singleBody = new BizPlanManageEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizPlanManageEntity, NullEntity> mutBean= new MutBean<BizPlanManageEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizPlanManageServiceImpl.class).queryBizPlanManage(queryCommonMsg); 
		
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
	@CachePut(value = "bizPlanManage", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizPlanManageEntity, NullEntity> saveOrUpdateBizPlanManage(CommonMsg<BizPlanManageEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizPlanManageEntity, NullEntity> queryCommonMsg = new CommonMsg<BizPlanManageEntity, NullEntity>();
		//定义singleBody
		BizPlanManageEntity singleBody = new BizPlanManageEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<BizPlanManageEntity, NullEntity> mutBean= new MutBean<BizPlanManageEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizPlanManageServiceImpl.class).queryBizPlanManage(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, BizPlanManageEntity> saveOrUpdateBizPlanManageList(CommonMsg<NullEntity, BizPlanManageEntity> commonMsg) {
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
    @CacheEvict(value = "bizPlanManage", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizPlanManageEntity, NullEntity> deleteBizPlanManage(CommonMsg<BizPlanManageEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizPlanManageEntity> deleteBizPlanManageList(CommonMsg<NullEntity, BizPlanManageEntity> commonMsg) {
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
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportBizPlanManageExcel(CommonMsg<BizPlanManageEntity, BizPlanManageEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<BizPlanManageEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizPlanManageEntity.class);
		
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
		Page<BizPlanManageEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizPlanManageEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "计划管理";
		String fileName = "计划管理";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("项目ID");
		headList.add("项目名称");
		headList.add("计划名称");
		headList.add("计划描述");
		headList.add("计划层级");
		headList.add("上级计划ID");
		headList.add("上级计划名称");
		headList.add("计划开始时间");
		headList.add("计划完成时间");
		headList.add("进度");
		headList.add("责任人ID");
		headList.add("责任人姓名");
		headList.add("干系人ID列表");
		headList.add("干系人姓名列表");
		excelData.add(headList);
		for (BizPlanManageEntity bizPlanManageEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(bizPlanManageEntity.getProjectId());	    		
			lists.add(bizPlanManageEntity.getProjectName());	    		
			lists.add(bizPlanManageEntity.getPlanName());	    		
			lists.add(bizPlanManageEntity.getPlanDesc());	    		
			lists.add(bizPlanManageEntity.getPlanLevel());	    		
			lists.add(bizPlanManageEntity.getParentPlanId());	    		
			lists.add(bizPlanManageEntity.getParentPlanName());	    		
			lists.add(String.valueOf(bizPlanManageEntity.getPlanBeginDatetime()));	    		
			lists.add(String.valueOf(bizPlanManageEntity.getPlanFinishDatetime()));	    		
			lists.add(String.valueOf(bizPlanManageEntity.getProgress()));	    		
			lists.add(bizPlanManageEntity.getRespsbltPersonId());	    		
			lists.add(bizPlanManageEntity.getRespsbltPersonName());	    		
			lists.add(bizPlanManageEntity.getStakeholderIdList());	    		
			lists.add(bizPlanManageEntity.getStakeholderNameList());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportBizPlanManageExcel service end == " + RespStatus.json.getString("S0000"));
	}

	@Override
	public CommonMsg<BizPlanManageEntity, JSONObject> queryBizPlanTree(CommonMsg<BizPlanManageEntity, JSONObject> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 查询条件
		BizPlanManageEntity planParam = new BizPlanManageEntity();
		planParam.setTenantId(commonMsg.getHead().getTenantId());
		planParam.setProjectId(commonMsg.getBody().getSingleBody().getProjectId());
		
		QueryWrapper<BizPlanManageEntity> queryWrapper = new QueryWrapper<BizPlanManageEntity>();
		queryWrapper.eq("tenant_id", planParam.getTenantId())
					.eq("project_id", planParam.getProjectId())
					.eq("valid_flag", "1");
		
		List<BizPlanManageEntity> planList = super.list(queryWrapper);
		
		List<JSONObject> list = new ArrayList<JSONObject>();

        planList = dealRespsbltPersonAndStakeholder(planList);

		planList.forEach(plan -> {
			JSONObject obj = (JSONObject) JSONObject.toJSON(plan);
			
			Integer duration = DateUtils.countDays(plan.getPlanBeginDatetime(), plan.getPlanFinishDatetime()) - 1;
			
			obj.put("id", plan.getId());
			obj.put("parent", plan.getParentPlanId());
			obj.put("text", plan.getPlanName());
			obj.put("start_date", plan.getPlanBeginDatetime());
			obj.put("duration", duration);
			obj.put("progress", plan.getProgress());
			
			list.add(obj);
		});
				
		commonMsg.getBody().setListBody(list);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	/**
	 * 处理责任人和干系人
	 */
	public List<BizPlanManageEntity> dealRespsbltPersonAndStakeholder(List<BizPlanManageEntity> planList) {
		/**
		 * 处理责任人和干系人
		 */
		List<String> queryUserIdList = new ArrayList<String>();
        List<String> stakeholderIdList = null;
        for (BizPlanManageEntity plan: planList){
            if(StringUtils.isNotBlank(plan.getRespsbltPersonId())) {
                queryUserIdList.add(plan.getRespsbltPersonId());
            }

            if(StringUtils.isNotBlank(plan.getStakeholderIdList())) {
                stakeholderIdList = JSONArray.parseArray(plan.getStakeholderIdList(), String.class);
                if(null != stakeholderIdList && !stakeholderIdList.isEmpty()) {
                    queryUserIdList.addAll(stakeholderIdList);
                }
            }
        }

		// 查询用户信息
		List<SysUserInfoEntity> userList = null;
		if(null != queryUserIdList && !queryUserIdList.isEmpty()) {			
			userList = sysUserInfoMapper.selectBatchIds(queryUserIdList);
		}

        if(null != userList && !userList.isEmpty()) {
            Map<String, String> userMap =  new HashMap<>();
            userList.forEach(user -> {
                userMap.put(user.getId(), user.getRealName());
            });

            if(null != stakeholderIdList){
                stakeholderIdList.clear();
            }
			String stakeholders = "";
			for(BizPlanManageEntity plan: planList){
                stakeholders = "";
                plan.setRespsbltPersonName(userMap.get(plan.getRespsbltPersonId()));
                if(StringUtils.isNotBlank(plan.getStakeholderIdList())){
                    stakeholderIdList = Arrays.asList(plan.getStakeholderIdList().split(";"));
                    for (String stakeholderId: stakeholderIdList) {
                        stakeholders += userMap.get(stakeholderId) + ";";
                    }
                }
                plan.setStakeholderNameList(stakeholders);
            }
		}
		return planList;
	}
}
