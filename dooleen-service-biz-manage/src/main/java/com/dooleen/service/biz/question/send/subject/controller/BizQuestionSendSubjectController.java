package com.dooleen.service.biz.question.send.subject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
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
import com.dooleen.service.biz.question.send.subject.entity.BizQuestionSendSubjectEntity;
import com.dooleen.service.biz.question.send.subject.service.BizQuestionSendSubjectService;
import com.dooleen.service.biz.question.send.subject.service.impl.BizQuestionSendSubjectServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:37:04
 * @Description : 问卷下发题目管理Controller
 * @Author : apple
 * @Update: 2021-02-22 11:37:04
 */
@Slf4j
@RestController
@Api(tags = "问卷下发题目管理相关接口")
@RequestMapping("/biz/question/bizQuestionSendSubject")
public class BizQuestionSendSubjectController {

	@Autowired
	private BizQuestionSendSubjectService bizQuestionSendSubjectService;

	/**
	 * 统计答卷
	 * @param projectName
	 * @param subjectTitle
	 * @param flag
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@ApiOperation(value ="按标题统计答卷")
	@RequestMapping(value = "questionReport", method = RequestMethod.GET)
	@SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="COMPUTE")
	public JSONObject questionReport( @RequestParam("projectName") String projectName,@RequestParam("batchNo") String batchNo, @RequestParam("subjectTitle") String subjectTitle, @RequestParam("flag") String flag, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String tenantId = request.getHeader("TenantId");
		JSONObject jsonObj = bizQuestionSendSubjectService.questionReport(tenantId, projectName,batchNo, subjectTitle,flag);
		log.info("====按标题统计答卷处理 end ==== ");
		return jsonObj;
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
	@RequestMapping(value = "/queryBizQuestionSendSubject", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> queryBizQuestionSendSubject(
			@RequestBody CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.queryBizQuestionSendSubject(commonMsg);
		log.info("====queryBizQuestionSendSubject end ==== ");
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
	@RequestMapping(value = "/queryBizQuestionSendSubjectList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectList(
			@RequestBody CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.queryBizQuestionSendSubjectList(commonMsg);
		log.info("====queryBizQuestionSendSubjectList end ==== ");
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
	@RequestMapping(value = "/queryBizQuestionSendSubjectListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListPage(
			@RequestBody CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.queryBizQuestionSendSubjectListPage(commonMsg);
		log.info("====queryBizQuestionSendSubjectListPage end ==== ");
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
	@RequestMapping(value = "/queryBizQuestionSendSubjectListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListMap(
			@RequestBody CommonMsg<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.queryBizQuestionSendSubjectListMap(commonMsg);
		log.info("====queryBizQuestionSendSubjectListMap end ==== ");
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
	@RequestMapping(value = "/saveBizQuestionSendSubject", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveBizQuestionSendSubject(
			@RequestBody @Validated CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.saveBizQuestionSendSubject(commonMsg);
		log.info("====saveBizQuestionSendSubject end ==== ");
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
	@RequestMapping(value = "/saveBizQuestionSendSubjectList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizQuestionSendSubjectEntity> saveBizQuestionSendSubjectList(
			@RequestBody @Validated CommonMsg<NullEntity,BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.saveBizQuestionSendSubjectList(commonMsg);
		log.info("====saveBizQuestionSendSubjectList end ==== ");
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
	@RequestMapping(value = "/updateBizQuestionSendSubject", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizQuestionSendSubjectServiceImpl.class,queryMethodName="queryBizQuestionSendSubject", entityClass=BizQuestionSendSubjectEntity.class, handleName = "更新")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> updateBizQuestionSendSubject(
			@RequestBody @Validated CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.updateBizQuestionSendSubject(commonMsg);
		log.info("====updateBizQuestionSendSubject end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizQuestionSendSubject", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=BizQuestionSendSubjectServiceImpl.class,queryMethodName="queryBizQuestionSendSubject", entityClass=BizQuestionSendSubjectEntity.class, handleName = "更新")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveOrUpdateBizQuestionSendSubject(
			@RequestBody @Validated CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.saveOrUpdateBizQuestionSendSubject(commonMsg);
		log.info("====saveOrUpdateBizQuestionSendSubject end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizQuestionSendSubjectList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizQuestionSendSubjectServiceImpl.class,queryMethodName="queryBizQuestionSendSubject", entityClass=BizQuestionSendSubjectEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizQuestionSendSubjectEntity> saveOrUpdateBizQuestionSendSubjectList(
			@RequestBody @Validated CommonMsg<NullEntity,BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.saveOrUpdateBizQuestionSendSubjectList(commonMsg);
		log.info("====saveOrUpdateBizQuestionSendSubjectList end ==== ");
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
	@RequestMapping(value = "/deleteBizQuestionSendSubject", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizQuestionSendSubjectEntity, NullEntity> deleteBizQuestionSendSubject(
			@RequestBody @Validated CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.deleteBizQuestionSendSubject(commonMsg);
		log.info("====deleteBizQuestionSendSubject end ==== ");
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
	@RequestMapping(value = "/deleteBizQuestionSendSubjectList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizQuestionSendSubjectEntity> deleteBizQuestionSendSubjectList(
			@RequestBody @Validated CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizQuestionSendSubjectService.deleteBizQuestionSendSubjectList(commonMsg);
		log.info("====deleteBizQuestionSendSubjectList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportBizQuestionSendSubjectExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizQuestionSendSubjectExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> commonMsg = new CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity>();
		BizQuestionSendSubjectEntity singleBody = new BizQuestionSendSubjectEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity> mutBean= new MutBean<BizQuestionSendSubjectEntity, BizQuestionSendSubjectEntity>();
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
		bizQuestionSendSubjectService.exportBizQuestionSendSubjectExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizQuestionSendSubjectExcel end ==== ");
	}    
}