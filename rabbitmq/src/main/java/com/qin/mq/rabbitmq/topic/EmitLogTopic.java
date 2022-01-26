package com.qin.mq.rabbitmq.topic;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/26 23:10.
 */
public class EmitLogTopic {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String text = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "lazy.orange.12", null, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:" + text);
        }
    }
}
