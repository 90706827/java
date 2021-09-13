package com.jimmy.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 自定义线程工程
 * @Author zhangguoq
 **/
public class CustomThreadFactory implements ThreadFactory {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadFactory.class);
    private AtomicInteger count = new AtomicInteger(0);

    private final String name;

    public CustomThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = name + count.addAndGet(1);
        logger.info("创建线程：" + threadName);
        t.setName(threadName);
        return t;
    }
}