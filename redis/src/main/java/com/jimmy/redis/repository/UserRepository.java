package com.jimmy.redis.repository;

import com.jimmy.redis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: java
 * @description: 用户dao
 * @author: Mr.jimmy
 * @create: 2018-07-31 21:08
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
