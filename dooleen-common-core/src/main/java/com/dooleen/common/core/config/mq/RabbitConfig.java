package com.dooleen.common.core.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dooleen.common.core.constant.HistoryDealTypeConstant;
import com.dooleen.common.core.constant.MqExchangeConstant;
import com.dooleen.common.core.constant.MqQueueConstant;
import com.dooleen.common.core.constant.MsgTypeConstant;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-08-14 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:zoujin
 * @Update:
 */
@Configuration
public class RabbitConfig {

	// 历史
    /**
     * @Description: 初始化history的直连交换器
     * @Title: initHisDirectExchange
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
	@Bean
	public DirectExchange initHisDirectExchange() {
		return new DirectExchange(MqExchangeConstant.HISTORY_DIRECT_EXCHANGE);
	}

    /**
     * @Description: 初始化HISTORY_ADD_QUEUE
     * @Title: initHistoryAddQueue
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
    @Bean
    public Queue initHistoryAddQueue() {
        return new Queue(MqQueueConstant.HISTORY_ADD_QUEUE);
    }

    /**
     * @Description: 初始化HISTORY_ADD_QUEUE
     * @Title: 绑定使用路由键为 HistoryDealTypeConstant.ADD 的 initHistoryAddQueue 队列到直连交换器上
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
    @Bean
    public Binding bindingHistoryAdd() {
      return BindingBuilder.bind(initHistoryAddQueue()).to(initHisDirectExchange()).with(HistoryDealTypeConstant.ADD);
    }




    // 消息
    /**
     * @Description: 初始化msg的直连交换器
     * @Title: initMsgExchange
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
	@Bean
	public DirectExchange initMsgExchange() {
		return new DirectExchange(MqExchangeConstant.MSG_DIRECT_EXCHANGE);
	}

    @Bean
    public Queue initEmailCommonQueue() {
        return new Queue(MqQueueConstant.EMAIL_QUEUE);
    }

    // 绑定使用路由键为 MsgTypeConstant.EMAIL 的 initEmailCommonQueue 队列到直连交换器上
    @Bean
    public Binding bindingEmailCommon() {
      return BindingBuilder.bind(initEmailCommonQueue()).to(initMsgExchange()).with(MsgTypeConstant.EMAIL);
    }

    @Bean
    public Queue initWeChatCommonQueue() {
        return new Queue(MqQueueConstant.WECHAT_QUEUE);
    }

    // 绑定使用路由键为 MsgTypeConstant.WECHAT 的 initWeChatCommonQueue 队列到直连交换器上
    @Bean
    public Binding bindingWeChatCommon() {
        return BindingBuilder.bind(initWeChatCommonQueue()).to(initMsgExchange()).with(MsgTypeConstant.WECHAT);
    }


    // 日志
    /**
     * @Description: 初始化LOG_QUEUE
     * @Title: initLogQueue
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
    @Bean
    public Queue initLogQueue() {
        return new Queue(MqQueueConstant.LOG_QUEUE);
    }




    // 日志
    /**
     * @Description: 初始化PUSH_DATA_QUEUE
     * @Title: initPushDataQueue
     * @return Queue
     * @author zoujin
     * @date 2020-07-07 10:00:01
     */
    @Bean
    public Queue initPushDataQueue() {
        return new Queue(MqQueueConstant.PUSH_DATA_QUEUE);
    }
}
