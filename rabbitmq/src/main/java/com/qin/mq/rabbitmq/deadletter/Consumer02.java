package com.qin.mq.rabbitmq.deadletter;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/27 11:23.
 */
public class Consumer02 {

    /**
     * 普通交换机
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    /**
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "dead_exchange";
    /**
     * 普通队列的名称
     */
    public static final String NORMAL_QUEUE = "normal_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args)  throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("等待接收消息...");

        channel.basicConsume(DEAD_QUEUE, true, (consumerTag, message) -> {
            System.out.println("消费消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
