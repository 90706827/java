package com.example.demo.executor;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java
 * @description: 定时任务类线程池
 * @author: Mr.jimmy
 * @create: 2018-09-04 22:20
 **/
public class NewScheduledThreadPool {
    public static void main(String args[]) {
        //线程数量需要考虑CPU的数量
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getId() + "执行了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
