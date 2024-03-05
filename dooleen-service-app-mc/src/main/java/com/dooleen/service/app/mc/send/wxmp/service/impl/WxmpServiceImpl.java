package com.dooleen.service.app.mc.send.wxmp.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.app.system.msg.config.service.SysMsgConfigEntityService;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.RedisUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.UrlUtil;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.wxmp.service.WxmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


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
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WxmpServiceImpl implements WxmpService {
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	@Async("asyncTaskExecutor")
	public void sendWxmpMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg){
		//读取三方配置获取appId, appKey
		JSONObject jsonObj = commonService.getThirdParam(commonMsg.getBody().getSingleBody().getTenantId(),"微信公众号");

		String returnMsg = "";
		String status = "";
		//判断发送开关是否打开
		if(jsonObj.get("wechatSendChecked") != null && jsonObj.getBoolean("wechatSendChecked")) {
			//获取token
			String token = getToken(commonMsg.getBody().getSingleBody().getTenantId(), jsonObj.getString("appId"), jsonObj.getString("secret"), jsonObj.getString("accessTokenUrl"));
			//获取消息模板配置
			SysMsgConfigEntity  sysMsgConfigEntity =commonService.getMsgConfig(commonMsg.getBody().getSingleBody().getTenantId(),commonMsg.getBody().getSingleBody().getBizSceneName(),"微信公众号");
			//发送消息
			String postUrl = jsonObj.getString("sendTemplateMsgUrl") + "?access_token=" + token;

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("template_id", commonMsg.getBody().getSingleBody().getTemplateId());
			if (!StringUtil.isEmpty(sysMsgConfigEntity.getGotoAddress())) {
				jsonObject.put("url", sysMsgConfigEntity.getGotoAddress());
			}
			if (!StringUtil.isEmpty(sysMsgConfigEntity.getMiniProgramAddress())) {
				JSONObject miniObject = new JSONObject();
				miniObject.put("appid", sysMsgConfigEntity.getMiniProgramId());
				miniObject.put("pagepath", sysMsgConfigEntity.getMiniProgramAddress());
				jsonObject.put("miniprogram", miniObject);
			}

			JSONObject contentObj = JSONObject.parseObject(commonMsg.getBody().getSingleBody().getMsgContentJson());

			//解析传入发送内容
			JSONObject data = new JSONObject();
			JSONObject first = new JSONObject();
			if (!StringUtil.isEmpty(contentObj.getString("first"))) {
				first.put("value", contentObj.getString("first"));
				first.put("color", "#173177");
				data.put("first", first);
			}
			if (!StringUtil.isEmpty(contentObj.getString("keyword1"))) {
				JSONObject keyword1 = new JSONObject();
				keyword1.put("value", contentObj.getString("keyword1"));
				keyword1.put("color", "#173177");
				data.put("keyword1", keyword1);
			}
			if (!StringUtil.isEmpty(contentObj.getString("keyword2"))) {
				JSONObject keyword2 = new JSONObject();
				keyword2.put("value", contentObj.getString("keyword2"));
				keyword2.put("color", "#173177");
				data.put("keyword2", keyword2);
			}

			if (!StringUtil.isEmpty(contentObj.getString("keyword3"))) {
				JSONObject keyword3 = new JSONObject();
				keyword3.put("value", contentObj.getString("keyword3"));
				keyword3.put("color", "#173177");
				data.put("keyword3", keyword3);
			}
			if (!StringUtil.isEmpty(contentObj.getString("keyword4"))) {
				JSONObject keyword4 = new JSONObject();
				keyword4.put("value", contentObj.getString("keyword4"));
				keyword4.put("color", "#173177");
				data.put("keyword4", keyword4);
			}
			if (!StringUtil.isEmpty(contentObj.getString("keyword5"))) {
				JSONObject keyword5 = new JSONObject();
				keyword5.put("value", contentObj.getString("keyword5"));
				keyword5.put("color", "#173177");
				data.put("keyword5", keyword5);
			}
			if (!StringUtil.isEmpty(contentObj.getString("remark"))) {
				JSONObject remark = new JSONObject();
				remark.put("value", contentObj.getString("remark"));
				remark.put("color", "#173177");
				data.put("remark", remark);
			}

			jsonObject.put("data", data);

			//按照列表逐个发送
			List<SendMsgUserInfoEntity> userSendList = commonService.getUserList(commonMsg.getBody().getSingleBody().getTenantId(), commonMsg.getBody().getSingleBody().getSendAddressList());
			for(SendMsgUserInfoEntity sendMsgUserInfoEntity : userSendList) {
				jsonObject.put("touser", sendMsgUserInfoEntity.getWxOpenId());

				//判断开关是否打开
				String string = HttpUtil.post(postUrl, jsonObject.toJSONString());
				JSONObject result = JSON.parseObject(string);
				int errcode = result.getIntValue("errcode");

				if (errcode == 0) {
					// 发送成功
					returnMsg = "发送成功";
					status = "0000";
					log.info("发送成功");
				} else {
					// 发送失败
					returnMsg = "发送失败";
					status = "8000";
					log.info("发送失败");
				}
				commonMsg.getBody().getSingleBody().setSmsParam(jsonObject.toJSONString());
				//写发送日志
				commonMsg.getBody().getSingleBody().setSenderRealName(sendMsgUserInfoEntity.getRealName());
				commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
			}
		}
		else{
			returnMsg = "消息已关闭";
			log.info("开关已关闭，发送失败");
			status = "2000";
			//写发送日志
			commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
		}
	}

	// 获取微信TOKEN
	public String getToken(String tenantId, String appId, String secret, String accessTokenUrl) {
		if(StringUtil.isEmpty(redisUtil.get("wxToken:"+tenantId))) {
			//UrlUtil HttpUtil = new UrlUtil();
			log.info(">>> 开始获取getToken。。。。");
			String params = "grant_type=client_credential&" + "appid=" + appId + "&secret=" + secret;
			String responseData = UrlUtil.get(accessTokenUrl, params);
			// 解析相应内容（转换成json对象）
			JSONObject json = JSONObject.parseObject(responseData);
			System.out.println("微信返回json:{}" + json);
			// 用户的唯一标识（openid）
			if (null != json.get("access_token")) {
				redisUtil.set("wxToken:"+tenantId, json.getString("access_token"),3600);
				return json.getString("access_token");
			}
			return "";
		}
		else{
			return (String) redisUtil.get("wxToken:"+tenantId);
		}
	}


}