package com.jimmy.redis.service;


import com.jimmy.redis.entity.User;
import com.jimmy.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: java
 * @description: 用户
 * @author: Mr.jimmy
 * @create: 2018-08-02 00:08
 **/
@Component
//@CacheConfig(cacheNames = "user")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，
     * 而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     * @return
     */
    @Cacheable(value = "user")
    public List<User> findAll() {
        System.out.println("sql");
        return userRepository.findAll();
    }

    @CachePut(value = "user",key="#user.id")
    public void save(User user){
        userRepository.save(user);
    }

    /**
     * #@CacheEvict:是用来标注在需要清除缓存元素的方法或类上的。当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
     *  #@CacheEvict可以指定的属性有value、key、condition、allEntries和beforeInvocation。
     *  其中value、key和condition的语义与@Cacheable对应的属性类似。即value表示清除操作是发生在哪些Cache上的（对应Cache的名称）；
     *  key表示需要清除的是哪个key，如未指定则会使用默认策略生成的key；condition表示清除操作发生的条件。
     *  下面我们来介绍一下新出现的两个属性allEntries和beforeInvocation。
     * allEntries:是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。
     * 当指定了allEntries为true时，Spring Cache将忽略指定的key。有的时候我们需要Cache一下清除所有的元素，
     * 这比一个一个清除元素更有效率。@CacheEvict(value="users", allEntries=true)
     * beforeInvocation:清除操作默认是在对应方法成功执行之后触发的，即方法如果因为抛出异常而未能成功返回时也不会触发清除操作。
     * 使用beforeInvocation可以改变触发清除操作的时间，当我们指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素。
     * #@CacheEvict(value="users", beforeInvocation=true)
     * @param id
     */
    @CacheEvict(value = "user",key="#id",allEntries = true,beforeInvocation = true)
    public void del(long id){
        userRepository.deleteById(id);
    }

    /**
     * #@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @param id
     * @return
     */
    @Cacheable(value = "user",key = "#id")
    public User findById(long id){
       return userRepository.getOne(id);
    }
}
