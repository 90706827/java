package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author zhangguoq
 **/
public class ThreadTask extends Thread {
    private static final Logger logger = LoggerFactory.getLogger("task");

    @Override
    public void run() {
        logger.info("{} 执行任务：{}", Thread.currentThread().getName(), "ThreadTask");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}