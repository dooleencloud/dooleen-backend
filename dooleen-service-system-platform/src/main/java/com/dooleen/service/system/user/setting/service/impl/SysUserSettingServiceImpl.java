package com.dooleen.service.system.user.setting.service.impl;

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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.info.mapper.SysTenantInfoMapper;
import com.dooleen.service.system.user.setting.entity.SysUserSettingEntity;
import com.dooleen.service.system.user.setting.mapper.SysUserSettingMapper;
import com.dooleen.service.system.user.setting.service.SysUserSettingService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-17 15:20:53
 * @Description : 系统用户设置管理服务实现
 * @Author : apple
 * @Update: 2020-06-17 15:20:53
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserSettingServiceImpl extends ServiceImpl<SysUserSettingMapper, SysUserSettingEntity> implements SysUserSettingService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    public static final String SUPER_TENANT_ID = "DOOL1001";

	@Autowired
	private  SysUserSettingMapper sysUserSettingMapper;

	@Autowired
	private  SysTenantInfoMapper sysTenantInfoMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_USER_SETTING.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_USER_SETTING.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysUserSetting", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysUserSettingEntity, NullEntity> querySysUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysUserSettingEntity sysUserSettingEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       	commonMsg.getBody().setListBody(nullEntityList);
       	commonMsg.getBody().setSingleBody(sysUserSettingEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 获取租户及用户设置
	 */
	@Override
	public CommonMsg<SysUserSettingEntity, NullEntity> querySysTenantUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();

		//定义singleBody
		SysUserSettingEntity sysUserSettingEntity = new SysUserSettingEntity();
		sysUserSettingEntity.setId(commonMsg.getBody().getSingleBody().getId());
		// 根据Key值查询
		sysUserSettingEntity = super.getById(sysUserSettingEntity.getId());
		
		//如果不存在，写入一条用户设置数据
		if (sysUserSettingEntity == null) {
			log.info("=====>>>22222 {}", commonMsg.getBody().getSingleBody().toString());
			sysUserSettingEntity = commonMsg.getBody().getSingleBody();
			sysUserSettingEntity.setSystemVersionNo("");
			sysUserSettingEntity.setTopMenuVersionNo("");
			sysUserSettingEntity.setMenuVersionNo("");
			sysUserSettingEntity.setResetNo("");
			sysUserSettingEntity.setMsgNo("");
			sysUserSettingEntity.setNoticeMsg("");
			sysUserSettingEntity.setValidFlag("1");
			sysUserSettingEntity.setSelfDefMenuContent("");
			sysUserSettingEntity.setSelfDefNaviContent("");
			sysUserSettingEntity.setPersonalSettingContent("");
			sysUserSettingEntity.setPersonalSettingOneContent("");
			sysUserSettingEntity.setPersonalSettingTwoContent("");
			//设置需要修改的签名
			String bodySign = DooleenMD5Util.md5(sysUserSettingEntity.toString(),  ConstantValue.md5Key);
			sysUserSettingEntity.setDataSign(bodySign);
			// 保存数据
			boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysUserSettingEntity,
					commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}  
		sysUserSettingEntity.setPersonalSettingContent("");
		sysUserSettingEntity.setPersonalSettingOneContent("");
		sysUserSettingEntity.setPersonalSettingTwoContent("");
		// 查询租户设置信息
		SysTenantInfoEntity sysTenantInfoEntity = new SysTenantInfoEntity();
		sysTenantInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		QueryWrapper<SysTenantInfoEntity> queryWrapper = new QueryWrapper<SysTenantInfoEntity>();
		queryWrapper.lambda().eq(SysTenantInfoEntity::getTenantId, sysTenantInfoEntity.getTenantId());
		sysTenantInfoEntity = sysTenantInfoMapper.selectOne(queryWrapper);
		if (sysTenantInfoEntity != null) { 
			String systemVersionNo = sysTenantInfoEntity.getSystemVersionNo();
			String topMenuVersionNo = sysTenantInfoEntity.getTopMenuVersionNo();
			String menuVersionNo = sysTenantInfoEntity.getMenuVersionNo();
			String resetNo = sysTenantInfoEntity.getResetNo();
			String msgNo = sysTenantInfoEntity.getMsgNo();
			String noticeMsg = sysTenantInfoEntity.getNoticeMsg();
			String validFlag = sysTenantInfoEntity.getValidFlag();
			if(systemVersionNo.compareTo(sysUserSettingEntity.getSystemVersionNo()) > 0) {
				sysUserSettingEntity.setSystemVersionNo(systemVersionNo);
			}
			if(topMenuVersionNo.compareTo(sysUserSettingEntity.getTopMenuVersionNo()) > 0) {
				sysUserSettingEntity.setTopMenuVersionNo(topMenuVersionNo);
			}
			if(menuVersionNo.compareTo(sysUserSettingEntity.getMenuVersionNo()) > 0) {
				sysUserSettingEntity.setMenuVersionNo(menuVersionNo);
			}
			if(resetNo.compareTo(sysUserSettingEntity.getResetNo()) > 0) {
				sysUserSettingEntity.setResetNo(resetNo);
			}
			if(msgNo.compareTo(sysUserSettingEntity.getMsgNo()) > 0) {
				sysUserSettingEntity.setMsgNo(msgNo);
				sysUserSettingEntity.setNoticeMsg(noticeMsg);
			}
			if(validFlag.compareTo(sysUserSettingEntity.getValidFlag()) < 0) {
				sysUserSettingEntity.setValidFlag(validFlag); 
			}
			sysUserSettingEntity.setClientId(sysTenantInfoEntity.getClientId());
			sysUserSettingEntity.setCustSecretKey(sysTenantInfoEntity.getCustSecretKey());
		}		
		commonMsg.getBody().setSingleBody(sysUserSettingEntity);
		commonMsg.getBody().setListBody(nullEntityList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	@Override
	public CommonMsg<NullEntity, SysUserSettingEntity> querySysUserSettingList(CommonMsg<NullEntity, SysUserSettingEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysUserSettingEntity> sysUserSettingEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysUserSettingEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysUserSettingEntity, SysUserSettingEntity> querySysUserSettingListPage(
			CommonMsg<SysUserSettingEntity, SysUserSettingEntity> commonMsg) {
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
		QueryWrapper<SysUserSettingEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserSettingEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserSettingEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysUserSettingEntity.class);
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
		Page<SysUserSettingEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysUserSettingEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<SysUserSettingEntity, SysUserSettingEntity> querySysUserSettingListMap(
			CommonMsg<SysUserSettingEntity, SysUserSettingEntity> commonMsg) {
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
		Collection<SysUserSettingEntity> sysUserSettingEntityList =  super.listByMap(conditionMap);
		List<SysUserSettingEntity> sysUserSettingMapList = new ArrayList<SysUserSettingEntity>(sysUserSettingEntityList);
		commonMsg.getBody().setListBody(sysUserSettingMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysUserSetting", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserSettingEntity, NullEntity> saveSysUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysUserSettingEntity> saveSysUserSettingList(CommonMsg<NullEntity, SysUserSettingEntity> commonMsg) {
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
	@CachePut(value = "sysUserSetting", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserSettingEntity, NullEntity> updateSysUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysUserSettingEntity, NullEntity> queryCommonMsg = new CommonMsg<SysUserSettingEntity, NullEntity>();
		//定义singleBody
		SysUserSettingEntity singleBody = new SysUserSettingEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysUserSettingEntity, NullEntity> mutBean= new MutBean<SysUserSettingEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysUserSettingServiceImpl.class).querySysUserSetting(queryCommonMsg); 
		
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
	@CachePut(value = "sysUserSetting", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysUserSettingEntity, NullEntity> saveOrUpdateSysUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysUserSettingEntity, NullEntity> queryCommonMsg = new CommonMsg<SysUserSettingEntity, NullEntity>();
		//定义singleBody
		SysUserSettingEntity singleBody = new SysUserSettingEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysUserSettingEntity, NullEntity> mutBean= new MutBean<SysUserSettingEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysUserSettingServiceImpl.class).querySysUserSetting(queryCommonMsg); 
		// 如果记录存在，设置用户同步记录
		if(queryCommonMsg.getHead().getRespCode().equals(RespStatus.succCode)) {
			commonMsg.getBody().getSingleBody().setPersonalSettingTwoContent(queryCommonMsg.getBody().getSingleBody().getPersonalSettingOneContent());
			commonMsg.getBody().getSingleBody().setPersonalSettingOneContent(queryCommonMsg.getBody().getSingleBody().getPersonalSettingContent());
			commonMsg.getBody().getSingleBody().setPersonalSettingContent(commonMsg.getBody().getSingleBody().getPersonalSettingContent());
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
	public CommonMsg<NullEntity, SysUserSettingEntity> saveOrUpdateSysUserSettingList(CommonMsg<NullEntity, SysUserSettingEntity> commonMsg) {
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
    @CacheEvict(value = "sysUserSetting", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysUserSettingEntity, NullEntity> deleteSysUserSetting(CommonMsg<SysUserSettingEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysUserSettingEntity> deleteSysUserSettingList(CommonMsg<NullEntity, SysUserSettingEntity> commonMsg) {
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
	public void exportSysUserSettingExcel(CommonMsg<SysUserSettingEntity, SysUserSettingEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
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

		QueryWrapper<SysUserSettingEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysUserSettingEntity.class);
		
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
		Page<SysUserSettingEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysUserSettingEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统用户设置表";
		String fileName = "系统用户设置表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("用户名");
		headList.add("真实姓名");
		headList.add("系统版本号");
		headList.add("顶部菜单版本号");
		headList.add("菜单版本号");
		headList.add("重置编号");
		headList.add("通知消息");
		headList.add("个人设置内容");
		headList.add("个人设置内容1");
		headList.add("个人设置内容2");
		excelData.add(headList);
		for (SysUserSettingEntity sysUserSettingEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysUserSettingEntity.getId());	    		
			lists.add(sysUserSettingEntity.getTenantId());	    		
			lists.add(sysUserSettingEntity.getUserName());	    		
			lists.add(sysUserSettingEntity.getRealName());	    		
			lists.add(sysUserSettingEntity.getSystemVersionNo());	    		
			lists.add(sysUserSettingEntity.getTopMenuVersionNo());	    		
			lists.add(sysUserSettingEntity.getMenuVersionNo());	    		
			lists.add(sysUserSettingEntity.getResetNo());	    		
			lists.add(sysUserSettingEntity.getNoticeMsg());	    		
			lists.add(sysUserSettingEntity.getPersonalSettingContent());	    		
			lists.add(sysUserSettingEntity.getPersonalSettingOneContent());	    		
			lists.add(sysUserSettingEntity.getPersonalSettingTwoContent());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
