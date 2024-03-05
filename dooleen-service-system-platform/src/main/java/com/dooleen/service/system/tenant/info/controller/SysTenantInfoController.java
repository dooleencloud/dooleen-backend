package com.dooleen.service.system.tenant.info.controller;

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
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.info.service.SysTenantInfoService;
import com.dooleen.service.system.tenant.info.service.impl.SysTenantInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 15:51:08
 * @Description : 租户管理Controller
 * @Author : apple
 * @Update: 2020-06-04 15:51:08
 */
@Slf4j
@RestController
@Api(tags = "租户管理相关接口")
@RequestMapping("/platform/sysTenantInfo")
public class SysTenantInfoController {

	@Autowired
	private SysTenantInfoService sysTenantInfoService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysTenantInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysTenantInfoEntity, NullEntity> querySysTenantInfo(
			@RequestBody CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.querySysTenantInfo(commonMsg);
		log.info("====querySysTenantInfo end ==== ");
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
	@RequestMapping(value = "/querySysTenantInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysTenantInfoEntity> querySysTenantInfoList(
			@RequestBody CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.querySysTenantInfoList(commonMsg);
		log.info("====querySysTenantInfoList end ==== ");
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
	@RequestMapping(value = "/querySysTenantInfoListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> querySysTenantInfoListPage(
			@RequestBody CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.querySysTenantInfoListPage(commonMsg);
		log.info("====querySysTenantInfoListPage end ==== ");
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
	@RequestMapping(value = "/querySysTenantInfoListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> querySysTenantInfoListMap(
			@RequestBody CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.querySysTenantInfoListMap(commonMsg);
		log.info("====querySysTenantInfoListMap end ==== ");
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
	@RequestMapping(value = "/saveSysTenantInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysTenantInfoEntity, NullEntity> saveSysTenantInfo(
			@RequestBody @Validated CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		commonMsg.getBody().getSingleBody().setTenantId(commonMsg.getBody().getSingleBody().getUserTenantId());
		log.info("=========tenantId0  ==== {}", commonMsg.getBody().getSingleBody().getTenantId());
		// 调用服务 
		commonMsg = sysTenantInfoService.saveSysTenantInfo(commonMsg);
		log.info("====saveSysTenantInfo end ==== ");
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
	@RequestMapping(value = "/saveSysTenantInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysTenantInfoEntity> saveSysTenantInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
			commonMsg.getBody().getListBody().get(i).setTenantId(commonMsg.getBody().getListBody().get(i).getUserTenantId());
		}	
		// 调用服务
		commonMsg = sysTenantInfoService.saveSysTenantInfoList(commonMsg);
		log.info("====saveSysTenantInfoList end ==== ");
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
	@RequestMapping(value = "/updateSysTenantInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysTenantInfoServiceImpl.class,queryMethodName="querySysTenantInfo", handleName = "更新")
	public CommonMsg<SysTenantInfoEntity, NullEntity> updateSysTenantInfo(
			@RequestBody @Validated CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		if(commonMsg.getBody().getSingleBody().getTenantId() == null || commonMsg.getBody().getSingleBody().getTenantId().equals("")) {
			commonMsg.getBody().getSingleBody().setTenantId(commonMsg.getBody().getSingleBody().getUserTenantId());
		}
		// 调用服务
		commonMsg = sysTenantInfoService.updateSysTenantInfo(commonMsg);
		log.info("====updateSysTenantInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysTenantInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysTenantInfoServiceImpl.class,queryMethodName="querySysTenantInfo", handleName = "更新")
	public CommonMsg<SysTenantInfoEntity, NullEntity> saveOrUpdateSysTenantInfo(
			@RequestBody @Validated CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		commonMsg.getBody().getSingleBody().setTenantId(commonMsg.getBody().getSingleBody().getUserTenantId());

		// 调用服务
		commonMsg = sysTenantInfoService.saveOrUpdateSysTenantInfo(commonMsg);
		log.info("====saveOrUpdateSysTenantInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysTenantInfoList", method = RequestMethod.POST)
	public CommonMsg<NullEntity,SysTenantInfoEntity> saveOrUpdateSysTenantInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
			commonMsg.getBody().getListBody().get(i).setTenantId(commonMsg.getBody().getListBody().get(i).getUserTenantId());
		}	
		// 调用服务
		commonMsg = sysTenantInfoService.saveOrUpdateSysTenantInfoList(commonMsg);
		log.info("====saveOrUpdateSysTenantInfoList end ==== ");
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
	@RequestMapping(value = "/deleteSysTenantInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysTenantInfoEntity, NullEntity> deleteSysTenantInfo(
			@RequestBody @Validated CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.deleteSysTenantInfo(commonMsg);
		log.info("====deleteSysTenantInfo end ==== ");
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
	@RequestMapping(value = "/deleteSysTenantInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysTenantInfoEntity> deleteSysTenantInfoList(
			@RequestBody @Validated CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysTenantInfoService.deleteSysTenantInfoList(commonMsg);
		log.info("====deleteSysTenantInfoList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysTenantInfoExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysTenantInfoExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysTenantInfoEntity,SysTenantInfoEntity> commonMsg = new CommonMsg<SysTenantInfoEntity,SysTenantInfoEntity>();
		SysTenantInfoEntity singleBody = new SysTenantInfoEntity();
		//定义body
		MutBean<SysTenantInfoEntity, SysTenantInfoEntity> mutBean= new MutBean<SysTenantInfoEntity, SysTenantInfoEntity>();
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
		sysTenantInfoService.exportSysTenantInfoExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysTenantInfoExcel end ==== ");
	}    
}