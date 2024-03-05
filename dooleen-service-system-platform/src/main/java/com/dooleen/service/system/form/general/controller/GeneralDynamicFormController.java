package com.dooleen.service.system.form.general.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.ExtFormEntity;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.form.general.service.GeneralDynamicFormService;
import com.dooleen.service.system.form.general.service.impl.GeneralDynamicFormServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-24 21:41:40
 * @Description : 系统动态表单管理Controller
 * @Author : apple
 * @Update: 2020-05-24 21:41:40
 */
@Slf4j
@RestController
@Api(tags = "系统动态表单管理相关接口")
@RequestMapping("/platform/dynamicForm")
public class GeneralDynamicFormController {

	@Autowired
	private GeneralDynamicFormService dynamicFormService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryDynamicForm", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<JSONObject, ExtFormEntity> queryDynamicForm(
			@RequestBody CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		//给extBody中的bizCode赋值，主要用于获取cache
		commonMsg.getBody().getExtBody().setBizCode(commonMsg.getBody().getSingleBody().getString("bizCode"));
		commonMsg.getBody().getExtBody().setId(commonMsg.getBody().getSingleBody().getString("id"));
		// 调用服务
		commonMsg = dynamicFormService.queryDynamicForm(commonMsg);
		log.info("====queryDynamicForm end ==== ");
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
	@ApiOperation(value = "根据条件分页查询记录", notes = "无需传参")
	@RequestMapping(value = "/queryDynamicFormListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<JSONObject, JSONObject> queryDynamicFormListPage(@RequestBody CommonMsg<JSONObject, JSONObject> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 调用服务
		commonMsg = dynamicFormService.queryDynamicFormListPage(commonMsg);
		log.info("====queryDynamicFormListPage end ==== ");
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
	@RequestMapping(value = "/saveDynamicForm", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<JSONObject, ExtFormEntity> saveDynamicForm(
			@RequestBody @Validated CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getBody().getExtBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = dynamicFormService.saveDynamicForm(commonMsg);
		log.info("====saveDynamicForm end ==== ");
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
	@RequestMapping(value = "/saveDynamicFormList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,JSONObject> saveDynamicFormList(
			@RequestBody @Validated CommonMsg<NullEntity,JSONObject> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = dynamicFormService.saveDynamicFormList(commonMsg);
		log.info("====saveDynamicFormList end ==== ");
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
	@RequestMapping(value = "/updateDynamicForm", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralDynamicFormServiceImpl.class,queryMethodName="queryDynamicForm", entityClass=JSONObject.class,  handleName = "更新")
	public CommonMsg<JSONObject, ExtFormEntity> updateDynamicForm(
			@RequestBody @Validated CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 调用服务
		commonMsg = dynamicFormService.updateDynamicForm(commonMsg);
		log.info("====updateDynamicForm end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateDynamicForm", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralDynamicFormServiceImpl.class,queryMethodName="queryDynamicForm", handleName = "更新")
	public CommonMsg<JSONObject, ExtFormEntity> saveOrUpdateDynamicForm(
			@RequestBody @Validated CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 初始化ExtBody ID，tenendID赋值
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getBody().getExtBody(),commonMsg.getHead()));

		// 调用服务
		commonMsg = dynamicFormService.saveOrUpdateDynamicForm(commonMsg);
		log.info("====saveOrUpdateDynamicForm end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateDynamicFormList", method = RequestMethod.POST)
	public CommonMsg<NullEntity,JSONObject> saveOrUpdateDynamicFormList(
			@RequestBody @Validated CommonMsg<NullEntity,JSONObject> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = dynamicFormService.saveOrUpdateDynamicFormList(commonMsg);
		log.info("====saveOrUpdateDynamicFormList end ==== ");
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
	@RequestMapping(value = "/deleteDynamicForm", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<JSONObject, ExtFormEntity> deleteDynamicForm(
			@RequestBody @Validated CommonMsg<JSONObject, ExtFormEntity> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 初始化ExtBody ID，tenendID赋值
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getBody().getExtBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = dynamicFormService.deleteDynamicForm(commonMsg);
		log.info("====deleteDynamicForm end ==== ");
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
	@RequestMapping(value = "/deleteDynamicFormList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<ExtFormEntity, JSONObject> deleteDynamicFormList(
			@RequestBody @Validated CommonMsg<ExtFormEntity, JSONObject> commonMsg) {
		CommonUtil.controllerJson(commonMsg);
		// 调用服务
		commonMsg = dynamicFormService.deleteDynamicFormList(commonMsg);
		log.info("====deleteDynamicFormList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportDynamicFormExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportDynamicFormExcel(@RequestParam("tenantId") String tenantId,@RequestParam("formId") String formId,@RequestParam("bizCode") String bizCode, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<JSONObject,JSONObject> commonMsg = new CommonMsg<JSONObject,JSONObject>();
		JSONObject singleBody = new JSONObject();
		//定义body
		MutBean<JSONObject, JSONObject> mutBean= new MutBean<JSONObject, JSONObject>();
		List<SQLConditionEntity> sqlCondition = new ArrayList();
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
		//System.out.println("bizCode == "+ bizCode);
		singleBody.put("formId", formId);
		singleBody.put("bizCode", bizCode);
		mutBean.setSingleBody(singleBody);
		mutBean.setSqlCondition(sqlCondition);
		mutBean.setCurrentPage(1);
		mutBean.setPageSize(99999999);		
		//格式化排序
		List<SQLOrderEntity> orderRule =  new ArrayList();
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
		
		CommonUtil.controllerJson(commonMsg);
		// 调用服务
		dynamicFormService.exportDynamicFormExcel(commonMsg,response);
		// 服务结束
		log.info("====exportDynamicFormExcel end ==== ");
	}    
}