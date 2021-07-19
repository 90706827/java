package com.jimmy.rabbitmq.service.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description TODO
 * @Author jimmy
 * @Date 2018/11/3 22:08
 **/
@Component
public class ProducerService {
    private static final String QUEUE_NAME = "test";
    private int count = 0;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        boolean kg = true;
        while (kg) {
            String sendMsg = "hello " + count + new Date();
            System.out.println("发送: " + sendMsg);
            rabbitTemplate.convertAndSend(QUEUE_NAME, sendMsg);
            count++;
            if (count == 100) {
                kg = false;
            }
        }

    }
}
