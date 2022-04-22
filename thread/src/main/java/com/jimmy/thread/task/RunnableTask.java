package com.jimmy.thread.task;

import com.jimmy.thread.openSdk.OpenSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

/**
 * @Description
 * @Author zhangguoq
 **/
public class RunnableTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger("task");
    private int taskNo;

    public RunnableTask(int taskNo) {
        this.taskNo = taskNo;
    }

    @Override
    public void run() {
        logger.info("{} 执行任务：{}", Thread.currentThread().getName(), taskNo);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}