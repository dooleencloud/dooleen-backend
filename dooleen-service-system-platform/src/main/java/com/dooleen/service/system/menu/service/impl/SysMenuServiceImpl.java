package com.dooleen.service.system.menu.service.impl;

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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
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
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.system.menu.entity.SysMenuEntity;
import com.dooleen.service.system.menu.mapper.SysMenuMapper;
import com.dooleen.service.system.menu.service.SysMenuService;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.service.system.role.mapper.SysRoleMapper;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.menu.entity.SysTenantMenuRelationEntity;
import com.dooleen.service.system.tenant.privilege.entity.SysTenantPrivilegeRelationEntity;
import com.dooleen.service.system.user.group.entity.SysUserGroupRelationEntity;
import com.dooleen.service.system.utils.BuildMenuTree;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-08 16:40:01
 * @Description : 系统菜单管理服务实现
 * @Author : apple
 * @Update: 2020-06-08 16:40:01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    public static final String SUPER_TENANT_ID = "DOOL1001";

	@Autowired
	private  BuildMenuTree buildMenuTree;
	
	@Autowired
	private  SysMenuMapper sysMenuMapper;
	
	@Autowired
	private  SysRoleMapper sysRoleMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_MENU.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_MENU.getEntityName();
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysMenu", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysMenuEntity, NullEntity> querySysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysMenuEntity sysMenuEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
  		commonMsg.getBody().setListBody(nullEntityList);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysMenuEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysMenuEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysMenuEntity> querySysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysMenuEntity> sysMenuEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysMenuEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	//@Cacheable(value = "sysMenu", key = "#commonMsg.head.tenantId+#commonMsg.body.singleBody.parentMenuId")
	public CommonMsg<SysMenuEntity,JSONObject> querySysMenuTree(CommonMsg<SysMenuEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 查询条件
		String resourceType = commonMsg.getBody().getSingleBody().getMenuLevel();
		SysMenuEntity sysMenu = new SysMenuEntity();
		sysMenu.setMenuCategory(commonMsg.getBody().getSingleBody().getMenuCategory());
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysMenu.setTenantId(commonMsg.getHead().getTenantId());
		}
		else
		{
			sysMenu.setTenantId(commonMsg.getBody().getSingleBody().getTenantId());
		}
		sysMenu.setId(commonMsg.getBody().getSingleBody().getParentMenuId());
		
		// 查询出所有的一级机构 根目目录菜单默认是 租户号+00000000 如：DOOL100100000000
		List<SysMenuEntity>  menuList= new ArrayList<SysMenuEntity>();
//		boolean isSuper = false;
		//System.out.println("======is super= "+SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId()));
		if(SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId())) {
			menuList= sysMenuMapper.queryByParentMenuIdForSuper(sysMenu,sysMenu.getMenuCategory());
//			isSuper = true;
		}
		else if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getProjectId())) { 
			SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity = new SysTenantPrivilegeRelationEntity();
			sysTenantPrivilegeRelationEntity.setId(sysMenu.getId());
			sysTenantPrivilegeRelationEntity.setTenantId(sysMenu.getTenantId());
			sysTenantPrivilegeRelationEntity.setResourceType(resourceType);
			menuList= sysMenuMapper.queryByParentMenuIdAndProject(sysTenantPrivilegeRelationEntity,sysMenu.getMenuCategory(),commonMsg.getBody().getSingleBody().getProjectId());
			
			menuList = excludeTenantPrivilege(menuList, commonMsg.getHead().getTenantId());
		}
		else {
			SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity = new SysTenantPrivilegeRelationEntity();
			sysTenantPrivilegeRelationEntity.setId(sysMenu.getId());
			sysTenantPrivilegeRelationEntity.setTenantId(sysMenu.getTenantId());
			sysTenantPrivilegeRelationEntity.setResourceType(resourceType);
			menuList= sysMenuMapper.queryByParentMenuId(sysTenantPrivilegeRelationEntity,sysMenu.getMenuCategory());
		}

		List<JSONObject> list = buildMenuTree.getChildrenByUserId(sysMenu.getTenantId()+"00000000",menuList);

		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(9999);
		commonMsg.getBody().setTotal(9999);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 获取用户菜单
	 */
	@Override
	public CommonMsg<SysUserRoleRelationEntity,JSONObject> querySysMenuTreeByUserId(CommonMsg<SysUserRoleRelationEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 查询条件
		SysUserRoleRelationEntity sysUserRoleRelationEntity = commonMsg.getBody().getSingleBody();
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysUserRoleRelationEntity.setTenantId(commonMsg.getHead().getTenantId());
		}
		else
		{
			sysUserRoleRelationEntity.setTenantId(commonMsg.getBody().getSingleBody().getTenantId());
		}
		// 获取用户所在的角色组
		List<SysRoleEntity> userRoleList = sysRoleMapper.querySysRoleByUserId(sysUserRoleRelationEntity);
		// 获取用户所在用户组的角色组
		SysUserGroupRelationEntity sysUserGroupRelationEntity = new SysUserGroupRelationEntity();
		sysUserGroupRelationEntity.setTenantId(sysUserRoleRelationEntity.getTenantId());
		sysUserGroupRelationEntity.setUserId(sysUserRoleRelationEntity.getUserId());
		List<SysRoleEntity> userGroupRoleList = sysRoleMapper.querySysRoleByUserGroupId(sysUserGroupRelationEntity);
		//合并用户角色和用户组角色
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = new ArrayList<String>();
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i).getId())) {
				roleIdList.add(userRoleList.get(i).getId());
			}
		}
		// 如果角色为空，给一个空值，否则会抛出异常
		if(roleIdList.size() == 0) {
			roleIdList.add("");
		}

		//获取菜单列表 menuCategory = 1
		List<SysMenuEntity>  menuList= sysMenuMapper.querySysMenuListByRoleList(roleIdList,sysUserRoleRelationEntity.getTenantId(),"1",sysUserRoleRelationEntity.getLevel());
		
		menuList = excludeTenantPrivilege(menuList, commonMsg.getHead().getTenantId());
		
		//		JSONObject treeObject = new JSONObject();
		List<JSONObject> list = buildMenuTree.getChildrenByUserId(sysUserRoleRelationEntity.getTenantId()+"00000000",menuList);
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(9999);
		commonMsg.getBody().setTotal(9999);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}	
	
	/**
	 * 获取用户项目相关菜单
	 */
	@Override
	public CommonMsg<SysUserRoleRelationEntity,JSONObject> querySysMenuTreeByUserIdAndProject(CommonMsg<SysUserRoleRelationEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 查询条件
		SysUserRoleRelationEntity sysUserRoleRelationEntity = commonMsg.getBody().getSingleBody();
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysUserRoleRelationEntity.setTenantId(commonMsg.getHead().getTenantId());
		}
		else {
			sysUserRoleRelationEntity.setTenantId(commonMsg.getBody().getSingleBody().getTenantId());
		}
		// 获取用户所在的角色组
		List<SysRoleEntity> userRoleList = sysRoleMapper.querySysRoleByUserId(sysUserRoleRelationEntity);
		// 获取用户所在用户组的角色组
		SysUserGroupRelationEntity sysUserGroupRelationEntity = new SysUserGroupRelationEntity();
		sysUserGroupRelationEntity.setTenantId(sysUserRoleRelationEntity.getTenantId());
		sysUserGroupRelationEntity.setUserId(sysUserRoleRelationEntity.getUserId());
		List<SysRoleEntity> userGroupRoleList = sysRoleMapper.querySysRoleByUserGroupId(sysUserGroupRelationEntity);
		//合并用户角色和用户组角色
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = new ArrayList<String>();
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i).getId())) {
				roleIdList.add(userRoleList.get(i).getId());
			}
		}
		// 如果角色为空，给一个空值，否则会抛出异常
		if(roleIdList.size() == 0) {
			roleIdList.add("");
		}

		//获取菜单列表 menuCategory = 1
		List<SysMenuEntity>  menuList= sysMenuMapper.querySysMenuListByRoleListAndProject(roleIdList,sysUserRoleRelationEntity.getTenantId(),sysUserRoleRelationEntity.getProjectId(),"1",sysUserRoleRelationEntity.getLevel());

		menuList = excludeTenantPrivilege(menuList, commonMsg.getHead().getTenantId());
		
		//		JSONObject treeObject = new JSONObject();
		List<JSONObject> list = buildMenuTree.getChildrenByUserId(sysUserRoleRelationEntity.getTenantId()+"00000000",menuList);
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(9999);
		commonMsg.getBody().setTotal(9999);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}	
	
	/**
	 * 获取用户按钮权限
	 */
	@Override
	public CommonMsg<SysUserRoleRelationEntity,JSONObject> querySysButtonByUserId(CommonMsg<SysUserRoleRelationEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 查询条件
		SysUserRoleRelationEntity sysUserRoleRelationEntity = commonMsg.getBody().getSingleBody();
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysUserRoleRelationEntity.setTenantId(commonMsg.getHead().getTenantId());
		}
		else
		{
			sysUserRoleRelationEntity.setTenantId(commonMsg.getBody().getSingleBody().getTenantId());
		}
		// 获取用户所在的角色组
		List<SysRoleEntity> userRoleList = sysRoleMapper.querySysRoleByUserId(sysUserRoleRelationEntity);
		// 获取用户所在用户组的角色组
		SysUserGroupRelationEntity sysUserGroupRelationEntity = new SysUserGroupRelationEntity();
		sysUserGroupRelationEntity.setTenantId(sysUserRoleRelationEntity.getTenantId());
		sysUserGroupRelationEntity.setUserId(sysUserRoleRelationEntity.getUserId());
		List<SysRoleEntity> userGroupRoleList = sysRoleMapper.querySysRoleByUserGroupId(sysUserGroupRelationEntity);
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = new ArrayList<String>();
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i).getId())) {
				roleIdList.add(userRoleList.get(i).getId());
			}
		}
		// 如果角色为空，给一个空值，否则会抛出异常 
		if(roleIdList.size() == 0) {
			roleIdList.add("");
		}
		// 获取按钮列表 menuCategory = 2 
		List<String>  menuList= sysMenuMapper.querySysButtonListByRoleList(roleIdList,sysUserRoleRelationEntity.getTenantId(),"2",sysUserRoleRelationEntity.getLevel());
		// System.out.println("===menuList  ===" + menuList.toString());		
		JSONObject treeObject = new JSONObject();
		// 格式化按钮数据 
		for(int i=0; i<menuList.size(); i++) {
			treeObject.put(menuList.get(i).trim(), true);
		}
		List<JSONObject> list = new ArrayList<JSONObject>();
		list.add(treeObject);
		
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(9999);
		commonMsg.getBody().setTotal(9999);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}	
	@Override
	public CommonMsg<SysTenantMenuRelationEntity, SysMenuEntity> querySysMenuListByTenantId(CommonMsg<SysTenantMenuRelationEntity, SysMenuEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 赋值租户号
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			commonMsg.getBody().getSingleBody().setTenantId(commonMsg.getHead().getTenantId());
		}
		// 获取所有列表
		List<SysMenuEntity> sysMenuEntityList = sysMenuMapper.querySysMenuListByTenantId(commonMsg.getBody().getSingleBody());
		commonMsg.getBody().setListBody(sysMenuEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<SysMenuEntity, SysMenuEntity> querySysMenuListPage(CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg) {
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
		QueryWrapper<SysMenuEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMenuEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMenuEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysMenuEntity.class);
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
		Page<SysMenuEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysMenuEntity> list =  super.page(pages, queryWrapper);
		// 查找是否有子机构
		for(int i = 0; i < list.getRecords().size(); i++) {
			int count=sysMenuMapper.queryCountByCondition(list.getRecords().get(i));
			if(count > 0) {
				list.getRecords().get(i).setHasChildren("true");
			}
			else {
				list.getRecords().get(i).setHasChildren("false");
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
	public CommonMsg<SysMenuEntity, SysMenuEntity> querySysMenuListMap(
			CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg) {
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
		Collection<SysMenuEntity> sysMenuEntityList =  super.listByMap(conditionMap);
		List<SysMenuEntity> sysMenuMapList = new ArrayList<SysMenuEntity>(sysMenuEntityList);
		commonMsg.getBody().setListBody(sysMenuMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysMenu", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMenuEntity, NullEntity> saveSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysMenuEntity> saveSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg) {
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
	public CommonMsg<NullEntity,SysTenantInfoEntity> synchronizeSysMenu(CommonMsg<NullEntity,SysTenantInfoEntity> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		} 
		// 获取所有的菜单信息
		QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<SysMenuEntity>();
		queryWrapper.lambda().eq(SysMenuEntity::getTenantId, commonMsg.getHead().getTenantId());
		List<SysMenuEntity> sysMenuEntityList = sysMenuMapper.selectList(queryWrapper);
		
		// 循环处理每个租户
		boolean result =  false;
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			List<SysMenuEntity> newSysMenuEntityList = new ArrayList<SysMenuEntity>();
			//删除
			if(!commonMsg.getBody().getListBody().get(i).getUserTenantId().equals("DOOL1001")) {
				QueryWrapper<SysMenuEntity> queryWrapperTmp = new QueryWrapper<SysMenuEntity>();
				queryWrapperTmp.lambda().eq(SysMenuEntity::getTenantId, commonMsg.getBody().getListBody().get(i).getUserTenantId());
				//删除机构下所有的菜单
				log.info("======开始删除"+commonMsg.getBody().getListBody().get(i).getTenantName()+"机构下的菜单=====");
				sysMenuMapper.delete(queryWrapperTmp);
				//重新组装菜单结构
				for(int j=0;j < sysMenuEntityList.size(); j++) {
					SysMenuEntity newSysMenuEntity = new SysMenuEntity();
					newSysMenuEntity = sysMenuEntityList.get(j);

					newSysMenuEntity.setId(commonMsg.getBody().getListBody().get(i).getUserTenantId()+ newSysMenuEntity.getId().substring(8,newSysMenuEntity.getId().length()));
					newSysMenuEntity.setTenantId(commonMsg.getBody().getListBody().get(i).getUserTenantId());
					newSysMenuEntity.setParentMenuId(commonMsg.getBody().getListBody().get(i).getUserTenantId()+ newSysMenuEntity.getParentMenuId().substring(8,newSysMenuEntity.getParentMenuId().length()));
					newSysMenuEntityList.add(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(newSysMenuEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					//System.out.println("=======newSysMenuEntity=========="+ newSysMenuEntityList.toString());
				}
			}
			log.info("===开始保存==");
			// 批量保存
			result = super.saveOrUpdateBatch(newSysMenuEntityList);
		}	 
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	
	@Override
	@CachePut(value = "sysMenu", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMenuEntity, NullEntity> updateSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysMenuEntity, NullEntity> queryCommonMsg = new CommonMsg<SysMenuEntity, NullEntity>();
		//定义singleBody
		SysMenuEntity singleBody = new SysMenuEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysMenuEntity, NullEntity> mutBean= new MutBean<SysMenuEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysMenuServiceImpl.class).querySysMenu(queryCommonMsg); 
		
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
	@CachePut(value = "sysMenu", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMenuEntity, NullEntity> saveOrUpdateSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysMenuEntity, NullEntity> queryCommonMsg = new CommonMsg<SysMenuEntity, NullEntity>();
		//定义singleBody
		SysMenuEntity singleBody = new SysMenuEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysMenuEntity, NullEntity> mutBean= new MutBean<SysMenuEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysMenuServiceImpl.class).querySysMenu(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, SysMenuEntity> saveOrUpdateSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg) {
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
    @CacheEvict(value = "sysMenu", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysMenuEntity, NullEntity> deleteSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//递归删除下级所有项
		buildMenuTree.deleteMenuByParentId(commonMsg.getHead().getTenantId(), commonMsg.getBody().getSingleBody().getId());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;	
	}

	@Override
	public CommonMsg<NullEntity, SysMenuEntity> deleteSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		
		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
			//递归删除下级所有项
			buildMenuTree.deleteMenuByParentId(commonMsg.getHead().getTenantId(), commonMsg.getBody().getListBody().get(i).getId());
		}
		// 执行删除
		boolean result =  super.removeByIds(keylist);
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysMenuExcel(CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysMenuEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMenuEntity.class);
		
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
		Page<SysMenuEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysMenuEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统菜单表";
		String fileName = "系统菜单表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("上级菜单ID");
		headList.add("菜单分类");
		headList.add("菜单名称");
		headList.add("菜单昵称");
		headList.add("菜单编号");
		headList.add("路由地址");
		headList.add("菜单图标");
		headList.add("菜单序号");
		headList.add("是否有子节点");
		headList.add("上级菜单名称");
		headList.add("保持活动标志");
		excelData.add(headList);
		for (SysMenuEntity sysMenuEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysMenuEntity.getId());	    		
			lists.add(sysMenuEntity.getTenantId());	    		
			lists.add(sysMenuEntity.getParentMenuId());	    		
			lists.add(sysMenuEntity.getMenuCategory());	    		
			lists.add(sysMenuEntity.getMenuName());	    		
			lists.add(sysMenuEntity.getMenuNickName());	    		
			lists.add(sysMenuEntity.getMenuNo());	    		
			lists.add(sysMenuEntity.getRouteAddress());	    		
			lists.add(sysMenuEntity.getMenuIcon());	    		
			lists.add(String.valueOf(sysMenuEntity.getMenuSeq()));	    		
			lists.add(sysMenuEntity.getHasChildren());	    		
			lists.add(sysMenuEntity.getParentMenuName());	    		
			lists.add(sysMenuEntity.getKeepAliveFlag());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
	
	public void clearCache(SysMenuEntity sysMenuEntity) {
		redisTemplate.delete("sysMenu::"+sysMenuEntity.getTenantId()+sysMenuEntity.getParentMenuId());
	}
	
	/**
	 * 排除租户权限中没有的菜单
	 * @param sysMenuList
	 * @return List<SysMenuEntity>
	 */
	public List<SysMenuEntity> excludeTenantPrivilege(List<SysMenuEntity> sysMenuList, String tenantId){
		// 查询所有租户权限中的资源ID
		List<String> resourceIdList = sysMenuMapper.selectAllTenantResourceId(tenantId);
		
		List<SysMenuEntity> result = sysMenuList.parallelStream().filter(menu -> resourceIdList.contains(menu.getId())).collect(Collectors.toList());
		
		return result;
	}
}
