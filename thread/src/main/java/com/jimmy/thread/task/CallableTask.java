package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CallableTask implements Callable {
    private static final Logger logger = LoggerFactory.getLogger("task");

    @Override
    public Object call() throws Exception {
        logger.info("{} 执行任务：{}", Thread.currentThread().getName(), "CallableTask");
        Thread.sleep(2000);
        return 1;
    }
}