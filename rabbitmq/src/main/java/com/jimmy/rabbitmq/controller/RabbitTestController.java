package com.jimmy.rabbitmq.controller;

import com.jimmy.rabbitmq.service.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author jimmy
 * @Date 2018/11/3 20:49
 **/
@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private ProducerService pullService;

    /**
     * @Author Mr.jimmy
     * @Description dsdfasdfasdf
     * @Date 2018/11/5 0:22
     * @Param []
     * @return void
     **/
    @GetMapping("/test")
    public void hello() {
        pullService.send();
    }
}
