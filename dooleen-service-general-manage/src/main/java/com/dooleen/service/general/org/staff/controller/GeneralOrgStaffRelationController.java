package com.dooleen.service.general.org.staff.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.general.org.staff.service.GeneralOrgStaffRelationService;
import com.dooleen.service.general.org.staff.service.impl.GeneralOrgStaffRelationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 10:50:02
 * @Description : 系统组织用户关系管理Controller
 * @Author : apple
 * @Update: 2020-06-11 10:50:02
 */
@Slf4j
@RestController
@Api(tags = "系统组织用户关系管理相关接口")
@RequestMapping("/general/generalOrgStaffRelation")
public class GeneralOrgStaffRelationController {

	@Autowired
	private GeneralOrgStaffRelationService generalOrgStaffRelationService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralOrgStaffRelation", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> queryGeneralOrgStaffRelation(
			@RequestBody CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.queryGeneralOrgStaffRelation(commonMsg);
		log.info("====queryGeneralOrgStaffRelation end ==== ");
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
	@RequestMapping(value = "/queryGeneralOrgStaffRelationList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationList(
			@RequestBody CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.queryGeneralOrgStaffRelationList(commonMsg);
		log.info("====queryGeneralOrgStaffRelationList end ==== ");
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
	@RequestMapping(value = "/queryGeneralOrgStaffRelationListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListPage(
			@RequestBody CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.queryGeneralOrgStaffRelationListPage(commonMsg);
		log.info("====queryGeneralOrgStaffRelationListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralOrgStaffRelationListByPage", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListByPage(
			@RequestBody CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.queryGeneralOrgStaffRelationListByPage (commonMsg);
		log.info("====queryGeneralOrgStaffRelationListByPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralOrgStaffRelationListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationListMap(
			@RequestBody CommonMsg<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.queryGeneralOrgStaffRelationListMap(commonMsg);
		log.info("====queryGeneralOrgStaffRelationListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralOrgStaffRelation", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveGeneralOrgStaffRelation(
			@RequestBody @Validated CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalOrgStaffRelationService.saveGeneralOrgStaffRelation(commonMsg);
		log.info("====saveGeneralOrgStaffRelation end ==== ");
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
	@RequestMapping(value = "/saveGeneralOrgStaffRelationList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralOrgStaffRelationEntity> saveGeneralOrgStaffRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalOrgStaffRelationService.saveGeneralOrgStaffRelationList(commonMsg);
		log.info("====saveGeneralOrgStaffRelationList end ==== ");
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
	@RequestMapping(value = "/updateGeneralOrgStaffRelation", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralOrgStaffRelationServiceImpl.class,queryMethodName="queryGeneralOrgStaffRelation", handleName = "更新")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> updateGeneralOrgStaffRelation(
			@RequestBody @Validated CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.updateGeneralOrgStaffRelation(commonMsg);
		log.info("====updateGeneralOrgStaffRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralOrgStaffRelation", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralOrgStaffRelationServiceImpl.class,queryMethodName="queryGeneralOrgStaffRelation", handleName = "更新")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveOrUpdateGeneralOrgStaffRelation(
			@RequestBody @Validated CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalOrgStaffRelationService.saveOrUpdateGeneralOrgStaffRelation(commonMsg);
		log.info("====saveOrUpdateGeneralOrgStaffRelation end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralOrgStaffRelationList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= GeneralOrgStaffRelationServiceImpl.class,queryMethodName="queryGeneralOrgStaffRelation", handleName = "更新")
	public CommonMsg<NullEntity,GeneralOrgStaffRelationEntity> saveOrUpdateGeneralOrgStaffRelationList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalOrgStaffRelationService.saveOrUpdateGeneralOrgStaffRelationList(commonMsg);
		log.info("====saveOrUpdateGeneralOrgStaffRelationList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralOrgStaffRelation", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> deleteGeneralOrgStaffRelation(
			@RequestBody @Validated CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.deleteGeneralOrgStaffRelation(commonMsg);
		log.info("====deleteGeneralOrgStaffRelation end ==== ");
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
	@RequestMapping(value = "/deleteGeneralOrgStaffRelationList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> deleteGeneralOrgStaffRelationList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralOrgStaffRelationEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalOrgStaffRelationService.deleteGeneralOrgStaffRelationList(commonMsg);
		log.info("====deleteGeneralOrgStaffRelationList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralOrgStaffRelationExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralOrgStaffRelationExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralOrgStaffRelationEntity,GeneralOrgStaffRelationEntity> commonMsg = new CommonMsg<GeneralOrgStaffRelationEntity,GeneralOrgStaffRelationEntity>();
		GeneralOrgStaffRelationEntity singleBody = new GeneralOrgStaffRelationEntity();
		//定义body
		MutBean<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity> mutBean= new MutBean<GeneralOrgStaffRelationEntity, GeneralOrgStaffRelationEntity>();
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
				log.info("====condColumns[2]===== {}", condColumns[2]);
				singleBody.setBelongOrgNo(condColumns[2]);
				sqlCondition.add(sqlConditionEntity);
			}	
		}
		mutBean.setSingleBody(singleBody);
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
		generalOrgStaffRelationService.exportGeneralOrgStaffRelationExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralOrgStaffRelationExcel end ==== ");
	}    
}