package com.jangni.shiro.module;

import java.util.HashSet;
import java.util.Set;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 17:13
 * Description:
 */
public class Role {
    private Integer rid;
    private String rname;
    private Set<User> users = new HashSet<>();
    private Set<Module> modules = new HashSet<>();
}
