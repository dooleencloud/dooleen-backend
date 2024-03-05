package com.dooleen.service.system.calender.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.extra.spring.SpringUtil;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.utils.*;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.dooleen.service.system.calender.entity.SysCalenderEntity;
import com.dooleen.service.system.calender.mapper.SysCalenderMapper;
import com.dooleen.service.system.calender.service.SysCalenderService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowExtInfoEntity;
import com.dooleen.common.core.app.general.flow.util.GeneralFlowProcessUtil;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.ValidationResult;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.mq.utils.MsgSendUtil;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-02 20:34:51
 * @Description : 系统日历管理服务实现
 * @Author : apple
 * @Update: 2021-07-02 20:34:51
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysCalenderServiceImpl extends ServiceImpl<SysCalenderMapper, SysCalenderEntity> implements SysCalenderService {

    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

    @Autowired
    public GeneralFlowProcessService generalFlowProcessService;

	@Autowired
	private GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

	@Autowired
	private  SysCalenderMapper sysCalenderMapper;

	@Autowired
	private  GetBizParamsService getBizParamsService;

	@Autowired
	private GeneralFlowProcessUtil generalFlowProcessUtil;

	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//SYS_CALENDER("sys_calender", "SysCalender", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.SYS_CALENDER.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_CALENDER.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Autowired
	private MsgSendUtil msgSendUtil;

	 /**
	  * 生成年度日历
	  * @param commonMsg
	  * @return
	  */
	 @Override
	 public CommonMsg<SysCalenderEntity, JSONObject> queryYearsCalender(CommonMsg<SysCalenderEntity, JSONObject> commonMsg) {
		 CommonUtil.service(commonMsg);
		 List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		 //查询当年节假日
		 QueryWrapper<SysCalenderEntity> queryWrapper = new QueryWrapper<SysCalenderEntity>();
		 queryWrapper.lambda().eq(SysCalenderEntity::getTenantId, commonMsg.getHead().getTenantId());
		 queryWrapper.lambda().eq(SysCalenderEntity::getYear,commonMsg.getBody().getSingleBody().getHolidayDate());
		 List<SysCalenderEntity> sysCalenderEntityList = sysCalenderMapper.selectList(queryWrapper);
		 //将假期放入map中
		 Map<String, String> holidayMap = new HashMap<>();
		 for(SysCalenderEntity sysCalenderEntity  :sysCalenderEntityList){
			 holidayMap.put(sysCalenderEntity.getHolidayDate(),sysCalenderEntity.getHolidayName());
		 }
		 //生成12个月的日历
		 JSONObject calendarData  = new JSONObject();
		 calendarData.put("years",commonMsg.getBody().getSingleBody().getHolidayDate());

		 JSONArray monthDataList  = new JSONArray();
		 for(int i=1; i<=12; i++){
			 JSONObject monthData  = new JSONObject();
			 monthData.put("month",i+"");
			 String month = String.valueOf(i);
			 String day = "";
			 if(i<10){
			 	month = "0"+i;
			 }
			 monthData.put("monthFull",commonMsg.getBody().getSingleBody().getHolidayDate()+"-"+month+"-01");

			 int monthDays = DateUtilPro.getMonthDays(commonMsg.getBody().getSingleBody().getHolidayDate()+"-"+month+"-01");
			 JSONArray markDataList = new JSONArray();
			 //循环31天找出节假日
			 for(int j=1; j<=monthDays; j++){
			 	 day = String.valueOf(j);
				 if(j<10){
					 day = "0"+j;
				 }
				 String key = commonMsg.getBody().getSingleBody().getHolidayDate()+"-"+month+"-"+day;
				 DateUtilPro.DateTime date = DateUtilPro.date(DateUtils.getDateByStr(key));

				 JSONObject markData  = new JSONObject();
			 	 if(holidayMap.containsKey(key)){
					 markData.put("selfDate",key);
					 markData.put("things", holidayMap.get(key).equals("工作日")? "" : holidayMap.get(key));
					 markData.put("isWorkDay",holidayMap.get(key).equals("工作日")? "班": "休");
					 markDataList.add(markData);
				 }
			 	 else {
					 markData.put("selfDate",key);
					 markData.put("things","");
					 markData.put("isWorkDay",date.day(j).isWorkday()? "班": "休");
					 markDataList.add(markData);
				 }
			 }
			 monthData.put("markData",markDataList);
			 monthDataList.add(monthData);
		 }
		 calendarData.put("monthData",monthDataList);

		 //组装返回
		 List<JSONObject> returnObj = new ArrayList<>();
		 returnObj.add(calendarData);
		 commonMsg.getBody().setListBody(returnObj);
		 //设置返回值
		 CommonUtil.successReturn(commonMsg);
		 return commonMsg;
	 }

	@Override
	@Cacheable(value = "sysCalender", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<SysCalenderEntity, NullEntity> querySysCalender(CommonMsg<SysCalenderEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysCalenderEntity sysCalenderEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

		commonMsg.getBody().setListBody(nullEntityList);

		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysCalenderEntity,commonMsg);

		//获取流程相关数据
		generalFlowProcessUtil.queryExtData(commonMsg);

		commonMsg.getBody().setSingleBody(sysCalenderEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysCalenderEntity> querySysCalenderList(CommonMsg<NullEntity, SysCalenderEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysCalenderEntity> sysCalenderEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysCalenderEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysCalenderEntity, SysCalenderEntity> querySysCalenderListPage(
			CommonMsg<SysCalenderEntity, SysCalenderEntity> commonMsg) {
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
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0")  || entry.getValue().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0)))) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					if(entry.getKey().equals("holidayName")){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<SysCalenderEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCalenderEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCalenderEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysCalenderEntity.class);
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
		Page<SysCalenderEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysCalenderEntity> list =  super.page(pages, queryWrapper);

		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysCalenderEntity, SysCalenderEntity> querySysCalenderListMap(
			CommonMsg<SysCalenderEntity, SysCalenderEntity> commonMsg) {
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
		Collection<SysCalenderEntity> sysCalenderEntityList =  super.listByMap(conditionMap);
		List<SysCalenderEntity> sysCalenderMapList = new ArrayList<SysCalenderEntity>(sysCalenderEntityList);
		commonMsg.getBody().setListBody(sysCalenderMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysCalender", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCalenderEntity, NullEntity> saveSysCalender(CommonMsg<SysCalenderEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		//自动启动流程
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true") && commonMsg.getBody().getFlowArea().getFlowBeginWay().equals("1")) {
			GeneralFlowExtInfoEntity generalFlowExtInfoEntity = new GeneralFlowExtInfoEntity();
			generalFlowExtInfoEntity.setBizType("业务类型");
			generalFlowExtInfoEntity.setFormType("2");
			generalFlowExtInfoEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
			generalFlowExtInfoEntity.setBizName("填入自己的业务名称");
			generalFlowExtInfoEntity.setBizCode("填入自己的业务码"); //后续通过业务码获取字典表里的流程ID
			//generalFlowExtInfoEntity.setFormId(commonMsg.getBody().getSingleBody().get("formId").toString());
			//generalFlowExtInfoEntity.setFlowId(commonMsg.getBody().getFlowArea().getFlowId());
			generalFlowProcessUtil.handleProccess(commonMsg, generalFlowExtInfoEntity);
			//判断流程是否处理成功，失败直接返回
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
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
	public CommonMsg<NullEntity, SysCalenderEntity> saveSysCalenderList(CommonMsg<NullEntity, SysCalenderEntity> commonMsg) {
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
	@CachePut(value = "sysCalender", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCalenderEntity, NullEntity> updateSysCalender(CommonMsg<SysCalenderEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}

		//处理流程
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			GeneralFlowExtInfoEntity generalFlowExtInfoEntity = new GeneralFlowExtInfoEntity();
			generalFlowExtInfoEntity.setBizType("业务类型");
			generalFlowExtInfoEntity.setFormType("2");
			generalFlowExtInfoEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
			generalFlowExtInfoEntity.setBizName("填入自己的业务名称");
			generalFlowExtInfoEntity.setBizCode("填入自己的业务码"); //后续通过业务码获取字典表里的流程ID
			//generalFlowExtInfoEntity.setFormId(commonMsg.getBody().getSingleBody().get("formId").toString());
			//generalFlowExtInfoEntity.setFlowId(commonMsg.getBody().getFlowArea().getFlowId());

			generalFlowProcessUtil.handleProccess(commonMsg, generalFlowExtInfoEntity);
			//判断流程是否处理成功，失败直接返回
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
		}

		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<SysCalenderEntity, NullEntity> queryCommonMsg = new CommonMsg<SysCalenderEntity, NullEntity>();
		//定义body
		MutBean<SysCalenderEntity, NullEntity> mutBean= new MutBean<SysCalenderEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(commonMsg.getBody().getSingleBody());
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(SysCalenderServiceImpl.class).querySysCalender(queryCommonMsg);

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
	@CachePut(value = "sysCalender", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<SysCalenderEntity, NullEntity> saveOrUpdateSysCalender(CommonMsg<SysCalenderEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysCalenderEntity, NullEntity> queryCommonMsg = new CommonMsg<SysCalenderEntity, NullEntity>();
		//定义singleBody
		SysCalenderEntity singleBody = new SysCalenderEntity();

		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(!StringUtil.isEmptyAndZero(commonMsg.getBody().getSingleBody().getId())){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
			//定义body
			MutBean<SysCalenderEntity, NullEntity> mutBean= new MutBean<SysCalenderEntity, NullEntity>();
			FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
			mutBean.setSingleBody(singleBody);
			mutBean.setFlowArea(flowArea);
			//深拷贝数据进行AOP查询
			queryCommonMsg.setHead(commonMsg.getHead());
			queryCommonMsg.setBody(mutBean);
			queryCommonMsg = SpringUtil.getBean(SysCalenderServiceImpl.class).querySysCalender(queryCommonMsg);

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
	public CommonMsg<NullEntity, SysCalenderEntity> saveOrUpdateSysCalenderList(CommonMsg<NullEntity, SysCalenderEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}

		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(),  ConstantValue.md5Key));
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "sysCalender", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<SysCalenderEntity, NullEntity> deleteSysCalender(CommonMsg<SysCalenderEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, SysCalenderEntity> deleteSysCalenderList(CommonMsg<NullEntity, SysCalenderEntity> commonMsg) {
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
	public void exportSysCalenderExcel(CommonMsg<SysCalenderEntity, SysCalenderEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<SysCalenderEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysCalenderEntity.class);

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
		Page<SysCalenderEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysCalenderEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		//防止导出异常，如果列表为null，初始化一条空记录
		if(list.getRecords().size() == 0){
			List<SysCalenderEntity> listEntity = new ArrayList<SysCalenderEntity>();
			listEntity.add(new SysCalenderEntity());
			commonMsg.getBody().setListBody(listEntity);
		}
		/**
		 * 生成excel并输出
		 */
		Date start = new Date();
		String sheetName = "系统日历表";
		String fileName = "系统日历表-"+start.getTime();
		ExportParams params = new ExportParams();
		Workbook workbook = ExcelExportUtil.exportExcel(params, SysCalenderEntity.class, commonMsg.getBody().getListBody());
		//导出Excel数据流
		try {
			ExcelUtil.exportPoi(response,workbook,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("====exportSysCalenderExcel service end == " + RespStatus.json.getString("S0000"));
	}
	/**
	* 导入excel新增
	*/
	@Override
	public CommonMsg<SysCalenderEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		CommonMsg<SysCalenderEntity,NullEntity> commonMsg = new CommonMsg<SysCalenderEntity,NullEntity>();
		HeadEntity head =new HeadEntity();
		commonMsg.setHead(head);
		commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
		commonMsg.getHead().setUserName(request.getHeader("username"));
		ImportParams params = new ImportParams();
		params.setTitleRows(0);
		List<SysCalenderEntity> list = new ArrayList<>();
		try {
			commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
			list = ExcelImportUtil.importExcel(file.getInputStream(), SysCalenderEntity.class, params);
		} catch (Exception e) {
			e.printStackTrace();
	    	map.put("E0002","导入出现异常！");
	    	commonMsg.getHead().setRespCode(RespStatus.errorCode);
	    	commonMsg.getHead().setRespMsg(map);
	    	return commonMsg;
		}
		//批量导入数据并做校验
		List<SysCalenderEntity> tmpList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			SysCalenderEntity sysCalenderEntity = list.get(i);

			//检查字段的有效性
			ValidationResult validationResult = ValidationUtils.validateEntity(sysCalenderEntity);
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
				sysCalenderEntity.setDataSign(DooleenMD5Util.md5(sysCalenderEntity.toString(),  ConstantValue.md5Key));
				// 初始化数据
				sysCalenderEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysCalenderEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				tmpList.add(sysCalenderEntity);
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
