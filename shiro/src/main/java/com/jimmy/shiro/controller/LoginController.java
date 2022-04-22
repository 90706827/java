package com.jimmy.shiro.controller;

import com.jimmy.shiro.service.RoleService;
import com.jimmy.shiro.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ClassName LoginController
 * Description 登录退出
 * Author Mr.jimmy
 * Date 2018/12/24 22:20
 * Version 1.0
 **/
@Controller
public class LoginController {


    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    //注解的使用
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create() {
        return "Create success!";
    }

    //错误页面展示
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    //post登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Param("userName") String userName, @Param("passWord") String passWord) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return "index";
    }

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

//    //数据初始化
//    @RequestMapping(value = "/addUser")
//    public String addUser(@RequestBody Map<String,Object> map){
//        User user = loginService.addUser(map);
//        return "addUser is ok! \n" + user;
//    }
//
//    //角色初始化
//    @RequestMapping(value = "/addRole")
//    public String addRole(@RequestBody Map<String,Object> map){
//        Role role = loginService.addRole(map);
//        return "addRole is ok! \n" + role;
//    }

    //登出
    @RequestMapping(value = "/logout")
    public String logout() {
        return "logout";
    }
}