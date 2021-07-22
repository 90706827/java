package com.jimmy.thread.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * @Description 自定义线程工程
 * @Author zhangguoq
 **/
public class MonitorThreadFactory implements ThreadFactory {
    private static final Logger logger = LoggerFactory.getLogger(MonitorThreadFactory.class);

    private final String name;

    public MonitorThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = name;
        t.setName(threadName);
        return t;
    }
}