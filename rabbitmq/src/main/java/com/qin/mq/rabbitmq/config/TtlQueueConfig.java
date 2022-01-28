package com.qin.mq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/27 22:58.
 */
@Configuration
public class TtlQueueConfig {

    /**
     * 普通交换机名称
     */
    public static final String X_EXCHANGE = "X";
    /**
     * 私信交换机名称
     */
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    /**
     * 普通队列名称
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    /**
     * 私信队列名称
     */
    public static final String DEAD_LETTER_QUEUE = "QD";

    @Bean
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue queueA(){
        return QueueBuilder.durable(QUEUE_A)
                //设置死信交换机
                .withArgument("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE)
                //设置死信队列RoutingKey
                .withArgument("x-dead-letter-routing-key", "YD")
                //设置TTL,单位:ms
                .withArgument("x-message-ttl", 10000)
                .build();
    }

    @Bean
    public Queue queueB(){
        return QueueBuilder.durable(QUEUE_B)
                //设置死信交换机
                .withArgument("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE)
                //设置死信队列RoutingKey
                .withArgument("x-dead-letter-routing-key", "YD")
                //设置TTL,单位:ms
                .withArgument("x-message-ttl", 40000)
                .build();
    }

    @Bean
    public Queue queueC(){
        return QueueBuilder.durable(QUEUE_C)
                //设置死信交换机
                .withArgument("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE)
                //设置死信队列RoutingKey
                .withArgument("x-dead-letter-routing-key", "YD")
                .build();
    }

    @Bean
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Binding queueABindX(){
        return BindingBuilder.bind(queueA()).to(xExchange()).with("XA");
    }

    @Bean
    public Binding queueBBindX(){
        return BindingBuilder.bind(queueB()).to(xExchange()).with("XB");
    }

    @Bean
    public Binding queueDBindY(){
        return BindingBuilder.bind(queueD()).to(yExchange()).with("YD");
    }

    @Bean
    public Binding queueCBindX(){
        return BindingBuilder.bind(queueA()).to(xExchange()).with("XC");
    }
}
