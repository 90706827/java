package com.jangni.socket.core;

import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import scala.util.Try;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName HandleExceptionRunnable
 * @Description 异常处理线程
 * @Author Mr.Jangni
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
