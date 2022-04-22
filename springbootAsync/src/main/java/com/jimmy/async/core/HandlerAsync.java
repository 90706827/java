package com.jimmy.async.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/21 00:06
 **/
public abstract class HandlerAsync<T,R> extends ThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(HandlerAsync.class);

    private void context(){
        logger.info("{}-封装上下文",Thread.currentThread().getName());
    }

    protected R handler(T t){
        return null;
    }

    public CompletableFuture<R> supplyAsync(T t){
        return CompletableFuture.supplyAsync(()->{
            context();
            return handler(t);
        },service);
    }

}
