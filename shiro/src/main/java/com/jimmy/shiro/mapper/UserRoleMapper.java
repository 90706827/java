package com.jimmy.shiro.mapper;

import com.jimmy.shiro.entity.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}