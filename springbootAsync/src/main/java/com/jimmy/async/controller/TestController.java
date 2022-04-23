package com.jimmy.async.controller;

import com.jimmy.async.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/22 23:45
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/get")
    public String get(@RequestParam("id")Long id){
        return testService.getTestA(id).getName();
    }
    @GetMapping("/insert")
    public String insert() throws Exception {
        return testService.insert();
    }

    @GetMapping("/inserts")
    public String inserts() throws Exception {
        return testService.asyncInsert();
    }
}
