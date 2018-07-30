package com.jangni.shiro.module;

import java.io.Serializable;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 13:57
 * Description:
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer uid;
    private String name;
    private String password;
    private String salt;
    private Integer state;
    private String username;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

}
