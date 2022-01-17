package com.qin.mq.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/14 22:20.
 */
public class RabbitMqUtils {

    public static final String HOST = "192.168.3.45";

    public static final String USERNAME = "admin";

    public static final String PASSWORD = "123456";

    /**
     * 获取信道
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接host
        factory.setHost(HOST);
        //用户名
        factory.setUsername(USERNAME);
        //密码
        factory.setPassword(PASSWORD);

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        return connection.createChannel();
    }
}
