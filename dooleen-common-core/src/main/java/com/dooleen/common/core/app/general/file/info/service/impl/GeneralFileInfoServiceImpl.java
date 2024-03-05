package com.dooleen.common.core.app.general.file.info.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.history.mapper.GeneralFileHistoryMapper;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.mapper.GeneralFileInfoMapper;
import com.dooleen.common.core.app.general.file.info.service.GeneralFileInfoService;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
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
 * @CreateDate : 2020-06-21 07:40:13
 * @Description : 文档信息管理服务实现
 * @Author : apple
 * @Update: 2020-06-21 07:40:13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralFileInfoServiceImpl extends ServiceImpl<GeneralFileInfoMapper, GeneralFileInfoEntity> implements GeneralFileInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

//	@Autowired
//	private  GeneralFileInfoMapper generalFileInfoMapper;
	@Autowired
	private GeneralFileHistoryMapper generalFileHistoryMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_FILE_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_FILE_INFO.getEntityName();
	
	private  static String historySeqPrefix= TableEntityORMEnum.GENERAL_FILE_HISTORY.getSeqPrefix();
	private  static String historyTableName = TableEntityORMEnum.GENERAL_FILE_HISTORY.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	@Cacheable(value = "generalFileInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralFileInfoEntity, NullEntity> queryGeneralFileInfo(CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralFileInfoEntity generalFileInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(generalFileInfoEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> queryGeneralFileInfoAndHistory(CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		// 根据Key值查询
		GeneralFileInfoEntity generalFileInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(generalFileInfoEntity);
		//查询历史记录
		QueryWrapper<GeneralFileHistoryEntity> queryWrapper = new QueryWrapper<GeneralFileHistoryEntity>();
		queryWrapper.lambda().eq(GeneralFileHistoryEntity::getTenantId, generalFileInfoEntity.getTenantId());
		queryWrapper.lambda().eq(GeneralFileHistoryEntity::getFileId, generalFileInfoEntity.getId());
		queryWrapper.orderByDesc("file_version_no");
		List<GeneralFileHistoryEntity> generalFileHistoryEntityList = generalFileHistoryMapper.selectList(queryWrapper);
		commonMsg.getBody().setListBody(generalFileHistoryEntityList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<NullEntity, GeneralFileInfoEntity> queryGeneralFileInfoList(CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralFileInfoEntity> generalFileInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalFileInfoEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> queryGeneralFileInfoListPage(
			CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg) {
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
					//名字用模糊查询
					if(entry.getKey().equals("fileName")) {
						sqlConditionEntity0.setType("like");
					}
					else {
						sqlConditionEntity0.setType("=");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<GeneralFileInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFileInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFileInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralFileInfoEntity.class);
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
		Page<GeneralFileInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralFileInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> queryGeneralFileInfoListMap(
			CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg) {
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
		Collection<GeneralFileInfoEntity> generalFileInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralFileInfoEntity> generalFileInfoMapList = new ArrayList<GeneralFileInfoEntity>(generalFileInfoEntityList);
		commonMsg.getBody().setListBody(generalFileInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalFileInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFileInfoEntity, NullEntity> saveGeneralFileInfo(CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg) {
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
		commonMsg.getBody().getSingleBody().setFileSign(commonMsg.getBody().getSingleBody().getDataSign());

		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> saveGeneralFileInfoAndHistory(
			CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		GeneralFileInfoEntity generalFileInfoEntity = new GeneralFileInfoEntity();
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		generalFileInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalFileInfoEntity,commonMsg);
		if(generalFileInfoEntity != null) {
			if(StringUtil.isNumeric(generalFileInfoEntity.getLastVersionNo())){
				generalFileInfoEntity.setLastVersionNo((Integer.parseInt(generalFileInfoEntity.getLastVersionNo())+1)+"");
			}
			else {
				generalFileInfoEntity.setLastVersionNo("1");
			}
			if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getFileSign())) {
				generalFileInfoEntity.setFileSign(commonMsg.getBody().getSingleBody().getFileSign());
			}
			generalFileInfoEntity.setFileSize(commonMsg.getBody().getSingleBody().getFileSize());
			generalFileInfoEntity.setFilePath(commonMsg.getBody().getSingleBody().getFilePath());
			generalFileInfoEntity.setDataSign(DooleenMD5Util.md5(generalFileInfoEntity.toString(),  ConstantValue.md5Key));
			// 执行保存
			boolean result =  super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFileInfoEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
			
			BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		}

		//写历史记录
		GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
		generalFileHistoryEntity.setTenantId(generalFileInfoEntity.getTenantId());
		generalFileHistoryEntity.setFileId(generalFileInfoEntity.getId());
		generalFileHistoryEntity.setFileSize(generalFileInfoEntity.getFileSize());
		generalFileHistoryEntity.setFileName(generalFileInfoEntity.getFileName());
		generalFileHistoryEntity.setFileVersionNo(generalFileInfoEntity.getLastVersionNo());
		generalFileHistoryEntity.setFilePath(generalFileInfoEntity.getFilePath());
		generalFileHistoryEntity.setDataSign(DooleenMD5Util.md5(generalFileHistoryEntity.toString(),  ConstantValue.md5Key));
		generalFileHistoryMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFileHistoryEntity, commonMsg.getHead(),historyTableName, historySeqPrefix, redisTemplate));
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	
	@Override
	public CommonMsg<NullEntity, GeneralFileInfoEntity> saveGeneralFileInfoList(CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		} 
		// 初始化数据
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setLastVersionNo("1");
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
			commonMsg.getBody().getListBody().get(i).setFileSign(commonMsg.getBody().getListBody().get(i).getDataSign());
			commonMsg.getBody().getListBody().set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
			//写历史记录
			GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
			generalFileHistoryEntity.setTenantId(commonMsg.getBody().getListBody().get(i).getTenantId());
			generalFileHistoryEntity.setFileId(commonMsg.getBody().getListBody().get(i).getId());
			generalFileHistoryEntity.setFileName(commonMsg.getBody().getListBody().get(i).getFileName());
			generalFileHistoryEntity.setFileVersionNo(commonMsg.getBody().getListBody().get(i).getLastVersionNo());
			generalFileHistoryEntity.setFilePath(commonMsg.getBody().getListBody().get(i).getFilePath());
			generalFileHistoryEntity.setDataSign(DooleenMD5Util.md5(generalFileHistoryEntity.toString(),  ConstantValue.md5Key));
			generalFileHistoryMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFileHistoryEntity, commonMsg.getHead(),historyTableName, historySeqPrefix, redisTemplate));
		}
		// 批量保存
		boolean result =  super.saveBatch(commonMsg.getBody().getListBody());
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalFileInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFileInfoEntity, NullEntity> updateGeneralFileInfo(CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralFileInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralFileInfoEntity, NullEntity>();
		//定义singleBody
		GeneralFileInfoEntity singleBody = new GeneralFileInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralFileInfoEntity, NullEntity> mutBean= new MutBean<GeneralFileInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralFileInfoServiceImpl.class).queryGeneralFileInfo(queryCommonMsg); 
		
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
	@CachePut(value = "generalFileInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralFileInfoEntity, NullEntity> saveOrUpdateGeneralFileInfo(CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralFileInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralFileInfoEntity, NullEntity>();
		//定义singleBody
		GeneralFileInfoEntity singleBody = new GeneralFileInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralFileInfoEntity, NullEntity> mutBean= new MutBean<GeneralFileInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralFileInfoServiceImpl.class).queryGeneralFileInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralFileInfoEntity> saveOrUpdateGeneralFileInfoList(CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalFileInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralFileInfoEntity, NullEntity> deleteGeneralFileInfo(CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralFileInfoEntity> deleteGeneralFileInfoList(CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg) {
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
	public void exportGeneralFileInfoExcel(CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<GeneralFileInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralFileInfoEntity.class);
		
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
		Page<GeneralFileInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralFileInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "文档信息表";
		String fileName = "文档信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("目录ID");
		headList.add("文档名称");
		headList.add("文档类型");
		headList.add("文档大小");
		headList.add("文档路径");
		headList.add("文档签名");
		headList.add("最后版本号");
		headList.add("有效日期");
		headList.add("分享次数");
		headList.add("分享有效期");
		headList.add("拥有人ID");
		excelData.add(headList);
		for (GeneralFileInfoEntity generalFileInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalFileInfoEntity.getId());	    		
			lists.add(generalFileInfoEntity.getTenantId());	    		
			lists.add(generalFileInfoEntity.getCatalogId());	    		
			lists.add(generalFileInfoEntity.getFileName());	    		
			lists.add(generalFileInfoEntity.getFileType());	    		
			lists.add(generalFileInfoEntity.getFileSize());	    		
			lists.add(generalFileInfoEntity.getFilePath());	    		
			lists.add(generalFileInfoEntity.getFileSign());	    		
			lists.add(generalFileInfoEntity.getLastVersionNo());	    		
			lists.add(generalFileInfoEntity.getValidDate());	    		
			lists.add(String.valueOf(generalFileInfoEntity.getShareTimes()));	    		
			lists.add(generalFileInfoEntity.getShareValidDate());	    		
			lists.add(generalFileInfoEntity.getOwnerId());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
