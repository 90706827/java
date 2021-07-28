package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author zhangguoq
 **/
public class LockObject extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(LockObject.class);
    private BaseTest test;

    public LockObject(String name, BaseTest test) {
        super(name);
        this.test = test;
    }

    @Override
    public void run() {
        test.method();
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest reentrant = new ReentrantLockTest();
        Thread thread1 = new Thread(new LockObject("线程-1", reentrant));
        Thread thread2 = new Thread(new LockObject("线程-2", reentrant));
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread2.interrupt();
    }
}