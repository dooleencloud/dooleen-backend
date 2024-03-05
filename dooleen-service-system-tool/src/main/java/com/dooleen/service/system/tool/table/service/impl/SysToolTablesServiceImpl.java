package com.dooleen.service.system.tool.table.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import com.dooleen.service.system.tool.column.entity.SysToolColumnsEntity;
import com.dooleen.service.system.tool.column.mapper.SysToolColumnsMapper;
import com.dooleen.service.system.tool.table.entity.SysToolTablesEntity;
import com.dooleen.service.system.tool.table.mapper.SysToolTablesMapper;
import com.dooleen.service.system.tool.table.serivce.util.GeneratorCode;
import com.dooleen.service.system.tool.table.service.SysToolTablesService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-21 23:45:59
 * @Description : 系统表服务实现
 * @Author : apple
 * @Update: 2020-05-21 23:45:59
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysToolTablesServiceImpl extends ServiceImpl<SysToolTablesMapper, SysToolTablesEntity> implements SysToolTablesService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Value("${dooleen.templates.path}")
	private String path;


	@Autowired
	private  SysToolColumnsMapper sysToolColumnsMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_TOOL_TABLES.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_TOOL_TABLES.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysToolTables", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysToolTablesEntity, NullEntity> querySysToolTables(CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysToolTablesEntity sysToolTablesEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setSingleBody(sysToolTablesEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysToolTablesEntity> querySysToolTablesList(CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysToolTablesEntity> sysToolTablesEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysToolTablesEntityList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> querySysToolTablesListPage(
			CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
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
					if(entry.getKey().equals("tableComment")){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<SysToolTablesEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolTablesEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolTablesEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysToolTablesEntity.class);
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
		Page<SysToolTablesEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysToolTablesEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> querySysToolTablesListMap(
			CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
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
		Collection<SysToolTablesEntity> sysToolTablesEntityList =  super.listByMap(conditionMap);
		List<SysToolTablesEntity> sysToolTablesMapList = new ArrayList<SysToolTablesEntity>(sysToolTablesEntityList);
		commonMsg.getBody().setListBody(sysToolTablesMapList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysToolTables", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolTablesEntity, NullEntity> saveSysToolTables(CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysToolTablesEntity> saveSysToolTablesList(CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
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
	@CachePut(value = "sysToolTables", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolTablesEntity, NullEntity> updateSysToolTables(CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysToolTablesEntity, NullEntity> queryCommonMsg = new CommonMsg<SysToolTablesEntity, NullEntity>();
		//定义singleBody
		SysToolTablesEntity singleBody = new SysToolTablesEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysToolTablesEntity, NullEntity> mutBean= new MutBean<SysToolTablesEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysToolTablesServiceImpl.class).querySysToolTables(queryCommonMsg); 
		
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
	@CachePut(value = "sysToolTables", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolTablesEntity, NullEntity> saveOrUpdateSysToolTables(CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysToolTablesEntity, NullEntity> queryCommonMsg = new CommonMsg<SysToolTablesEntity, NullEntity>();
		//定义singleBody
		SysToolTablesEntity singleBody = new SysToolTablesEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysToolTablesEntity, NullEntity> mutBean= new MutBean<SysToolTablesEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysToolTablesServiceImpl.class).querySysToolTables(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, SysToolTablesEntity> saveOrUpdateSysToolTablesList(CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}

		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "sysToolTables", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolTablesEntity, NullEntity> deleteSysToolTables(CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行保存
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysToolTablesEntity> deleteSysToolTablesList(CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		
		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
		}
		// 执行保存
		boolean result =  super.removeByIds(keylist);
		
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysToolTablesExcel(CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysToolTablesEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolTablesEntity.class);
		
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
		Page<SysToolTablesEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysToolTablesEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统表管理";
		String fileName = "系统表管理";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("id");
		headList.add("租户ID");
		headList.add("系统名");
		headList.add("数据库");
		headList.add("表名");
		headList.add("表备注");
		headList.add("表类型");
		headList.add("主键");
		headList.add("服务名称");
		headList.add("工程名");
		headList.add("模块名称");
		headList.add("后端包名");
		headList.add("前后端标志");
		headList.add("后端路径");
		headList.add("前端路径");
		headList.add("版本");
		headList.add("是否已删除");
		headList.add("索引1");
		headList.add("索引2");
		headList.add("索引3");
		headList.add("索引4");
		headList.add("索引5");
		headList.add("数据签名");
		excelData.add(headList);
		for (SysToolTablesEntity sysToolTablesEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysToolTablesEntity.getId());	    		
			lists.add(sysToolTablesEntity.getTenantId());	    		
			lists.add(sysToolTablesEntity.getTableCatalog());	    		
			lists.add(sysToolTablesEntity.getTableSchema());	    		
			lists.add(sysToolTablesEntity.getTableName());	    		
			lists.add(sysToolTablesEntity.getTableComment());	    		
			lists.add(sysToolTablesEntity.getTableType());	    		
			lists.add(sysToolTablesEntity.getPrimaryKey());	    		
			lists.add(sysToolTablesEntity.getServiceName());	    		
			lists.add(sysToolTablesEntity.getProjectName());	    		
			lists.add(sysToolTablesEntity.getModuleName());	    		
			lists.add(sysToolTablesEntity.getPackageName());	    		
			lists.add(sysToolTablesEntity.getBackFrontFlag());	    		
			lists.add(sysToolTablesEntity.getBackendPath());	    		
			lists.add(sysToolTablesEntity.getFrontendPath());	    		
			lists.add(String.valueOf(sysToolTablesEntity.getVersion()));	    		
			lists.add(sysToolTablesEntity.getIsDeleted());	    		
			lists.add(sysToolTablesEntity.getIndex1());	    		
			lists.add(sysToolTablesEntity.getIndex2());	    		
			lists.add(sysToolTablesEntity.getIndex3());	    		
			lists.add(sysToolTablesEntity.getIndex4());	    		
			lists.add(sysToolTablesEntity.getIndex5());	    		
			lists.add(sysToolTablesEntity.getDataSign());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
	@Override
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> generatorCode(CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		String pathProfix = "templates";
		log.info("===templates path==="+path);
		pathProfix = path;
		SysToolTablesEntity sysToolTablesEntity = commonMsg.getBody().getSingleBody();
		String tableName = sysToolTablesEntity.getTableName();
		//构建查询条件
		QueryWrapper<SysToolColumnsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", sysToolTablesEntity.getTableName());
        queryWrapper.orderByAsc("ordinal_position");
        //查询字段列表
	    List<SysToolColumnsEntity> sysToolColumnsList = sysToolColumnsMapper.selectList(queryWrapper);
	    List<SysToolColumnsEntity> sysToolColumnsArrayList = new ArrayList<SysToolColumnsEntity>();
	    Iterator<SysToolColumnsEntity> it = sysToolColumnsList.iterator();
		while(it.hasNext()){
			SysToolColumnsEntity  sysToolColumns= it.next();
			sysToolColumns.setColumnName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sysToolColumns.getColumnName()));
			sysToolColumnsArrayList.add(sysToolColumns);
		}
		sysToolTablesEntity.setTableName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sysToolTablesEntity.getTableName()));
		Map<String, Object> codeInfoMap = new HashMap<String, Object>(500);
		codeInfoMap.put("sysToolColumnsList",sysToolColumnsArrayList);
		codeInfoMap.put("sysToolTablesEntity",sysToolTablesEntity);
		codeInfoMap.put("sysTableName",tableName);
		codeInfoMap.put("upCaseSysTableName",tableName.toUpperCase());
		
        String upcaseTableName = sysToolTablesEntity.getTableName().substring(0, 1).toUpperCase() + sysToolTablesEntity.getTableName().substring(1);
        //String ftlPath = "F:/syfintech/back/dooleen/dooleen-backend/dooleen-service-system-tool/src/main/resources/templates/java";
      	String ftlPath = pathProfix+"/java";
		String ftlName = "";
		String outPath = "";
		String outFile = "";
		
		//将报名转换成路径一部分
		String packagePath = sysToolTablesEntity.getPackageName().replace(".", "/");
		//===============开始生成后端代码===================
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"/entity";
		ftlName = "Entity.ftl";
		outFile = upcaseTableName+"Entity.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		//生成controller
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"/controller";
		ftlName = "Controller.ftl";
		outFile = upcaseTableName+"Controller.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);

		//生成mapper
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"/mapper";
		ftlName = "Mapper.ftl";
		outFile = upcaseTableName+"Mapper.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);

		//生成Service
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"/service";
		ftlName = "Service.ftl";
		outFile = upcaseTableName+"Service.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//生成ServiceImpl
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"/service/impl";
		ftlName = "ServiceImpl.ftl";
		outFile = upcaseTableName+"ServiceImpl.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);

		//生成pom.xml
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"";
		ftlName = "pom.ftl";
		outFile = "pom.xml";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//生成bootstrap.properties
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/resources";
		ftlName = "properties.ftl";
		outFile = "bootstrap.properties";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//生成Application.java
		outPath =  sysToolTablesEntity.getBackendPath()+"/"+sysToolTablesEntity.getProjectName()+"/src/main/java/"+packagePath+"";
		ftlName = "Application.ftl";
		outFile = upcaseTableName+"Application.java";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//===============生成后端代码结束=====================

		//===============生成前端代码开始=====================
		//ftlPath = "F:/syfintech/back/dooleen/dooleen-backend/dooleen-service-system-tool/src/main/resources/templates/vue";
      	ftlPath = pathProfix+"/vue";

		//生成vueIndex
		outPath =  sysToolTablesEntity.getFrontendPath()+"/views/"+sysToolTablesEntity.getModuleName();
		ftlName = "vueIndex.ftl";
		outFile = sysToolTablesEntity.getTableName()+".vue";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//生成任务处理对话框
		outPath =  sysToolTablesEntity.getFrontendPath()+"/views/"+sysToolTablesEntity.getModuleName();
		ftlName = "vueIndexFlowDialog.ftl";
		outFile = sysToolTablesEntity.getTableName()+"Dialog.vue";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		
		//生成vueApi
		outPath =  sysToolTablesEntity.getFrontendPath()+"/api/"+sysToolTablesEntity.getModuleName();
		ftlName = "vueApi.ftl";
		outFile = sysToolTablesEntity.getTableName()+"Api.js";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);

		//生成vueApi
		outPath =  sysToolTablesEntity.getFrontendPath()+"/api/"+sysToolTablesEntity.getModuleName();
		ftlName = "appApi.ftl";
		outFile = sysToolTablesEntity.getTableName()+"AppApi.js";
		GeneratorCode.genCode(codeInfoMap, ftlPath,ftlName, outPath, outFile);
		//===============生成前端代码结束=====================

		log.info("====generatorCode service end == ");

		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
