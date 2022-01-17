package com.qin.mq.rabbitmq.confirm;

import com.qin.mq.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * 发布确认模式
 * 1.单个确认
 * 2.批量确认
 * 3.异步批量确认
 *
 * @author DELL
 * @date 2022/01/17 22:25.
 */
public class ConfirmMessage {

    public static final String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception{
//        publishMessageIndividually(); //耗时3145ms
//        publishMessageBatch(); // 耗时380ms
        publishMessageAsync();//耗时205ms
    }

    /**
     * 单个确认
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        channel.queueDeclare(ACK_QUEUE_NAME, true, false, false, null);
        //开启确认默认
        channel.confirmSelect();

        //开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = "msg-" + i;
            channel.basicPublish("", ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            //单个消息确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功-" + i);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时： " + (endTime - startTime) + "ms");
    }

    /**
     * 批量确认
     * @throws IOException
     * @throws TimeoutException
     */
    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        channel.queueDeclare(ACK_QUEUE_NAME, true, false, false, null);
        //开启确认默认
        channel.confirmSelect();

        //开始时间
        long startTime = System.currentTimeMillis();

        int batchSize = 100;
        int confirmTimes = 0;
        for (int i = 0; i < 1000; i++) {
            String message = "msg-" + i;
            channel.basicPublish("", ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            if((i + 1) % 100 == 0){
                channel.waitForConfirms();
                confirmTimes++;
                System.out.println("确认次数： " + confirmTimes);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时： " + (endTime - startTime) + "ms");
    }

    /**
     * 异步发布确认
     */
    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        channel.queueDeclare(ACK_QUEUE_NAME, true, false, false, null);
        //开启确认默认
        channel.confirmSelect();

        //
        ConcurrentSkipListMap<Long, String> confirmMap = new ConcurrentSkipListMap<>();

        //添加监听器
        channel.addConfirmListener((deliveryTag, multiple) -> { //确认成功的监听器
            //删除确认的消息
            //如果是批量确认，则批量删除确认的消息
            if(multiple){
                ConcurrentNavigableMap confirmed = confirmMap.headMap(deliveryTag);
                confirmed.clear();
            }else{
                confirmMap.remove(deliveryTag);
            }

            System.out.println("确认的消息编号：" + deliveryTag + ", 未确认消息size: " + confirmMap.size());
        }, (deliveryTag, multiple) -> { //确认失败的监听器
            System.out.println("未确认的消息标号:" + deliveryTag);
        });

        //开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = "msg-" + i;
            //记录所有的发送的消息
            confirmMap.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("", ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));

        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时： " + (endTime - startTime) + "ms， 未确认消息size： " + confirmMap.size());
    }
}
