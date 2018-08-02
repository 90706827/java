package com.jangni.shiro.module;

import java.util.Date;

/**
 * @program: java
 * @description: user
 * @author: Mr.Jangni
 * @create: 2018-07-31 22:20
 **/
public class UserModule {
    private String name;
    private Integer age;
    private Date birthday;
    private String email;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

