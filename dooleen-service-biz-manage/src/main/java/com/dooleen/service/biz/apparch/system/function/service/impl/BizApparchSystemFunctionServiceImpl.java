package com.dooleen.service.biz.apparch.system.function.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.biz.apparch.system.function.entity.BizApparchSystemFunctionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.biz.apparch.system.function.mapper.BizApparchSystemFunctionMapper;
import com.dooleen.service.biz.apparch.system.function.service.BizApparchSystemFunctionService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.CommonUtil;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-09-02 11:54:57
 * @Description : 系统功能点管理服务实现
 * @Author : apple
 * @Update: 2020-09-02 11:54:57
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizApparchSystemFunctionServiceImpl extends ServiceImpl<BizApparchSystemFunctionMapper, BizApparchSystemFunctionEntity> implements BizApparchSystemFunctionService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  BizApparchSystemFunctionMapper bizApparchSystemFunctionMapper;
	
	@Autowired
	private  GetBizParamsService getBizParamsService;
	
	
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_APPARCH_SYSTEM_FUNCTION("biz_apparch_system_function", "BizApparchSystemFunction", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.BIZ_APPARCH_SYSTEM_FUNCTION.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_APPARCH_SYSTEM_FUNCTION.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "bizApparchSystemFunction", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizApparchSystemFunctionEntity, NullEntity> queryBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizApparchSystemFunctionEntity bizApparchSystemFunctionEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizApparchSystemFunctionEntity,commonMsg);
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
			
		commonMsg.getBody().setSingleBody(bizApparchSystemFunctionEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizApparchSystemFunctionEntity> bizApparchSystemFunctionEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizApparchSystemFunctionEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizApparchSystemFunctionEntity, BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionListPage(
			CommonMsg<BizApparchSystemFunctionEntity, BizApparchSystemFunctionEntity> commonMsg) {
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
		QueryWrapper<BizApparchSystemFunctionEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemFunctionEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemFunctionEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizApparchSystemFunctionEntity.class);
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
		Page<BizApparchSystemFunctionEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizApparchSystemFunctionEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizApparchSystemFunctionEntity, BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionListMap(
			CommonMsg<BizApparchSystemFunctionEntity, BizApparchSystemFunctionEntity> commonMsg) {
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
		Collection<BizApparchSystemFunctionEntity> bizApparchSystemFunctionEntityList =  super.listByMap(conditionMap);
		List<BizApparchSystemFunctionEntity> bizApparchSystemFunctionMapList = new ArrayList<BizApparchSystemFunctionEntity>(bizApparchSystemFunctionEntityList);
		commonMsg.getBody().setListBody(bizApparchSystemFunctionMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizApparchSystemFunction", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemFunctionEntity, NullEntity> saveBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg) {
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
		BizApparchSystemFunctionEntity bizApparchSystemFunctionEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
		//如果新增的时候场景编号为空，则将ID赋值给场景编号
		if(StringUtils.isEmpty(bizApparchSystemFunctionEntity.getSystemFunctionNo())) {
			bizApparchSystemFunctionEntity.setSystemFunctionNo(bizApparchSystemFunctionEntity.getId());
		}
		boolean result =  super.save(bizApparchSystemFunctionEntity);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizApparchSystemFunctionEntity> saveBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg) {
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
	@CachePut(value = "bizApparchSystemFunction", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemFunctionEntity, NullEntity> updateBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}
		/** 不需要流程请自行删除  开始 */
		// 判断是否需要处理流程 
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			FlowProcessDataEntity flowProcessDataEntity = commonMsg.getBody().getFlowArea();
			// 流程操作标志 flowOptFlag 1-开始启动 2-处理流程 3-撤回流程
			if(flowProcessDataEntity.getFlowOptFlag().equals("1")) {
				GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
                // 在业务字典定义： 业务流程ID列表（字典名：flowIdList； 字典值：示例：bizAppArchFlowId）
			    String flowId = getBizParamsService.getBizDictValue(getBizParamsService.getBizDict(commonMsg, "flowIdList"),"bizAppArchFlowId");
				generalFlowProcessRecordEntity.setFlowId("flowId");
				// 请将业务类型改为实际的
				generalFlowProcessRecordEntity.setBizType("");
				generalFlowProcessRecordEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName("");
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.startFlow(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			}
			else if(flowProcessDataEntity.getFlowOptFlag().equals("2")) {
				GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				generalFlowProcessRecordEntity.setId(commonMsg.getBody().getFlowArea().getProcessRecordId());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizType("");
				generalFlowProcessRecordEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName("");
				generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getFlowArea().getFlowExtData().toString());
				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.processFlow(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			}
			else if(flowProcessDataEntity.getFlowOptFlag().equals("3")) {
				GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				generalFlowProcessRecordEntity.setId(commonMsg.getBody().getFlowArea().getProcessRecordId());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizType("");
				generalFlowProcessRecordEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName("");
				generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getFlowArea().getFlowExtData().toString());
				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.processCallBack(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			}
			//处理失败直接返回
			if(!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
			
		}
		/** 不需要流程请自行删除 结束 */
		
		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<BizApparchSystemFunctionEntity, NullEntity> queryCommonMsg = new CommonMsg<BizApparchSystemFunctionEntity, NullEntity>();
		//定义singleBody
		BizApparchSystemFunctionEntity singleBody = new BizApparchSystemFunctionEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizApparchSystemFunctionEntity, NullEntity> mutBean= new MutBean<BizApparchSystemFunctionEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizApparchSystemFunctionServiceImpl.class).queryBizApparchSystemFunction(queryCommonMsg); 
		
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
	@CachePut(value = "bizApparchSystemFunction", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemFunctionEntity, NullEntity> saveOrUpdateBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizApparchSystemFunctionEntity, NullEntity> queryCommonMsg = new CommonMsg<BizApparchSystemFunctionEntity, NullEntity>();
		//定义singleBody
		BizApparchSystemFunctionEntity singleBody = new BizApparchSystemFunctionEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<BizApparchSystemFunctionEntity, NullEntity> mutBean= new MutBean<BizApparchSystemFunctionEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizApparchSystemFunctionServiceImpl.class).queryBizApparchSystemFunction(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, BizApparchSystemFunctionEntity> saveOrUpdateBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg) {
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
    @CacheEvict(value = "bizApparchSystemFunction", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizApparchSystemFunctionEntity, NullEntity> deleteBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizApparchSystemFunctionEntity> deleteBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg) {
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
	public void exportBizApparchSystemFunctionExcel(CommonMsg<BizApparchSystemFunctionEntity, BizApparchSystemFunctionEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<BizApparchSystemFunctionEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemFunctionEntity.class);
		
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
		Page<BizApparchSystemFunctionEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizApparchSystemFunctionEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统功能点表";
		String fileName = "系统功能点表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("业务功能点编号");
		headList.add("系统功能点编号");
		headList.add("版本号");
		headList.add("资产标志");
		headList.add("系统类型");
		headList.add("系统编号");
		headList.add("系统名称");
		headList.add("功能点类型");
		headList.add("业务功能点名称");
		headList.add("系统功能点名称");
		headList.add("系统功能点序号");
		headList.add("是否改造标志");
		headList.add("需求变更编号");
		headList.add("项目编号");
		headList.add("项目名称");
		headList.add("变更日期");
		headList.add("标签信息");
		headList.add("接口标志");
		headList.add("接口编号");
		headList.add("接口名称");
		headList.add("连接方式");
		headList.add("交互类型");
		headList.add("服务方系统编号");
		headList.add("服务方系统名称");
		headList.add("服务方系统状态");
		headList.add("服务方系统功能点编号");
		headList.add("服务方系统功能点名称");
		headList.add("服务方接口编号");
		headList.add("服务方接口名称");
		excelData.add(headList);
		for (BizApparchSystemFunctionEntity bizApparchSystemFunctionEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(bizApparchSystemFunctionEntity.getBizFunctionNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getSystemFunctionNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getVersionNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getAssetFlag());	    		
			lists.add(bizApparchSystemFunctionEntity.getSystemType());	    		
			lists.add(bizApparchSystemFunctionEntity.getSystemNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getSystemName());	    		
			lists.add(bizApparchSystemFunctionEntity.getFunctionType());	    		
			lists.add(bizApparchSystemFunctionEntity.getBizFunctionName());	    		
			lists.add(bizApparchSystemFunctionEntity.getSystemFunctionName());	    		
			lists.add(String.valueOf(bizApparchSystemFunctionEntity.getSystemFunctionSeq()));	    		
			lists.add(bizApparchSystemFunctionEntity.getIsReformFlag());	    		
			lists.add(bizApparchSystemFunctionEntity.getDemandChangeNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getProjectNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getProjectName());	    		
			lists.add(bizApparchSystemFunctionEntity.getChangeDate());	    		
			lists.add(bizApparchSystemFunctionEntity.getLabelInfo());	    		
			lists.add(bizApparchSystemFunctionEntity.getInterfaceFlag());	    		
			lists.add(bizApparchSystemFunctionEntity.getInterfaceNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getInterfaceName());	    		
			lists.add(bizApparchSystemFunctionEntity.getLinkWay());	    		
			lists.add(bizApparchSystemFunctionEntity.getInteractiveType());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceSystemNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceSystemName());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceSystemStatus());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceSystemFunctionNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceSystemFunctionName());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceInterfaceNo());	    		
			lists.add(bizApparchSystemFunctionEntity.getServiceInterfaceName());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportBizApparchSystemFunctionExcel service end == " + RespStatus.json.getString("S0000"));
	}
}
