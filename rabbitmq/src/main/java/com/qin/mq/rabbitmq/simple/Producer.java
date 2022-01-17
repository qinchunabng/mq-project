package com.qin.mq.rabbitmq.simple;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/13 23:02.
 */
public class Producer {

    //队列名称
    public static final String QUEUE_NAME = "durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        durableMessage();
    }

    public static void simpleProduce() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 生成一个队列
         * 1.队列名称
         * 2.是否持久化队列，默认存储在内存中
         * 3.是否共多个消费者消息，true只能一个消费者消息，false多个消费者消息
         * 4.是否自动删除，true自动删除
         * 5.配置属性
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发消息
//        String message = "hello world";

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.nextLine();
            /**
             * 1.发送到哪个交换器
             * 2.路由的key，本次是队列名称
             * 3.其他配置参数
             * 4.发送消息体
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        }

        System.out.println("消息发送完毕");
    }

    /**
     * 持久化消息
     * @throws IOException
     * @throws TimeoutException
     */
    public static void durableMessage() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 生成一个队列
         * 1.队列名称
         * 2.是否持久化队列，默认存储在内存中
         * 3.是否共多个消费者消息，true只能一个消费者消息，false多个消费者消息
         * 4.是否自动删除，true自动删除
         * 5.配置属性
         */
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        //发消息
//        String message = "hello world";

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.nextLine();
            /**
             * 1.发送到哪个交换器
             * 2.路由的key，本次是队列名称
             * 3.其他配置参数
             * 4.发送消息体
             */
            //持久化消息需要同时设置队列和消息为持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        }

        System.out.println("消息发送完毕");
    }



}
