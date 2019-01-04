package com.example.demo.executor.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

class AsyncExecutor extends IExecutor {

    AsyncExecutor() {
        super.pool = new ThreadPoolTaskExecutor();
        // 防止频繁回收核心线程，最低保留4个
        super.pool.setCorePoolSize(4);
        super.pool.setQueueCapacity(0);
        // 允许核心线程池超时后回收
        super.pool.setAllowCoreThreadTimeOut(true);
        super.pool.setThreadNamePrefix("thread-async-biz-");
        super.pool.afterPropertiesSet();
    }

}
