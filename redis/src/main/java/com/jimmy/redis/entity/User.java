package com.jimmy.redis.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: java
 * @description: 用户信息
 * @author: Mr.jimmy
 * @create: 2018-07-31 21:06
 **/

@Entity
@Table(name = "tbl_user") // 指定关联的数据库的表名
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键ID

    private String name; // 姓名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }

}
