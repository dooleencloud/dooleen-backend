package com.dooleen.service.biz.sixteen.user.info.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.app.system.user.role.entity.SysRoleEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.biz.sixteen.topic.db.entity.BizSixteenTopicDbEntity;
import com.dooleen.service.biz.sixteen.topic.db.mapper.BizSixteenTopicDbMapper;
import com.dooleen.service.biz.sixteen.user.answers.entity.BizSixteenUserAnswersEntity;
import com.dooleen.service.biz.sixteen.user.answers.mapper.BizSixteenUserAnswersMapper;
import com.dooleen.service.biz.sixteen.user.answers.service.BizSixteenUserAnswersService;
import com.dooleen.service.biz.sixteen.user.info.mapper.BizSixteenUserInfoMapper;
import com.dooleen.service.biz.sixteen.user.info.service.BizSixteenUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 22:00:25
 * @Description : 用户信息管理服务实现
 * @Author : apple
 * @Update: 2021-05-08 22:00:25
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizSixteenUserInfoServiceImpl extends ServiceImpl<BizSixteenUserInfoMapper, BizSixteenUserInfoEntity> implements BizSixteenUserInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  BizSixteenUserInfoMapper bizSixteenUserInfoMapper;
	
	@Autowired
	private BizSixteenUserAnswersService bizSixteenUserAnswersService;

	 @Autowired
	 private BizSixteenTopicDbMapper bizSixteenTopicDbMapper;

	 @Autowired
	 private BizSixteenUserAnswersMapper bizSixteenUserAnswersMapper;
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_SIXTEEN_USER_INFO("biz_sixteen_user_info", "BizSixteenUserInfo", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.BIZ_SIXTEEN_USER_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_SIXTEEN_USER_INFO.getEntityName();

	 private  static String asSeqPrefix= TableEntityORMEnum.BIZ_SIXTEEN_USER_ANSWERS.getSeqPrefix();
	 private  static String asTableName = TableEntityORMEnum.BIZ_SIXTEEN_USER_ANSWERS.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	 // 根据用户名获取用户信息
	 @Override
	 public CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> queryBizSixteenUserInfoByUserName(CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg) {
		 CommonUtil.service(commonMsg);
		 // 根据用户名查询用户信息
		 QueryWrapper<BizSixteenUserInfoEntity> queryWrapper = new QueryWrapper<BizSixteenUserInfoEntity>();
		 //如果openId不为空，使用openId登录
		 if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getWxOpenId())){
			 queryWrapper.lambda().eq(BizSixteenUserInfoEntity::getWxOpenId, commonMsg.getBody().getSingleBody().getWxOpenId());
		 }
		 //如果app openId不为空，使用app openId登录
		 else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getAppWxOpenId())){
			 queryWrapper.lambda().eq(BizSixteenUserInfoEntity::getAppWxOpenId, commonMsg.getBody().getSingleBody().getAppWxOpenId());
		 }
		 // 如果手机不为空，用手机号登录
		 else if(StringUtils.isNotEmpty(commonMsg.getBody().getSingleBody().getPhone())){
			 queryWrapper.lambda().eq(BizSixteenUserInfoEntity::getPhone, commonMsg.getBody().getSingleBody().getPhone());
		 }
		 // 否则用用户名登录
		 else {
			 queryWrapper.lambda().eq(BizSixteenUserInfoEntity::getUserName, commonMsg.getBody().getSingleBody().getUserName());
		 }
		 BizSixteenUserInfoEntity sysUserInfoEntity = bizSixteenUserInfoMapper.selectOne(queryWrapper);

		 // 用户信息不存在
		 BizResponseEnum.USER_INFO_NOT_EXIST_SIXTEEN.assertNotNull(sysUserInfoEntity,commonMsg);

		 // 用户已注销
		 BizResponseEnum.USER_HAS_LOG_OFF.assertEquals("1", sysUserInfoEntity.getValidFlag(),commonMsg);

		 //角色赋空值
		 List<SysRoleEntity> roleIdList = new ArrayList<SysRoleEntity>();

		 commonMsg.getBody().setSingleBody(sysUserInfoEntity);
		 commonMsg.getBody().setListBody(roleIdList);

		 //设置返回值
		 CommonUtil.successReturn(commonMsg);
		 return commonMsg;
	 }


	 @Override
	@Cacheable(value = "bizSixteenUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> queryBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizSixteenUserInfoEntity bizSixteenUserInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizSixteenUserInfoEntity,commonMsg);
		/** 不需要流程请自行删除  开始 */
		//判断是否需要查流程信息
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = generalFlowProcessRecordMapper.selectById(commonMsg.getBody().getFlowArea().getProcessRecordId());
			if(generalFlowProcessRecordEntity != null) {
				commonMsg.getBody().getFlowArea().setProcessRecordId(generalFlowProcessRecordEntity.getId());
				commonMsg.getBody().getFlowArea().setFlowExtData((JSONObject) JSON.parse(generalFlowProcessRecordEntity.getDataArea()));
				commonMsg.getBody().getFlowArea().setFlowEndFlag(generalFlowProcessRecordEntity.getFlowEndFlag());
			}
		}
		/** 不需要流程请自行删除  结束 */
			
		commonMsg.getBody().setSingleBody(bizSixteenUserInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizSixteenUserInfoEntity> queryBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizSixteenUserInfoEntity> bizSixteenUserInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizSixteenUserInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizSixteenUserInfoEntity, BizSixteenUserInfoEntity> queryBizSixteenUserInfoListPage(
			CommonMsg<BizSixteenUserInfoEntity, BizSixteenUserInfoEntity> commonMsg) {
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
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0") || entry.getValue().equals(0)))) {					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					if(entry.getKey().equals("realName") || entry.getKey().equals("orgName")){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<BizSixteenUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizSixteenUserInfoEntity.class);
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
		Page<BizSixteenUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizSixteenUserInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizSixteenUserInfoEntity, BizSixteenUserInfoEntity> queryBizSixteenUserInfoListMap(
			CommonMsg<BizSixteenUserInfoEntity, BizSixteenUserInfoEntity> commonMsg) {
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
		Collection<BizSixteenUserInfoEntity> bizSixteenUserInfoEntityList =  super.listByMap(conditionMap);
		List<BizSixteenUserInfoEntity> bizSixteenUserInfoMapList = new ArrayList<BizSixteenUserInfoEntity>(bizSixteenUserInfoEntityList);
		commonMsg.getBody().setListBody(bizSixteenUserInfoMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizSixteenUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> saveBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		//检查该用户手机号是否也注册
		QueryWrapper<BizSixteenUserInfoEntity>  userQueryWrapper = new QueryWrapper<BizSixteenUserInfoEntity>();
		userQueryWrapper.lambda().eq(BizSixteenUserInfoEntity::getTenantId, commonMsg.getBody().getSingleBody().getTenantId());
		userQueryWrapper.lambda().eq(BizSixteenUserInfoEntity::getUserName, commonMsg.getBody().getSingleBody().getUserName());
		BizSixteenUserInfoEntity bizSixteenUserInfoEntity= super.getOne(userQueryWrapper);
		BizResponseEnum.USER_HAS_EXIST_CHANGE_PHONE.assertIsNull(bizSixteenUserInfoEntity,commonMsg);

		commonMsg.getBody().getSingleBody().setRegTime(DateUtils.getLongDateStr());
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		// 新增成功为用户生成试卷；
		//1、读取题目数据
		QueryWrapper<BizSixteenTopicDbEntity>  queryWrapper = new QueryWrapper<BizSixteenTopicDbEntity>();
		List<BizSixteenTopicDbEntity> topicList = bizSixteenTopicDbMapper.selectList(queryWrapper);
		for(BizSixteenTopicDbEntity bizSixteenTopicDbEntity: topicList){
			BizSixteenUserAnswersEntity  bizSixteenUserAnswersEntity =new BizSixteenUserAnswersEntity();
			CopyFieldBeanUtil.copyProperties(commonMsg.getBody().getSingleBody(), bizSixteenUserAnswersEntity,true);
			CopyFieldBeanUtil.copyProperties(bizSixteenTopicDbEntity, bizSixteenUserAnswersEntity,true);
			//为了初始化用户answer列表，将ID值为空
			bizSixteenUserAnswersEntity.setId("");
			bizSixteenUserAnswersEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserAnswersEntity.toString(),  ConstantValue.md5Key));
			bizSixteenUserAnswersEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserAnswersEntity, commonMsg.getHead(),asTableName, asSeqPrefix, redisTemplate);
			int batchResult = bizSixteenUserAnswersMapper.insert(bizSixteenUserAnswersEntity);
			BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(batchResult == 1,commonMsg);
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizSixteenUserInfoEntity> saveBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg) {
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
	@CachePut(value = "bizSixteenUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> updateBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizSixteenUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<BizSixteenUserInfoEntity, NullEntity>();
		//定义singleBody
		BizSixteenUserInfoEntity singleBody = new BizSixteenUserInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizSixteenUserInfoEntity, NullEntity> mutBean= new MutBean<BizSixteenUserInfoEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizSixteenUserInfoServiceImpl.class).queryBizSixteenUserInfo(queryCommonMsg); 
		
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
	@CachePut(value = "bizSixteenUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> saveOrUpdateBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizSixteenUserInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<BizSixteenUserInfoEntity, NullEntity>();
		//定义singleBody
		BizSixteenUserInfoEntity singleBody = new BizSixteenUserInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(!StringUtil.isEmptyAndZero(commonMsg.getBody().getSingleBody().getId())){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
			//定义body
			MutBean<BizSixteenUserInfoEntity, NullEntity> mutBean= new MutBean<BizSixteenUserInfoEntity, NullEntity>();
			FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
			mutBean.setSingleBody(singleBody);
			mutBean.setFlowArea(flowArea);
			//深拷贝数据进行AOP查询
			queryCommonMsg.setHead(commonMsg.getHead());
			queryCommonMsg.setBody(mutBean);
			queryCommonMsg = SpringUtil.getBean(BizSixteenUserInfoServiceImpl.class).queryBizSixteenUserInfo(queryCommonMsg);

			//判断修改内容是否被其他用户修改
			if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
				return commonMsg;
			}
		}
		else {
			singleBody.setId("0");
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
	public CommonMsg<NullEntity, BizSixteenUserInfoEntity> saveOrUpdateBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg) {
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
    @CacheEvict(value = "bizSixteenUserInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> deleteBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizSixteenUserInfoEntity> deleteBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg) {
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
	public void exportBizSixteenUserInfoExcel(CommonMsg<BizSixteenUserInfoEntity, BizSixteenUserInfoEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<BizSixteenUserInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserInfoEntity.class);
		
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
		Page<BizSixteenUserInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizSixteenUserInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		Date start = new Date();
		String sheetName = "用户信息表";
		String fileName = "用户信息表-"+start.getTime();
		ExportParams params = new ExportParams();
		Workbook workbook = ExcelExportUtil.exportExcel(params, BizSixteenUserInfoEntity.class, commonMsg.getBody().getListBody());
		//导出Excel数据流
		try {
			ExcelUtil.exportPoi(response,workbook,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("====exportBizSixteenUserInfoExcel service end == " + RespStatus.json.getString("S0000"));
	}
	/**
	* 导入excel新增
	*/
	@Override
	public CommonMsg<BizSixteenUserInfoEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		CommonMsg<BizSixteenUserInfoEntity,NullEntity> commonMsg = new CommonMsg<BizSixteenUserInfoEntity,NullEntity>();
		HeadEntity head =new HeadEntity();
		commonMsg.setHead(head);
		commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
		commonMsg.getHead().setUserName(request.getHeader("username"));
		ImportParams params = new ImportParams();
		params.setTitleRows(0);
		List<BizSixteenUserInfoEntity> list = new ArrayList<>();
		try {
			commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
			list = ExcelImportUtil.importExcel(file.getInputStream(), BizSixteenUserInfoEntity.class, params);
		} catch (Exception e) {
			e.printStackTrace();
	    	map.put("E0002","导入出现异常！");
	    	commonMsg.getHead().setRespCode(RespStatus.errorCode);
	    	commonMsg.getHead().setRespMsg(map);
	    	return commonMsg;
		}
		//批量导入数据并做校验
		List<BizSixteenUserInfoEntity> tmpList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			BizSixteenUserInfoEntity bizSixteenUserInfoEntity = list.get(i);

			//检查字段的有效性
			ValidationResult validationResult = ValidationUtils.validateEntity(bizSixteenUserInfoEntity);
			boolean isError = validationResult.isHasErrors();
			// 检查前端传入字段
			if (isError == true) {
				String errStr = "";
				for(String value : validationResult.getErrorMsg().values()){
				errStr += value+";";
			}
				map.put("第"+i+"行",errStr);
			}
			else if(map.isEmpty()){
				bizSixteenUserInfoEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserInfoEntity.toString(),  ConstantValue.md5Key));
				// 初始化数据
				bizSixteenUserInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserInfoEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				tmpList.add(bizSixteenUserInfoEntity);
			}
		}
		if(!map.isEmpty()){
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			return commonMsg;
		}
		// 保存记录
		boolean result =  super.saveBatch(tmpList);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
