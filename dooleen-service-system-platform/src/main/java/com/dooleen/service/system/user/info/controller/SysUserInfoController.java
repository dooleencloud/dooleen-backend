package com.dooleen.service.system.user.info.controller;

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
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
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
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.service.system.user.info.service.SysUserInfoService;
import com.dooleen.service.system.user.info.service.impl.SysUserInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 系统用户管理Controller
 * @Author : apple
 * @Update: 2020-06-06 19:37:42
 */
@Slf4j
@RestController
@Api(tags = "系统用户管理相关接口")
@RequestMapping("/platform/sysUserInfo")
public class SysUserInfoController {

	@Autowired
	private SysUserInfoService sysUserInfoService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysUserInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysUserInfoEntity, NullEntity> querySysUserInfo(
			@RequestBody CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.querySysUserInfo(commonMsg);
		log.info("====querySysUserInfo end ==== ");
		return commonMsg;
	}
	
	/**
	 * 根据登录userName & password查询 ，用户登录微服务调用: 微服务调用不用加密传输
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据用户名+密码查询用户信息", notes = "传入UserName,Password")
	@RequestMapping(value = "/querySysUserInfoByUserName", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(
			@RequestBody CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.querySysUserInfoByUserName(commonMsg);
		log.info("====querySysUserInfoByUserName end ==== ");
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
	@RequestMapping(value = "/querySysUserInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysUserInfoEntity> querySysUserInfoList(
			@RequestBody CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.querySysUserInfoList(commonMsg);
		log.info("====querySysUserInfoList end ==== ");
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
	@RequestMapping(value = "/querySysUserInfoListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListPage(
			@RequestBody CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.querySysUserInfoListPage(commonMsg);
		log.info("====querySysUserInfoListPage end ==== ");
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
	@ApiOperation(value = "根据条件模糊查询记录")
	@RequestMapping(value = "/querySysUserInfoListPage", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
    //@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public List<JSONObject> querySysUserInfoListPage(@RequestParam("columnName") String columnName,@RequestParam("columnValue") String columnValue, @RequestParam("tenantId") String tenantId,@RequestParam("optFlag") String optFlag) {
		CommonMsg<SysUserInfoEntity,SysUserInfoEntity> commonMsg = new CommonMsg<SysUserInfoEntity,SysUserInfoEntity>();
		//定义body
		MutBean<SysUserInfoEntity, SysUserInfoEntity> mutBean= new MutBean<SysUserInfoEntity, SysUserInfoEntity>();
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
		commonMsg = sysUserInfoService.querySysUserInfoListPage(commonMsg);
		// 转换格式
		for(SysUserInfoEntity sysUserInfoEntity: commonMsg.getBody().getListBody()) {
			JSONObject reObj = new JSONObject();
			if(optFlag.equals("org")) {
				reObj.put("userName",(sysUserInfoEntity.getBelongOrgName().trim().equals("")?"无机构":sysUserInfoEntity.getBelongOrgName().trim())+"--"+sysUserInfoEntity.getRealName()+"("+sysUserInfoEntity.getUserName()+")");
			}
			else {
				reObj.put("userName",sysUserInfoEntity.getUserName());
			}
			reObj.put("realName",sysUserInfoEntity.getRealName()+"("+sysUserInfoEntity.getUserName()+")");
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
	@RequestMapping(value = "/querySysUserInfoListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListMap(
			@RequestBody CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.querySysUserInfoListMap(commonMsg);
		log.info("====querySysUserInfoListMap end ==== ");
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
	@RequestMapping(value = "/saveSysUserInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysUserInfoEntity, NullEntity> saveSysUserInfo(
			@RequestBody @Validated CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysUserInfoService.saveSysUserInfo(commonMsg);
		log.info("====saveSysUserInfo end ==== ");
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
	@RequestMapping(value = "/saveSysUserInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysUserInfoEntity> saveSysUserInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysUserInfoService.saveSysUserInfoList(commonMsg);
		log.info("====saveSysUserInfoList end ==== ");
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
	@RequestMapping(value = "/updateSysUserInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysUserInfoServiceImpl.class,queryMethodName="querySysUserInfo", handleName = "更新")
	public CommonMsg<SysUserInfoEntity, NullEntity> updateSysUserInfo(
			@RequestBody @Validated CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.updateSysUserInfo(commonMsg);
		log.info("====updateSysUserInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysUserInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysUserInfoServiceImpl.class,queryMethodName="querySysUserInfo", handleName = "更新")
	public CommonMsg<SysUserInfoEntity, NullEntity> saveOrUpdateSysUserInfo(
			@RequestBody @Validated CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysUserInfoService.saveOrUpdateSysUserInfo(commonMsg);
		log.info("====saveOrUpdateSysUserInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysUserInfoList", method = RequestMethod.POST)
	public CommonMsg<NullEntity,SysUserInfoEntity> saveOrUpdateSysUserInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysUserInfoService.saveOrUpdateSysUserInfoList(commonMsg);
		log.info("====saveOrUpdateSysUserInfoList end ==== ");
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
	@RequestMapping(value = "/deleteSysUserInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysUserInfoEntity, NullEntity> deleteSysUserInfo(
			@RequestBody @Validated CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.deleteSysUserInfo(commonMsg);
		log.info("====deleteSysUserInfo end ==== ");
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
	@RequestMapping(value = "/deleteSysUserInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysUserInfoEntity> deleteSysUserInfoList(
			@RequestBody @Validated CommonMsg<NullEntity, SysUserInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.deleteSysUserInfoList(commonMsg);
		log.info("====deleteSysUserInfoList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysUserInfoExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysUserInfoExcel(@RequestParam("tenantId") String tenantId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysUserInfoEntity,SysUserInfoEntity> commonMsg = new CommonMsg<SysUserInfoEntity,SysUserInfoEntity>();
		//定义body
		MutBean<SysUserInfoEntity, SysUserInfoEntity> mutBean= new MutBean<SysUserInfoEntity, SysUserInfoEntity>();
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
		sysUserInfoService.exportSysUserInfoExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysUserInfoExcel end ==== ");
	} 
	
	/**
	 * 创建项目Admin用户
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "创建项目Admin用户", notes = "")
	@RequestMapping(value = "/createProjectAdminUser", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="CREATEADMIN")
	public CommonMsg<SysUserInfoEntity, NullEntity> createProjectAdminUser(@RequestBody CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.createProjectAdminUser(commonMsg);
		log.info("====createProjectAdminUser end ==== ");
		return commonMsg;
	}
	/**
	 * 创建租户Admin用户
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "创建租户Admin用户", notes = "")
	@RequestMapping(value = "/createAdminUser", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="CREATEADMIN")
	public CommonMsg<SysUserInfoEntity, NullEntity> createAdminUser(@RequestBody CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.createAdminUser(commonMsg);
		log.info("====creatAdminUser end ==== ");
		return commonMsg;
	}
	/**
	 * 重置租户Admin密码
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "重置租户Admin密码", notes = "")
	@RequestMapping(value = "/restAdminPassword", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="RESTADMIN")
	public CommonMsg<SysUserInfoEntity, NullEntity> restAdminPassword(@RequestBody CommonMsg<SysUserInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserInfoService.restAdminPassword(commonMsg);
		log.info("====restAdminPassword end ==== ");
		return commonMsg;
	}
}