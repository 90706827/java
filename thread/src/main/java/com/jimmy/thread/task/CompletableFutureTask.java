package com.jimmy.thread.task;

import com.jimmy.thread.pool.CustomThreadPool;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CompletableFutureTask extends CustomThreadPool implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger("task");

    @SneakyThrows
    @Override
    public void run() {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务", Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }, pool);
        Boolean bool = future.get();
        logger.info("任务执行结果:{}", bool);
    }

}