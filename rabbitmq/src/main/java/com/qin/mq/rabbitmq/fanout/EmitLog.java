package com.qin.mq.rabbitmq.fanout;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * description
 *
 * @author qcb
 * @date 2022/01/26 22:53.
 */
public class EmitLog {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //声明一个临时队列,队列的名称是随机的，当消费者断开连接时，队列被自动删除
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String text = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "", null, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:" + text);
        }
    }
}
