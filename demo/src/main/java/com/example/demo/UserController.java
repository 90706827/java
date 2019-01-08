package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * ClassName UserController
 * Description test
 * Author  Mr.Jangni
 * Date 2018/12/26 10:22
 * Version 1.0
 **/
@RestController
public class UserController {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String user(@PathParam("id") String id) {
        return "abc";
    }
}
