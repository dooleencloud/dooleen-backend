package com.dooleen.service.general.file.history.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.history.service.GeneralFileHistoryService;
import com.dooleen.common.core.app.general.file.history.service.impl.GeneralFileHistoryServiceImpl;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
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
 * @CreateDate : 2020-06-21 07:48:23
 * @Description : 文档历史管理Controller
 * @Author : apple
 * @Update: 2020-06-21 07:48:23
 */
@Slf4j
@RestController
@Api(tags = "文档历史管理相关接口")
@RequestMapping("/general/generalFileHistory")
public class GeneralFileHistoryController {

	@Autowired
	private GeneralFileHistoryService generalFileHistoryService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralFileHistory", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralFileHistoryEntity, NullEntity> queryGeneralFileHistory(
			@RequestBody CommonMsg<GeneralFileHistoryEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.queryGeneralFileHistory(commonMsg);
		log.info("====queryGeneralFileHistory end ==== ");
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
	@RequestMapping(value = "/queryGeneralFileHistoryList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralFileHistoryEntity> queryGeneralFileHistoryList(
			@RequestBody CommonMsg<NullEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.queryGeneralFileHistoryList(commonMsg);
		log.info("====queryGeneralFileHistoryList end ==== ");
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
	@RequestMapping(value = "/queryGeneralFileHistoryListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralFileHistoryEntity, GeneralFileHistoryEntity> queryGeneralFileHistoryListPage(
			@RequestBody CommonMsg<GeneralFileHistoryEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.queryGeneralFileHistoryListPage(commonMsg);
		log.info("====queryGeneralFileHistoryListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralFileHistoryListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralFileHistoryEntity, GeneralFileHistoryEntity> queryGeneralFileHistoryListMap(
			@RequestBody CommonMsg<GeneralFileHistoryEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.queryGeneralFileHistoryListMap(commonMsg);
		log.info("====queryGeneralFileHistoryListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralFileHistory", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralFileHistoryEntity, NullEntity> saveGeneralFileHistory(
			@RequestBody @Validated CommonMsg<GeneralFileHistoryEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalFileHistoryService.saveGeneralFileHistory(commonMsg);
		log.info("====saveGeneralFileHistory end ==== ");
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
	@RequestMapping(value = "/saveGeneralFileHistoryList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralFileHistoryEntity> saveGeneralFileHistoryList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalFileHistoryService.saveGeneralFileHistoryList(commonMsg);
		log.info("====saveGeneralFileHistoryList end ==== ");
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
	@RequestMapping(value = "/updateGeneralFileHistory", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralFileHistoryServiceImpl.class,queryMethodName="queryGeneralFileHistory", handleName = "更新")
	public CommonMsg<GeneralFileHistoryEntity, NullEntity> updateGeneralFileHistory(
			@RequestBody @Validated CommonMsg<GeneralFileHistoryEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.updateGeneralFileHistory(commonMsg);
		log.info("====updateGeneralFileHistory end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralFileHistory", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralFileHistoryServiceImpl.class,queryMethodName="queryGeneralFileHistory", handleName = "更新")
	public CommonMsg<GeneralFileHistoryEntity, NullEntity> saveOrUpdateGeneralFileHistory(
			@RequestBody @Validated CommonMsg<GeneralFileHistoryEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalFileHistoryService.saveOrUpdateGeneralFileHistory(commonMsg);
		log.info("====saveOrUpdateGeneralFileHistory end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralFileHistoryList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= GeneralFileHistoryServiceImpl.class,queryMethodName="queryGeneralFileHistory", handleName = "更新")
	public CommonMsg<NullEntity,GeneralFileHistoryEntity> saveOrUpdateGeneralFileHistoryList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalFileHistoryService.saveOrUpdateGeneralFileHistoryList(commonMsg);
		log.info("====saveOrUpdateGeneralFileHistoryList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralFileHistory", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralFileHistoryEntity, NullEntity> deleteGeneralFileHistory(
			@RequestBody @Validated CommonMsg<GeneralFileHistoryEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.deleteGeneralFileHistory(commonMsg);
		log.info("====deleteGeneralFileHistory end ==== ");
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
	@RequestMapping(value = "/deleteGeneralFileHistoryList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralFileHistoryEntity> deleteGeneralFileHistoryList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralFileHistoryEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFileHistoryService.deleteGeneralFileHistoryList(commonMsg);
		log.info("====deleteGeneralFileHistoryList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralFileHistoryExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralFileHistoryExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralFileHistoryEntity,GeneralFileHistoryEntity> commonMsg = new CommonMsg<GeneralFileHistoryEntity,GeneralFileHistoryEntity>();
//		GeneralFileHistoryEntity singleBody = new GeneralFileHistoryEntity();
		//定义body
		MutBean<GeneralFileHistoryEntity, GeneralFileHistoryEntity> mutBean= new MutBean<GeneralFileHistoryEntity, GeneralFileHistoryEntity>();
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
		generalFileHistoryService.exportGeneralFileHistoryExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralFileHistoryExcel end ==== ");
	}    
}