package com.dooleen.service.system.license.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.system.license.entity.LicenseEntity;
import com.dooleen.service.system.license.service.LicenseService;
import com.dooleen.service.system.tenant.info.service.SysTenantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-21 16:40:24
 * @Description : 系统客户用户表Controller
 * @Author : apple
 * @Update: 2021-02-21 16:40:24
 */
@Slf4j
@RestController
@Api(tags = "系统客户用户表相关接口")
@RequestMapping("/platform")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;

	/**
	 * 是否检查license
	 */
	@Value("${license.checkLicense}")
	private String checkLicense;
	/**
	 * 检查license是否过期
	 *
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param commonMsg
	 * @Return CommonMsg
	 */

	@ApiOperation(value = "检查租户license是否过期", notes = "")
	@RequestMapping(value = "/checkTenantLicense", method = RequestMethod.POST)
	@SecretAnnotation(decode=true,encode=true)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="CHECK")
	public CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(@RequestBody CommonMsg<LicenseEntity,NullEntity> commonMsg) {
		CommonUtil.controller(commonMsg);
		if(checkLicense.equals("true")) {
			licenseService.checkTenantLicense(commonMsg);
		}
		else{
			log.debug("==>>不检查license");
			commonMsg.getHead().setRespCode("S0000");
		}
		log.info("====checkTenantLicense end ==== ");
		return commonMsg;
	}

}