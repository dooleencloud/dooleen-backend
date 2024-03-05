package com.dooleen.service.biz.sixteen.user.answers.controller;

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
import com.dooleen.service.biz.sixteen.user.answers.entity.BizSixteenUserAnswersEntity;
import com.dooleen.service.biz.sixteen.user.answers.service.BizSixteenUserAnswersService;
import com.dooleen.service.biz.sixteen.user.answers.service.impl.BizSixteenUserAnswersServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 21:47:19
 * @Description : 用户答题结果管理Controller
 * @Author : apple
 * @Update: 2021-05-08 21:47:19
 */
@Slf4j
@RestController
@Api(tags = "用户答题结果管理相关接口")
@RequestMapping("/biz/sixteen/bizSixteenUserAnswers")
public class BizSixteenUserAnswersController {

	@Autowired
	private BizSixteenUserAnswersService bizSixteenUserAnswersService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizSixteenUserAnswers", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> queryBizSixteenUserAnswers(
			@RequestBody CommonMsg<BizSixteenUserAnswersEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.queryBizSixteenUserAnswers(commonMsg);
		log.info("====queryBizSixteenUserAnswers end ==== ");
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
	@RequestMapping(value = "/queryBizSixteenUserAnswersList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizSixteenUserAnswersEntity> queryBizSixteenUserAnswersList(
			@RequestBody CommonMsg<NullEntity, BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.queryBizSixteenUserAnswersList(commonMsg);
		log.info("====queryBizSixteenUserAnswersList end ==== ");
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
	@RequestMapping(value = "/queryBizSixteenUserAnswersListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity> queryBizSixteenUserAnswersListPage(
			@RequestBody CommonMsg<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.queryBizSixteenUserAnswersListPage(commonMsg);
		log.info("====queryBizSixteenUserAnswersListPage end ==== ");
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
	@RequestMapping(value = "/queryBizSixteenUserAnswersListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity> queryBizSixteenUserAnswersListMap(
			@RequestBody CommonMsg<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.queryBizSixteenUserAnswersListMap(commonMsg);
		log.info("====queryBizSixteenUserAnswersListMap end ==== ");
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
	@RequestMapping(value = "/saveBizSixteenUserAnswers", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> saveBizSixteenUserAnswers(
			@RequestBody @Validated CommonMsg<BizSixteenUserAnswersEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.saveBizSixteenUserAnswers(commonMsg);
		log.info("====saveBizSixteenUserAnswers end ==== ");
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
	@RequestMapping(value = "/saveBizSixteenUserAnswersList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizSixteenUserAnswersEntity> saveBizSixteenUserAnswersList(
			@RequestBody @Validated CommonMsg<NullEntity,BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.saveBizSixteenUserAnswersList(commonMsg);
		log.info("====saveBizSixteenUserAnswersList end ==== ");
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
	@RequestMapping(value = "/updateBizSixteenUserAnswers", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizSixteenUserAnswersServiceImpl.class,queryMethodName="queryBizSixteenUserAnswers", entityClass=BizSixteenUserAnswersEntity.class, handleName = "更新")
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> updateBizSixteenUserAnswers(
			@RequestBody @Validated CommonMsg<BizSixteenUserAnswersEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.updateBizSixteenUserAnswers(commonMsg);
		log.info("====updateBizSixteenUserAnswers end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizSixteenUserAnswers", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=BizSixteenUserAnswersServiceImpl.class,queryMethodName="queryBizSixteenUserAnswers", entityClass=BizSixteenUserAnswersEntity.class, handleName = "更新")
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> saveOrUpdateBizSixteenUserAnswers(
			@RequestBody @Validated CommonMsg<BizSixteenUserAnswersEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.saveOrUpdateBizSixteenUserAnswers(commonMsg);
		log.info("====saveOrUpdateBizSixteenUserAnswers end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizSixteenUserAnswersList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizSixteenUserAnswersServiceImpl.class,queryMethodName="queryBizSixteenUserAnswers", entityClass=BizSixteenUserAnswersEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizSixteenUserAnswersEntity> saveOrUpdateBizSixteenUserAnswersList(
			@RequestBody @Validated CommonMsg<NullEntity,BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.saveOrUpdateBizSixteenUserAnswersList(commonMsg);
		log.info("====saveOrUpdateBizSixteenUserAnswersList end ==== ");
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
	@RequestMapping(value = "/deleteBizSixteenUserAnswers", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> deleteBizSixteenUserAnswers(
			@RequestBody @Validated CommonMsg<BizSixteenUserAnswersEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.deleteBizSixteenUserAnswers(commonMsg);
		log.info("====deleteBizSixteenUserAnswers end ==== ");
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
	@RequestMapping(value = "/deleteBizSixteenUserAnswersList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizSixteenUserAnswersEntity> deleteBizSixteenUserAnswersList(
			@RequestBody @Validated CommonMsg<NullEntity, BizSixteenUserAnswersEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizSixteenUserAnswersService.deleteBizSixteenUserAnswersList(commonMsg);
		log.info("====deleteBizSixteenUserAnswersList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportBizSixteenUserAnswersExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizSixteenUserAnswersExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizSixteenUserAnswersEntity,BizSixteenUserAnswersEntity> commonMsg = new CommonMsg<BizSixteenUserAnswersEntity,BizSixteenUserAnswersEntity>();
		BizSixteenUserAnswersEntity singleBody = new BizSixteenUserAnswersEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity> mutBean= new MutBean<BizSixteenUserAnswersEntity, BizSixteenUserAnswersEntity>();
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
		bizSixteenUserAnswersService.exportBizSixteenUserAnswersExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizSixteenUserAnswersExcel end ==== ");
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
	public CommonMsg<BizSixteenUserAnswersEntity, NullEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonMsg<BizSixteenUserAnswersEntity,NullEntity> commonMsg = new CommonMsg<BizSixteenUserAnswersEntity,NullEntity>();
		try {
			commonMsg = bizSixteenUserAnswersService.uploadExcel(file,request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commonMsg;
	}
}