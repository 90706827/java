package com.jimmy.async.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/21 00:06
 **/
public abstract class AsyncBaseBlock<T, U, R> {
    private static final Logger logger = LoggerFactory.getLogger(SupplyAsyncBaseBlock.class);

    @Autowired
    private ExecutorService serviceThread;

    /**
     * 处理上下文
     */
    protected void context() {
        logger.info("{}-封装上下文", Thread.currentThread().getName());
    }

    /**
     * 业务逻辑实现方法
     *
     * @param t
     * @return R
     */
    public void execute(T t) {
    }

    public void execute(T t, U u) {
    }

    public void exception(T t, U u) {
    }

    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行一个异步任务并返回结果
     *
     * @param t 参数
     * @return R
     */
    public CompletableFuture<Void> runAsync(T t) {
        return CompletableFuture.runAsync(() -> {
            context();
            execute(t);
        }, serviceThread);
    }

    /**
     * 运行一个异步任务并返回结果
     *
     * @param t 参数1
     * @param u 参数2
     * @return R
     */
    public CompletableFuture<Void> runAsync(T t, U u) {
        return CompletableFuture.runAsync(() -> {
            context();
            execute(t, u);
        }, serviceThread);
    }

    /**
     * 运行一个异步任务，前置任务的结果是此任务的参数，并返回结果；
     *
     * @param future 前置任务
     * @return
     */
    public CompletableFuture<Void> thenAcceptAsync(CompletableFuture<T> future) {
        return future.thenAcceptAsync((temp) -> {
            context();
            execute(temp);
        }, serviceThread);
    }

    /**
     * 运行一个异步任务，前置任务的结果是此任务的参数，并返回结果；
     *
     * @param future 前置任务
     * @return
     */
    public CompletableFuture<T> whenCompleteAsync(CompletableFuture<T> future) {
        return future.whenCompleteAsync((temp, exception) -> {
            context();
            execute(temp, (U) exception);
        }, serviceThread);
    }

}
