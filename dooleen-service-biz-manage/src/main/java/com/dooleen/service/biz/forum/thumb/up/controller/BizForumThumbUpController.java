package com.dooleen.service.biz.forum.thumb.up.controller;

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
import com.dooleen.service.biz.forum.thumb.up.entity.BizForumThumbUpEntity;
import com.dooleen.service.biz.forum.thumb.up.service.BizForumThumbUpService;
import com.dooleen.service.biz.forum.thumb.up.service.impl.BizForumThumbUpServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-29 11:23:39
 * @Description : 点赞管理Controller
 * @Author : apple
 * @Update: 2021-07-29 11:23:39
 */
@Slf4j
@RestController
@Api(tags = "点赞管理相关接口")
@RequestMapping("/biz/forum/bizForumThumbUp")
public class BizForumThumbUpController {

	@Autowired
	private BizForumThumbUpService bizForumThumbUpService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizForumThumbUp", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizForumThumbUpEntity, NullEntity> queryBizForumThumbUp(
			@RequestBody CommonMsg<BizForumThumbUpEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.queryBizForumThumbUp(commonMsg);
		log.info("====queryBizForumThumbUp end ==== ");
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
	@RequestMapping(value = "/queryBizForumThumbUpList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLIST")
	public CommonMsg<NullEntity, BizForumThumbUpEntity> queryBizForumThumbUpList(
			@RequestBody CommonMsg<NullEntity, BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.queryBizForumThumbUpList(commonMsg);
		log.info("====queryBizForumThumbUpList end ==== ");
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
	@RequestMapping(value = "/queryBizForumThumbUpListPage", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERYLISTPAGE")
	public CommonMsg<BizForumThumbUpEntity, BizForumThumbUpEntity> queryBizForumThumbUpListPage(
			@RequestBody CommonMsg<BizForumThumbUpEntity, BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.queryBizForumThumbUpListPage(commonMsg);
		log.info("====queryBizForumThumbUpListPage end ==== ");
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
	@RequestMapping(value = "/queryBizForumThumbUpListMap", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizForumThumbUpEntity, BizForumThumbUpEntity> queryBizForumThumbUpListMap(
			@RequestBody CommonMsg<BizForumThumbUpEntity, BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.queryBizForumThumbUpListMap(commonMsg);
		log.info("====queryBizForumThumbUpListMap end ==== ");
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
	@RequestMapping(value = "/saveBizForumThumbUp", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVE")
	public CommonMsg<BizForumThumbUpEntity, NullEntity> saveBizForumThumbUp(
			@RequestBody @Validated CommonMsg<BizForumThumbUpEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizForumThumbUpService.saveBizForumThumbUp(commonMsg);
		log.info("====saveBizForumThumbUp end ==== ");
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
	@RequestMapping(value = "/saveBizForumThumbUpList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVELIST")
	public CommonMsg<NullEntity,BizForumThumbUpEntity> saveBizForumThumbUpList(
			@RequestBody @Validated CommonMsg<NullEntity,BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizForumThumbUpService.saveBizForumThumbUpList(commonMsg);
		log.info("====saveBizForumThumbUpList end ==== ");
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
	@RequestMapping(value = "/updateBizForumThumbUp", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATE",serviceclass= BizForumThumbUpServiceImpl.class,queryMethodName="queryBizForumThumbUp", entityClass=BizForumThumbUpEntity.class, handleName = "更新")
	public CommonMsg<BizForumThumbUpEntity, NullEntity> updateBizForumThumbUp(
			@RequestBody @Validated CommonMsg<BizForumThumbUpEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.updateBizForumThumbUp(commonMsg);
		log.info("====updateBizForumThumbUp end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizForumThumbUp", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="SAVEORUPDATE",serviceclass=BizForumThumbUpServiceImpl.class,queryMethodName="queryBizForumThumbUp", entityClass=BizForumThumbUpEntity.class, handleName = "更新")
	public CommonMsg<BizForumThumbUpEntity, NullEntity> saveOrUpdateBizForumThumbUp(
			@RequestBody @Validated CommonMsg<BizForumThumbUpEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign 
		commonMsg.getBody().setSingleBody(EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getSingleBody(),commonMsg.getHead()));
		// 调用服务
		commonMsg = bizForumThumbUpService.saveOrUpdateBizForumThumbUp(commonMsg);
		log.info("====saveOrUpdateBizForumThumbUp end ==== ");
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
	@RequestMapping(value = "/saveOrUpdateBizForumThumbUpList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="UPDATELIST",serviceclass=BizForumThumbUpServiceImpl.class,queryMethodName="queryBizForumThumbUp", entityClass=BizForumThumbUpEntity.class, handleName = "更新")
	public CommonMsg<NullEntity,BizForumThumbUpEntity> saveOrUpdateBizForumThumbUpList(
			@RequestBody @Validated CommonMsg<NullEntity,BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 新增初始化ID，tenendID，dataSign
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().set(i,EntityInitUtils.initEntityPublicInfo(commonMsg.getBody().getListBody().get(i),commonMsg.getHead()));
		}	
		// 调用服务
		commonMsg = bizForumThumbUpService.saveOrUpdateBizForumThumbUpList(commonMsg);
		log.info("====saveOrUpdateBizForumThumbUpList end ==== ");
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
	@RequestMapping(value = "/deleteBizForumThumbUp", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<BizForumThumbUpEntity, NullEntity> deleteBizForumThumbUp(
			@RequestBody @Validated CommonMsg<BizForumThumbUpEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.deleteBizForumThumbUp(commonMsg);
		log.info("====deleteBizForumThumbUp end ==== ");
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
	@RequestMapping(value = "/deleteBizForumThumbUpList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="DELETE")
	public CommonMsg<NullEntity, BizForumThumbUpEntity> deleteBizForumThumbUpList(
			@RequestBody @Validated CommonMsg<NullEntity, BizForumThumbUpEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		commonMsg = bizForumThumbUpService.deleteBizForumThumbUpList(commonMsg);
		log.info("====deleteBizForumThumbUpList end ==== ");
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
	@RequestMapping(value = "/excelDownload/exportBizForumThumbUpExcel", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="EXPORT")
	public void exportBizForumThumbUpExcel(@RequestParam("tenantId") String tenantId ,@RequestParam("projectId") String projectId, @RequestParam("condition") String condition, @RequestParam("orders") String orders, HttpServletResponse response,HttpServletRequest request) throws Exception {
		CommonMsg<BizForumThumbUpEntity,BizForumThumbUpEntity> commonMsg = new CommonMsg<BizForumThumbUpEntity,BizForumThumbUpEntity>();
		BizForumThumbUpEntity singleBody = new BizForumThumbUpEntity();
		if(StringUtils.isNoneEmpty(projectId) && !projectId.equals("undefined")) {
			//和项目相关请取消注释
			//singleBody.setProjectId(projectId);
		}
		//定义body
		MutBean<BizForumThumbUpEntity, BizForumThumbUpEntity> mutBean= new MutBean<BizForumThumbUpEntity, BizForumThumbUpEntity>();
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
		bizForumThumbUpService.exportBizForumThumbUpExcel(commonMsg,response);
		// 服务结束
		log.info("====exportBizForumThumbUpExcel end ==== ");
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
	public CommonMsg<BizForumThumbUpEntity, NullEntity> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		CommonMsg<BizForumThumbUpEntity,NullEntity> commonMsg = new CommonMsg<BizForumThumbUpEntity,NullEntity>();
		try {
			commonMsg = bizForumThumbUpService.uploadExcel(file,request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commonMsg;
	}
}