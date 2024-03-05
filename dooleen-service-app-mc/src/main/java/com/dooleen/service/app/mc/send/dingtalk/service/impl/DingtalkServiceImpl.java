package com.dooleen.service.app.mc.send.dingtalk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.*;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.utils.RedisUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.dingtalk.service.DingtalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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
public class DingtalkServiceImpl implements DingtalkService {
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	@Async("asyncTaskExecutor")
	public void sendDingtalkMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg){
		//读取三方配置获取appId, appKey
		JSONObject jsonObj = commonService.getThirdParam(commonMsg.getBody().getSingleBody().getTenantId(),"钉钉消息");

		String returnMsg = "";
		String status = "";
		//判断发送开关是否打开
		if(jsonObj.get("dingtalkChecked") != null && jsonObj.getBoolean("dingtalkChecked")) {
			//获取消息模板配置
			SysMsgConfigEntity  sysMsgConfigEntity =commonService.getMsgConfig(commonMsg.getBody().getSingleBody().getTenantId(),commonMsg.getBody().getSingleBody().getBizSceneName(),"钉钉消息");

			//获取token
			String token = getToken(commonMsg.getBody().getSingleBody().getTenantId(), jsonObj.getString("appKey"), jsonObj.getString("appSecret"), jsonObj.getInteger("tokenExpired"));

			//按照列表逐个发送
			List<SendMsgUserInfoEntity> userSendList = commonService.getUserList(commonMsg.getBody().getSingleBody().getTenantId(), commonMsg.getBody().getSingleBody().getSendAddressList());
			//为了记录日志，每个用户逐条发送
			for(SendMsgUserInfoEntity sendMsgUserInfoEntity : userSendList) {
				//如果钉钉用户ID为空则获取钉钉用户ID
				String dingtalkUserId = sendMsgUserInfoEntity.getDingtalkUserId();
				if(StringUtil.isEmpty(dingtalkUserId)){
					if(!StringUtil.isEmpty(sendMsgUserInfoEntity.getMobileNo())){
						dingtalkUserId = getUserIdByMobile(sendMsgUserInfoEntity.getId(),sendMsgUserInfoEntity.getMobileNo(), token);
					}
					else {
						log.error("==>用户：{}未绑定手机号码", sendMsgUserInfoEntity.getRealName());
					}
				}
				JSONObject contentObj = JSONObject.parseObject(commonMsg.getBody().getSingleBody().getMsgContentJson());
				try {
					DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
					OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
					req.setAgentId(jsonObj.getLong("agentId"));
					req.setUseridList(dingtalkUserId);
					Msg obj1 = new Msg();

					obj1.setMsgtype("oa");
					OA obj2 = new OA();
					Body obj3 = new Body();
					List<Form> list5 = new ArrayList<Form>();
					if (!StringUtil.isEmpty(contentObj.getString("label1"))) {
						Form obj6 = new Form();
						obj6.setKey(contentObj.getString("label1")+"：");
						obj6.setValue( contentObj.getString("keyword1"));
						list5.add(obj6);
					}
					if (!StringUtil.isEmpty(contentObj.getString("label2"))) {
						Form obj62 = new Form();
						obj62.setKey(contentObj.getString("label2")+"：");
						obj62.setValue( contentObj.getString("keyword2"));
						list5.add(obj62);
					}
					if (!StringUtil.isEmpty(contentObj.getString("label3"))) {
						Form obj63 = new Form();
						obj63.setKey(contentObj.getString("label3")+"：");
						obj63.setValue( contentObj.getString("keyword3"));
						list5.add(obj63);
					}
					if (!StringUtil.isEmpty(contentObj.getString("label4"))) {
						Form obj64 = new Form();
						obj64.setKey(contentObj.getString("label4")+"：");
						obj64.setValue( contentObj.getString("keyword4"));
						list5.add(obj64);
					}
					if (!StringUtil.isEmpty(contentObj.getString("remark"))) {
						Form obj65 = new Form();
						obj65.setKey("备注：");
						obj65.setValue( contentObj.getString("remark"));
						list5.add(obj65);
					}

					obj3.setForm(list5);

					if (!StringUtil.isEmpty(contentObj.getString("first"))) {
						obj3.setTitle(contentObj.getString("first"));
					}
					else {
						obj3.setTitle(commonMsg.getBody().getSingleBody().getMsgTitle());
					}
					obj2.setBody(obj3);

					Head obj7 = new Head();
					obj7.setText("");
					obj2.setHead(obj7);
					obj1.setOa(obj2);
					req.setMsg(obj1);
					OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(req, token);

					JSONObject result = JSON.parseObject(rsp.getBody());
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
						log.info("发送失败"+result.getString("errmsg"));
					}
				} catch (Exception e) {
					status = "9000";
					returnMsg = "发送异常";
				}
				//写发送日志
				commonMsg.getBody().getSingleBody().setSenderRealName(sendMsgUserInfoEntity.getRealName());
				commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
			}
		}
		else{
			status = "2000";
			returnMsg = "消息已关闭";
			log.info("开关已关闭，发送失败");
			//写发送日志
			commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
		}
	}

	// 获取钉钉TOKEN
	public String getToken(String tenantId, String appkey, String secret, int tokenExpired) {
		if(StringUtil.isEmpty(redisUtil.get("dingtalkToken:"+tenantId))) {
			try {
				DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
				OapiGettokenRequest req = new OapiGettokenRequest();
				req.setAppkey(appkey);
				req.setAppsecret(secret);
				req.setHttpMethod("GET");
				OapiGettokenResponse rsp = client.execute(req);
				log.debug("==获取钉钉企业token返回" + rsp.getBody());
				JSONObject json = JSONObject.parseObject(rsp.getBody());
				if (null != json.get("access_token")) {
					redisUtil.set("dingtalkToken:"+tenantId, json.getString("access_token"),tokenExpired);
					return json.getString("access_token");
				}
				else{
					log.info("==获取钉钉企业token发生错误" + json.getString("errmsg"));
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else{
			return (String) redisUtil.get("dingtalkToken:"+tenantId);
		}
	}

	// 根据钉钉绑定的手机号获取钉钉用户ID
	public String getUserIdByMobile(String id,String mobile, String token) {
		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
			OapiUserGetByMobileRequest req = new OapiUserGetByMobileRequest();
			req.setMobile(mobile);
			req.setHttpMethod("GET");
			OapiUserGetByMobileResponse rsp = client.execute(req, token);
			log.debug("==获取钉钉用户名返回：" + rsp.getBody());
			JSONObject json = JSONObject.parseObject(rsp.getBody());
			if (null != json.get("userid")) {
				commonService.updateUserDingtalkUserId(id, json.getString("userid"));
				return json.getString("userid");
			}
			else{
				log.info("==获取钉钉用户名发生错误：" + json.getString("errmsg"));
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}