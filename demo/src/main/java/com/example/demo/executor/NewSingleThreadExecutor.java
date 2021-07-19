package com.example.demo.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java
 * @description: 单个线程线程池
 * @author: Mr.jimmy
 * @create: 2018-09-04 22:18
 **/
public class NewSingleThreadExecutor {
    private static Runnable getThread(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService singPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            singPool.execute(getThread(i));
        }
        singPool.shutdown();
    }
}
