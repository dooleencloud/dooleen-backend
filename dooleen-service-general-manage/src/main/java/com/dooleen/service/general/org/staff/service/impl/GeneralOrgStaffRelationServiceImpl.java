package com.dooleen.service.general.org.staff.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.org.staff.mapper.GeneralOrgStaffRelationMapper;
import com.dooleen.service.general.note.book.entity.GeneralNoteBookEntity;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.org.staff.service.GeneralOrgStaffRelationService;
import com.dooleen.service.general.util.BuildOrgTree;
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
import java.util.stream.Collectors;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 10:50:02
 * @Description : 系统组织用户关系管理服务实现
 * @Author : apple
 * @Update: 2020-06-11 10:50:02
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralOrgStaffRelationServiceImpl extends ServiceImpl<GeneralOrgStaffRelationMapper, GeneralOrgStaffRelationEntity> implements GeneralOrgStaffRelationService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private GeneralOrgStaffRelationMapper generalOrgStaffRelationMapper;
	@Autowired
	private  BuildOrgTree buildOrgTree;
	
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_ORG_STAFF_RELATION.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_ORG_STAFF_RELATION.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalOrgStaffRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> queryGeneralOrgStaffRelation(CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralOrgStaffRelationEntity generalOrgStaffRelationEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setSingleBody(generalOrgStaffRelationEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalOrgStaffRelationEntity,commonMsg);
		commonMsg.getBody().setSingleBody(generalOrgStaffRelationEntity);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationList(CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralOrgStaffRelationEntity> generalOrgStaffRelationEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalOrgStaffRelationEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListByPage(
			CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
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
		QueryWrapper<GeneralOrgStaffRelationEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
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
		Page<GeneralOrgStaffRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralOrgStaffRelationEntity> list =  super.page(pages, queryWrapper);

		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	/**
	 * 根据机构号向下查找所有人员信息
	 * @param commonMsg
	 * @return
	 */
	@Override
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListPage(
			CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
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
				if (!entry.getKey().equals("belongOrgNo") && !entry.getKey().equals("parentOrgNo") && !(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
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
		QueryWrapper<GeneralOrgStaffRelationEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
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
		// 查询当前机构下的所有机构号
		if(commonMsg.getBody().getSqlCondition().size() == 0 && commonMsg.getBody().getSingleBody().getBelongOrgNo() != null && !commonMsg.getBody().getSingleBody().getBelongOrgNo().equals("")) {
			GeneralOrgInfoEntity generalOrgInfoEntity =  new GeneralOrgInfoEntity();
			generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
			generalOrgInfoEntity.setOrgNo(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			List<String> tmpLis = new ArrayList<String>();
			tmpLis.add(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			buildOrgTree.getAllChildrenOrgNo(generalOrgInfoEntity,tmpLis);
			queryWrapper.in("belong_org_no", tmpLis);
		}
		// 定义分页查询
		Page<GeneralOrgStaffRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralOrgStaffRelationEntity> list =  super.page(pages, queryWrapper);

		List<GeneralOrgStaffRelationEntity> staffList =new ArrayList<GeneralOrgStaffRelationEntity>();
		List<String> ids = new ArrayList<String>();
		for(int i=0; i< list.getRecords().size();i++) {
			ids.add(list.getRecords().get(i).getId());
		}

		if (ids.size() > 0) {
			//连表查询是否开户
			staffList = generalOrgStaffRelationMapper.queryGeneralOrgStaffRelationByCondition(ids);

			// 连表查询已开户用户的角色信息
			List<String> userIdList = staffList.parallelStream().filter(item -> "1".equals(item.getStatus())).map(GeneralOrgStaffRelationEntity::getUserId).collect(Collectors.toList());
			if(null != userIdList && !userIdList.isEmpty()){
				List<Map<String, String>> userRoleList = generalOrgStaffRelationMapper.queryUserRolesByUserId(commonMsg.getHead().getTenantId(), userIdList);

				for(Map<String, String> userRole: userRoleList){
					for(GeneralOrgStaffRelationEntity staff: staffList){
						if(StringUtils.isNotBlank(staff.getUserId())
								&& StringUtils.isNotBlank(userRole.get("userId"))
								&& staff.getUserId().equals(userRole.get("userId"))){
							if(StringUtils.isBlank(staff.getRoleName())){
								staff.setRoleName("");
							}
							staff.setRoleName(staff.getRoleName() + userRole.get("roleNickName") + "; ");
							break;
						}
					}
				}
			}
		}

		commonMsg.getBody().setListBody(staffList);
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListMap(
			CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
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
		Collection<GeneralOrgStaffRelationEntity> generalOrgStaffRelationEntityList =  super.listByMap(conditionMap);
		List<GeneralOrgStaffRelationEntity> generalOrgStaffRelationMapList = new ArrayList<GeneralOrgStaffRelationEntity>(generalOrgStaffRelationEntityList);
		commonMsg.getBody().setListBody(generalOrgStaffRelationMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalOrgStaffRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveGeneralOrgStaffRelation(CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> saveGeneralOrgStaffRelationList(CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
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
	@CachePut(value = "generalOrgStaffRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> updateGeneralOrgStaffRelation(CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
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

		// 判断是否是移动人员到其他组织架构
		if("1".equals(commonMsg.getBody().getSingleBody().getMoveStaffOrgFlag())){
			// 查询人员是否存在于新的组织架构下
			QueryWrapper<GeneralOrgStaffRelationEntity> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("tenant_id", commonMsg.getHead().getTenantId())
						.eq("belong_org_no", commonMsg.getBody().getSingleBody().getBelongOrgNo())
						.eq("belong_org_name", commonMsg.getBody().getSingleBody().getBelongOrgName())
						.eq("staff_id", commonMsg.getBody().getSingleBody().getStaffId())
						.eq("valid_flag", "1");
			GeneralOrgStaffRelationEntity data = generalOrgStaffRelationMapper.selectOne(queryWrapper);
			BizResponseEnum.DATA_HAS_EXIST.assertIsTrue(null == data || StringUtils.isBlank(data.getId()));
		}

		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralOrgStaffRelationEntity, NullEntity>();
		//定义singleBody
		GeneralOrgStaffRelationEntity singleBody = new GeneralOrgStaffRelationEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralOrgStaffRelationEntity, NullEntity> mutBean= new MutBean<GeneralOrgStaffRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralOrgStaffRelationServiceImpl.class).queryGeneralOrgStaffRelation(queryCommonMsg); 
		
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
	@CachePut(value = "generalOrgStaffRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveOrUpdateGeneralOrgStaffRelation(CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralOrgStaffRelationEntity, NullEntity>();
		//定义singleBody
		GeneralOrgStaffRelationEntity singleBody = new GeneralOrgStaffRelationEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralOrgStaffRelationEntity, NullEntity> mutBean= new MutBean<GeneralOrgStaffRelationEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralOrgStaffRelationServiceImpl.class).queryGeneralOrgStaffRelation(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> saveOrUpdateGeneralOrgStaffRelationList(CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		

		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			//删除
			if(commonMsg.getBody().getListBody().get(i).getValidFlag().equals("0")) {
				QueryWrapper<GeneralOrgStaffRelationEntity> queryWrapper = new QueryWrapper<GeneralOrgStaffRelationEntity>();
				queryWrapper.lambda().eq(GeneralOrgStaffRelationEntity::getTenantId, commonMsg.getHead().getTenantId());
				queryWrapper.lambda().eq(GeneralOrgStaffRelationEntity::getBelongOrgNo, commonMsg.getBody().getListBody().get(i).getBelongOrgNo());
				queryWrapper.lambda().eq(GeneralOrgStaffRelationEntity::getStaffId, commonMsg.getBody().getListBody().get(i).getStaffId());
				GeneralOrgStaffRelationEntity generalOrgStaffRelationEntity = generalOrgStaffRelationMapper.selectOne(queryWrapper);
				if(generalOrgStaffRelationEntity != null) {
					generalOrgStaffRelationMapper.delete(queryWrapper);
				}
			}
			// 新增关系记录
			else {
				commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
				super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
			}
		}		

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "generalOrgStaffRelation", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> deleteGeneralOrgStaffRelation(CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> deleteGeneralOrgStaffRelationList(CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
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
	public void exportGeneralOrgStaffRelationExcel(CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg, HttpServletResponse response) {
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
			
		QueryWrapper<GeneralOrgStaffRelationEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgStaffRelationEntity.class);
		// 查询当前机构下的所有机构号
		if(commonMsg.getBody().getSingleBody().getBelongOrgNo() != null && !commonMsg.getBody().getSingleBody().getBelongOrgNo().equals("")) {
			GeneralOrgInfoEntity generalOrgInfoEntity =  new GeneralOrgInfoEntity();
			generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId()); 
			generalOrgInfoEntity.setOrgNo(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			List<String> tmpLis = new ArrayList<String>();
			tmpLis.add(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			buildOrgTree.getAllChildrenOrgNo(generalOrgInfoEntity,tmpLis);
			queryWrapper.in("belong_org_no", tmpLis);
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
		Page<GeneralOrgStaffRelationEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralOrgStaffRelationEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统组织用户关系表";
		String fileName = "系统组织用户关系表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("所属机构号");
		headList.add("所属机构名称");
		headList.add("用户ID");
		headList.add("用户名");
		headList.add("真实姓名");
		excelData.add(headList);
		for (GeneralOrgStaffRelationEntity generalOrgStaffRelationEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalOrgStaffRelationEntity.getBelongOrgNo());	    		
			lists.add(generalOrgStaffRelationEntity.getBelongOrgName());	    		
			lists.add(generalOrgStaffRelationEntity.getStaffId());	    		
			lists.add(generalOrgStaffRelationEntity.getStaffName());	    		
			lists.add(generalOrgStaffRelationEntity.getRealName());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
