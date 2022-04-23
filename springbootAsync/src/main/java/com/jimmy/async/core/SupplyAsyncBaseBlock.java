package com.jimmy.async.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 20:00
 **/
public abstract class SupplyAsyncBaseBlock<T, U, R> {
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
    public R execute(T t) {
        return null;
    }

    public R execute(T t,U u){
        return null;
    }

    public R exception(T t,U u){
        return null;
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
     * @param t 参数
     * @return R
     */
    public CompletableFuture<R> supplyAsync(T t) {
        return CompletableFuture.supplyAsync(() -> {
            context();
            return execute(t);
        }, serviceThread);
    }
    /**
     * 运行一个异步任务并返回结果
     * @param t 参数1
     * @param u 参数2
     * @return R
     */
    public CompletableFuture<R> supplyAsync(T t,U u) {
        return CompletableFuture.supplyAsync(() -> {
            context();
            return execute(t,u);
        }, serviceThread);
    }

    /**
     * 运行一个异步任务，前置任务的结果是此任务的参数，并返回结果；
     * @param future 前置任务
     * @return
     */
    public CompletableFuture<R> thenApplyAsync(CompletableFuture<T> future) {
        return future.thenApplyAsync((temp) -> {
            context();
            return execute(temp);
        }, serviceThread);
    }

    /**
     * 运行一个异步任务，两个前置任务的结果是此任务的参数，并返回结果；
     * @param future1 任务1
     * @param future2 任务2
     * @return
     */
    public CompletableFuture<R> thenCombineAsync(CompletableFuture<T> future1,CompletableFuture<U> future2) {
        return future1.thenCombineAsync(future2,(temp1,temp2) -> {
            context();
            return execute(temp1,temp2);
        }, serviceThread);
    }


    /**
     * 运行一个异步任务，前置任务的结果是此任务的参数，并返回结果；
     * @param future 前置任务
     * @return
     */
    public CompletableFuture<T> whenCompleteAsync(CompletableFuture<T> future) {
        return future.whenCompleteAsync((temp,exception) -> {
            context();
            execute(temp, (U) exception);
        }, serviceThread);
    }
}
