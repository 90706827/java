package com.jangni.shiro.mapper;

import com.jangni.shiro.entity.RolePower;

public interface RolePowerMapper {
    int insert(RolePower record);

    int insertSelective(RolePower record);
}