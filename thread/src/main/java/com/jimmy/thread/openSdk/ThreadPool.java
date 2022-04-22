package com.jimmy.thread.openSdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jimmy
 */
public abstract class ThreadPool {
    protected ExecutorService service = getThreadPoolExecutor();

    public ThreadPoolExecutor getThreadPoolExecutor() {
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
