package com.dooleen.service.general.conference.info.controller;

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
import com.dooleen.service.general.conference.info.entity.GeneralConferenceInfoEntity;
import com.dooleen.service.general.conference.info.service.GeneralConferenceInfoService;
import com.dooleen.service.general.conference.info.service.impl.GeneralConferenceInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-26 14:09:14
 * @Description : 会议信息表Controller
 * @Author : apple
 * @Update: 2021-04-26 14:09:14
 */
@Slf4j
@RestController
@Api(tags = "会议信息表相关接口")
@RequestMapping("/general/generalConferenceInfo")
public class GeneralConferenceInfoController {

	@Autowired
	private GeneralConferenceInfoService generalConferenceInfoService;

	/**
	 * 查询时间段内的会议信息
	 * @Author zoujin
	 * @CreateTime 2020/08/03 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "查询时间段内的会议信息")
	@RequestMapping(value = "/queryConferenceDuration", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryConferenceDuration(
			@RequestBody CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.queryConferenceDuration(commonMsg);
		log.info("====queryConferenceDuration end ==== ");
		return commonMsg;
	}
	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryGeneralConferenceInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> queryGeneralConferenceInfo(
			@RequestBody CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.queryGeneralConferenceInfo(commonMsg);
		log.info("====queryGeneralConferenceInfo end ==== ");
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
	@RequestMapping(value = "/queryGeneralConferenceInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoList(
			@RequestBody CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.queryGeneralConferenceInfoList(commonMsg);
		log.info("====queryGeneralConferenceInfoList end ==== ");
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
	@RequestMapping(value = "/queryGeneralConferenceInfoListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoListPage(
			@RequestBody CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.queryGeneralConferenceInfoListPage(commonMsg);
		log.info("====queryGeneralConferenceInfoListPage end ==== ");
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
	@RequestMapping(value = "/queryGeneralConferenceInfoListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryGeneralConferenceInfoListMap(
			@RequestBody CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.queryGeneralConferenceInfoListMap(commonMsg);
		log.info("====queryGeneralConferenceInfoListMap end ==== ");
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
	@RequestMapping(value = "/saveGeneralConferenceInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveGeneralConferenceInfo(
			@RequestBody @Validated CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalConferenceInfoService.saveGeneralConferenceInfo(commonMsg);
		log.info("====saveGeneralConferenceInfo end ==== ");
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
	@RequestMapping(value = "/saveGeneralConferenceInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,GeneralConferenceInfoEntity> saveGeneralConferenceInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalConferenceInfoService.saveGeneralConferenceInfoList(commonMsg);
		log.info("====saveGeneralConferenceInfoList end ==== ");
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
	@RequestMapping(value = "/updateGeneralConferenceInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= GeneralConferenceInfoServiceImpl.class,queryMethodName="queryGeneralConferenceInfo", entityClass=GeneralConferenceInfoEntity.class, handleName = "更新")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> updateGeneralConferenceInfo(
			@RequestBody @Validated CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.updateGeneralConferenceInfo(commonMsg);
		log.info("====updateGeneralConferenceInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralConferenceInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=GeneralConferenceInfoServiceImpl.class,queryMethodName="queryGeneralConferenceInfo", entityClass=GeneralConferenceInfoEntity.class, handleName = "更新")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveOrUpdateGeneralConferenceInfo(
			@RequestBody @Validated CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = generalConferenceInfoService.saveOrUpdateGeneralConferenceInfo(commonMsg);
		log.info("====saveOrUpdateGeneralConferenceInfo end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateGeneralConferenceInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=GeneralConferenceInfoServiceImpl.class,queryMethodName="queryGeneralConferenceInfo", entityClass=GeneralConferenceInfoEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,GeneralConferenceInfoEntity> saveOrUpdateGeneralConferenceInfoList(
			@RequestBody @Validated CommonMsg<NullEntity,GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = generalConferenceInfoService.saveOrUpdateGeneralConferenceInfoList(commonMsg);
		log.info("====saveOrUpdateGeneralConferenceInfoList end ==== ");
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
	@RequestMapping(value = "/deleteGeneralConferenceInfo", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<GeneralConferenceInfoEntity, NullEntity> deleteGeneralConferenceInfo(
			@RequestBody @Validated CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.deleteGeneralConferenceInfo(commonMsg);
		log.info("====deleteGeneralConferenceInfo end ==== ");
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
	@RequestMapping(value = "/deleteGeneralConferenceInfoList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, GeneralConferenceInfoEntity> deleteGeneralConferenceInfoList(
			@RequestBody @Validated CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.deleteGeneralConferenceInfoList(commonMsg);
		log.info("====deleteGeneralConferenceInfoList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportGeneralConferenceInfoExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportGeneralConferenceInfoExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> commonMsg = new CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity>();
		GeneralConferenceInfoEntity singleBody = new GeneralConferenceInfoEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> mutBean= new MutBean<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity>();
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
		generalConferenceInfoService.exportGeneralConferenceInfoExcel(commonMsg,response);
		// 服务结束
		log.info("====exportGeneralConferenceInfoExcel end ==== ");
	}
	/**
	 * 创建腾讯会议
	 * @Author zoujin
	 * @CreateTime 2020/08/20 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */
	@ApiOperation(value = "创建腾讯会议")
	@RequestMapping(value = "/createTencentMeeting", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="createTencentMeeting")
	public CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> createTencentMeeting(
			@RequestBody CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = generalConferenceInfoService.createTencentMeeting(commonMsg);
		log.info("====createTencentMeeting end ==== ");
		return commonMsg;
	}
}