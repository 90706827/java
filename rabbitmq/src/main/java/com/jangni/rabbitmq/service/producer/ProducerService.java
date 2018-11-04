package com.jangni.rabbitmq.service.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/3 22:08
 **/
@Component
public class ProducerService {
    private static final String QUEUE_NAME = "test";
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("发送: " + sendMsg);
        this.rabbitTemplate.convertAndSend(QUEUE_NAME, sendMsg);
    }
}
