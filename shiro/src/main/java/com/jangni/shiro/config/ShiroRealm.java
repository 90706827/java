package com.jangni.shiro.config;

import com.jangni.shiro.entity.Power;
import com.jangni.shiro.entity.Role;
import com.jangni.shiro.entity.User;
import com.jangni.shiro.service.RoleService;
import com.jangni.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * program: java
 * description: 自定义身份和权限认证操作
 * author: Mr.Jangni
 * create: 2018-07-31 23:48
 **/
@Component
public class ShiroRealm extends AuthorizingRealm implements Logger {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByUserName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            throw new AccountException("用户不存在");
        }
        if (!user.getPassWord().equals(new String((char[]) authenticationToken.getCredentials()))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(user.getUserName(), user.getPassWord().toString(), getName());
    }

    /**
     * 获取授权信息
     * param principalCollection
     * return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名称
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //查询用户
        List<Role> roleList = roleService.selectUserByUserName(userName);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : roleList) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            logger.info("加载角色：{}", role.getRoleName());
            List<Power> powerList = roleService.selectByRoleId(role.getId());
            for (Power power : powerList) {
                //添加权限
                simpleAuthorizationInfo.addStringPermission(power.getPowerName());
                logger.info("加载权限：{}", role.getRoleName());
            }
        }
        return simpleAuthorizationInfo;
    }
}
