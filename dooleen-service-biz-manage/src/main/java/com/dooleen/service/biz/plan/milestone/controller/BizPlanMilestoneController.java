package com.dooleen.service.biz.plan.milestone.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.biz.plan.milestone.entity.BizPlanMilestoneEntity;
import com.dooleen.service.biz.plan.milestone.service.BizPlanMilestoneService;
import com.dooleen.service.biz.plan.milestone.service.impl.BizPlanMilestoneServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-28 22:02:22
 * @Description : 里程碑信息维护Controller
 * @Author : apple
 * @Update: 2021-04-28 22:02:22
 */
@Slf4j
@RestController
@Api(tags = "里程碑信息维护相关接口")
@RequestMapping("/biz/plan/bizPlanMilestone")
public class BizPlanMilestoneController {

	@Autowired
	private BizPlanMilestoneService bizPlanMilestoneService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizPlanMilestone", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizPlanMilestoneEntity, NullEntity> queryBizPlanMilestone(
			@RequestBody CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.queryBizPlanMilestone(commonMsg);
		log.info("====queryBizPlanMilestone end ==== ");
		return commonMsg;
	}

	/**
	 * 查询全部记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "查询全部记录，谨用！")
	@RequestMapping(value = "/queryBizPlanMilestoneList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizPlanMilestoneEntity> queryBizPlanMilestoneList(
			@RequestBody CommonMsg<NullEntity, BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.queryBizPlanMilestoneList(commonMsg);
		log.info("====queryBizPlanMilestoneList end ==== ");
		return commonMsg;
	}

	/**
	 * 根据条件分页查询记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "根据条件分页查询记录")
	@RequestMapping(value = "/queryBizPlanMilestoneListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizPlanMilestoneEntity, BizPlanMilestoneEntity> queryBizPlanMilestoneListPage(
			@RequestBody CommonMsg<BizPlanMilestoneEntity, BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.queryBizPlanMilestoneListPage(commonMsg);
		log.info("====queryBizPlanMilestoneListPage end ==== ");
		return commonMsg;
	}

	/**
	 * 根据指定字段查询集合
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="根据指定字段查询集合")
	@RequestMapping(value = "/queryBizPlanMilestoneListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizPlanMilestoneEntity, BizPlanMilestoneEntity> queryBizPlanMilestoneListMap(
			@RequestBody CommonMsg<BizPlanMilestoneEntity, BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.queryBizPlanMilestoneListMap(commonMsg);
		log.info("====queryBizPlanMilestoneListMap end ==== ");
		return commonMsg;
	}
	 
	/**
	 * 新增记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="新增记录")
	@RequestMapping(value = "/saveBizPlanMilestone", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizPlanMilestoneEntity, NullEntity> saveBizPlanMilestone(
			@RequestBody @Validated CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizPlanMilestoneService.saveBizPlanMilestone(commonMsg);
		log.info("====saveBizPlanMilestone end ==== ");
		return commonMsg;
	}
	
	/**
	 * 批量新增记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="批量新增记录")
	@RequestMapping(value = "/saveBizPlanMilestoneList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizPlanMilestoneEntity> saveBizPlanMilestoneList(
			@RequestBody @Validated CommonMsg<NullEntity,BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizPlanMilestoneService.saveBizPlanMilestoneList(commonMsg);
		log.info("====saveBizPlanMilestoneList end ==== ");
		return commonMsg;
	}
	

	/**
	 * 更新单条记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="更新单条记录")
	@RequestMapping(value = "/updateBizPlanMilestone", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizPlanMilestoneServiceImpl.class,queryMethodName="queryBizPlanMilestone", entityClass=BizPlanMilestoneEntity.class, handleName = "更新")
	public CommonMsg<BizPlanMilestoneEntity, NullEntity> updateBizPlanMilestone(
			@RequestBody @Validated CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.updateBizPlanMilestone(commonMsg);
		log.info("====updateBizPlanMilestone end ==== ");
		return commonMsg;
	}
	 
	/**
	 * 新增或者更新记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="新增或者更新记录")
	@RequestMapping(value = "/saveOrUpdateBizPlanMilestone", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=BizPlanMilestoneServiceImpl.class,queryMethodName="queryBizPlanMilestone", entityClass=BizPlanMilestoneEntity.class, handleName = "更新")
	public CommonMsg<BizPlanMilestoneEntity, NullEntity> saveOrUpdateBizPlanMilestone(
			@RequestBody @Validated CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizPlanMilestoneService.saveOrUpdateBizPlanMilestone(commonMsg);
		log.info("====saveOrUpdateBizPlanMilestone end ==== ");
		return commonMsg;
	}
	/**
	 * 批量新增或更新记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="批量新增或更新记录")
	@RequestMapping(value = "/saveOrUpdateBizPlanMilestoneList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizPlanMilestoneServiceImpl.class,queryMethodName="queryBizPlanMilestone", entityClass=BizPlanMilestoneEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizPlanMilestoneEntity> saveOrUpdateBizPlanMilestoneList(
			@RequestBody @Validated CommonMsg<NullEntity,BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizPlanMilestoneService.saveOrUpdateBizPlanMilestoneList(commonMsg);
		log.info("====saveOrUpdateBizPlanMilestoneList end ==== ");
		return commonMsg;
	}
	

	/**
	 * 根据ID删除记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="根据ID删除记录")
	@RequestMapping(value = "/deleteBizPlanMilestone", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizPlanMilestoneEntity, NullEntity> deleteBizPlanMilestone(
			@RequestBody @Validated CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.deleteBizPlanMilestone(commonMsg);
		log.info("====deleteBizPlanMilestone end ==== ");
		return commonMsg;
	}
	 

	/**
	 * 根据ID批量删除记录
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="根据ID批量删除记录")
	@RequestMapping(value = "/deleteBizPlanMilestoneList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizPlanMilestoneEntity> deleteBizPlanMilestoneList(
			@RequestBody @Validated CommonMsg<NullEntity, BizPlanMilestoneEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizPlanMilestoneService.deleteBizPlanMilestoneList(commonMsg);
		log.info("====deleteBizPlanMilestoneList end ==== ");
		return commonMsg;
	}
	
	/**
	 * 导出excel记录
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param tenantId
	 * @Param condition
	 * @Param orders
	 * @Return 
	 */
	@ApiOperation(value ="导出excel记录")
	@RequestMapping(value = "/excelDownload/exportBizPlanMilestoneExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizPlanMilestoneExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> commonMsg = new CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity>();
		BizPlanMilestoneEntity singleBody = new BizPlanMilestoneEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizPlanMilestoneEntity, BizPlanMilestoneEntity> mutBean= new MutBean<BizPlanMilestoneEntity, BizPlanMilestoneEntity>();
		mutBean.setSingleBody(singleBody);
		List<SQLConditionEntity> sqlCondition = new ArrayList<SQLConditionEntity>();

		//格式化查询条件
		if(condition != null && !condition.trim().equals("")) {
			String[] conditions = condition.split(";");
			for(int i = 0; i < conditions.length; i++) {
				String[] condColumns = conditions[i].split(",");
				SQLConditionEntity sqlConditionEntity= new SQLConditionEntity();
				sqlConditionEntity.setColumn(condColumns[0]);
				sqlConditionEntity.setType(condColumns[1]);
				sqlConditionEntity.setValue(condColumns[2]);
				sqlCondition.add(sqlConditionEntity);
			}	
		}
		mutBean.setSqlCondition(sqlCondition);
		mutBean.setCurrentPage(1);
		mutBean.setPageSize(99999999);		
		//格式化排序
		List<SQLOrderEntity> orderRule =  new ArrayList<SQLOrderEntity>();
		if(orders != null && !orders.trim().equals("")) {
			String[] orderColumn = orders.split("-");
			SQLOrderEntity sqlOrderEntity = new SQLOrderEntity();
			sqlOrderEntity.setOrder(orderColumn[0]);
			sqlOrderEntity.setProp(orderColumn[0]);
		}
		mutBean.setOrderRule(orderRule );
		HeadEntity head =new HeadEntity();
		head.setTenantId(tenantId);
		commonMsg.setHead(head);
		commonMsg.setBody(mutBean); 
		
		CommonUtil.controller(commonMsg);
		// 调用服务
		bizPlanMilestoneService.exportBizPlanMilestoneExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizPlanMilestoneExcel end ==== ");
	}    
}