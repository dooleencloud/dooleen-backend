package com.dooleen.service.system.msg.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.system.msg.notice.entity.SysMsgNoticeEntity;
import com.dooleen.service.system.msg.notice.service.SysMsgNoticeService;
import com.dooleen.service.system.msg.notice.service.impl.SysMsgNoticeServiceImpl;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-06-05 13:38:58
 * @Description : 消息通知管理Controller
 * @Author : apple
 * @Update: 2021-06-05 13:38:58
 */
@Slf4j
@RestController
@Api(tags = "消息通知管理相关接口")
@RequestMapping("/platform/sysMsgNotice")
public class SysMsgNoticeController {

	@Autowired
	private SysMsgNoticeService sysMsgNoticeService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/querySysMsgNotice", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysMsgNoticeEntity, NullEntity> querySysMsgNotice(
			@RequestBody CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.querySysMsgNotice(commonMsg);
		log.info("====querySysMsgNotice end ==== ");
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
	@RequestMapping(value = "/querySysMsgNoticeList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, SysMsgNoticeEntity> querySysMsgNoticeList(
			@RequestBody CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.querySysMsgNoticeList(commonMsg);
		log.info("====querySysMsgNoticeList end ==== ");
		return commonMsg;
	}

	/**
	 * 查询当期对话消息
	 *
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "查询当期对话消息！")
	@RequestMapping(value = "/querySysMsgList", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgList(@RequestBody CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.querySysMsgList(commonMsg);
		log.info("====querySysMsgList end ==== ");
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
	@RequestMapping(value = "/querySysMsgNoticeListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgNoticeListPage(
			@RequestBody CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.querySysMsgNoticeListPage(commonMsg);
		log.info("====querySysMsgNoticeListPage end ==== ");
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
	@RequestMapping(value = "/querySysMsgNoticeListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgNoticeListMap(
			@RequestBody CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.querySysMsgNoticeListMap(commonMsg);
		log.info("====querySysMsgNoticeListMap end ==== ");
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
	@RequestMapping(value = "/saveSysMsgNotice", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<SysMsgNoticeEntity, NullEntity> saveSysMsgNotice(
			@RequestBody @Validated CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysMsgNoticeService.saveSysMsgNotice(commonMsg);
		log.info("====saveSysMsgNotice end ==== ");
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
	@RequestMapping(value = "/saveSysMsgNoticeList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,SysMsgNoticeEntity> saveSysMsgNoticeList(
			@RequestBody @Validated CommonMsg<NullEntity,SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysMsgNoticeService.saveSysMsgNoticeList(commonMsg);
		log.info("====saveSysMsgNoticeList end ==== ");
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
	@RequestMapping(value = "/updateSysMsgNotice", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= SysMsgNoticeServiceImpl.class,queryMethodName="querySysMsgNotice", entityClass=SysMsgNoticeEntity.class, handleName = "更新")
	public CommonMsg<SysMsgNoticeEntity, NullEntity> updateSysMsgNotice(
			@RequestBody @Validated CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.updateSysMsgNotice(commonMsg);
		log.info("====updateSysMsgNotice end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysMsgNotice", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=SysMsgNoticeServiceImpl.class,queryMethodName="querySysMsgNotice", entityClass=SysMsgNoticeEntity.class, handleName = "更新")
	public CommonMsg<SysMsgNoticeEntity, NullEntity> saveOrUpdateSysMsgNotice(
			@RequestBody @Validated CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = sysMsgNoticeService.saveOrUpdateSysMsgNotice(commonMsg);
		log.info("====saveOrUpdateSysMsgNotice end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateSysMsgNoticeList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=SysMsgNoticeServiceImpl.class,queryMethodName="querySysMsgNotice", entityClass=SysMsgNoticeEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,SysMsgNoticeEntity> saveOrUpdateSysMsgNoticeList(
			@RequestBody @Validated CommonMsg<NullEntity,SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = sysMsgNoticeService.saveOrUpdateSysMsgNoticeList(commonMsg);
		log.info("====saveOrUpdateSysMsgNoticeList end ==== ");
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
	@RequestMapping(value = "/deleteSysMsgNotice", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<SysMsgNoticeEntity, NullEntity> deleteSysMsgNotice(
			@RequestBody @Validated CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.deleteSysMsgNotice(commonMsg);
		log.info("====deleteSysMsgNotice end ==== ");
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
	@RequestMapping(value = "/deleteSysMsgNoticeList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, SysMsgNoticeEntity> deleteSysMsgNoticeList(
			@RequestBody @Validated CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = sysMsgNoticeService.deleteSysMsgNoticeList(commonMsg);
		log.info("====deleteSysMsgNoticeList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportSysMsgNoticeExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportSysMsgNoticeExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> commonMsg = new CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity>();
		SysMsgNoticeEntity singleBody = new SysMsgNoticeEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<SysMsgNoticeEntity, SysMsgNoticeEntity> mutBean= new MutBean<SysMsgNoticeEntity, SysMsgNoticeEntity>();
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
		sysMsgNoticeService.exportSysMsgNoticeExcel(commonMsg,response);
		// 服务结束
		log.info("====exportSysMsgNoticeExcel end ==== ");
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
	public CommonMsg<SysMsgNoticeEntity, NullEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonMsg<SysMsgNoticeEntity,NullEntity> commonMsg = new CommonMsg<SysMsgNoticeEntity,NullEntity>();
		try {
			commonMsg = sysMsgNoticeService.uploadExcel(file,request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commonMsg;
	}
}