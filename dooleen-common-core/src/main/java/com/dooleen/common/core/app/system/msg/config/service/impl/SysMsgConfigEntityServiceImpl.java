package com.dooleen.common.core.app.system.msg.config.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.app.system.msg.config.mapper.SysMsgConfigEntityMapper;
import com.dooleen.common.core.app.system.msg.config.service.SysMsgConfigEntityService;
import com.dooleen.common.core.app.system.msg.config.vo.SysMsgSceneAndType;
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

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-13 19:12:18
 * @Description : 消息配置管理服务实现
 * @Author : apple
 * @Update: 2020-07-13 19:12:18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMsgConfigEntityServiceImpl extends ServiceImpl<SysMsgConfigEntityMapper, SysMsgConfigEntity> implements SysMsgConfigEntityService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private  SysMsgConfigEntityMapper sysMsgConfigEntityMapper;
	private  static String seqPrefix= TableEntityORMEnum.SYS_MSG_CONFIG.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_MSG_CONFIG.getEntityName();
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType")
	public CommonMsg<SysMsgConfigEntity, NullEntity> queryMessageConfigInfoByType(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
		QueryWrapper<SysMsgConfigEntity> wrapper = new QueryWrapper<SysMsgConfigEntity>();
		wrapper.lambda().eq(SysMsgConfigEntity::getTenantId,commonMsg.getBody().getSingleBody().getTenantId());
		wrapper.lambda().eq(SysMsgConfigEntity::getSendScene,commonMsg.getBody().getSingleBody().getSendScene());
		wrapper.lambda().eq(SysMsgConfigEntity::getMsgType,commonMsg.getBody().getSingleBody().getMsgType());
		SysMsgConfigEntity sysMsgConfigEntity = sysMsgConfigEntityMapper.selectOne(wrapper);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysMsgConfigEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysMsgConfigEntity);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@Cacheable(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType")
	public CommonMsg<SysMsgConfigEntity, NullEntity> queryMessageConfigInfo(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysMsgConfigEntity messageConfigInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

		commonMsg.getBody().setListBody(nullEntityList);

		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(messageConfigInfoEntity,commonMsg);

		commonMsg.getBody().setSingleBody(messageConfigInfoEntity);

		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysMsgConfigEntity> queryMessageConfigInfoList(CommonMsg<NullEntity, SysMsgConfigEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysMsgConfigEntity> messageConfigInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(messageConfigInfoEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysMsgConfigEntity, SysMsgConfigEntity> queryMessageConfigInfoListPage(
			CommonMsg<SysMsgConfigEntity, SysMsgConfigEntity> commonMsg) {
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
					sqlConditionEntity0.setType("=");
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<SysMsgConfigEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgConfigEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgConfigEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysMsgConfigEntity.class);
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
		Page<SysMsgConfigEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysMsgConfigEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysMsgConfigEntity, SysMsgConfigEntity> queryMessageConfigInfoListMap(
			CommonMsg<SysMsgConfigEntity, SysMsgConfigEntity> commonMsg) {
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
		Collection<SysMsgConfigEntity> messageConfigInfoEntityList =  super.listByMap(conditionMap);
		List<SysMsgConfigEntity> messageConfigInfoMapList = new ArrayList<SysMsgConfigEntity>(messageConfigInfoEntityList);
		commonMsg.getBody().setListBody(messageConfigInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMsgConfigEntity, NullEntity> saveMessageConfigInfo(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysMsgConfigEntity> saveMessageConfigInfoList(CommonMsg<NullEntity, SysMsgConfigEntity> commonMsg) {
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
	@CachePut(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMsgConfigEntity, NullEntity> updateMessageConfigInfo(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<SysMsgConfigEntity, NullEntity> queryCommonMsg = new CommonMsg<SysMsgConfigEntity, NullEntity>();

		//定义body
		MutBean<SysMsgConfigEntity, NullEntity> mutBean= new MutBean<SysMsgConfigEntity, NullEntity>();
		mutBean.setSingleBody(commonMsg.getBody().getSingleBody());
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysMsgConfigEntityServiceImpl.class).queryMessageConfigInfo(queryCommonMsg); 
		
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
	@CachePut(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysMsgConfigEntity, NullEntity> saveOrUpdateMessageConfigInfo(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		// 判断内容是否发生更改
		if (!commonMsg.getBody().getSingleBody().getId().equals("0") && !CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<SysMsgConfigEntity, NullEntity> queryCommonMsg = new CommonMsg<SysMsgConfigEntity, NullEntity>();
		//定义singleBody
		SysMsgConfigEntity singleBody = new SysMsgConfigEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysMsgConfigEntity, NullEntity> mutBean= new MutBean<SysMsgConfigEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysMsgConfigEntityServiceImpl.class).queryMessageConfigInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, SysMsgConfigEntity> saveOrUpdateMessageConfigInfoList(CommonMsg<NullEntity, SysMsgConfigEntity> commonMsg) {
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
    @CacheEvict(value = "messageConfigInfo", key = "#commonMsg.body.singleBody.tenantId+#commonMsg.body.singleBody.sendScene+#commonMsg.body.singleBody.msgType")
	public CommonMsg<SysMsgConfigEntity, NullEntity> deleteMessageConfigInfo(CommonMsg<SysMsgConfigEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysMsgConfigEntity> deleteMessageConfigInfoList(CommonMsg<NullEntity, SysMsgConfigEntity> commonMsg) {
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
	public void exportMessageConfigInfoExcel(CommonMsg<SysMsgConfigEntity, SysMsgConfigEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysMsgConfigEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgConfigEntity.class);
		
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
		Page<SysMsgConfigEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysMsgConfigEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "消息配置表";
		String fileName = "消息配置表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("发送场景");
		headList.add("消息类型");
		headList.add("消息模板内容");
		headList.add("消息模板示例内容");
		excelData.add(headList);
		for (SysMsgConfigEntity messageConfigInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(messageConfigInfoEntity.getId());	    		
			lists.add(messageConfigInfoEntity.getTenantId());	    		
			lists.add(messageConfigInfoEntity.getSendScene());	    		
			lists.add(messageConfigInfoEntity.getMsgType());	    		
			lists.add(messageConfigInfoEntity.getMsgTemplateContent());	    		
			lists.add(messageConfigInfoEntity.getMsgTemplateEgContent());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}

	@Override
	public CommonMsg<SysMsgSceneAndType, NullEntity> querySceneAndType(CommonMsg<SysMsgSceneAndType, NullEntity> commonMsg) {
				
//		// 发送场景
//		List<Map<String, String>> MsgSendSceneList = new ArrayList<Map<String,String>>();
//		Map<String, String> sceneMap = null;
//		
//		// 消息类型
//		List<Map<String, String>> MsgTypeList = new ArrayList<Map<String,String>>();
//		Map<String, String> typeMap = null;
//		
//		// 处理发送场景
//		MsgSendSenceEnum[] sendSences = MsgSendSenceEnum.values();
//		for (MsgSendSenceEnum scene : sendSences) {
//			sceneMap = new HashMap<String, String>();
//			sceneMap.put("label", scene.getName());
//			sceneMap.put("value", scene.getId());
//			MsgSendSceneList.add(sceneMap);
//		}
//		
//		// 处理消息类型
//		MsgTypeEnum[] msgTypes = MsgTypeEnum.values();
//		for (MsgTypeEnum msgType : msgTypes) {
//			typeMap = new HashMap<String, String>();
//			typeMap.put("label", msgType.getLabel());
//			typeMap.put("value", msgType.getValue());
//			MsgTypeList.add(typeMap);
//		}
//		
//		SysMsgSceneAndType sysMsgSceneAndType = new SysMsgSceneAndType();
//		sysMsgSceneAndType.setMsgSendSceneList(MsgSendSceneList);
//		sysMsgSceneAndType.setMsgTypeList(MsgTypeList);
//		
//		commonMsg.getBody().setSingleBody(sysMsgSceneAndType);
//		
//		//设置返回值
//		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public List<SysMsgConfigEntity> queryByScene(String tenantId, String sendSence) {
		return sysMsgConfigEntityMapper.selectByScene(tenantId, sendSence);
	}
}
