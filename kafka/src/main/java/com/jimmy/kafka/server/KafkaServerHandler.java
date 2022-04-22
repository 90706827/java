package com.jimmy.kafka.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;


public class KafkaServerHandler {


    public void proc(ConsumerRecord<String, String> record) {
        String reqMsg = record.value();
//       logger.info("接收到kafka消息：" + reqMsg);
    }
}
