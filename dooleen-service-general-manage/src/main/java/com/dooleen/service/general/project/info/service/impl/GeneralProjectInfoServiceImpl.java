package com.dooleen.service.general.project.info.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.org.info.service.GeneralOrgInfoService;
import com.dooleen.service.general.project.info.entity.GeneralProjectInfoEntity;
import com.dooleen.service.general.project.info.mapper.GeneralProjectInfoMapper;
import com.dooleen.service.general.project.info.service.GeneralProjectInfoService;
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
 * @CreateDate : 2020-06-25 23:09:50
 * @Description : 项目信息管理服务实现
 * @Author : apple
 * @Update: 2020-06-25 23:09:50
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralProjectInfoServiceImpl extends ServiceImpl<GeneralProjectInfoMapper, GeneralProjectInfoEntity> implements GeneralProjectInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	@Autowired
	private  GeneralProjectInfoMapper generalProjectInfoMapper;

	@Autowired
	private GeneralOrgInfoService generalOrgInfoService;
	
	private  static String seqPrefix= TableEntityORMEnum.GENERAL_PROJECT_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_PROJECT_INFO.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "generalProjectInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> queryGeneralProjectInfo(CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		GeneralProjectInfoEntity generalProjectInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(generalProjectInfoEntity,commonMsg);
		commonMsg.getBody().setSingleBody(generalProjectInfoEntity);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoList(CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<GeneralProjectInfoEntity> generalProjectInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(generalProjectInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoListPage(
			CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());

		sqlConditionList.add(sqlConditionEntity);
		// 如果CreateUserName 不为空时，表示查询我的项目。我的项目先递归查找用户所在的项目组，再查找项目信息
		List<String> projectNolist = new ArrayList();
		if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody()) && !StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getCreateUserName())) {
			projectNolist = generalProjectInfoMapper.queryProjectNoByStaffId(sqlConditionEntity.getValue(), commonMsg.getHead().getUserName());
		} 
		log.info("===projectNolist===== {}", projectNolist.toString());
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if(entry.getValue() != null)
			{
				if (!entry.getKey().equals("createUserName") && !(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
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
		
		QueryWrapper<GeneralProjectInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralProjectInfoEntity.class);
		// 将项目编号列表加入查询条件
		if(!StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getCreateUserName())) {
			if(projectNolist.size() > 0) { 
				queryWrapper.in("id", projectNolist);
			}
			else {
				projectNolist.add("DOOL1002PRJI00000000");
				queryWrapper.in("id", projectNolist);
			}
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
		Page<GeneralProjectInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralProjectInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoListMap(
			CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> commonMsg) {
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
		Collection<GeneralProjectInfoEntity> generalProjectInfoEntityList =  super.listByMap(conditionMap);
		List<GeneralProjectInfoEntity> generalProjectInfoMapList = new ArrayList<GeneralProjectInfoEntity>(generalProjectInfoEntityList);
		commonMsg.getBody().setListBody(generalProjectInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "generalProjectInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> saveGeneralProjectInfo(CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
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

		// 给项目新增一条默认的组织架构：以项目名作为根的名字
		GeneralProjectInfoEntity projectInfo = commonMsg.getBody().getSingleBody();
		NullEntity nullEntity = new NullEntity();
		GeneralOrgInfoEntity generalOrgInfoEntity = new GeneralOrgInfoEntity();
		generalOrgInfoEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalOrgInfoEntity.setOrgNo(projectInfo.getProjectNo());
		generalOrgInfoEntity.setOrgName(projectInfo.getProjectName());
		generalOrgInfoEntity.setOrgFullName(projectInfo.getProjectName());
		generalOrgInfoEntity.setOrderSeq(1);
		generalOrgInfoEntity.setBelongProjectNo(projectInfo.getId());
		generalOrgInfoEntity.setParentOrgNo(projectInfo.getId());
		generalOrgInfoEntity.setOrgType("2");

		CommonMsg<GeneralOrgInfoEntity, NullEntity> orgCommonMsg =  CreateCommonMsg.getCommonMsg(generalOrgInfoEntity, nullEntity);
		orgCommonMsg.setHead(commonMsg.getHead());
		// 新增初始化ID，tenendID，dataSign
		orgCommonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(orgCommonMsg.getBody().getSingleBody(), orgCommonMsg.getHead()));
		orgCommonMsg = generalOrgInfoService.saveGeneralOrgInfo(orgCommonMsg);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(RespStatus.succCode.equals(orgCommonMsg.getHead().getRespCode()));

		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> saveGeneralProjectInfoList(CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
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
	@CachePut(value = "generalProjectInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> updateGeneralProjectInfo(CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralProjectInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralProjectInfoEntity, NullEntity>();
		//定义singleBody
		GeneralProjectInfoEntity singleBody = new GeneralProjectInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<GeneralProjectInfoEntity, NullEntity> mutBean= new MutBean<GeneralProjectInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralProjectInfoServiceImpl.class).queryGeneralProjectInfo(queryCommonMsg); 
		
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
	@CachePut(value = "generalProjectInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> saveOrUpdateGeneralProjectInfo(CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<GeneralProjectInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<GeneralProjectInfoEntity, NullEntity>();
		//定义singleBody
		GeneralProjectInfoEntity singleBody = new GeneralProjectInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<GeneralProjectInfoEntity, NullEntity> mutBean= new MutBean<GeneralProjectInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(GeneralProjectInfoServiceImpl.class).queryGeneralProjectInfo(queryCommonMsg); 
		
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
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> saveOrUpdateGeneralProjectInfoList(CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
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
    @CacheEvict(value = "generalProjectInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> deleteGeneralProjectInfo(CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
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
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> deleteGeneralProjectInfoList(CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
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
	public void exportGeneralProjectInfoExcel(CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> commonMsg, HttpServletResponse response) {
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

		QueryWrapper<GeneralProjectInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, GeneralProjectInfoEntity.class);
		
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
		Page<GeneralProjectInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<GeneralProjectInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "项目信息表";
		String fileName = "项目信息表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("项目编号");
		headList.add("项目名称");
		headList.add("项目分类");
		headList.add("项目简称");
		headList.add("需求编号");
		headList.add("需求名称");
		headList.add("主办部门");
		headList.add("主办科室");
		headList.add("协办部门");
		headList.add("建设方式");
		headList.add("实施方式");
		headList.add("项目经理");
		headList.add("项目级别");
		headList.add("优先等级");
		headList.add("预计工作量");
		headList.add("预算类目");
		headList.add("预算金额");
		headList.add("项目状态");
		headList.add("计划开始日期");
		headList.add("计划完成日期");
		headList.add("实际开始日期");
		headList.add("实际完成日期");
		headList.add("立项文档地址");
		headList.add("项目描述");
		headList.add("备注");
		excelData.add(headList);
		for (GeneralProjectInfoEntity generalProjectInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(generalProjectInfoEntity.getId());	    		
			lists.add(generalProjectInfoEntity.getTenantId());	    		
			lists.add(generalProjectInfoEntity.getProjectNo());	    		
			lists.add(generalProjectInfoEntity.getProjectName());	    		
			lists.add(generalProjectInfoEntity.getProjectCategory());	    		
			lists.add(generalProjectInfoEntity.getProjectShortName());	    		
			lists.add(generalProjectInfoEntity.getDemandNo());	    		
			lists.add(generalProjectInfoEntity.getDemandName());	    		
			lists.add(generalProjectInfoEntity.getMakeMainDeptName());
			lists.add(generalProjectInfoEntity.getMakeAssistDeptName());
			lists.add(generalProjectInfoEntity.getBizMainDeptName());
			lists.add(generalProjectInfoEntity.getBizAssistDeptName());
			lists.add(generalProjectInfoEntity.getBuildWay());	    		
			lists.add(generalProjectInfoEntity.getMakeWay());	    		
			lists.add(generalProjectInfoEntity.getProjectManagerName());	    		
			lists.add(generalProjectInfoEntity.getProjectGrade());	    		
			lists.add(String.valueOf(generalProjectInfoEntity.getPriorLevel()));	    		
			lists.add(generalProjectInfoEntity.getExpectWorkload());	    		
			lists.add(generalProjectInfoEntity.getBudgetClass());	    		
			lists.add(String.valueOf(generalProjectInfoEntity.getBudgetAmt()));	    		
			lists.add(generalProjectInfoEntity.getProjectStatus());	    		
			lists.add(generalProjectInfoEntity.getPlanBeginDate());	    		
			lists.add(generalProjectInfoEntity.getPlanFinishDate());	    		
			lists.add(generalProjectInfoEntity.getActualBeginDate());	    		
			lists.add(generalProjectInfoEntity.getActualFinishDate());	    		
			lists.add(generalProjectInfoEntity.getApprProjectFileAddress());	    		
			lists.add(generalProjectInfoEntity.getProjectDesc());	    		
			lists.add(generalProjectInfoEntity.getRemark());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
