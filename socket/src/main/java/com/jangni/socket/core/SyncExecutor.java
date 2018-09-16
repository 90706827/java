package com.jangni.socket.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @ClassName SyncExecutor
 * @Description 同步线程池
 * @Author Mr.Jangni
 * @Date 2018/9/16 16:42
 * @Version 1.0
 **/
public class SyncExecutor {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    private int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;

    public  ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setThreadNamePrefix("taks-jangni-");
        threadPoolTaskExecutor.afterPropertiesSet();
//        pool.setTaskDecorator(new MaxWaitTimeoutTaskDecorator(maxWaitTimeout))
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }
}
