package com.jangni.shiro.mapper;

import com.jangni.shiro.entity.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}