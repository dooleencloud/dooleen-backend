package com.dooleen.service.system.send.log.controller;

import com. dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com. dooleen.common.core.common.entity.*;
import com. dooleen.common.core.utils.CommonUtil;
import com. dooleen.common.core.utils.EntityInitUtils;
import com. dooleen.common.core.utils.aes.SecretAnnotation;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.service.system.send.log.service.SysSendLogService;
import com.dooleen.service.system.send.log.service.impl.SysSendLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project :  dooleen平台
 * @Project No :  dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理Controller
 * @Author : apple
 * @Update: 2020-11-07 22:12:08
 */
@Slf4j
@RestController
@Api(tags = "信息发送管理相关接口")
@RequestMapping("/learn/sysSendLog")
public class SysSendLogController {

	@Autowired
	private SysSendLogService sysSendLogService;

	/**
	 * 根据ID查询
	 *
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */

	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysSendLog", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysSendLogEntity, NullEntity> querySysSendLog(
			@RequestBody CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.querySysSendLog(commonMsg);
		log.info("====querySysSendLog end ==== ");
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
	@RequestMapping(value = "/querySysSendLogList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysSendLogEntity> querySysSendLogList(
			@RequestBody CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.querySysSendLogList(commonMsg);
		log.info("====querySysSendLogList end ==== ");
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
	@RequestMapping(value = "/querySysSendLogListPage", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysSendLogEntity, SysSendLogEntity> querySysSendLogListPage(
			@RequestBody CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.querySysSendLogListPage(commonMsg);
		log.info("====querySysSendLogListPage end ==== ");
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
	@RequestMapping(value = "/querySysSendLogListMap", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysSendLogEntity, SysSendLogEntity> querySysSendLogListMap(
			@RequestBody CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.querySysSendLogListMap(commonMsg);
		log.info("====querySysSendLogListMap end ==== ");
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
	@RequestMapping(value = "/saveSysSendLog", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysSendLogEntity, NullEntity> saveSysSendLog(
			@RequestBody @Validated CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysSendLogService.saveSysSendLog(commonMsg);
		log.info("====saveSysSendLog end ==== ");
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
	@RequestMapping(value = "/saveSysSendLogList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysSendLogEntity> saveSysSendLogList(
			@RequestBody @Validated CommonMsg<NullEntity,SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}
		// 调用服务
		commonMsg = sysSendLogService.saveSysSendLogList(commonMsg);
		log.info("====saveSysSendLogList end ==== ");
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
	@RequestMapping(value = "/updateSysSendLog", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysSendLogServiceImpl.class,queryMethodName="querySysSendLog", entityClass=SysSendLogEntity.class, handleName = "更新")
	public CommonMsg<SysSendLogEntity, NullEntity> updateSysSendLog(
			@RequestBody @Validated CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.updateSysSendLog(commonMsg);
		log.info("====updateSysSendLog end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysSendLog", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=SysSendLogServiceImpl.class,queryMethodName="querySysSendLog", entityClass=SysSendLogEntity.class, handleName = "更新")
	public CommonMsg<SysSendLogEntity, NullEntity> saveOrUpdateSysSendLog(
			@RequestBody @Validated CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysSendLogService.saveOrUpdateSysSendLog(commonMsg);
		log.info("====saveOrUpdateSysSendLog end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysSendLogList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=SysSendLogServiceImpl.class,queryMethodName="querySysSendLog", entityClass=SysSendLogEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysSendLogEntity> saveOrUpdateSysSendLogList(
			@RequestBody @Validated CommonMsg<NullEntity,SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}
		// 调用服务
		commonMsg = sysSendLogService.saveOrUpdateSysSendLogList(commonMsg);
		log.info("====saveOrUpdateSysSendLogList end ==== ");
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
	@RequestMapping(value = "/deleteSysSendLog", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysSendLogEntity, NullEntity> deleteSysSendLog(
			@RequestBody @Validated CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.deleteSysSendLog(commonMsg);
		log.info("====deleteSysSendLog end ==== ");
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
	@RequestMapping(value = "/deleteSysSendLogList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysSendLogEntity> deleteSysSendLogList(
			@RequestBody @Validated CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysSendLogService.deleteSysSendLogList(commonMsg);
		log.info("====deleteSysSendLogList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysSendLogExcel", method = RequestMethod.GET)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysSendLogExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysSendLogEntity,SysSendLogEntity> commonMsg = new CommonMsg<SysSendLogEntity,SysSendLogEntity>();
		SysSendLogEntity singleBody = new SysSendLogEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<SysSendLogEntity, SysSendLogEntity> mutBean= new MutBean<SysSendLogEntity, SysSendLogEntity>();
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
		sysSendLogService.exportSysSendLogExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysSendLogExcel end ==== ");
	}
}