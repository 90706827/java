package com.jimmy.rabbitmq.service.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author jimmy
 * @Date 2018/11/3 22:09
 **/
@Component
@RabbitListener(queues = "test")
public class CustomerService {



    @RabbitHandler
    public void process(String hello) {
        System.out.println("接收: " + hello);
    }
}
