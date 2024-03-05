package com.dooleen.service.app.mc.send.push.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.send.log.mapper.SysSendLogMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.push.service.PushAppMsgService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.Constants;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理服务实现
 * @Author : apple
 * @Update: 2020-11-07 22:12:08
 */

/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PushAppMsgServiceImpl extends ServiceImpl<SysSendLogMapper, SysSendLogEntity> implements PushAppMsgService {

   @Autowired
   public GeneralFlowProcessService  generalFlowProcessService;

   @Autowired
   private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

   @Autowired
   private CommonService commonService;


   @Autowired
   private  GetBizParamsService getBizParamsService;

    // STEP1：获取应用基本信息
    private String appId;

    private String appKey;

    private String masterSecret;

    // 如果需要使用HTTPS，直接修改url即可
    private static String url = "http://api.getui.com/apiex.htm";

   @Autowired
   private RedisTemplate<String, ?> redisTemplate;

   @Override
   public void pushAppMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
       //读取三方配置获取appId, appKey
       JSONObject jsonObj = commonService.getThirdParam(commonMsg.getBody().getSingleBody().getTenantId(),"APP个推");

       appId = jsonObj.getString("appId");

       appKey = jsonObj.getString("appKey");

       masterSecret =  jsonObj.getString("masterSecret");

       // 是否发送成功
       if(jsonObj.get("pushChecked") != null && jsonObj.getBoolean("pushChecked")) {
           try {
               if (commonMsg.getBody().getSingleBody().getSendAddressList().equals("all")) {
                   sendBatch(commonMsg);
               } else {
                   sendSingle(commonMsg);
               }
           } catch (Exception e) {
               log.error("===>> 消息推送处理失败");
           }
       }
       else{
           commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(), "2000","推送已关闭");
       }
   }
    /**
     * 个人推送
     * @param commonMsg
     * @return
     * @throws Exception
     */
    public void sendSingle(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) throws Exception {
        //按照列表逐个发送
        List<SendMsgUserInfoEntity> userSendList = commonService.getUserList(commonMsg.getBody().getSingleBody().getTenantId(),commonMsg.getBody().getSingleBody().getSendAddressList());
        for(SendMsgUserInfoEntity sendMsgUserInfoEntity : userSendList) {
            String returnMsg = "";
            String status = "";
            // 设置后，根据别名推送，会返回每个cid的推送结果
            System.setProperty(Constants.GEXIN_PUSH_SINGLE_ALIAS_DETAIL, "true");
            IGtPush push = new IGtPush(url, appKey, masterSecret);

            TransmissionTemplate template = transmissionTemplateDemo(commonMsg.getBody().getSingleBody());
            SingleMessage message = new SingleMessage();
            message.setData(template);
            message.setOffline(true);
            // 离线有效时间，单位为毫秒
            message.setOfflineExpireTime(24 * 1000 * 3600);
            // 厂商通道下发策略
            message.setStrategyJson("{\"default\":4,\"ios\":4,\"st\":4}");

            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(sendMsgUserInfoEntity.getClientId());

            IPushResult ret = null;
            try {
                ret = push.pushMessageToSingle(message, target);
            } catch (RequestException e) {
                e.printStackTrace();
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            System.out.println(ret.getResponse().toString());
            //{result=RepeatedContent}
            if (ret.getResponse().get("result").equals("ok")) {
                returnMsg = "发送成功";
                status = "0000";
            } else {
                log.info("==>> 发送失败，原因：" + ret.getResponse().get("result"));
                returnMsg = "发送失败" + ret.getResponse().get("result");
                status = "8000";
            }
            commonMsg.getBody().getSingleBody().setSenderRealName(sendMsgUserInfoEntity.getRealName());
            commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
        }

    }


    /**
     * 批量推送
     * @param commonMsg
     * @return
     * @throws Exception
     */
    public void sendBatch(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) throws Exception {
        String returnMsg = "";
        String status = "";
        // 设置后，根据别名推送，会返回每个cid的推送结果
        System.setProperty(Constants.GEXIN_PUSH_SINGLE_ALIAS_DETAIL, "true");
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        TransmissionTemplate template = transmissionTemplateDemo(commonMsg.getBody().getSingleBody());
        AppMessage message = new AppMessage();
        message.setData(template);

        message.setOffline(true);
        // 离线有效时间，单位为毫秒
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 厂商通道下发策略
        message.setStrategyJson("{\"default\":4,\"ios\":4,\"st\":4}");

        // 推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);

        IPushResult ret = push.pushMessageToApp(message,"消息推送任务");
        //{result=RepeatedContent}
        if (ret.getResponse().get("result").equals("ok")) {
            returnMsg = "发送成功";
            status = "0000";
        } else {
            log.info("==>> 发送失败，原因：" + ret.getResponse().get("result"));
            returnMsg = "发送失败"+  ret.getResponse().get("result");
            status = "8000";
        }
        commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),status, returnMsg);
    }

    public TransmissionTemplate transmissionTemplateDemo(SysSendLogEntity  sysSendLogEntity){
        log.info("==>> 发送开始：");
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent("批量发送");
        template.setTransmissionType(2);
        Notify notify = new Notify();
        notify.setTitle(sysSendLogEntity.getMsgTitle());
        notify.setContent(sysSendLogEntity.getMsgContent());
        String payloadStr = "{\"title\":"+sysSendLogEntity.getMsgTitle()+",\"content\":"+sysSendLogEntity.getMsgContent()+",\"sourceId\":"+sysSendLogEntity.getResourceId()+",\"msgType\":"+sysSendLogEntity.getMsgType()+"}";
        notify.setIntent("intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;component=com.dooleen.learn/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title="+sysSendLogEntity.getMsgTitle()+";S.content="+sysSendLogEntity.getMsgContent()+";S.payload="+payloadStr+";end");
        notify.setType(GtReq.NotifyInfo.Type._intent);
        // notify.setUrl("https://dev.getui.com/");
        //notify.setType(Type._url);
        template.set3rdNotifyInfo(notify);//设置第三方通知

        //设置ios推送消息
        APNPayload payload = new APNPayload();
        payload.setAutoBadge("+1");
        payload.setSound("default");
        JSONObject json = new JSONObject();
        json.put("title",sysSendLogEntity.getMsgTitle());
        json.put("msgType",sysSendLogEntity.getMsgType());
        json.put("sourceId",sysSendLogEntity.getResourceId());
        json.put("content",sysSendLogEntity.getMsgContent());
        payload.setCategory(json.toJSONString());
        //payload.setCategory("$由客户端定义");
        //简单模式APNPayload.SimpleMsg
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(sysSendLogEntity.getMsgTitle());
        alertMsg.setBody(sysSendLogEntity.getMsgContent());
        payload.setAlertMsg(alertMsg);
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        log.info("==>> 发送结束：");
        return template;
    }


}
