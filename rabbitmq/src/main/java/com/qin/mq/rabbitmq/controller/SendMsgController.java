package com.qin.mq.rabbitmq.controller;

import com.qin.mq.rabbitmq.config.DelayedQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/28 21:50.
 */
@RestController
@RequestMapping("/msg")
public class SendMsgController {

    private final Logger logger = LoggerFactory.getLogger(SendMsgController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{message}")
    public void sendMsg(@PathVariable String message){
        logger.info("发送一条消息: {}", message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自TTL为10s的队列: " + message);
        rabbitTemplate.convertAndSend("X","XB","消息来自TTL为40s的队列:" + message);
    }

    @GetMapping("/{message}/ttl/{ttl}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttl){
        logger.info("发送一条消息: {}, TTL: {}", message, ttl);
        rabbitTemplate.convertAndSend("X", "XC", "消息来自TTL为40s的队列:" + message, msg -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        });
    }

    @GetMapping("/{message}/delay/{delayTime}")
    public void sendDelayedMsg(@PathVariable String message, @PathVariable Integer delayTime){
        logger.info("发送一条Delayed消息: {}, delayTime: {}", message, delayTime);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message, msg -> {
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}
