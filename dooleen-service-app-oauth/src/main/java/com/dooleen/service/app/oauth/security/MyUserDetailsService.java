package com.dooleen.service.app.oauth.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.utils.ValidationUtils;
import com.dooleen.service.app.oauth.entity.LicenseEntity;
import com.dooleen.service.app.oauth.service.BizSixteenUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dooleen.common.core.utils.RedisUtil;
import com.dooleen.common.core.utils.aes.AESUtil;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.entity.SysUserInfoEntity;
import com.dooleen.service.app.oauth.service.SysUserInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description :
 * @Author : name
 * @Update : 2020-06-06 19:37:42
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserInfoService sysUserInfoService;
	@Autowired
	private BizSixteenUserInfoService bizSixteenUserInfoService;
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// 当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String nowStr = sdf.format(now);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		log.info(">> 登录传入参数 === " + userName);
		String detailStr = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
		int beginIndex = detailStr.indexOf("RemoteIpAddress:");
		int endIndex = detailStr.indexOf("SessionId:");
		String ipAddr = detailStr.substring(beginIndex + 17, endIndex - 2);
		//log.info("ipAddr == " + ipAddr);
		if (StringUtils.isBlank(userName)) {
			log.error("username 为空  null");
			throw new UsernameNotFoundException("E4001-登录用户名为空！");
		}
		String[] userNameStr= {};
		String[] userInfo = {};
		if (userName.indexOf("@@") > 0) {
			userNameStr = userName.split("@@");
			//System.out.println("===>>userNameStr.length ==="+userNameStr.length);
			if (userNameStr[0].indexOf("=") > 0) {
				userInfo = userNameStr[0].split("=");
			} else {
				throw new UsernameNotFoundException("E4002-登录参数错误！");
			}
		}
		else {
			throw new UsernameNotFoundException("E4003-不可识别的登录方式！");
		}

		// username = 租户ID0 + 用户类型1 + 登录方式2 + 登录终端3 + 登录用户名4 + grant_type(自动获取)

		SysUserInfoEntity sysUserInfo = new SysUserInfoEntity();
		//LicenseEntity licenseInfo = new LicenseEntity();
		List<SysRoleEntity> sysRoleEntityList = new ArrayList();
		// 后端管理用户登录: 用户类型=manage
		if("user".equals(userInfo[1])) {
			// 组装微服务报文头
			CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg = new CommonMsg<SysUserInfoEntity, SysRoleEntity>();
			CommonMsg<LicenseEntity, NullEntity> commonMsgLicense = new CommonMsg<LicenseEntity, NullEntity>();
			MutBean<SysUserInfoEntity, SysRoleEntity> mutBean = new MutBean<SysUserInfoEntity, SysRoleEntity>();
			MutBean<LicenseEntity, NullEntity> mutBeanLicense = new MutBean<LicenseEntity, NullEntity>();
			HeadEntity head = new HeadEntity();
			head.setTenantId(userInfo[0]);
			commonMsgLicense.setHead(head);
			commonMsgLicense.setBody(mutBeanLicense);
			commonMsg.setHead(head);
			commonMsg.setBody(mutBean);

			SysUserInfoEntity sysUserInfoEntity = new SysUserInfoEntity();
			sysUserInfoEntity.setTenantId(userInfo[0]);
			sysUserInfoEntity.setUserName(userInfo[4]);
			sysUserInfoEntity.setUserType(userInfo[3]);
			mutBean.setSingleBody(sysUserInfoEntity);
			// 定义body
			List<SQLConditionEntity> sqlCondition = new ArrayList();
			mutBean.setSqlCondition(sqlCondition);
			List<SQLOrderEntity> orderRule = new ArrayList();
			mutBean.setOrderRule(orderRule);

			if (AESUtil.encode()) {
				String commonMsgStr = "";
				JSONObject comObj = new JSONObject();
				//验证license
				try {
					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(commonMsgLicense));
					commonMsgStr = sysUserInfoService.checkTenantLicense(commonMsgStr);
					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
					commonMsgLicense = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<LicenseEntity, NullEntity>>() {});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				licenseInfo = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),
//						LicenseEntity.class);

				if (!commonMsgLicense.getHead().getRespCode().equals("S0000")) {
					throw new BadCredentialsException(commonMsgLicense.getHead().getRespMsg().toString());
				}
				//查询用户信息
				try {
					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(commonMsg));
					commonMsgStr = sysUserInfoService.querySysUserInfoByUserName(commonMsgStr);
					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
					commonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<SysUserInfoEntity, SysRoleEntity>>() {
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sysUserInfo = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),
						SysUserInfoEntity.class);
				sysRoleEntityList = JSON.parseArray(comObj.getJSONObject("body").get("listBody").toString(),
						SysRoleEntity.class);

			} else {
				//验证license
				commonMsgLicense = sysUserInfoService.checkTenantLicense(commonMsgLicense);
//				licenseInfo = commonMsgLicense.getBody().getSingleBody();
				if (!commonMsgLicense.getHead().getRespCode().equals("S0000")) {
					throw new BadCredentialsException(commonMsgLicense.getHead().getRespMsg().toString());
				}
				// 查询用户信息
				commonMsg = sysUserInfoService.querySysUserInfoByUserName(commonMsg);
				sysUserInfo = commonMsg.getBody().getSingleBody();
				sysRoleEntityList = commonMsg.getBody().getListBody();
			}
			// 如果处理失败则抛出异常
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				throw new BadCredentialsException(commonMsg.getHead().getRespMsg().toString());
			}
			//验证码登录将密码设置为code 不校验密码
			if (userInfo[0].equals("code")) {
				sysUserInfo.setPassword("code");
			}
			sysUserInfo.setLoginName(sysUserInfo.getUserName());
			sysUserInfo.setUserName(userName);
//			sysUserInfo.setEffectDate(licenseInfo.getEffectDate());
//			sysUserInfo.setInvalidDate(licenseInfo.getInvalidDate());
		}
		// 验证C端客户登录
		else  if("custom".equals(userInfo[1])) {
			// 组装微服务报文头
			CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> commonMsg = new CommonMsg<SysCustomUserInfoEntity, SysRoleEntity>();
			CommonMsg<LicenseEntity, NullEntity> commonMsgLicense = new CommonMsg<LicenseEntity, NullEntity>();
			MutBean<SysCustomUserInfoEntity, SysRoleEntity> mutBean = new MutBean<SysCustomUserInfoEntity, SysRoleEntity>();
			MutBean<LicenseEntity, NullEntity> mutBeanLicense = new MutBean<LicenseEntity, NullEntity>();
			HeadEntity head = new HeadEntity();
			head.setTenantId(userInfo[0]);
			commonMsgLicense.setHead(head);
			commonMsgLicense.setBody(mutBeanLicense);
			commonMsg.setHead(head);
			commonMsg.setBody(mutBean);
			SysCustomUserInfoEntity customUserInfoEntity = new SysCustomUserInfoEntity();
			if("weixin".equals(userInfo[2])){
				if("weixin".equals(userInfo[3])) { //小程序
					customUserInfoEntity.setWxOpenId(userInfo[4]);
				}
				else if("app".equals(userInfo[3])) { //app
					customUserInfoEntity.setAppWxOpenId(userInfo[4]);
				}
			}
			else {
				//如果是手机号码
				if(ValidationUtils.isMobile(userInfo[4])){
					customUserInfoEntity.setMobileNo(userInfo[4]);
				}
				else
				{
					customUserInfoEntity.setUserName(userInfo[4]);
				}
			}
			mutBean.setSingleBody(customUserInfoEntity);
			// 定义body
			List<SQLConditionEntity> sqlCondition = new ArrayList();
			mutBean.setSqlCondition(sqlCondition);
			List<SQLOrderEntity> orderRule = new ArrayList();
			mutBean.setOrderRule(orderRule);

			if (AESUtil.encode()) {
				String commonMsgStr = "";
				JSONObject comObj = new JSONObject();
				//检查license是否有效：
				//验证license
				try {
					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(commonMsgLicense));
					commonMsgStr = sysUserInfoService.checkTenantLicense(commonMsgStr);
					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
					commonMsgLicense = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<LicenseEntity, NullEntity>>() {});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				licenseInfo = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),
//						LicenseEntity.class);
				if (!commonMsgLicense.getHead().getRespCode().equals("S0000")) {
					throw new BadCredentialsException(commonMsgLicense.getHead().getRespMsg().toString());
				}

				try {
					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(commonMsg));
					commonMsgStr = sysUserInfoService.querySysCustomUserInfoByUserName(commonMsgStr);
					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
					commonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<SysCustomUserInfoEntity, SysRoleEntity>>() {
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customUserInfoEntity = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),
						SysCustomUserInfoEntity.class);
				sysRoleEntityList = JSON.parseArray(comObj.getJSONObject("body").get("listBody").toString(),
						SysRoleEntity.class);

			} else {
				//验证license
				commonMsgLicense = sysUserInfoService.checkTenantLicense(commonMsgLicense);
//				licenseInfo = commonMsgLicense.getBody().getSingleBody();
				if (!commonMsgLicense.getHead().getRespCode().equals("S0000")) {
					throw new BadCredentialsException(commonMsgLicense.getHead().getRespMsg().toString());
				}
				// 查询用户信息
				commonMsg = sysUserInfoService.querySysCustomUserInfoByUserName(commonMsg);
				customUserInfoEntity = commonMsg.getBody().getSingleBody();
				sysRoleEntityList = commonMsg.getBody().getListBody();
			}
			// 微信登录时用户不存在抛出异常
			if (commonMsg.getHead().getRespCode().equals("E1001") && userInfo[2].equals("weixin")) {
				throw new BadCredentialsException("weixinNotRegiste");
			}
			// 验证码登录时用户不存在抛出异常
			else  if (commonMsg.getHead().getRespCode().equals("E1001") && userInfo[2].equals("code")) {
				throw new BadCredentialsException("codeNotRegiste");
			}
			else if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				throw new BadCredentialsException(commonMsg.getHead().getRespMsg().toString());
			}
			//验证码登录将密码设置为code 不校验密码
			if ("code".equals(userInfo[2]) && "password".equals(userNameStr[1])) {
				String verifyCode = (String) redisUtil.get("SMS:sms"+userInfo[4]);
				log.debug("SMS:sms+userInfo[4]==="+"SMS:sms"+userInfo[4]);
				log.debug("verifyCode==="+verifyCode);
				if (StringUtils.isEmpty(verifyCode)) {
					throw new UsernameNotFoundException("E4002-验证码已过期，请重新获取验证码！");
				}
				sysUserInfo.setPassword(verifyCode);
			}
			else{
				sysUserInfo.setPassword(customUserInfoEntity.getPassword());
			}
			sysUserInfo.setId(String.valueOf(customUserInfoEntity.getId()));
			sysUserInfo.setUserName(userNameStr[0]+"@@"+"token");
			sysUserInfo.setRealName(customUserInfoEntity.getOrgName());
			sysUserInfo.setNickName(customUserInfoEntity.getNickName());
			sysUserInfo.setHeadImgUrl(customUserInfoEntity.getHeadImgUrl());
			sysUserInfo.setLoginName(customUserInfoEntity.getUserName());
//			sysUserInfo.setEffectDate(licenseInfo.getEffectDate());
//			sysUserInfo.setInvalidDate(licenseInfo.getInvalidDate());
			sysUserInfo.setTenantId(customUserInfoEntity.getTenantId());

		}
		// 验证16pf端客户登录
		else  if("16pfCustom".equals(userInfo[1])) {
			// 组装微服务报文头
			CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg = new CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity>();
			MutBean<BizSixteenUserInfoEntity, SysRoleEntity> mutBean = new MutBean<BizSixteenUserInfoEntity, SysRoleEntity>();
			MutBean<LicenseEntity, NullEntity> mutBeanLicense = new MutBean<LicenseEntity, NullEntity>();
			HeadEntity head = new HeadEntity();
			head.setTenantId(userInfo[0]);
			commonMsg.setHead(head);
			commonMsg.setBody(mutBean);
			BizSixteenUserInfoEntity customUserInfoEntity = new BizSixteenUserInfoEntity();
			if("weixin".equals(userInfo[2])){
				if("weixin".equals(userInfo[3])) { //小程序
					customUserInfoEntity.setWxOpenId(userInfo[4]);
				}
				else if("app".equals(userInfo[3])) { //app
					customUserInfoEntity.setAppWxOpenId(userInfo[4]);
				}
			}
			else {
				//如果是手机号码
				if(ValidationUtils.isMobile(userInfo[4])){
					customUserInfoEntity.setPhone(userInfo[4]);
				}
				else
				{
					customUserInfoEntity.setUserName(userInfo[4]);
				}
			}
			mutBean.setSingleBody(customUserInfoEntity);
			// 定义body
			List<SQLConditionEntity> sqlCondition = new ArrayList();
			mutBean.setSqlCondition(sqlCondition);
			List<SQLOrderEntity> orderRule = new ArrayList();
			mutBean.setOrderRule(orderRule);

			if (AESUtil.encode()) {
				String commonMsgStr = "";
				JSONObject comObj = new JSONObject();

				try {
					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(commonMsg));
					commonMsgStr = bizSixteenUserInfoService.queryBizSixteenUserInfoByUserName(commonMsgStr);
					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
					commonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity>>() {
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customUserInfoEntity = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),
						BizSixteenUserInfoEntity.class);
				sysRoleEntityList = JSON.parseArray(comObj.getJSONObject("body").get("listBody").toString(),
						SysRoleEntity.class);

			} else {
				// 查询用户信息
				commonMsg = bizSixteenUserInfoService.queryBizSixteenUserInfoByUserName(commonMsg);
				customUserInfoEntity = commonMsg.getBody().getSingleBody();
				sysRoleEntityList = commonMsg.getBody().getListBody();
			}
			// 微信登录时用户不存在抛出异常
			if (commonMsg.getHead().getRespCode().equals("E1001") && userInfo[2].equals("weixin")) {
				throw new BadCredentialsException("weixinNotRegiste");
			}
			// 验证码登录时用户不存在抛出异常
			else  if (commonMsg.getHead().getRespCode().equals("E1001") && userInfo[2].equals("code")) {
				throw new BadCredentialsException("codeNotRegiste");
			}
			else if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				throw new BadCredentialsException(commonMsg.getHead().getRespMsg().toString());
			}
			//验证码登录将密码设置为code 不校验密码
			if ("code".equals(userInfo[2]) && "password".equals(userNameStr[1])) {
				String verifyCode = (String) redisUtil.get("SMS:sms"+userInfo[4]);
				System.out.println("SMS:sms+userInfo[4]==="+"SMS:sms"+userInfo[4]);
				System.out.println("verifyCode==="+verifyCode);
				if (StringUtils.isEmpty(verifyCode)) {
					throw new UsernameNotFoundException("E4002-验证码已过期，请重新获取验证码！");
				}
				sysUserInfo.setPassword(verifyCode);
			}
			else{
				sysUserInfo.setPassword(customUserInfoEntity.getPassword());
			}
			sysUserInfo.setId(String.valueOf(customUserInfoEntity.getId()));
			sysUserInfo.setUserName(userNameStr[0]+"@@"+"token");
			sysUserInfo.setRealName(customUserInfoEntity.getRealName());
			sysUserInfo.setNickName(customUserInfoEntity.getRealName());
			sysUserInfo.setHeadImgUrl("");
			sysUserInfo.setLoginName(customUserInfoEntity.getUserName());
//			sysUserInfo.setEffectDate(licenseInfo.getEffectDate());
//			sysUserInfo.setInvalidDate(licenseInfo.getInvalidDate());
			sysUserInfo.setTenantId(customUserInfoEntity.getTenantId());
		}
		return new MyUserDetails(sysUserInfo, sysRoleEntityList);
	}

	public static boolean isJson(String content) {
		try {
			JSONObject.parseObject(content);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
