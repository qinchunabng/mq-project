package com.qin.mq.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/28 22:14.
 */
@Component
public class DeadLetterQueueConsumer {


    private final Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        String msg = new String(message.getBody());
        logger.info("收到死信队列消息: {}", msg);
    }
}
