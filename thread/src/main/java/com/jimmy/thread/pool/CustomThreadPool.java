package com.jimmy.thread.pool;

import com.google.common.base.Stopwatch;
import com.jimmy.thread.common.MonitorThreadPool;
import com.jimmy.thread.task.SynchronizedObject;
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
                150,
                150,
                1000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(true),
                new CustomThreadFactory("Task-Thread-"),
                new CustomRejectedExecutionHandler());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }


    @Override
    public void task() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SynchronizedObject synchronizedObject = new SynchronizedObject();
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            pool.execute(() -> {
                synchronizedObject.add("第" + finalI + "个人");
            });
        }
        stopwatch.stop();
        logger.info("用时：{}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool();
        customThreadPool.start();
    }
}