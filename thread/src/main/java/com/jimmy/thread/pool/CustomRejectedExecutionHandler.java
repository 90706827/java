package com.jimmy.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomRejectedExecutionHandler.class);

    public CustomRejectedExecutionHandler() {
        super();
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {

                new Thread(r).start();

        String msg = String.format("Thread pool Wain Info:" +
                        " Task, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d)," +
                        " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
                e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        logger.error(msg);
    }

}