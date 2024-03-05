package com.dooleen.service.system.api.scope.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dooleen.service.system.api.scope.entity.SysApiScopeEntity;
import com.dooleen.service.system.api.scope.mapper.SysApiScopeMapper;
import com.dooleen.service.system.api.scope.service.SysApiScopeService;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.service.system.role.mapper.SysRoleMapper;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.user.group.entity.SysUserGroupRelationEntity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-10 16:26:18
 * @Description : 系统接口管理服务实现
 * @Author : apple
 * @Update: 2020-06-10 16:26:18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysApiScopeServiceImpl extends ServiceImpl<SysApiScopeMapper, SysApiScopeEntity> implements SysApiScopeService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    public static final String SUPER_TENANT_ID = "DOOL1001";

	@Autowired
	private  SysApiScopeMapper sysApiScopeMapper;
	@Autowired
	private  SysRoleMapper sysRoleMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_API_SCOPE.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_API_SCOPE.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
    //@Cacheable(value = "sysApiScope", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysApiScopeEntity sysApiScopeEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysApiScopeEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysApiScopeEntity);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 查询用户是否具备接口权限
	 * @param: userId 放到sysApiScopeEntity.id中
	 * @param: interfaceAddress
	 * 
	 */
	@Override
	public CommonMsg<SysApiScopeEntity,NullEntity> querySysApiScopeByUserIdAndAddress(CommonMsg<SysApiScopeEntity,NullEntity> commonMsg){
		CommonUtil.service(commonMsg);
		
		Map<String, String> map = new HashMap<String, String>(2);
		// 查询条件
		SysApiScopeEntity sysApiScopeEntity = commonMsg.getBody().getSingleBody();
		SysUserRoleRelationEntity sysUserRoleRelationEntity = new SysUserRoleRelationEntity();
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysUserRoleRelationEntity.setTenantId(commonMsg.getHead().getTenantId());
		}
		else
		{
			sysUserRoleRelationEntity.setTenantId(sysApiScopeEntity.getTenantId());
		}
		//sysApiScopeEntity.id 须传入用户id
		sysUserRoleRelationEntity.setUserId(sysApiScopeEntity.getId());
		
		// 获取用户所在的角色组
		List<SysRoleEntity> userRoleList = sysRoleMapper.querySysRoleByUserId(sysUserRoleRelationEntity);
		// 获取用户所在用户组的角色组
		SysUserGroupRelationEntity sysUserGroupRelationEntity = new SysUserGroupRelationEntity();
		sysUserGroupRelationEntity.setTenantId(sysUserRoleRelationEntity.getTenantId());
		sysUserGroupRelationEntity.setUserId(sysUserRoleRelationEntity.getUserId());
		List<SysRoleEntity> userGroupRoleList = sysRoleMapper.querySysRoleByUserGroupId(sysUserGroupRelationEntity);
		userRoleList.addAll(userGroupRoleList);
		List<String> roleIdList = new ArrayList();
		for(int i=0; i<userRoleList.size(); i++) {
			if(!roleIdList.contains(userRoleList.get(i).getId())) {
				roleIdList.add(userRoleList.get(i).getId());
			}
		}
		// 如果角色为空，给一个空值，否则会抛出异常 
		if(roleIdList.size() == 0) {
			roleIdList.add("");
		}
		log.debug("=========roleIdList  ======"+ roleIdList.toString());
		// 获取按钮列表 menuCategory = 2 
		int count= sysApiScopeMapper.querySysApiScopeByUserIdAndAddress(roleIdList,sysUserRoleRelationEntity.getTenantId(),sysApiScopeEntity.getInterfaceAddress());
		
		BizResponseEnum.DATA_NOT_FOUND.assertIsTrue(count > 0);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}	
	@Override
	public CommonMsg<NullEntity, SysApiScopeEntity> querySysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysApiScopeEntity> sysApiScopeEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysApiScopeEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}
	@Override
	public CommonMsg<SysApiScopeEntity,JSONObject> querySysApiScopeTree(CommonMsg<SysApiScopeEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		Map<String, String> map = new HashMap<String, String>(2);
		// 查询条件
		SysApiScopeEntity sysApiScope = commonMsg.getBody().getSingleBody();
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sysApiScope.setTenantId(commonMsg.getHead().getTenantId());
		}
		// 查询出所有的一级机构 根目目录菜单默认是 租户号+00000000 如：DOOL100100000000
		List<SysApiScopeEntity>  menuList= new ArrayList<SysApiScopeEntity>();
		boolean isSuper = false;
		if(SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId())) {
			QueryWrapper<SysApiScopeEntity> queryWrapper = new QueryWrapper<SysApiScopeEntity>();
			queryWrapper.lambda().eq(SysApiScopeEntity::getTenantId, sysApiScope.getTenantId());
			queryWrapper.lambda().eq(SysApiScopeEntity::getValidFlag, '1');
			queryWrapper.orderByAsc("interface_category");
			menuList= sysApiScopeMapper.selectList(queryWrapper);
			isSuper = true;
		}
		else {
			menuList= sysApiScopeMapper.queryByApiScopeId(sysApiScope);
		}
		log.info("==menuList== {}", menuList);
		// 循环分类
		List<JSONObject> list = new ArrayList<>();
		List<JSONObject> childrenList = new ArrayList<>();
		String oldInterfaceCategory = "";
		String  interfaceId = "";
		String  interfaceName = "";
		JSONObject treeObject = new JSONObject();
		for (SysApiScopeEntity sysApiScopeEntity : menuList) {
			JSONObject metaObject = new JSONObject();
			log.info("==sysApiScopeEntity== {}", sysApiScopeEntity.toString());
			if(oldInterfaceCategory.equals("") || !oldInterfaceCategory.equals(sysApiScopeEntity.getInterfaceCategory().trim())) {
				if(oldInterfaceCategory.equals("")) {
					oldInterfaceCategory = sysApiScopeEntity.getInterfaceCategory().trim();
					interfaceId = sysApiScopeEntity.getId();
					interfaceName = sysApiScopeEntity.getInterfaceName();
					log.info("===111");
				}
				else {
					treeObject.put("id",interfaceId);
					treeObject.put("interfaceCategory",oldInterfaceCategory);
					treeObject.put("interfaceName",oldInterfaceCategory);
					treeObject.put("children",childrenList);
					list.add(treeObject);
					childrenList = new ArrayList<>();
					treeObject = new JSONObject();
					oldInterfaceCategory = sysApiScopeEntity.getInterfaceCategory().trim();
					interfaceId = sysApiScopeEntity.getId();
					interfaceName = sysApiScopeEntity.getInterfaceName();
					log.info("===222");
				}
			}
			metaObject.put("id",sysApiScopeEntity.getId());
			metaObject.put("interfaceCategory",oldInterfaceCategory);
			metaObject.put("interfaceName",sysApiScopeEntity.getInterfaceName());
			childrenList.add(metaObject);
		}
		if(!oldInterfaceCategory.equals("")) {
			treeObject.put("id",interfaceId);
			treeObject.put("interfaceCategory",oldInterfaceCategory);
			treeObject.put("interfaceName",oldInterfaceCategory);
			treeObject.put("children",childrenList);
			list.add(treeObject);
			log.info("===333");
		}
		log.info("===444 {}", list.toString());
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(9999);
		commonMsg.getBody().setTotal(9999);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListPage(
			CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
		QueryWrapper<SysApiScopeEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysApiScopeEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysApiScopeEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysApiScopeEntity.class);
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
		Page<SysApiScopeEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysApiScopeEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListMap(
			CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
		Collection<SysApiScopeEntity> sysApiScopeEntityList =  super.listByMap(conditionMap);
		List<SysApiScopeEntity> sysApiScopeMapList = new ArrayList<SysApiScopeEntity>(sysApiScopeEntityList);
		commonMsg.getBody().setListBody(sysApiScopeMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysApiScope", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysApiScopeEntity, NullEntity> saveSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	public CommonMsg<NullEntity, SysApiScopeEntity> saveSysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	@CachePut(value = "sysApiScope", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysApiScopeEntity, NullEntity> updateSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysApiScopeEntity, NullEntity> queryCommonMsg = new CommonMsg<SysApiScopeEntity, NullEntity>();
		//定义singleBody
		SysApiScopeEntity singleBody = new SysApiScopeEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysApiScopeEntity, NullEntity> mutBean= new MutBean<SysApiScopeEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysApiScopeServiceImpl.class).querySysApiScope(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		Map<String, String> map = new HashMap<String, String>(2);
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
	@CachePut(value = "sysApiScope", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysApiScopeEntity, NullEntity> saveOrUpdateSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysApiScopeEntity, NullEntity> queryCommonMsg = new CommonMsg<SysApiScopeEntity, NullEntity>();
		//定义singleBody
		SysApiScopeEntity singleBody = new SysApiScopeEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysApiScopeEntity, NullEntity> mutBean= new MutBean<SysApiScopeEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysApiScopeServiceImpl.class).querySysApiScope(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		Map<String, String> map = new HashMap<String, String>(2);
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
	public CommonMsg<NullEntity, SysApiScopeEntity> saveOrUpdateSysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		
		Map<String, String> map = new HashMap<String, String>(2);
		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity,SysTenantInfoEntity> synchronizeSysApiScope(CommonMsg<NullEntity,SysTenantInfoEntity> commonMsg){
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		} 
		// 获取所有的接口信息
		QueryWrapper<SysApiScopeEntity> queryWrapper = new QueryWrapper<SysApiScopeEntity>();
		queryWrapper.lambda().eq(SysApiScopeEntity::getTenantId, commonMsg.getHead().getTenantId());
		List<SysApiScopeEntity> sysApiScopeEntity = sysApiScopeMapper.selectList(queryWrapper);
		
		// 循环处理每个租户
		boolean result =  false;
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			List<SysApiScopeEntity> newSysApiScopeEntityList = new ArrayList();
			//删除
			if(!commonMsg.getBody().getListBody().get(i).getTenantId().equals("DOOL1001")) {
				QueryWrapper<SysApiScopeEntity> queryWrapperTmp = new QueryWrapper<SysApiScopeEntity>();
				queryWrapperTmp.lambda().eq(SysApiScopeEntity::getTenantId, commonMsg.getBody().getListBody().get(i).getTenantId());
				//删除机构下所有的接口
				log.info("======开始删除"+commonMsg.getBody().getListBody().get(i).getTenantId()+"机构下的菜单=====");
				sysApiScopeMapper.delete(queryWrapperTmp);
				
				//重新组装接口结构
				for(int j=0;j < sysApiScopeEntity.size(); j++) {
					SysApiScopeEntity newSysApiScopeEntity = new SysApiScopeEntity();
					newSysApiScopeEntity = sysApiScopeEntity.get(j); 
					log.info("=======newSysApiScopeEntity2========== {}", newSysApiScopeEntity.toString());
					newSysApiScopeEntity.setId(commonMsg.getBody().getListBody().get(i).getTenantId()+ newSysApiScopeEntity.getId().substring(8,newSysApiScopeEntity.getId().length()));
					newSysApiScopeEntity.setTenantId(commonMsg.getBody().getListBody().get(i).getTenantId());
					newSysApiScopeEntityList.add(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(newSysApiScopeEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
				}
			}
			// 批量保存
			result = super.saveOrUpdateBatch(newSysApiScopeEntityList);
		}	 
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "sysApiScope", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysApiScopeEntity, NullEntity> deleteSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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
	public CommonMsg<NullEntity, SysApiScopeEntity> deleteSysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
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
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysApiScopeExcel(CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
//		Map<String, String> map = new HashMap<String, String>(2);
		
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

		QueryWrapper<SysApiScopeEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysApiScopeEntity.class);
		
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
		Page<SysApiScopeEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysApiScopeEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统接口表";
		String fileName = "系统接口表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("菜单ID");
		headList.add("菜单名称");
		headList.add("接口分类");
		headList.add("接口名称");
		headList.add("接口地址");
		headList.add("接口编码");
		headList.add("备注");
		excelData.add(headList);
		for (SysApiScopeEntity sysApiScopeEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysApiScopeEntity.getId());	    		
			lists.add(sysApiScopeEntity.getTenantId());	    		
			lists.add(sysApiScopeEntity.getMenuId());	    		
			lists.add(sysApiScopeEntity.getMenuName());	    		
			lists.add(sysApiScopeEntity.getInterfaceCategory());	    		
			lists.add(sysApiScopeEntity.getInterfaceName());	    		
			lists.add(sysApiScopeEntity.getInterfaceAddress());	    		
			lists.add(sysApiScopeEntity.getInterfaceCode());	    		
			lists.add(sysApiScopeEntity.getRemark());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
