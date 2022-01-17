package com.qin.mq.rabbitmq.simple;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.qin.mq.rabbitmq.util.SleepUtils;
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

    public static final String QUEUE_NAME = "durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        manualConsume2();
    }

    /**
     * 简单消费
     * @throws IOException
     * @throws TimeoutException
     */
    public static void simpleConsume() throws IOException, TimeoutException {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
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

    /**
     * 多个消费者轮询消费，手动应答，如果其中一个消费者挂掉，会由存活的消费者消费
     * @throws IOException
     * @throws TimeoutException
     */
    public static void manualConsume1() throws IOException, TimeoutException {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
        //设置不公平分发
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        /**
         * 消费消息
         * 1.队列名称
         * 2.是否自动应答，true自动应答，false手动应答
         * 3.接收消息的回调
         * 4.取消消费的回调
         */
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, (consumerTag, message) -> {
            SleepUtils.sleep(1);
            System.out.println("ConsumerTag:" + consumerTag + ", Message:" + new String(message.getBody()));
            /**
             * 1.消息标记tag
             * 2.是否批量应答消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
            System.out.println("消息消费被终端， consumerTag:" + consumerTag);
        });
    }

    public static void manualConsume2() throws IOException, TimeoutException {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
        //设置不公平分发
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);
        /**
         * 消费消息
         * 1.队列名称
         * 2.是否自动应答，true自动应答，false手动应答
         * 3.接收消息的回调
         * 4.取消消费的回调
         */
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, (consumerTag, message) -> {
            SleepUtils.sleep(30);
            System.out.println("ConsumerTag:" + consumerTag + ", Message:" + new String(message.getBody()));
            /**
             * 1.消息标记tag
             * 2.是否批量应答消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {
            System.out.println("消息消费被终端， consumerTag:" + consumerTag);
        });
    }
}
