package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author zhangguoq
 **/
public class SynchronizedDeadLock {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedTest.class);
    private Object lockA = new Object();
    private Object lockB = new Object();

    private void deadLock() {
        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                logger.info("{}获得锁-A", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }logger.info("{}开始获得锁-B", Thread.currentThread().getName());
                synchronized (lockB) {
                    logger.info("{}获得锁-B", Thread.currentThread().getName());
                    logger.info("{}释放锁-B", Thread.currentThread().getName());
                }
                logger.info("{}释放锁-A", Thread.currentThread().getName());
            }
        }, "线程A-");

        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                logger.info("{}获得锁-B", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("{}开始获得锁-A", Thread.currentThread().getName());
                synchronized (lockA) {
                    logger.info("{}获得锁-A", Thread.currentThread().getName());
                    logger.info("{}释放锁-A", Thread.currentThread().getName());
                }
                logger.info("{}释放锁-B", Thread.currentThread().getName());
            }
        }, "线程B-");

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        new SynchronizedDeadLock().deadLock();
    }
}