package com.dooleen.service.general.project.privilege.controller;

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
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.general.project.privilege.entity.GeneralProjectPrivilegeRelationEntity;
import com.dooleen.service.general.project.privilege.service.GeneralProjectPrivilegeRelationService;
import com.dooleen.service.general.project.privilege.service.impl.GeneralProjectPrivilegeRelationServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-07 11:20:39
 * @Description : 项目权限关系表Controller
 * @Author : apple
 * @Update: 2020-08-07 11:20:39
 */
@Slf4j
@RestController
@Api(tags = "项目权限关系表相关接口")
@RequestMapping("/general/generalProjectPrivilegeRelation")
public class GeneralProjectPrivilegeRelationController {

	@Autowired
	private GeneralProjectPrivilegeRelationService generalProjectPrivilegeRelationService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralProjectPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> queryGeneralProjectPrivilegeRelation(
			@RequestBody CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.queryGeneralProjectPrivilegeRelation(commonMsg);
		log.info("====queryGeneralProjectPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/queryGeneralProjectPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationList(
			@RequestBody CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.queryGeneralProjectPrivilegeRelationList(commonMsg);
		log.info("====queryGeneralProjectPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/queryGeneralProjectPrivilegeRelationListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationListPage(
			@RequestBody CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.queryGeneralProjectPrivilegeRelationListPage(commonMsg);
		log.info("====queryGeneralProjectPrivilegeRelationListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralProjectPrivilegeRelationListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationListMap(
			@RequestBody CommonMsg<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.queryGeneralProjectPrivilegeRelationListMap(commonMsg);
		log.info("====queryGeneralProjectPrivilegeRelationListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralProjectPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> saveGeneralProjectPrivilegeRelation(
			@RequestBody @Validated CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.saveGeneralProjectPrivilegeRelation(commonMsg);
		log.info("====saveGeneralProjectPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveGeneralProjectPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralProjectPrivilegeRelationEntity> saveGeneralProjectPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.saveGeneralProjectPrivilegeRelationList(commonMsg);
		log.info("====saveGeneralProjectPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/updateGeneralProjectPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralProjectPrivilegeRelationServiceImpl.class,queryMethodName="queryGeneralProjectPrivilegeRelation", entityClass=GeneralProjectPrivilegeRelationEntity.class, handleName = "更新")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> updateGeneralProjectPrivilegeRelation(
			@RequestBody @Validated CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.updateGeneralProjectPrivilegeRelation(commonMsg);
		log.info("====updateGeneralProjectPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralProjectPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=GeneralProjectPrivilegeRelationServiceImpl.class,queryMethodName="queryGeneralProjectPrivilegeRelation", entityClass=GeneralProjectPrivilegeRelationEntity.class, handleName = "更新")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> saveOrUpdateGeneralProjectPrivilegeRelation(
			@RequestBody @Validated CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.saveOrUpdateGeneralProjectPrivilegeRelation(commonMsg);
		log.info("====saveOrUpdateGeneralProjectPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralProjectPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=GeneralProjectPrivilegeRelationServiceImpl.class,queryMethodName="queryGeneralProjectPrivilegeRelation", entityClass=GeneralProjectPrivilegeRelationEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,GeneralProjectPrivilegeRelationEntity> saveOrUpdateGeneralProjectPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.saveOrUpdateGeneralProjectPrivilegeRelationList(commonMsg);
		log.info("====saveOrUpdateGeneralProjectPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralProjectPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> deleteGeneralProjectPrivilegeRelation(
			@RequestBody @Validated CommonMsg<GeneralProjectPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.deleteGeneralProjectPrivilegeRelation(commonMsg);
		log.info("====deleteGeneralProjectPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/deleteGeneralProjectPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> deleteGeneralProjectPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralProjectPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectPrivilegeRelationService.deleteGeneralProjectPrivilegeRelationList(commonMsg);
		log.info("====deleteGeneralProjectPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralProjectPrivilegeRelationExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralProjectPrivilegeRelationExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralProjectPrivilegeRelationEntity,GeneralProjectPrivilegeRelationEntity> commonMsg = new CommonMsg<GeneralProjectPrivilegeRelationEntity,GeneralProjectPrivilegeRelationEntity>();
		GeneralProjectPrivilegeRelationEntity singleBody = new GeneralProjectPrivilegeRelationEntity();
		//定义body
		MutBean<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity> mutBean= new MutBean<GeneralProjectPrivilegeRelationEntity, GeneralProjectPrivilegeRelationEntity>();
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
		generalProjectPrivilegeRelationService.exportGeneralProjectPrivilegeRelationExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralProjectPrivilegeRelationExcel end ==== ");
	}    
}