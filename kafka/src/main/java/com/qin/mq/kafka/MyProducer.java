package com.qin.mq.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/09 12:11.
 */
public class MyProducer {

    public static void main(String[] args) throws IOException {
        //1. kafka生产者配置信息
        Properties properties = new Properties();
        //2. 指定kakfa地址
        properties.put("bootstrap.servers","192.168.3.45:9092");
        //3. 应答级别
        properties.put("acks", "all");
        //4. 重试次数
        properties.put("retries", 1);
        //5. 批次大小
        properties.put("batch.size", 16384);
        //6. 等待时间
        properties.put("linger.ms", 1);
        //7. RecordAccumulator缓冲区大小
        properties.put("buffer.memory", 33554432);
        //8. Key,Value的序列化类
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //9. 创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        //10. 发送数据
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("quickstart-event", "test-" + i), (recordMetadata, e) -> {
                if(e != null){
                    System.err.println(e);
                }else{
                    System.out.println("Metadata: topic -> " + recordMetadata.topic() + ", partition -> " + recordMetadata.partition() + ", offset -> " + recordMetadata.offset());
                }
            });
        }
        //11. 关闭资源
        producer.close();
        System.in.read();
    }
}
