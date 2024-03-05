package com.dooleen.service.biz.sixteen.user.analysis.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.biz.sixteen.eight.sf.entity.BizSixteenEightSfEntity;
import com.dooleen.service.biz.sixteen.eight.sf.mapper.BizSixteenEightSfMapper;
import com.dooleen.service.biz.sixteen.pf.entity.BizSixteenPfEntity;
import com.dooleen.service.biz.sixteen.pf.mapper.BizSixteenPfMapper;
import com.dooleen.service.biz.sixteen.user.answers.entity.BizSixteenUserAnswersEntity;
import com.dooleen.service.biz.sixteen.user.answers.mapper.BizSixteenUserAnswersMapper;
import com.dooleen.service.biz.sixteen.user.info.mapper.BizSixteenUserInfoMapper;
import com.dooleen.service.biz.sixteen.user.pf.exch.entity.BizSixteenUserPfExchEntity;
import com.dooleen.service.biz.sixteen.user.pf.exch.mapper.BizSixteenUserPfExchMapper;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.dooleen.service.biz.sixteen.user.analysis.entity.BizSixteenUserAnalysisEntity;
import com.dooleen.service.biz.sixteen.user.analysis.mapper.BizSixteenUserAnalysisMapper;
import com.dooleen.service.biz.sixteen.user.analysis.service.BizSixteenUserAnalysisService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.ValidationResult;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 21:28:50
 * @Description : 用户结果分析管理服务实现
 * @Author : apple
 * @Update: 2021-05-08 21:28:50
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizSixteenUserAnalysisServiceImpl extends ServiceImpl<BizSixteenUserAnalysisMapper, BizSixteenUserAnalysisEntity> implements BizSixteenUserAnalysisService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  BizSixteenUserAnalysisMapper bizSixteenUserAnalysisMapper;
	
	@Autowired
	private  GetBizParamsService getBizParamsService;

	 @Autowired
	 private BizSixteenUserInfoMapper bizSixteenUserInfoMapper;

	 @Autowired
	 private BizSixteenUserAnswersMapper bizSixteenUserAnswersMapper;

	 @Autowired
	 private BizSixteenUserPfExchMapper bizSixteenUserPfExchMapper;

	 @Autowired
	 private BizSixteenPfMapper bizSixteenPfMapper;

	 @Autowired
	 private BizSixteenEightSfMapper bizSixteenEightSfMapper;

	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_SIXTEEN_USER_ANALYSIS("biz_sixteen_user_analysis", "BizSixteenUserAnalysis", "AAAA"),

	private  static String seqPrefix= TableEntityORMEnum.BIZ_SIXTEEN_USER_PF_EXCH.getSeqPrefix();
	 private  static String tableName = TableEntityORMEnum.BIZ_SIXTEEN_USER_PF_EXCH.getEntityName();


	 private  static String anaSeqPrefix= TableEntityORMEnum.BIZ_SIXTEEN_USER_ANALYSIS.getSeqPrefix();
	private  static String anaTableName = TableEntityORMEnum.BIZ_SIXTEEN_USER_ANALYSIS.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "bizSixteenUserAnalysis", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> queryBizSixteenUserAnalysis(CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizSixteenUserAnalysisEntity,commonMsg);
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
			
		commonMsg.getBody().setSingleBody(bizSixteenUserAnalysisEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> queryBizSixteenUserAnalysisList(CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizSixteenUserAnalysisEntity> bizSixteenUserAnalysisEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizSixteenUserAnalysisEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> queryBizSixteenUserAnalysisListPage(
			CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> commonMsg) {
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
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().toString().equals("0") || entry.getValue().toString().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0)))) {
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
		QueryWrapper<BizSixteenUserAnalysisEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserAnalysisEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserAnalysisEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizSixteenUserAnalysisEntity.class);
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
		Page<BizSixteenUserAnalysisEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizSixteenUserAnalysisEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> queryBizSixteenUserAnalysisListMap(
			CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> commonMsg) {
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
		Collection<BizSixteenUserAnalysisEntity> bizSixteenUserAnalysisEntityList =  super.listByMap(conditionMap);
		List<BizSixteenUserAnalysisEntity> bizSixteenUserAnalysisMapList = new ArrayList<BizSixteenUserAnalysisEntity>(bizSixteenUserAnalysisEntityList);
		commonMsg.getBody().setListBody(bizSixteenUserAnalysisMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizSixteenUserAnalysis", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> saveBizSixteenUserAnalysis(CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 生成分析数据
		// 获取用户信息
		BizSixteenUserInfoEntity bizSixteenUserInfoEntity = bizSixteenUserInfoMapper.selectById(commonMsg.getHead().getUserId());
		BizResponseEnum.USER_INFO_NOT_EXIST_SIXTEEN.assertNotNull(bizSixteenUserInfoEntity,commonMsg);

		//查询用户是否已提交过数据
		QueryWrapper<BizSixteenUserAnalysisEntity> queryWrapper = new QueryWrapper<BizSixteenUserAnalysisEntity>();
		queryWrapper.lambda().eq(BizSixteenUserAnalysisEntity::getTenantId,bizSixteenUserInfoEntity.getTenantId());
		queryWrapper.lambda().eq(BizSixteenUserAnalysisEntity::getUserName,bizSixteenUserInfoEntity.getUserName());
		List<BizSixteenUserAnalysisEntity> analysisList = bizSixteenUserAnalysisMapper.selectList(queryWrapper);
		if(analysisList.size() > 0){
			BizResponseEnum.USER_HAS_SUBMIT_SIXTEEN.assertIsTrue(false,commonMsg);
		}

		//获取用户问卷记录
		QueryWrapper<BizSixteenUserAnswersEntity> answerQueryWrapper = new QueryWrapper<BizSixteenUserAnswersEntity>();
		answerQueryWrapper.lambda().eq(BizSixteenUserAnswersEntity::getTenantId,bizSixteenUserInfoEntity.getTenantId());
		answerQueryWrapper.lambda().eq(BizSixteenUserAnswersEntity::getUserName,bizSixteenUserInfoEntity.getUserName());
		List<BizSixteenUserAnswersEntity> answersList = bizSixteenUserAnswersMapper.selectList(answerQueryWrapper);

		//加载原始分换标准分表
		Hashtable orgTOstandHas = new Hashtable();
		String[] orgTOstandA;
		String[] orgTOstandB;
		String[] orgTOstandC;
		String[] orgTOstandE;
		String[] orgTOstandF;
		String[] orgTOstandG;
		String[] orgTOstandH;
		String[] orgTOstandI;
		String[] orgTOstandL;
		String[] orgTOstandM;
		String[] orgTOstandN;
		String[] orgTOstandO;
		String[] orgTOstandQ1;
		String[] orgTOstandQ2;
		String[] orgTOstandQ3;
		String[] orgTOstandQ4;
		//成人女性
		if (bizSixteenUserInfoEntity.getSex().equals("女"))
		{
			orgTOstandA = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8-4", "9,10-5", "11,12-6", "13,14-7", "15,16-8", "17-9", "18,19,20-10" };
			orgTOstandB = new String[] { "0,1,2,3-1", "4,5-2", "6-3", "7-4", "8-5", "9-6", "10-7", "11-8", "12-9", "13-10" };
			orgTOstandC = new String[] { "0,1,2,3,4,5-1", "6,7-2", "8,9-3", "10,11-4", "12,13-5", "14,15,16-6", "17,18-7", "19,20-8", "21,22-9", "23,24,25,26-10" };
			orgTOstandE = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8,9-4", "10,11-5", "12,13-6", "14,15-7", "16,17-8", "18,19-9", "20,21,22,23,24,25,26-10" };
			orgTOstandF = new String[] { "0,1,2-1", "3,4,5-2", "6,7-3", "8,9-4", "10,11,12-5", "13,14,15-6", "16,17-7", "18,19-8", "20,21,22-9", "23,24,25,26-10" };
			orgTOstandG = new String[] { "0,1,2,3,4,5-1", "6,7-2", "8-3", "9,10-4", "11,12-5", "13,14-6", "15,16-7", "17-8", "18,19-9", "20-10" };
			orgTOstandH = new String[] { "0,1-1", "2,3-2", "4,5-3", "6,7-4", "8,9,10-5", "11,12,13-6", "14,15-7", "16,17-8", "18,19,20-9", "21,22,23,24,25,26-10" };
			orgTOstandI = new String[] { "0,1,2,3,4-1", "5-2", "6,7-3", "8-4", "9,10-5", "11,12-6", "13-7", "14,15-8", "16,17-9", "18,19,20-10" };
			orgTOstandL = new String[] { "0,1,2,3-1", "4-2", "5,6-3", "7,8-4", "9,10-5", "11,12-6", "13-7", "14,15-8", "16,17-9", "18,19,20-10" };
			orgTOstandM = new String[] { "0,1,2,3,4,5-1", "6-2", "7,8-3", "9,10-4", "11,12-5", "13,14-6", "15,16-7", "17-8", "18,19-9", "20,21,22,23,24,25,26-10" };
			orgTOstandN = new String[] { "0,1,2,3-1", "4,5-2", "6-3", "7-4", "8,9-5", "10,11-6", "12,13-7", "14-8", "15,16-9", "17,18,19,20-10" };
			orgTOstandO = new String[] { "0,1-1", "2,3-2", "4,5-3", "6,7,8-4", "9-5", "10,11,12-6", "13,14-7", "15,16-8", "17,18-9", "19,20,21,22,23,24,25,26-10" };
			orgTOstandQ1 = new String[] { "0,1,2,3,4-1", "5-2", "6,7-3", "8,9-4", "10,11-5", "12,13-6", "14-7", "15-8", "16,17-9", "18,19,20-10" };
			orgTOstandQ2 = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8,9-4", "10,11-5", "12,13-6", "14,15-7", "16,17-8", "18-9", "19,20-10" };
			orgTOstandQ3 = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8,9-4", "10,11,12-5", "13,14-6", "15,16-7", "17,18-8", "19-9", "20-10" };
			orgTOstandQ4 = new String[] { "0,1,2-1", "3,4-2", "5,6-3", "7,8-4", "9,10,11-5", "12,13,14-6", "15,16-7", "17,18-8", "19,20-9", "21,22,23,24,25,26-10" };
		}
		else //成人男性
		{
			orgTOstandA = new String[] { "0,1,2-1", "3-2", "4,5-3", "6,7-4", "8,9,10-5", "11,12-6", "13-7", "14,15-8", "16,17-9", "18,19,20-10" };
			orgTOstandB = new String[] { "0,1,2,3-1", "4-2", "5,6-3", "7-4", "8-5", "9-6", "10-7", "11-8", "12-9", "13-10" };
			orgTOstandC = new String[] { "0,1,2,3,4,5,6-1", "7,8-2", "9,10-3", "11,12-4", "13,14,15-5", "16,17-6", "18,19-7", "20,21-8", "22,23-9", "24,25,26-10" };
			orgTOstandE = new String[] { "0,1,2,3,4,5-1", "6-2", "7,8-3", "9,10-4", "11,12-5", "13,14-6", "15,16-7", "17,18-8", "19,20-9", "21,22,23,24,25,26-10" };
			orgTOstandF = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8,9-4", "10,11,12-5", "13,14-6", "15,16,17-7", "18,19-8", "20,21,22-9", "23,24,25,26-10" };
			orgTOstandG = new String[] { "0,1,2,3,4,5-1", "6-2", "7,8-3", "9,10-4", "11,12-5", "13,14-6", "15,16-7", "17-8", "18,19-9", "20-10" };
			orgTOstandH = new String[] { "0,1-1", "2,3-2", "4,5,6-3", "7,8-4", "9,10-5", "11,12,13-6", "14,15-7", "16,17,18-8", "19,20-9", "21,22,23,24,25,26-10" };
			orgTOstandI = new String[] { "0,1,2,3-1", "4,5-2", "6-3", "7-4", "8,9-5", "10,11-6", "12-7", "13,14-8", "15-9", "16,17,18,19,20-10" };
			orgTOstandL = new String[] { "0,1,2,3,4-1", "5-2", "6,7-3", "8,9-4", "10,11-5", "12-6", "13,14-7", "15-8", "16,17-9", "18,19,20-10" };
			orgTOstandM = new String[] { "0,1,2,3-1", "4-2", "5,6-3", "7,8-4", "9,10-5", "11,12,13-6", "14-7", "15,16-8", "17,18-9", "19,20,21,22,23,24,25,26-10" };
			orgTOstandN = new String[] { "0,1,2,3-1", "4-2", "5,6-3", "7-4", "8,9-5", "10,11-6", "12-7", "13,14-8", "15-9", "16,17,18,19,20-10" };
			orgTOstandO = new String[] { "0,1-1", "2-2", "3,4-3", "5,6-4", "7,8-5", "9,10-6", "11,12-7", "13,14-8", "15,16-9", "17,18,19,20,21,22,23,24,25,26-10" };
			orgTOstandQ1 = new String[] { "0,1,2,3,4-1", "5,6-2", "7-3", "8,9-4", "10,11-5", "12,13-6", "14-7", "15,16-8", "17-9", "18,19,20-10" };
			orgTOstandQ2 = new String[] { "0,1,2,3,4,5-1", "6,7-2", "8,9-3", "10-4", "11,12-5", "13,14,15-6", "16-7", "17,18-8", "19-9", "20-10" };
			orgTOstandQ3 = new String[] { "0,1,2,3-1", "4,5-2", "6,7-3", "8,9-4", "10,11,12-5", "13,14-6", "15,16-7", "17,18-8", "19-9", "20-10" };
			orgTOstandQ4 = new String[] { "0,1,2-1", "3,4-2", "5,6-3", "7,8-4", "9,10-5", "11,12,13-6", "14,15-7", "16,17-8", "18,19-9", "20,21,22,23,24,25,26-10" };
		}


		orgTOstandHas.put("A", orgTOstandA);
		orgTOstandHas.put("B", orgTOstandB);
		orgTOstandHas.put("C", orgTOstandC);
		orgTOstandHas.put("E", orgTOstandE);
		orgTOstandHas.put("F", orgTOstandF);
		orgTOstandHas.put("G", orgTOstandG);
		orgTOstandHas.put("H", orgTOstandH);
		orgTOstandHas.put("I", orgTOstandI);
		orgTOstandHas.put("L", orgTOstandL);
		orgTOstandHas.put("M", orgTOstandM);
		orgTOstandHas.put("N", orgTOstandN);
		orgTOstandHas.put("O", orgTOstandO);
		orgTOstandHas.put("Q1", orgTOstandQ1);
		orgTOstandHas.put("Q2", orgTOstandQ2);
		orgTOstandHas.put("Q3", orgTOstandQ3);
		orgTOstandHas.put("Q4", orgTOstandQ4);

		//建立答案原始积分hash表
		Hashtable scoreHas = new Hashtable();
		scoreHas.put("A", 0);
		scoreHas.put("B", 0);
		scoreHas.put("C", 0);
		scoreHas.put("E", 0);
		scoreHas.put("F", 0);
		scoreHas.put("G", 0);
		scoreHas.put("H", 0);
		scoreHas.put("I", 0);
		scoreHas.put("L", 0);
		scoreHas.put("M", 0);
		scoreHas.put("N", 0);
		scoreHas.put("O", 0);
		scoreHas.put("Q1", 0);
		scoreHas.put("Q2", 0);
		scoreHas.put("Q3", 0);
		scoreHas.put("Q4", 0);

		for (BizSixteenUserAnswersEntity answersEntity : answersList)
		{
			String RightAnswer = answersEntity.getRightAnswer();
			int RightAnsScore = answersEntity.getRightAnswerScore();
			String PreAnswer = answersEntity.getPreAnswer();
			int PreAnsScore = answersEntity.getPreAnswerScore();
			String FactorID = answersEntity.getFactorId();
			String selectAnswer = answersEntity.getUserAnswer();
			//判断答案是否正确，并计算原始分保存在scoreHas中
			int currentScore = 0;
			if (selectAnswer.equals(RightAnswer))
			{
				currentScore = RightAnsScore;
			}
			else if (selectAnswer.equals(PreAnswer))
			{
				currentScore = PreAnsScore;
			}
			//由于做了初始化，直接做运算。不用考虑异常
			int lastScore = (int) scoreHas.get(FactorID);
			lastScore = lastScore + currentScore;
			scoreHas.put(FactorID, lastScore);
		}


		//计算标准分
		Hashtable standScore = new Hashtable();
		//初始化标准分哈希表
		standScore.put("A", 0);
		standScore.put("B", 0);
		standScore.put("C", 0);
		standScore.put("E", 0);
		standScore.put("F", 0);
		standScore.put("G", 0);
		standScore.put("H", 0);
		standScore.put("I", 0);
		standScore.put("L", 0);
		standScore.put("M", 0);
		standScore.put("N", 0);
		standScore.put("O", 0);
		standScore.put("Q1", 0);
		standScore.put("Q2", 0);
		standScore.put("Q3", 0);
		standScore.put("Q4", 0);

		List<String> scoreHasKeys = sortKeys(scoreHas);
		for(String keys : scoreHasKeys){
			String[] list = (String[])orgTOstandHas.get(keys);

			String lastScore = scoreHas.get(keys).toString();
			for (int i = 0; i < list.length; i++)
			{
				// Response.Write(list[i]);
				if (list[i].indexOf("-") > 0)
				{
					String[] orgStand = list[i].split("-");
					//原始分数组
					String[] orgScore = orgStand[0].split(",");
					if(Arrays.asList(orgScore).contains(lastScore))
					{
						standScore.put(keys, orgStand[1]);
					}
				}
			}
		}
		//输出原始分：
		System.out.println("=====================================16PF测试结果=======================================<br/>");
		List<String> standScoreKeys = sortKeys(standScore);
		for(String keys : standScoreKeys){
			System.out.println("standScore.get(keys)=="+standScore.get(keys));
			int standSc = Integer.parseInt(standScore.get(keys).toString());
			QueryWrapper<BizSixteenPfEntity>  bizQueryWrapper = new QueryWrapper<BizSixteenPfEntity>();
			bizQueryWrapper.lambda().eq(BizSixteenPfEntity::getTenantId, bizSixteenUserInfoEntity.getTenantId());
			bizQueryWrapper.lambda().eq(BizSixteenPfEntity::getFactorId, keys);
			BizSixteenPfEntity sixteenpf = bizSixteenPfMapper.selectOne(bizQueryWrapper);
			BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sixteenpf,commonMsg);

			String hlDec = "";
			String desc = "";
			String factor = "";
			int score = 0;
			String lowScoFeatures = "";
			String highScoFeatures = "";

			if (!StringUtil.isNull(sixteenpf))
			{
				if (standSc >= sixteenpf.getLowScoreMin() && standSc <= sixteenpf.getLowScoreMax())
				{
					hlDec = "低分";
				}
				else if (standSc >= sixteenpf.getHighScoreMin() && standSc <= sixteenpf.getHighScoreMax())
				{
					hlDec = "高分";
				}
				else
				{
					hlDec = "中间状态";
				}

				factor = sixteenpf.getFactor();

				score = (int) scoreHas.get(keys);

				lowScoFeatures = sixteenpf.getLowScoreFeatures();

				if (StringUtil.isEmpty(sixteenpf.getLowScoreFeatures()))
				{
					lowScoFeatures = " ";
				}

				highScoFeatures = sixteenpf.getHighScoreFeatures();
				if (StringUtil.isEmpty(sixteenpf.getHighScoreFeatures()))
				{
					highScoFeatures = " ";
				}
			}
			//写记录到16PF原始标准表
				BizSixteenUserPfExchEntity bizSixteenUserPfExchEntity = new BizSixteenUserPfExchEntity();
				CopyFieldBeanUtil.copyProperties(bizSixteenUserInfoEntity,bizSixteenUserPfExchEntity,true);
				bizSixteenUserPfExchEntity.setId("");
				bizSixteenUserPfExchEntity.setFactorId(keys);
				bizSixteenUserPfExchEntity.setOriginScore(score);
				bizSixteenUserPfExchEntity.setCompaScore(0);
				bizSixteenUserPfExchEntity.setStatusScore(standSc);
				bizSixteenUserPfExchEntity.setDate(DateUtils.getShortDateStr());
				bizSixteenUserPfExchEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserPfExchEntity.toString(),  ConstantValue.md5Key));
				bizSixteenUserPfExchEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserPfExchEntity, commonMsg.getHead(),anaTableName, anaSeqPrefix, redisTemplate);
				log.debug("==>>开始写UserPfExch记录表");
				int addResult = bizSixteenUserPfExchMapper.insert(bizSixteenUserPfExchEntity);
				BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(addResult == 1);
			//写用户分析表
				BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity = new BizSixteenUserAnalysisEntity();
				bizSixteenUserAnalysisEntity.setUserName(bizSixteenUserInfoEntity.getUserName());
				bizSixteenUserAnalysisEntity.setFactor(factor);
				bizSixteenUserAnalysisEntity.setFactorId(keys);
				bizSixteenUserAnalysisEntity.setScore(score);
				bizSixteenUserAnalysisEntity.setFactorType("16PF");
				bizSixteenUserAnalysisEntity.setStatusScore(standSc);
				bizSixteenUserAnalysisEntity.setStatus(hlDec);
				bizSixteenUserAnalysisEntity.setLowScoreFeatures(lowScoFeatures);
				bizSixteenUserAnalysisEntity.setHighScoreFeatures(highScoFeatures);
				bizSixteenUserAnalysisEntity.setDate(DateUtils.getShortDateStr());
				bizSixteenUserAnalysisEntity.setAnswerDuration(DateUtils.getShortDateStr());
				bizSixteenUserAnalysisEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserAnalysisEntity.toString(),  ConstantValue.md5Key));
				bizSixteenUserAnalysisEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserAnalysisEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				log.debug("==>>开始写用户分析记录表");
				boolean result = super.save(bizSixteenUserAnalysisEntity);
				BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		}

		//计算八种次级因素
		Hashtable eightSFHas = new Hashtable();
		float A = Float.parseFloat(standScore.get("A").toString());
		float B = Float.parseFloat(standScore.get("B").toString());
		float C = Float.parseFloat(standScore.get("C").toString());
		float E = Float.parseFloat(standScore.get("E").toString());
		float F = Float.parseFloat(standScore.get("F").toString());
		float G = Float.parseFloat(standScore.get("G").toString());
		float H = Float.parseFloat(standScore.get("H").toString());
		float I = Float.parseFloat(standScore.get("I").toString());
		float L = Float.parseFloat(standScore.get("L").toString());
		float M = Float.parseFloat(standScore.get("M").toString());
		float N = Float.parseFloat(standScore.get("N").toString());
		float O = Float.parseFloat(standScore.get("O").toString());
		float Q1 = Float.parseFloat(standScore.get("Q1").toString());
		float Q2 = Float.parseFloat(standScore.get("Q2").toString());
		float Q3 = Float.parseFloat(standScore.get("Q3").toString());
		float Q4 = Float.parseFloat(standScore.get("Q4").toString());
		float X1 = ((38 + 2 * L + 3 * O + 4 * Q4) - (2 * C + 2 * H + 2 * Q2)) / 10;
		float X2 = ((2 * A + 3 * E + 4 * F + 5 * H) - (2 * Q2 + 11)) / 10;
		float X3 = ((77 + 2 * C + 2 * E + 2 * F + 2 * N) - (4 * A + 6 * I + 2 * M)) / 10;
		float X4 = ((4 * E + 3 * M + 4 * Q1 + 4 * Q2) - (3 * A + 2 * G)) / 10;
		float Y1 = C + F + (11 - O) + (11 - Q4);
		float Y2 = Q3 * 2 + G * 2 + C * 2 + E + N + Q2 + Q1;
		float Y3 = (11 - A) * 2 + B * 2 + E + (11 - F) * 2 + H + I * 2 + M + (11 - N) + Q1 + Q2 * 2;
		float Y4 = B + G + Q3 + (11 - F);
		eightSFHas.put("X1", X1);
		eightSFHas.put("X2", X2);
		eightSFHas.put("X3", X3);
		eightSFHas.put("X4", X4);
		eightSFHas.put("Y1", Y1);
		eightSFHas.put("Y2", Y2);
		eightSFHas.put("Y3", Y3);
		eightSFHas.put("Y4", Y4);
		System.out.println("=====================================8种次级因素测试结果=======================================<br/>");
		List<String> eightSFKeys = sortKeys(eightSFHas);
		for(String keys : eightSFKeys){
			//读取八项次级因素值
			QueryWrapper<BizSixteenEightSfEntity>  bizSfQueryWrapper = new QueryWrapper<BizSixteenEightSfEntity>();
			bizSfQueryWrapper.lambda().eq(BizSixteenEightSfEntity::getTenantId, bizSixteenUserInfoEntity.getTenantId());
			bizSfQueryWrapper.lambda().eq(BizSixteenEightSfEntity::getFactorId, keys);
			BizSixteenEightSfEntity sixteensf = bizSixteenEightSfMapper.selectOne(bizSfQueryWrapper);
			BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sixteensf,commonMsg);
			
			String hlDec = "";
			String desc = "";
			float score = 0;
			float standSc = 0;
			String lowScoFeatures = "";
			String highScoFeatures = "";
			String factor = "";

			if (!StringUtil.isNull(sixteensf))
			{
				score = Float.parseFloat(eightSFHas.get(keys).toString());
				standSc = Float.parseFloat(eightSFHas.get(keys).toString());
				lowScoFeatures = sixteensf.getLowFactorContent();
				highScoFeatures = sixteensf.getHightFactorContent();
				factor = sixteensf.getFactor();

				//如果从8中次级因素表中没有取到低分特征或者高分特征或者取到的是空字符串，给lowScoFeatures或者highScoFeatures赋值一个空格字符串
				if (StringUtil.isEmpty(sixteensf.getLowFactorContent())) {
					lowScoFeatures = " ";
				}

				if (StringUtil.isEmpty(sixteensf.getHightFactorContent())) {
					highScoFeatures = " ";
				}
				if (standSc >= sixteensf.getLowScoreMin() && standSc <= sixteensf.getLowScoreMax())
				{
					hlDec = "低分";
				}
				else if (standSc >= sixteensf.getHightScoreMin() && standSc <= sixteensf.getHightScoreMax())
				{
					hlDec = "高分";
				}
				else
				{
					hlDec = "中间状态";
				}
			}
			//写用户分析表
			BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity = new BizSixteenUserAnalysisEntity();
			bizSixteenUserAnalysisEntity.setUserName(bizSixteenUserInfoEntity.getUserName());
			bizSixteenUserAnalysisEntity.setFactor(factor);
			bizSixteenUserAnalysisEntity.setFactorId(keys);
			bizSixteenUserAnalysisEntity.setScore(score);
			bizSixteenUserAnalysisEntity.setStatusScore(score);
			bizSixteenUserAnalysisEntity.setFactorType("8SF");
			bizSixteenUserAnalysisEntity.setStatus(hlDec);
			bizSixteenUserAnalysisEntity.setLowScoreFeatures(lowScoFeatures);
			bizSixteenUserAnalysisEntity.setHighScoreFeatures(highScoFeatures);
			bizSixteenUserAnalysisEntity.setDate(DateUtils.getShortDateStr());
			bizSixteenUserAnalysisEntity.setAnswerDuration(DateUtils.getShortDateStr());
			bizSixteenUserAnalysisEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserAnalysisEntity.toString(),  ConstantValue.md5Key));
			bizSixteenUserAnalysisEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserAnalysisEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
			log.debug("==>>开始写用户分析记录表");
			boolean result = super.save(bizSixteenUserAnalysisEntity);
			BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		}

		//*******************************************// 
		//从UserAnalysis表中获取数据，为数据展示提供数据
		QueryWrapper<BizSixteenUserAnalysisEntity>  anabizSfQueryWrapper = new QueryWrapper<BizSixteenUserAnalysisEntity>();
		anabizSfQueryWrapper.lambda().eq(BizSixteenUserAnalysisEntity::getTenantId, bizSixteenUserInfoEntity.getTenantId());
		anabizSfQueryWrapper.lambda().eq(BizSixteenUserAnalysisEntity::getUserName, bizSixteenUserInfoEntity.getUserName());
		List<BizSixteenUserAnalysisEntity>  userAnalysisList = bizSixteenUserAnalysisMapper.selectList(anabizSfQueryWrapper);
		//计算时长
		A = 0;
		B = 0;
		C = 0;
		E = 0;
		F = 0;
		G = 0;
		H = 0;
		I = 0;
		L = 0;
		M = 0;
		N = 0;
		O = 0;
		Q1 = 0;
		Q2 = 0;
		Q3 = 0;
		Q4 = 0;
		X1 = 0;
		X2 = 0;
		X3 = 0;
		X4 = 0;
		Y1 = 0;
		Y2 = 0;
		Y3 = 0;
		Y4 = 0;
		Hashtable score = new Hashtable();
		for (BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity:userAnalysisList)
		{
			String factorType = bizSixteenUserAnalysisEntity.getFactorId();
			float scores = bizSixteenUserAnalysisEntity.getStatusScore();
			score.put(factorType, scores);
			// Response.Write("FactorID="+factorType+"<br/>");
			if (factorType.equals("A"))
			{
				A = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("B"))
			{
				B = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("C"))
			{
				C = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("E"))
			{
				E = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("F"))
			{
				F = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("G"))
			{
				G = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("H"))
			{
				H = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("I"))
			{
				I = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("L"))
			{
				L = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("M"))
			{
				M = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("N"))
			{
				N = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("O"))
			{
				O = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Q1"))
			{
				Q1 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Q2"))
			{
				Q2 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Q3"))
			{
				Q3 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Q4"))
			{
				Q4 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("X1"))
			{
				X1 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("X2"))
			{
				X2 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("X3"))
			{
				X3 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("X4"))
			{
				X4 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Y1"))
			{
				Y1 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Y2"))
			{
				Y2 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Y3"))
			{
				Y3 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
			else if (factorType.equals("Y4"))
			{
				Y4 = bizSixteenUserAnalysisEntity.getStatusScore();
			}
		}
		//计算方法A
		float mA = Y1 + Y2 + Y3 / 2 - 60;
		//计算方法B
		float mB = A + 2 * B + 2 * C + 2 * G + H + I - L + M - O + Q1 + Q2 + Q3 - Q4;
		//判断高压线，不能进入面试
		String noFaceexm = "";
		int noFaceexmCnt = 0;
		if (mA < 51) //使用方法A、方法B分别计算16PF总分，如果有一个16PF总分不及格，则不能进入面试。
		{
			noFaceexm += "&nbsp;&nbsp;方法A，心理健康因素、专业有成就性格因素、创造力强性格因素等综合因素不合格";
			noFaceexmCnt++;
		}
		else if (mB < 50)
		{
			noFaceexm += "&nbsp;&nbsp;方法B，有恒性、稳定性、聪慧性等综合因素不合格";
			noFaceexmCnt++;
		}
		else if (G <= 4) //有恒性小于等于4，则不能进入面试
		{
			noFaceexm += "&nbsp;&nbsp;有恒性小于等于4，建议不能进入面试";
			noFaceexmCnt++;
		}
		else if (C <= 4)//稳定性小于等于4，则不能进入面试
		{
			noFaceexm += "&nbsp;&nbsp;稳定性小于等于4，建议不能进入面试";
			noFaceexmCnt++;
		}
		else if (Y1 <= 4)//心理健康因素小于等于19，则不能进入面试
		{
			noFaceexm += "&nbsp;&nbsp;心理健康因素小于等于19，建议不能进入面试";
			noFaceexmCnt++;
		}
		// 判断区间：
		int notInAreaCnt = 0;
		String notInDesc = "";
		Hashtable areaHas = new Hashtable();
		areaHas.put("A", "乐群性,3-10");
		areaHas.put("B", "聪慧性,7-10");
		areaHas.put("C", "稳定性,7-10");
		areaHas.put("E", "恃强性,5-8");
		areaHas.put("F", "兴奋性,5-9");
		areaHas.put("G", "有恒性,6-10");
		areaHas.put("H", "敢为性,4-10");
		areaHas.put("I", "敏感性,3-6");
		areaHas.put("L", "怀疑性,2-5");
		areaHas.put("M", "幻想性,3-7");
		areaHas.put("N", "世故性,3-7");
		areaHas.put("O", "忧虑性,1-5");
		areaHas.put("Q1", "实验性,4-7");
		areaHas.put("Q2", "独立性,3-6");
		areaHas.put("Q3", "自律性,6-10");
		areaHas.put("Q4", "紧张性,1-5");
		areaHas.put("X1", "适应与焦虑型,1-5");
		areaHas.put("X2", "内向与外向型,5-10");
		areaHas.put("X3", "感情用事与安详机警型,6-10");
		areaHas.put("X4", "怯懦与果断型,4.1-10");
		areaHas.put("Y1", "心理健康因素,25-100");
		areaHas.put("Y2", "专业有成就者个性因素,58-100");
		areaHas.put("Y3", "创造能力个性因素,80-100");
		areaHas.put("Y4", "新环境中有成长能力个性因素,24-100");
		List<String> akeys = sortKeys(areaHas);
		for (String key : akeys)//由键找值
		{
			String[] list = areaHas.get(key).toString().split(",");
			String FctName = list[0];
			String[] scoreArea = list[1].split("-");
			float minSrc = Float.parseFloat(scoreArea[0]);
			float maxSrc = Float.parseFloat(scoreArea[1]);
			float currSrc = Float.parseFloat(score.get(key).toString());
			if (currSrc < minSrc || currSrc > maxSrc)
			{
				notInAreaCnt++;
				notInDesc += "&nbsp;&nbsp;" + notInAreaCnt + "). " + key + "-" + FctName + ",得分:" + currSrc + "分，不在推荐值范围内,推荐值[" + minSrc + "," + maxSrc + "]<br/>";
			}
		}
		//Response.Write(notInDesc);
		//判断是否要重点关注

		String Care = "";
		String imCare = "";
		int CareCnt = 0, imCareCnt = 0;
		if (A == 1)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). A-乐群性,得分:" + A + "分,需要面试官关注<br/>";

		}
		if (B >= 3 && B <= 4)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). B-聪慧性,得分:" + B + "分,需要面试官关注<br/>";

		}
		else if (B >= 1 && B <= 2)
		{
			imCareCnt++;
			imCare += "&nbsp;&nbsp;" + imCareCnt + "). B-聪慧性,得分:" + B + "分,需要面试官重点关注<br/>";

		}
		if (C == 5)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). C-稳定性,得分:" + C + "分,需要面试官关注<br/>";

		}
		if (G == 5)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). G-有恒性,得分:" + G + "分,需要面试官关注<br/>";

		}
		if (I >= 8 && I <= 10)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). I-敏感性,得分:" + I + "分,需要面试官关注<br/>";

		}
		if (L >= 8 && L <= 10)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). L-怀疑性,得分:" + L + "分,需要面试官关注<br/>";

		}
		if (O >= 7 && O <= 10)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). O-忧虑性,得分:" + O + "分,需要面试官关注<br/>";

		}
		if (Q3 >= 3 && Q3 <= 4)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). Q3-自律性,得分:" + Q3 + "分,需要面试官关注<br/>";

		}
		else if (Q3 >= 1 && Q3 <= 2)
		{
			imCareCnt++;
			imCare += "&nbsp;&nbsp;" + imCareCnt + "). Q3-自律性,得分:" + Q3 + "分,需要面试官重点关注<br/>";

		}
		if (Q4 >= 7 && Q4 <= 8)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). Q4-紧张性,得分:" + Q4 + "分,需要面试官关注<br/>";

		}
		else if (Q4 >= 9 && Q4 <= 10)
		{
			imCareCnt++;
			imCare += "&nbsp;&nbsp;" + imCareCnt + "). Q4-紧张性,得分:" + Q4 + "分,需要面试官重点关注<br/>";

		}
		if (X1 >= 7 && X1 <= 8)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). X1-适应与焦虑型,得分:" + X1 + "分,需要面试官关注<br/>";

		}
		else if (X1 >= 9)
		{
			imCareCnt++;
			imCare += "&nbsp;&nbsp;" + imCareCnt + "). X1-适应与焦虑型,得分:" + X1 + "分,需要面试官重点关注<br/>";

		}
		if (X2 < 2)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). X2-内向与外向型,得分:" + X2 + "分,需要面试官关注<br/>";

		}
		if (X3 >= 2 && X3 <= 4)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). X3-感情用事与安详机警型,得分:" + X3 + "分,需要面试官关注<br/>";

		}
		else if (X3 < 2)
		{
			imCareCnt++;
			imCare += "&nbsp;&nbsp;" + imCareCnt + "). X3-感情用事与安详机警型,得分:" + X3 + "分,需要面试官重点关注<br/>";

		}
		if (Y1 >= 20 && Y1 <= 21)
		{
			CareCnt++;
			Care += "&nbsp;&nbsp;" + CareCnt + "). Y1-心理健康因素,得分:" + Y1 + "分,需要面试官关注<br/>";

		}
		String AnalyStatus = "";
		String AnalyDesc = "";
		//不能进入面试
		if (noFaceexmCnt > 0)
		{
			AnalyStatus = "A";
			AnalyDesc = "该考生已触及高压线，建议不能进入面试！";

		}
		else if (notInAreaCnt > 10)
		{
			AnalyStatus = "B";
			AnalyDesc = " 该考生16PF大于10项不在推荐区间内，需要面试官重点关注！";
		}
		else if (notInAreaCnt > 6)
		{
			AnalyStatus = "C";
			AnalyDesc = " 该考生16PF人格因素大于6项不在推荐区间内，需要面试官关注！";

		}
		else if (notInAreaCnt > 0)
		{
			AnalyStatus = "D";
			AnalyDesc = " 该考生各项16PF综合指标合格，建议关注以下注意事项后再录取！";

		}
		else if (notInAreaCnt == 0)
		{
			AnalyStatus = "E";
			AnalyDesc = " 该考生各项16PF指标合格，建议录取！";

		}

		//********************************************//     
		//答题完成修改考生信息表
		bizSixteenUserInfoEntity.setAnalysisStatus(AnalyStatus);
		bizSixteenUserInfoEntity.setAnalysisDesc(AnalyDesc);
		bizSixteenUserInfoMapper.updateById(bizSixteenUserInfoEntity);

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	public List<String> sortKeys(Hashtable hashtable){
		List<String> akeys = new ArrayList();
		Enumeration e1 = hashtable.keys();
		while( e1.hasMoreElements() ) {
			String keys = e1.nextElement().toString();
			akeys.add(keys);
		}

		Collections.sort(akeys);//按字母顺序进行排序
		return akeys;
	}
	@Override
	public CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> saveBizSixteenUserAnalysisList(CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> commonMsg) {
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
	@CachePut(value = "bizSixteenUserAnalysis", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> updateBizSixteenUserAnalysis(CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> queryCommonMsg = new CommonMsg<BizSixteenUserAnalysisEntity, NullEntity>();
		//定义singleBody
		BizSixteenUserAnalysisEntity singleBody = new BizSixteenUserAnalysisEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<BizSixteenUserAnalysisEntity, NullEntity> mutBean= new MutBean<BizSixteenUserAnalysisEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(singleBody);
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(BizSixteenUserAnalysisServiceImpl.class).queryBizSixteenUserAnalysis(queryCommonMsg); 
		
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
	@CachePut(value = "bizSixteenUserAnalysis", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> saveOrUpdateBizSixteenUserAnalysis(CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> commonMsg) {
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
		CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> queryCommonMsg = new CommonMsg<BizSixteenUserAnalysisEntity, NullEntity>();
		//定义singleBody
		BizSixteenUserAnalysisEntity singleBody = new BizSixteenUserAnalysisEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(!StringUtil.isEmptyAndZero(commonMsg.getBody().getSingleBody().getId())){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
			//定义body
			MutBean<BizSixteenUserAnalysisEntity, NullEntity> mutBean= new MutBean<BizSixteenUserAnalysisEntity, NullEntity>();
			FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
			mutBean.setSingleBody(singleBody);
			mutBean.setFlowArea(flowArea);
			//深拷贝数据进行AOP查询
			queryCommonMsg.setHead(commonMsg.getHead());
			queryCommonMsg.setBody(mutBean);
			queryCommonMsg = SpringUtil.getBean(BizSixteenUserAnalysisServiceImpl.class).queryBizSixteenUserAnalysis(queryCommonMsg);

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
	public CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> saveOrUpdateBizSixteenUserAnalysisList(CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> commonMsg) {
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
    @CacheEvict(value = "bizSixteenUserAnalysis", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> deleteBizSixteenUserAnalysis(CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> deleteBizSixteenUserAnalysisList(CommonMsg<NullEntity, BizSixteenUserAnalysisEntity> commonMsg) {
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
	public void exportBizSixteenUserAnalysisExcel(CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> commonMsg, HttpServletResponse response) {
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
		
		QueryWrapper<BizSixteenUserAnalysisEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizSixteenUserAnalysisEntity.class);
		
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
		Page<BizSixteenUserAnalysisEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizSixteenUserAnalysisEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		Date start = new Date();
		String sheetName = "用户结果分析表";
		String fileName = "用户结果分析表-"+start.getTime();
		ExportParams params = new ExportParams();
		Workbook workbook = ExcelExportUtil.exportExcel(params, BizSixteenUserAnalysisEntity.class, commonMsg.getBody().getListBody());
		//导出Excel数据流
		try {
			ExcelUtil.exportPoi(response,workbook,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("====exportBizSixteenUserAnalysisExcel service end == " + RespStatus.json.getString("S0000"));
	}
	/**
	* 导入excel新增
	*/
	@Override
	public CommonMsg<BizSixteenUserAnalysisEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		CommonMsg<BizSixteenUserAnalysisEntity,NullEntity> commonMsg = new CommonMsg<BizSixteenUserAnalysisEntity,NullEntity>();
		HeadEntity head =new HeadEntity();
		commonMsg.setHead(head);
		commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
		commonMsg.getHead().setUserName(request.getHeader("username"));
		ImportParams params = new ImportParams();
		params.setTitleRows(0);
		List<BizSixteenUserAnalysisEntity> list = new ArrayList<>();
		try {
			commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
			list = ExcelImportUtil.importExcel(file.getInputStream(), BizSixteenUserAnalysisEntity.class, params);
		} catch (Exception e) {
			e.printStackTrace();
	    	map.put("E0002","导入出现异常！");
	    	commonMsg.getHead().setRespCode(RespStatus.errorCode);
	    	commonMsg.getHead().setRespMsg(map);
	    	return commonMsg;
		}
		//批量导入数据并做校验
		List<BizSixteenUserAnalysisEntity> tmpList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity = list.get(i);

			//检查字段的有效性
			ValidationResult validationResult = ValidationUtils.validateEntity(bizSixteenUserAnalysisEntity);
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
				bizSixteenUserAnalysisEntity.setDataSign(DooleenMD5Util.md5(bizSixteenUserAnalysisEntity.toString(),  ConstantValue.md5Key));
				// 初始化数据
				bizSixteenUserAnalysisEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizSixteenUserAnalysisEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				tmpList.add(bizSixteenUserAnalysisEntity);
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
