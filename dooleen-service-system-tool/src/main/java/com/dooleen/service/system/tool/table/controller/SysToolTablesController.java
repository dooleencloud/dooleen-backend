package com.dooleen.service.system.tool.table.controller;

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
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.tool.table.entity.SysToolTablesEntity;
import com.dooleen.service.system.tool.table.service.SysToolTablesService;
import com.dooleen.service.system.tool.table.service.impl.SysToolTablesServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-21 23:45:59
 * @Description : 系统表Controller
 * @Author : apple
 * @Update: 2020-05-21 23:45:59
 */
@Slf4j
@RestController
@Api(tags = "系统表相关接口")
@RequestMapping("/tool/sysToolTables")
public class SysToolTablesController {

	@Autowired
	private SysToolTablesService sysToolTablesService;
	 
	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg 
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysToolTables", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysToolTablesEntity, NullEntity> querySysToolTables(
			@RequestBody CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.querySysToolTables(commonMsg);
		log.info("====querySysToolTables end ==== ");
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
	@ApiOperation(value = "查询全部记录，谨用！", notes = "无需传参")
	@RequestMapping(value = "/querySysToolTablesList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysToolTablesEntity> querySysToolTablesList(
			@RequestBody CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.querySysToolTablesList(commonMsg);
		log.info("====querySysToolTablesList end ==== ");
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
	@RequestMapping(value = "/querySysToolTablesListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> querySysToolTablesListPage(
			@RequestBody CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.querySysToolTablesListPage(commonMsg);
		log.info("====querySysToolTablesListPage end ==== ");
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
	@RequestMapping(value = "/querySysToolTablesListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> querySysToolTablesListMap(
			@RequestBody CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.querySysToolTablesListMap(commonMsg);
		log.info("====querySysToolTablesListMap end ==== ");
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
	@RequestMapping(value = "/saveSysToolTables", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysToolTablesEntity, NullEntity> saveSysToolTables(
			@RequestBody @Validated CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，datasign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysToolTablesService.saveSysToolTables(commonMsg);
		log.info("====saveSysToolTables end ==== ");
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
	@RequestMapping(value = "/saveSysToolTablesList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysToolTablesEntity> saveSysToolTablesList(
			@RequestBody @Validated CommonMsg<NullEntity,SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，datasign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysToolTablesService.saveSysToolTablesList(commonMsg);
		log.info("====saveSysToolTablesList end ==== ");
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
	@RequestMapping(value = "/updateSysToolTables", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysToolTablesServiceImpl.class,queryMethodName="querySysToolTables", handleName = "更新")
	public CommonMsg<SysToolTablesEntity, NullEntity> updateSysToolTables(
			@RequestBody @Validated CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.updateSysToolTables(commonMsg);
		log.info("====updateSysToolTables end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysToolTables", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysToolTablesServiceImpl.class,queryMethodName="querySysToolTables", handleName = "更新")
	public CommonMsg<SysToolTablesEntity, NullEntity> saveOrUpdateSysToolTables(
			@RequestBody @Validated CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，datasign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysToolTablesService.saveOrUpdateSysToolTables(commonMsg);
		log.info("====saveOrUpdateSysToolTables end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysToolTablesList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	public CommonMsg<NullEntity,SysToolTablesEntity> saveOrUpdateSysToolTablesList(
			@RequestBody @Validated CommonMsg<NullEntity,SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，datasign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysToolTablesService.saveOrUpdateSysToolTablesList(commonMsg);
		log.info("====saveOrUpdateSysToolTablesList end ==== ");
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
	@RequestMapping(value = "/deleteSysToolTables", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysToolTablesEntity, NullEntity> deleteSysToolTables(
			@RequestBody @Validated CommonMsg<SysToolTablesEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.deleteSysToolTables(commonMsg);
		log.info("====deleteSysToolTables end ==== ");
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
	@RequestMapping(value = "/deleteSysToolTablesList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysToolTablesEntity> deleteSysToolTablesList(
			@RequestBody @Validated CommonMsg<NullEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.deleteSysToolTablesList(commonMsg);
		log.info("====deleteSysToolTablesList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysToolTablesExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysToolTablesExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysToolTablesEntity,SysToolTablesEntity> commonMsg = new CommonMsg<SysToolTablesEntity,SysToolTablesEntity>();
//		SysToolTablesEntity singleBody = new SysToolTablesEntity();
		//定义body
		MutBean<SysToolTablesEntity, SysToolTablesEntity> mutBean= new MutBean<SysToolTablesEntity, SysToolTablesEntity>();
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
		sysToolTablesService.exportSysToolTablesExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysToolTablesExcel end ==== ");
	}  
	/**
	 * 生成代码
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="生成代码")
	@RequestMapping(value = "/generatorCode", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="GENERATOR")
	public CommonMsg<SysToolTablesEntity, SysToolTablesEntity> generatorCode(
			@RequestBody @Validated CommonMsg<SysToolTablesEntity, SysToolTablesEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolTablesService.generatorCode(commonMsg);
		log.info("====generatorCode end ==== ");
		return commonMsg;
	}
}