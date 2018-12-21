package com.jangni.druid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName IndexController
 * Description test
 * Author  Mr.Jangni
 * Date 2018/12/20 18:12
 * Version 1.0
 **/
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/index")
    public String index(){

        return "index";
    }
}