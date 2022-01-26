package com.qin.mq.rabbitmq.direct;

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
public class EmitDirectLogs {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String text = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "info", null, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:" + text);
        }
    }
}
