package com.dooleen.service.system.custom.user.info.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.app.system.custom.user.mapper.SysCustomUserInfoMapper;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import org.apache.commons.lang3.StringUtils;
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
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.system.custom.user.info.service.SysCustomUserInfoService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.CommonUtil;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-21 16:40:24
 * @Description : 系统客户用户表服务实现
 * @Author : apple
 * @Update: 2021-02-21 16:40:24
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysCustomUserInfoServiceImpl extends ServiceImpl<SysCustomUserInfoMapper, SysCustomUserInfoEntity> implements SysCustomUserInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  SysCustomUserInfoMapper sysCustomUserInfoMapper;
	
	@Autowired
	private  GetBizParamsService getBizParamsService;
	
	
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//SYS_CUSTOM_USER_INFO("sys_custom_user_info", "SysCustomUserInfo", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.SYS_CUSTOM_USER_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_CUSTOM_USER_INFO.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	 // 根据用户名获取用户信息
	 @Override
	 public CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> querySysCustomUserInfoByUserName(CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> commonMsg) {
		 CommonUtil.service(commonMsg);
		 // 根据用户名查询用户信息
		 QueryWrapper<SysCustomUserInfoEntity> queryWrapper = new QueryWrapper<SysCustomUserInfoEntity>();
		 //如果openId不为空，使用openId登录
		 if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getWxOpenId())){
			 queryWrapper.lambda().eq(SysCustomUserInfoEntity::getWxOpenId, commonMsg.getBody().getSingleBody().getWxOpenId());
		 }
		 //如果app openId不为空，使用app openId登录
		 else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getAppWxOpenId())){
			 queryWrapper.lambda().eq(SysCustomUserInfoEntity::getAppWxOpenId, commonMsg.getBody().getSingleBody().getAppWxOpenId());
		 }
		 // 如果手机不为空，用手机号登录
		 else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getMobileNo())){
			 queryWrapper.lambda().eq(SysCustomUserInfoEntity::getMobileNo, commonMsg.getBody().getSingleBody().getMobileNo());
		 }
		 // 否则用用户名登录
		 else {
			 queryWrapper.lambda().eq(SysCustomUserInfoEntity::getUserName, commonMsg.getBody().getSingleBody().getUserName());
		 }
		 SysCustomUserInfoEntity sysUserInfoEntity = sysCustomUserInfoMapper.selectOne(queryWrapper);

		 // 用户信息不存在
		 BizResponseEnum.USER_INFO_NOT_EXIST.assertNotNull(sysUserInfoEntity,commonMsg);

		 // 用户已注销
		 BizResponseEnum.USER_HAS_LOG_OFF.assertEquals("1", sysUserInfoEntity.getValidFlag());

		 //角色赋空值
		 List<SysRoleEntity> roleIdList = new ArrayList<SysRoleEntity>();

		 commonMsg.getBody().setSingleBody(sysUserInfoEntity);
		 commonMsg.getBody().setListBody(roleIdList);

		 //设置返回值
		 CommonUtil.successReturn(commonMsg);
		 return commonMsg;
	 }
	
	@Override
	@Cacheable(value = "sysCustomUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<SysCustomUserInfoEntity, NullEntity> querySysCustomUserInfo(CommonMsg<SysCustomUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysCustomUserInfoEntity sysCustomUserInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysCustomUserInfoEntity,commonMsg);
			
		commonMsg.getBody().setSingleBody(sysCustomUserInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysCustomUserInfoEntity> querySysCustomUserInfoList(CommonMsg<NullEntity, SysCustomUserInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysCustomUserInfoEntity> sysCustomUserInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysCustomUserInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysCustomUserInfoEntity, SysCustomUserInfoEntity> querySysCustomUserInfoListPage(
			CommonMsg<SysCustomUserInfoEntity, SysCustomUserInfoEntity> commonMsg) {
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
			if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString()))
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
		QueryWrapper<SysCustomUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCustomUserInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCustomUserInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysCustomUserInfoEntity.class);
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
		Page<SysCustomUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysCustomUserInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysCustomUserInfoEntity, SysCustomUserInfoEntity> querySysCustomUserInfoListMap(
			CommonMsg<SysCustomUserInfoEntity, SysCustomUserInfoEntity> commonMsg) {
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
		Collection<SysCustomUserInfoEntity> sysCustomUserInfoEntityList =  super.listByMap(conditionMap);
		List<SysCustomUserInfoEntity> sysCustomUserInfoMapList = new ArrayList<SysCustomUserInfoEntity>(sysCustomUserInfoEntityList);
		commonMsg.getBody().setListBody(sysCustomUserInfoMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysCustomUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCustomUserInfoEntity, NullEntity> saveSysCustomUserInfo(CommonMsg<SysCustomUserInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysCustomUserInfoEntity> saveSysCustomUserInfoList(CommonMsg<NullEntity, SysCustomUserInfoEntity> commonMsg) {
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
	@CachePut(value = "sysCustomUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCustomUserInfoEntity, NullEntity> updateSysCustomUserInfo(CommonMsg<SysCustomUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysCustomUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysCustomUserInfoEntity, NullEntity>();
		//定义singleBody
		SysCustomUserInfoEntity singleBody = new SysCustomUserInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysCustomUserInfoEntity, NullEntity> mutBean= new MutBean<SysCustomUserInfoEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysCustomUserInfoServiceImpl.class).querySysCustomUserInfo(queryCommonMsg); 
		
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
	@CachePut(value = "sysCustomUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCustomUserInfoEntity, NullEntity> saveOrUpdateSysCustomUserInfo(CommonMsg<SysCustomUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysCustomUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysCustomUserInfoEntity, NullEntity>();
		//定义singleBody
		SysCustomUserInfoEntity singleBody = new SysCustomUserInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysCustomUserInfoEntity, NullEntity> mutBean= new MutBean<SysCustomUserInfoEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysCustomUserInfoServiceImpl.class).querySysCustomUserInfo(queryCommonMsg); 
		
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
		// 保存失败
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, SysCustomUserInfoEntity> saveOrUpdateSysCustomUserInfoList(CommonMsg<NullEntity, SysCustomUserInfoEntity> commonMsg) {
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
    @CacheEvict(value = "sysCustomUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<SysCustomUserInfoEntity, NullEntity> deleteSysCustomUserInfo(CommonMsg<SysCustomUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysCustomUserInfoEntity> deleteSysCustomUserInfoList(CommonMsg<NullEntity, SysCustomUserInfoEntity> commonMsg) {
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
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysCustomUserInfoExcel(CommonMsg<SysCustomUserInfoEntity, SysCustomUserInfoEntity> commonMsg, HttpServletResponse response) {
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
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
		}
		
		QueryWrapper<SysCustomUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCustomUserInfoEntity.class);
		
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
		Page<SysCustomUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysCustomUserInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "系统客户用户表";
		String fileName = "系统客户用户表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("用户名");
		headList.add("微信openID");
		headList.add("APP微信openID");
		headList.add("支付宝openID");
		headList.add("APP支付宝openID");
		headList.add("其它openID");
		headList.add("APP其它openID");
		headList.add("密码");
		headList.add("统一信用代码");
		headList.add("机构名称");
		headList.add("营业执照链接");
		headList.add("法人姓名");
		headList.add("法人身份证号");
		headList.add("法人身份证链接");
		headList.add("注册地址");
		headList.add("联系人姓名");
		headList.add("联系人身份证号");
		headList.add("联系人身份证链接");
		headList.add("所属区域代码");
		headList.add("所属区域名称");
		headList.add("固定电话号码");
		headList.add("手机号码");
		headList.add("邮箱");
		headList.add("传真号码");
		headList.add("激活手机号");
		headList.add("企业有效期");
		headList.add("密码错误次数");
		headList.add("注册渠道");
		headList.add("头像链接");
		excelData.add(headList);
		for (SysCustomUserInfoEntity sysCustomUserInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysCustomUserInfoEntity.getUserName());	    		
			lists.add(sysCustomUserInfoEntity.getWxOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getAppWxOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getAlipayOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getAppAlipayOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getOtherOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getAppOtherOpenId());	    		
			lists.add(sysCustomUserInfoEntity.getPassword());	    		
			lists.add(sysCustomUserInfoEntity.getUnifiedCreditDcode());	    		
			lists.add(sysCustomUserInfoEntity.getOrgName());	    		
			lists.add(sysCustomUserInfoEntity.getBusinessLicenseUrl());	    		
			lists.add(sysCustomUserInfoEntity.getLegalPersonName());	    		
			lists.add(sysCustomUserInfoEntity.getLegalPersonIdCardNo());	    		
			lists.add(sysCustomUserInfoEntity.getLegalPersonIdCardUrl());	    		
			lists.add(sysCustomUserInfoEntity.getRegistAddress());	    		
			lists.add(sysCustomUserInfoEntity.getContactPersonName());	    		
			lists.add(sysCustomUserInfoEntity.getContactPersonIdCardNo());	    		
			lists.add(sysCustomUserInfoEntity.getContactPersonIdCardUrl());	    		
			lists.add(sysCustomUserInfoEntity.getBelongAreaDcode());	    		
			lists.add(sysCustomUserInfoEntity.getBelongAreaName());	    		
			lists.add(sysCustomUserInfoEntity.getFixedPhoneNo());	    		
			lists.add(sysCustomUserInfoEntity.getMobileNo());	    		
			lists.add(sysCustomUserInfoEntity.getEmail());	    		
			lists.add(sysCustomUserInfoEntity.getFaxNo());	    		
			lists.add(sysCustomUserInfoEntity.getActiveMobileNo());	    		
			lists.add(sysCustomUserInfoEntity.getCompanyValidDate());	    		
			lists.add(String.valueOf(sysCustomUserInfoEntity.getPasswordErrorTimes()));	    		
			lists.add(sysCustomUserInfoEntity.getRegistChannel());	    		
			lists.add(sysCustomUserInfoEntity.getHeadImgUrl());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportSysCustomUserInfoExcel service end == " + RespStatus.json.getString("S0000"));
	}
}
