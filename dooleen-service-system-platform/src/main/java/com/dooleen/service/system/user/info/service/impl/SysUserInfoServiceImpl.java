package com.dooleen.service.system.user.info.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.app.general.staff.info.entity.GeneralStaffInfoEntity;
import com.dooleen.common.core.app.system.param.entity.SysParamEntity;
import com.dooleen.common.core.app.system.param.mapper.SysParamMapper;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import com.dooleen.common.core.app.system.user.role.mapper.SysUserRoleRelationMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.constant.SysOrgTypeConstant;
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
import com.dooleen.common.core.utils.aes.AESUtil;
import com.dooleen.service.system.inter.IGeneralHystrix;
import com.dooleen.service.system.org.info.entity.SysOrgInfoEntity;
import com.dooleen.service.system.org.info.mapper.SysOrgInfoMapper;
import com.dooleen.service.system.org.user.entity.SysOrgUserRelationEntity;
import com.dooleen.service.system.org.user.mapper.SysOrgUserRelationMapper;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.service.system.role.mapper.SysRoleMapper;
import com.dooleen.service.system.role.privilege.entity.SysRolePrivilegeRelationEntity;
import com.dooleen.service.system.role.privilege.mapper.SysRolePrivilegeRelationMapper;
import com.dooleen.service.system.role.privilege.service.SysRolePrivilegeRelationService;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.info.mapper.SysTenantInfoMapper;
import com.dooleen.service.system.tenant.info.service.impl.SysTenantInfoServiceImpl;
import com.dooleen.service.system.user.group.entity.SysUserGroupRelationEntity;
import com.dooleen.service.system.user.info.service.SysUserInfoService;
import com.dooleen.service.system.utils.BuildOrgTree;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 系统用户管理服务实现
 * @Author : apple
 * @Update: 2020-06-06 19:37:42
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfoEntity> implements SysUserInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	
	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
    
	@Autowired
	private SysParamMapper sysParamMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysOrgInfoMapper sysOrgInfoMapper;
	
	@Autowired
	private SysOrgUserRelationMapper sysOrgUserRelationMapper;
	
	@Autowired
	private SysRolePrivilegeRelationMapper sysRolePrivilegeRelationMapper;
	
	@Autowired
	private SysUserRoleRelationMapper sysUserRoleRelationMapper;
	
	@Autowired
	private SysTenantInfoMapper sysTenantInfoMapper;
	
	@Autowired
	private SysRolePrivilegeRelationService sysRolePrivilegeRelationService;
	
	@Autowired
	private IGeneralHystrix generalHystrix;
	
	@Autowired
	private BuildOrgTree buildOrgTree;

	private static String seqPrefix= TableEntityORMEnum.SYS_USER_INFO.getSeqPrefix();
	private static String tableName = TableEntityORMEnum.SYS_USER_INFO.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysUserInfoEntity, NullEntity> querySysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysUserInfoEntity sysUserInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysUserInfoEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysUserInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 根据用户名查询用户信息
		QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
		queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, commonMsg.getBody().getSingleBody().getTenantId());
		//如果openId不为空，使用openId登录
		if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getWxOpenId())){
			queryWrapper.lambda().eq(SysUserInfoEntity::getWxOpenId, commonMsg.getBody().getSingleBody().getWxOpenId());
		}
//		//如果app openId不为空，使用app openId登录
//		else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getAppWxOpenId())){
//			queryWrapper.lambda().eq(SysUserInfoEntity::getAppWxOpenId, commonMsg.getBody().getSingleBody().getAppWxOpenId());
//		}
		// 如果手机不为空，用手机号登录
		else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getMobileNo())){
			queryWrapper.lambda().eq(SysUserInfoEntity::getMobileNo, commonMsg.getBody().getSingleBody().getMobileNo());
		}
		// 否则用用户名登录
		else {
			queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, commonMsg.getBody().getSingleBody().getUserName());
		}
		SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);
		
		// 用户信息不存在
		BizResponseEnum.USER_INFO_NOT_EXIST.assertNotNull(sysUserInfoEntity,commonMsg);
		
		// 用户已注销
		BizResponseEnum.USER_HAS_LOG_OFF.assertEquals("1", sysUserInfoEntity.getValidFlag());
		
		//获取角色列表
		// 查询条件
		SysUserRoleRelationEntity sysUserRoleRelationEntity = new SysUserRoleRelationEntity();
		sysUserRoleRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysUserRoleRelationEntity.setUserId(sysUserInfoEntity.getId());
		// 1、获取用户所在的角色组
		List<SysRoleEntity> userRoleList = sysRoleMapper.querySysRoleByUserId(sysUserRoleRelationEntity);
		// 2、获取用户所在用户组的角色组
		SysUserGroupRelationEntity sysUserGroupRelationEntity = new SysUserGroupRelationEntity();
		sysUserGroupRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysUserGroupRelationEntity.setUserId(sysUserInfoEntity.getId());
		
		List<SysRoleEntity> userGroupRoleList = sysRoleMapper.querySysRoleByUserGroupId(sysUserGroupRelationEntity);
		userRoleList.addAll(userGroupRoleList);
		List<SysRoleEntity> roleIdList = new ArrayList<SysRoleEntity>();
		//去重
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i))) {
				roleIdList.add(userRoleList.get(i));
			}
		}
		commonMsg.getBody().setSingleBody(sysUserInfoEntity);
		commonMsg.getBody().setListBody(roleIdList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysUserInfoEntity, NullEntity> querySysUserInfoByMobile(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 根据用户名查询用户信息
		QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
		queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, commonMsg.getBody().getSingleBody().getTenantId());
		//如果openId不为空，使用openId登录
		if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getWxOpenId())){
			queryWrapper.lambda().eq(SysUserInfoEntity::getWxOpenId, commonMsg.getBody().getSingleBody().getWxOpenId());
		}
//		//如果app openId不为空，使用app openId登录
//		else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getAppWxOpenId())){
//			queryWrapper.lambda().eq(SysUserInfoEntity::getAppWxOpenId, commonMsg.getBody().getSingleBody().getAppWxOpenId());
//		}
		// 如果手机不为空，用手机号登录
		else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getMobileNo())){
			queryWrapper.lambda().eq(SysUserInfoEntity::getMobileNo, commonMsg.getBody().getSingleBody().getMobileNo());
		}
		// 否则用用户名登录
		else {
			queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, commonMsg.getBody().getSingleBody().getUserName());
		}
		SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);

		// 用户信息不存在
		BizResponseEnum.USER_INFO_NOT_EXIST.assertNotNull(sysUserInfoEntity);
		// 用户已注销
		BizResponseEnum.USER_HAS_LOG_OFF.assertEquals("1", sysUserInfoEntity.getValidFlag());

		commonMsg.getBody().setSingleBody(sysUserInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysUserInfoEntity> querySysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysUserInfoEntity> sysUserInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysUserInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListPage(
			CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg) {
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
				if (!entry.getKey().equals("belongOrgNo") && !(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0")  || entry.getValue().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0)))) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					if(entry.getKey().indexOf("RealName") >= 0 || entry.getKey().indexOf("realName") >= 0){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<SysUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysUserInfoEntity.class);
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
			SysOrgInfoEntity sysOrgInfoEntity =  new SysOrgInfoEntity();
			sysOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId()); 
			sysOrgInfoEntity.setOrgNo(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			List<String> tmpLis = new ArrayList<String>();
			tmpLis.add(commonMsg.getBody().getSingleBody().getBelongOrgNo());
			buildOrgTree.getAllChildrenOrgNo(sysOrgInfoEntity,tmpLis);
			queryWrapper.in("belong_org_no", tmpLis);
		}
		// 定义分页查询
		Page<SysUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysUserInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}
	
	@Override
	public CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListMap(
			CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg) {
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
		Collection<SysUserInfoEntity> sysUserInfoEntityList =  super.listByMap(conditionMap);
		List<SysUserInfoEntity> sysUserInfoMapList = new ArrayList<SysUserInfoEntity>(sysUserInfoEntityList);
		commonMsg.getBody().setListBody(sysUserInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	/**
	 * 创建项目管理员
	 */
	@Override
	public CommonMsg<SysUserInfoEntity, NullEntity> createProjectAdminUser(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		BizResponseEnum.INPUT_DATA_EMPTY.assertNotNull(commonMsg.getBody().getSingleBody().getTenantId(),commonMsg);
		
		SysUserInfoEntity sysUserInfoEntity = commonMsg.getBody().getSingleBody();
		if(StringUtil.isEmpty(sysUserInfoEntity.getTenantId())) {
			sysUserInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		}		
		
		/**
		 * 获取系统配置参数—— 获取菜单资源ID， projectAdminMenuIds =  rootMenuId，roleMenuId， buttonMenuId
		 */
		QueryWrapper<SysParamEntity> sysParamQueryWrapper = new QueryWrapper<SysParamEntity>();
		sysParamQueryWrapper.lambda().eq(SysParamEntity::getTenantId, sysUserInfoEntity.getTenantId());
		sysParamQueryWrapper.lambda().eq(SysParamEntity::getParamKey, "projectAdminMenuIds");
		SysParamEntity sysParamEntity = sysParamMapper.selectOne(sysParamQueryWrapper);	
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysParamEntity,commonMsg); 
		
		JSONObject sysParamJson = JSONObject.parseObject(sysParamEntity.getParamValue());
		
		/**
		 * 根据用户名查询用户信息
		 */
		QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
		queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, sysUserInfoEntity.getTenantId());
		queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, sysUserInfoEntity.getUserName());
		sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);
		// 用户不存在返回失败
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysUserInfoEntity,commonMsg); 
		
		/**
		 * 根据用户名查询角色信息
		 */
		QueryWrapper<SysRoleEntity> sysRoleQueryWrapper = new QueryWrapper<SysRoleEntity>();
		sysRoleQueryWrapper.lambda().eq(SysRoleEntity::getTenantId, sysUserInfoEntity.getTenantId());
		sysRoleQueryWrapper.lambda().eq(SysRoleEntity::getRoleName, commonMsg.getHead().getProjectNo()+"_ADMIN_ROLE");
		SysRoleEntity sysRole = sysRoleMapper.selectOne(sysRoleQueryWrapper);
		// 角色存在则返回错误
		BizResponseEnum.ADMIN_HAS_EXIST.assertIsNull(sysRole,commonMsg);
		
		/**
		 * 根据用户名查询人员信息
		 */
		GeneralStaffInfoEntity singleBody = new GeneralStaffInfoEntity();
		singleBody.setTenantId(commonMsg.getHead().getTenantId());
		singleBody.setStaffId(sysUserInfoEntity.getUserName());
		singleBody.setEducationEntity(null);
		singleBody.setValidFlag("1");
		GeneralStaffInfoEntity listBody = new GeneralStaffInfoEntity();
		CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> staffCommonMsg = CreateCommonMsg.getCommonMsg(singleBody, listBody);
		staffCommonMsg.setHead(commonMsg.getHead());
		staffCommonMsg.getHead().setTransCode("queryGeneralStaffInfoListPage");
		if(AESUtil.encode()) {
			String commonMsgStr = "";
			log.info("====AESUtil === {}", AESUtil.encode());
			try {
				commonMsgStr = AESUtil.encrypt(JSON.toJSONString(staffCommonMsg));
				commonMsgStr = generalHystrix.queryGeneralStaffInfoListPage(commonMsgStr);
				staffCommonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity>>(){});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			staffCommonMsg = generalHystrix.queryGeneralStaffInfoListPage(staffCommonMsg);
		}
		String staffId = staffCommonMsg.getBody().getListBody().get(0).getId();
		
		/**
		 * 创建默认项目组织
		 */
		GeneralOrgInfoEntity generalOrgInfoEntity = new GeneralOrgInfoEntity();
		generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfoEntity.setBelongProjectNo(commonMsg.getHead().getProjectId());
		generalOrgInfoEntity.setOrgNo(commonMsg.getHead().getProjectNo());
		generalOrgInfoEntity.setOrgType("9");
		generalOrgInfoEntity.setOrgName(commonMsg.getHead().getProjectName());
		generalOrgInfoEntity.setOrgFullName(commonMsg.getHead().getProjectName());
		generalOrgInfoEntity.setParentOrgNo(commonMsg.getHead().getProjectId());
		generalOrgInfoEntity.setBelongOrgGroup("");
		generalOrgInfoEntity.setOrderSeq(1);
		generalOrgInfoEntity.setHasChildren("");
		generalOrgInfoEntity.setRespsbUserId(sysUserInfoEntity.getUserName());
		generalOrgInfoEntity.setRespsbUserName(sysUserInfoEntity.getRealName());
		generalOrgInfoEntity.setValidFlag("1");
		generalOrgInfoEntity.setDataSign(DooleenMD5Util.md5(generalOrgInfoEntity.toString(), ConstantValue.md5Key));
		generalOrgInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalOrgInfoEntity, commonMsg.getHead(),TableEntityORMEnum.GENERAL_ORG_INFO.getEntityName(),TableEntityORMEnum.GENERAL_ORG_INFO.getSeqPrefix(), redisTemplate);
		
		NullEntity nullEntity = new NullEntity();
		CommonMsg<GeneralOrgInfoEntity, NullEntity> orgInfoCommonMsg = CreateCommonMsg.getCommonMsg(generalOrgInfoEntity, nullEntity);
		orgInfoCommonMsg.setHead(commonMsg.getHead());
		orgInfoCommonMsg.getHead().setTransCode("saveGeneralOrgInfo");
		
		if(AESUtil.encode()) {
			String commonMsgStr = "";
			log.info("====AESUtil === {}", AESUtil.encode());
			try {
				commonMsgStr = AESUtil.encrypt(JSON.toJSONString(orgInfoCommonMsg));
				commonMsgStr = generalHystrix.saveGeneralOrgInfo(commonMsgStr);
				orgInfoCommonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<GeneralOrgInfoEntity, NullEntity>>(){});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			orgInfoCommonMsg = generalHystrix.saveGeneralOrgInfo(orgInfoCommonMsg);
		}
		
		/**
		 * 创建默认项目组织-人员关系
		 */
		GeneralOrgStaffRelationEntity generalOrgStaffRelationEntity = new GeneralOrgStaffRelationEntity();
		generalOrgStaffRelationEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgStaffRelationEntity.setBelongOrgNo(generalOrgInfoEntity.getOrgNo());
		generalOrgStaffRelationEntity.setBelongOrgName(generalOrgInfoEntity.getOrgName());
		generalOrgStaffRelationEntity.setStaffId(staffId);
		generalOrgStaffRelationEntity.setStaffName(sysUserInfoEntity.getUserName());
		generalOrgStaffRelationEntity.setRealName(sysUserInfoEntity.getRealName());
		generalOrgStaffRelationEntity.setValidFlag("1");
		generalOrgStaffRelationEntity.setDataSign(DooleenMD5Util.md5(generalOrgStaffRelationEntity.toString(), ConstantValue.md5Key));
		generalOrgStaffRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalOrgStaffRelationEntity, commonMsg.getHead(),TableEntityORMEnum.GENERAL_ORG_STAFF_RELATION.getEntityName(),TableEntityORMEnum.GENERAL_ORG_STAFF_RELATION.getSeqPrefix(), redisTemplate);
		
		CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> orgStaffCommonMsg = CreateCommonMsg.getCommonMsg(generalOrgStaffRelationEntity, nullEntity);
		orgStaffCommonMsg.setHead(commonMsg.getHead());
		orgStaffCommonMsg.getHead().setTransCode("saveGeneralOrgStaffRelation");
		
		if(AESUtil.encode()) {
			String commonMsgStr = "";
			log.info("====AESUtil === {}", AESUtil.encode());
			try {
				commonMsgStr = AESUtil.encrypt(JSON.toJSONString(orgStaffCommonMsg));
				commonMsgStr = generalHystrix.saveGeneralOrgStaffRelation(commonMsgStr);
				orgStaffCommonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<GeneralOrgStaffRelationEntity, NullEntity>>(){});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			orgStaffCommonMsg = generalHystrix.saveGeneralOrgStaffRelation(orgStaffCommonMsg);
		}
		
		/**
		 * 创建默认角色
		 */
		SysRoleEntity sysRoleEntity = new SysRoleEntity();
		sysRoleEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysRoleEntity.setProjectId(commonMsg.getHead().getProjectId());
		sysRoleEntity.setRoleName(commonMsg.getHead().getProjectNo()+"_ADMIN_ROLE");
		sysRoleEntity.setRoleNickName(commonMsg.getHead().getProjectName()+"_管理员");
		sysRoleEntity.setRoleGroupName("管理员组");
		sysRoleEntity.setRoleType("1");
		sysRoleEntity.setRoleSeq(0);
		sysRoleEntity.setStatus("1");
		sysRoleEntity.setValidFlag("1");
		sysRoleEntity.setDataSign(DooleenMD5Util.md5(sysRoleEntity.toString(),  ConstantValue.md5Key));
		sysRoleEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRoleEntity, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE.getEntityName(),TableEntityORMEnum.SYS_ROLE.getSeqPrefix(), redisTemplate);
		sysRoleMapper.insert(sysRoleEntity);

		/**
		 * 创建权限资源 - root菜单资源
		 */
//		SysRolePrivilegeRelationEntity sysRolePrivilegeRelationRoot = new SysRolePrivilegeRelationEntity();
//		sysRolePrivilegeRelationRoot.setTenantId(sysUserInfoEntity.getTenantId());
//		sysRolePrivilegeRelationRoot.setRoleId(sysRoleEntity.getId());
//		sysRolePrivilegeRelationRoot.setResourceType("1");
//		sysRolePrivilegeRelationRoot.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8)+sysParamJson.getString("rootMenuId"));
//		sysRolePrivilegeRelationRoot.setValidFlag("1");
//		sysRolePrivilegeRelationRoot.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelationRoot.toString(),  ConstantValue.md5Key));
//		sysRolePrivilegeRelationRoot = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelationRoot, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName(), TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix(), redisTemplate);
//		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelationRoot);		
		
		/**
		 * 创建权限资源 - 菜单管理资源-角色管理
		 */
//		SysRolePrivilegeRelationEntity sysRolePrivilegeRelationEntity = new SysRolePrivilegeRelationEntity();
//		sysRolePrivilegeRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
//		sysRolePrivilegeRelationEntity.setRoleId(sysRoleEntity.getId());
//		sysRolePrivilegeRelationEntity.setResourceType("1");
//		sysRolePrivilegeRelationEntity.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8)+sysParamJson.getString("roleMenuId"));
//		sysRolePrivilegeRelationEntity.setValidFlag("1");
//		sysRolePrivilegeRelationEntity.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelationEntity.toString(),  ConstantValue.md5Key));
//		sysRolePrivilegeRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelationEntity, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName(), TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix(), redisTemplate);
//		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelationEntity);
		
		/**
		 * 创建权限资源 - 菜单管理->编辑按钮资源
		 */
//		SysRolePrivilegeRelationEntity sysRolePrivilegeRelation = new SysRolePrivilegeRelationEntity();
//		sysRolePrivilegeRelation.setTenantId(sysUserInfoEntity.getTenantId());
//		sysRolePrivilegeRelation.setRoleId(sysRoleEntity.getId());
//		sysRolePrivilegeRelation.setResourceType("2");
//		sysRolePrivilegeRelation.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8)+sysParamJson.getString("buttonMenuId"));
//		sysRolePrivilegeRelation.setValidFlag("1");
//		sysRolePrivilegeRelation.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelation.toString(),  ConstantValue.md5Key));
//		sysRolePrivilegeRelation = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelation, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName(), TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix(), redisTemplate);
//		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelation);
		
		/**
		 * 创建权限资源 - 菜单/按钮 资源
		 */
		List<SysRolePrivilegeRelationEntity> sysRolePrivilegeRelationEntityList = new ArrayList<SysRolePrivilegeRelationEntity>();
		//菜单
		List<String> menuIdList = JSON.parseArray(sysParamJson.getString("menuId"), String.class);
		for(String menuId: menuIdList) {
			menuId = sysUserInfoEntity.getTenantId().substring(0, 8) + menuId;
			SysRolePrivilegeRelationEntity sysRolePrivilegeRelation = new SysRolePrivilegeRelationEntity();
			sysRolePrivilegeRelation.setTenantId(sysUserInfoEntity.getTenantId());
			sysRolePrivilegeRelation.setRoleId(sysRoleEntity.getId());
			sysRolePrivilegeRelation.setResourceType("1");
			sysRolePrivilegeRelation.setResourceId(menuId);
			sysRolePrivilegeRelation.setValidFlag("1");
			sysRolePrivilegeRelation.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelation.toString(), ConstantValue.md5Key));
			sysRolePrivilegeRelation = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelation, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName(), TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix(), redisTemplate);
			sysRolePrivilegeRelationEntityList.add(sysRolePrivilegeRelation);
		}
		
		// 按钮
		List<String> buttonMenuIdList = JSON.parseArray(sysParamJson.getString("buttonMenuId"), String.class);
		for(String menuId: buttonMenuIdList) {
			menuId = sysUserInfoEntity.getTenantId().substring(0, 8) + menuId;
			SysRolePrivilegeRelationEntity sysRolePrivilegeRelation = new SysRolePrivilegeRelationEntity();
			sysRolePrivilegeRelation.setTenantId(sysUserInfoEntity.getTenantId());
			sysRolePrivilegeRelation.setRoleId(sysRoleEntity.getId());
			sysRolePrivilegeRelation.setResourceType("2");
			sysRolePrivilegeRelation.setResourceId(menuId);
			sysRolePrivilegeRelation.setValidFlag("1");
			sysRolePrivilegeRelation.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelation.toString(), ConstantValue.md5Key));
			sysRolePrivilegeRelation = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelation, commonMsg.getHead(),TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName(), TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix(), redisTemplate);
			sysRolePrivilegeRelationEntityList.add(sysRolePrivilegeRelation);
		}
		
		sysRolePrivilegeRelationService.saveBatch(sysRolePrivilegeRelationEntityList);
		
		/**
		 * 创建用户角色关系
		 */
		SysUserRoleRelationEntity  sysUserRoleRelationEntity =new SysUserRoleRelationEntity();
		sysUserRoleRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysUserRoleRelationEntity.setUserId(sysUserInfoEntity.getId());
		sysUserRoleRelationEntity.setRoleId(sysRoleEntity.getId());
		sysUserRoleRelationEntity.setValidFlag("1");
		sysUserRoleRelationEntity.setDataSign(DooleenMD5Util.md5(sysUserRoleRelationEntity.toString(),  ConstantValue.md5Key));
		sysUserRoleRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysUserRoleRelationEntity, commonMsg.getHead(),TableEntityORMEnum.SYS_USER_ROLE_RELATION.getEntityName(), TableEntityORMEnum.SYS_USER_ROLE_RELATION.getSeqPrefix(), redisTemplate);
		sysUserRoleRelationMapper.insert(sysUserRoleRelationEntity);		

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	/**
	 * 创建租户管理员
	 */
	@Override
	public CommonMsg<SysUserInfoEntity, NullEntity> createAdminUser(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		BizResponseEnum.INPUT_DATA_EMPTY.assertNotEmpty(commonMsg.getBody().getSingleBody().getTenantId());
		
		// 创建admin用户
		SysUserInfoEntity sysUserInfoEntity = commonMsg.getBody().getSingleBody();
		sysUserInfoEntity.setUserName("admin"+sysUserInfoEntity.getTenantId().substring(4, 8));
		String pwdMd5 =  DooleenMD5Util.md5("Admin"+sysUserInfoEntity.getTenantId().substring(4, 8), ConstantValue.md5Key);
		sysUserInfoEntity.setPassword(pwdMd5);
		sysUserInfoEntity.setRealName(sysUserInfoEntity.getCompanyName());
		sysUserInfoEntity.setBelongOrgNo("1001");
		sysUserInfoEntity.setBelongOrgName(sysUserInfoEntity.getCompanyName());
		sysUserInfoEntity.setStatus("1");
		sysUserInfoEntity.setValidFlag("1");
		sysUserInfoEntity.setDataSign(DooleenMD5Util.md5(sysUserInfoEntity.toString(),  ConstantValue.md5Key));
		commonMsg.getHead().setTenantId(sysUserInfoEntity.getTenantId());
		
		// 获取系统配置参数—— 获取菜单资源ID， projectAdminMenuIds =  rootMenuId，roleMenuId， buttonMenuId
		QueryWrapper<SysParamEntity> sysParamQueryWrapper = new QueryWrapper<SysParamEntity>();
		sysParamQueryWrapper.lambda().eq(SysParamEntity::getTenantId, sysUserInfoEntity.getTenantId());
		sysParamQueryWrapper.lambda().eq(SysParamEntity::getParamKey, "tenantAdminMenuIds");
		SysParamEntity sysParamEntity = sysParamMapper.selectOne(sysParamQueryWrapper);	
		
		BizResponseEnum.SYS_PARAM_NOT_CONFIG.assertNotNull(sysParamEntity,commonMsg);
		
		JSONObject sysParamJson = JSONObject.parseObject(sysParamEntity.getParamValue());	
		
		// 根据用户名查询用户信息
		QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
		queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, sysUserInfoEntity.getTenantId());
		queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, sysUserInfoEntity.getUserName());
		SysUserInfoEntity sysUserInfo = sysUserInfoMapper.selectOne(queryWrapper);
		//用户存在返回失败
		BizResponseEnum.USER_HAS_EXIST.assertIsNull(sysUserInfo);
		
		// 执行保存
		sysUserInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysUserInfoEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
		boolean result =  super.save(sysUserInfoEntity);
		// 保存失败
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		/**
		 * 创建系统机构信息
		 */
		String entityNameTmp = TableEntityORMEnum.SYS_ORG_INFO.getEntityName();
		String seqPrefixTmp = TableEntityORMEnum.SYS_ORG_INFO.getSeqPrefix();
		
		SysOrgInfoEntity sysOrgInfoEntity = new SysOrgInfoEntity();
		sysOrgInfoEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysOrgInfoEntity.setOrgNo(sysUserInfoEntity.getBelongOrgNo());
		sysOrgInfoEntity.setOrgType(SysOrgTypeConstant.COMPANY);
		sysOrgInfoEntity.setOrgName(sysUserInfoEntity.getCompanyName());
		sysOrgInfoEntity.setOrgFullName(sysUserInfoEntity.getCompanyName());
		sysOrgInfoEntity.setParentOrgNo("DOOL");
		sysOrgInfoEntity.setBelongOrgGroup("");
		sysOrgInfoEntity.setOrderSeq(1);
		sysOrgInfoEntity.setRemark("");
		sysOrgInfoEntity.setHasChildren("false");
		sysOrgInfoEntity.setRespsbUserId("");
		sysOrgInfoEntity.setRespsbUserName("");
		sysOrgInfoEntity.setValidFlag("1");
		sysOrgInfoEntity.setDataSign(DooleenMD5Util.md5(sysOrgInfoEntity.toString(),  ConstantValue.md5Key));
		sysOrgInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysOrgInfoEntity, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysOrgInfoMapper.insert(sysOrgInfoEntity);
		
		/**
		 * 创建系统机构-用户关系
		 */
		entityNameTmp = TableEntityORMEnum.SYS_ORG_USER_RELATION.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_ORG_USER_RELATION.getSeqPrefix();
		
		SysOrgUserRelationEntity sysOrgUserRelationEntity = new SysOrgUserRelationEntity();
		sysOrgUserRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysOrgUserRelationEntity.setBelongOrgNo(sysOrgInfoEntity.getId());
		sysOrgUserRelationEntity.setBelongOrgName(sysOrgInfoEntity.getOrgName());
		sysOrgUserRelationEntity.setUserId(sysUserInfoEntity.getId());
		sysOrgUserRelationEntity.setUserName(sysUserInfoEntity.getUserName());
		sysOrgUserRelationEntity.setRealName(sysUserInfoEntity.getRealName());
		sysOrgUserRelationEntity.setValidFlag("1");
		sysOrgUserRelationEntity.setDataSign(DooleenMD5Util.md5(sysOrgUserRelationEntity.toString(),  ConstantValue.md5Key));
		sysOrgUserRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysOrgUserRelationEntity, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysOrgUserRelationMapper.insert(sysOrgUserRelationEntity);
		
		
		/**
		 * 创建角色
		 */
		entityNameTmp = TableEntityORMEnum.SYS_ROLE.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_ROLE.getSeqPrefix();
		
		SysRoleEntity sysRoleEntity = new SysRoleEntity();
		sysRoleEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysRoleEntity.setRoleName("ADMIN_ROLE");
		sysRoleEntity.setRoleType("1");
		sysRoleEntity.setRoleNickName("管理员");
		sysRoleEntity.setRoleGroupName("管理员组");
		sysRoleEntity.setRoleSeq(0);
		sysRoleEntity.setStatus("1");
		sysRoleEntity.setValidFlag("1");
		sysRoleEntity.setDataSign(DooleenMD5Util.md5(sysRoleEntity.toString(),  ConstantValue.md5Key));
		sysRoleEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRoleEntity, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysRoleMapper.insert(sysRoleEntity);

		/**
		 * 创建权限资源 - root 资源
		 */
		entityNameTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix();
		
		SysRolePrivilegeRelationEntity sysRolePrivilegeRelationRoot = new SysRolePrivilegeRelationEntity();
		sysRolePrivilegeRelationRoot.setTenantId(sysUserInfoEntity.getTenantId());
		sysRolePrivilegeRelationRoot.setRoleId(sysRoleEntity.getId());
		sysRolePrivilegeRelationRoot.setResourceType("1");
		sysRolePrivilegeRelationRoot.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8) + sysParamJson.getString("rootMenuId"));//"00000002"
		sysRolePrivilegeRelationRoot.setValidFlag("1");
		sysRolePrivilegeRelationRoot.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelationRoot.toString(),  ConstantValue.md5Key));
		sysRolePrivilegeRelationRoot = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelationRoot, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelationRoot);		
		
		/**
		 * 创建权限资源 - 菜单管理资源-角色管理
		 */
		entityNameTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix();
		
		SysRolePrivilegeRelationEntity sysRolePrivilegeRelationEntity = new SysRolePrivilegeRelationEntity();
		sysRolePrivilegeRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysRolePrivilegeRelationEntity.setRoleId(sysRoleEntity.getId());
		sysRolePrivilegeRelationEntity.setResourceType("1");
		sysRolePrivilegeRelationEntity.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8) + sysParamJson.getString("roleMenuId"));//"00000032"
		sysRolePrivilegeRelationEntity.setValidFlag("1");
		sysRolePrivilegeRelationEntity.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelationEntity.toString(),  ConstantValue.md5Key));
		sysRolePrivilegeRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelationEntity, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelationEntity);
		
		/**
		 * 创建权限资源 - 菜单管理->编辑按钮资源
		 */
		entityNameTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_ROLE_PRIVILEGE_RELATION.getSeqPrefix();
		
		SysRolePrivilegeRelationEntity sysRolePrivilegeRelation = new SysRolePrivilegeRelationEntity();
		sysRolePrivilegeRelation.setTenantId(sysUserInfoEntity.getTenantId());
		sysRolePrivilegeRelation.setRoleId(sysRoleEntity.getId());
		sysRolePrivilegeRelation.setResourceType("2");
		sysRolePrivilegeRelation.setResourceId(sysUserInfoEntity.getTenantId().substring(0, 8) + sysParamJson.getString("buttonMenuId"));//"00000180"
		sysRolePrivilegeRelation.setValidFlag("1");
		sysRolePrivilegeRelation.setDataSign(DooleenMD5Util.md5(sysRolePrivilegeRelation.toString(),  ConstantValue.md5Key));
		sysRolePrivilegeRelation = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysRolePrivilegeRelation, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysRolePrivilegeRelationMapper.insert(sysRolePrivilegeRelation);
		
		
		/**
		 * 创建用户角色关系
		 */
		entityNameTmp = TableEntityORMEnum.SYS_USER_ROLE_RELATION.getEntityName();
		seqPrefixTmp = TableEntityORMEnum.SYS_USER_ROLE_RELATION.getSeqPrefix();
		
		SysUserRoleRelationEntity  sysUserRoleRelationEntity =new SysUserRoleRelationEntity();
		sysUserRoleRelationEntity.setTenantId(sysUserInfoEntity.getTenantId());
		sysUserRoleRelationEntity.setUserId(sysUserInfoEntity.getId());
		sysUserRoleRelationEntity.setRoleId(sysRoleEntity.getId());
		sysUserRoleRelationEntity.setValidFlag("1");
		sysUserRoleRelationEntity.setDataSign(DooleenMD5Util.md5(sysUserRoleRelationEntity.toString(),  ConstantValue.md5Key));
		sysUserRoleRelationEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysUserRoleRelationEntity, commonMsg.getHead(), entityNameTmp, seqPrefixTmp, redisTemplate);
		sysUserRoleRelationMapper.insert(sysUserRoleRelationEntity);
		
		/**
		 * 将用户名和密码更新至租户号上
		 */
		QueryWrapper<SysTenantInfoEntity> recordQueryWrapper = new QueryWrapper<SysTenantInfoEntity>();
		recordQueryWrapper.lambda().eq(SysTenantInfoEntity::getTenantId, sysUserInfoEntity.getTenantId());
		SysTenantInfoEntity sysTenantInfoEntity = sysTenantInfoMapper.selectOne(recordQueryWrapper);
		sysTenantInfoEntity.setClientId(sysUserInfoEntity.getUserName());
		sysTenantInfoEntity.setDefaultPassword("Admin"+sysUserInfoEntity.getTenantId().substring(4, 8));
		
		//由于缓存，使用AOP进行更新
		log.debug("====开始AOP调用更新方法 ====== ");
		CommonMsg<SysTenantInfoEntity, NullEntity> updateCommonMsg = new CommonMsg<SysTenantInfoEntity, NullEntity>();
		//定义body
		MutBean<SysTenantInfoEntity, NullEntity> mutBean= new MutBean<SysTenantInfoEntity, NullEntity>();
		mutBean.setSingleBody(sysTenantInfoEntity);
		//深拷贝数据进行AOP查询
		updateCommonMsg.setHead(commonMsg.getHead());
		updateCommonMsg.setBody(mutBean); 
		updateCommonMsg = SpringUtil.getBean(SysTenantInfoServiceImpl.class).updateSysTenantInfo(updateCommonMsg); 		
		if(!updateCommonMsg.getHead().getRespCode().equals("S0000")) {
			commonMsg.setHead(updateCommonMsg.getHead());
			return commonMsg;
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 重置用户密码
	 */
	@Override
	public CommonMsg<SysUserInfoEntity, NullEntity> restAdminPassword(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		BizResponseEnum.INPUT_DATA_EMPTY.assertNotEmpty(commonMsg.getBody().getSingleBody().getTenantId());
				
		SysUserInfoEntity sysUserInfoEntity = commonMsg.getBody().getSingleBody();
		String password = sysUserInfoEntity.getPassword();
		//如果id为空表示重置租户管理员密码
		if(StringUtil.isEmpty(sysUserInfoEntity.getId())) {
			//拼接admin用户账号
			sysUserInfoEntity.setUserName("admin"+sysUserInfoEntity.getTenantId().substring(4, 8));
			commonMsg.getHead().setTenantId(sysUserInfoEntity.getTenantId());
			// 根据用户名查询用户信息
			QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
			queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId, sysUserInfoEntity.getTenantId());
			queryWrapper.lambda().eq(SysUserInfoEntity::getUserName, sysUserInfoEntity.getUserName());
			sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);
		}
		else
		{
			sysUserInfoEntity = super.getById(sysUserInfoEntity.getId());
		}
		//用户不存在返回失败
		BizResponseEnum.USER_INFO_NOT_EXIST.assertNotNull(sysUserInfoEntity,commonMsg);
		
		if(StringUtil.isEmpty(password)) {
			String pwdMd5 =  DooleenMD5Util.md5("Admin"+sysUserInfoEntity.getTenantId().substring(4, 8), ConstantValue.md5Key);
			sysUserInfoEntity.setPassword(pwdMd5);
			sysUserInfoEntity.setPasswordStatus("0");
		}
		else
		{
			sysUserInfoEntity.setPassword(password);
			sysUserInfoEntity.setPasswordStatus("0");
		}
		int result = sysUserInfoMapper.updateById(sysUserInfoEntity);
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result > 0);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	
	@Override
	@CachePut(value = "sysUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserInfoEntity, NullEntity> saveSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysUserInfoEntity> saveSysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
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
	@CachePut(value = "sysUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserInfoEntity, NullEntity> updateSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysUserInfoEntity, NullEntity>();
		//定义singleBody
		SysUserInfoEntity singleBody = new SysUserInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysUserInfoEntity, NullEntity> mutBean= new MutBean<SysUserInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysUserInfoServiceImpl.class).querySysUserInfo(queryCommonMsg); 
		
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
	@CachePut(value = "sysUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserInfoEntity, NullEntity> saveOrUpdateSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysUserInfoEntity, NullEntity>();
		//定义singleBody
		SysUserInfoEntity singleBody = new SysUserInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysUserInfoEntity, NullEntity> mutBean= new MutBean<SysUserInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysUserInfoServiceImpl.class).querySysUserInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, SysUserInfoEntity> saveOrUpdateSysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
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
    @CacheEvict(value = "sysUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysUserInfoEntity, NullEntity> deleteSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysUserInfoEntity> deleteSysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
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
	public void exportSysUserInfoExcel(CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserInfoEntity.class);
		
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
		Page<SysUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysUserInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统用户表";
		String fileName = "系统用户表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("用户名");
		headList.add("真实姓名");
		headList.add("昵称");
		headList.add("所属机构号");
		headList.add("所属机构名称");
		headList.add("所属部门ID");
		headList.add("所属部门名称");
		headList.add("公司名称");
		headList.add("职位名称");
		headList.add("性别");
		headList.add("年龄");
		headList.add("电话号码");
		headList.add("手机号码");
		headList.add("邮箱");
		headList.add("头像链接");
		headList.add("状态");
		excelData.add(headList);
		for (SysUserInfoEntity sysUserInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysUserInfoEntity.getId());	    		
			lists.add(sysUserInfoEntity.getTenantId());	  
			lists.add(sysUserInfoEntity.getUserName());	    		
			lists.add(sysUserInfoEntity.getRealName());	    		
			lists.add(sysUserInfoEntity.getNickName());	    		
			lists.add(sysUserInfoEntity.getBelongOrgNo());	    		
			lists.add(sysUserInfoEntity.getBelongOrgName());	    		
			lists.add(sysUserInfoEntity.getBelongDeptId());	    		
			lists.add(sysUserInfoEntity.getBelongDeptName());	    		
			lists.add(sysUserInfoEntity.getCompanyName());	    		
			lists.add(sysUserInfoEntity.getPostName());	    		
			lists.add(sysUserInfoEntity.getSex());	    		
			lists.add(String.valueOf(sysUserInfoEntity.getAge()));	    		
			lists.add(sysUserInfoEntity.getPhoneNo());	    		
			lists.add(sysUserInfoEntity.getMobileNo());	    		
			lists.add(sysUserInfoEntity.getEmail());	    		
			lists.add(sysUserInfoEntity.getHeadImgUrl());	    		
			lists.add(sysUserInfoEntity.getStatus());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
