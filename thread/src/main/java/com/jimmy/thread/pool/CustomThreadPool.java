package com.jimmy.thread.pool;

import com.google.common.base.Stopwatch;
import com.jimmy.thread.common.MonitorThreadPool;
import com.jimmy.thread.openSdk.OpenSDK;
import com.jimmy.thread.task.LockObject;
import com.jimmy.thread.task.ReentrantLockTest;
import com.jimmy.thread.task.RunnableTask;
import com.jimmy.thread.task.SynchronizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
                10,
                20,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(30),
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
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                20,
                20,
                3000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new com.jimmy.thread.openSdk.CustomThreadFactory("Thread-"),
                new com.jimmy.thread.openSdk.CustomRejectedExecutionHandler());
        for (int i = 1; i <= 100; i++) {
//            pool.execute(new RunnableTask(i));
//            pool.execute(new LockObject("线程" + i, new SynchronizedTest()));
            try {
                OpenSDK client = new OpenSDK(3000, 10000,threadPoolExecutor);
                String response = client.buildMethod(HttpMethod.GET).buildUrl("http://localhost:8080/test/name").call();
                logger.info(response);
            }catch (Exception ex){
                logger.error(ex.getMessage());
            }


        }
        logger.info("用时：{}", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool customThreadPool = new CustomThreadPool();
        customThreadPool.start();
    }
}