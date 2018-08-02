package com.jangni.redis.service;


import com.jangni.redis.entity.User;
import com.jangni.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: java
 * @description: 用户
 * @author: Mr.Jangni
 * @create: 2018-08-02 00:08
 **/
@Component
@CacheConfig(cacheNames = "user")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Cacheable
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void save(User user){
        userRepository.save(user);
    }

}
