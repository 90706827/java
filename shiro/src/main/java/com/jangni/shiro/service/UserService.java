package com.jangni.shiro.service;

import com.jangni.shiro.entity.PomeloUser;
import com.jangni.shiro.entity.User;
import com.jangni.shiro.mapper.PomeloUserMapper;
import com.jangni.shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName UserService
 * Description 用户
 * Author Mr.Jangni
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