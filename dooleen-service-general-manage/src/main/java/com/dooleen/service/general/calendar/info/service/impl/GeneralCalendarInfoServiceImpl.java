package com.dooleen.service.general.calendar.info.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.dooleen.common.core.utils.DateUtils;
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

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-30 14:39:14
 * @Description : 日历管理服务实现
 * @Author : apple
 * @Update: 2020-07-30 14:39:14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralCalendarInfoServiceImpl extends ServiceImpl<GeneralCalendarInfoMapper, GeneralCalendarInfoEntity> implements GeneralCalendarInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	
	@Autowired
	private  GeneralCalendarInfoMapper generalCalendarInfoMapper;
	
	@Autowired
	private GeneralScheduleInfoMapper scheduleMapper;

	private  static String seqPrefix= TableEntityORMEnum.GENERAL_CALENDAR_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_CALENDAR_INFO.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Override
	@Cacheable(value = "generalCalendarInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralCalendarInfoEntity, NullEntity> queryGeneralCalendarInfo(CommonMsg<GeneralCalendarInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralCalendarInfoEntity generalCalendarInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalCalendarInfoEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(generalCalendarInfoEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralCalendarInfoEntity> queryGeneralCalendarInfoList(CommonMsg<NullEntity, GeneralCalendarInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralCalendarInfoEntity> generalCalendarInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalCalendarInfoEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> queryGeneralCalendarInfoListPage(
			CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> commonMsg) {
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
		QueryWrapper<GeneralCalendarInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralCalendarInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralCalendarInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralCalendarInfoEntity.class);
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
		Page<GeneralCalendarInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralCalendarInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> queryGeneralCalendarInfoListMap(
			CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> commonMsg) {
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
		Collection<GeneralCalendarInfoEntity> generalCalendarInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralCalendarInfoEntity> generalCalendarInfoMapList = new ArrayList<GeneralCalendarInfoEntity>(generalCalendarInfoEntityList);
		commonMsg.getBody().setListBody(generalCalendarInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalCalendarInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralCalendarInfoEntity, NullEntity> saveGeneralCalendarInfo(CommonMsg<GeneralCalendarInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralCalendarInfoEntity> saveGeneralCalendarInfoList(CommonMsg<NullEntity, GeneralCalendarInfoEntity> commonMsg) {
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
	@CachePut(value = "generalCalendarInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralCalendarInfoEntity, NullEntity> updateGeneralCalendarInfo(CommonMsg<GeneralCalendarInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralCalendarInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralCalendarInfoEntity, NullEntity>();
		//定义singleBody
		GeneralCalendarInfoEntity singleBody = new GeneralCalendarInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralCalendarInfoEntity, NullEntity> mutBean= new MutBean<GeneralCalendarInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralCalendarInfoServiceImpl.class).queryGeneralCalendarInfo(queryCommonMsg); 
		
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
	@CachePut(value = "generalCalendarInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralCalendarInfoEntity, NullEntity> saveOrUpdateGeneralCalendarInfo(CommonMsg<GeneralCalendarInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralCalendarInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralCalendarInfoEntity, NullEntity>();
		//定义singleBody
		GeneralCalendarInfoEntity singleBody = new GeneralCalendarInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralCalendarInfoEntity, NullEntity> mutBean= new MutBean<GeneralCalendarInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralCalendarInfoServiceImpl.class).queryGeneralCalendarInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralCalendarInfoEntity> saveOrUpdateGeneralCalendarInfoList(CommonMsg<NullEntity, GeneralCalendarInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalCalendarInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralCalendarInfoEntity, NullEntity> deleteGeneralCalendarInfo(CommonMsg<GeneralCalendarInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralCalendarInfoEntity> deleteGeneralCalendarInfoList(CommonMsg<NullEntity, GeneralCalendarInfoEntity> commonMsg) {
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
	public void exportGeneralCalendarInfoExcel(CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<GeneralCalendarInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralCalendarInfoEntity.class);
		
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
		Page<GeneralCalendarInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralCalendarInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "日历信息表";
		String fileName = "日历信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("日历名称");
		headList.add("日历描述");
		headList.add("日历类型");
		headList.add("拥有人ID");
		headList.add("分享标志");
		headList.add("编辑角色列表");
		headList.add("只读角色列表");
		excelData.add(headList);
		for (GeneralCalendarInfoEntity generalCalendarInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalCalendarInfoEntity.getId());	    		
			lists.add(generalCalendarInfoEntity.getTenantId());	    		
			lists.add(generalCalendarInfoEntity.getCalendarName());	    		
			lists.add(generalCalendarInfoEntity.getCalendarDesc());	    		
			lists.add(generalCalendarInfoEntity.getCalendarType());	    		
			lists.add(generalCalendarInfoEntity.getOwnerId());	    		
			lists.add(generalCalendarInfoEntity.getShareFlag());	    		  		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}

	@Override
	public CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> queryPermCalendarList(
			CommonMsg<GeneralCalendarInfoEntity, GeneralCalendarInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		String tenantId = commonMsg.getHead().getTenantId();
		String ownerId = commonMsg.getBody().getSingleBody().getOwnerId();
		
		// 获取用户拥有的日历
		QueryWrapper<GeneralCalendarInfoEntity> calQueryWrapper = new QueryWrapper<GeneralCalendarInfoEntity>();
		calQueryWrapper.eq("tenant_id", tenantId);
		calQueryWrapper.eq("owner_id", ownerId);
		List<GeneralCalendarInfoEntity> ownerList = super.list(calQueryWrapper);
//		List<String> ownerCalIds = ownerList.parallelStream().map(GeneralCalendarInfoEntity::getId).collect(Collectors.toList());
		
		// 获取用户所在的角色组
		List<String> userRoleList = generalCalendarInfoMapper.queryRoleListByUserId(commonMsg.getHead().getTenantId(),commonMsg.getHead().getUserId());
		// 获取用户所在用户组的角色组
		List<String> userGroupRoleList = generalCalendarInfoMapper.queryRoleListByUserGroupUserId(commonMsg.getHead().getTenantId(),commonMsg.getHead().getUserId());
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = Lists.newArrayList();
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
		
		// 查询拥有权限的日历ID
		List<GeneralCalendarInfoEntity> calendarPris = generalCalendarInfoMapper.queryCalendarByRoleList(tenantId, roleIdList);
//		List<String> priCalIds = calendarPris.parallelStream().map(GeneralCalendarInfoEntity::getId).collect(Collectors.toList());
		
		ownerList.addAll(calendarPris);
		
		List<GeneralCalendarInfoEntity> result = Lists.newArrayList();
		List<String> dateList = Lists.newArrayList();
		if(null != ownerList && !ownerList.isEmpty()) {			
			List<String> calIdsTmp = Lists.newArrayList();
			
			for(GeneralCalendarInfoEntity cal: ownerList) {
				if(!calIdsTmp.contains(cal.getId())) {
					result.add(cal);
					calIdsTmp.add(cal.getId());
				}
			}
			
			// 查询所有日历对应的日程信息，只做标识
			List<String> calIds = ownerList.parallelStream().map(GeneralCalendarInfoEntity::getId).collect(Collectors.toList());
			// 查询日历下的所有日程
			QueryWrapper<GeneralScheduleInfoEntity> scheduleWrapper = new QueryWrapper<GeneralScheduleInfoEntity>();
			scheduleWrapper.eq("tenant_id", tenantId)
			.in("calendar_id", calIds)
			.eq("valid_flag", "1");
			List<GeneralScheduleInfoEntity> scheduleList = scheduleMapper.selectList(scheduleWrapper);
			
			// 查询日历工作日参数
//			SysParamEntity sysParamEntity = new SysParamEntity();
//			sysParamEntity.setId(null);
//			sysParamEntity.setTenantId(tenantId);
//			sysParamEntity.setParamKey("calendarWorkdayParam");
//			SysParamEntity sysParamEntity1 = new SysParamEntity();
			
//			CommonMsg<SysParamEntity, SysParamEntity> sysParamCommonMsg = CreateCommonMsg.getCommonMsg(sysParamEntity, sysParamEntity1);
//			sysParamCommonMsg.setHead(commonMsg.getHead());
//			sysParamCommonMsg.getHead().setTransCode("querySysParamListPage");
//			CommonMsg<SysParamEntity, SysParamEntity> sysParamList = iPlatformHystrix.querySysParamListPage(sysParamCommonMsg);
//			String startParam = " 00:00:00";
//			String endParam = " 23:59:59";
//			if(null != sysParamList 
//					&& null != sysParamList.getBody().getListBody() 
//					&& !sysParamList.getBody().getListBody().isEmpty()) {
//				String paramStr = sysParamList.getBody().getListBody().get(0).getParamValue();
//				List<HashMap> paramMap = JSONArray.parseArray(paramStr, HashMap.class);
//				startParam = String.valueOf(paramMap.get(0).get("startTime")) + ":00";
//				endParam = String.valueOf(paramMap.get(0).get("endTime")) + ":00";
//			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = "";
			String endTime = "";
//			String startDateStr = "";
//			String endDateStr = "";
			try {
				for(GeneralScheduleInfoEntity schedule: scheduleList) {
					startTime = schedule.getScheduleBeginDatetime();
//					startDateStr = startTime.substring(0, 10);
					endTime = schedule.getScheduleEndDatetime();
//					endDateStr = endTime.substring(0, 10);
					
//					// 判断开始日期的点是否大于参数的end
//					if(sdf.parse(startTime).getTime() > sdf.parse(startDateStr + " " + endParam).getTime()) {
//						startTime = sdf.format(new Date(sdf.parse(startTime).getTime() + 1000 * 60 * 60 * 24));
//					}
//					// 判断结束日期的点是否小于参数的start
//					if(sdf.parse(endTime).getTime() < sdf.parse(endDateStr + " " + startParam).getTime()) {
//						endTime = sdf.format(new Date(sdf.parse(endTime).getTime() - (1000 * 60 * 60 * 24 - 1000)));
//					}
					List<String> betweenDays = DateUtils.getBetweenDays(sdf.parse(startTime), sdf.parse(endTime));
					
					betweenDays.forEach(item -> {
						dateList.add(item);
					});
				}
			} catch (ParseException e) {
				log.error("queryGeneralCalendarInfoList service====>>>日期抓换出错！！！！");
				e.printStackTrace();
			}
		}
		
		commonMsg.getBody().getSingleBody().setDateList(dateList);
		commonMsg.getBody().setListBody(result);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotEmpty(ownerList,commonMsg);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
