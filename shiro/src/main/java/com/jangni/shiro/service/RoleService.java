package com.jangni.shiro.service;

import com.jangni.shiro.entity.Power;
import com.jangni.shiro.entity.Role;
import com.jangni.shiro.mapper.PowerMapper;
import com.jangni.shiro.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName RoleService
 * Description 用户角色权限认证
 * Author Mr.Jangni
 * Date 2018/12/24 19:53
 * Version 1.0
 **/
@Component
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PowerMapper powerMapper;

    public List<Role> selectUserByUserName(String userName) {
        return roleMapper.selectByUserName(userName);
    }

    public List<Power> selectByRoleId(Integer roleId){
        return powerMapper.selectByRoleId(roleId);
    }
}