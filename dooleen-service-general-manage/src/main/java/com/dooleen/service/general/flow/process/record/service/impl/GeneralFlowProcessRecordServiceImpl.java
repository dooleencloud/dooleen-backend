package com.dooleen.service.general.flow.process.record.service.impl;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.system.user.info.service.UserInfoService;
import com.dooleen.common.core.common.entity.*;
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
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.service.general.flow.process.record.service.GeneralFlowProcessRecordService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-04 04:16:29
 * @Description : 流程处理记录管理服务实现
 * @Author : apple
 * @Update: 2020-07-04 04:16:29
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralFlowProcessRecordServiceImpl extends ServiceImpl<GeneralFlowProcessRecordMapper, GeneralFlowProcessRecordEntity> implements GeneralFlowProcessRecordService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

//	@Autowired
//	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
    
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_FLOW_PROCESS_RECORD.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_FLOW_PROCESS_RECORD.getEntityName();
	
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;

    //获取头像列表
    @Autowired
	public UserInfoService userInfoService;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalFlowProcessRecord", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> queryGeneralFlowProcessRecord(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFlowProcessRecordEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(generalFlowProcessRecordEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordList(CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalFlowProcessRecordEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordListPage(
			CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg) {
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
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0")  || entry.getValue().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0)))) {
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
		QueryWrapper<GeneralFlowProcessRecordEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFlowProcessRecordEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFlowProcessRecordEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralFlowProcessRecordEntity.class);
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
		Page<GeneralFlowProcessRecordEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralFlowProcessRecordEntity> list =  super.page(pages, queryWrapper);
		

		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = list.getRecords();

		// 获取对应用户的头像
		Map<String, SendMsgUserInfoEntity> userHeadImgMap = userInfoService.getHeadImgUrlList(generalFlowProcessRecordEntityList,"launchUserName");

		//处理时间差
		for(int i = 0; i < generalFlowProcessRecordEntityList.size(); i++) {
			String  nowDate = DateUtils.getLongDateStr();
			if(StringUtils.isNotEmpty(generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime()) && !generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime().equals(DateUtils.initialDatatime())) {
				nowDate = generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime();
			}
			generalFlowProcessRecordEntityList.get(i).setNodeWaitDuration(DateUtilPro.timeDiff(generalFlowProcessRecordEntityList.get(i).getProcessBeginDatetime(),nowDate));
			generalFlowProcessRecordEntityList.get(i).setHeadImgUrl(userHeadImgMap.get(generalFlowProcessRecordEntityList.get(i).getLaunchUserName()).getHeadImgUrl());
			generalFlowProcessRecordEntityList.get(i).setBelongDeptName(userHeadImgMap.get(generalFlowProcessRecordEntityList.get(i).getLaunchUserName()).getBelongDeptName());
		}
		
		commonMsg.getBody().setListBody(generalFlowProcessRecordEntityList);
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordListMap(
			CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg) {
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
		Collection<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList =  super.listByMap(conditionMap);
		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordMapList = new ArrayList<GeneralFlowProcessRecordEntity>(generalFlowProcessRecordEntityList);
		commonMsg.getBody().setListBody(generalFlowProcessRecordMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalFlowProcessRecord", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> saveGeneralFlowProcessRecord(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> saveGeneralFlowProcessRecordList(CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg) {
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
	@CachePut(value = "generalFlowProcessRecord", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> updateGeneralFlowProcessRecord(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralFlowProcessRecordEntity, NullEntity>();
		//定义singleBody
		GeneralFlowProcessRecordEntity singleBody = new GeneralFlowProcessRecordEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralFlowProcessRecordEntity, NullEntity> mutBean= new MutBean<GeneralFlowProcessRecordEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralFlowProcessRecordServiceImpl.class).queryGeneralFlowProcessRecord(queryCommonMsg); 
		
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
	@CachePut(value = "generalFlowProcessRecord", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> saveOrUpdateGeneralFlowProcessRecord(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralFlowProcessRecordEntity, NullEntity>();
		//定义singleBody
		GeneralFlowProcessRecordEntity singleBody = new GeneralFlowProcessRecordEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralFlowProcessRecordEntity, NullEntity> mutBean= new MutBean<GeneralFlowProcessRecordEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralFlowProcessRecordServiceImpl.class).queryGeneralFlowProcessRecord(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> saveOrUpdateGeneralFlowProcessRecordList(CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg) {
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
    @CacheEvict(value = "generalFlowProcessRecord", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> deleteGeneralFlowProcessRecord(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> deleteGeneralFlowProcessRecordList(CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg) {
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
	public void exportGeneralFlowProcessRecordExcel(CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<GeneralFlowProcessRecordEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFlowProcessRecordEntity.class);
		
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
		Page<GeneralFlowProcessRecordEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralFlowProcessRecordEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "流程处理记录表";
		String fileName = "流程处理记录表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("流程ID");
		headList.add("流程名称");
		headList.add("节点类型");
		headList.add("前节点ID");
		headList.add("节点ID");
		headList.add("节点名称");
		headList.add("业务ID");
		headList.add("业务类型");
		headList.add("业务名称");
		headList.add("表单类型");
		headList.add("节点状态");
		headList.add("转派委托前ID");
		headList.add("节点阶段状态");
		headList.add("是否转派标志");
		headList.add("转派用户名");
		headList.add("是否委托标志");
		headList.add("委托用户名");
		headList.add("流程结束标志");
		headList.add("处理状态");
		headList.add("处理结果");
		headList.add("处理部门编号");
		headList.add("处理部门名称");
		headList.add("处理人用户名");
		headList.add("处理人姓名");
		headList.add("处理意见");
		headList.add("处理开始时间");
		headList.add("处理完成时间");
		headList.add("表单区");
		headList.add("数据区");
		excelData.add(headList);
		for (GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalFlowProcessRecordEntity.getId());	    		
			lists.add(generalFlowProcessRecordEntity.getTenantId());	    		
			lists.add(generalFlowProcessRecordEntity.getFlowId());	    		
			lists.add(generalFlowProcessRecordEntity.getFlowName());	    		
			lists.add(generalFlowProcessRecordEntity.getNodeType());	    		
			lists.add(generalFlowProcessRecordEntity.getPrevNodeIdList());	    		
			lists.add(generalFlowProcessRecordEntity.getNodeId());	    		
			lists.add(generalFlowProcessRecordEntity.getNodeName());	    		
			lists.add(generalFlowProcessRecordEntity.getBizId());	    		
			lists.add(generalFlowProcessRecordEntity.getBizType());	    		
			lists.add(generalFlowProcessRecordEntity.getBizName());	    		
			lists.add(generalFlowProcessRecordEntity.getFormType());	    		
			lists.add(generalFlowProcessRecordEntity.getNodeStatus());	    		
			lists.add(generalFlowProcessRecordEntity.getTransferDelegatePrevId());	    		
			lists.add(generalFlowProcessRecordEntity.getNodeStageStatus());	    		
			lists.add(generalFlowProcessRecordEntity.getIsTransferFlag());	    		
			lists.add(generalFlowProcessRecordEntity.getTransferUserName());	    		
			lists.add(generalFlowProcessRecordEntity.getIsDelegateFlag());	    		
			lists.add(generalFlowProcessRecordEntity.getDelegateUserName());	    		
			lists.add(generalFlowProcessRecordEntity.getFlowEndFlag());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessStatus());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessResult());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessDeptNo());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessDeptName());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessUserName());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessRealName());	    		
			lists.add(generalFlowProcessRecordEntity.getProcessOpinion());	    		
			lists.add(String.valueOf(generalFlowProcessRecordEntity.getProcessBeginDatetime()));	    		
			lists.add(String.valueOf(generalFlowProcessRecordEntity.getProcessFinishDatetime()));	    		
			lists.add(generalFlowProcessRecordEntity.getFormArea());	    		
			lists.add(generalFlowProcessRecordEntity.getDataArea());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
	/**
	 * 获取流程实例实时处理数据用于流程图展示
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowChartData(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = commonMsg.getBody().getSingleBody();
		generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
		CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
		flowCommonMsg.setHead(commonMsg.getHead());
		commonMsg = generalFlowProcessService.getProcessFlowChartData(flowCommonMsg);
		
		return commonMsg;
	}
	
	/**
	 * 获取流程实例的处理时间线
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowTimelineData(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = commonMsg.getBody().getSingleBody();
		generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
		CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
		flowCommonMsg.setHead(commonMsg.getHead());
		commonMsg = generalFlowProcessService.getProcessFlowTimelineData(flowCommonMsg);
		
		return commonMsg;
	}		
}
