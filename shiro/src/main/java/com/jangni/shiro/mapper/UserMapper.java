package com.jangni.shiro.mapper;

import com.jangni.shiro.module.User;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 17:23
 * Description:
 */
public interface UserMapper {
    public User findByUserName(String username);
}
