package com.dooleen.service.batch.question.sent.list.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.batch.question.sent.list.entity.BizQuestionSendListEntity;
import com.dooleen.service.batch.question.sent.list.service.BizQuestionSendListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:29:58
 * @Description : 下发问卷管理Controller
 * @Author : apple
 * @Update: 2021-02-22 11:29:58
 */
@Slf4j
@RestController
@Api(tags = "下发问卷管理相关接口")
@RequestMapping("/biz/question/bizQuestionSendList")
public class BizQuestionSendListController {

	@Autowired
	private BizQuestionSendListService bizQuestionSendListService;

	/**
	 * 根据ID查询
	 * 
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg 
	 * @Return CommonMsg
	 */
	
	@ApiOperation(value = "根据ID查询", notes = "传入singleBody:{id:}")
	@RequestMapping(value = "/queryBizQuestionSendList", method = RequestMethod.POST)
    @SecretAnnotation(decode=true,encode=true)
    @SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="QUERY")
	public CommonMsg<BizQuestionSendListEntity, NullEntity> queryBizQuestionSendList(
			@RequestBody CommonMsg<BizQuestionSendListEntity, NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		// 调用服务
		bizQuestionSendListService.sendUserQuestion();
		log.info("====queryBizQuestionSendList end ==== ");
		return commonMsg;
	}

}