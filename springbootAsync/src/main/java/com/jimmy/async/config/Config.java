package com.jimmy.async.config;

import com.jimmy.async.core.CustomRejectedExecutionHandler;
import com.jimmy.async.core.CustomThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 10:36
 **/
@Configuration
public class Config {
    @Bean
    public ExecutorService serviceThread() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                10,
                20,
                1000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new CustomThreadFactory("Task-Thread-"),
                new CustomRejectedExecutionHandler());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }
}
