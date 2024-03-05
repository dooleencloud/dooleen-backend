package com.dooleen.service.app.mc.send.noticeMsg.impl;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.utils.DateUtils;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.noticeMsg.NoticeMsgService;
import com.dooleen.service.app.util.WebSocketClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class NoticeMsgServiceImpl implements NoticeMsgService {
 
    @Autowired
    private WebSocketClientUtil webSocketClientUtil;

    @Autowired
    private CommonService commonService;

    /**
     * 发消息002
     * @param commonMsg
     */
    @Override
    //@Async("asyncTaskExecutor")
    public void sendNoticeMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
        //读取三方配置获取appId, appKey
        JSONObject jsonObj = commonService.getThirdParam(commonMsg.getBody().getSingleBody().getTenantId(),"站内消息");

        //判断发送开关是否打开
        if(jsonObj.get("noticeMsgChecked") != null && jsonObj.getBoolean("noticeMsgChecked")) {
            commonMsg.getBody().getSingleBody().setSendChannel(jsonObj.getString("terminalType"));
            String pcUrl = jsonObj.getString("sendAddress") +":" + jsonObj.getString("port") + "/webSocket/sendMsg/" + commonMsg.getBody().getSingleBody().getTenantId() + "/PC/sendOneUserMsg/system/system/headImgUrl";
            if (commonMsg.getBody().getSingleBody().getSendAddressList().equals("all")) {
                pcUrl = jsonObj.getString("sendAddress") +":" + jsonObj.getString("port") + "/webSocket/sendMsg/" + commonMsg.getBody().getSingleBody().getTenantId() + "/PC/sendAllUserMsg/system/system/headImgUrl";
            }

            String appUrl = jsonObj.getString("sendAddress") +":" + jsonObj.getString("port") + "/webSocket/sendMsg/" + commonMsg.getBody().getSingleBody().getTenantId() + "/APP/sendOneUserMsg/system/system/headImgUrl";
            if (commonMsg.getBody().getSingleBody().getSendAddressList().equals("all")) {
                appUrl = jsonObj.getString("sendAddress") +":" + jsonObj.getString("port") + "/webSocket/sendMsg/" + commonMsg.getBody().getSingleBody().getTenantId() + "/APP/sendAllUserMsg/system/system/headImgUrl";
            }

            WebSocketClient pcClient = null;
            WebSocketClient appClient = null;
            if(jsonObj.getString("terminalType").equals("all")) {
                pcClient = webSocketClientUtil.getClient(pcUrl);
                appClient = webSocketClientUtil.getClient(appUrl);
            }
            else if(jsonObj.getString("terminalType").equals("pc")){
                pcClient = webSocketClientUtil.getClient(pcUrl);
            }
            else if(jsonObj.getString("terminalType").equals("app")){
                appClient = webSocketClientUtil.getClient(appUrl);
            }

            //全部发送
            JSONObject msgObj = new JSONObject();
            if (commonMsg.getBody().getSingleBody().getSendAddressList().equals("all")) {
                JSONObject contentObj = JSONObject.parseObject(commonMsg.getBody().getSingleBody().getMsgContentJson());
                msgObj.put("type","message");
                msgObj.put("userName","");
                msgObj.put("msgType",commonMsg.getBody().getSingleBody().getMsgNoticeType());
                msgObj.put("sendMsgUserName", commonMsg.getBody().getSingleBody().getSenderId());
                msgObj.put("sendRealName", commonMsg.getBody().getSingleBody().getSenderRealName());
                msgObj.put("senderHeadImgUrl", commonMsg.getBody().getSingleBody().getHeadImgUrl());
                msgObj.put("bizSceneName",commonMsg.getBody().getSingleBody().getBizSceneName());
                msgObj.put("msgContentJson",commonMsg.getBody().getSingleBody().getMsgContentJson());
                msgObj.put("msgContent",commonMsg.getBody().getSingleBody().getMsgContent());
                msgObj.put("msgTitle",commonMsg.getBody().getSingleBody().getMsgTitle());
                msgObj.put("msgResourceId",commonMsg.getBody().getSingleBody().getResourceId());
                msgObj.put("createDatetime", DateUtils.getLongDateStr());
                msgObj.put("createUserName", commonMsg.getBody().getSingleBody().getSenderId());
                msgObj.put("readStatus","0");
                msgObj.put("notReadCnt",0);
                commonService.sysSysSendLog(commonMsg, contentObj.getString("first"),"0000", "推送站内消息成功");
                //提示消息入库
                commonService.writeNoticeMsg(commonMsg, new SendMsgUserInfoEntity());

                //发送消息
                if(jsonObj.getString("terminalType").equals("all")) {
                    pcClient.send(msgObj.toJSONString());
                    appClient.send(msgObj.toJSONString());
                }
                else if(jsonObj.getString("terminalType").equals("pc")){
                    pcClient.send(msgObj.toJSONString());
                }
                else if(jsonObj.getString("terminalType").equals("app")){
                    appClient.send(msgObj.toJSONString());
                }
            }
            else {
                //根据用户列表逐个发送
                List<SendMsgUserInfoEntity> userSendList = commonService.getUserList(commonMsg.getBody().getSingleBody().getTenantId(), commonMsg.getBody().getSingleBody().getSendAddressList());
                //为了记录日志，每个用户逐条发送
                for(SendMsgUserInfoEntity sendMsgUserInfoEntity : userSendList) {
                    JSONObject contentObj = JSONObject.parseObject(commonMsg.getBody().getSingleBody().getMsgContentJson());
                    msgObj.put("type","message");
                    msgObj.put("userName",sendMsgUserInfoEntity.getUserName());
                    msgObj.put("msgType",commonMsg.getBody().getSingleBody().getMsgNoticeType());
                    msgObj.put("sendMsgUserName", commonMsg.getBody().getSingleBody().getSenderId());
                    msgObj.put("sendRealName", commonMsg.getBody().getSingleBody().getSenderRealName());
                    msgObj.put("senderHeadImgUrl", commonMsg.getBody().getSingleBody().getHeadImgUrl());
                    msgObj.put("bizSceneName",commonMsg.getBody().getSingleBody().getBizSceneName());
                    msgObj.put("msgContentJson",commonMsg.getBody().getSingleBody().getMsgContentJson());
                    msgObj.put("msgContent",commonMsg.getBody().getSingleBody().getMsgContent());
                    msgObj.put("msgTitle",commonMsg.getBody().getSingleBody().getMsgTitle());
                    msgObj.put("msgResourceId",commonMsg.getBody().getSingleBody().getResourceId());
                    msgObj.put("createDatetime", DateUtils.getLongDateStr());
                    msgObj.put("createUserName", commonMsg.getBody().getSingleBody().getSenderId());
                    msgObj.put("readStatus", "0");
                    msgObj.put("notReadCnt",0);

                    commonService.sysSysSendLog(commonMsg, contentObj.getString("first"),"0000", "推送站内消息成功");
                    //提示消息入库
                    String id = commonService.writeNoticeMsg(commonMsg, sendMsgUserInfoEntity);
                    msgObj.put("id",id);
                    //发送消息
                    if(jsonObj.getString("terminalType").equals("all")) {
                        pcClient.send(msgObj.toJSONString());
                        appClient.send(msgObj.toJSONString());
                    }
                    else if(jsonObj.getString("terminalType").equals("pc")){
                        pcClient.send(msgObj.toJSONString());
                    }
                    else if(jsonObj.getString("terminalType").equals("app")){
                        appClient.send(msgObj.toJSONString());
                    }
                }
            }
            //关闭客户端连接 容量
            if(jsonObj.getString("terminalType").equals("all")) {
                pcClient.close();
                appClient.close();
            }
            else if(jsonObj.getString("terminalType").equals("pc")){
                pcClient.close();
            }
            else if(jsonObj.getString("terminalType").equals("app")){
                appClient.close();
            }
        }
    }

}