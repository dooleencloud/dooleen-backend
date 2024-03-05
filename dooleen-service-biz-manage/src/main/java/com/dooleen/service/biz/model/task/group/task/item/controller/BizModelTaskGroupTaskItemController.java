package com.dooleen.service.biz.model.task.group.task.item.controller;

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
import com.dooleen.service.biz.model.task.group.task.item.entity.BizModelTaskGroupTaskItemEntity;
import com.dooleen.service.biz.model.task.group.task.item.service.BizModelTaskGroupTaskItemService;
import com.dooleen.service.biz.model.task.group.task.item.service.impl.BizModelTaskGroupTaskItemServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:20:40
 * @Description : 模型任务组任务项关系管理Controller
 * @Author : apple
 * @Update: 2022-01-04 23:20:40
 */
@Slf4j
@RestController
@Api(tags = "模型任务组任务项关系管理相关接口")
@RequestMapping("/biz/model/bizModelTaskGroupTaskItem")
public class BizModelTaskGroupTaskItemController {

	@Autowired
	private BizModelTaskGroupTaskItemService bizModelTaskGroupTaskItemService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizModelTaskGroupTaskItem", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> queryBizModelTaskGroupTaskItem(
			@RequestBody CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.queryBizModelTaskGroupTaskItem(commonMsg);
		log.info("====queryBizModelTaskGroupTaskItem end ==== ");
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
	@RequestMapping(value = "/queryBizModelTaskGroupTaskItemList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemList(
			@RequestBody CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.queryBizModelTaskGroupTaskItemList(commonMsg);
		log.info("====queryBizModelTaskGroupTaskItemList end ==== ");
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
	@RequestMapping(value = "/queryBizModelTaskGroupTaskItemListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemListPage(
			@RequestBody CommonMsg<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.queryBizModelTaskGroupTaskItemListPage(commonMsg);
		log.info("====queryBizModelTaskGroupTaskItemListPage end ==== ");
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
	@RequestMapping(value = "/queryBizModelTaskGroupTaskItemListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemListMap(
			@RequestBody CommonMsg<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.queryBizModelTaskGroupTaskItemListMap(commonMsg);
		log.info("====queryBizModelTaskGroupTaskItemListMap end ==== ");
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
	@RequestMapping(value = "/saveBizModelTaskGroupTaskItem", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> saveBizModelTaskGroupTaskItem(
			@RequestBody @Validated CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.saveBizModelTaskGroupTaskItem(commonMsg);
		log.info("====saveBizModelTaskGroupTaskItem end ==== ");
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
	@RequestMapping(value = "/saveBizModelTaskGroupTaskItemList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> saveBizModelTaskGroupTaskItemList(
			@RequestBody @Validated CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.saveBizModelTaskGroupTaskItemList(commonMsg);
		log.info("====saveBizModelTaskGroupTaskItemList end ==== ");
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
	@RequestMapping(value = "/updateBizModelTaskGroupTaskItem", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizModelTaskGroupTaskItemServiceImpl.class,queryMethodName="queryBizModelTaskGroupTaskItem", entityClass=BizModelTaskGroupTaskItemEntity.class, handleName = "更新")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> updateBizModelTaskGroupTaskItem(
			@RequestBody @Validated CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.updateBizModelTaskGroupTaskItem(commonMsg);
		log.info("====updateBizModelTaskGroupTaskItem end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizModelTaskGroupTaskItem", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass=BizModelTaskGroupTaskItemServiceImpl.class,queryMethodName="queryBizModelTaskGroupTaskItem", entityClass=BizModelTaskGroupTaskItemEntity.class, handleName = "更新")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> saveOrUpdateBizModelTaskGroupTaskItem(
			@RequestBody @Validated CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.saveOrUpdateBizModelTaskGroupTaskItem(commonMsg);
		log.info("====saveOrUpdateBizModelTaskGroupTaskItem end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizModelTaskGroupTaskItemList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizModelTaskGroupTaskItemServiceImpl.class,queryMethodName="queryBizModelTaskGroupTaskItem", entityClass=BizModelTaskGroupTaskItemEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> saveOrUpdateBizModelTaskGroupTaskItemList(
			@RequestBody @Validated CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.saveOrUpdateBizModelTaskGroupTaskItemList(commonMsg);
		log.info("====saveOrUpdateBizModelTaskGroupTaskItemList end ==== ");
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
	@RequestMapping(value = "/deleteBizModelTaskGroupTaskItem", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> deleteBizModelTaskGroupTaskItem(
			@RequestBody @Validated CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.deleteBizModelTaskGroupTaskItem(commonMsg);
		log.info("====deleteBizModelTaskGroupTaskItem end ==== ");
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
	@RequestMapping(value = "/deleteBizModelTaskGroupTaskItemList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> deleteBizModelTaskGroupTaskItemList(
			@RequestBody @Validated CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizModelTaskGroupTaskItemService.deleteBizModelTaskGroupTaskItemList(commonMsg);
		log.info("====deleteBizModelTaskGroupTaskItemList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportBizModelTaskGroupTaskItemExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizModelTaskGroupTaskItemExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> commonMsg = new CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity>();
		BizModelTaskGroupTaskItemEntity singleBody = new BizModelTaskGroupTaskItemEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity> mutBean= new MutBean<BizModelTaskGroupTaskItemEntity, BizModelTaskGroupTaskItemEntity>();
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
		bizModelTaskGroupTaskItemService.exportBizModelTaskGroupTaskItemExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizModelTaskGroupTaskItemExcel end ==== ");
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
	public CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonMsg<BizModelTaskGroupTaskItemEntity,NullEntity> commonMsg = new CommonMsg<BizModelTaskGroupTaskItemEntity,NullEntity>();
		try {
			commonMsg = bizModelTaskGroupTaskItemService.uploadExcel(file,request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commonMsg;
	}
}