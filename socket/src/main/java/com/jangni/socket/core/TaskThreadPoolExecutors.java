package com.jangni.socket.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName TaskThreadPoolExecutors
 * @Description 线程池
 * @Author Mr.Jangni
 * @Date 2018/9/16 15:39
 * @Version 1.0
 **/
public class TaskThreadPoolExecutors {

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        threadPoolTaskExecutor.setKeepAliveSeconds(10);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setThreadNamePrefix("taks-jangni-");
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }
}
