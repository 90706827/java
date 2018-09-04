package com.example.demo.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java
 * @description: 缓存类线程池
 * @author: Mr.Jangni
 * @create: 2018-09-04 22:17
 **/
public class NewCachedThreadPool {
    private static Runnable getThread(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) {
        ExecutorService cachePool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            cachePool.execute(getThread(i));
        }
    }
}
