package com.jimmy.druid.repository;

import com.jimmy.druid.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: java
 * @description: 用户dao
 * @author: Mr.jimmy
 * @create: 2018-07-31 21:08
 **/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
