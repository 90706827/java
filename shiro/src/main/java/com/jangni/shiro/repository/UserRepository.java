package com.jangni.shiro.repository;

import com.jangni.shiro.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: java
 * @description: 用户dao
 * @author: Mr.Jangni
 * @create: 2018-07-31 21:08
 **/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
