package com.jangni.redis.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.jangni.redis.config.HibernateProxyTypeAdapter;
import com.jangni.redis.entity.User;
import com.jangni.redis.service.UserService;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: java
 * @description: 用户
 * @author: Mr.Jangni
 * @create: 2018-07-31 21:10
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    private Gson json = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public Object add(@RequestParam(name = "name", required = true) String name) {
        User user = new User();
        user.setName(name);
        userService.save(user);
        return json.toJson(user);
    }

    @RequestMapping("/list")
    public Object list() {
        Iterable<User> users = userService.findAll();
        return json.toJson(users);
    }

    @RequestMapping("/find")
    public Object find(@RequestParam(name = "id", required = true) Long id) {
        User user = userService.findById(id);
        return json.toJson(user);
    }

    @RequestMapping("/del")
    public Object del(@RequestParam(name = "id", required = true) Long id) {

        userService.del(id);
        return json.toJson("ok");
    }
}
