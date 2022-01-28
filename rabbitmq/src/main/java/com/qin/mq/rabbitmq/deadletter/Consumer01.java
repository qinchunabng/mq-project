package com.qin.mq.rabbitmq.deadletter;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/27 11:23.
 */
public class Consumer01 {

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
        //声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明普通队列
        /**
         * 3种情况下消息会被投递到死信队列
         * 1.消息过期
         * 2.队列达到最大消息长度
         * 3.消息被拒收
         */
        //声明死信队列
        Map<String, Object> arguments = new HashMap<>();
        //设置过期时间,单位:ms
//        arguments.put("x-message-ttl", 10000);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "lisi");
        //设置队列长度
        arguments.put("x-max-length", 2);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        //绑定交换机
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        System.out.println("等待接收消息...");

        channel.basicConsume(NORMAL_QUEUE, true, (consumerTag, message) -> {
            System.out.println("消费消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
