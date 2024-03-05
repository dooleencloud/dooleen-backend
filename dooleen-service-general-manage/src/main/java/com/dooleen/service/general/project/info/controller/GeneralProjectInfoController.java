package com.dooleen.service.general.project.info.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.general.project.info.entity.GeneralProjectInfoEntity;
import com.dooleen.service.general.project.info.service.GeneralProjectInfoService;
import com.dooleen.service.general.project.info.service.impl.GeneralProjectInfoServiceImpl;
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
 * @CreateDate : 2020-06-25 23:09:50
 * @Description : 项目信息管理Controller
 * @Author : apple
 * @Update: 2020-06-25 23:09:50
 */
@Slf4j
@RestController
@Api(tags = "项目信息管理相关接口")
@RequestMapping("/general/generalProjectInfo")
public class GeneralProjectInfoController {

	@Autowired
	private GeneralProjectInfoService generalProjectInfoService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralProjectInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> queryGeneralProjectInfo(
			@RequestBody CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.queryGeneralProjectInfo(commonMsg);
		log.info("====queryGeneralProjectInfo end ==== ");
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
	@RequestMapping(value = "/queryGeneralProjectInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoList(
			@RequestBody CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.queryGeneralProjectInfoList(commonMsg);
		log.info("====queryGeneralProjectInfoList end ==== ");
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
	@RequestMapping(value = "/queryGeneralProjectInfoListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoListPage(
			@RequestBody CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.queryGeneralProjectInfoListPage(commonMsg);
		log.info("====queryGeneralProjectInfoListPage end ==== ");
		return commonMsg;
	}

	/**
	 * 根据条件模糊匹配
	 */
	@ApiOperation(value = "根据条件模糊查询记录")
	@RequestMapping(value = "/queryGeneralProjectInfoListPage", method = RequestMethod.GET)
	@SecretAnnotation(decode=true,encode=true)
	//@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public List<JSONObject> queryGeneralProjectInfoListPage(@RequestParam("columnName") String columnName, @RequestParam("columnValue") String columnValue, @RequestParam("tenantId") String tenantId, @RequestParam("optFlag") String optFlag) {
		CommonMsg<GeneralProjectInfoEntity,GeneralProjectInfoEntity> commonMsg = new CommonMsg<GeneralProjectInfoEntity,GeneralProjectInfoEntity>();
		//定义body
		MutBean<GeneralProjectInfoEntity, GeneralProjectInfoEntity> mutBean= new MutBean<GeneralProjectInfoEntity, GeneralProjectInfoEntity>();
		List<SQLConditionEntity> sqlCondition = new ArrayList<SQLConditionEntity>();
		List<JSONObject> returnJsonList =new ArrayList<JSONObject>();
		//格式化查询条件
		if(!StringUtil.isEmpty(columnValue)) {;
			SQLConditionEntity sqlConditionEntity= new SQLConditionEntity();
			sqlConditionEntity.setColumn(columnName);
			sqlConditionEntity.setType("like");
			sqlConditionEntity.setValue(columnValue.trim());
			sqlCondition.add(sqlConditionEntity);
		}
		else {
			// 如果查询条件columnComment为空时 直接返回空数据。
			JSONObject reObj = new JSONObject();
			returnJsonList.add(reObj);
			return returnJsonList;
		}
		mutBean.setSqlCondition(sqlCondition);
		mutBean.setCurrentPage(1);
		mutBean.setPageSize(30);
		//格式化排序
		List<SQLOrderEntity> orderRule =  new ArrayList<SQLOrderEntity>();
		mutBean.setOrderRule(orderRule );
		mutBean.setSingleBody(new GeneralProjectInfoEntity());
		HeadEntity head =new HeadEntity();
		head.setTenantId(tenantId);
		commonMsg.setHead(head);
		commonMsg.setBody(mutBean);

		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.queryGeneralProjectInfoListPage(commonMsg);
		// 转换格式
		for(GeneralProjectInfoEntity generalProjectInfoEntity: commonMsg.getBody().getListBody()) {
			JSONObject reObj = new JSONObject();
			reObj.put("projectName",generalProjectInfoEntity.getProjectName()+"("+generalProjectInfoEntity.getProjectNo()+")");
			returnJsonList.add(reObj);
		}
		log.info("====querySysUserInfoListPage by key end ==== ");
		return returnJsonList;
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
	@RequestMapping(value = "/queryGeneralProjectInfoListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> queryGeneralProjectInfoListMap(
			@RequestBody CommonMsg<GeneralProjectInfoEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.queryGeneralProjectInfoListMap(commonMsg);
		log.info("====queryGeneralProjectInfoListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralProjectInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> saveGeneralProjectInfo(
			@RequestBody @Validated CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalProjectInfoService.saveGeneralProjectInfo(commonMsg);
		log.info("====saveGeneralProjectInfo end ==== ");
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
	@RequestMapping(value = "/saveGeneralProjectInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralProjectInfoEntity> saveGeneralProjectInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalProjectInfoService.saveGeneralProjectInfoList(commonMsg);
		log.info("====saveGeneralProjectInfoList end ==== ");
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
	@RequestMapping(value = "/updateGeneralProjectInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",
    	serviceclass= GeneralProjectInfoServiceImpl.class,
    	entityClass=GeneralProjectInfoEntity.class, 
    	queryMethodName="queryGeneralProjectInfo", 
    	handleName = "更新")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> updateGeneralProjectInfo(
			@RequestBody @Validated CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.updateGeneralProjectInfo(commonMsg);
		log.info("====updateGeneralProjectInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralProjectInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(
    		operatoinType="UPDATE",
		    serviceclass= GeneralProjectInfoServiceImpl.class,
		    queryMethodName="queryGeneralProjectInfo", 
		    handleName = "更新")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> saveOrUpdateGeneralProjectInfo(
			@RequestBody @Validated CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalProjectInfoService.saveOrUpdateGeneralProjectInfo(commonMsg);
		log.info("====saveOrUpdateGeneralProjectInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralProjectInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= GeneralProjectInfoServiceImpl.class,queryMethodName="queryGeneralProjectInfo", handleName = "更新")
	public CommonMsg<NullEntity,GeneralProjectInfoEntity> saveOrUpdateGeneralProjectInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalProjectInfoService.saveOrUpdateGeneralProjectInfoList(commonMsg);
		log.info("====saveOrUpdateGeneralProjectInfoList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralProjectInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralProjectInfoEntity, NullEntity> deleteGeneralProjectInfo(
			@RequestBody @Validated CommonMsg<GeneralProjectInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.deleteGeneralProjectInfo(commonMsg);
		log.info("====deleteGeneralProjectInfo end ==== ");
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
	@RequestMapping(value = "/deleteGeneralProjectInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralProjectInfoEntity> deleteGeneralProjectInfoList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralProjectInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalProjectInfoService.deleteGeneralProjectInfoList(commonMsg);
		log.info("====deleteGeneralProjectInfoList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralProjectInfoExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralProjectInfoExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralProjectInfoEntity,GeneralProjectInfoEntity> commonMsg = new CommonMsg<GeneralProjectInfoEntity,GeneralProjectInfoEntity>();
		GeneralProjectInfoEntity singleBody = new GeneralProjectInfoEntity();
		//定义body
		MutBean<GeneralProjectInfoEntity, GeneralProjectInfoEntity> mutBean= new MutBean<GeneralProjectInfoEntity, GeneralProjectInfoEntity>();
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
		generalProjectInfoService.exportGeneralProjectInfoExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralProjectInfoExcel end ==== ");
	}    
}