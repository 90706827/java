package com.jimmy.shiro.service;

import com.jimmy.shiro.entity.User;
import com.jimmy.shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName UserService
 * Description 用户
 * Author Mr.jimmy
 * Date 2018/12/23 14:52
 * Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }
}