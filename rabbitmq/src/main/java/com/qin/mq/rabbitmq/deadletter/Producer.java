package com.qin.mq.rabbitmq.deadletter;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/27 11:45.
 */
public class Producer {

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //设置消息的过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String text = scanner.next();
            channel.basicPublish(Consumer01.NORMAL_EXCHANGE, "zhangsan", null, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:" + text);
        }
    }
}
