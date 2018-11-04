package com.jangni.rabbitmq.controller;

import com.jangni.rabbitmq.service.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/3 20:49
 **/
@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private ProducerService pullService;

    @GetMapping("/test")
    public void hello() {
        pullService.send();
    }
}
