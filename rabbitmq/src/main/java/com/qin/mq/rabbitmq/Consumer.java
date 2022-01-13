package com.qin.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者代码
 *
 * @author DELL
 * @date 2022/01/13 23:30.
 */
public class Consumer {

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
         * 消费消息
         * 1.队列名称
         * 2.是否自动应答，true自动应答，false手动应答
         * 3.接收消息的回调
         * 4.取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("ConsumerTag:" + consumerTag + ", Message:" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println("消息消费被终端， consumerTag:" + consumerTag);
        });
    }
}
