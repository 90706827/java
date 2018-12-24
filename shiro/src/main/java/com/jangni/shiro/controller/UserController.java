package com.jangni.shiro.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.jangni.shiro.entity.PomeloUser;
import com.jangni.shiro.service.RoleService;
import com.jangni.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName UserController
 * Description 测试
 * Author Mr.Jangni
 * Date 2018/12/23 14:50
 * Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getPageUser() {
        //你需要在进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法，紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。
        PageHelper.startPage(0, 2);
        List<PomeloUser> providerfunctionInfos = null;
        //使用PageInfo对查询结果结果进行封装
        PageInfo<PomeloUser> pageInfo = new PageInfo<>(providerfunctionInfos);
        return new Gson().toJson(pageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public String getUser() {

//        return new Gson().toJson(userService.getUserById(2));
        return new Gson().toJson(roleService.selectUserByUserName("user"));
    }
}