package com.jangni.druid.controller;

import com.google.gson.Gson;
import com.jangni.druid.entity.User;
import com.jangni.druid.repository.UserRepository;
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
    private Gson json = new Gson();
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/add")
    public Object add(@RequestParam(name = "name", required = true) String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return json.toJson(user);
    }

    @RequestMapping("/list")
    public Object list() {
        Iterable<User> users = userRepository.findAll();
        return json.toJson(users);
    }

}
