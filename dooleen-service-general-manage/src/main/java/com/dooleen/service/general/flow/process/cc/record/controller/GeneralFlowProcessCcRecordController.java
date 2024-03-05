package com.dooleen.service.general.flow.process.cc.record.controller;

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

import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessCcRecordEntity;
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
import com.dooleen.service.general.flow.process.cc.record.service.GeneralFlowProcessCcRecordService;
import com.dooleen.service.general.flow.process.cc.record.service.impl.GeneralFlowProcessCcRecordServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-30 09:54:05
 * @Description : 流程处理抄送记录表Controller
 * @Author : apple
 * @Update: 2020-07-30 09:54:05
 */
@Slf4j
@RestController
@Api(tags = "流程处理抄送记录表相关接口")
@RequestMapping("/general/generalFlowProcessCcRecord")
public class GeneralFlowProcessCcRecordController {

	@Autowired
	private GeneralFlowProcessCcRecordService generalFlowProcessCcRecordService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralFlowProcessCcRecord", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> queryGeneralFlowProcessCcRecord(
			@RequestBody CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.queryGeneralFlowProcessCcRecord(commonMsg);
		log.info("====queryGeneralFlowProcessCcRecord end ==== ");
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
	@RequestMapping(value = "/queryGeneralFlowProcessCcRecordList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordList(
			@RequestBody CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.queryGeneralFlowProcessCcRecordList(commonMsg);
		log.info("====queryGeneralFlowProcessCcRecordList end ==== ");
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
	@RequestMapping(value = "/queryGeneralFlowProcessCcRecordListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordListPage(
			@RequestBody CommonMsg<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.queryGeneralFlowProcessCcRecordListPage(commonMsg);
		log.info("====queryGeneralFlowProcessCcRecordListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralFlowProcessCcRecordListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordListMap(
			@RequestBody CommonMsg<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.queryGeneralFlowProcessCcRecordListMap(commonMsg);
		log.info("====queryGeneralFlowProcessCcRecordListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralFlowProcessCcRecord", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> saveGeneralFlowProcessCcRecord(
			@RequestBody @Validated CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.saveGeneralFlowProcessCcRecord(commonMsg);
		log.info("====saveGeneralFlowProcessCcRecord end ==== ");
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
	@RequestMapping(value = "/saveGeneralFlowProcessCcRecordList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> saveGeneralFlowProcessCcRecordList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.saveGeneralFlowProcessCcRecordList(commonMsg);
		log.info("====saveGeneralFlowProcessCcRecordList end ==== ");
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
	@RequestMapping(value = "/updateGeneralFlowProcessCcRecord", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralFlowProcessCcRecordServiceImpl.class,queryMethodName="queryGeneralFlowProcessCcRecord", entityClass=GeneralFlowProcessCcRecordEntity.class, handleName = "更新")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> updateGeneralFlowProcessCcRecord(
			@RequestBody @Validated CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.updateGeneralFlowProcessCcRecord(commonMsg);
		log.info("====updateGeneralFlowProcessCcRecord end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralFlowProcessCcRecord", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=GeneralFlowProcessCcRecordServiceImpl.class,queryMethodName="queryGeneralFlowProcessCcRecord", entityClass=GeneralFlowProcessCcRecordEntity.class, handleName = "更新")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> saveOrUpdateGeneralFlowProcessCcRecord(
			@RequestBody @Validated CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.saveOrUpdateGeneralFlowProcessCcRecord(commonMsg);
		log.info("====saveOrUpdateGeneralFlowProcessCcRecord end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralFlowProcessCcRecordList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=GeneralFlowProcessCcRecordServiceImpl.class,queryMethodName="queryGeneralFlowProcessCcRecord", entityClass=GeneralFlowProcessCcRecordEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> saveOrUpdateGeneralFlowProcessCcRecordList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.saveOrUpdateGeneralFlowProcessCcRecordList(commonMsg);
		log.info("====saveOrUpdateGeneralFlowProcessCcRecordList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralFlowProcessCcRecord", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> deleteGeneralFlowProcessCcRecord(
			@RequestBody @Validated CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.deleteGeneralFlowProcessCcRecord(commonMsg);
		log.info("====deleteGeneralFlowProcessCcRecord end ==== ");
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
	@RequestMapping(value = "/deleteGeneralFlowProcessCcRecordList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> deleteGeneralFlowProcessCcRecordList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalFlowProcessCcRecordService.deleteGeneralFlowProcessCcRecordList(commonMsg);
		log.info("====deleteGeneralFlowProcessCcRecordList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralFlowProcessCcRecordExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralFlowProcessCcRecordExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> commonMsg = new CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity>();
//		GeneralFlowProcessCcRecordEntity singleBody = new GeneralFlowProcessCcRecordEntity();
		//定义body
		MutBean<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity> mutBean= new MutBean<GeneralFlowProcessCcRecordEntity, GeneralFlowProcessCcRecordEntity>();
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
		generalFlowProcessCcRecordService.exportGeneralFlowProcessCcRecordExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralFlowProcessCcRecordExcel end ==== ");
	}    
}