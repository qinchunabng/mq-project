package com.qin.mq.rabbitmq.direct;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/26 22:59.
 */
public class ReceiveLogsDirect01 {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列
        String queueName = "console";
        channel.queueDeclare(queueName, false, false, false, null);
        //绑定交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        channel.queueBind(queueName, EXCHANGE_NAME, "warning");

        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            System.out.println("收到消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
