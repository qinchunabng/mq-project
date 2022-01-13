package com.qin.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/13 23:02.
 */
public class Producer {

    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接host
        factory.setHost("192.168.3.45");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123456");

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
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
        String message = "hello world";
        /**
         * 1.发送到哪个交换器
         * 2.路由的key，本次是队列名称
         * 3.其他配置参数
         * 4.发送消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送完毕");
    }
}
