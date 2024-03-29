package com.jimmy.shiro.mapper;

import com.jimmy.shiro.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUserName(String userName);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);
}