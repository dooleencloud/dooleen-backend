package com.dooleen.service.app.controller;

import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mc")
@Slf4j
public class TestMsgController {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private MsgSendUtil msgSendUtil;

    @GetMapping("/sendMsg")
    public Boolean sendMsg(@RequestParam("tenantId") String tenantId ) {
        log.info("===>> begin sendMsg。。。");
        SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
        sysSendLogEntity.setTenantId("DOOL1001");
        sysSendLogEntity.setSenderRealName("李秋宏001");
        sysSendLogEntity.setSenderId("liqh");
        sysSendLogEntity.setHeadImgUrl("https://22323.jps");
        sysSendLogEntity.setSendAddressList("all");//("[\"admin\",\"liqh\"]");  // all 表示发送全部
        sysSendLogEntity.setBizSceneName("会议通知");
        sysSendLogEntity.setMsgTitle("这是一个钉钉消息测试");
        sysSendLogEntity.setMsgContentJson("{ \"first\": \"钉钉测试001"+tenantId+"\", \"label1\":\"会议名称\", \"keyword1\": \"金刚川会议\", \"label2\":\"会议人\", \"keyword2\": \"李秋宏\", \"label3\":\"会议时间\", \"keyword3\": \"111\", \"label4\":\"会议内容\", \"keyword4\": \"启动会\", \"remark\": \"没人知道\" }");
        msgSendUtil.sendMsgToMq(sysSendLogEntity);
        return true;
    }

}