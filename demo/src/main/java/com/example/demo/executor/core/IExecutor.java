package com.example.demo.executor.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;

public class IExecutor {

    /**
     * 线程池
     */
    ThreadPoolTaskExecutor pool;


    /**
     * 提交一个执行的内容
     *
     * @param service 执行业务
     * @param context 上下文
     */
    public Future submit(ChinaService service, Context context) {
        return pool.submit(new HandleExceptionCallable(service, context));
    }
}
