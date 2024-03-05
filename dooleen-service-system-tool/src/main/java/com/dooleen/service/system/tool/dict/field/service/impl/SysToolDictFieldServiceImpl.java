package com.dooleen.service.system.tool.dict.field.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.system.tool.dict.attr.entity.SysToolDictAttrEntity;
import com.dooleen.service.system.tool.dict.attr.mapper.SysToolDictAttrMapper;
import com.dooleen.service.system.tool.dict.field.entity.SysToolDictFieldEntity;
import com.dooleen.service.system.tool.dict.field.mapper.SysToolDictFieldMapper;
import com.dooleen.service.system.tool.dict.field.service.SysToolDictFieldService;
import com.dooleen.service.system.tool.dict.root.entity.SysToolDictRootEntity;
import com.dooleen.service.system.tool.dict.root.mapper.SysToolDictRootMapper;
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
 * @CreateDate : 2020-05-29 23:28:56
 * @Description : 数据标准变量服务服务实现
 * @Author : apple
 * @Update: 2020-05-29 23:28:56
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysToolDictFieldServiceImpl extends ServiceImpl<SysToolDictFieldMapper, SysToolDictFieldEntity> implements SysToolDictFieldService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private  SysToolDictRootMapper sysToolDictRootMapper;

	@Autowired
	private  SysToolDictAttrMapper sysToolDictAttrMapper;	

	private  static String seqPrefix= TableEntityORMEnum.SYS_TOOL_DICT_FIELD.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_TOOL_DICT_FIELD.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Autowired
	private MsgSendUtil msgSendUtil;
	
	@Override
	@Cacheable(value = "sysToolDictField", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> querySysToolDictField(CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysToolDictFieldEntity sysToolDictFieldEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysToolDictFieldEntity,commonMsg);
		
		commonMsg.getBody().setSingleBody(sysToolDictFieldEntity);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysToolDictFieldEntity> querySysToolDictFieldList(CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysToolDictFieldEntity> sysToolDictFieldEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysToolDictFieldEntityList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> querySysToolDictFieldListPage(
			CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> commonMsg) {
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
		QueryWrapper<SysToolDictFieldEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictFieldEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictFieldEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysToolDictFieldEntity.class);
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
		Page<SysToolDictFieldEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysToolDictFieldEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> querySysToolDictFieldListMap(
			CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> commonMsg) {
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
		Collection<SysToolDictFieldEntity> sysToolDictFieldEntityList =  super.listByMap(conditionMap);
		List<SysToolDictFieldEntity> sysToolDictFieldMapList = new ArrayList<SysToolDictFieldEntity>(sysToolDictFieldEntityList);
		commonMsg.getBody().setListBody(sysToolDictFieldMapList);
		
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysToolDictField", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> saveSysToolDictField(CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysToolDictFieldEntity> saveSysToolDictFieldList(CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
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
	@CachePut(value = "sysToolDictField", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> updateSysToolDictField(CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysToolDictFieldEntity, NullEntity> queryCommonMsg = new CommonMsg<SysToolDictFieldEntity, NullEntity>();
		//定义singleBody
		SysToolDictFieldEntity singleBody = new SysToolDictFieldEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysToolDictFieldEntity, NullEntity> mutBean= new MutBean<SysToolDictFieldEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysToolDictFieldServiceImpl.class).querySysToolDictField(queryCommonMsg); 
		
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
	@CachePut(value = "sysToolDictField", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> saveOrUpdateSysToolDictField(CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysToolDictFieldEntity, NullEntity> queryCommonMsg = new CommonMsg<SysToolDictFieldEntity, NullEntity>();
		//定义singleBody
		SysToolDictFieldEntity singleBody = new SysToolDictFieldEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysToolDictFieldEntity, NullEntity> mutBean= new MutBean<SysToolDictFieldEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysToolDictFieldServiceImpl.class).querySysToolDictField(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, SysToolDictFieldEntity> saveOrUpdateSysToolDictFieldList(CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
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
    @CacheEvict(value = "sysToolDictField", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> deleteSysToolDictField(CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysToolDictFieldEntity> deleteSysToolDictFieldList(CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
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
	public CommonMsg<JSONObject, JSONObject> autoGetSysToolDictFieldList(CommonMsg<JSONObject, JSONObject> commonMsg) {
		List<JSONObject> returnList =  new ArrayList<JSONObject>();
		for (JSONObject dictList: commonMsg.getBody().getListBody()) {
			if(dictList.getString("comment") != null && !dictList.getString("comment").equals("")) {
				boolean isFound = false;
				String columnName = "";
				String columnFullName = "";
				String likeColumnComment = "";
				String comment = "";
				String dataType = "";
				int length = 0;
				int decimalLength = 0;
				
				//临时中间变量
				String columnNameTmp = "";
				String columnFullNameTmp = "";
				String commentTmp = "";
				String dataTypeTmp = "";
				String foundFlag = "";
				int lengthTmp = 0;
				int decimalLengthTmp = 0;
				
				String columnComment = dictList.getString("comment").trim();
				
				
				// 直接用名字去变量库里查询，如果存在直接返回
				SysToolDictFieldEntity sysToolDictFieldEntity = getDictField(columnComment,"=");
				if(sysToolDictFieldEntity != null)
				{
					columnName = sysToolDictFieldEntity.getColumnName();
					comment = sysToolDictFieldEntity.getColumnComment();
					columnFullName = sysToolDictFieldEntity.getColumnFullName();
					dataType = sysToolDictFieldEntity.getDataType();
					length = sysToolDictFieldEntity.getLength();
					decimalLength = sysToolDictFieldEntity.getDecimalLength();
					System.out.println(columnName+" 从变量表直接获取 == "+sysToolDictFieldEntity.getColumnName());
					isFound = true;
					foundFlag = "field";
				}
				// 直接用名字去词根库里查询，如果存在直接返回
				if(!isFound) {
					SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(columnComment);
					if(sysToolDictRootEntity != null)
					{
						columnName = sysToolDictRootEntity.getRootName().trim();
						comment = sysToolDictRootEntity.getComment();
						columnFullName = sysToolDictRootEntity.getRootFullName();
						dataType = sysToolDictRootEntity.getDataType();
						length = sysToolDictRootEntity.getLength();
						decimalLength = sysToolDictRootEntity.getDecimalLength();
						System.out.println(columnName+" 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
						isFound = true;
						foundFlag = "root";
					}
				}
				if(isFound)
				{
					//存入临时中间变量
					columnNameTmp = columnName;
					commentTmp = comment;
					columnFullNameTmp = columnFullName;
					dataTypeTmp =  dataType;
					lengthTmp = length;
					decimalLengthTmp = decimalLength;
				}
				if(!isFound) {
					// 每2个字段分词检索，最后一个字是词根
					int clength = columnComment.length();
					String subWord = "";
					boolean isRoot = false;
					float fountRate = 0;
					float maxFountRate = 0;
					int beginIndex = 0;
					System.out.println(clength+"====columnC  "+(clength / 2)+"  omment===="+columnComment);
					int wordCount=0;
					int foundCount = 0;
					
					//优先处理自定义分词 
					if(columnComment.indexOf(" ") > 0) {
						columnName = "";
						columnFullName = "";
						comment = "";
						dataType = "";
						length = 0;
						decimalLength = 0;
						foundCount = 0;
						String[] wordList = columnComment.split(" ");
						wordCount = wordList.length;
						for(int i  =0; i < wordCount; i++) {
							subWord = wordList[i];
							// 处理词根
							if(i+1 == wordCount) {
								isRoot = true;
							}
							else {
								isRoot = false;
							}
							// 用分词去定于表查询
							if(!isRoot) {
								SysToolDictAttrEntity sysToolDictAttrEntity = getDictAttr(subWord);
								if(sysToolDictAttrEntity != null)
								{
									columnName += sysToolDictAttrEntity.getAttrName().trim()+"_";
									comment += sysToolDictAttrEntity.getAttrComment()+"";
									columnFullName += sysToolDictAttrEntity.getAttrFullName()+" ";
									System.out.println(columnName+" 自定义分词格式 从定语表获取 == "+sysToolDictAttrEntity.getAttrName().trim());
									foundCount ++;
								}						
							}
							// 最后一个分词去词根表查
							else {
								SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(subWord);
								if(sysToolDictRootEntity != null)
								{
									columnName += sysToolDictRootEntity.getRootName().trim();
									comment += sysToolDictRootEntity.getComment();
									columnFullName += sysToolDictRootEntity.getRootFullName();
									dataType = sysToolDictRootEntity.getDataType();
									length = sysToolDictRootEntity.getLength();
									decimalLength = sysToolDictRootEntity.getDecimalLength();
									System.out.println(columnName+" 自定义分词格式 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
									foundCount ++;
								}
							} 
							System.out.println("====自定义分词 subWord000===="+subWord);

						}
					}
					if(columnComment.indexOf(" ") > 0)
					{
						System.out.println("自定义分词查找， 共 "+wordCount+" 次，找到 "+foundCount+" 次");
						//存入临时中间变量
						columnNameTmp = columnName;
						commentTmp = comment;
						columnFullNameTmp = columnFullName;
						dataTypeTmp =  dataType;
						lengthTmp = length;
						decimalLengthTmp = decimalLength;
						fountRate = (float) foundCount / wordCount;
						maxFountRate = fountRate;
					}
					// 2个字分词查找
					if(fountRate < 1) {
						columnName = "";
						columnFullName = "";
						comment = "";
						dataType = "";
						length = 0;
						decimalLength = 0;
						foundCount = 0;
						if(clength % 2 == 0){
							wordCount = clength / 2;
						}
						else {
							wordCount = clength / 2 + 1;
						}
						for(int i = 0; i < wordCount; i++) {
							if(i*2+2 <= clength){
								subWord = columnComment.substring(i*2,i*2+2);
								isRoot = false;
								if(i*2+2 == clength) {
									isRoot = true;
								}
							} 
							else {
								subWord = columnComment.substring(i*2,i*2+1);
								isRoot = true;
							}
							// 用分词去定语表查询
							if(!isRoot) {
								System.out.println("====subWord111定语===="+subWord);
								SysToolDictAttrEntity sysToolDictAttrEntity = getDictAttr(subWord);
								if(sysToolDictAttrEntity != null)
								{
									columnName += sysToolDictAttrEntity.getAttrName().trim()+"_";
									comment += sysToolDictAttrEntity.getAttrComment()+"";
									columnFullName += sysToolDictAttrEntity.getAttrFullName()+" ";
									System.out.println(columnName+" 2个字分词格式 从定语表获取 == "+sysToolDictAttrEntity.getAttrName().trim());
									foundCount ++;
								}						
							}
							// 最后一个分词去词根表查
							else {
								System.out.println("====subWord111词根===="+subWord);
								SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(subWord);
								if(sysToolDictRootEntity != null)
								{
									columnName += sysToolDictRootEntity.getRootName().trim();
									comment += sysToolDictRootEntity.getComment();
									columnFullName += sysToolDictRootEntity.getRootFullName();
									dataType = sysToolDictRootEntity.getDataType();
									length = sysToolDictRootEntity.getLength();
									decimalLength = sysToolDictRootEntity.getDecimalLength();
									System.out.println(columnName+" 2个字分词格式 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
									foundCount ++;
								}
							} 
							System.out.println("====2个词 subWord111===="+subWord);

						}
					}
					System.out.println("2个字分词查找， 共 "+wordCount+" 次，找到 "+foundCount+" 次");
					
					// 如果查找命中率小于0.5 采用3个字分词
					fountRate = (float) foundCount / wordCount;
					if(fountRate > maxFountRate) {
						//存入临时中间变量
						columnNameTmp = columnName;
						commentTmp = comment;
						columnFullNameTmp = columnFullName;
						dataTypeTmp =  dataType;
						lengthTmp = length;
						decimalLengthTmp = decimalLength;
						 
						maxFountRate = fountRate;
					}
					if(fountRate < 1) {
						columnName = "";
						columnFullName = "";
						comment = "";
						dataType = "";
						length = 0;
						decimalLength = 0;
						foundCount = 0;
						System.out.println("====开始三个词====");
						if(clength % 3 == 0){
							wordCount = clength / 3;
						}
						else { 
							wordCount = clength / 3 + 1;
						}
						for(int i = 0; i < wordCount; i++) {
							if(i*3+3 <= clength){
								subWord = columnComment.substring(i*3,i*3+3);
								if(i*3+3 == clength) {
									isRoot = true;
								}
							} 
							else if(i*3+2 <= clength -1){
								subWord = columnComment.substring(i*3,i*3+2);
								if(i*3+2 == clength) {
									isRoot = true;
								}
							} 
							else {
								if(clength % 3 == 1){
									subWord = columnComment.substring(i*3,i*3+1);
								}
								else {
									subWord = columnComment.substring(i*3,i*3+2);
								}
								isRoot = true;
							}
							// 用分词去定于表查询
							if(!isRoot) {
								SysToolDictAttrEntity sysToolDictAttrEntity = getDictAttr(subWord);
								if(sysToolDictAttrEntity != null)
								{
									columnName += sysToolDictAttrEntity.getAttrName().trim()+"_";
									comment += sysToolDictAttrEntity.getAttrComment()+"";
									columnFullName = sysToolDictAttrEntity.getAttrFullName()+" ";
									System.out.println(columnName+" 3个字分词格式 从定语表获取 == "+sysToolDictAttrEntity.getAttrName().trim());
									foundCount ++;
								}						}
							// 最后一个分词去词根表查
							else {
								SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(subWord);
								if(sysToolDictRootEntity != null)
								{
									columnName += sysToolDictRootEntity.getRootName().trim();
									comment += sysToolDictRootEntity.getComment();
									columnFullName = sysToolDictRootEntity.getRootFullName();
									dataType = sysToolDictRootEntity.getDataType();
									length = sysToolDictRootEntity.getLength();
									decimalLength = sysToolDictRootEntity.getDecimalLength();
									System.out.println(columnName+" 3个字分词格式 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
									foundCount ++;
								}
							} 
							System.out.println("====3个词 subWord2222===="+subWord);
						}
					}
					System.out.println("3个字分词查找， 共 "+wordCount+" 次，找到 "+foundCount+" 次");
					// 如果查找命中率小于0.5 采用后2个字分词倒推算法
					fountRate = (float) foundCount / wordCount; 
					if(fountRate > maxFountRate) {
						//存入临时中间变量
						columnNameTmp = columnName;
						commentTmp = comment;
						columnFullNameTmp = columnFullName;
						dataTypeTmp =  dataType;
						lengthTmp = length;
						decimalLengthTmp = decimalLength;
						 
						maxFountRate = fountRate;
					}
					if(fountRate < 1) {
						columnName = "";
						columnFullName = "";
						comment = "";
						dataType = "";
						length = 0;
						decimalLength = 0;
						foundCount = 0;
						foundCount = 0;
						System.out.println("====开始2个词倒推算法====");
						if(clength % 2 == 0){
							wordCount = clength / 2;
						}
						else { 
							wordCount = clength / 2 + 1;
						}
						for(int i = wordCount - 1; i >= 0; i--) {
							if(i*2+2 == clength){
								subWord = columnComment.substring(i*2,i*2+2);
								isRoot = true;
							}
							else if(i*2+1 == clength){
								if(clength == 1 ) {
									beginIndex = 0;
								}
								else {
									beginIndex = i*2-1;
								}
								subWord = columnComment.substring(beginIndex,i*2+1);
								isRoot = true;
							}
							else {
								if(i*2-2 < 0 ) {
									beginIndex = 0;
								}
								else {
									beginIndex = i*2-1;
								}
								subWord = columnComment.substring(beginIndex,i*2+1);
								isRoot = false;
							}
							// 用分词去定于表查询
							if(!isRoot) {
								SysToolDictAttrEntity sysToolDictAttrEntity = getDictAttr(subWord);
								if(sysToolDictAttrEntity != null)
								{
									columnName = sysToolDictAttrEntity.getAttrName().trim()+"_"+columnName;
									comment = sysToolDictAttrEntity.getAttrComment()+""+comment;
									columnFullName = sysToolDictAttrEntity.getAttrFullName()+" "+columnFullName;
									System.out.println(columnName+" 2个字倒序分词格式 从定语表获取 == "+sysToolDictAttrEntity.getAttrName().trim());
									foundCount ++;
								}						
							}
							// 最后一个分词去词根表查
							else {
								SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(subWord);
								if(sysToolDictRootEntity != null)
								{
									columnName = sysToolDictRootEntity.getRootName().trim();
									comment = sysToolDictRootEntity.getComment();
									columnFullName = sysToolDictRootEntity.getRootFullName();
									dataType = sysToolDictRootEntity.getDataType();
									length = sysToolDictRootEntity.getLength();
									decimalLength = sysToolDictRootEntity.getDecimalLength();
									System.out.println(columnName+" 2个字倒序分词格式 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
									foundCount ++;
								}
							} 
							
							System.out.println("====2个词倒推 subWord3333===="+subWord);
						}
					}
					System.out.println("2个字倒序分词查找， 共 "+wordCount+" 次，找到 "+foundCount+" 次");
					
					// 如果查找命中率小于0.5 采用后3个字分 词倒推算法
					fountRate = (float) foundCount / wordCount;
					if(fountRate > maxFountRate) {
						//存入临时中间变量
						columnNameTmp = columnName;
						commentTmp = comment;
						columnFullNameTmp = columnFullName;
						dataTypeTmp =  dataType;
						lengthTmp = length;
						decimalLengthTmp = decimalLength;
						 
						maxFountRate = fountRate;
					}
					if(fountRate < 1) {
						columnName = "";
						columnFullName = "";
						comment = "";
						dataType = "";
						length = 0;
						decimalLength = 0;
						foundCount = 0;
						System.out.println("====开始三个词倒推====");
						if(clength % 3 == 0){
							wordCount = clength / 3;
						}
						else { 
							wordCount = clength / 3 + 1;
						} 
						for(int i = wordCount - 1; i >= 0; i--) {
							if(i*3+3 == clength){
								subWord = columnComment.substring(i*3,i*3+3);
								isRoot = true;
							}
							else if(i*3+2 == clength){ 
								if(clength == 2 ) {
									beginIndex = 0;
								}
								else {
									beginIndex = i*3-1;
								}
								subWord = columnComment.substring(beginIndex,i*3+2);
								isRoot = true;
							}
							else if(i*3+1 == clength){
								if(clength == 1 ) {
									beginIndex = 0;
								}
								else {
									beginIndex = i*3-2;
								}
								subWord = columnComment.substring(beginIndex,i*3+1);
								isRoot = true;
							}
							else {
								if(i*3-3 < 0 ) {
									beginIndex = 0;
								}
								else {
									beginIndex = i*3-2;
								}
								if(clength % 3 == 1) {
									subWord = columnComment.substring(beginIndex,i*3+1);
								}
								else {
									subWord = columnComment.substring(beginIndex,i*3+2);
								}
								isRoot = false;
							}
							// 用分词去定于表查询
							if(!isRoot) {
								SysToolDictAttrEntity sysToolDictAttrEntity = getDictAttr(subWord);
								if(sysToolDictAttrEntity != null)
								{
									columnName = sysToolDictAttrEntity.getAttrName().trim()+"_"+columnName;
									comment = sysToolDictAttrEntity.getAttrComment()+""+comment;
									columnFullName = sysToolDictAttrEntity.getAttrFullName()+" "+columnFullName;
									System.out.println(columnName+" 3个字倒序分词格式 从定语表获取 == "+sysToolDictAttrEntity.getAttrName().trim());
									foundCount ++;
								}						
							}
							// 最后一个分词去词根表查
							else {
								SysToolDictRootEntity sysToolDictRootEntity = getDictRoot(subWord);
								if(sysToolDictRootEntity != null)
								{
									columnName = sysToolDictRootEntity.getRootName().trim();
									comment = sysToolDictRootEntity.getComment();
									columnFullName = sysToolDictRootEntity.getRootFullName();
									dataType = sysToolDictRootEntity.getDataType();
									length = sysToolDictRootEntity.getLength();
									decimalLength = sysToolDictRootEntity.getDecimalLength();
									System.out.println(columnName+" 3个字倒序分词格式 从词根表直接获取 == "+sysToolDictRootEntity.getRootName().trim());
									foundCount ++;
								}
							} 
							System.out.println("====3个词 subWord444===="+subWord);
						}
					}
					System.out.println("3个字倒序分词查找， 共 "+wordCount+" 次，找到 "+foundCount+" 次");
					// 如果命中率比之前的高，则赋值给临时中间变量
					fountRate =(float)foundCount / wordCount;
					System.out.println("fountRate = "+fountRate+" ；maxFountRate =  "+maxFountRate);
					if(fountRate > maxFountRate) {
						//存入临时中间变量
						columnNameTmp = columnName;
						commentTmp = comment;
						columnFullNameTmp = columnFullName;
						dataTypeTmp =  dataType;
						lengthTmp = length;
						decimalLengthTmp = decimalLength;
						maxFountRate = fountRate;
					}
					//如果匹配率低于1 查找相似变量
					if(maxFountRate < 1) {
						for(int i = wordCount - 1; i >= 0; i--) {
							subWord = columnComment.substring(0,i);
							// 直接用名字去变量库里查询，如果存在直接返回
							sysToolDictFieldEntity = getDictField(columnComment,"like");
							if(sysToolDictFieldEntity != null)
							{
								likeColumnComment = sysToolDictFieldEntity.getColumnComment();
								System.out.println(columnName+" 从变量表模糊获取 == "+sysToolDictFieldEntity.getColumnName());
								i = -1;
							}
						}
						for(int i = 0; i < wordCount; i++) {
							subWord = columnComment.substring(i,wordCount - 1);
							// 直接用名字去变量库里查询，如果存在直接返回
							sysToolDictFieldEntity = getDictField(columnComment,"like");
							if(sysToolDictFieldEntity != null)
							{
								if(!likeColumnComment.equals(sysToolDictFieldEntity.getColumnComment())) {
									likeColumnComment += " "+sysToolDictFieldEntity.getColumnComment();
								}
								comment = sysToolDictFieldEntity.getColumnComment();
								System.out.println(columnName+" 从变量表模糊获取 == "+sysToolDictFieldEntity.getColumnName());
								i = 99999;
							}
						}
					}
				}
				
				System.out.println("===== columnComment = "+ columnComment);
				System.out.println("===== likeColumnComment = "+ likeColumnComment);
				System.out.println("===== columnNameTmp = "+ columnNameTmp);
				System.out.println("=====  commentTmp = "+ commentTmp);
				System.out.println("=====  columnFullNameTmp = "+ columnFullNameTmp);
				System.out.println("=====  dataTypeTmp = "+ dataTypeTmp);
				System.out.println("=====  lengthTmp = "+ lengthTmp);
				System.out.println("=====  decimalLengthTmp = "+ decimalLengthTmp);
				JSONObject newObj= new JSONObject();
				newObj.put("dataType", dataTypeTmp);
				if(dataTypeTmp.equals("int") || dataTypeTmp.equals("bigint") || dataTypeTmp.equals("char")  || dataTypeTmp.equals("varchar")) {
					dataTypeTmp = dataTypeTmp +"("+lengthTmp+")";
				}
				else if(dataTypeTmp.equals("decimal")) {
					dataTypeTmp = dataTypeTmp +"("+lengthTmp+","+decimalLengthTmp+")";
				}
				if(dataTypeTmp.trim().equals("") && commentTmp.trim().equals("")) {
					foundFlag = "lostAttrRoot";
				}
				else if(dataTypeTmp.trim().equals("")) {
					foundFlag = "lostRoot";
				}
				else if(!columnComment.trim().replaceAll(" ", "").equals(commentTmp.trim())) {
					foundFlag = "lostAttr";
				}
				if(foundFlag.equals("root") || (foundFlag.equals("") && !dataTypeTmp.trim().equals(""))) {
					foundFlag = "find";
				}
				
				newObj.put("likeColumnComment", likeColumnComment);
				newObj.put("oldColumnComment", columnComment);
				newObj.put("columnComment", commentTmp);
				newObj.put("columnName", columnNameTmp);
				newObj.put("columnFullName", columnFullNameTmp);
				newObj.put("dataTypeFmt", dataTypeTmp);
				newObj.put("length", lengthTmp);
				newObj.put("decimalLength", decimalLengthTmp);
				newObj.put("foundFlag",foundFlag);
				returnList.add(newObj);
				// 每个两个字分词检索，最后一个词是词根
			}
		}

		commonMsg.getBody().setListBody(returnList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 
	 * 根据关键字查变量表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月31日 下午8:13:16
	 * @Param : columnComment 
	 * @Return : SysToolDictFieldEntity
	 */
	public SysToolDictFieldEntity getDictField(String columnComment,String type) {
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("columnComment");
		sqlConditionEntity.setType(type);
		sqlConditionEntity.setValue(columnComment);
		sqlConditionList.add(sqlConditionEntity);
		QueryWrapper<SysToolDictFieldEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictFieldEntity.class);				
		SysToolDictFieldEntity sysToolDictFieldEntity =  super.getOne(queryWrapper);
		return sysToolDictFieldEntity;
	}
	/**
	 * 
	 * 根据关键字查定语表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月31日 下午8:13:16
	 * @Param : columnComment 
	 * @Return : SysToolDictAttrEntity
	 */ 
	public SysToolDictAttrEntity getDictAttr(String columnComment) {
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("attrComment");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(columnComment);
		sqlConditionList.add(sqlConditionEntity);
		QueryWrapper<SysToolDictAttrEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictAttrEntity.class);				
		SysToolDictAttrEntity sysToolDictAttrEntity =  sysToolDictAttrMapper.selectOne(queryWrapper);
		return sysToolDictAttrEntity;
	}
	/**
	 * 
	 * 根据关键字查词根表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月31日 下午8:13:16
	 * @Param : columnComment 
	 * @Return : SysToolDictRootEntity
	 */ 
	public SysToolDictRootEntity getDictRoot(String columnComment) {
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("comment");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(columnComment);
		sqlConditionList.add(sqlConditionEntity);
		QueryWrapper<SysToolDictRootEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictRootEntity.class);				
		SysToolDictRootEntity sysToolDictFieldEntity =  sysToolDictRootMapper.selectOne(queryWrapper);
		return sysToolDictFieldEntity;
	}
	
	
	
	@Override
	public void exportSysToolDictFieldExcel(CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysToolDictFieldEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysToolDictFieldEntity.class);
		
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
		Page<SysToolDictFieldEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysToolDictFieldEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统数据标准变量表";
		String fileName = "系统数据标准变量表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("id");
		headList.add("租户ID");
		headList.add("字段名");
		headList.add("字段说明");
		headList.add("数据类型");
		headList.add("字段长度");
		headList.add("小数点长度");
		headList.add("字段全名");
		headList.add("申请系统");
		headList.add("申请应用");
		headList.add("申请人");
		excelData.add(headList);
		for (SysToolDictFieldEntity sysToolDictFieldEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysToolDictFieldEntity.getId());	    		
			lists.add(sysToolDictFieldEntity.getTenantId());	    		
			lists.add(sysToolDictFieldEntity.getColumnName());	    		
			lists.add(sysToolDictFieldEntity.getColumnComment());	    		
			lists.add(sysToolDictFieldEntity.getDataType());	    		
			lists.add(String.valueOf(sysToolDictFieldEntity.getLength()));	    		
			lists.add(String.valueOf(sysToolDictFieldEntity.getDecimalLength()));	    		
			lists.add(sysToolDictFieldEntity.getColumnFullName());	    		
			lists.add(sysToolDictFieldEntity.getApplySystem());	    		
			lists.add(sysToolDictFieldEntity.getApplyApp());	    		
			lists.add(sysToolDictFieldEntity.getApplyUserName());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
