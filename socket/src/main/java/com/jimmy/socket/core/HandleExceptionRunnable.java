package com.jimmy.socket.core;

import java.util.concurrent.Callable;

/**
 * @ClassName HandleExceptionRunnable
 * @Description 异常处理线程
 * @Author Mr.jimmy
 * @Date 2018/9/16 16:02
 * @Version 1.0
 **/
public class HandleExceptionRunnable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 1;
    }

    public static void main(String[] args) {
        HandleExceptionRunnable handleExceptionRunnable = new HandleExceptionRunnable();
        handleExceptionRunnable.maintest();

    }

    public void maintest(){
        HandleExceptionRunnable handleExceptionRunnable = new HandleExceptionRunnable();
        new SyncExecutor().getThreadPoolTaskExecutor().execute((Runnable) handleExceptionRunnable);
    }

}
