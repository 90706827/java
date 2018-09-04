package com.example.demo.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java
 * @description: 固定线程池
 * @author: Mr.Jangni
 * @create: 2018-09-04 22:15
 **/
public class NewFixedThreadPool {

    private static Runnable getThread(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) {
        ExecutorService fixPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            fixPool.execute(getThread(i));
        }
        fixPool.shutdown();
    }
}
