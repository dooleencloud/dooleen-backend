package com.dooleen.service.general.conference.info.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.param.entity.GeneralBizParamEntity;
import com.dooleen.common.core.app.general.param.mapper.GeneralBizParamMapper;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.app.system.third.mapper.SysThirdPartyInfoMapper;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.calendar.info.entity.GeneralCalendarInfoEntity;
import com.dooleen.service.general.calendar.info.mapper.GeneralCalendarInfoMapper;
import com.dooleen.service.general.schedule.entity.GeneralScheduleInfoEntity;
import com.dooleen.service.general.schedule.mapper.GeneralScheduleInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.general.conference.info.entity.GeneralConferenceInfoEntity;
import com.dooleen.service.general.conference.info.mapper.GeneralConferenceInfoMapper;
import com.dooleen.service.general.conference.info.service.GeneralConferenceInfoService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.ValidationResult;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-26 14:09:14
 * @Description : 会议信息表服务实现
 * @Author : apple
 * @Update: 2021-04-26 14:09:14
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralConferenceInfoServiceImpl extends ServiceImpl<GeneralConferenceInfoMapper, GeneralConferenceInfoEntity> implements GeneralConferenceInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  GeneralConferenceInfoMapper generalConferenceInfoMapper;

	 @Autowired
	 private GeneralBizParamMapper generalBizParamMapper;

	 @Autowired
	 private GeneralScheduleInfoMapper generalScheduleInfoMapper;

	 @Autowired
	 private GeneralCalendarInfoMapper generalCalendarInfoMapper;

	@Autowired
	private SysThirdPartyInfoMapper sysThirdPartyInfoMapper;

	 private  static String scheduleSeqPrefix= TableEntityORMEnum.GENERAL_SCHEDULE_INFO.getSeqPrefix();
	 private  static String scheduleTableName = TableEntityORMEnum.GENERAL_SCHEDULE_INFO.getEntityName();

	private  static String seqPrefix= TableEntityORMEnum.GENERAL_CONFERENCE_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_CONFERENCE_INFO.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	 @Autowired
	 private MsgSendUtil msgSendUtil;
	@Override
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryConferenceDuration(
			 CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		 CommonUtil.service(commonMsg);
		 GeneralConferenceInfoEntity singleBody = commonMsg.getBody().getSingleBody();
		 // 租户ID
		 String tenantId = commonMsg.getHead().getTenantId();

		 String conferenceRoomId = singleBody.getConferenceRoomId();

		 // 开始时间
		 String startTime = singleBody.getConferenceBeginDatetime();
		 // 结束时间
		 String endTime = singleBody.getConferenceEndDatetime();
		 List<GeneralConferenceInfoEntity> conferenceList = generalConferenceInfoMapper.selectConferenceDuration(tenantId, startTime, endTime,conferenceRoomId);
		 commonMsg.getBody().setListBody(conferenceList);
		 //设置返回值
		 CommonUtil.successReturn(commonMsg);
		 return commonMsg;
	 }

	@Override
	@Cacheable(value = "generalConferenceInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> queryGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralConferenceInfoEntity generalConferenceInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalConferenceInfoEntity,commonMsg);
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
			
		commonMsg.getBody().setSingleBody(generalConferenceInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralConferenceInfoEntity> generalConferenceInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalConferenceInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoListPage(
			CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
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
		QueryWrapper<GeneralConferenceInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralConferenceInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralConferenceInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, GeneralConferenceInfoEntity.class);
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
		Page<GeneralConferenceInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralConferenceInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoListMap(
			CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
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
		Collection<GeneralConferenceInfoEntity> generalConferenceInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralConferenceInfoEntity> generalConferenceInfoMapList = new ArrayList<GeneralConferenceInfoEntity>(generalConferenceInfoEntityList);
		commonMsg.getBody().setListBody(generalConferenceInfoMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalConferenceInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}

		//noticeUserList.add(commonMsg.getBody().getSingleBody().getConvenerUserName());

		//检查会议室冲突
		// 租户ID
		String tenantId = commonMsg.getHead().getTenantId();
		String conferenceType = commonMsg.getBody().getSingleBody().getConferenceType();
		String conferenceRoomId = commonMsg.getBody().getSingleBody().getConferenceRoomId();
		// 开始时间
		String startTime = commonMsg.getBody().getSingleBody().getConferenceBeginDatetime();
		// 结束时间
		String endTime = commonMsg.getBody().getSingleBody().getConferenceEndDatetime();
		//非线上会议检查会议室冲突
		if(!conferenceRoomId.equals("online")) {
			int cont = generalConferenceInfoMapper.selectConferenceConflict(tenantId, startTime, endTime, conferenceType, conferenceRoomId);
			BizResponseEnum.CONFERENCE_OCCUPIED.assertIsTrue(cont == 0,commonMsg);
		}

		//检查参会人员时间冲突
		Map<String, String> conflictMap = new HashMap<>();
		//获取参会人员列表含召集人
		JSONArray jsonArray = JSON.parseArray(commonMsg.getBody().getSingleBody().getParticipantsList());
		//获取时间段会议列表
		List<GeneralConferenceInfoEntity>  generalConferenceInfoEntityList= generalConferenceInfoMapper.selectConferenceUserConflict(tenantId, startTime, endTime);
		//检查人员冲突
		for(GeneralConferenceInfoEntity generalConferenceInfoEntity: generalConferenceInfoEntityList){
			if(generalConferenceInfoEntity.getParticipantsList().indexOf(commonMsg.getBody().getSingleBody().getConvenerRealName()+"("+commonMsg.getBody().getSingleBody().getConvenerUserName()+")") >= 0){
				conflictMap.put(commonMsg.getBody().getSingleBody().getConvenerRealName()+"("+commonMsg.getBody().getSingleBody().getConvenerUserName()+")","会议时间："+generalConferenceInfoEntity.getConferenceBeginDatetime()+"-"+generalConferenceInfoEntity.getConferenceEndDatetime());
			}
			for(int i=0; i < jsonArray.size(); i++){
				if(generalConferenceInfoEntity.getParticipantsList().indexOf(jsonArray.getString(i)) >= 0){
					conflictMap.put(jsonArray.getString(i),"会议时间冲突（"+generalConferenceInfoEntity.getConferenceBeginDatetime()+"-"+generalConferenceInfoEntity.getConferenceEndDatetime()+")");
				}
			}
		}
		//检查参数是否强制提示人员冲突
		QueryWrapper<GeneralBizParamEntity> generalBizParamEntityWrapper = new QueryWrapper<GeneralBizParamEntity>();
		generalBizParamEntityWrapper.lambda().eq(GeneralBizParamEntity::getTenantId, commonMsg.getHead().getTenantId());
		generalBizParamEntityWrapper.lambda().eq(GeneralBizParamEntity::getParamKey, "conflictNotice");
		GeneralBizParamEntity generalBizParamEntity =  generalBizParamMapper.selectOne(generalBizParamEntityWrapper);
		log.debug("==============conflictUser:"+conflictMap);
		if(conflictMap.size() > 0  && generalBizParamEntity.getParamValue().equals("true")){
			Map<String, String> map = new HashMap<String, String>();
			map = conflictMap;
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			return commonMsg;
		}
		else{
			commonMsg.getBody().getSingleBody().setConflictMsg(conflictMap.toString());
		}
		//数据签名
		GeneralConferenceInfoEntity generalConferenceInfoEntity = commonMsg.getBody().getSingleBody();
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(generalConferenceInfoEntity.toString(),  ConstantValue.md5Key));
		// 执行保存
		generalConferenceInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalConferenceInfoEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
		boolean result =  super.save(generalConferenceInfoEntity);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);

		//同步将会议写入对应人员日程
		GeneralScheduleInfoEntity generalScheduleInfoEntity = new GeneralScheduleInfoEntity();
		generalScheduleInfoEntity.setScheduleSubject(commonMsg.getBody().getSingleBody().getConferenceSubject());
		generalScheduleInfoEntity.setScheduleContent(commonMsg.getBody().getSingleBody().getConferenceIssue());
		generalScheduleInfoEntity.setScheduleBeginDatetime(commonMsg.getBody().getSingleBody().getConferenceBeginDatetime());
		generalScheduleInfoEntity.setScheduleEndDatetime(commonMsg.getBody().getSingleBody().getConferenceEndDatetime());
		generalScheduleInfoEntity.setColor(commonMsg.getBody().getSingleBody().getColor());
		generalScheduleInfoEntity.setAllDayShowFlag(commonMsg.getBody().getSingleBody().getAllDayShowFlag());
		generalScheduleInfoEntity.setValidFlag(commonMsg.getBody().getSingleBody().getValidFlag());
		//通知用户列表
		JSONArray noticeUserList = new JSONArray();
		// 循环处理每个参会人的
		for(int i=0; i < jsonArray.size(); i++) {
			String user = jsonArray.getString(i);
			int splitPos = user.indexOf('(');
			String userName = user.substring(splitPos + 1, user.length() - 1);

			noticeUserList.add(userName);
			//System.out.println("====userName===" + userName);
			//获取每个用户的会议日程ID
			QueryWrapper<GeneralCalendarInfoEntity> generalCalendarInfoWrapper = new QueryWrapper<GeneralCalendarInfoEntity>();
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getOwnerId, userName);
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getCalendarName, "会议日程");
			List<GeneralCalendarInfoEntity> generalCalendarInfoEntityList = generalCalendarInfoMapper.selectList(generalCalendarInfoWrapper);
			if (generalCalendarInfoEntityList.size() > 0){
				generalScheduleInfoEntity.setCalendarId(generalCalendarInfoEntityList.get(0).getId());
				generalScheduleInfoEntity.setOwnerUserName(userName);
				generalScheduleInfoEntity.setConferenceId(generalConferenceInfoEntity.id);
				generalScheduleInfoEntity.setDataSign(DooleenMD5Util.md5(generalScheduleInfoEntity.toString(), ConstantValue.md5Key));
				// 执行保存
				generalScheduleInfoMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalScheduleInfoEntity, commonMsg.getHead(), scheduleTableName, scheduleSeqPrefix, redisTemplate));
			}
			else {
				log.info("====warning: 用户{}没有建立会议日程，无法为他写入日程信息",user);
			}
		}
		//通知相关人员
		System.out.println(noticeUserList.toString());
		log.debug("===>> 参会人："+noticeUserList.toString());
		log.info("===>> begin sendMsg。。。");
		SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
		sysSendLogEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysSendLogEntity.setSenderRealName(commonMsg.getHead().getRealName());
		sysSendLogEntity.setSenderId(commonMsg.getHead().getUserName());
		sysSendLogEntity.setHeadImgUrl(commonMsg.getHead().getHeadImgUrl());
		sysSendLogEntity.setResourceId(generalConferenceInfoEntity.getId());
		sysSendLogEntity.setSendAddressList(noticeUserList.toJSONString());//("[\"admin\",\"liqh\"]");  // all 表示发送全部
		sysSendLogEntity.setBizSceneName("会议通知");
		sysSendLogEntity.setMsgTitle(commonMsg.getHead().getRealName()+"发起了"+commonMsg.getBody().getSingleBody().getConferenceType());
		sysSendLogEntity.setMsgContentJson("{ \"first\": \""+commonMsg.getBody().getSingleBody().getConferenceSubject()+"\", \"label1\":\"会议类型\", \"keyword1\": \""+commonMsg.getBody().getSingleBody().getConferenceType()+"\", \"label2\":\"会议地点\", \"keyword2\": \""+commonMsg.getBody().getSingleBody().getConferenceRoom()+"\", \"label3\":\"会议时间\", \"keyword3\": \""+commonMsg.getBody().getSingleBody().getConferenceBeginDatetime()+"\",\"label4\":\"召 集 人\", \"keyword4\": \""+commonMsg.getBody().getSingleBody().getConvenerRealName()+"("+commonMsg.getBody().getSingleBody().getConvenerUserName()+")\", \"remark\": \"所属项目："+commonMsg.getBody().getSingleBody().getProjectName()+"\" }");
		msgSendUtil.sendMsgToMq(sysSendLogEntity);

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> saveGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
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
	@CachePut(value = "generalConferenceInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> updateGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralConferenceInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralConferenceInfoEntity, NullEntity>();
		//定义singleBody
		GeneralConferenceInfoEntity singleBody = new GeneralConferenceInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralConferenceInfoEntity, NullEntity> mutBean= new MutBean<GeneralConferenceInfoEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralConferenceInfoServiceImpl.class).queryGeneralConferenceInfo(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		}  
		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);

		// 租户ID
		String tenantId = commonMsg.getHead().getTenantId();
		String conferenceType = commonMsg.getBody().getSingleBody().getConferenceType();
		String conferenceRoomId = commonMsg.getBody().getSingleBody().getConferenceRoomId();
		// 开始时间
		String startTime = commonMsg.getBody().getSingleBody().getConferenceBeginDatetime();
		// 结束时间
		String endTime = commonMsg.getBody().getSingleBody().getConferenceEndDatetime();
		//检查参会人员时间冲突
		Map<String, String> conflictMap = new HashMap<>();
		//获取参会人员列表含召集人
		JSONArray jsonArray = JSON.parseArray(commonMsg.getBody().getSingleBody().getParticipantsList());
		//获取时间段会议列表
		List<GeneralConferenceInfoEntity>  generalConferenceInfoEntityList= generalConferenceInfoMapper.selectConferenceUserConflict(tenantId, startTime, endTime);
		//检查人员冲突
		for(GeneralConferenceInfoEntity generalConferenceInfoEntity: generalConferenceInfoEntityList){
			if(!generalConferenceInfoEntity.getId().equals(commonMsg.getBody().getSingleBody().getId())) {
				if (generalConferenceInfoEntity.getParticipantsList().indexOf(commonMsg.getBody().getSingleBody().getConvenerRealName() + "(" + commonMsg.getBody().getSingleBody().getConvenerUserName() + ")") >= 0) {
					conflictMap.put(commonMsg.getBody().getSingleBody().getConvenerRealName() + "(" + commonMsg.getBody().getSingleBody().getConvenerUserName() + ")", "会议时间：" + generalConferenceInfoEntity.getConferenceBeginDatetime() + "-" + generalConferenceInfoEntity.getConferenceEndDatetime());
				}
				for (int i = 0; i < jsonArray.size(); i++) {
					if (generalConferenceInfoEntity.getParticipantsList().indexOf(jsonArray.getString(i)) >= 0) {
						conflictMap.put(jsonArray.getString(i), "会议时间冲突（" + generalConferenceInfoEntity.getConferenceBeginDatetime() + "-" + generalConferenceInfoEntity.getConferenceEndDatetime() + ")");
					}
				}
			}
		}
		//检查参数是否强制提示人员冲突
		QueryWrapper<GeneralBizParamEntity> generalBizParamEntityWrapper = new QueryWrapper<GeneralBizParamEntity>();
		generalBizParamEntityWrapper.lambda().eq(GeneralBizParamEntity::getTenantId, commonMsg.getHead().getTenantId());
		generalBizParamEntityWrapper.lambda().eq(GeneralBizParamEntity::getParamKey, "conflictNotice");
		GeneralBizParamEntity generalBizParamEntity =  generalBizParamMapper.selectOne(generalBizParamEntityWrapper);
		log.debug("==============conflictUser:"+conflictMap);
		if(conflictMap.size() > 0  && generalBizParamEntity.getParamValue().equals("true")){
			Map<String, String> map = new HashMap<String, String>();
			map = conflictMap;
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			return commonMsg;
		}
		else{
			commonMsg.getBody().getSingleBody().setConflictMsg(conflictMap.toString());
		}
		//数据签名
		GeneralConferenceInfoEntity generalConferenceInfoEntity = commonMsg.getBody().getSingleBody();
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(generalConferenceInfoEntity.toString(),  ConstantValue.md5Key));
		// 执行保存
		generalConferenceInfoEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalConferenceInfoEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
		// 保存数据
		boolean result =  super.updateById(generalConferenceInfoEntity);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);

		//同步将会议写入对应人员日程
		GeneralScheduleInfoEntity generalScheduleInfoEntity = new GeneralScheduleInfoEntity();
		generalScheduleInfoEntity.setScheduleSubject(commonMsg.getBody().getSingleBody().getConferenceSubject());
		generalScheduleInfoEntity.setScheduleContent(commonMsg.getBody().getSingleBody().getConferenceIssue());
		generalScheduleInfoEntity.setScheduleBeginDatetime(commonMsg.getBody().getSingleBody().getConferenceBeginDatetime());
		generalScheduleInfoEntity.setScheduleEndDatetime(commonMsg.getBody().getSingleBody().getConferenceEndDatetime());
		generalScheduleInfoEntity.setColor(commonMsg.getBody().getSingleBody().getColor());
		generalScheduleInfoEntity.setAllDayShowFlag(commonMsg.getBody().getSingleBody().getAllDayShowFlag());
		generalScheduleInfoEntity.setValidFlag(commonMsg.getBody().getSingleBody().getValidFlag());

		// 删除参会人的日历
		QueryWrapper<GeneralScheduleInfoEntity> generalScheduleInfoWrapper = new QueryWrapper<GeneralScheduleInfoEntity>();
		generalScheduleInfoWrapper.lambda().eq(GeneralScheduleInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
		generalScheduleInfoWrapper.lambda().eq(GeneralScheduleInfoEntity::getConferenceId, commonMsg.getBody().getSingleBody().getId());
		int delResult = generalScheduleInfoMapper.delete(generalScheduleInfoWrapper);

		//通知用户列表
		JSONArray noticeUserList = new JSONArray();
		// 循环处理每个参会人的日程
		for(int i=0; i < jsonArray.size(); i++) {
			String user = jsonArray.getString(i);
			int splitPos = user.indexOf('(');
			String userName = user.substring(splitPos + 1, user.length() - 1);

			noticeUserList.add(userName);
			//System.out.println("====userName===" + userName);
			//获取每个用户的会议日程ID
			QueryWrapper<GeneralCalendarInfoEntity> generalCalendarInfoWrapper = new QueryWrapper<GeneralCalendarInfoEntity>();
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getOwnerId, userName);
			generalCalendarInfoWrapper.lambda().eq(GeneralCalendarInfoEntity::getCalendarName, "会议日程");
			List<GeneralCalendarInfoEntity> generalCalendarInfoEntityList = generalCalendarInfoMapper.selectList(generalCalendarInfoWrapper);
			if (generalCalendarInfoEntityList.size() > 0){
				generalScheduleInfoEntity.setCalendarId(generalCalendarInfoEntityList.get(0).getId());
				generalScheduleInfoEntity.setOwnerUserName(userName);
				generalScheduleInfoEntity.setConferenceId(generalConferenceInfoEntity.id);
				generalScheduleInfoEntity.setDataSign(DooleenMD5Util.md5(generalScheduleInfoEntity.toString(), ConstantValue.md5Key));
				// 执行保存
				generalScheduleInfoMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalScheduleInfoEntity, commonMsg.getHead(), scheduleTableName, scheduleSeqPrefix, redisTemplate));
			}
			else {
				log.info("====warning: 用户{}没有建立会议日程，无法为他写入日程信息",user);
			}
		}
		//通知相关人员
		log.debug("===>> 参会人："+noticeUserList.toString());
		log.info("===>> begin sendMsg。。。");
		SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
		sysSendLogEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysSendLogEntity.setSenderRealName(commonMsg.getHead().getRealName());
		sysSendLogEntity.setSenderId(commonMsg.getHead().getUserName());
		sysSendLogEntity.setResourceId(commonMsg.getBody().getSingleBody().getId());
		sysSendLogEntity.setHeadImgUrl(commonMsg.getHead().getHeadImgUrl());
		sysSendLogEntity.setSendAddressList(noticeUserList.toJSONString());//("[\"admin\",\"liqh\"]");  // all 表示发送全部
		sysSendLogEntity.setBizSceneName("会议通知");
		sysSendLogEntity.setMsgTitle(commonMsg.getHead().getRealName()+"变更了"+commonMsg.getBody().getSingleBody().getConferenceType()+"信息");
		sysSendLogEntity.setMsgContentJson("{ \"first\": \""+commonMsg.getHead().getRealName()+"变更了关于《"+commonMsg.getBody().getSingleBody().getConferenceSubject()+"》的会议，请注意日程安排！\", \"label1\":\"会议类型\", \"keyword1\": \""+commonMsg.getBody().getSingleBody().getConferenceType()+"\", \"label2\":\"会议地点\", \"keyword2\": \""+commonMsg.getBody().getSingleBody().getConferenceRoom()+"\", \"label3\":\"会议时间\", \"keyword3\": \""+commonMsg.getBody().getSingleBody().getConferenceBeginDatetime()+"\",\"label4\":\"召 集 人\", \"keyword4\": \""+commonMsg.getBody().getSingleBody().getConvenerRealName()+"("+commonMsg.getBody().getSingleBody().getConvenerUserName()+")\", \"remark\": \"所属项目："+commonMsg.getBody().getSingleBody().getProjectName()+"\" }");
		msgSendUtil.sendMsgToMq(sysSendLogEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalConferenceInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveOrUpdateGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralConferenceInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralConferenceInfoEntity, NullEntity>();
		//定义singleBody
		GeneralConferenceInfoEntity singleBody = new GeneralConferenceInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralConferenceInfoEntity, NullEntity> mutBean= new MutBean<GeneralConferenceInfoEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralConferenceInfoServiceImpl.class).queryGeneralConferenceInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> saveOrUpdateGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalConferenceInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> deleteGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);

		//获取参会人员列表含召集人
		JSONArray jsonArray = JSON.parseArray(commonMsg.getBody().getSingleBody().getParticipantsList());

		//获取每个用户的会议日程ID
		QueryWrapper<GeneralScheduleInfoEntity> generalScheduleInfoWrapper = new QueryWrapper<GeneralScheduleInfoEntity>();
		generalScheduleInfoWrapper.lambda().eq(GeneralScheduleInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
		generalScheduleInfoWrapper.lambda().eq(GeneralScheduleInfoEntity::getConferenceId, commonMsg.getBody().getSingleBody().getId());
		int delResult = generalScheduleInfoMapper.delete(generalScheduleInfoWrapper);

		//通知用户列表
		JSONArray noticeUserList = new JSONArray();
		// 循环处理每个参会人的日程
		for(int i=0; i < jsonArray.size(); i++) {
			String user = jsonArray.getString(i);
			int splitPos = user.indexOf('(');
			String userName = user.substring(splitPos + 1, user.length() - 1);
			noticeUserList.add(userName);
		}
		//通知相关人员
		log.info("===>> begin sendMsg。。。");
		SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
		sysSendLogEntity.setTenantId(commonMsg.getHead().getTenantId());
		sysSendLogEntity.setSenderRealName(commonMsg.getHead().getRealName());
		sysSendLogEntity.setSenderId(commonMsg.getHead().getUserName());
		sysSendLogEntity.setHeadImgUrl(commonMsg.getHead().getHeadImgUrl());
		sysSendLogEntity.setResourceId(commonMsg.getBody().getSingleBody().getId());
		sysSendLogEntity.setSendAddressList(noticeUserList.toJSONString());//("[\"admin\",\"liqh\"]");  // all 表示发送全部
		sysSendLogEntity.setBizSceneName("会议通知");
		sysSendLogEntity.setMsgTitle(commonMsg.getHead().getRealName()+"取消了"+commonMsg.getBody().getSingleBody().getConferenceType());
		sysSendLogEntity.setMsgContentJson("{ \"first\": \""+commonMsg.getHead().getRealName()+"取消了关于《"+commonMsg.getBody().getSingleBody().getConferenceSubject()+"》的会议，请注意日程安排！\", \"label1\":\"会议类型\", \"keyword1\": \""+commonMsg.getBody().getSingleBody().getConferenceType()+"\", \"label2\":\"会议地点\", \"keyword2\": \""+commonMsg.getBody().getSingleBody().getConferenceRoom()+"\", \"label3\":\"会议时间\", \"keyword3\": \""+commonMsg.getBody().getSingleBody().getConferenceBeginDatetime()+"\",\"label4\":\"召 集 人\", \"keyword4\": \""+commonMsg.getBody().getSingleBody().getConvenerUserName()+"\", \"remark\": \"所属项目："+commonMsg.getBody().getSingleBody().getProjectName()+"\" }");
		msgSendUtil.sendMsgToMq(sysSendLogEntity);

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> deleteGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
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
	public void exportGeneralConferenceInfoExcel(CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<GeneralConferenceInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralConferenceInfoEntity.class);
		
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
		Page<GeneralConferenceInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralConferenceInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "会议信息表";
		String fileName = "会议信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("会议类型");
		headList.add("会议室");
		headList.add("会议主题");
		headList.add("主持人");
		headList.add("主持人名");
		headList.add("参与人列表");
		headList.add("会议开始时间");
		headList.add("会议结束时间");
		headList.add("会议议题");
		headList.add("会议号");
		headList.add("会议链接");
		headList.add("会议附件列表");
		headList.add("颜色");
		headList.add("全天显示标志");
		excelData.add(headList);
		for (GeneralConferenceInfoEntity generalConferenceInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalConferenceInfoEntity.getId());	    		
			lists.add(generalConferenceInfoEntity.getTenantId());	    		
			lists.add(generalConferenceInfoEntity.getConferenceType());	    		
			lists.add(generalConferenceInfoEntity.getConferenceRoom());	    		
			lists.add(generalConferenceInfoEntity.getConferenceSubject());	    		
			lists.add(generalConferenceInfoEntity.getConvenerUserName());	    		
			lists.add(generalConferenceInfoEntity.getConvenerRealName());	    		
			lists.add(generalConferenceInfoEntity.getParticipantsList());	    		
			lists.add(String.valueOf(generalConferenceInfoEntity.getConferenceBeginDatetime()));	    		
			lists.add(String.valueOf(generalConferenceInfoEntity.getConferenceEndDatetime()));	    		
			lists.add(generalConferenceInfoEntity.getConferenceIssue());	    		
			lists.add(generalConferenceInfoEntity.getConferenceNo());	    		
			lists.add(generalConferenceInfoEntity.getConferenceUrl());	    		
			lists.add(generalConferenceInfoEntity.getConferenceAttList());	    		
			lists.add(generalConferenceInfoEntity.getColor());	    		
			lists.add(generalConferenceInfoEntity.getAllDayShowFlag());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		log.info("====exportGeneralConferenceInfoExcel service end == " + RespStatus.json.getString("S0000"));
	}

	 /**
	  * 获取腾讯会议链接
	  */
	 @Override
	 public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> createTencentMeeting(CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		 CommonUtil.service(commonMsg);

		 GeneralConferenceInfoEntity conferenceInfoEntity = commonMsg.getBody().getSingleBody();

		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 // 查询腾讯会议配置
		 QueryWrapper<SysThirdPartyInfoEntity> SysThirdPartyInfoWrapper = new QueryWrapper<SysThirdPartyInfoEntity>();
		 SysThirdPartyInfoWrapper.lambda().eq(SysThirdPartyInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
		 SysThirdPartyInfoWrapper.lambda().eq(SysThirdPartyInfoEntity::getType, "腾讯会议");
		 SysThirdPartyInfoEntity thirdPartyInfo =  sysThirdPartyInfoMapper.selectOne(SysThirdPartyInfoWrapper);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertIsFalse(StringUtil.isEmpty(thirdPartyInfo),commonMsg);

		 // 校验腾讯会议配置信息是否存在
		 JSONObject tencentMeetingInfo = JSON.parseObject(thirdPartyInfo.getThirdPartyConfigContent());
		 String secretId = tencentMeetingInfo.getString("secretId");
		 String secretKey = tencentMeetingInfo.getString("secretKey");
		 String appId = tencentMeetingInfo.getString("appId");
		 String sdkId = tencentMeetingInfo.getString("sdkId");
		 String createMeetingUrl = tencentMeetingInfo.getString("createMeetingUrl");
		 String createMeetingUri = tencentMeetingInfo.getString("createMeetingUri");
		 boolean tencentMeetingChecked = tencentMeetingInfo.getBoolean("tencentMeetingChecked");


		 // 校验腾讯会议配置，各个字段信息是否存在
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(secretId,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(secretKey,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(appId,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(sdkId,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(createMeetingUrl,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertNotEmpty(createMeetingUri,commonMsg);
		 BizResponseEnum.THIRD_PARTY_NOT_CONFIG.assertIsTrue(tencentMeetingChecked,commonMsg);


		 // 创建会议接口，body参数
		 JSONObject reqBodyJson = new JSONObject();
		 try {
			 reqBodyJson.put("userid", commonMsg.getHead().getUserId());
			 reqBodyJson.put("instanceid", 1);
			 reqBodyJson.put("subject", conferenceInfoEntity.getConferenceSubject());
			 reqBodyJson.put("type", 0);
			 reqBodyJson.put("start_time", String.valueOf(sdf.parse(conferenceInfoEntity.getConferenceBeginDatetime()).getTime()).substring(0, 10));
			 reqBodyJson.put("end_time", String.valueOf(sdf.parse(conferenceInfoEntity.getConferenceEndDatetime()).getTime()).substring(0, 10));
		 } catch (Exception e) {
			 BizResponseEnum.DONE_EXCEPTION.assertFail(commonMsg);
		 }

		 // 创建会议
		 String meetingResult = "";
		 try {
			 meetingResult = TencentMeetingUtil.createMeeting(secretId, secretKey, appId, sdkId, createMeetingUri, createMeetingUrl, JSON.toJSONString(reqBodyJson));
		 } catch (Exception e) {
			 BizResponseEnum.DONE_EXCEPTION.assertFail(commonMsg);
		 }
		 BizResponseEnum.DONE_EXCEPTION.assertNotEmpty(meetingResult,commonMsg);

		 JSONObject meetingJson = JSONObject.parseObject(meetingResult);
		 List<Map<String, Object>> meetingList = (List<Map<String, Object>>) meetingJson.get("meeting_info_list");

		 conferenceInfoEntity.setConferenceNo(String.valueOf(meetingList.get(0).get("meeting_code")));
		 conferenceInfoEntity.setConferenceUrl(String.valueOf(meetingList.get(0).get("join_url")));

		 commonMsg.getBody().setSingleBody(conferenceInfoEntity);

		 //设置返回值
		 CommonUtil.successReturn(commonMsg);
		 return commonMsg;
	 }
 }
