package com.jangni.shiro.module;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 17:11
 * Description:
 */
public class User implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<Role>();
}
