package com.dooleen.service.general.org.info.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.org.info.mapper.GeneralOrgInfoMapper;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.org.info.service.GeneralOrgInfoService;
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
import java.lang.reflect.Field;
import java.util.*;

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
public class GeneralOrgInfoServiceImpl extends ServiceImpl<GeneralOrgInfoMapper, GeneralOrgInfoEntity> implements GeneralOrgInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	@Autowired
	private  BuildOrgTree buildOrgTree;
	
	@Autowired
	private  GeneralOrgInfoMapper generalOrgInfoMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_ORG_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_ORG_INFO.getEntityName();
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Override
	@Cacheable(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> queryGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralOrgInfoEntity generalOrgInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setSingleBody(generalOrgInfoEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalOrgInfoEntity,commonMsg);	
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralOrgInfoEntity> queryGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralOrgInfoEntity> generalOrgInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalOrgInfoEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	//@Cacheable(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+#commonMsg.body.singleBody.orgNo")
	public CommonMsg<GeneralOrgInfoEntity,JSONObject> queryGeneralOrgTree(CommonMsg<GeneralOrgInfoEntity,JSONObject> commonMsg){
		CommonUtil.service(commonMsg);

		// 查询条件
		GeneralOrgInfoEntity generalOrgInfo = new GeneralOrgInfoEntity();
		generalOrgInfo.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfo.setOrgNo(commonMsg.getBody().getSingleBody().getOrgNo());
		// 查询出所有的一级机构
		List<GeneralOrgInfoEntity>  orgList= generalOrgInfoMapper.queryByParentOrgNo(generalOrgInfo);

		// 所有字段，包含父类的字段，但是不包含私有字段
		Field[] fields = generalOrgInfo.getClass().getFields();
		// 本类的所有字段，包含私有字段，但是不包含父类的字段
		Field[] fields1 = generalOrgInfo.getClass().getDeclaredFields();
		List<String> fieldList = new ArrayList<>();
		for (Field field: fields){
			fieldList.add(field.getName());
		}
		for (Field field: fields1){
			if(!fieldList.contains(field.getName())){
				fieldList.add(field.getName());
			}
		}

		Map entityMap =  null;
		// 处理机构树
		List<JSONObject> list = new ArrayList<>();
		for (GeneralOrgInfoEntity generalOrgInfoEntity : orgList) {
			if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getOrgType())) {
				if(generalOrgInfoEntity.getOrgType().equals(commonMsg.getBody().getSingleBody().getOrgType())) {
					JSONObject treeObject = new JSONObject();
					entityMap = JSONObject.parseObject(JSONObject.toJSONString(generalOrgInfoEntity), Map.class);
					for (String field: fieldList){
						if(!field.equals("serialVersionUID")){
							treeObject.put(field, entityMap.get(field));
						}
					}
					List<JSONObject> childrenData = buildOrgTree.getChildren(generalOrgInfoEntity, fieldList);
					if(null == childrenData || childrenData.isEmpty()){
						treeObject.put("leaf", true);
					}
					treeObject.put("children", childrenData);
					list.add(treeObject);
				}
			}
			else {
				JSONObject treeObject = new JSONObject();
				entityMap = JSONObject.parseObject(JSONObject.toJSONString(generalOrgInfoEntity), Map.class);
				for (String field: fieldList){
					if(!field.equals("serialVersionUID")){
						treeObject.put(field, entityMap.get(field));
					}
				}
				List<JSONObject> childrenData = buildOrgTree.getChildren(generalOrgInfoEntity, fieldList);
				if(null == childrenData || childrenData.isEmpty()){
					treeObject.put("leaf", true);
				}
				treeObject.put("children", childrenData);
				list.add(treeObject);
			}
		}
		
		commonMsg.getBody().setListBody(list);
		commonMsg.getBody().setCurrentPage(1);
		commonMsg.getBody().setPageSize(99999);
		commonMsg.getBody().setTotal(99999);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	public static void main(String[] args) {
		GeneralOrgInfoEntity a = new GeneralOrgInfoEntity();
		Field[] fields = a.getClass().getFields();
		Field[] fields1 = a.getClass().getDeclaredFields();
		for (Field field: fields){
			field.setAccessible(true);
			System.out.println(field.getName());
		}
		for (Field field: fields1){
			System.out.println(field.getName());
		}
	}

	@Override
	public CommonMsg<GeneralOrgInfoEntity, GeneralOrgInfoEntity> queryGeneralOrgInfoListPage(
			CommonMsg<GeneralOrgInfoEntity, GeneralOrgInfoEntity> commonMsg) {
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
		QueryWrapper<GeneralOrgInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralOrgInfoEntity.class);
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
		Page<GeneralOrgInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralOrgInfoEntity> list =  super.page(pages, queryWrapper);
		
		// 查找是否有子机构
		for(int i = 0; i < list.getRecords().size(); i++) {
			int count=generalOrgInfoMapper.queryCountByCondition(list.getRecords().get(i));
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
	public CommonMsg<GeneralOrgInfoEntity, GeneralOrgInfoEntity> queryGeneralOrgInfoListMap(
			CommonMsg<GeneralOrgInfoEntity, GeneralOrgInfoEntity> commonMsg) {
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
		Collection<GeneralOrgInfoEntity> generalOrgInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralOrgInfoEntity> generalOrgInfoMapList = new ArrayList<GeneralOrgInfoEntity>(generalOrgInfoEntityList);
		commonMsg.getBody().setListBody(generalOrgInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> saveGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
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
		QueryWrapper<GeneralOrgInfoEntity> queryWrapper = new QueryWrapper<GeneralOrgInfoEntity>();
		queryWrapper.eq("tenant_id", commonMsg.getHead().getTenantId())
					.eq("org_no", commonMsg.getBody().getSingleBody().getOrgNo())
					.eq("valid_flag", "1");
		int count = super.count(queryWrapper);
		BizResponseEnum.ORG_HAS_EXIST.assertIsFalse(count > 0,commonMsg);
		
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralOrgInfoEntity> saveGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg) {
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
		GeneralOrgInfoEntity generalOrgInfoEntity = new GeneralOrgInfoEntity();
		generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfoEntity.setOrgNo("DOOL");
		clearCache(generalOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> updateGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
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
		QueryWrapper<GeneralOrgInfoEntity> queryWrapper = new QueryWrapper<GeneralOrgInfoEntity>();
		queryWrapper.eq("tenant_id", commonMsg.getHead().getTenantId())
					.eq("org_no", commonMsg.getBody().getSingleBody().getOrgNo())
					.eq("valid_flag", "1");
		List<GeneralOrgInfoEntity> list = super.list(queryWrapper);
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
		CommonMsg<GeneralOrgInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralOrgInfoEntity, NullEntity>();
		//定义singleBody
		GeneralOrgInfoEntity singleBody = new GeneralOrgInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralOrgInfoEntity, NullEntity> mutBean= new MutBean<GeneralOrgInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralOrgInfoServiceImpl.class).queryGeneralOrgInfo(queryCommonMsg); 
		
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
	@CachePut(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> saveOrUpdateGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralOrgInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralOrgInfoEntity, NullEntity>();
		//定义singleBody
		GeneralOrgInfoEntity singleBody = new GeneralOrgInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralOrgInfoEntity, NullEntity> mutBean= new MutBean<GeneralOrgInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralOrgInfoServiceImpl.class).queryGeneralOrgInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralOrgInfoEntity> saveOrUpdateGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg) {
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
		GeneralOrgInfoEntity generalOrgInfoEntity = new GeneralOrgInfoEntity();
		generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfoEntity.setOrgNo("DOOL");
		clearCache(generalOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "generalOrgInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> deleteGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 查询记录是否存在
		GeneralOrgInfoEntity generalOrgInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalOrgInfoEntity,commonMsg);
		
		//递归删除子机构
		super.removeById(commonMsg.getBody().getSingleBody().getId());
		buildOrgTree.deleteOrgParentOrgNo(commonMsg.getHead().getTenantId(), generalOrgInfoEntity.getOrgNo());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		// 清理缓存
		clearCache(commonMsg.getBody().getSingleBody());
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralOrgInfoEntity> deleteGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		
		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
			//查询机构号
			GeneralOrgInfoEntity generalOrgInfoEntity = super.getById(commonMsg.getBody().getListBody().get(i).getId());
			//递归删除子机构
			buildOrgTree.deleteOrgParentOrgNo(commonMsg.getHead().getTenantId(), generalOrgInfoEntity.getOrgNo());
		}
		// 执行删除
		boolean result =  super.removeByIds(keylist);

		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		// 清理缓存
		GeneralOrgInfoEntity generalOrgInfoEntity = new GeneralOrgInfoEntity();
		generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfoEntity.setOrgNo("DOOL");
		clearCache(generalOrgInfoEntity);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportGeneralOrgInfoExcel(CommonMsg<GeneralOrgInfoEntity, GeneralOrgInfoEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<GeneralOrgInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralOrgInfoEntity.class);
		
		// 处理排序
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++){
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null){
				if(sqlOrderList.get(i).getOrder().equals("ascending")){
					queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}else{
					queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
			}
		}
		
		// 定义分页查询
		Page<GeneralOrgInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralOrgInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "组织机构表(综合)";
		String fileName = "组织机构表(综合)";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("机构号");
		headList.add("机构类型");
		headList.add("机构名称");
		headList.add("机构全名");
		headList.add("上级机构号");
		headList.add("所属机构组");
		headList.add("排序序号");
		headList.add("是否有子节点");
		headList.add("负责人ID");
		headList.add("负责人名");
		headList.add("机构地址");
		headList.add("机构邮编号");
		headList.add("联系电话号码");
		headList.add("办公室编号");
		headList.add("备注");
		excelData.add(headList);
		for (GeneralOrgInfoEntity generalOrgInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalOrgInfoEntity.getId());	    		
			lists.add(generalOrgInfoEntity.getTenantId());	    		
			lists.add(generalOrgInfoEntity.getOrgNo());	    		
			lists.add(generalOrgInfoEntity.getOrgType());	    		
			lists.add(generalOrgInfoEntity.getOrgName());	    		
			lists.add(generalOrgInfoEntity.getOrgFullName());	    		
			lists.add(generalOrgInfoEntity.getParentOrgNo());	    		
			lists.add(generalOrgInfoEntity.getBelongOrgGroup ());	    		
			lists.add(String.valueOf(generalOrgInfoEntity.getOrderSeq()));	    		
			lists.add(generalOrgInfoEntity.getHasChildren());	    		
			lists.add(generalOrgInfoEntity.getRespsbUserId());	    		
			lists.add(generalOrgInfoEntity.getRespsbUserName());	    		
			lists.add(generalOrgInfoEntity.getOrgAddress());	    		
			lists.add(generalOrgInfoEntity.getOrgPostNo());	    		
			lists.add(generalOrgInfoEntity.getContactPhoneNo());	    		
			lists.add(generalOrgInfoEntity.getOfficeRoomNo());	    		
			lists.add(generalOrgInfoEntity.getRemark());	    		
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
	public void clearCache(GeneralOrgInfoEntity generalOrgInfoEntity) {
		log.info("-=======================clear==== generalOrgInfo:: {}", generalOrgInfoEntity.getTenantId()+generalOrgInfoEntity.getOrgNo());
		redisTemplate.delete("generalOrgInfo::"+generalOrgInfoEntity.getTenantId()+"DOOL");
	}
}
