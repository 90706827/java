package com.jimmy.thread.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Description 线程打印
 * @Author zhangguoq
 **/
public abstract class MonitorThreadPool {

    public static final Logger loggerBase = LoggerFactory.getLogger(MonitorThreadPool.class);

    protected ThreadPoolExecutor pool = getThreadPoolExecutor();

    private static ThreadPoolExecutor work = new ThreadPoolExecutor(
            1,
            1,
            1000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new MonitorThreadFactory("Monitor-Thread"));

    public void monitor() {
        work.execute(() -> {
            while (pool.getPoolSize() >= 0) {
                print();
                if (pool.getPoolSize() == 0) {
                    print();
                    pool.shutdown();
                    work.shutdown();
                    break;
                }
            }

        });
    }

    private void print() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loggerBase.info("----------------------------------------------------");
        loggerBase.info("核心线程数:{}", pool.getCorePoolSize());
        loggerBase.info("池中线程数:{}", pool.getPoolSize());
        loggerBase.info("队列任务数:{}", pool.getQueue().size());
        loggerBase.info("----------------------------------------------------");
    }

    public void start() {
        task();
        monitor();
    }

    /**
     * 任务
     */
    public abstract void task();

    /**
     * 自定义 线程池
     *
     * @return ThreadPoolExecutor
     */
    public abstract ThreadPoolExecutor getThreadPoolExecutor();
}