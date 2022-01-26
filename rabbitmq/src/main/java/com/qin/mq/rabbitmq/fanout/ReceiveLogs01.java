package com.qin.mq.rabbitmq.fanout;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/26 22:44.
 */
public class ReceiveLogs01 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //声明一个临时队列,队列的名称是随机的，当消费者断开连接时，队列被自动删除
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息....");
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            System.out.println("收到消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
