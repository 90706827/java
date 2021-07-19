package com.example.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TaskTest
 * @Description
 * @Author Mr.jimmy
 * @Date 2018/9/16 13:12
 * @Version 1.0
 **/
public class TaskTest {
    public static void main(String[] args) {
         BlockingQueue blockingQueue=new ArrayBlockingQueue<>(10);

         ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, blockingQueue);
        TaskWithoutResult taskWithoutResult = new TaskWithoutResult(1000);
        for(int i=0;i<100;i++)
        {
            Runnable runnable=new TaskWithoutResult(1000);
            threadPoolExecutor.submit(runnable);
        }
//        threadPoolExecutor.shutdown();//不会触发中断
//        threadPoolExecutor.shutdownNow();//会触发中断

    }
}
