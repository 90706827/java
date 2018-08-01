package com.jangni.shiro.service;


import com.jangni.shiro.entity.User;
import com.jangni.shiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @program: java
 * @description: 用户
 * @author: Mr.Jangni
 * @create: 2018-08-02 00:08
 **/
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String name){
        return userRepository.findByName(name);
    }

}
