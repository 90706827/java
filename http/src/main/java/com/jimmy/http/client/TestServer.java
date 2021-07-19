package com.jimmy.http.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName TestServer
 * Description
 * Author Mr.jimmy
 * Date 2019/2/28 22:32
 * Version 1.0
 **/
@RestController
public class TestServer {


    @RequestMapping(value = "/post")
    @ResponseBody
    public String post(){
        return "test";
    }

}