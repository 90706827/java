package com.jangni.socket.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @ClassName TaskExecutors
 * @Description 自定义线程池
 * @Author Mr.Jangni
 * @Date 2018/9/19 11:13
 * @Version 1.0
 **/
public class TaskExecutors {
    public static ThreadPoolTaskExecutor pool = getThreadPoolTaskExecutor();

    private static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(4);
        pool.setMaxPoolSize(8);
        pool.setKeepAliveSeconds(10);
        pool.setAllowCoreThreadTimeOut(true);
        pool.setThreadNamePrefix("taks-jangni-");
        pool.afterPropertiesSet();
        return pool;
    }
}
