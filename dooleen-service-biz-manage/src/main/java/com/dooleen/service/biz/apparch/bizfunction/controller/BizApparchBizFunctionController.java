package com.dooleen.service.biz.apparch.bizfunction.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.biz.apparch.bizfunction.entity.BizApparchBizFunctionEntity;
import com.dooleen.service.biz.apparch.bizfunction.service.BizApparchBizFunctionService;
import com.dooleen.service.biz.apparch.bizfunction.service.impl.BizApparchBizFunctionServiceImpl;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-28 11:30:33
 * @Description : 业务功能点管理Controller
 * @Author : apple
 * @Update: 2020-08-28 11:30:33
 */
@Slf4j
@RestController
@Api(tags = "业务功能点管理相关接口")
@ApiSupport(author = "xiaoymin@foxmail.com",order = 284)
@RequestMapping("/biz/apparch/bizApparchBizFunction")
public class BizApparchBizFunctionController {

	@Autowired
	private BizApparchBizFunctionService bizApparchBizFunctionService;

	/**
	 * 根据ID查询
	 *
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */

	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizApparchBizFunction", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizApparchBizFunctionEntity, NullEntity> queryBizApparchBizFunction(
			@RequestBody CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.queryBizApparchBizFunction(commonMsg);
		log.info("====queryBizApparchBizFunction end ==== ");
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
	@RequestMapping(value = "/queryBizApparchBizFunctionList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizApparchBizFunctionEntity> queryBizApparchBizFunctionList(
			@RequestBody CommonMsg<NullEntity, BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.queryBizApparchBizFunctionList(commonMsg);
		log.info("====queryBizApparchBizFunctionList end ==== ");
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
	@RequestMapping(value = "/queryBizApparchBizFunctionListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity> queryBizApparchBizFunctionListPage(
			@RequestBody CommonMsg<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.queryBizApparchBizFunctionListPage(commonMsg);
		log.info("====queryBizApparchBizFunctionListPage end ==== ");
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
	@RequestMapping(value = "/queryBizApparchBizFunctionListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity> queryBizApparchBizFunctionListMap(
			@RequestBody CommonMsg<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.queryBizApparchBizFunctionListMap(commonMsg);
		log.info("====queryBizApparchBizFunctionListMap end ==== ");
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
	@RequestMapping(value = "/saveBizApparchBizFunction", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizApparchBizFunctionEntity, NullEntity> saveBizApparchBizFunction(
			@RequestBody @Validated CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizApparchBizFunctionService.saveBizApparchBizFunction(commonMsg);
		log.info("====saveBizApparchBizFunction end ==== ");
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
	@RequestMapping(value = "/saveBizApparchBizFunctionList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizApparchBizFunctionEntity> saveBizApparchBizFunctionList(
			@RequestBody @Validated CommonMsg<NullEntity,BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}
		// 调用服务
		commonMsg = bizApparchBizFunctionService.saveBizApparchBizFunctionList(commonMsg);
		log.info("====saveBizApparchBizFunctionList end ==== ");
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
	@RequestMapping(value = "/updateBizApparchBizFunction", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizApparchBizFunctionServiceImpl.class,queryMethodName="queryBizApparchBizFunction", entityClass=BizApparchBizFunctionEntity.class, handleName = "更新")
	public CommonMsg<BizApparchBizFunctionEntity, NullEntity> updateBizApparchBizFunction(
			@RequestBody @Validated CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.updateBizApparchBizFunction(commonMsg);
		log.info("====updateBizApparchBizFunction end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizApparchBizFunction", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=BizApparchBizFunctionServiceImpl.class,queryMethodName="queryBizApparchBizFunction", entityClass=BizApparchBizFunctionEntity.class, handleName = "更新")
	public CommonMsg<BizApparchBizFunctionEntity, NullEntity> saveOrUpdateBizApparchBizFunction(
			@RequestBody @Validated CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizApparchBizFunctionService.saveOrUpdateBizApparchBizFunction(commonMsg);
		log.info("====saveOrUpdateBizApparchBizFunction end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizApparchBizFunctionList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizApparchBizFunctionServiceImpl.class,queryMethodName="queryBizApparchBizFunction", entityClass=BizApparchBizFunctionEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizApparchBizFunctionEntity> saveOrUpdateBizApparchBizFunctionList(
			@RequestBody @Validated CommonMsg<NullEntity,BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}
		// 调用服务
		commonMsg = bizApparchBizFunctionService.saveOrUpdateBizApparchBizFunctionList(commonMsg);
		log.info("====saveOrUpdateBizApparchBizFunctionList end ==== ");
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
	@RequestMapping(value = "/deleteBizApparchBizFunction", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizApparchBizFunctionEntity, NullEntity> deleteBizApparchBizFunction(
			@RequestBody @Validated CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.deleteBizApparchBizFunction(commonMsg);
		log.info("====deleteBizApparchBizFunction end ==== ");
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
	@RequestMapping(value = "/deleteBizApparchBizFunctionList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizApparchBizFunctionEntity> deleteBizApparchBizFunctionList(
			@RequestBody @Validated CommonMsg<NullEntity, BizApparchBizFunctionEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizApparchBizFunctionService.deleteBizApparchBizFunctionList(commonMsg);
		log.info("====deleteBizApparchBizFunctionList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportBizApparchBizFunctionExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizApparchBizFunctionExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> commonMsg = new CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity>();
		BizApparchBizFunctionEntity singleBody = new BizApparchBizFunctionEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity> mutBean= new MutBean<BizApparchBizFunctionEntity, BizApparchBizFunctionEntity>();
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
		bizApparchBizFunctionService.exportBizApparchBizFunctionExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizApparchBizFunctionExcel end ==== ");
	}    
}