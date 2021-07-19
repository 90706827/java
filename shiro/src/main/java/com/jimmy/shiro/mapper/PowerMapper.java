package com.jimmy.shiro.mapper;

import com.jimmy.shiro.entity.Power;

import java.util.List;

public interface PowerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Power record);

    int insertSelective(Power record);

    Power selectByPrimaryKey(Integer id);

    List<Power> selectByRoleId(Integer roleId);

    int updateByPrimaryKey(Power record);

    int updateByPrimaryKeySelective(Power record);
}