package com.dooleen.service.system.tool.dict.field.controller;

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
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.tool.dict.field.entity.SysToolDictFieldEntity;
import com.dooleen.service.system.tool.dict.field.service.SysToolDictFieldService;
import com.dooleen.service.system.tool.dict.field.service.impl.SysToolDictFieldServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-29 23:28:56
 * @Description : 数据标准变量服务Controller
 * @Author : apple
 * @Update: 2020-05-29 23:28:56
 */
@Slf4j
@RestController
@Api(tags = "数据标准变量服务相关接口")
@RequestMapping("/tool/sysToolDictField")
public class SysToolDictFieldController {

	@Autowired
	private SysToolDictFieldService sysToolDictFieldService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysToolDictField", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> querySysToolDictField(
			@RequestBody CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.querySysToolDictField(commonMsg);
		log.info("====querySysToolDictField end ==== ");
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
	@RequestMapping(value = "/querySysToolDictFieldList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysToolDictFieldEntity> querySysToolDictFieldList(
			@RequestBody CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.querySysToolDictFieldList(commonMsg);
		log.info("====querySysToolDictFieldList end ==== ");
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
	@RequestMapping(value = "/querySysToolDictFieldListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> querySysToolDictFieldListPage(
			@RequestBody CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.querySysToolDictFieldListPage(commonMsg);
		log.info("====querySysToolDictFieldListPage end ==== ");
		return commonMsg;
	}
	
	/**
	 * 根据条件模糊匹配
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "根据条件分页查询记录")
	@RequestMapping(value = "/querySysToolDictFieldListPage", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
    //@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public List<JSONObject> querySysToolDictFieldListPage(@RequestParam("columnComment") String columnComment, @RequestParam("tenantId") String tenantId) {
		CommonMsg<SysToolDictFieldEntity,SysToolDictFieldEntity> commonMsg = new CommonMsg<SysToolDictFieldEntity,SysToolDictFieldEntity>();
//		SysToolDictFieldEntity singleBody = new SysToolDictFieldEntity();
		//定义body
		MutBean<SysToolDictFieldEntity, SysToolDictFieldEntity> mutBean= new MutBean<SysToolDictFieldEntity, SysToolDictFieldEntity>();
		List<SQLConditionEntity> sqlCondition = new ArrayList<SQLConditionEntity>();
		//格式化查询条件
		if(StringUtil.isEmpty(columnComment)) {;
			SQLConditionEntity sqlConditionEntity= new SQLConditionEntity();
			sqlConditionEntity.setColumn("columnComment");
			sqlConditionEntity.setType("like");
			sqlConditionEntity.setValue(columnComment);
			sqlCondition.add(sqlConditionEntity);
		}
		else {
			// 如果查询条件columnComment为空时 直接返回ID字段数据数据。
			SQLConditionEntity sqlConditionEntity= new SQLConditionEntity();
			sqlConditionEntity.setColumn("columnComment");
			sqlConditionEntity.setType("=");
			sqlConditionEntity.setValue("id");
			sqlCondition.add(sqlConditionEntity);
		}
		mutBean.setSqlCondition(sqlCondition);
		mutBean.setCurrentPage(1);
		mutBean.setPageSize(99999999);		
		//格式化排序
		List<SQLOrderEntity> orderRule =  new ArrayList<SQLOrderEntity>();
		mutBean.setOrderRule(orderRule );
		HeadEntity head =new HeadEntity();
		head.setTenantId(tenantId);
		commonMsg.setHead(head);
		commonMsg.setBody(mutBean); 
		
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.querySysToolDictFieldListPage(commonMsg);
		// 转换格式
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for(SysToolDictFieldEntity sysToolDictFieldEntity: commonMsg.getBody().getListBody()) {
			String valueStr = sysToolDictFieldEntity.getColumnName() 
					+ "||" + sysToolDictFieldEntity.getColumnComment()
					+ "||" + sysToolDictFieldEntity.getDataType()
					+ "||" + sysToolDictFieldEntity.getLength()
					+ "||" + sysToolDictFieldEntity.getDecimalLength();
			JSONObject reObj = new JSONObject();
			reObj.put("columnComment",sysToolDictFieldEntity.getColumnComment());
			reObj.put("columnName",valueStr);
			jsonList.add(reObj);
		}
		log.info("====querySysToolDictFieldListPage by key end ==== ");
		return jsonList;
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
	@RequestMapping(value = "/querySysToolDictFieldListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> querySysToolDictFieldListMap(
			@RequestBody CommonMsg<SysToolDictFieldEntity, SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.querySysToolDictFieldListMap(commonMsg);
		log.info("====querySysToolDictFieldListMap end ==== ");
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
	@RequestMapping(value = "/saveSysToolDictField", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> saveSysToolDictField(
			@RequestBody @Validated CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysToolDictFieldService.saveSysToolDictField(commonMsg);
		log.info("====saveSysToolDictField end ==== ");
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
	@RequestMapping(value = "/saveSysToolDictFieldList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysToolDictFieldEntity> saveSysToolDictFieldList(
			@RequestBody @Validated CommonMsg<NullEntity,SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysToolDictFieldService.saveSysToolDictFieldList(commonMsg);
		log.info("====saveSysToolDictFieldList end ==== ");
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
	@RequestMapping(value = "/updateSysToolDictField", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",
    				 serviceclass= SysToolDictFieldServiceImpl.class,
    				 entityClass=SysToolDictFieldEntity.class,
    				 queryMethodName="querySysToolDictField",
    				 handleName = "更新")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> updateSysToolDictField(
			@RequestBody @Validated CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.updateSysToolDictField(commonMsg);
		log.info("====updateSysToolDictField end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysToolDictField", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysToolDictFieldServiceImpl.class,queryMethodName="querySysToolDictField", handleName = "更新")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> saveOrUpdateSysToolDictField(
			@RequestBody @Validated CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysToolDictFieldService.saveOrUpdateSysToolDictField(commonMsg);
		log.info("====saveOrUpdateSysToolDictField end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysToolDictFieldList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	public CommonMsg<NullEntity,SysToolDictFieldEntity> saveOrUpdateSysToolDictFieldList(
			@RequestBody @Validated CommonMsg<NullEntity,SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysToolDictFieldService.saveOrUpdateSysToolDictFieldList(commonMsg);
		log.info("====saveOrUpdateSysToolDictFieldList end ==== ");
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
	@RequestMapping(value = "/deleteSysToolDictField", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysToolDictFieldEntity, NullEntity> deleteSysToolDictField(
			@RequestBody @Validated CommonMsg<SysToolDictFieldEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.deleteSysToolDictField(commonMsg);
		log.info("====deleteSysToolDictField end ==== ");
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
	@RequestMapping(value = "/deleteSysToolDictFieldList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysToolDictFieldEntity> deleteSysToolDictFieldList(
			@RequestBody @Validated CommonMsg<NullEntity, SysToolDictFieldEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.deleteSysToolDictFieldList(commonMsg);
		log.info("====deleteSysToolDictFieldList end ==== ");
		return commonMsg;
	}

	/**
	 * 智能生成变量
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	@ApiOperation(value ="智能生成变量")
	@RequestMapping(value = "/autoGetSysToolDictFieldList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<JSONObject, JSONObject> autoGetSysToolDictFieldList(
			@RequestBody @Validated CommonMsg<JSONObject, JSONObject> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysToolDictFieldService.autoGetSysToolDictFieldList(commonMsg);
		log.info("====autoGetSysToolDictFieldList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysToolDictFieldExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysToolDictFieldExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysToolDictFieldEntity,SysToolDictFieldEntity> commonMsg = new CommonMsg<SysToolDictFieldEntity,SysToolDictFieldEntity>();
//		SysToolDictFieldEntity singleBody = new SysToolDictFieldEntity();
		//定义body
		MutBean<SysToolDictFieldEntity, SysToolDictFieldEntity> mutBean= new MutBean<SysToolDictFieldEntity, SysToolDictFieldEntity>();
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
		sysToolDictFieldService.exportSysToolDictFieldExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysToolDictFieldExcel end ==== ");
	}    
}