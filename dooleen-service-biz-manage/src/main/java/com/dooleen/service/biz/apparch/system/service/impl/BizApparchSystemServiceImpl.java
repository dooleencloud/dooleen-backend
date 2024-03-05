package com.dooleen.service.biz.apparch.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dooleen.service.biz.apparch.system.entity.BizApparchSystemEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
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
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.biz.apparch.system.mapper.BizApparchSystemMapper;
import com.dooleen.service.biz.apparch.system.service.BizApparchSystemService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 20:02:53
 * @Description : 应用系统管理服务实现
 * @Author : apple
 * @Update: 2020-08-31 20:02:53
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizApparchSystemServiceImpl extends ServiceImpl<BizApparchSystemMapper, BizApparchSystemEntity> implements BizApparchSystemService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  BizApparchSystemMapper bizApparchSystemMapper;
	
	@Autowired
	private  GetBizParamsService getBizParamsService;
	
	
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_APPARCH_SYSTEM("biz_apparch_system", "BizApparchSystem", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.BIZ_APPARCH_SYSTEM.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_APPARCH_SYSTEM.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "bizApparchSystem", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizApparchSystemEntity, NullEntity> queryBizApparchSystem(CommonMsg<BizApparchSystemEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizApparchSystemEntity bizApparchSystemEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizApparchSystemEntity,commonMsg);
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
			
		commonMsg.getBody().setSingleBody(bizApparchSystemEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizApparchSystemEntity> queryBizApparchSystemList(CommonMsg<NullEntity, BizApparchSystemEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizApparchSystemEntity> bizApparchSystemEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizApparchSystemEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizApparchSystemEntity, BizApparchSystemEntity> queryBizApparchSystemListPage(
			CommonMsg<BizApparchSystemEntity, BizApparchSystemEntity> commonMsg) {
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
				if (!entry.getKey().equals("isQueryChildren") && !(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
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
		QueryWrapper<BizApparchSystemEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizApparchSystemEntity.class);
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
		Page<BizApparchSystemEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizApparchSystemEntity> list =  super.page(pages, queryWrapper);
		// 查找是否有子机构
		for(int i = 0; i < list.getRecords().size(); i++) {
			int count=bizApparchSystemMapper.queryCountByCondition(list.getRecords().get(i));
			if(count > 0) {
				list.getRecords().get(i).setHasChildren("true");
			}
			else {
				list.getRecords().get(i).setHasChildren("false");
			} 
			list.getRecords().get(i).setSystemNameTmp(list.getRecords().get(i).getSystemName());
		}				
//		// 查找是否有子机构
//		// 特殊需求 SystemNameTmp 用于树形展示
//		for(int i = 0; i < list.getRecords().size(); i++) {
//			list.getRecords().get(i).setSystemNameTmp(list.getRecords().get(i).getSystemName());
//			if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getIsQueryChildren()) && commonMsg.getBody().getSingleBody().getIsQueryChildren().equals("true")) {
//				QueryWrapper<BizApparchSystemEntity> bizQueryWrapper = new QueryWrapper<BizApparchSystemEntity>();
//				bizQueryWrapper.lambda().eq(BizApparchSystemEntity::getTenantId, list.getRecords().get(i).getTenantId());
//				bizQueryWrapper.lambda().eq(BizApparchSystemEntity::getParentSystemId, list.getRecords().get(i).getId());
//				List<BizApparchSystemEntity> bizApparchSystemEntityList=bizApparchSystemMapper.selectList(bizQueryWrapper);
//				if(bizApparchSystemEntityList.size() > 0) {
//					for(int j = 0; j < bizApparchSystemEntityList.size(); j++) {
//						bizApparchSystemEntityList.get(j).setSystemNameTmp(bizApparchSystemEntityList.get(j).getSystemName());
//					}
//					list.getRecords().get(i).setChildren(bizApparchSystemEntityList);
//				}
//			}	
//		}
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizApparchSystemEntity, BizApparchSystemEntity> queryBizApparchSystemListMap(
			CommonMsg<BizApparchSystemEntity, BizApparchSystemEntity> commonMsg) {
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
		Collection<BizApparchSystemEntity> bizApparchSystemEntityList =  super.listByMap(conditionMap);
		List<BizApparchSystemEntity> bizApparchSystemMapList = new ArrayList<BizApparchSystemEntity>(bizApparchSystemEntityList);
		commonMsg.getBody().setListBody(bizApparchSystemMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizApparchSystem", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemEntity, NullEntity> saveBizApparchSystem(CommonMsg<BizApparchSystemEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizApparchSystemEntity> saveBizApparchSystemList(CommonMsg<NullEntity, BizApparchSystemEntity> commonMsg) {
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
	@CachePut(value = "bizApparchSystem", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemEntity, NullEntity> updateBizApparchSystem(CommonMsg<BizApparchSystemEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizApparchSystemEntity, NullEntity> queryCommonMsg = new CommonMsg<BizApparchSystemEntity, NullEntity>();
		//定义singleBody
		BizApparchSystemEntity singleBody = new BizApparchSystemEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizApparchSystemEntity, NullEntity> mutBean= new MutBean<BizApparchSystemEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizApparchSystemServiceImpl.class).queryBizApparchSystem(queryCommonMsg); 
		
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
	@CachePut(value = "bizApparchSystem", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizApparchSystemEntity, NullEntity> saveOrUpdateBizApparchSystem(CommonMsg<BizApparchSystemEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizApparchSystemEntity, NullEntity> queryCommonMsg = new CommonMsg<BizApparchSystemEntity, NullEntity>();
		//定义singleBody
		BizApparchSystemEntity singleBody = new BizApparchSystemEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<BizApparchSystemEntity, NullEntity> mutBean= new MutBean<BizApparchSystemEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizApparchSystemServiceImpl.class).queryBizApparchSystem(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, BizApparchSystemEntity> saveOrUpdateBizApparchSystemList(CommonMsg<NullEntity, BizApparchSystemEntity> commonMsg) {
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
    @CacheEvict(value = "bizApparchSystem", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizApparchSystemEntity, NullEntity> deleteBizApparchSystem(CommonMsg<BizApparchSystemEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizApparchSystemEntity> deleteBizApparchSystemList(CommonMsg<NullEntity, BizApparchSystemEntity> commonMsg) {
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
	public void exportBizApparchSystemExcel(CommonMsg<BizApparchSystemEntity, BizApparchSystemEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<BizApparchSystemEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizApparchSystemEntity.class);
		
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
		Page<BizApparchSystemEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizApparchSystemEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "应用系统表";
		String fileName = "应用系统表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("上级系统ID");
		headList.add("上级系统名称");
		headList.add("系统名称");
		headList.add("系统简称");
		headList.add("系统英文名称");
		headList.add("系统类别");
		headList.add("系统类型");
		headList.add("所属部门名称");
		headList.add("开发部门名称");
		headList.add("开发团队名称");
		headList.add("系统负责人");
		headList.add("系统负责人名");
		headList.add("建设方式");
		headList.add("建设类型");
		headList.add("系统版本号");
		headList.add("系统状态");
		headList.add("上线日期");
		headList.add("下线日期");
		headList.add("系统功能描述");
		headList.add("架构层级");
		headList.add("应用群组");
		headList.add("功能组");
		headList.add("重要级别");
		headList.add("安全等级");
		headList.add("RTO分值");
		headList.add("RPO分值");
		headList.add("系统运行日期");
		headList.add("系统运行方式");
		headList.add("可单独部署标志");
		headList.add("中间件列表");
		headList.add("数据库列表");
		headList.add("项目编号");
		headList.add("项目名称");
		headList.add("供应商名称");
		headList.add("供应商负责人");
		headList.add("供应商联系电话");
		excelData.add(headList);
		for (BizApparchSystemEntity bizApparchSystemEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(bizApparchSystemEntity.getId());	    		
			lists.add(bizApparchSystemEntity.getTenantId());	    		
			lists.add(bizApparchSystemEntity.getParentSystemId());	    		
			lists.add(bizApparchSystemEntity.getParentSystemName());	    		
			lists.add(bizApparchSystemEntity.getSystemName());	    		
			lists.add(bizApparchSystemEntity.getSystemShortName());	    		
			lists.add(bizApparchSystemEntity.getSystemEnglishName());	    		
			lists.add(bizApparchSystemEntity.getSystemCategory());	    		
			lists.add(bizApparchSystemEntity.getSystemType());	    		
			lists.add(bizApparchSystemEntity.getBelongDeptName());	    		
			lists.add(bizApparchSystemEntity.getDevelopDeptName());	    		
			lists.add(bizApparchSystemEntity.getDevelopTeamName());	    		
			lists.add(bizApparchSystemEntity.getSystemRespsbUserName());	    		
			lists.add(bizApparchSystemEntity.getSystemRespsbRealName());	    		
			lists.add(bizApparchSystemEntity.getBuildWay());	    		
			lists.add(bizApparchSystemEntity.getBuildType());	    		
			lists.add(bizApparchSystemEntity.getSystemVersionNo());	    		
			lists.add(bizApparchSystemEntity.getSystemStatus());	    		
			lists.add(bizApparchSystemEntity.getOnlineDate());	    		
			lists.add(bizApparchSystemEntity.getOfflineDate());	    		
			lists.add(bizApparchSystemEntity.getSystemFunctionDesc());	    		
			lists.add(bizApparchSystemEntity.getArchitectureLevel());	    		
			lists.add(bizApparchSystemEntity.getAppGroup());	    		
			lists.add(bizApparchSystemEntity.getFunctionGroup());	    		
			lists.add(bizApparchSystemEntity.getImportantGrade());	    		
			lists.add(bizApparchSystemEntity.getSecurityLevel());	    		
			lists.add(String.valueOf(bizApparchSystemEntity.getRtoScore()));	    		
			lists.add(String.valueOf(bizApparchSystemEntity.getRpoScore()));	    		
			lists.add(bizApparchSystemEntity.getSystemRunDate());	    		
			lists.add(bizApparchSystemEntity.getSystemRunWay());	    		
			lists.add(bizApparchSystemEntity.getMayAloneDeployFlag());	    		
			lists.add(bizApparchSystemEntity.getMiddlewareList());	    		
			lists.add(bizApparchSystemEntity.getDatabaseList());	    		
			lists.add(bizApparchSystemEntity.getProjectNo());	    		
			lists.add(bizApparchSystemEntity.getProjectName());	    		
			lists.add(bizApparchSystemEntity.getSupplierName());	    		
			lists.add(bizApparchSystemEntity.getSupplierRespsbName());	    		
			lists.add(bizApparchSystemEntity.getSupplierContactPhoneNo());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportBizApparchSystemExcel service end == " + RespStatus.json.getString("S0000"));
	}}
