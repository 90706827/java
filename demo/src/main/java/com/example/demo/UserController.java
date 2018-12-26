package com.example.demo;

import org.jboss.logging.Param;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String user(@PathParam("id") String id){
        return "abc";
    }
}
