package com.example.demo.concurrency.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description 总结 1：在
 * @Author Jangni
 * @Date 2018/11/21 20:31
 **/
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        int threadSum = 200;

        final CountDownLatch countDownLatch = new CountDownLatch(threadSum);
        ExecutorService executorservice = Executors.newCachedThreadPool();

        for (int i = 0; i < threadSum; i++) {
            final int threadNum = i;
            executorservice.execute(() -> {
                try {
                    add(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        executorservice.shutdown();
    }

    public static void add(int threadNum) throws InterruptedException {
        Thread.sleep(100);
    }

}
