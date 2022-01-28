package com.qin.mq.rabbitmq.consumer;

import com.qin.mq.rabbitmq.config.DelayedQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费基于插件的延迟消息
 *
 * @author DELL
 * @date 2022/01/28 23:28.
 */
@Component
public class DelayQueueConsumer {

    private final Logger logger = LoggerFactory.getLogger(DelayQueueConsumer.class);

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedMessage(Message message){
        logger.info("收到延迟队列的消息: {}", new String(message.getBody()));
    }
}
