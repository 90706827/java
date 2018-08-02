package com.jangni.redis.repository;

import com.jangni.redis.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: java
 * @description: 用户dao
 * @author: Mr.Jangni
 * @create: 2018-07-31 21:08
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
