package com.dooleen.common.core.mq.utils;

import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.service.SysMsgConfigEntityService;
import com.dooleen.common.core.constant.MqQueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgSendUtil {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private SysMsgConfigEntityService sysMsgConfigEntityService;

	@Async("asyncTaskExecutor")
	public void sendMsgToMq(SysSendLogEntity sysSendLogEntity) {
		rabbitTemplate.convertAndSend(MqQueueConstant.PUSH_APP_MESSAGE, sysSendLogEntity);
		log.debug("MsgSendUtil===>push app message===>消息写入mq成功");
	}
}
