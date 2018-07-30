package com.jangni.shiro.service;

import com.jangni.shiro.mapper.UserMapper;
import com.jangni.shiro.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 17:27
 * Description:
 */
@Component
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByUserName(String username){
        return userMapper.findByUserName(username);
    }
}
