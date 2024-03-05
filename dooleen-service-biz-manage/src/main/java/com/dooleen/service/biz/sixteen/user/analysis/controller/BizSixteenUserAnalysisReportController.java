package com.dooleen.service.biz.sixteen.user.analysis.controller;

import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.biz.sixteen.user.analysis.entity.BizSixteenUserAnalysisEntity;
import com.dooleen.service.biz.sixteen.user.analysis.service.BizSixteenUserAnalysisService;
import com.dooleen.service.biz.sixteen.user.info.service.BizSixteenUserInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 21:28:50
 * @Description : 用户结果分析管理Controller
 * @Author : apple
 * @Update: 2021-05-08 21:28:50
 */
@Slf4j
@Controller
@Api(tags = "用户结果分析管理相关接口")
@RequestMapping("/biz/sixteen/bizSixteenUserAnalysis")
public class BizSixteenUserAnalysisReportController {

	@Autowired
	private BizSixteenUserAnalysisService bizSixteenUserAnalysisService;

	@Autowired
	private BizSixteenUserInfoService bizSixteenUserInfoService;

	/**
	 * 查看分析结果
	 */
	@RequestMapping(value = "/resultReport", method = RequestMethod.GET)
	@SecretAnnotation(decode=false,encode=false)
	public String resultReport(@RequestParam("tenantId") String tenantId, @RequestParam("userId") String userId,  ModelMap model) {
		//获取用户信息
		try{
			tenantId = new String( Base64.decodeBase64(tenantId));
			userId = new String( Base64.decodeBase64(userId)) ;
		}
		catch (Exception e){
			log.info( "用户参数输入错误！");
			return "/biz/paramError";
		}
		BizSixteenUserInfoEntity bizSixteenUserInfoEntity = new BizSixteenUserInfoEntity();
		CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(bizSixteenUserInfoEntity, null);
		commonMsg.getHead().setTenantId(tenantId);
		commonMsg.getBody().getSingleBody().setId(userId);
		commonMsg = bizSixteenUserInfoService.queryBizSixteenUserInfo(commonMsg);
		if(commonMsg.getBody().getSingleBody() == null){
			log.info( "用户信息不存在，请检查用户参数是否正确！");
			return "/biz/notFound";
		}
		//获取用户分析记录
		BizSixteenUserAnalysisEntity bizSixteenUserAnalysisEntity = new BizSixteenUserAnalysisEntity();
		CommonMsg<BizSixteenUserAnalysisEntity, BizSixteenUserAnalysisEntity> commonMsgAnalysis = CreateCommonMsg.getCommonMsg(bizSixteenUserAnalysisEntity, bizSixteenUserAnalysisEntity);
		commonMsgAnalysis.getHead().setTenantId(tenantId);
		commonMsgAnalysis.getBody().setOrderRule(new ArrayList<>());
		commonMsgAnalysis.getBody().setPageSize(9999);
		commonMsgAnalysis.getBody().setCurrentPage(1);
		commonMsgAnalysis.getBody().setSqlCondition(new ArrayList<>());
		commonMsgAnalysis.getBody().getSingleBody().setUserName(commonMsg.getBody().getSingleBody().getUserName());
		commonMsgAnalysis = bizSixteenUserAnalysisService.queryBizSixteenUserAnalysisListPage(commonMsgAnalysis);
		if(commonMsgAnalysis.getBody().getListBody().size() == 0){
			log.info("答题信息不存在，请检查用户参数是否正确！");
			return "/biz/notFound";
		}
		Map<String, Object> hasMap = new HashMap<>();
		//计算耗时
		String takeTimes = commonMsg.getBody().getSingleBody().getTakeTime();
		if(StringUtil.isNumeric(commonMsg.getBody().getSingleBody().getTakeTime())){
			int times = Integer.parseInt(commonMsg.getBody().getSingleBody().getTakeTime());
			takeTimes = String.valueOf ((int)(times/60)) + "分"+ times%60 +"秒";
		}
		commonMsg.getBody().getSingleBody().setTakeTime(takeTimes);
		hasMap.put("userInfo",commonMsg.getBody().getSingleBody());
		hasMap.put("userAnalysis", commonMsgAnalysis.getBody().getListBody());
		model.addAttribute("report", hasMap);
		return "/biz/resultReport";
	}
}