package com.dooleen.service.system.custom.pay.log.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.service.system.custom.pay.log.entity.SysPayLogEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.custom.pay.log.service.SysPayLogService;
import com.dooleen.service.system.custom.pay.log.service.impl.SysPayLogServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 北京数云信息服务有限公司
 * @Project : Learning平台
 * @Project No : Learning
 * @Version : 1.0.0
 * @CreateDate : 2020-11-17 21:38:54
 * @Description : 支付日志管理Controller
 * @Author : apple
 * @Update: 2020-11-17 21:38:54
 */
@Slf4j
@RestController
@Api(tags = "支付日志管理相关接口")
@RequestMapping("/learn/sysPayLog")
public class SysPayLogController {

	@Autowired
	private SysPayLogService sysPayLogService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysPayLog", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysPayLogEntity, NullEntity> querySysPayLog(
			@RequestBody CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.querySysPayLog(commonMsg);
		log.info("====querySysPayLog end ==== ");
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
	@RequestMapping(value = "/querySysPayLogList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysPayLogEntity> querySysPayLogList(
			@RequestBody CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.querySysPayLogList(commonMsg);
		log.info("====querySysPayLogList end ==== ");
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
	@RequestMapping(value = "/querySysPayLogListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysPayLogEntity, SysPayLogEntity> querySysPayLogListPage(
			@RequestBody CommonMsg<SysPayLogEntity, SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.querySysPayLogListPage(commonMsg);
		log.info("====querySysPayLogListPage end ==== ");
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
	@ApiOperation("根据指定字段查询集合")
	@RequestMapping(value = "/querySysPayLogListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysPayLogEntity, SysPayLogEntity> querySysPayLogListMap(
			@RequestBody CommonMsg<SysPayLogEntity, SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.querySysPayLogListMap(commonMsg);
		log.info("====querySysPayLogListMap end ==== ");
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
	@ApiOperation("新增记录")
	@RequestMapping(value = "/saveSysPayLog", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysPayLogEntity, NullEntity> saveSysPayLog(
			@RequestBody @Validated CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysPayLogService.saveSysPayLog(commonMsg);
		log.info("====saveSysPayLog end ==== ");
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
	@ApiOperation("批量新增记录")
	@RequestMapping(value = "/saveSysPayLogList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysPayLogEntity> saveSysPayLogList(
			@RequestBody @Validated CommonMsg<NullEntity,SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysPayLogService.saveSysPayLogList(commonMsg);
		log.info("====saveSysPayLogList end ==== ");
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
	@ApiOperation("更新单条记录")
	@RequestMapping(value = "/updateSysPayLog", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="UPDATE",serviceclass= SysPayLogServiceImpl.class,queryMethodName="querySysPayLog", entityClass=SysPayLogEntity.class, handleName = "更新")
	public CommonMsg<SysPayLogEntity, NullEntity> updateSysPayLog(
			@RequestBody @Validated CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.updateSysPayLog(commonMsg);
		log.info("====updateSysPayLog end ==== ");
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
	@ApiOperation("新增或者更新记录")
	@RequestMapping(value = "/saveOrUpdateSysPayLog", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="UPDATE",serviceclass=SysPayLogServiceImpl.class,queryMethodName="querySysPayLog", entityClass=SysPayLogEntity.class, handleName = "更新")
	public CommonMsg<SysPayLogEntity, NullEntity> saveOrUpdateSysPayLog(
			@RequestBody @Validated CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysPayLogService.saveOrUpdateSysPayLog(commonMsg);
		log.info("====saveOrUpdateSysPayLog end ==== ");
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
	@ApiOperation("批量新增或更新记录")
	@RequestMapping(value = "/saveOrUpdateSysPayLogList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @EnableSystemLog(operatoinType="UPDATELIST",serviceclass=SysPayLogServiceImpl.class,queryMethodName="querySysPayLog", entityClass=SysPayLogEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysPayLogEntity> saveOrUpdateSysPayLogList(
			@RequestBody @Validated CommonMsg<NullEntity,SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysPayLogService.saveOrUpdateSysPayLogList(commonMsg);
		log.info("====saveOrUpdateSysPayLogList end ==== ");
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
	@ApiOperation("根据ID删除记录")
	@RequestMapping(value = "/deleteSysPayLog", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysPayLogEntity, NullEntity> deleteSysPayLog(
			@RequestBody @Validated CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.deleteSysPayLog(commonMsg);
		log.info("====deleteSysPayLog end ==== ");
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
	@ApiOperation("根据ID批量删除记录")
	@RequestMapping(value = "/deleteSysPayLogList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysPayLogEntity> deleteSysPayLogList(
			@RequestBody @Validated CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysPayLogService.deleteSysPayLogList(commonMsg);
		log.info("====deleteSysPayLogList end ==== ");
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
	@ApiOperation("导出excel记录")
	@RequestMapping(value = "/excelDownload/exportSysPayLogExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysPayLogExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysPayLogEntity,SysPayLogEntity> commonMsg = new CommonMsg<SysPayLogEntity,SysPayLogEntity>();
		SysPayLogEntity singleBody = new SysPayLogEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<SysPayLogEntity, SysPayLogEntity> mutBean= new MutBean<SysPayLogEntity, SysPayLogEntity>();
		mutBean.setSingleBody(singleBody);
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
		sysPayLogService.exportSysPayLogExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysPayLogExcel end ==== ");
	}    
}