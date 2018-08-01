package com.jangni.shiro.controller;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: java
 * @description: 游客访问
 * @author: Mr.Jangni
 * @create: 2018-08-02 00:35
 **/
@RestController
@RequestMapping("/guest")
public class GuestController{
    private Gson gson;

    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public String login() {
        return gson.toJson("欢迎进入，您的身份是游客");
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String submitLogin() {
        return gson.toJson("您拥有获得该接口的信息的权限！");
    }
}
