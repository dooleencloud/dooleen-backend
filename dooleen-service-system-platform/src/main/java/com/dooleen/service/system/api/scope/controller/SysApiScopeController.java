package com.dooleen.service.system.api.scope.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.api.scope.entity.SysApiScopeEntity;
import com.dooleen.service.system.api.scope.service.SysApiScopeService;
import com.dooleen.service.system.api.scope.service.impl.SysApiScopeServiceImpl;
import com.dooleen.service.system.menu.service.impl.SysMenuServiceImpl;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
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
 * @CreateDate : 2020-06-10 16:26:18
 * @Description : 系统接口管理Controller
 * @Author : apple
 * @Update: 2020-06-10 16:26:18
 */
@Slf4j
@RestController
@Api(tags = "系统接口管理相关接口")
@RequestMapping("/platform/sysApiScope")
public class SysApiScopeController {

	@Autowired
	private SysApiScopeService sysApiScopeService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScope(
			@RequestBody CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScope(commonMsg);
		log.info("====querySysApiScope end ==== ");
		return commonMsg;
	}
	/**
	 * 查询用户是否具备接口权限 ,网关微服务调用查询用户是否具备接口权限。不加密
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "查询用户是否具备接口权限", notes = "")
	@RequestMapping(value = "/querySysApiScopeByUserIdAndAddress", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYBYUSERID")
	public CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScopeByUserIdAndAddress(
			@RequestBody CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScopeByUserIdAndAddress(commonMsg);
		log.info("====querySysApiScopeByUserIdAndAddress end ==== ");
		return commonMsg;
	}
	/**
	 * 查询接口分组列表
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "查询接口分组列表")
	@RequestMapping(value = "/querySysApiScopeTree", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYTREE")
	public CommonMsg<SysApiScopeEntity,JSONObject> querySysApiScopeTree(
			@RequestBody CommonMsg<SysApiScopeEntity, JSONObject> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScopeTree(commonMsg);
		log.info("====querySysApiScopeTree end ==== ");
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
	@RequestMapping(value = "/querySysApiScopeList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysApiScopeEntity> querySysApiScopeList(
			@RequestBody CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScopeList(commonMsg);
		log.info("====querySysApiScopeList end ==== ");
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
	@RequestMapping(value = "/querySysApiScopeListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	//@SentinelResource(blockHandler = "fallBacks",fallback = "sayHellofails")
	public CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListPage(
			@RequestBody CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
//		Random r = new Random();
//		int ran1 = r.nextInt(10);
//		if(ran1 % 2 == 0){
//			try {
//				throw new Exception("开始抛出异常-。。。。！");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScopeListPage(commonMsg);
		log.info("====querySysApiScopeListPage end ==== ");
		return commonMsg;
	}
//	public  CommonMsg<Object, Object> sayHellofails(@RequestBody CommonMsg<Object, Object> commonMsg,BlockException e){
//		System.out.println("====sayHellofail====sayHellofail");
//		log.info("====querySysApiScopeListPage end 2222==== ");
//		BizResponseEnum.SENTINEL_ERROR_DEGRADE.assertIsTrue(false,commonMsg);
//		return null;
//	}
//	public  CommonMsg<Object, Object> fallBacks(@RequestBody CommonMsg<Object, Object> commonMsg, BlockException e){
//		System.out.println("====fallBack====fallBack");
//		log.info("====querySysApiScopeListPage end 111111==== ");
//		BizResponseEnum.SENTINEL_ERROR_FLOW.assertIsTrue(false,commonMsg);
//		return null;
//	}
	/**
	 * 根据指定字段查询集合
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="根据指定字段查询集合")
	@RequestMapping(value = "/querySysApiScopeListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListMap(
			@RequestBody CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.querySysApiScopeListMap(commonMsg);
		log.info("====querySysApiScopeListMap end ==== ");
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
	@RequestMapping(value = "/saveSysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysApiScopeEntity, NullEntity> saveSysApiScope(
			@RequestBody @Validated CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysApiScopeService.saveSysApiScope(commonMsg);
		log.info("====saveSysApiScope end ==== ");
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
	@RequestMapping(value = "/saveSysApiScopeList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysApiScopeEntity> saveSysApiScopeList(
			@RequestBody @Validated CommonMsg<NullEntity,SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysApiScopeService.saveSysApiScopeList(commonMsg);
		log.info("====saveSysApiScopeList end ==== ");
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
	@RequestMapping(value = "/updateSysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysApiScopeServiceImpl.class,queryMethodName="querySysApiScope", handleName = "更新")
	public CommonMsg<SysApiScopeEntity, NullEntity> updateSysApiScope(
			@RequestBody @Validated CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.updateSysApiScope(commonMsg);
		log.info("====updateSysApiScope end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysApiScopeServiceImpl.class,queryMethodName="querySysApiScope", handleName = "更新")
	public CommonMsg<SysApiScopeEntity, NullEntity> saveOrUpdateSysApiScope(
			@RequestBody @Validated CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysApiScopeService.saveOrUpdateSysApiScope(commonMsg);
		log.info("====saveOrUpdateSysApiScope end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysApiScopeList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= SysMenuServiceImpl.class,queryMethodName="querySysMenu", handleName = "更新")
	public CommonMsg<NullEntity,SysApiScopeEntity> saveOrUpdateSysApiScopeList(
			@RequestBody @Validated CommonMsg<NullEntity,SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysApiScopeService.saveOrUpdateSysApiScopeList(commonMsg);
		log.info("====saveOrUpdateSysApiScopeList end ==== ");
		return commonMsg;
	}
	/**
	 * 同步接口到租户
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="同步菜单到租户")
	@RequestMapping(value = "/synchronizeSysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SYNCH",serviceclass= SysMenuServiceImpl.class,queryMethodName="querySysMenu", handleName = "更新")
	public CommonMsg<NullEntity,SysTenantInfoEntity> synchronizeSysApiScope(
			@RequestBody @Validated CommonMsg<NullEntity,SysTenantInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysApiScopeService.synchronizeSysApiScope(commonMsg);
		log.info("====sysApiScopeService end ==== ");
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
	@RequestMapping(value = "/deleteSysApiScope", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysApiScopeEntity, NullEntity> deleteSysApiScope(
			@RequestBody @Validated CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.deleteSysApiScope(commonMsg);
		log.info("====deleteSysApiScope end ==== ");
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
	@RequestMapping(value = "/deleteSysApiScopeList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysApiScopeEntity> deleteSysApiScopeList(
			@RequestBody @Validated CommonMsg<NullEntity, SysApiScopeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysApiScopeService.deleteSysApiScopeList(commonMsg);
		log.info("====deleteSysApiScopeList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysApiScopeExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysApiScopeExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysApiScopeEntity,SysApiScopeEntity> commonMsg = new CommonMsg<SysApiScopeEntity,SysApiScopeEntity>();
		SysApiScopeEntity singleBody = new SysApiScopeEntity();
		//定义body
		MutBean<SysApiScopeEntity, SysApiScopeEntity> mutBean= new MutBean<SysApiScopeEntity, SysApiScopeEntity>();
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
		sysApiScopeService.exportSysApiScopeExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysApiScopeExcel end ==== ");
	}    
}