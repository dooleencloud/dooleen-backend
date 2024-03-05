package com.dooleen.service.general.staff.info.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.staff.info.entity.EducationEntity;
import com.dooleen.common.core.app.general.staff.info.entity.GeneralStaffInfoEntity;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.staff.info.mapper.GeneralStaffInfoMapper;
import com.dooleen.service.general.staff.info.service.GeneralStaffInfoService;
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

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-18 18:12:48
 * @Description : 人员管理服务实现
 * @Author : apple
 * @Update: 2020-06-18 18:12:48
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralStaffInfoServiceImpl extends ServiceImpl<GeneralStaffInfoMapper, GeneralStaffInfoEntity> implements GeneralStaffInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
	@Autowired
	private  SysUserInfoMapper sysUserInfoMapper;
	
	@Autowired
	private  GeneralStaffInfoMapper generalStaffInfoMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_STAFF_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_STAFF_INFO.getEntityName();
	
	@Autowired
	private  BuildOrgTree buildOrgTree;
	
	@Autowired	
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalStaffInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> queryGeneralStaffInfo(CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralStaffInfoEntity generalStaffInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalStaffInfoEntity,commonMsg);
		
		List<EducationEntity> educationEntityList = JSON.parseArray(generalStaffInfoEntity.getEduInfoList(),EducationEntity.class);
		generalStaffInfoEntity.setEducationEntity(educationEntityList);
		generalStaffInfoEntity.setEduInfoList("");
		commonMsg.getBody().setSingleBody(generalStaffInfoEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoList(CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralStaffInfoEntity> generalStaffInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalStaffInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListPage(
			CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg) {
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
			if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
				if (!entry.getKey().equals("educationEntity") && !entry.getKey().equals("belongOrgNo") && !entry.getKey().equals("parentOrgNo") && !(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
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
		
		QueryWrapper<GeneralStaffInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralStaffInfoEntity.class);
		
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
		Page<GeneralStaffInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralStaffInfoEntity> list =  super.page(pages, queryWrapper);
		
		//查询用户信息，判断是否注册用户
		for(int i=0; i < list.getRecords().size(); i++) {
			// 根据用户名查询用户信息
			QueryWrapper<SysUserInfoEntity> userInfoQueryWrapper = new QueryWrapper<SysUserInfoEntity>();
			userInfoQueryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, list.getRecords().get(i).getTenantId());
			userInfoQueryWrapper.lambda().eq(SysUserInfoEntity::getUserName, list.getRecords().get(i).getStaffId());
			SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(userInfoQueryWrapper);
			if(sysUserInfoEntity != null) {
				list.getRecords().get(i).setUserId(sysUserInfoEntity.getId());
				list.getRecords().get(i).setStatus(sysUserInfoEntity.getValidFlag());
			}
		}
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListMap(
			CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg) {
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
		Collection<GeneralStaffInfoEntity> generalStaffInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralStaffInfoEntity> generalStaffInfoMapList = new ArrayList<GeneralStaffInfoEntity>(generalStaffInfoEntityList);
		commonMsg.getBody().setListBody(generalStaffInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalStaffInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> saveGeneralStaffInfo(CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}

		// 判断员工ID是否已存在（同一租户下员工ID不能重复）
		String tenantId = commonMsg.getBody().getSingleBody().getTenantId();
		if(StringUtils.isBlank(tenantId)){
			tenantId = commonMsg.getHead().getTenantId();
			BizResponseEnum.TENANT_ID_EMPTY.assertIsTrue(StringUtils.isNotBlank(tenantId),commonMsg);
		}
		boolean staffIdIsExist = checkStaffIdIsExist(tenantId, commonMsg.getBody().getSingleBody().getStaffId(), commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.STAFF_ID_HAS_EXIST.assertIsFalse(staffIdIsExist,commonMsg);

		commonMsg.getBody().getSingleBody().setEduInfoList(JSONObject.toJSONString(commonMsg.getBody().getSingleBody().getEducationEntity()));
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> saveGeneralStaffInfoList(CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
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
	@CachePut(value = "generalStaffInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> updateGeneralStaffInfo(CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralStaffInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralStaffInfoEntity, NullEntity>();
		//定义singleBody
		GeneralStaffInfoEntity singleBody = new GeneralStaffInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralStaffInfoEntity, NullEntity> mutBean= new MutBean<GeneralStaffInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralStaffInfoServiceImpl.class).queryGeneralStaffInfo(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}

		// 判断员工ID是否已存在（同一租户下员工ID不能重复）
		String tenantId = commonMsg.getBody().getSingleBody().getTenantId();
		if(StringUtils.isBlank(tenantId)){
			tenantId = commonMsg.getHead().getTenantId();
			BizResponseEnum.TENANT_ID_EMPTY.assertIsTrue(StringUtils.isNotBlank(tenantId),commonMsg);
		}
		boolean staffIdIsExist = checkStaffIdIsExist(tenantId, commonMsg.getBody().getSingleBody().getStaffId(), commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.STAFF_ID_HAS_EXIST.assertIsFalse(staffIdIsExist,commonMsg);

		//设置需要修改的签名
		commonMsg.getBody().getSingleBody().setEduInfoList(JSONObject.toJSONString(commonMsg.getBody().getSingleBody().getEducationEntity()));
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
	@CachePut(value = "generalStaffInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> saveOrUpdateGeneralStaffInfo(CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralStaffInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralStaffInfoEntity, NullEntity>();
		//定义singleBody
		GeneralStaffInfoEntity singleBody = new GeneralStaffInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralStaffInfoEntity, NullEntity> mutBean= new MutBean<GeneralStaffInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralStaffInfoServiceImpl.class).queryGeneralStaffInfo(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}

		// 判断员工ID是否已存在（同一租户下员工ID不能重复）
		String tenantId = commonMsg.getBody().getSingleBody().getTenantId();
		if(StringUtils.isBlank(tenantId)){
			tenantId = commonMsg.getHead().getTenantId();
			BizResponseEnum.TENANT_ID_EMPTY.assertIsTrue(StringUtils.isNotBlank(tenantId),commonMsg);
		}
		boolean staffIdIsExist = checkStaffIdIsExist(tenantId, commonMsg.getBody().getSingleBody().getStaffId(), commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.STAFF_ID_HAS_EXIST.assertIsFalse(staffIdIsExist,commonMsg);

		//设置需要修改的签名
		commonMsg.getBody().getSingleBody().setEduInfoList(JSONObject.toJSONString(commonMsg.getBody().getSingleBody().getEducationEntity()));
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
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> saveOrUpdateGeneralStaffInfoList(CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalStaffInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> deleteGeneralStaffInfo(CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> deleteGeneralStaffInfoList(CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
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
	public void exportGeneralStaffInfoExcel(CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<GeneralStaffInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralStaffInfoEntity.class);
		
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
		Page<GeneralStaffInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralStaffInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "人员信息表";
		String fileName = "人员信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("员工ID");
		headList.add("员工类型");
		headList.add("员工姓名");
		headList.add("头像链接");
		headList.add("性别");
		headList.add("出生日期");
		headList.add("身份证号码");
		headList.add("民族");
		headList.add("籍贯");
		headList.add("出生地址");
		headList.add("政治面貌");
		headList.add("婚姻状况");
		headList.add("健康状况");
		headList.add("毕业院校名称");
		headList.add("学历名称");
		headList.add("手机号码");
		headList.add("邮箱地址");
		headList.add("座机号码");
		headList.add("现居地址");
		headList.add("语言信息");
		headList.add("教育信息列表");
		headList.add("专业技能列表");
		headList.add("资质信息列表");
		headList.add("获奖信息列表");
		headList.add("社会关系列表");
		headList.add("职务级别");
		headList.add("职务名称");
		headList.add("岗位名称");
		headList.add("职称名称");
		headList.add("所属机构号");
		headList.add("所属机构名称");
		headList.add("上级机构号");
		headList.add("上级机构名称");
		headList.add("员工状态");
		headList.add("入职日期");
		headList.add("入职经办人");
		headList.add("离职日期");
		headList.add("离职经办人");
		headList.add("离职原因");
		headList.add("合同到期日期");
		headList.add("合同链接");
		headList.add("账号列表");
		headList.add("办公地址");
		headList.add("通用标签");
		headList.add("公司名称");
		headList.add("参加工作日期");
		headList.add("金融工作年限");
		headList.add("核心项目个数");
		headList.add("工作经历列表");
		headList.add("项目经历列表");
		headList.add("行内服务标志");
		headList.add("行内项目列表");
		headList.add("服务类型");
		headList.add("驻场类型");
		headList.add("所属项目名称");
		headList.add("级别名称");
		headList.add("档次名称");
		headList.add("入场日期");
		headList.add("入场经办人");
		headList.add("离场日期");
		headList.add("离场经办人");
		headList.add("离场原因");
		excelData.add(headList);
		for (GeneralStaffInfoEntity generalStaffInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalStaffInfoEntity.getId());	    		
			lists.add(generalStaffInfoEntity.getTenantId());	    		
			lists.add(generalStaffInfoEntity.getStaffId());	    		
			lists.add(generalStaffInfoEntity.getStaffType());	    		
			lists.add(generalStaffInfoEntity.getStaffName());	    		
			lists.add(generalStaffInfoEntity.getHeadImgUrl());	    		
			lists.add(generalStaffInfoEntity.getSex());	    		
			lists.add(generalStaffInfoEntity.getBirthDate());	    		
			lists.add(generalStaffInfoEntity.getIdCardNo());	    		
			lists.add(generalStaffInfoEntity.getNation());	    		
			lists.add(generalStaffInfoEntity.getNativePlace());	    		
			lists.add(generalStaffInfoEntity.getBirthAddress());	    		
			lists.add(generalStaffInfoEntity.getPoliticalPoliticalStatus());	    		
			lists.add(generalStaffInfoEntity.getMarriageCond());	    		
			lists.add(generalStaffInfoEntity.getHealthyCond());	    		
			lists.add(generalStaffInfoEntity.getGraduationCollegeName());	    		
			lists.add(generalStaffInfoEntity.getEduDegreeName());	    		
			lists.add(generalStaffInfoEntity.getMobileNo());	    		
			lists.add(generalStaffInfoEntity.getEmailAddress());	    		
			lists.add(generalStaffInfoEntity.getTelNo());	    		
			lists.add(generalStaffInfoEntity.getLiveAddress());	    		
			lists.add(generalStaffInfoEntity.getLanInfo());	    		
			lists.add(generalStaffInfoEntity.getEduInfoList());	    		
			lists.add(generalStaffInfoEntity.getMajorSkillList());	    		
			lists.add(generalStaffInfoEntity.getQualInfoList());	    		
			lists.add(generalStaffInfoEntity.getAwardInfoList());	    		
			lists.add(generalStaffInfoEntity.getSocietyRelationList());	    		
			lists.add(generalStaffInfoEntity.getDutyGrade());	    		
			lists.add(generalStaffInfoEntity.getDutyName());	    		
			lists.add(generalStaffInfoEntity.getStationName());	    		
			lists.add(generalStaffInfoEntity.getTechTitleName());	    		
			lists.add(generalStaffInfoEntity.getBelongOrgNo());	    		
			lists.add(generalStaffInfoEntity.getBelongOrgName());	    		
			lists.add(generalStaffInfoEntity.getParentOrgNo());	    		
			lists.add(generalStaffInfoEntity.getParentOrgName());	    		
			lists.add(generalStaffInfoEntity.getStaffStatus());	    		
			lists.add(generalStaffInfoEntity.getEntryDate());	    		
			lists.add(generalStaffInfoEntity.getEntryOptName());	    		
			lists.add(generalStaffInfoEntity.getLeaveDate());	    		
			lists.add(generalStaffInfoEntity.getLeaveOptName());	    		
			lists.add(generalStaffInfoEntity.getLeaveReason());	    		
			lists.add(generalStaffInfoEntity.getAgreementExpireDate());	    		
			lists.add(generalStaffInfoEntity.getAgreementUrl());	    		
			lists.add(generalStaffInfoEntity.getAcctList());	    		
			lists.add(generalStaffInfoEntity.getOfficeAddress());	    		
			lists.add(generalStaffInfoEntity.getGeneralLabel());	    		
			lists.add(generalStaffInfoEntity.getCompanyName());	    		
			lists.add(generalStaffInfoEntity.getJoinJobDate());	    		
			lists.add(generalStaffInfoEntity.getFinanceJobYears());	    		
			lists.add(String.valueOf(generalStaffInfoEntity.getCoreProjectCount()));	    		
			lists.add(generalStaffInfoEntity.getJobExpeList());	    		
			lists.add(generalStaffInfoEntity.getProjectExpeList());	    		
			lists.add(generalStaffInfoEntity.getOwnBankServiceFlag());	    		
			lists.add(generalStaffInfoEntity.getOwnBankProjectList());	    		
			lists.add(generalStaffInfoEntity.getServiceType());	    		
			lists.add(generalStaffInfoEntity.getSettleType());	    		
			lists.add(generalStaffInfoEntity.getBelongProjectName());	    		
			lists.add(generalStaffInfoEntity.getLevelName());	    		
			lists.add(generalStaffInfoEntity.getGradeName());	    		
			lists.add(generalStaffInfoEntity.getEntranceDate());	    		
			lists.add(generalStaffInfoEntity.getEntranceOptName());	    		
			lists.add(generalStaffInfoEntity.getDepartureDate());	    		
			lists.add(generalStaffInfoEntity.getDepartureOptName());	    		
			lists.add(generalStaffInfoEntity.getDepartureReason());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}

	private Boolean checkStaffIdIsExist(String tenantId, String staffId, String id){
		QueryWrapper<GeneralStaffInfoEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tenant_id", tenantId)
				.eq("staff_id", staffId)
				.eq("valid_flag", "1");
		GeneralStaffInfoEntity generalStaffInfoEntity = super.getOne(queryWrapper);
		if(null != generalStaffInfoEntity) {
			if (!id.equals(generalStaffInfoEntity.getId()) && staffId.equals(generalStaffInfoEntity.getStaffId())) {
				return true;
			} else {
				return false;
			}
		}
		else{
			return false;
		}
	}
}
