package com.dooleen.service.system.tenant.privilege.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.service.system.role.privilege.entity.SysRolePrivilegeRelationEntity;
import com.dooleen.service.system.role.privilege.service.impl.SysRolePrivilegeRelationServiceImpl;
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
import com.dooleen.service.system.tenant.privilege.entity.SysTenantPrivilegeRelationEntity;
import com.dooleen.service.system.tenant.privilege.service.SysTenantPrivilegeRelationService;
import com.dooleen.service.system.tenant.privilege.service.impl.SysTenantPrivilegeRelationServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 17:17:34
 * @Description : 系统租户权限关系管理Controller
 * @Author : apple
 * @Update: 2020-06-11 17:17:34
 */
@Slf4j
@RestController
@Api(tags = "系统租户权限关系管理相关接口")
@RequestMapping("/platform/sysTenantPrivilegeRelation")
public class SysTenantPrivilegeRelationController {

	@Autowired
	private SysTenantPrivilegeRelationService sysTenantPrivilegeRelationService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysTenantPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> querySysTenantPrivilegeRelation(
			@RequestBody CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.querySysTenantPrivilegeRelation(commonMsg);
		log.info("====querySysTenantPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/querySysTenantPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationList(
			@RequestBody CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.querySysTenantPrivilegeRelationList(commonMsg);
		log.info("====querySysTenantPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/querySysTenantPrivilegeRelationListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationListPage(
			@RequestBody CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.querySysTenantPrivilegeRelationListPage(commonMsg);
		log.info("====querySysTenantPrivilegeRelationListPage end ==== ");
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
	@RequestMapping(value = "/querySysTenantPrivilegeRelationListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> querySysTenantPrivilegeRelationListMap(
			@RequestBody CommonMsg<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.querySysTenantPrivilegeRelationListMap(commonMsg);
		log.info("====querySysTenantPrivilegeRelationListMap end ==== ");
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
	@RequestMapping(value = "/saveSysTenantPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> saveSysTenantPrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.saveSysTenantPrivilegeRelation(commonMsg);
		log.info("====saveSysTenantPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveSysTenantPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysTenantPrivilegeRelationEntity> saveSysTenantPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.saveSysTenantPrivilegeRelationList(commonMsg);
		log.info("====saveSysTenantPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/updateSysTenantPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysTenantPrivilegeRelationServiceImpl.class,queryMethodName="querySysTenantPrivilegeRelation", handleName = "更新")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> updateSysTenantPrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.updateSysTenantPrivilegeRelation(commonMsg);
		log.info("====updateSysTenantPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysTenantPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysTenantPrivilegeRelationServiceImpl.class,queryMethodName="querySysTenantPrivilegeRelation", handleName = "更新")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> saveOrUpdateSysTenantPrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.saveOrUpdateSysTenantPrivilegeRelation(commonMsg);
		log.info("====saveOrUpdateSysTenantPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysTenantPrivilegeRelationList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= SysTenantPrivilegeRelationServiceImpl.class,queryMethodName="querySysTenantPrivilegeRelation", entityClass= SysTenantPrivilegeRelationEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysTenantPrivilegeRelationEntity> saveOrUpdateSysTenantPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
			commonMsg.getBody().getListBody().get(i).setTenantId(commonMsg.getBody().getListBody().get(i).getUserTenantId());
		}	
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.saveOrUpdateSysTenantPrivilegeRelationList(commonMsg);
		log.info("====saveOrUpdateSysTenantPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/deleteSysTenantPrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> deleteSysTenantPrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysTenantPrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.deleteSysTenantPrivilegeRelation(commonMsg);
		log.info("====deleteSysTenantPrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/deleteSysTenantPrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> deleteSysTenantPrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity, SysTenantPrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantPrivilegeRelationService.deleteSysTenantPrivilegeRelationList(commonMsg);
		log.info("====deleteSysTenantPrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysTenantPrivilegeRelationExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysTenantPrivilegeRelationExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysTenantPrivilegeRelationEntity,SysTenantPrivilegeRelationEntity> commonMsg = new CommonMsg<SysTenantPrivilegeRelationEntity,SysTenantPrivilegeRelationEntity>();
		SysTenantPrivilegeRelationEntity singleBody = new SysTenantPrivilegeRelationEntity();
		//定义body
		MutBean<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity> mutBean= new MutBean<SysTenantPrivilegeRelationEntity, SysTenantPrivilegeRelationEntity>();
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
		sysTenantPrivilegeRelationService.exportSysTenantPrivilegeRelationExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysTenantPrivilegeRelationExcel end ==== ");
	}    
}