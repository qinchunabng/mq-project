package com.qin.mq.rabbitmq.topic;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/26 23:11.
 */
public class ReceiveLogsTopic02 {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //声明一个队列
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        //绑定交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        System.out.println("等待接收消息...");
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            System.out.println("收到消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
