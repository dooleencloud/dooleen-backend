package com.dooleen.service.system.org.info.service.impl;

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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
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
import com.dooleen.service.system.org.info.entity.SysOrgInfoEntity;
import com.dooleen.service.system.org.info.mapper.SysOrgInfoMapper;
import com.dooleen.service.system.org.info.service.SysOrgInfoService;
import com.dooleen.service.system.utils.BuildOrgTree;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 00:05:24
 * @Description : 组织机构管理服务实现
 * @Author : apple
 * @Update: 2020-06-04 00:05:24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOrgInfoServiceImpl extends ServiceImpl<SysOrgInfoMapper, SysOrgInfoEntity> implements SysOrgInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	@Autowired
	private  BuildOrgTree buildOrgTree;
	
	@Autowired
	private  SysOrgInfoMapper sysOrgInfoMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_ORG_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_ORG_INFO.getEntityName();
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Override
	@Cacheable(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysOrgInfoEntity, NullEntity> querySysOrgInfo(CommonMsg<SysOrgInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysOrgInfoEntity sysOrgInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysOrgInfoEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(sysOrgInfoEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysOrgInfoEntity> querySysOrgInfoList(CommonMsg<NullEntity, SysOrgInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysOrgInfoEntity> sysOrgInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysOrgInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	//@Cacheable(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+#commonMsg.body.singleBody.orgNo")
	public CommonMsg<SysOrgInfoEntity,JSONObject> querySysOrgTree(CommonMsg<SysOrgInfoEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);
		
		// 查询条件
		SysOrgInfoEntity sysOrgInfo = new SysOrgInfoEntity();
		sysOrgInfo.setTenantId(commonMsg.getHead().getTenantId());
		sysOrgInfo.setOrgNo(commonMsg.getBody().getSingleBody().getOrgNo());
		// 查询出所有的一级机构
		List<SysOrgInfoEntity>  orgList= sysOrgInfoMapper.queryByParentOrgNo(sysOrgInfo);
		
		// 处理机构树
		List<JSONObject> list = new ArrayList<>();
		for (SysOrgInfoEntity sysOrgInfoEntity : orgList) {
			if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getOrgType())) {
				if(sysOrgInfoEntity.getOrgType().equals(commonMsg.getBody().getSingleBody().getOrgType())) {
					JSONObject treeObject = new JSONObject();
					treeObject.put("id", sysOrgInfoEntity.getId());
					treeObject.put("orgNo", sysOrgInfoEntity.getOrgNo());
					treeObject.put("orgType", sysOrgInfoEntity.getOrgType());
					treeObject.put("orgName", sysOrgInfoEntity.getOrgName());
					treeObject.put("parentOrgNo", sysOrgInfoEntity.getParentOrgNo());
					treeObject.put("children", buildOrgTree.getChildren(sysOrgInfoEntity));
					list.add(treeObject);
				}
			}
			else {
				JSONObject treeObject = new JSONObject();
				treeObject.put("id", sysOrgInfoEntity.getId());
				treeObject.put("orgNo", sysOrgInfoEntity.getOrgNo());
				treeObject.put("orgType", sysOrgInfoEntity.getOrgType());
				treeObject.put("orgName", sysOrgInfoEntity.getOrgName());
				treeObject.put("parentOrgNo", sysOrgInfoEntity.getParentOrgNo());
				treeObject.put("children", buildOrgTree.getChildren(sysOrgInfoEntity));
				list.add(treeObject);
			}
		}
		
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(99999);
		commonMsg.getBody().setTotal(99999);
		if (orgList.size() == 0) {
			BizResponseEnum.DATA_NOT_FOUND.assertFail();
		} 
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	
	@Override
	public CommonMsg<SysOrgInfoEntity, SysOrgInfoEntity> querySysOrgInfoListPage(
			CommonMsg<SysOrgInfoEntity, SysOrgInfoEntity> commonMsg) {
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
		System.out.print("===singleBodyMap === "+ singleBodyMap.toString());
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
		QueryWrapper<SysOrgInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysOrgInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysOrgInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysOrgInfoEntity.class);
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
		Page<SysOrgInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysOrgInfoEntity> list =  super.page(pages, queryWrapper);
		
		// 查找是否有子机构
		for(int i = 0; i < list.getRecords().size(); i++) {
			int count=sysOrgInfoMapper.queryCountByCondition(list.getRecords().get(i));
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
	public CommonMsg<SysOrgInfoEntity, SysOrgInfoEntity> querySysOrgInfoListMap(
			CommonMsg<SysOrgInfoEntity, SysOrgInfoEntity> commonMsg) {
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
		Collection<SysOrgInfoEntity> sysOrgInfoEntityList =  super.listByMap(conditionMap);
		List<SysOrgInfoEntity> sysOrgInfoMapList = new ArrayList<SysOrgInfoEntity>(sysOrgInfoEntityList);
		commonMsg.getBody().setListBody(sysOrgInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysOrgInfoEntity, NullEntity> saveSysOrgInfo(CommonMsg<SysOrgInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		
		/**
		 * 查询机构是否存在
		 */
		QueryWrapper<SysOrgInfoEntity> queryWrapper = new QueryWrapper<SysOrgInfoEntity>();
		queryWrapper.eq("tenant_id", commonMsg.getHead().getTenantId())
					.eq("org_no", commonMsg.getBody().getSingleBody().getOrgNo())
					.eq("valid_flag", "1");
		int count = super.count(queryWrapper);
		if(count > 0) {
			BizResponseEnum.ORG_HAS_EXIST.assertFail();
		}
		
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		// 清理缓存
		clearCache(commonMsg.getBody().getSingleBody());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysOrgInfoEntity> saveSysOrgInfoList(CommonMsg<NullEntity, SysOrgInfoEntity> commonMsg) {
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
		
		// 清理缓存
		SysOrgInfoEntity sysOrgInfoEntity = new SysOrgInfoEntity();
		sysOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysOrgInfoEntity.setOrgNo("DOOL");
		clearCache(sysOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysOrgInfoEntity, NullEntity> updateSysOrgInfo(CommonMsg<SysOrgInfoEntity, NullEntity> commonMsg) {
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
		
		/**
		 * 查询机构是否存在
		 */
		QueryWrapper<SysOrgInfoEntity> queryWrapper = new QueryWrapper<SysOrgInfoEntity>();
		queryWrapper.eq("tenant_id", commonMsg.getHead().getTenantId())
					.eq("org_no", commonMsg.getBody().getSingleBody().getOrgNo())
					.eq("valid_flag", "1");
		List<SysOrgInfoEntity> list = super.list(queryWrapper);
		if(null != list) {
			list.forEach(item -> {
				if(item.getOrgNo().equals(commonMsg.getBody().getSingleBody().getOrgNo())
						&& !item.getId().equals(commonMsg.getBody().getSingleBody().getId())) {					
					BizResponseEnum.ORG_HAS_EXIST.assertFail();
				}
			});
		}
		
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<SysOrgInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysOrgInfoEntity, NullEntity>();
		//定义singleBody
		SysOrgInfoEntity singleBody = new SysOrgInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysOrgInfoEntity, NullEntity> mutBean= new MutBean<SysOrgInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysOrgInfoServiceImpl.class).querySysOrgInfo(queryCommonMsg); 
		
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
		
		// 清理缓存
		clearCache(commonMsg.getBody().getSingleBody());			
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysOrgInfoEntity, NullEntity> saveOrUpdateSysOrgInfo(CommonMsg<SysOrgInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysOrgInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysOrgInfoEntity, NullEntity>();
		//定义singleBody
		SysOrgInfoEntity singleBody = new SysOrgInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysOrgInfoEntity, NullEntity> mutBean= new MutBean<SysOrgInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysOrgInfoServiceImpl.class).querySysOrgInfo(queryCommonMsg); 
		
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
		
		// 清理缓存
		clearCache(commonMsg.getBody().getSingleBody());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, SysOrgInfoEntity> saveOrUpdateSysOrgInfoList(CommonMsg<NullEntity, SysOrgInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		
		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}

		// 清理缓存
		SysOrgInfoEntity sysOrgInfoEntity = new SysOrgInfoEntity();
		sysOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysOrgInfoEntity.setOrgNo("DOOL");
		clearCache(sysOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "sysOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysOrgInfoEntity, NullEntity> deleteSysOrgInfo(CommonMsg<SysOrgInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 查询记录是否存在
		SysOrgInfoEntity sysOrgInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		
		BizResponseEnum.DATA_NOT_FOUND.assertIsNull(sysOrgInfoEntity);
		
		//递归删除子机构
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		buildOrgTree.deleteOrgParentOrgNo(commonMsg.getHead().getTenantId(), sysOrgInfoEntity.getOrgNo());
		
		// 清理缓存
		clearCache(commonMsg.getBody().getSingleBody());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysOrgInfoEntity> deleteSysOrgInfoList(CommonMsg<NullEntity, SysOrgInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		
		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
			//查询机构号
			SysOrgInfoEntity sysOrgInfoEntity = super.getById(commonMsg.getBody().getListBody().get(i).getId());
			//递归删除子机构
			buildOrgTree.deleteOrgParentOrgNo(commonMsg.getHead().getTenantId(), sysOrgInfoEntity.getOrgNo());
		}
		// 执行删除
		boolean result =  super.removeByIds(keylist);

		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		// 清理缓存
		SysOrgInfoEntity sysOrgInfoEntity = new SysOrgInfoEntity();
		sysOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysOrgInfoEntity.setOrgNo("DOOL");
		clearCache(sysOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysOrgInfoExcel(CommonMsg<SysOrgInfoEntity, SysOrgInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysOrgInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysOrgInfoEntity.class);
		
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
		Page<SysOrgInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysOrgInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "机构表";
		String fileName = "机构表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("机构号");
		headList.add("机构类型");
		headList.add("机构名称");
		headList.add("机构全名");
		headList.add("上级机构号");
		headList.add("排序序号");
		headList.add("备注");
		excelData.add(headList);
		for (SysOrgInfoEntity sysOrgInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysOrgInfoEntity.getOrgNo());	    		
			lists.add(sysOrgInfoEntity.getOrgType());	    		
			lists.add(sysOrgInfoEntity.getOrgName());	    		
			lists.add(sysOrgInfoEntity.getOrgFullName());	    		
			lists.add(sysOrgInfoEntity.getParentOrgNo());	    		
			lists.add(String.valueOf(sysOrgInfoEntity.getOrderSeq()));	    		
			lists.add(sysOrgInfoEntity.getRemark());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
	/*
	 * 清除缓存
	 */
	public void clearCache(SysOrgInfoEntity sysOrgInfoEntity) {
		log.info("-=======================clear==== sysOrgInfo:: {}", sysOrgInfoEntity.getTenantId()+sysOrgInfoEntity.getOrgNo());
		redisTemplate.delete("sysOrgInfo::"+sysOrgInfoEntity.getTenantId()+"DOOL");
	}
}
