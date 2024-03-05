package com.dooleen.service.general.staff.info.controller;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.staff.info.entity.GeneralStaffInfoEntity;
import com.dooleen.common.core.common.entity.*;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.general.staff.info.service.GeneralStaffInfoService;
import com.dooleen.service.general.staff.info.service.impl.GeneralStaffInfoServiceImpl;
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
 * @CreateDate : 2020-06-18 18:12:48
 * @Description : 人员管理Controller
 * @Author : apple
 * @Update: 2020-06-18 18:12:48
 */
@Slf4j
@RestController
@Api(tags = "人员管理相关接口")
@RequestMapping("/general/generalStaffInfo")
public class GeneralStaffInfoController {

	@Autowired
	private GeneralStaffInfoService generalStaffInfoService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralStaffInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> queryGeneralStaffInfo(
			@RequestBody CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.queryGeneralStaffInfo(commonMsg);
		log.info("====queryGeneralStaffInfo end ==== ");
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
	@RequestMapping(value = "/queryGeneralStaffInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoList(
			@RequestBody CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.queryGeneralStaffInfoList(commonMsg);
		log.info("====queryGeneralStaffInfoList end ==== ");
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
	@RequestMapping(value = "/queryGeneralStaffInfoListPage", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListPage(
			@RequestBody CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.queryGeneralStaffInfoListPage(commonMsg);
		log.info("====queryGeneralStaffInfoListPage end ==== ");
		return commonMsg;
	}

	/**
	 * 根据条件模糊匹配
	 */
	@ApiOperation(value = "根据条件模糊查询记录")
	@RequestMapping(value = "/queryGeneralStaffInfoListPage", method = RequestMethod.GET)
	@SecretAnnotation(decode=true,encode=true)
	//@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public List<JSONObject> queryGeneralStaffInfoListPage(@RequestParam("columnName") String columnName, @RequestParam("columnValue") String columnValue, @RequestParam("tenantId") String tenantId, @RequestParam("optFlag") String optFlag) {
		CommonMsg<GeneralStaffInfoEntity,GeneralStaffInfoEntity> commonMsg = new CommonMsg<GeneralStaffInfoEntity,GeneralStaffInfoEntity>();
		//定义body
		MutBean<GeneralStaffInfoEntity, GeneralStaffInfoEntity> mutBean= new MutBean<GeneralStaffInfoEntity, GeneralStaffInfoEntity>();
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
		HeadEntity head =new HeadEntity();
		head.setTenantId(tenantId);
		commonMsg.setHead(head);
		commonMsg.setBody(mutBean);

		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.queryGeneralStaffInfoListPage(commonMsg);
		// 转换格式
		for(GeneralStaffInfoEntity generalStaffInfoEntity: commonMsg.getBody().getListBody()) {
			JSONObject reObj = new JSONObject();
			if("org".equals(optFlag)) {
				reObj.put("userName",("".equals(generalStaffInfoEntity.getBelongOrgName().trim())?"无机构":generalStaffInfoEntity.getBelongOrgName().trim())+"--"+generalStaffInfoEntity.getStaffName()+"("+generalStaffInfoEntity.getStaffId()+")");
			}
			else {
				reObj.put("userName",generalStaffInfoEntity.getStaffId());
			}
			reObj.put("realName",generalStaffInfoEntity.getStaffName()+"("+generalStaffInfoEntity.getStaffId()+")");
			reObj.put("userId",generalStaffInfoEntity.getUserId());
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
	@RequestMapping(value = "/queryGeneralStaffInfoListMap", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListMap(
			@RequestBody CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.queryGeneralStaffInfoListMap(commonMsg);
		log.info("====queryGeneralStaffInfoListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralStaffInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> saveGeneralStaffInfo(
			@RequestBody @Validated CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalStaffInfoService.saveGeneralStaffInfo(commonMsg);
		log.info("====saveGeneralStaffInfo end ==== ");
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
	@RequestMapping(value = "/saveGeneralStaffInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralStaffInfoEntity> saveGeneralStaffInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalStaffInfoService.saveGeneralStaffInfoList(commonMsg);
		log.info("====saveGeneralStaffInfoList end ==== ");
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
	@RequestMapping(value = "/updateGeneralStaffInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralStaffInfoServiceImpl.class,queryMethodName="queryGeneralStaffInfo", handleName = "更新")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> updateGeneralStaffInfo(
			@RequestBody @Validated CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.updateGeneralStaffInfo(commonMsg);
		log.info("====updateGeneralStaffInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralStaffInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralStaffInfoServiceImpl.class,queryMethodName="queryGeneralStaffInfo", handleName = "更新")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> saveOrUpdateGeneralStaffInfo(
			@RequestBody @Validated CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalStaffInfoService.saveOrUpdateGeneralStaffInfo(commonMsg);
		log.info("====saveOrUpdateGeneralStaffInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralStaffInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass= GeneralStaffInfoServiceImpl.class,queryMethodName="queryGeneralStaffInfo", handleName = "更新")
	public CommonMsg<NullEntity,GeneralStaffInfoEntity> saveOrUpdateGeneralStaffInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalStaffInfoService.saveOrUpdateGeneralStaffInfoList(commonMsg);
		log.info("====saveOrUpdateGeneralStaffInfoList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralStaffInfo", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralStaffInfoEntity, NullEntity> deleteGeneralStaffInfo(
			@RequestBody @Validated CommonMsg<GeneralStaffInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.deleteGeneralStaffInfo(commonMsg);
		log.info("====deleteGeneralStaffInfo end ==== ");
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
	@RequestMapping(value = "/deleteGeneralStaffInfoList", method = RequestMethod.POST)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralStaffInfoEntity> deleteGeneralStaffInfoList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralStaffInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalStaffInfoService.deleteGeneralStaffInfoList(commonMsg);
		log.info("====deleteGeneralStaffInfoList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralStaffInfoExcel", method = RequestMethod.GET)
	@SuppressWarnings({"rawtypes"})
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralStaffInfoExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralStaffInfoEntity,GeneralStaffInfoEntity> commonMsg = new CommonMsg<GeneralStaffInfoEntity,GeneralStaffInfoEntity>();
		GeneralStaffInfoEntity singleBody = new GeneralStaffInfoEntity();
		//定义body
		MutBean<GeneralStaffInfoEntity, GeneralStaffInfoEntity> mutBean= new MutBean<GeneralStaffInfoEntity, GeneralStaffInfoEntity>();
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
		generalStaffInfoService.exportGeneralStaffInfoExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralStaffInfoExcel end ==== ");
	}    
}