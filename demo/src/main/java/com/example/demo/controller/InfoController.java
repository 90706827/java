package com.example.demo.controller;

import okhttp3.internal.http2.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName InfoController
 * @Description
 * @Author Mr.jimmy
 * @Date 2019/3/13 13:06
 * @Version 1.0
 **/
@RestController
public class InfoController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e) {

        return "局部异常";
    }

    @RequestMapping(value = "/info")
    public String getinfo(){
        return "";
    }
}
