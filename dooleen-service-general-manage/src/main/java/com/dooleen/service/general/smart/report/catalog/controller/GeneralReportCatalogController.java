package com.dooleen.service.general.smart.report.catalog.controller;

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
import com.dooleen.service.general.smart.report.catalog.entity.GeneralReportCatalogEntity;
import com.dooleen.service.general.smart.report.catalog.service.GeneralReportCatalogService;
import com.dooleen.service.general.smart.report.catalog.service.impl.GeneralReportCatalogServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-05 14:49:49
 * @Description : 报表目录管理Controller
 * @Author : apple
 * @Update: 2020-08-05 14:49:49
 */
@Slf4j
@RestController
@Api(tags = "报表目录管理相关接口")
@RequestMapping("/general/generalReportCatalog")
public class GeneralReportCatalogController {

	@Autowired
	private GeneralReportCatalogService generalReportCatalogService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralReportCatalog", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> queryGeneralReportCatalog(
			@RequestBody CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.queryGeneralReportCatalog(commonMsg);
		log.info("====queryGeneralReportCatalog end ==== ");
		return commonMsg;
	}
	/**
	 * 查询目录树
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "查询目录树")
	@RequestMapping(value = "/queryReportCatalogTree", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYTREE")
	public CommonMsg<GeneralReportCatalogEntity,JSONObject> queryReportCatalogTree(
			@RequestBody CommonMsg<GeneralReportCatalogEntity, JSONObject> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		log.info("====queryReportCatalogTree end ==== ");
		return generalReportCatalogService.queryReportCatalogTree(commonMsg);
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
	@RequestMapping(value = "/queryGeneralReportCatalogList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogList(
			@RequestBody CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.queryGeneralReportCatalogList(commonMsg);
		log.info("====queryGeneralReportCatalogList end ==== ");
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
	@RequestMapping(value = "/queryGeneralReportCatalogListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogListPage(
			@RequestBody CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.queryGeneralReportCatalogListPage(commonMsg);
		log.info("====queryGeneralReportCatalogListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralReportCatalogListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> queryGeneralReportCatalogListMap(
			@RequestBody CommonMsg<GeneralReportCatalogEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.queryGeneralReportCatalogListMap(commonMsg);
		log.info("====queryGeneralReportCatalogListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralReportCatalog", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> saveGeneralReportCatalog(
			@RequestBody @Validated CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalReportCatalogService.saveGeneralReportCatalog(commonMsg);
		log.info("====saveGeneralReportCatalog end ==== ");
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
	@RequestMapping(value = "/saveGeneralReportCatalogList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralReportCatalogEntity> saveGeneralReportCatalogList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalReportCatalogService.saveGeneralReportCatalogList(commonMsg);
		log.info("====saveGeneralReportCatalogList end ==== ");
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
	@RequestMapping(value = "/updateGeneralReportCatalog", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralReportCatalogServiceImpl.class,queryMethodName="queryGeneralReportCatalog", entityClass=GeneralReportCatalogEntity.class, handleName = "更新")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> updateGeneralReportCatalog(
			@RequestBody @Validated CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.updateGeneralReportCatalog(commonMsg);
		log.info("====updateGeneralReportCatalog end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralReportCatalog", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=GeneralReportCatalogServiceImpl.class,queryMethodName="queryGeneralReportCatalog", entityClass=GeneralReportCatalogEntity.class, handleName = "更新")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> saveOrUpdateGeneralReportCatalog(
			@RequestBody @Validated CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalReportCatalogService.saveOrUpdateGeneralReportCatalog(commonMsg);
		log.info("====saveOrUpdateGeneralReportCatalog end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralReportCatalogList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=GeneralReportCatalogServiceImpl.class,queryMethodName="queryGeneralReportCatalog", entityClass=GeneralReportCatalogEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,GeneralReportCatalogEntity> saveOrUpdateGeneralReportCatalogList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalReportCatalogService.saveOrUpdateGeneralReportCatalogList(commonMsg);
		log.info("====saveOrUpdateGeneralReportCatalogList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralReportCatalog", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralReportCatalogEntity, NullEntity> deleteGeneralReportCatalog(
			@RequestBody @Validated CommonMsg<GeneralReportCatalogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.deleteGeneralReportCatalog(commonMsg);
		log.info("====deleteGeneralReportCatalog end ==== ");
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
	@RequestMapping(value = "/deleteGeneralReportCatalogList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralReportCatalogEntity> deleteGeneralReportCatalogList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralReportCatalogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalReportCatalogService.deleteGeneralReportCatalogList(commonMsg);
		log.info("====deleteGeneralReportCatalogList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralReportCatalogExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralReportCatalogExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralReportCatalogEntity,GeneralReportCatalogEntity> commonMsg = new CommonMsg<GeneralReportCatalogEntity,GeneralReportCatalogEntity>();
		GeneralReportCatalogEntity singleBody = new GeneralReportCatalogEntity();
		//定义body
		MutBean<GeneralReportCatalogEntity, GeneralReportCatalogEntity> mutBean= new MutBean<GeneralReportCatalogEntity, GeneralReportCatalogEntity>();
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
		
		CommonUtil.controller(commonMsg);
		// 调用服务
		generalReportCatalogService.exportGeneralReportCatalogExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralReportCatalogExcel end ==== ");
	}    
}