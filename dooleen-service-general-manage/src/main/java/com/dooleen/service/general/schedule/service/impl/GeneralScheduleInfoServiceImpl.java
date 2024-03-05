package com.dooleen.service.general.schedule.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.google.common.collect.Lists;
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
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.general.calendar.info.entity.GeneralCalendarInfoEntity;
import com.dooleen.service.general.calendar.info.mapper.GeneralCalendarInfoMapper;
import com.dooleen.service.general.calendar.info.service.GeneralCalendarInfoService;
import com.dooleen.service.general.schedule.entity.GeneralScheduleInfoEntity;
import com.dooleen.service.general.schedule.mapper.GeneralScheduleInfoMapper;
import com.dooleen.service.general.schedule.service.GeneralScheduleInfoService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-03 18:44:09
 * @Description : 日程管理服务实现
 * @Author : apple
 * @Update: 2020-08-03 18:44:09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralScheduleInfoServiceImpl extends ServiceImpl<GeneralScheduleInfoMapper, GeneralScheduleInfoEntity> implements GeneralScheduleInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	
	@Autowired
	private  GeneralScheduleInfoMapper generalScheduleInfoMapper;
	
	@Autowired
	private GeneralCalendarInfoMapper generalCalendarInfoMapper;
	
	@Autowired
	private GeneralCalendarInfoService generalCalendarInfoService;

	private  static String seqPrefix= TableEntityORMEnum.GENERAL_SCHEDULE_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_SCHEDULE_INFO.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalScheduleInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralScheduleInfoEntity, NullEntity> queryGeneralScheduleInfo(CommonMsg<GeneralScheduleInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralScheduleInfoEntity generalScheduleInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalScheduleInfoEntity,commonMsg);
		commonMsg.getBody().setSingleBody(generalScheduleInfoEntity);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralScheduleInfoEntity> queryGeneralScheduleInfoList(CommonMsg<NullEntity, GeneralScheduleInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralScheduleInfoEntity> generalScheduleInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalScheduleInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> queryGeneralScheduleInfoListPage(
			CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> commonMsg) {
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
		QueryWrapper<GeneralScheduleInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralScheduleInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralScheduleInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralScheduleInfoEntity.class);
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
		Page<GeneralScheduleInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralScheduleInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> queryGeneralScheduleInfoListMap(
			CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> commonMsg) {
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
		Collection<GeneralScheduleInfoEntity> generalScheduleInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralScheduleInfoEntity> generalScheduleInfoMapList = new ArrayList<GeneralScheduleInfoEntity>(generalScheduleInfoEntityList);
		commonMsg.getBody().setListBody(generalScheduleInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalScheduleInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralScheduleInfoEntity, NullEntity> saveGeneralScheduleInfo(CommonMsg<GeneralScheduleInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralScheduleInfoEntity> saveGeneralScheduleInfoList(CommonMsg<NullEntity, GeneralScheduleInfoEntity> commonMsg) {
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
	@CachePut(value = "generalScheduleInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralScheduleInfoEntity, NullEntity> updateGeneralScheduleInfo(CommonMsg<GeneralScheduleInfoEntity, NullEntity> commonMsg) {
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
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<GeneralScheduleInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralScheduleInfoEntity, NullEntity>();
		//定义singleBody
		GeneralScheduleInfoEntity singleBody = new GeneralScheduleInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralScheduleInfoEntity, NullEntity> mutBean= new MutBean<GeneralScheduleInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralScheduleInfoServiceImpl.class).queryGeneralScheduleInfo(queryCommonMsg); 
		
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
	@CachePut(value = "generalScheduleInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralScheduleInfoEntity, NullEntity> saveOrUpdateGeneralScheduleInfo(CommonMsg<GeneralScheduleInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralScheduleInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralScheduleInfoEntity, NullEntity>();
		//定义singleBody
		GeneralScheduleInfoEntity singleBody = new GeneralScheduleInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralScheduleInfoEntity, NullEntity> mutBean= new MutBean<GeneralScheduleInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralScheduleInfoServiceImpl.class).queryGeneralScheduleInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralScheduleInfoEntity> saveOrUpdateGeneralScheduleInfoList(CommonMsg<NullEntity, GeneralScheduleInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalScheduleInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralScheduleInfoEntity, NullEntity> deleteGeneralScheduleInfo(CommonMsg<GeneralScheduleInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralScheduleInfoEntity> deleteGeneralScheduleInfoList(CommonMsg<NullEntity, GeneralScheduleInfoEntity> commonMsg) {
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
	public void exportGeneralScheduleInfoExcel(CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
//
		
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

		QueryWrapper<GeneralScheduleInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralScheduleInfoEntity.class);
		
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
		Page<GeneralScheduleInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralScheduleInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "日程信息表";
		String fileName = "日程信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("日历ID");
		headList.add("日程主题");
		headList.add("日程开始时间");
		headList.add("日程结束时间");
		headList.add("日程内容");
		excelData.add(headList);
		for (GeneralScheduleInfoEntity generalScheduleInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalScheduleInfoEntity.getCalendarId());	    		
			lists.add(generalScheduleInfoEntity.getScheduleSubject());	    		
			lists.add(String.valueOf(generalScheduleInfoEntity.getScheduleBeginDatetime()));	    		
			lists.add(String.valueOf(generalScheduleInfoEntity.getScheduleEndDatetime()));	    		
			lists.add(generalScheduleInfoEntity.getScheduleContent());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}

	@Override
	public CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> queryScheduleDuration(
			CommonMsg<GeneralScheduleInfoEntity, GeneralScheduleInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		GeneralScheduleInfoEntity singleBody = commonMsg.getBody().getSingleBody();
		
		// 租户ID
		String tenantId = commonMsg.getHead().getTenantId();
		// 用户ID
		String userId = commonMsg.getHead().getUserName();
		
		// 获取日历ID列表
		List<String> calendarIds = null;
		List<GeneralScheduleInfoEntity> calendarList = commonMsg.getBody().getListBody();
		if(null != calendarList && !calendarList.isEmpty()) {
			calendarIds = calendarList.parallelStream().map(GeneralScheduleInfoEntity::getCalendarId).collect(Collectors.toList());
		}
		
		List<String> readonlyIds = Lists.newArrayList();
		List<String> editAbleIds = Lists.newArrayList();
		
		// 查询用户的日历,判断哪些日历是只读的，哪些是可编辑的
		GeneralCalendarInfoEntity generalCalendarInfoEntity = new GeneralCalendarInfoEntity();
		generalCalendarInfoEntity.setOwnerId(userId);
				
		CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> commonMsgTmp = CreateCommonMsg.getCommonMsg(generalCalendarInfoEntity, generalCalendarInfoEntity);
		commonMsgTmp.setHead(commonMsg.getHead());
		commonMsgTmp = generalCalendarInfoService.queryPermCalendarList(commonMsgTmp);
		
		List<GeneralCalendarInfoEntity> list = commonMsgTmp.getBody().getListBody();
		if(null != list && !list.isEmpty()) {
			list.forEach(item -> {
				if("1".equals(item.getReadFlag())) {
					readonlyIds.add(item.getId());
				}else {
					editAbleIds.add(item.getId());
				}
			});
		}
		
		// 如果数初始化页面调的接口
		if("init".equals(commonMsg.getBody().getSingleBody().getId()) && null != list && !list.isEmpty()) {
			calendarIds = list.parallelStream().map(GeneralCalendarInfoEntity::getId).collect(Collectors.toList());
		}
		
		if(null != calendarIds) {			
			List<GeneralScheduleInfoEntity> scheduleInfos = Lists.newArrayList(); 
			
			// 开始时间
			String startTime = singleBody.getScheduleBeginDatetime();
			// 结束时间
			String endTime = singleBody.getScheduleEndDatetime();
			
			List<GeneralScheduleInfoEntity> scheduleList = generalScheduleInfoMapper.selectScheduleDuration(tenantId, startTime, endTime, calendarIds);
			
			for(GeneralScheduleInfoEntity schedule: scheduleList) {
				if(readonlyIds.contains(schedule.getCalendarId())) {
					schedule.setReadFlag("1");
				}
				if(editAbleIds.contains(schedule.getCalendarId())) {
					schedule.setReadFlag("0");
				}
				scheduleInfos.add(schedule);
			}
				
			
			commonMsg.getBody().setListBody(scheduleInfos);
//			BizResponseEnum.DATA_NOT_FOUND.assertNotEmpty(scheduleList);
		}
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
