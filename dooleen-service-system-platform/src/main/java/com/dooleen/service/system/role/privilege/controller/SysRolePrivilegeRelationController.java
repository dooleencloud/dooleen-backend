package com.dooleen.service.system.role.privilege.controller;

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
import com.dooleen.service.system.role.privilege.entity.SysRolePrivilegeRelationEntity;
import com.dooleen.service.system.role.privilege.service.SysRolePrivilegeRelationService;
import com.dooleen.service.system.role.privilege.service.impl.SysRolePrivilegeRelationServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-10 18:28:00
 * @Description : 系统角色资源权限关系管理Controller
 * @Author : apple
 * @Update: 2020-06-10 18:28:00
 */
@Slf4j
@RestController
@Api(tags = "系统角色资源权限关系管理相关接口")
@RequestMapping("/platform/sysRolePrivilegeRelation")
public class SysRolePrivilegeRelationController {

	@Autowired
	private SysRolePrivilegeRelationService sysRolePrivilegeRelationService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysRolePrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> querySysRolePrivilegeRelation(
			@RequestBody CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.querySysRolePrivilegeRelation(commonMsg);
		log.info("====querySysRolePrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/querySysRolePrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysRolePrivilegeRelationEntity> querySysRolePrivilegeRelationList(
			@RequestBody CommonMsg<NullEntity, SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.querySysRolePrivilegeRelationList(commonMsg);
		log.info("====querySysRolePrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/querySysRolePrivilegeRelationListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity> querySysRolePrivilegeRelationListPage(
			@RequestBody CommonMsg<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.querySysRolePrivilegeRelationListPage(commonMsg);
		log.info("====querySysRolePrivilegeRelationListPage end ==== ");
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
	@RequestMapping(value = "/querySysRolePrivilegeRelationListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity> querySysRolePrivilegeRelationListMap(
			@RequestBody CommonMsg<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.querySysRolePrivilegeRelationListMap(commonMsg);
		log.info("====querySysRolePrivilegeRelationListMap end ==== ");
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
	@RequestMapping(value = "/saveSysRolePrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> saveSysRolePrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.saveSysRolePrivilegeRelation(commonMsg);
		log.info("====saveSysRolePrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveSysRolePrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysRolePrivilegeRelationEntity> saveSysRolePrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.saveSysRolePrivilegeRelationList(commonMsg);
		log.info("====saveSysRolePrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/updateSysRolePrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysRolePrivilegeRelationServiceImpl.class,queryMethodName="querySysRolePrivilegeRelation", handleName = "更新")
	public CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> updateSysRolePrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.updateSysRolePrivilegeRelation(commonMsg);
		log.info("====updateSysRolePrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysRolePrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysRolePrivilegeRelationServiceImpl.class,queryMethodName="querySysRolePrivilegeRelation", handleName = "更新")
	public CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> saveOrUpdateSysRolePrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.saveOrUpdateSysRolePrivilegeRelation(commonMsg);
		log.info("====saveOrUpdateSysRolePrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysRolePrivilegeRelationList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=SysRolePrivilegeRelationServiceImpl.class,queryMethodName="querySysRolePrivilegeRelation", entityClass=SysRolePrivilegeRelationEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysRolePrivilegeRelationEntity> saveOrUpdateSysRolePrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.saveOrUpdateSysRolePrivilegeRelationList(commonMsg);
		log.info("====saveOrUpdateSysRolePrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/deleteSysRolePrivilegeRelation", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> deleteSysRolePrivilegeRelation(
			@RequestBody @Validated CommonMsg<SysRolePrivilegeRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.deleteSysRolePrivilegeRelation(commonMsg);
		log.info("====deleteSysRolePrivilegeRelation end ==== ");
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
	@RequestMapping(value = "/deleteSysRolePrivilegeRelationList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysRolePrivilegeRelationEntity> deleteSysRolePrivilegeRelationList(
			@RequestBody @Validated CommonMsg<NullEntity, SysRolePrivilegeRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysRolePrivilegeRelationService.deleteSysRolePrivilegeRelationList(commonMsg);
		log.info("====deleteSysRolePrivilegeRelationList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysRolePrivilegeRelationExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysRolePrivilegeRelationExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysRolePrivilegeRelationEntity,SysRolePrivilegeRelationEntity> commonMsg = new CommonMsg<SysRolePrivilegeRelationEntity,SysRolePrivilegeRelationEntity>();
		SysRolePrivilegeRelationEntity singleBody = new SysRolePrivilegeRelationEntity();
		//定义body
		MutBean<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity> mutBean= new MutBean<SysRolePrivilegeRelationEntity, SysRolePrivilegeRelationEntity>();
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
		sysRolePrivilegeRelationService.exportSysRolePrivilegeRelationExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysRolePrivilegeRelationExcel end ==== ");
	}    
}