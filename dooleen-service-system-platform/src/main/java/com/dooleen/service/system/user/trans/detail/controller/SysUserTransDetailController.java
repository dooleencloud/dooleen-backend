package com.dooleen.service.system.user.trans.detail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
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
import com.dooleen.service.system.user.trans.detail.entity.SysUserTransDetailEntity;
import com.dooleen.service.system.user.trans.detail.service.SysUserTransDetailService;
import com.dooleen.service.system.user.trans.detail.service.impl.SysUserTransDetailServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-08-05 22:19:54
 * @Description : 账户交易明细管理Controller
 * @Author : apple
 * @Update: 2021-08-05 22:19:54
 */
@Slf4j
@RestController
@Api(tags = "账户交易明细管理相关接口")
@RequestMapping("/platform/sysUserTransDetail")
public class SysUserTransDetailController {

	@Autowired
	private SysUserTransDetailService sysUserTransDetailService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysUserTransDetail", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysUserTransDetailEntity, NullEntity> querySysUserTransDetail(
			@RequestBody CommonMsg<SysUserTransDetailEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.querySysUserTransDetail(commonMsg);
		log.info("====querySysUserTransDetail end ==== ");
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
	@RequestMapping(value = "/querySysUserTransDetailList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysUserTransDetailEntity> querySysUserTransDetailList(
			@RequestBody CommonMsg<NullEntity, SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.querySysUserTransDetailList(commonMsg);
		log.info("====querySysUserTransDetailList end ==== ");
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
	@RequestMapping(value = "/querySysUserTransDetailListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysUserTransDetailEntity, SysUserTransDetailEntity> querySysUserTransDetailListPage(
			@RequestBody CommonMsg<SysUserTransDetailEntity, SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.querySysUserTransDetailListPage(commonMsg);
		log.info("====querySysUserTransDetailListPage end ==== ");
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
	@RequestMapping(value = "/querySysUserTransDetailListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysUserTransDetailEntity, SysUserTransDetailEntity> querySysUserTransDetailListMap(
			@RequestBody CommonMsg<SysUserTransDetailEntity, SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.querySysUserTransDetailListMap(commonMsg);
		log.info("====querySysUserTransDetailListMap end ==== ");
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
	@RequestMapping(value = "/saveSysUserTransDetail", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysUserTransDetailEntity, NullEntity> saveSysUserTransDetail(
			@RequestBody @Validated CommonMsg<SysUserTransDetailEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysUserTransDetailService.saveSysUserTransDetail(commonMsg);
		log.info("====saveSysUserTransDetail end ==== ");
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
	@RequestMapping(value = "/saveSysUserTransDetailList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysUserTransDetailEntity> saveSysUserTransDetailList(
			@RequestBody @Validated CommonMsg<NullEntity,SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysUserTransDetailService.saveSysUserTransDetailList(commonMsg);
		log.info("====saveSysUserTransDetailList end ==== ");
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
	@RequestMapping(value = "/updateSysUserTransDetail", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysUserTransDetailServiceImpl.class,queryMethodName="querySysUserTransDetail", entityClass=SysUserTransDetailEntity.class, handleName = "更新")
	public CommonMsg<SysUserTransDetailEntity, NullEntity> updateSysUserTransDetail(
			@RequestBody @Validated CommonMsg<SysUserTransDetailEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.updateSysUserTransDetail(commonMsg);
		log.info("====updateSysUserTransDetail end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysUserTransDetail", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=SysUserTransDetailServiceImpl.class,queryMethodName="querySysUserTransDetail", entityClass=SysUserTransDetailEntity.class, handleName = "更新")
	public CommonMsg<SysUserTransDetailEntity, NullEntity> saveOrUpdateSysUserTransDetail(
			@RequestBody @Validated CommonMsg<SysUserTransDetailEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysUserTransDetailService.saveOrUpdateSysUserTransDetail(commonMsg);
		log.info("====saveOrUpdateSysUserTransDetail end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysUserTransDetailList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=SysUserTransDetailServiceImpl.class,queryMethodName="querySysUserTransDetail", entityClass=SysUserTransDetailEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysUserTransDetailEntity> saveOrUpdateSysUserTransDetailList(
			@RequestBody @Validated CommonMsg<NullEntity,SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysUserTransDetailService.saveOrUpdateSysUserTransDetailList(commonMsg);
		log.info("====saveOrUpdateSysUserTransDetailList end ==== ");
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
	@RequestMapping(value = "/deleteSysUserTransDetail", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysUserTransDetailEntity, NullEntity> deleteSysUserTransDetail(
			@RequestBody @Validated CommonMsg<SysUserTransDetailEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.deleteSysUserTransDetail(commonMsg);
		log.info("====deleteSysUserTransDetail end ==== ");
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
	@RequestMapping(value = "/deleteSysUserTransDetailList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysUserTransDetailEntity> deleteSysUserTransDetailList(
			@RequestBody @Validated CommonMsg<NullEntity, SysUserTransDetailEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysUserTransDetailService.deleteSysUserTransDetailList(commonMsg);
		log.info("====deleteSysUserTransDetailList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysUserTransDetailExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysUserTransDetailExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysUserTransDetailEntity,SysUserTransDetailEntity> commonMsg = new CommonMsg<SysUserTransDetailEntity,SysUserTransDetailEntity>();
		SysUserTransDetailEntity singleBody = new SysUserTransDetailEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<SysUserTransDetailEntity, SysUserTransDetailEntity> mutBean= new MutBean<SysUserTransDetailEntity, SysUserTransDetailEntity>();
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
		sysUserTransDetailService.exportSysUserTransDetailExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysUserTransDetailExcel end ==== ");
	}
	/**
	 * 批量导入文件
	 * @param file
	 * @return
	 */
	@ApiOperation(value ="批量导入数据")
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	@SecretAnnotation(decode=false,encode=false)
	@ResponseBody
	public CommonMsg<SysUserTransDetailEntity, NullEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonMsg<SysUserTransDetailEntity,NullEntity> commonMsg = new CommonMsg<SysUserTransDetailEntity,NullEntity>();
		try {
			commonMsg = sysUserTransDetailService.uploadExcel(file,request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commonMsg;
	}
}