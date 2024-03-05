package com.dooleen.service.system.third.service.weixin;

import com.alibaba.fastjson.JSON;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.UrlUtil;
import com.dooleen.common.core.wechat.vo.OpenIdEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;


/**
 * @Copy Right Information : 独领
 * @Project : 独领教育平台
 * @Project No :
 * @Description :
 * @Version : 1.0.0
 * @Since : 1.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Author : liqh
 * @Maintainer:
 * @Update:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WxUserInfoService {
	// 获取appId
	//@Value("${learning.weixin.mp.appid}")
	private String LEARN_APP_ID;
	// 获取 secret
	//@Value("${learning.weixin.mp.secret}")
	private String LEARN_SECRET;

	// 获取open appId
	//@Value("${learning.weixin.open.appid}")
	private String LEARN_OPEN_APP_ID;
	// 获取 open secret
	//@Value("${learning.weixin.open.secret}")
	private String LEARN_OPEN_SECRET;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	//小程序登录获取用户OpenId
	public CommonMsg<OpenIdEntity, NullEntity> getOpenId(CommonMsg<OpenIdEntity, NullEntity> commonMsg) {
		log.info(">>查询服务开始 LEARN_APP_ID= " + LEARN_APP_ID);
		log.debug(
				">>传入参数head" + ReflectionToStringBuilder.toString(commonMsg.getHead(), ToStringStyle.MULTI_LINE_STYLE));
		log.debug(">>传入参数single body" + ReflectionToStringBuilder.toString(commonMsg.getBody().getSingleBody(),
				ToStringStyle.MULTI_LINE_STYLE));
		String appId = LEARN_APP_ID;
		String secret = LEARN_SECRET;
		if(commonMsg.getBody().getSingleBody().getChannelId().equals("APP")){
			log.debug("===>>开始APP登录");
			appId = LEARN_OPEN_APP_ID;
			secret = LEARN_OPEN_SECRET;
		}
		String jsCode = commonMsg.getBody().getSingleBody().getJsCode();
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + jsCode + "&grant_type=authorization_code";
		String data = UrlUtil.get(requestUrl, params);
		log.debug("data:{}", data);
		// 解析相应内容（转换成json对象）
		JSONObject json = JSONObject.fromObject(data);
		log.debug("微信返回json:{}", json);
		// 用户的唯一标识（openid）
		String openId = String.valueOf(json.get("openid"));
		String sessionKey = String.valueOf(json.get("session_key"));
		log.info("返回 openId = " + openId);
		commonMsg.getBody().getSingleBody().setOpenId(openId);
		commonMsg.getBody().getSingleBody().setSessionKey(sessionKey);
		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	public String decrypt(byte[] sessionKey, byte[] ivData, byte[] encrypData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		String str = new String(cipher.doFinal(encrypData),"UTF-8");

		Map<String, Object> map = JSON.parseObject(str);
//		String watermark = (String) map.get("watermark");
		Map<String, Object> watermarkMap = (Map<String, Object>) map.get("watermark");
		String appid = (String) watermarkMap.get("appid");

		if(LEARN_APP_ID.equals(appid)) {
			String phoneNo = (String) map.get("phoneNumber");
			//解析解密后的字符串
			return phoneNo;
		}else {
			return null;
		}
	}
}