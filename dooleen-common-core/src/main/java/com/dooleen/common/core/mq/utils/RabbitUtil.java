package com.dooleen.common.core.mq.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitUtil {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Async("asyncTaskExecutor")
	public void sendToQueue(String queue, Object data) {
		rabbitTemplate.convertAndSend(queue, data);
	}

	@Async("asyncTaskExecutor")
	public void convertAndSend(String exchange, String routingKey, final Object object) {
		rabbitTemplate.convertAndSend(exchange, routingKey, object);
	}
}
