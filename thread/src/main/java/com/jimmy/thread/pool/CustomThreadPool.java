package com.jimmy.thread.pool;

import com.google.common.base.Stopwatch;
import com.jimmy.thread.common.MonitorThreadPool;
import com.jimmy.thread.task.LockObject;
import com.jimmy.thread.task.ReentrantLockTest;
import com.jimmy.thread.task.SynchronizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CustomThreadPool extends MonitorThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPool.class);
    protected ExecutorService service = getThreadPoolExecutor();

    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() {
        pool = new ThreadPoolExecutor(
                100,
                200,
                1000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new CustomThreadFactory("Task-Thread-"),
                new CustomRejectedExecutionHandler());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }


    @Override
    public void task() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SynchronizedTest sync = new SynchronizedTest();
        ReentrantLockTest reentrant = new ReentrantLockTest();
        for (int i = 1; i <= 10; i++) {
            pool.execute(new LockObject("线程" + i, reentrant));
        }
        logger.info("用时：{}", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool();
        customThreadPool.start();
    }
}